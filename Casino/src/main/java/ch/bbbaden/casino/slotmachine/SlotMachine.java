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

    private SlotFace[][] faces = new SlotFace[5][3];

    public SlotFace[][] getStart() {
        Random random = new Random();

        for (int i = 0; i < faces.length; i++) {
            ArrayList<Integer> numbers = new ArrayList<>();
            for (int j = 0; j < faces[i].length; j++) {
                if (j == 1) {
                    faces[i][j] = SlotFace.SUPERCHERRY;
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

    public SlotFace[][] rotate() {
        Random random = new Random();

        for (int i = 0; i < faces.length; i++) {
            ArrayList<Integer> numbers = new ArrayList<>();
            for (int j = 0; j < faces[i].length; j++) {
                int rand;
                do {
                    rand = random.nextInt(SlotFace.values().length);
                } while (numbers.contains(rand));
                numbers.add(rand);
                faces[i][j] = SlotFace.values()[rand];
            }
        }

        return faces;
    }

}
