package feedmelite.controllers;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import feedmelite.views.*;

public class PreferencesController extends TimerController 
    implements ActionListener
{
    public PreferencesController()
    {
        super(appWindow);
    }
    
    public void actionPerformed(ActionEvent evt)
    {
        String act = evt.getActionCommand();
        
        if (act.equals("Apply"))
        {
			updateTimer(PreferencesWindow.getTime());
        }
    }
}