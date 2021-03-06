/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.roulette;

import ch.bbbaden.casino.DataManager;
import java.util.*;

/**
 *
 * @author aless
 */
public class RouletteModel {

    private int betbalance = 0;
    private DataManager dm = DataManager.getInstance();
    private List<String> bets = new ArrayList<>();

    public void makebet(String bet) {
        bets.add(bet);
        int b = Integer.parseInt(bet.split(":")[1]);
        betbalance += b;
        dm.setchipamount(dm.getchipamount() - b);
        dm.updatestatistik("totalbet", b);
        dm.updatestatistik("roulettebet", b);
    }

    public int getbalance() {
        return dm.getchipamount();
    }

    public int getbetamount() {
        return betbalance;
    }

    public int check(String number) {
        int won = 0;

        for (String s : bets) {
            int currentwin = 0;
            int bet = Integer.parseInt(s.split(":")[1]);
            for (String string : s.split(":")[0].split(",")) {
                if (string.equals(number)) {

                    if (s.split(",").length != 5) {
                        currentwin += (bet * ((int) 35 / s.split(",").length)) + bet;
                    } else {
                        currentwin += (bet * ((int) 35 / s.split(",").length - 1)) + bet;
                    }
                }
            }
            if (currentwin == 0) {
                won -= bet;
            } else {
                won += currentwin;
            }
        }
        if (won > 0) {
            dm.setchipamount(dm.getchipamount() + won);
            dm.updatestatistik("totalwon", won);
            dm.updatestatistik("roulettewon", won);
        } else {
            dm.updatestatistik("totallost", won * -1);
            dm.updatestatistik("roulettelost", won * -1);
        }
        betbalance = 0;
        bets.removeAll(bets);
        return won;
    }
}
