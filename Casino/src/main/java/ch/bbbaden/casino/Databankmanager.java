/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino;

/**
 *
 * @author aless
 */
public class Databankmanager {
    
    public static Databankmanager dbm = new Databankmanager();
    
    public static Databankmanager getIntance()
    {
        return dbm;
    }
}
