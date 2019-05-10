/* Autor: RaviAnand Mohabir
 * Firma: BBBaden
 * Version: 2.6
 * Erstell-Datum: 10. April 2019
 * Letzte Bearbeitung: 09. Mai 2019
 */
package ch.bbbaden.casino.slotmachine;

import ch.bbbaden.casino.DataManager;
import ch.bbbaden.casino.Tuple;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

public final class SlotMachine {

    public static final int EXTRA = 50;

    private final SlotFace[][] faces;

    private boolean updateFreespins;
    private boolean firstGame = true;

    private int spinnable = -1;

    private int bank = 200;
    private int startBank = 200;
    private int bet = 0;
    private int multiplier = 1;
    private int freespins = 0;

    private ArrayList<String> winStates = new ArrayList<>();
    
    public SlotMachine(int bank, int wheels) {
        this.bank = bank;
        startBank = bank;
        DataManager.getInstance().updateBet("slotmachine", bank);
        
        faces = new SlotFace[wheels][SlotFace.values().length + EXTRA];
        rotate();
        for (int i = 0; i < faces.length; i++) {
            faces[i][faces[i].length - 2] = SlotFace.SUPERCHERRY;
        }
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

    public boolean getUpdateFreespins() {
        boolean returnVal = updateFreespins;
        updateFreespins = false;
        return returnVal;
    }

    public int getSpinnable() {
        return spinnable;
    }

    public int getFreespins() {
        return freespins;
    }

    public Optional<ArrayList<Tuple<SlotFace, Tuple<Integer, Integer>>>> getWinTuples() {
        if (!getWinState().isPresent() || firstGame) {
            firstGame = false;
            return Optional.empty();
        }

        ArrayList<Tuple<SlotFace, Tuple<Integer, Integer>>> tuples = new ArrayList<>();
        if (getScatter().isPresent()) {
            tuples.addAll(getScatter().get());
        } else if (getDiagonal().isPresent()) {
            tuples.addAll(Arrays.asList(getDiagonal().get()));
        } else if (getStraight().isPresent()) {
            tuples.addAll(getStraight().get());
        }

        if (tuples.size() >= 3) {
            return Optional.of(tuples);
        } else {
            return Optional.empty();
        }
    }

    private Optional<ArrayList<Tuple<SlotFace, Tuple<Integer, Integer>>>> getScatter() {
        ArrayList<Tuple<SlotFace, Tuple<Integer, Integer>>> tuples = new ArrayList<>();
        for (int i = 0; i < faces.length; i++) {
            for (int j = 1; j < 4; j++) {
                int x = faces[i].length - j;
                SlotFace face = faces[i][x];
                if (face == SlotFace.SCATTER) {
                    tuples.add(new Tuple(face, new Tuple(x, i)));
                }
            }
        }
        if (tuples.size() >= 3) {
            return Optional.of(tuples);
        } else {
            return Optional.empty();
        }
    }

    public void rotate() {
        for (int i = 0; i < faces.length; i++) {
            generateRoll(i);
        }
    }

    public void generateRoll(int index) {
        Random random = new Random();
        ArrayList<Integer> numbers = new ArrayList<>();

        for (int j = 0; j < faces[index].length; j++) {
            if (j <= EXTRA) {
                int rand = random.nextInt(SlotFace.values().length);
                faces[index][j] = SlotFace.values()[rand];
            } else {
                int rand;
                do {
                    rand = random.nextInt(SlotFace.values().length);
                } while (numbers.contains(rand));
                numbers.add(rand);
                faces[index][j] = SlotFace.values()[rand];
            }
        }
    }

    public void spinSpinnable() {
        int spinRoll = spinnable;
        spinnable = -1;
        generateRoll(spinRoll);
    }

    public SlotFace[][] getFaces() {
        return faces;
    }

    private Optional<Tuple<SlotFace, Tuple<Integer, Integer>>[]> getDiagonal() {
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

    private Optional<ArrayList<Tuple<SlotFace, Tuple<Integer, Integer>>>> getStraight() {
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

    public void handleWinState() {
        Optional<WinState> state = getWinState();

        if (freespins > 0) {
            freespins--;
        }
        if (!state.isPresent() && freespins == 0) {
            multiplier = 1;
            bank -= bet;
        } else if (state.isPresent()) {
            winStates.add(state.get().toString());
            switch (state.get()) {
                case CHERRY:
                    bank += bet * multiplier;
                    bet = 1;
                    multiplier = 1;
                    break;
                case GREENBAR:
                    multiplier = 3;
                    break;
                case GREENSEVEN:
                    multiplier = 5;
                    break;
                case GREENSTAR:
                    if (multiplier < 3) {
                        multiplier = 3;
                    }
                    break;
                case REDSEVEN:
                    if (multiplier < 5) {
                        multiplier = 5;
                    }
                    break;
                case SCATTER:
                    bank += getScatter().get().size();
                    break;
                case SPIN:
                    Random random = new Random();
                    spinnable = random.nextInt(5);
                    break;
                case SUPERCHERRY:
                    bank += bet * multiplier + 5;
                    bet = 1;
                    multiplier = 1;
                    break;
                case YELLOWBAR:
                    freespins += 2;
                    updateFreespins = true;
                    break;
                case YELLOWSEVEN:
                    multiplier = 2;
                    break;
            }
        }
    }

    public Optional<WinState> getWinState() {
        WinState state = null;

        Optional<Tuple<SlotFace, Tuple<Integer, Integer>>[]> diagonalResult = getDiagonal();
        Optional<ArrayList<Tuple<SlotFace, Tuple<Integer, Integer>>>> straightResult = getStraight();
        Optional<ArrayList<Tuple<SlotFace, Tuple<Integer, Integer>>>> scatter = getScatter();

        if (bank <= 0) {
            state = WinState.LOST;
        } else if (scatter.isPresent()) {
            state = WinState.SCATTER;
        } else if (diagonalResult.isPresent()) {
            SlotFace face = diagonalResult.get()[0].x;
            switch (face) {
                case SPIN:
                    state = WinState.SPIN;
                    break;
                case REDSEVEN:
                    state = WinState.REDSEVEN;
                    break;
                case GREENSTAR:
                    state = WinState.GREENSTAR;
                    break;
                case YELLOWBAR:
                    state = WinState.YELLOWBAR;
                    break;
            }
        } else if (straightResult.isPresent()) {
            Tuple<SlotFace, Tuple<Integer, Integer>> first = straightResult.get().get(0);
            SlotFace face = first.x;
            switch (face) {
                case GREENSEVEN:
                    state = WinState.GREENSEVEN;
                    break;
                case GREENBAR:
                    state = WinState.GREENBAR;
                    break;
                case YELLOWSEVEN:
                    state = WinState.YELLOWSEVEN;
                    break;
                case SUPERCHERRY:
                    if (first.y.x == faces[0].length - 2) {
                        state = WinState.CHERRY;
                        if (straightResult.get().size() == 5) {
                            state = WinState.SUPERCHERRY;
                        }
                    }
                    break;
            }
        }

        if (state == null) {
            return Optional.empty();
        } else {
            return Optional.of(state);
        }
    }

    public boolean evaluateFace(int i) {
        boolean w = false;
        SlotFace face = faces[i][faces[i].length - 2];
        if (face == SlotFace.SUPERCHERRY) {
            w = true;
        }
        return w;
    }

    public void handleFace(int i) {
        if (evaluateFace(i)) {
            bank += bet * multiplier;
            bet = 1;
            multiplier = 1;
            winStates.add(WinState.CHERRY.toString());
        }
    }
    
    public void endGame(){
        DataManager.getInstance().updateWon("slotmachine", bank - startBank);
    }
    
    @Override
    public String toString(){
        String ws = "Wins:\n" + String.join("\n", winStates);
        String credits = "Start credits: " + startBank + "\nEnd credits: " + bank;
        int balance = DataManager.getInstance().getchipamount() - (bank - startBank);
        String curBank = "Current bank value: " + balance;
        return String.join("\n\n", credits, ws, curBank);
    }

}
