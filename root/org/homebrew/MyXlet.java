package org.homebrew;

import java.io.*;

import java.util.*;

import java.awt.*;
import java.net.*;

import javax.media.*;

import javax.tv.xlet.*;

import org.bluray.ui.event.HRcEvent;
import org.bluray.net.BDLocator;
import org.davic.net.Locator;

import org.dvb.event.EventManager;
import org.dvb.event.UserEvent;
import org.dvb.event.UserEventListener;
import org.dvb.event.UserEventRepository;

import org.havi.ui.*;


public class MyXlet implements Xlet, UserEventListener
{
    private HScene scene;
    private Container gui;
    private XletContext context;
    private final ArrayList messages = new ArrayList();
    public void initXlet(XletContext context)
    {
        this.context = context;
        // START: Code required for text output. 
        scene = HSceneFactory.getInstance().getDefaultHScene();
        try
        {
            gui = new Screen(messages);
            gui.setSize(1920, 1080); // BD screen size
            scene.add(gui, BorderLayout.CENTER);
            // END: Code required for text output.
            UserEventRepository repo = new UserEventRepository("input");
            repo.addAllArrowKeys();
            repo.addAllColourKeys();
            repo.addAllNumericKeys();
            repo.addKey(HRcEvent.VK_ENTER);
            repo.addKey(HRcEvent.VK_POPUP_MENU);
            repo.addKey(19);
            repo.addKey(424);
            repo.addKey(425);
            repo.addKey(412);
            repo.addKey(417);
            EventManager.getInstance().addUserEventListener(this, repo);
            (new Thread()
            {
                public void run()
                {
                    try
                    {
                        PowerOf3.run(messages, scene);
                    }
                    catch(Exception e)
                    {
                        printStackTrace(e);
                        scene.repaint();
                    }
                }
            }).start();
        }
        catch(Exception e)
        {
            printStackTrace(e);
        }
        scene.validate();
    }
    // Don't touch any of the code from here on.
    public void startXlet()
    {
        gui.setVisible(true);
        scene.setVisible(true);
        gui.requestFocus();
    }
    public void pauseXlet()
    {
        gui.setVisible(false);
    }
    public void destroyXlet(boolean unconditional)
    {
        scene.remove(gui);
        scene = null;
    }
    private void printStackTrace(Throwable e)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String trace = sw.toString();
        if(trace.charAt(trace.length()-1) != '\n')
            trace += '\n';
        String line = "";
        for(int i = 0; i < trace.length(); i++)
        {
            char x = trace.charAt(i);
            if(x == '\n')
            {
                messages.add(line);
                line = "";
            }
            else
                line += x;
        }
    }
    public void userEventReceived(UserEvent evt)
    {
    }
}
