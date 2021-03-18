package com.medzaksdn.tenpinbowling;

import java.util.Scanner;

/**
 * @author Mohamed-Zakaria SAIDANE
 */
public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Please entry the sequence : ");
        String sequence = in.nextLine();

        Game game = new Game();
        try {
            game.setSequence(sequence);
            System.out.println("***** Score Sheet *****");
            game.printScoreSheet();
        } catch(final IllegalArgumentException e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

}