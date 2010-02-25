/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sillarai.models;

import com.sillarai.controllers.Controller;
import com.sillarai.utils.Logger;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author raghul
 */
public class Expense {

    private String description;
    private float amount;
    private String exp_time;

    private static int expenseIndex;
    private Controller controller;

    public Expense(Controller control){
        controller = control;
        expenseIndex = controller.getSettings().getExpenseIndex();
        expenseIndex++;
        Calendar c = Calendar.getInstance();
        Date d = new Date();
        c.setTime(d);
        exp_time = c.getTime().toString();

    }

    public static int getExpenseIndex() {
        return expenseIndex;
    }

    public static void setExpenseIndex(int expenseIndex) {
        Expense.expenseIndex = expenseIndex;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExp_time() {
        return exp_time;
    }



}
