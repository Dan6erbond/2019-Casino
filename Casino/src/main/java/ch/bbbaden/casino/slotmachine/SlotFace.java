/* Autor: RaviAnand Mohabir
 * Firma: BBBaden
 * Version: 1.0
 * Erstell-Datum: 10. April 2019
 * Letzte Bearbeitung: 02. Mai 2019
 */
package ch.bbbaden.casino.slotmachine;

import javafx.scene.image.Image;

public enum SlotFace {
    
    SPIN, GREENBAR, GREENSEVEN, GREENSTAR, REDSEVEN, YELLOWBAR, SCATTER, YELLOWSEVEN, SUPERCHERRY;
    
    //caching the images in a static context to save on RAM
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
        //returns an image based on the enum value
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
