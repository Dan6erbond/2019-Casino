/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.blackjack;

import java.util.Comparator;
import javafx.scene.image.Image;

/**
 *
 * @author marin
 */
public class Card {

    private final CardFace face;
    private CardValue value;

    public Card(CardFace face, CardValue value) {
        this.face = face;
        this.value = value;
    }

    public Image getImage() {
        String imageName = "";
        
        switch (face) {
            case HEART:
                switch (value) {
                    case TWO:
                        imageName = "2H";
                        break;
                    case THREE:
                        imageName = "3H";
                        break;
                    case FOUR:
                        imageName = "4H";
                        break;
                    case FIVE:
                        imageName = "5H";
                        break;
                    case SIX:
                        imageName = "6H";
                        break;
                    case SEVEN:
                        imageName = "7H";
                        break;
                    case EIGHT:
                        imageName = "8H";
                        break;
                    case NINE:
                        imageName = "9H";
                        break;
                    case TEN:
                        imageName = "10H";
                        break;
                    case JACK:
                        imageName = "JH";
                        break;
                    case QUEEN:
                        imageName = "QH";
                        break;
                    case KING:
                        imageName = "KH";
                        break;
                    case ACE:
                        imageName = "AH";
                        break;
                }
                break;
            case SPADES:
                switch(value){
                    case TWO:
                        imageName = "2S";
                        break;
                    case THREE:
                        imageName = "3S";
                        break;
                    case FOUR:
                        imageName = "4S";
                        break;
                    case FIVE:
                        imageName = "5S";
                        break;
                    case SIX:
                        imageName = "6S";
                        break;
                    case SEVEN:
                        imageName = "7S";
                        break;
                    case EIGHT:
                        imageName = "8S";
                        break;
                    case NINE:
                        imageName = "9S";
                        break;
                    case TEN:
                        imageName = "10S";
                        break;
                    case JACK:
                        imageName = "JS";
                        break;
                    case QUEEN:
                        imageName = "QS";
                        break;
                    case KING:
                        imageName = "KS";
                        break;
                    case ACE:
                        imageName = "AS";
                        break;
                }
                break;
            case DIAMOND:
                switch(value){
                    case TWO:
                        imageName = "2D";
                        break;
                    case THREE:
                        imageName = "3D";
                        break;
                    case FOUR:
                        imageName = "4D";
                        break;
                    case FIVE:
                        imageName = "5D";
                        break;
                    case SIX:
                        imageName = "6D";
                        break;
                    case SEVEN:
                        imageName = "7D";
                        break;
                    case EIGHT:
                        imageName = "8D";
                        break;
                    case NINE:
                        imageName = "9D";
                        break;
                    case TEN:
                        imageName = "10D";
                        break;
                    case JACK:
                        imageName = "JD";
                        break;
                    case QUEEN:
                        imageName = "QD";
                        break;
                    case KING:
                        imageName = "KD";
                        break;
                    case ACE:
                        imageName = "AD";
                        break;
                }
                break;
            case CLUB:
                switch(value){
                    case TWO:
                        imageName = "2C";
                        break;
                    case THREE:
                        imageName = "3C";
                        break;
                    case FOUR:
                        imageName = "4C";
                        break;
                    case FIVE:
                        imageName = "5C";
                        break;
                    case SIX:
                        imageName = "6C";
                        break;
                    case SEVEN:
                        imageName = "7C";
                        break;
                    case EIGHT:
                        imageName = "8C";
                        break;
                    case NINE:
                        imageName = "9C";
                        break;
                    case TEN:
                        imageName = "10C";
                        break;
                    case JACK:
                        imageName = "JC";
                        break;
                    case QUEEN:
                        imageName = "QC";
                        break;
                    case KING:
                        imageName = "KC";
                        break;
                    case ACE:
                        imageName = "AC";
                        break;
                }
            default:
                break;
        }

        return new Image("/img/cards/" + imageName + ".png");
    }
    
    public CardValue getValue(){
        return value;
    }
    
    public static Comparator<Card> comparator = (Card c1, Card c2) -> {
        CardValue cv1 = c1.getValue();
        CardValue cv2 = c2.getValue();
        
        return cv1.compareTo(cv2);
    };
}