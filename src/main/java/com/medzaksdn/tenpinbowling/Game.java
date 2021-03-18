package com.medzaksdn.tenpinbowling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mohamed-Zakaria SAIDANE
 */
public class Game {

    private int numberOfFrames=0;
    private Map<Integer, List<BowlingThrow>> bowlingFrames;

    public void setSequence(final String  sequence) {
        bowlingFrames = new HashMap<>();
        boolean frameEnd = false;
        List<BowlingThrow> bowlingThrows = new ArrayList<>();
        for(int i=0; i<sequence.length(); i++) {
            BowlingThrow bowlingThrow = new BowlingThrow(sequence.charAt(i));
            if(bowlingThrow.isSpare() && bowlingThrows.size() == 0)
                throw new IllegalArgumentException("There is an impossible spare in the sequence");

            if(bowlingThrows.size() == 1)
                bowlingThrow.setPreviousBowlingThrow(bowlingThrows.get(0));

            bowlingThrows.add(bowlingThrow);

            if(bowlingThrow.isStrike())
                frameEnd = true;

            if(frameEnd) {
                this.bowlingFrames.put(this.numberOfFrames, bowlingThrows);
                bowlingThrows = new ArrayList<>();
                this.numberOfFrames++;
            }

            frameEnd = !frameEnd;
        }
    }

    public void printScoreSheet() {
        System.out.print("Frame :         ");
        for(int i=0; i<this.numberOfFrames; i++) {
            System.out.print(i+1 +"     ");
        }
        System.out.println();

        System.out.print("Pins knocked : |");
        for(int i=0; i<this.numberOfFrames; i++) {
            for(BowlingThrow bowlingThrow : this.bowlingFrames.get(i)) {
                if(bowlingThrow.isStrike())
                    System.out.print(bowlingThrow.getPin()+"| |");
                else
                    System.out.print(bowlingThrow.getPin()+"|");
            }
            System.out.print(" |");
        }
        System.out.println();

        System.out.print("Score :         ");
        int frameScore;
        int totalScore = 0;
        List<BowlingThrow> bowlingFrames;
        for(int i=0; i<this.numberOfFrames; i++) {
            frameScore = 0;
            bowlingFrames = this.bowlingFrames.get(i);
            if(bowlingFrames.size() == 2) {
                frameScore = bowlingFrames.get(0).getNumberOfPinsKnocked() + bowlingFrames.get(1).getNumberOfPinsKnocked();
                if(i<9 && bowlingFrames.get(1).isSpare())
                    frameScore += this.bowlingFrames.get(i+1).get(0).getNumberOfPinsKnocked();
            }
            else if(bowlingFrames.get(0).isStrike()) {
                frameScore = bowlingFrames.get(0).getNumberOfPinsKnocked();
                if(i<9) {
                    if(this.bowlingFrames.get(i+1).size() == 2)
                        frameScore+= this.bowlingFrames.get(i+1).get(0).getNumberOfPinsKnocked() + this.bowlingFrames.get(i+1).get(1).getNumberOfPinsKnocked();
                    else
                        frameScore += this.bowlingFrames.get(i+1).get(0).getNumberOfPinsKnocked() + this.bowlingFrames.get(i+2).get(0).getNumberOfPinsKnocked();
                }
            }
            totalScore+= frameScore;
            System.out.print("|"+totalScore+"|  ");
        }
        System.out.println();
        System.out.print("Total score : "+ totalScore);
    }

}