/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.sillarai.controllers.Controller;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;

/**
 * @author raghul
 */
public class SillaraiMidlet extends MIDlet {
    private Display display;
    private Controller controller;

    public void startApp() {
        display = Display.getDisplay(this);
        controller = new Controller(this,display);
        controller.showExpenseScreen();
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
