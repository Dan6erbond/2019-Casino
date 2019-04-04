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
    
    public Field(boolean isblack)
    {
        this.isblack = isblack;
    }
    
    public boolean isblack()
    {
        return isblack;
    }
}
