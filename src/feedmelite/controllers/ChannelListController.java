package feedmelite.controllers;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.table.DefaultTableModel;

import feedmelite.views.AppWindow;
import feedmelite.helpers.*;
import feedmelite.models.ListFeed;

/**
 * This class will only manage the Channel list actions.
 */
public class ChannelListController extends ActionController 
    implements ListSelectionListener
{
    public ChannelListController(AppWindow appWindow)
    {
        super(appWindow);
    }
    
    /**
     * When a different Channel is selected, this method is called.
     * It gets the Item's for the selected <code>Channel</code> and 
     * displays them in the table.
     */
    public void valueChanged(ListSelectionEvent evt)
    {
        ListFeed selectedChannel = (ListFeed)
            appWindow.channelsList.getSelectedValue();
        
        if (selectedChannel != null)
        {
            NonEditableTableModel tableItems = 
                AppHelper.channelToTable(selectedChannel);
            
            appWindow.feedItems.setModel(tableItems);
        }
    }
}