/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sillarai.views;

import com.sillarai.controllers.Controller;
import com.sillarai.models.RecorderSettings;
import com.sillarai.utils.Logger;
import com.sillarai.utils.StringUtil;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

/**
 *
 * @author raghul
 */
public class ListExpenses extends List implements CommandListener{

    private Command backCommand;
    private Command deleteCommand;
    private Command editCommand;

    private Controller controller;
    private Hashtable indexTable;
    private RecorderSettings settings;

    public ListExpenses(Controller control){
        super("Expenses",List.IMPLICIT);
        controller = control;
        indexTable = new Hashtable();
        settings = controller.getSettings();
      //  showList();
        backCommand = new Command("back",Command.BACK,1);
        this.addCommand(backCommand);

        editCommand = new Command("Edit",Command.ITEM,1);
        this.addCommand(editCommand);

        deleteCommand = new Command("Delete",Command.ITEM,2);
        addCommand(deleteCommand);

        this.setCommandListener(this);
    }

    public  void showList(){
        this.deleteAll();
        Hashtable allExpenses = controller.getAllExpenses();
        int index = 0;
        Logger.info("Size "+allExpenses.size());
        for(Enumeration en = allExpenses.elements();en.hasMoreElements();){

            String data = (String)en.nextElement();
            String arr[] = StringUtil.split(data, "~~");
            String expId = arr[0];
            String description = arr[1];
            String amount = arr[2];
            String time = arr[3];

            indexTable.put(Integer.toString(index),expId);
            this.append(description +" - " +amount, null);
            index ++;

        }
    }

    public void commandAction(Command command,Displayable display){
        if(command == backCommand){
            controller.showExpenseScreen();
        }
        
        if(command == deleteCommand){
            String ii = (String)indexTable.get(String.valueOf(this.getSelectedIndex()));
           // String item = settings.getExpense(Integer.parseInt(ii), null);
            settings.setExpense(Integer.parseInt(ii), null);
            showList();
        }
        if(command == editCommand){
            String ii = (String)indexTable.get(String.valueOf(this.getSelectedIndex()));
            String item = settings.getExpense(Integer.parseInt(ii), null);
            String data = item;
            String arr[] = StringUtil.split(data, "~~");
            String expId = arr[0];
            String description = arr[1];
            String amount = arr[2];
            String time = arr[3];
            controller.description = description;
            controller.amount = amount;
            controller.expenseId = expId;
            controller.time = time;
            controller.setExpenseDetail();
            controller.showExpenseScreen();

        }
    }

}
