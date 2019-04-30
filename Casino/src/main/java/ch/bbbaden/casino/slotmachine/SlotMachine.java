/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.slotmachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author User
 */
public class SlotMachine {

    public static final int EXTRA = 50;
    private final SlotFace[][] faces = new SlotFace[5][SlotFace.values().length + EXTRA];

    public SlotFace[][] getStart() {
        SlotFace[][] fs = rotate();

        for (int i = 0; i < fs.length; i++) {
            fs[i][fs[i].length - 2] = SlotFace.SUPERCHERRY;
        }

        return fs;
    }

    public SlotFace[][] rotate() {
        Random random = new Random();

        for (int i = 0; i < faces.length; i++) {
            ArrayList<Integer> numbers = new ArrayList<>();
            for (int j = 0; j < faces[i].length; j++) {
                if (j <= EXTRA) {
                    int rand = random.nextInt(SlotFace.values().length);
                    faces[i][j] = SlotFace.values()[rand];
                } else {
                    int rand;
                    do {
                        rand = random.nextInt(SlotFace.values().length);
                    } while (numbers.contains(rand));
                    numbers.add(rand);
                    faces[i][j] = SlotFace.values()[rand];
                }
            }
        }

        return faces;
    }

    public SlotFace[][] getDisplayed() {
        SlotFace[][] fs = new SlotFace[5][3];

        for (int i = 0; i < fs.length; i++) {
            for (int j = 0; j < fs[i].length; j++) {
                fs[i][j] = faces[i][faces[i].length - j - 1];
            }
        }

        return fs;
    }

    public boolean getDiagonal() {
        SlotFace[][] fs = getDisplayed();

        for (int i = 0; i < fs.length; i++) {
            if (i > 0 && i < fs.length-1){
                SlotFace face = fs[i][1];
                if (fs[i-1][0] == face && fs[i+1][2] == face){
                    return true;
                } else if (fs[i-1][2] == face && fs[i+1][0] == face){
                    return true;
                }
            }
        }

        return false;
    }
    
    public boolean getStraight(){
        SlotFace[][] fs = getDisplayed();
        
        SlotFace previous = null;
        int count = 0;
        for (int i = 0; i < fs.length; i++) {
            if (fs[i][1] == previous){
                count++;
            } else {
                count = 0;
            }
            previous = fs[i][1];
        }
        
        if (count >= 3){
            return true;
        } else {
            return false;
        }
    }

}
