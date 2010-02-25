/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sillarai.views;

import com.sillarai.controllers.Controller;
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

    private Controller controller;

    public ListExpenses(Controller control){
        super("Expenses",List.IMPLICIT);
        controller = control;

        showList();
        backCommand = new Command("back",Command.BACK,1);
        this.addCommand(backCommand);
        //TODO
        deleteCommand = new Command("Delete",Command.SCREEN,1);
        //addCommand(deleteCommand);

        this.setCommandListener(this);
    }

    private  void showList(){
        this.deleteAll();
        Hashtable allExpenses = controller.getAllExpenses();
        Logger.info("Size "+allExpenses.size());
        for(Enumeration en = allExpenses.elements();en.hasMoreElements();){
            String data = (String)en.nextElement();
            String arr[] = StringUtil.split(data, "~~");
            String description = arr[0];
            String amount = arr[1];
            String time = arr[2];
            this.append(description +" - " +amount, null);

        }
    }

    public void commandAction(Command command,Displayable display){
        if(command == backCommand){
            controller.showExpenseScreen();
        }
        //TODO
        if(command == deleteCommand){

        }
    }

}
