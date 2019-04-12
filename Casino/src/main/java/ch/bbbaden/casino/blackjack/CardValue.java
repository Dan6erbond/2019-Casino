/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.blackjack;

/**
 *
 * @author marin
 */
public enum CardValue {
    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

    private final int value;

    private CardValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getPValue() {
        if (value == 11 && value > 21) {
            return value - 10;
        } else {
            return value;
        }
    }

    public int getBValue() {
        if (value == 11 && value > 21) {
            return value - 10;
        } else {
            return value;
        }
    }
}