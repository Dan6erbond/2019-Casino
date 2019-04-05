/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.roulette;

/**
 *
 * @author aless
 */
public class Field {
    private boolean isblack;
    private int number;
    public Field(int number)
    {
        this.number = number;
    }
    
    public boolean isblack()
    {
        return isblack;
    }
    public int getnumber()
    {
        return number;
    }
    public void setisblack(boolean isblack)
    {
        this.isblack = isblack;
    }
}
