/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.crypto.bcrypt.*;
/**
 *
 * @author aless
 */
public class Databankmanager {
    
   private int chipamount = 0;
   private String currentuser = "";
   private ServerAccess sa = new ServerAccess();
   private static Databankmanager dm = new Databankmanager();
   
   public static Databankmanager getInstance()
   {
       return dm;
   }
   
   private Databankmanager()
   {
       try {
           sa.InitSocket("84.74.61.42", 1756);
       } catch (IOException ex) {
           System.out.println("Can't connect to server. Try later");
       }
   }
   
   public void setcurrentuser(String currentuser)
   {
       this.currentuser = currentuser;
   }
   
   public String getcurrentuser()
   {
       return currentuser;
   }
   
   public void setchipamount(int chipamount)
   {
       this.chipamount = chipamount;
       updatechipamount(chipamount);
   }
   
   public int getchipamount()
   {
       return chipamount;
   }
   
   private void updatechipamount(int chipamount)
   {
       sa.send("updatechipamount:"+chipamount+";"+currentuser);
   }
   
   public void updatestatistik() // test
   {
       
   }
   
   
}
