/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sillarai.views;


import com.sillarai.controllers.Controller;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;


/**
 *
 * @author raghul
 */
public class LoginScreen extends Form implements CommandListener {

    private Controller controller;
    private Command loginCommand;
    private Command exitCommand;
    private TextField usernameField;
    private TextField passwordField;
    private ChoiceGroup rememberValuesChoice;

    /**
     * Creates a new instance of LoginForm
     * @param controller    Application controller.
     */
    public LoginScreen(Controller controller) {
        super("Login");
        this.controller = controller;

        //RecorderSettings settings = controller.getSettings();

        String username = "test";//settings.getStringProperty(Settings.USERNAME, "");
        usernameField = new TextField("Username", username, 32, TextField.ANY);
        append(usernameField);

        String password = "testtest";//settings.getStringProperty(Settings.PASSWORD, "");
        passwordField = new TextField("Password", password, 32, TextField.PASSWORD);
        append(passwordField);

        String[] labels = {"Save credentials"};
        rememberValuesChoice = new ChoiceGroup("Options", ChoiceGroup.MULTIPLE, labels, null);
        append(rememberValuesChoice);

        loginCommand = new Command("Login", Command.ITEM, 1);
        this.addCommand(loginCommand);

        exitCommand = new Command("Exit", Command.EXIT, 2);
        this.addCommand(exitCommand);

        this.setCommandListener(this);
    }

    /**
     * Handle commands (Login/Logout)
     * @param cmd   Activated command.
     * @param disp  Displayable item.
     */
    public void commandAction(Command cmd, Displayable disp) {
        if (cmd == loginCommand) {
            String username = usernameField.getString();
            String password = passwordField.getString();

         //   RecorderSettings settings = controller.getSettings();
            if (rememberValuesChoice.isSelected(0)) {
                /** Store username and password */
                System.out.println("Remember");
           //     settings.setUsername(username);
            //    settings.setPassword(password);
               // controller.username = username;
                //controller.password = password;
            } else {
                /** Clear username and password */
                //Log.debug("Clear");

               // controller.username  =username;
             //   controller.password  = password;

            }

         //   HttpUtil.setBasicAuthentication(username, password);
           // controller.login(username, password);
        } else if (cmd == exitCommand) {
           // controller.exit();
        }
    }
}
