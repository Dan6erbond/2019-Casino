/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.roulette;

import ch.bbbaden.casino.DataManager;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aless
 */
public class RouletteModelTest {

    @org.junit.Test
    public void testChecksingle() {
        DataManager.getInstance().setcurrentuser("test");
        RouletteModel rm = new RouletteModel();
        rm.makebet("1:100");
        
        int resultat = rm.check("1");
        
        assertEquals(3600, resultat, 0);
    }
    
    @org.junit.Test
    public void testChecksplit() {
        DataManager.getInstance().setcurrentuser("test");
        RouletteModel rm = new RouletteModel();
        rm.makebet("1,2:100");
        
        int resultat = rm.check("1");
        
        assertEquals(1800, resultat, 0);
    }
    @org.junit.Test
    public void testCheckstreet() {
        DataManager.getInstance().setcurrentuser("test");
        RouletteModel rm = new RouletteModel();
        rm.makebet("1,2,3:100");
        
        int resultat = rm.check("1");
        
        assertEquals(1200, resultat, 0);
    }
    @org.junit.Test
    public void testCheckcorner() {
        DataManager.getInstance().setcurrentuser("test");
        RouletteModel rm = new RouletteModel();
        rm.makebet("1,2,4,5:100");
        
        int resultat = rm.check("1");
        
        assertEquals(900, resultat, 0);
    }
    @org.junit.Test
    public void testCheckfive() {
        DataManager.getInstance().setcurrentuser("test");
        RouletteModel rm = new RouletteModel();
        rm.makebet("1,2,3,0,00:100");
        
        int resultat = rm.check("1");
        
        assertEquals(700, resultat, 0);
    }
    @org.junit.Test
    public void testCheckcolour() {
        DataManager.getInstance().setcurrentuser("test");
        RouletteModel rm = new RouletteModel();
        rm.makebet("1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36:100");
        
        int resultat = rm.check("1");
        
        assertEquals(200, resultat, 0);
    }
    @org.junit.Test
    public void testCheckdozen() {
        DataManager.getInstance().setcurrentuser("test");
        RouletteModel rm = new RouletteModel();
        rm.makebet("1,2,3,4,5,6,7,8,9,10,11,12:100");
        
        int resultat = rm.check("1");
        
        assertEquals(300, resultat, 0);
    }
    @org.junit.Test
    public void testCheckuneven() {
        DataManager.getInstance().setcurrentuser("test");
        RouletteModel rm = new RouletteModel();
        rm.makebet("1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35:100");
        
        int resultat = rm.check("1");
        
        assertEquals(200, resultat, 0);
    }
    @org.junit.Test
    public void testChecklow() {
        DataManager.getInstance().setcurrentuser("test");
        RouletteModel rm = new RouletteModel();
        rm.makebet("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18:100");
        
        int resultat = rm.check("1");
        
        assertEquals(200, resultat, 0);
    }
    @org.junit.Test
    public void testCheckcolumn() {
        DataManager.getInstance().setcurrentuser("test");
        RouletteModel rm = new RouletteModel();
        rm.makebet("1,4,7,10,13,16,19,22,25,28,31,34:100");
        
        int resultat = rm.check("1");
        
        assertEquals(300, resultat, 0);
    }
}
