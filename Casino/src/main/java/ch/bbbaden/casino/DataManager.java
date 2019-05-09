/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aless
 */
public class DataManager {

    private int chipamount = 0;
    private String currentuser = "";
    private ServerAccess sa = new ServerAccess();
    private static DataManager dm = new DataManager();

    public static DataManager getInstance() {
        return dm;
    }

    private DataManager() {
        try {
            //the first is the ip of my Server, the second is the port. It connects to the Server through the sockets
            sa.InitSocket("84.74.61.42", 1756);
        } catch (IOException ex) {
            System.out.println("Not connected to server! Please try using a VPN!");
        }

    }

    public void setcurrentuser(String currentuser) {
        this.currentuser = currentuser;
        chipamount = Integer.parseInt(readchipamount("chipamount"));
    }

    public String getcurrentuser() {
        return currentuser;
    }

    public void setchipamount(int chipamount) {
        this.chipamount = chipamount;
        updatechipamount(chipamount);
    }

    public int getchipamount() {
        return chipamount;
    }

    private void updatechipamount(int chipamount) {
        sa.send("updateplayer:chipamount;" + chipamount + ";" + currentuser);
    }

    public void updatestatistik(String table, int amount) {
        sa.send("updatestatistik:" + table + ";" + amount + ";" + currentuser);
    }

    public String readstatistik(String table, String username) {
        sa.send("readstatistik:" + table + ";" + username);
        Thread thread = new Thread() {
            public void run() {
                while (true) {

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {

                    }
                    if (sa.getmessage() != null) {
                        break;
                    }
                }
            }
        };
        thread.run();
        return sa.getmessage();
    }

    public String readchipamount(String table) {
        sa.send("readchipamount:" + table + ";" + currentuser);
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {

                    }
                    if (sa.getmessage() != null) {
                        break;
                    }
                }
            }
        };
        thread.run();
        return sa.getmessage();
    }

    public String readusername(String table) {
        sa.send("readusername:" + table + ";");
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                    }
                    if (sa.getmessage() != null) {
                        break;
                    }
                }
            }
        };
        thread.run();
        return sa.getmessage();
    }

    public void updateBet(String game, int bet) {
        setchipamount(dm.getchipamount() - bet);
        dm.updatestatistik("totalbet", bet);
        dm.updatestatistik(game + "bet", bet);
    }

    public void updateWon(String game, int won) {
        if (won > 0) {
            dm.setchipamount(dm.getchipamount() + won);
        }
        int wonNormalized = won > 0 ? won : -won;
        String total = won > 0 ? "totalwon" : "totallost";
        dm.updatestatistik(total, wonNormalized);
        String gameWon = won > 0 ? game + "won" : game + "lost";
        dm.updatestatistik(gameWon, wonNormalized);
    }
    public void close()
    {
        try {
            sa.send("/quit");
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
