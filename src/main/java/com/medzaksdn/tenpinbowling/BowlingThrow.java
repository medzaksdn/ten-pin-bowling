package com.medzaksdn.tenpinbowling;

/**
 * @author Mohamed-Zakaria SAIDANE
 */
public class BowlingThrow {
    private char pin;
    private BowlingThrow previousBowlingThrow;

    public BowlingThrow(char pin) {
        this.isValid(pin);
        this.pin = pin;
    }

    public char getPin() {
        return pin;
    }

    public void setPreviousBowlingThrow(BowlingThrow previousBowlingThrow) {
        this.previousBowlingThrow = previousBowlingThrow;
    }

    public int getNumberOfPinsKnocked() {
        switch(Character.toUpperCase(this.pin)) {
            case 'X':
                return 10;
            case '/':
                return 10 - this.previousBowlingThrow.getNumberOfPinsKnocked();
            case '-':
                return 0;
            default:
                return Integer.parseInt(String.valueOf(this.pin));
        }
    }

    public boolean isStrike() {
        return Character.toUpperCase(this.pin) == 'X';
    }

    public boolean isSpare() {
        return this.pin == '/';
    }

    private void isValid(char pin) {
        String authorizedCharacter = "-/X123456789";
        if(authorizedCharacter.indexOf(Character.toUpperCase(pin)) == -1)
            throw new IllegalArgumentException("There is an invalid character in the sequence");
    }

}