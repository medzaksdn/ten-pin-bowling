package com.medzaksdn.tenpinbowling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mohamed-Zakaria SAIDANE
 */
public class Game {
    private Map<Integer, List<BowlingThrow>> bowlingFrames;

    public void setSequence(final String sequence) {
        bowlingFrames = new HashMap<>();
        int numberOfFrames=0;
        boolean frameEnd = false;
        List<BowlingThrow> bowlingThrows = new ArrayList<>();
        for(int i=0; i<sequence.length(); i++) {
            BowlingThrow bowlingThrow = new BowlingThrow(sequence.charAt(i));
            if(bowlingThrow.isSpare() && bowlingThrows.size() == 0)
                throw new IllegalArgumentException("There is an impossible spare in the sequence");

            if(bowlingThrows.size()>0)
                bowlingThrow.setPreviousBowlingThrow(bowlingThrows.get(0));

            bowlingThrows.add(bowlingThrow);

            if(numberOfFrames<9) {
                if (bowlingThrow.isStrike())
                    frameEnd = true;

                if (frameEnd) {
                    this.bowlingFrames.put(numberOfFrames, bowlingThrows);
                    bowlingThrows = new ArrayList<>();
                    numberOfFrames++;
                }

                frameEnd = !frameEnd;
            }
            else if(i == sequence.length()-1)
                this.bowlingFrames.put(numberOfFrames, bowlingThrows);
        }
    }

    public int printScoreSheetAndReturnTotal() {
        System.out.println(this.bowlingFrames.size());
        System.out.print("Frame :         ");
        for(int i=0; i<this.bowlingFrames.size(); i++) {
            System.out.print(i+1 +"     ");
        }
        System.out.println();

        System.out.print("Pins knocked : |");
        for(int i=0; i<this.bowlingFrames.size(); i++) {
            for(BowlingThrow bowlingThrow : this.bowlingFrames.get(i)) {
                if (bowlingThrow.isStrike()) {
                    if (i < 9)
                        System.out.print(bowlingThrow.getPin() + "| |");
                    else
                        System.out.print(bowlingThrow.getPin() + "|");
                } else
                    System.out.print(bowlingThrow.getPin() + "|");
            }
            if(i!=this.bowlingFrames.size()-1)
                System.out.print(" |");
        }
        System.out.println();

        System.out.print("Score :         ");
        int frameScore;
        int totalScore = 0;
        List<BowlingThrow> bowlingFrame;
        for(int i=0; i<this.bowlingFrames.size(); i++) {
            frameScore = 0;
            bowlingFrame = this.bowlingFrames.get(i);
            if(bowlingFrame.size() > 1) {
                frameScore = bowlingFrame.stream().mapToInt(bowlingThrow->bowlingThrow.getNumberOfPinsKnocked()).sum();
                if(i<9 && bowlingFrame.get(1).isSpare())
                    frameScore += this.bowlingFrames.get(i+1).get(0).getNumberOfPinsKnocked();
            }
            else if(bowlingFrame.get(0).isStrike()) {
                if(i<9) {
                    frameScore = bowlingFrame.get(0).getNumberOfPinsKnocked();
                    if(this.bowlingFrames.get(i+1).size() > 1)
                        frameScore+= this.bowlingFrames.get(i+1).get(0).getNumberOfPinsKnocked() + this.bowlingFrames.get(i+1).get(1).getNumberOfPinsKnocked();
                    else
                        frameScore += this.bowlingFrames.get(i+1).get(0).getNumberOfPinsKnocked() + this.bowlingFrames.get(i+2).get(0).getNumberOfPinsKnocked();
                }
                else {
                    frameScore = bowlingFrame.stream().mapToInt(bowlingThrow->bowlingThrow.getNumberOfPinsKnocked()).sum();
                }
            }
            totalScore+= frameScore;
            System.out.print("|"+totalScore+"|  ");
        }
        System.out.println();
        System.out.println("Total score : "+ totalScore);
        return totalScore;
    }

}