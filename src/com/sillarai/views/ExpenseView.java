/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sillarai.views;

import com.sillarai.controllers.Controller;
import com.sillarai.models.Expense;
import com.sillarai.models.RecorderSettings;
import com.sillarai.utils.Logger;
import java.util.Hashtable;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author raghul
 */
public class ExpenseView extends Form implements CommandListener{

    private Controller controller;
    private RecorderSettings settings;

    private TextField descriptionField;
    private TextField amountField;

    private Command saveCommand;
    private Command listExpenseCommand;
    private Command exportCommand;
    private Command exitCommand;


    public ExpenseView(Controller controller) {
        super("Sillarai");
        this.controller = controller;
        
        descriptionField = new TextField("Details", "", 32, TextField.ANY);
        append(descriptionField);

        amountField = new TextField("Amount", "", 32, TextField.DECIMAL);
        append(amountField);



        saveCommand = new Command("Save", Command.ITEM, 1);
        this.addCommand(saveCommand);

        listExpenseCommand = new Command("List", Command.ITEM, 1);
        this.addCommand(listExpenseCommand);

        exportCommand = new Command("Export",Command.ITEM,2);
        this.addCommand(exportCommand);

        exitCommand = new Command("Exit", Command.EXIT, 2);
        this.addCommand(exitCommand);

        this.setCommandListener(this);
    }

    public void setExpenseDetail(){
        String desc ="";
        Logger.info(controller.description);
        if(!controller.description.equals("")){
            desc = controller.description;
        }
        Logger.info("AMT "+controller.amount);
        String amt ="";
        if(!controller.amount.equals("")){
            amt = controller.amount;
        }
        descriptionField.setString(desc);
        amountField.setString(amt);


    }
     public void commandAction(Command command, Displayable disp) {

         if(command == saveCommand){
             String descp = descriptionField.getString();
             String amount = amountField.getString();
             if( descp.equals("") || amount.equals("")){
                 controller.showAlert("Error", "Enter all the data.", AlertType.ERROR);
             }else{

                if(controller.description.equals("")){
                controller.saveExpense(descp, Float.parseFloat(amount));
                clearFields();
                controller.showAlert("Success", "Expense saved !", AlertType.INFO);
                Logger.info(String.valueOf(Expense.getExpenseIndex()));
                }else{

                 controller.getSettings().updateExpense(controller.expenseId, descp, amount, controller.time);
                clearFields();
                controller.showAlert("Success", "Updated Expense !", AlertType.INFO);
                }
             }

         }
         if(command == exitCommand){
             controller.exit();
         }
         if(command == listExpenseCommand){
             controller.showListExpenses();
         }
         if(command == exportCommand){
             Hashtable ch = controller.getAllExpenses();
             if(ch.size() == 0){
                 controller.showAlert("Information", "Noting to export.", AlertType.INFO);
             }else{
             controller.showExportScreen(disp);
             }
         }

     }

     public void clearFields(){
         amountField.setString("");
         descriptionField.setString("");
     }

}
