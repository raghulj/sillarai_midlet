/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sillarai.controllers;

import com.sillarai.models.Expense;
import com.sillarai.models.RecorderSettings;
import com.sillarai.utils.Logger;
import com.sillarai.utils.StringUtil;
import com.sillarai.views.ExpenseView;
import com.sillarai.views.FileChooser;
import com.sillarai.views.ListExpenses;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author raghul
 */
public class Controller {

    private Display display;
    private MIDlet midlet;
    private Alert alertBox;
    public String filePath;

    private Controller controller;
    private RecorderSettings rSettings;
    private ExpenseView expenseView;
    private ListExpenses listExpenses;
    private FileChooser filechooser;

    private boolean FileWrite;

    public String description ="";
    public String amount ="";
    public String expenseId ="";
    public String time = "";

    public Controller(MIDlet mid, Display disp){
        midlet = mid;
        display = disp;
        controller = this;


        try{
            rSettings = new RecorderSettings(midlet);
        }catch(Exception e){
            Logger.error("Error in settings recorder creation "+e);
        }
    }


    public RecorderSettings getSettings(){
       return rSettings;

    }

   
    public void setExpenseIndex(int value){
        rSettings.setExpenseIndex(value);

    }


    public void showExpenseScreen(){
        if(expenseView == null){
            expenseView = new ExpenseView(this);
        }
        display.setCurrent(expenseView);
    }

    public void showListExpenses(){
        if(listExpenses == null){
            listExpenses = new ListExpenses(this);
        }
        listExpenses.showList();
        display.setCurrent(listExpenses);
    }

    public void saveExpense(String detail,float amount){
        Expense exp = new Expense(this);
        exp.setAmount(amount);
        exp.setDescription(detail);
        rSettings.setExpense(Expense.getExpenseIndex(), exp);
        rSettings.setExpenseIndex(Expense.getExpenseIndex());

    }


    public void setExpenseDetail(){
        expenseView.setExpenseDetail();
    }
    public void showAlert(String title,String mess,AlertType al){
        alertBox = new Alert(title,mess,null,al);
        alertBox.setTimeout(Alert.FOREVER);
        final Thread t = new Thread( new Runnable(){
            public void run(){
                try{
                    Display.getDisplay(midlet).setCurrent(alertBox);
                }catch(IllegalArgumentException e){

                }
            }
        });
        t.start();
    }

    public Hashtable getAllExpenses(){
        Hashtable expenses = new Hashtable();
        int expenseCount = rSettings.getExpenseIndex();
        for(int i=1; i <= expenseCount;i++){
            String expData = rSettings.getExpense(i, null);
            if(!expData.equals("")){
            expenses.put(Integer.toString(i), expData);
            }
        }
        return expenses;
    }


    public FileChooser getFileChooser(Displayable displayable) {
        filechooser = new FileChooser(this, "E:/",false, displayable);
        return filechooser;
    }


    public void showExportScreen(final Displayable displayable) {

        Thread t = new Thread() {

            public void run() {
                //super.run();
                display.setCurrent(getFileChooser(displayable));
            }
        };
        t.start();
    }

    public void exit(){
        midlet.notifyDestroyed();
    }
    
    public void generateXLReport(){
        Hashtable allExpenses = getAllExpenses();
        String xlData = "";
        xlData += "Description\tAmount\tDate Time\n";
        for(Enumeration en = allExpenses.elements();en.hasMoreElements();){
            String data = (String)en.nextElement();
            String arr[] = StringUtil.split(data, "~~");
            String description = arr[0];
            String amount = arr[1];
            String time = arr[2];
            xlData += description+"\t"+amount+"\t"+time+"\n";
        }
       if(WritedataFile(xlData)){
           showAlert("Success","Data exported to xls format.",AlertType.INFO);
       }
    }

    public boolean WritedataFile(final String data){
        new Thread(){
            public void run(){
                super.run();
                try{
                String file = "file:///"+filePath+"date.xls";
                Logger.info(file);
                FileConnection connection = (FileConnection)
                  Connector.open(file, Connector.READ_WRITE );
                if (!connection.exists()) {
                    connection.create();
                    }
                OutputStream out = connection.openOutputStream();
                out.write( data.getBytes());
                out.flush();
                out.close();
                connection.close();
                FileWrite = true;
                }catch(Exception e){
                    Logger.error("Error in writing file "+e);
                    controller.showAlert("Error", "Error in exporting data.", AlertType.ERROR);
                   FileWrite = false;
                }
            }
        }.start();

        return FileWrite;
    }
}
