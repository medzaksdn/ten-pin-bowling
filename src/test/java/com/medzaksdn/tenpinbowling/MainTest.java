package com.medzaksdn.tenpinbowling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Mohamed-Zakaria SAIDANE
 */
public class MainTest {

    @Test
    public void testValidSequenceAndTotalScore() {
        //Given
        String sequence = "XXXXXXXXXXXX";

        //When
        Game game = new Game();
        game.setSequence(sequence);
        int totalScore = game.printScoreSheetAndReturnTotal();

        //Then
        assertEquals(totalScore, 300);
    }

    @Test
    public void testInvalidCharacter() {
        //Given
        String sequence = "Invalid character";

        //When
        Game game = new Game();
        IllegalArgumentException e =assertThrows(IllegalArgumentException.class, () -> {game.setSequence(sequence);});

        //Then
        assertEquals(e.getMessage(), "There is an invalid character in the sequence");
    }

    @Test
    public void testImpossibleSpare() {
        //Given
        String sequence = "/2";

        //When
        Game game = new Game();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {game.setSequence(sequence);});

        //Then
        assertEquals(e.getMessage(), "There is an impossible spare in the sequence");
    }

}