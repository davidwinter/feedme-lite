package feedmelite.controllers;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import feedmelite.views.AppWindow;

public class ToolBarController extends ActionController 
    implements ActionListener
{
    public ToolBarController(AppWindow appWindow)
    {
        super(appWindow);
    }
    
    public void actionPerformed(ActionEvent evt)
    {
        String act = evt.getActionCommand();
        
        if (act.equals("Add Feed"))
        {
            addNewFeed();
        }
    }
}