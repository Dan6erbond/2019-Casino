/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.roulette;

import java.util.*;

/**
 *
 * @author aless
 */
public class RouletteModel {
    
    private int balance = 0;
    private int betbalance = 0;
    List<String> bets = new ArrayList<>();
    
    public void setbalance(int balance)
    {
        this.balance = balance;
    }
    public void makebet(String bet)
    {
        bets.add(bet);
        betbalance += Integer.parseInt(bet.split(":")[1]);
        balance -= Integer.parseInt(bet.split(":")[1]);
    }
    public void spinwheel()
    {
        
    }
    public int getbalance()
    {
        return balance;
    }
    public int getbetamount()
    {
        return betbalance;
    }
    public void check(String number)
    {
        int won = 0;
        
        for(String s: bets)
        {
            int bet = Integer.parseInt(s.split(":")[1]);
            for(String string : s.split(","))
            {
                if(string.equals(number))
                {
                    if(s.split(",").length != 5){
                    won += (bet* ((int) 35/s.split(",").length))+bet;
                    }
                    else
                    {
                        won += (bet* ((int) 35/s.split(",").length-1))+bet;
                    }
                }
            }
        }
        balance += won;
        betbalance = 0;
        bets.removeAll(bets);
    }
}
