package feedmelite.controllers;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import feedmelite.views.AppWindow;

import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.sun.syndication.io.FeedException;

import feedmelite.models.ListFeed;


/**
 * This class will only manage the Menubar actions.
 */
public class MenuController extends ActionController implements ActionListener
{
    public MenuController(AppWindow appWindow)
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
        else if (act.equals("Remove Selected Feed"))
        {
            removeSelectedFeed();
        }
        else if (act.equals("Show Item Details"))
        {
            showItemDetails();
        }
        else if (act.equals("Hide Item Details"))
        {
            hideItemDetails();
        }
		else if (act.equals("Preferences"))
		{
			showPreferences();
		}
		else if (act.equals("Refresh Feeds"))
		{
			updateFeeds();
		}
    }
    
    private void removeSelectedFeed()
    {
        if (appWindow.confirmRemoveFeed() == JOptionPane.YES_OPTION)
        {
            int feed = appWindow.channelsList.getSelectedIndex();
            channels.removeElementAt(feed);
            appWindow.channelsList.setListData(channels);
        
            int numberOfFeeds = appWindow.channelsList.getModel().getSize();
        
            if (numberOfFeeds < 1)
            {
                appWindow.hideFeedItems();
                appWindow.welcomeItemDetails();
            }
            else
            {
                appWindow.channelsList.setSelectedIndex(0); 
            }
        }
        
        
    }
    
    private void showItemDetails()
    {
        // needs revising
        double position = 
            userPrefs.getDouble("item_divider_location", 0.6);
        
        if (position < 0.0 || position > 1.0)
        {
            position = 0.6;
        }
        
        appWindow.showItemDetails(position);
    }
    
    private void hideItemDetails()
    {
        appWindow.hideItemDetails();
    }
    
    private void showPreferences()
    {
        appWindow.showPreferencesWindow();
    }
}