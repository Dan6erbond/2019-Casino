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

    public Image getImage() {
        switch (this) {
            case SPIN:
                return new Image("/img/slotmachine/blue-spin.png");
            case GREENBAR:
                return new Image("/img/slotmachine/green-bar.png");
            case GREENSEVEN:
                return new Image("/img/slotmachine/green-seven.png");
            case GREENSTAR:
                return new Image("/img/slotmachine/green-star.png");
            case REDSEVEN:
                return new Image("/img/slotmachine/red-seven.png");
            case YELLOWBAR:
                return new Image("/img/slotmachine/yellow-bar.png");
            case SCATTER:
                return new Image("/img/slotmachine/yellow-scatter.png");
            case SUPERCHERRY:
                return new Image("/img/slotmachine/super-cherry.png");
            default:
                return new Image("/img/slotmachine/yellow-seven.png");
        }
    }
}
