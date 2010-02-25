


package com.sillarai.models;

import com.sillarai.utils.Logger;
import javax.microedition.midlet.MIDlet;



public class RecorderSettings {


    private static Settings settings;

    // --------------------------------------------------------------------------
    // String Constants
    // --------------------------------------------------------------------------
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String SESSION_TOKEN = "session_token";


    public RecorderSettings(MIDlet midlet) {
        try {
            settings = Settings.getInstance(midlet);
        } catch (Exception ex) {
            System.err.println("Error occured while creating an instance "
                    + "of Settings class: " + ex.toString());
        }
    }



    /** Save settings */
    private void saveSettings() {
        try {
            settings.save(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
     
     /**
      * set the city matrix
      */
     public void setUsername(String username){
         settings.setStringProperty(USERNAME, username);
         saveSettings();

     }

     public void setPassword(String password){
         settings.setStringProperty(PASSWORD, password);
         saveSettings();
     }
     /**
      * Get the entire matrix data
      */
     public String getUsername(){
         return settings.getStringProperty(USERNAME, null);
     }

     public String getPassword(){
         return settings.getStringProperty(PASSWORD, null);
     }

     public void setSessionToken(String token){
         settings.setStringProperty(SESSION_TOKEN, token);
         saveSettings();
     }

     public String getSessionToken(){
         return settings.getStringProperty(SESSION_TOKEN, null);

     }

     public int getExpenseIndex(){
         return settings.getIntProperty("EXPINDEX", 0);
     }

     public void setExpenseIndex(int value){
         settings.setIntProperty("EXPINDEX", value);
         saveSettings();
     }

     public void setExpense(int expIndex, Expense exp){
         String expense = "";
         if(exp != null){
          expense = expIndex +"~~"+exp.getDescription() +"~~"+exp.getAmount()+"~~"+exp.getExp_time();
         }else{
            expense = "";
         }
         settings.setStringProperty(String.valueOf(expIndex), expense);
         saveSettings();
     }

     public void updateExpense(String expIndex,String desc,String amt,String time){
        String expense;
        expense = expIndex +"~~"+desc +"~~"+amt+"~~"+time;
        settings.setStringProperty(expIndex, expense);
        saveSettings();

     }
     public String getExpense(int expIndex,String nu){

         String exp = settings.getStringProperty(String.valueOf(expIndex), null);
         return exp;
     }
}
