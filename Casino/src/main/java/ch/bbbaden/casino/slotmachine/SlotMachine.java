/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.slotmachine;

import ch.bbbaden.casino.Tuple;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

/**
 *
 * @author User
 */
public final class SlotMachine {

    public static final int EXTRA = 50;

    private final SlotFace[][] faces = new SlotFace[5][SlotFace.values().length + EXTRA];

    private boolean won;
    private boolean updateFreespins;
    
    private int spinnable = -1;
    
    private int bank = 200;
    private int bet = 0;
    private int multiplier = 1;
    private int freespins = 0;
    
    private final SlotMachineController controller;

    public SlotMachine(SlotMachineController controller) {
        rotate();
        won = false;
        for (int i = 0; i < faces.length; i++) {
            faces[i][faces[i].length - 2] = SlotFace.SUPERCHERRY;
        }
        this.controller = controller;
    }

    public int getBank() {
        return bank;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        int diff = bet - this.bet;
        this.bet = bet;
        bank -= diff;
    }

    public boolean getWon(){
        return won;
    }
    
    public boolean getUpdateFreespins(){
        boolean returnVal = updateFreespins;
        updateFreespins = false;
        return returnVal;
    }
    
    public int getSpinnable(){
        int returnVal = spinnable;
        spinnable = -1;
        return returnVal;
    }
    
    public int getFreespins(){
        return freespins;
    }
    
    public void evaluateWon() {
        won = false;

        if (getDiagonal().isPresent()) {
            SlotFace face = getDiagonal().get()[0].x;
            if (face == SlotFace.SPIN || face == SlotFace.REDSEVEN || face == SlotFace.YELLOWBAR || face == SlotFace.GREENSTAR) {
                won = true;
            }
        } else if (getStraight().isPresent()) {
            Tuple<SlotFace, Tuple<Integer, Integer>> first = getStraight().get().get(0);
            int y = first.y.x;
            SlotFace face = first.x;
            if (face == SlotFace.GREENSEVEN || face == SlotFace.GREENBAR || face == SlotFace.SCATTER
                    || face == SlotFace.YELLOWSEVEN || face == SlotFace.SUPERCHERRY && y == faces[0].length - 2) {
                won = true;
            }
        }
    }

    public void rotate() {
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
    }

    public SlotFace[][] getFaces() {
        return faces;
    }

    public Optional<Tuple<SlotFace, Tuple<Integer, Integer>>[]> getDiagonal() {
        Tuple<SlotFace, Tuple<Integer, Integer>>[] tuples = new Tuple[3];
        for (int i = 1; i < faces.length - 1; i++) {
            int x = faces[i].length - 2;
            SlotFace face = faces[i][x];
            tuples[0] = new Tuple(face, new Tuple(x, i));

            Tuple pos1 = new Tuple(x - 1, i - 1);
            SlotFace face1 = faces[(int) pos1.y][(int) pos1.x];
            Tuple pos2 = new Tuple(x + 1, i + 1);
            SlotFace face2 = faces[(int) pos2.y][(int) pos2.x];
            Tuple pos3 = new Tuple(x + 1, i - 1);
            SlotFace face3 = faces[(int) pos3.y][(int) pos3.x];
            Tuple pos4 = new Tuple(x - 1, i + 1);
            SlotFace face4 = faces[(int) pos4.y][(int) pos4.x];

            if (face1 == face && face2 == face) {
                tuples[1] = new Tuple(face, pos1);
                tuples[2] = new Tuple(face, pos2);
                return Optional.of(tuples);
            } else if (face3 == face && face4 == face) {
                tuples[1] = new Tuple(face, pos3);
                tuples[2] = new Tuple(face, pos4);
                return Optional.of(tuples);
            }
        }

        return Optional.empty();
    }

    public Optional<ArrayList<Tuple<SlotFace, Tuple<Integer, Integer>>>> getStraight() {
        ArrayList<Tuple<SlotFace, Tuple<Integer, Integer>>> tuples = new ArrayList<>();
        int[] js = {2, 1, 3};
        for (int j : js) {
            for (int i = 0; i < faces.length; i++) {
                int x = faces[i].length - j;
                if (tuples.isEmpty() || faces[i][x] == tuples.get(tuples.size() - 1).x && tuples.get(tuples.size() - 1).y.y == i - 1) {
                    Tuple tuple = new Tuple(faces[i][x], new Tuple(x, i));
                    tuples.add(tuple);
                } else if (tuples.size() < 3) {
                    tuples.clear();
                }
            }
            if (tuples.size() >= 3) {
                break;
            } else {
                tuples.clear();
            }
        }

        if (tuples.size() >= 3) {
            return Optional.of(tuples);
        } else {
            return Optional.empty();
        }
    }

    public void evaluate() {
        evaluateWon();
        if (freespins > 0){
            freespins--;
        }
        if (!won && freespins == 0) {
            multiplier = 1;
            bank -= bet;
        } else {
            if (getDiagonal().isPresent()) {
                SlotFace face = getDiagonal().get()[0].x;
                switch (face) {
                    case SPIN:
                        multiplier = 2;
                        break;
                    case REDSEVEN:
                        multiplier = 5;
                        break;
                    case GREENSTAR:
                        multiplier = 3;
                        break;
                    case YELLOWBAR:
                        freespins += 2;
                        updateFreespins = true;
                        break;
                }
            } else if (getStraight().isPresent()) {
                Tuple<SlotFace, Tuple<Integer, Integer>> first = getStraight().get().get(0);
                SlotFace face = first.x;
                switch (face) {
                    case GREENSEVEN:
                        multiplier = 5;
                        break;
                    case GREENBAR:
                        multiplier = 3;
                        break;
                    case SCATTER:
                        freespins += 3;
                        updateFreespins = true;
                        break;
                    case YELLOWSEVEN:
                        multiplier = 2;
                        break;
                    case SUPERCHERRY:
                        if (first.y.x == faces[0].length - 2) {
                            bank += bet * multiplier;
                            bet = 1;
                            multiplier = 1;
                        }
                        break;
                }
            }
        }
    }

}
