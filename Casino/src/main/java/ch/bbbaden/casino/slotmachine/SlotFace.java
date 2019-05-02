/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.slotmachine;

import javafx.scene.image.Image;

/**
 *
 * @author User
 */
public enum SlotFace {
    
    SPIN, GREENBAR, GREENSEVEN, GREENSTAR, REDSEVEN, YELLOWBAR, SCATTER, YELLOWSEVEN, SUPERCHERRY;
    
    public static Image[] images = {
        new Image("/img/slotmachine/blue-spin.png"),
        new Image("/img/slotmachine/green-bar.png"),
        new Image("/img/slotmachine/green-seven.png"),
        new Image("/img/slotmachine/green-star.png"),
        new Image("/img/slotmachine/red-seven.png"),
        new Image("/img/slotmachine/yellow-bar.png"),
        new Image("/img/slotmachine/yellow-scatter.png"),
        new Image("/img/slotmachine/super-cherry.png"),
        new Image("/img/slotmachine/yellow-seven.png")
    };
    
    public Image getImage() {
        Image image;
        switch (this) {
            case SPIN:
                image = images[0];
                break;
            case GREENBAR:
                image = images[1];
                break;
            case GREENSEVEN:
                image = images[2];
                break;
            case GREENSTAR:
                image = images[3];
                break;
            case REDSEVEN:
                image = images[4];
                break;
            case YELLOWBAR:
                image = images[5];
                break;
            case SCATTER:
                image = images[6];
                break;
            case SUPERCHERRY:
                image = images[7];
                break;
            default:
                image = images[8];
                break;
        }
        return image;
    }
}
