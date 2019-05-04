/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino;
import java.io.IOException;
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
            SceneManager.getInstance().getAnchorPane().getChildren().add(Panemanager.createPane(SceneManager.getInstance().getAnchorPane().getWidth(),SceneManager.getInstance().getAnchorPane().getHeight(),"Sorry, but we coulnd't \r\n connect to the Server. \r\n When you are in a school, \r\n try using a vpn"));
       }
       
   }
   
   public void setcurrentuser(String currentuser)
   {
       this.currentuser = currentuser;
       chipamount = Integer.parseInt(readchipamount("chipamount"));
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
       sa.send("updateplayer:chipamount;"+chipamount+";"+currentuser);
   }
   
   public void updatestatistik(String table,int amount) 
   {
       sa.send("updatestatistik:"+table+";"+amount+";"+currentuser);
   }
   
   public String readstatistik(String table,String username)
   {
       sa.send("readstatistik:"+table+";"+username);
               Thread thread = new Thread()
        {
            public void run()
            {
                while(true)
                {
                    
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                       
                    }
                    if(sa.getmessage() != null)
                    {
                        break;
                    }
                }
            }
        };
        thread.run();
       return sa.getmessage();
   }
   public String readchipamount(String table)
   {
       sa.send("readchipamount:"+table+";"+currentuser);
               Thread thread = new Thread()
        {
            public void run()
            {
                while(true)
                {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                       
                    }
                    if(sa.getmessage() != null){
                    break;
                    }
                }
            }
        };
       thread.run();
       return sa.getmessage();
   }
   public String readusername(String table)
   {
       sa.send("readusername:"+table+";");
               Thread thread = new Thread()
        {
            public void run()
            {
                while(true)
                {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) { 
                    }
                    if(sa.getmessage() != null)
                    {
                        break;
                    }
                }
            }
        };
        thread.run();
       return sa.getmessage();
   }
}
