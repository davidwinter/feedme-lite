package feedmelite.controllers;

import java.util.Vector;
import java.util.List;
import java.util.prefs.*;
import java.util.Iterator;

import javax.swing.JOptionPane;

import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.sun.syndication.io.FeedException;

import feedmelite.views.AppWindow;
import feedmelite.models.ListFeed;

import feedmelite.controllers.TimerController;

/**
 * All of our application controllers will inherit this class.
 * It just lets us organise the controllers a bit more.
 */
public abstract class ActionController
{
    /**
     * All of our controllers will need access to our main Application window.
     * This and Channel Vector need to be static so that all controllers
     * are using the same appwindow etc across the application.
     * Otherwise, they get over-written.
     */
    protected static AppWindow appWindow;
    
    /**
     * We need all controllers to be able to access the Channels as this
     * contains all feed info etc.
     */
    protected static Vector<ListFeed> channels = new Vector<ListFeed>();
	protected static Vector<String> urlOfFeed = new Vector();
    
    /**
     * User preferences.
     */
    protected Preferences userPrefs = 
        Preferences.userNodeForPackage(getClass());
    
    public ActionController(AppWindow appWindow)
    {
        this.appWindow = appWindow;
    }
    
    /**
     * This function will be called by multiple controllers. That's why it's
     * in the parent class of each controller.
     */
    protected void addNewFeed()
    {
        // re-enable toggle item details if hidden
        // if feeds = 1, set item details to pref setting?
        // select new feed auto so that it displays table items
        
        // call up dialog asking for feed address
        String feedURL = appWindow.addFeedDialog();
        
        // if something was entered in dialog...
        if (feedURL != null)
        {
            urlOfFeed.addElement(feedURL);
	
			try
            {
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(new URL(feedURL)));
                
                ListFeed listFeed = new ListFeed(feed);
                
                channels.add(listFeed);
                
                appWindow.channelsList.setListData(channels);
                appWindow.channelsList.setSelectedValue(listFeed, true);
                appWindow.showItemDetails(0.6);
            }
            catch (MalformedURLException e)
            {
                appWindow.showIncorrectFeedURLDialog();
            }
            catch (FeedException e)
            {
                appWindow.showInvalidFeedDialog();
            }
            catch (IOException e)
            {
                appWindow.showInaccessibleFeedDialog();
            }
        }     
    }

   public void updateFeeds()
    {
		int numberOfFeeds = appWindow.channelsList.getModel().getSize();		

		if(numberOfFeeds > 0)
		{
        	for(int feedID = 0; feedID < numberOfFeeds; feedID++)
        	{
				//go through the feeds and delete the item
				channels.removeElementAt(0);
           	}

				System.out.println("\nThe number of feeds found = " + numberOfFeeds);


			for(int feedNumber = 0; feedNumber < numberOfFeeds; feedNumber++)
			{
				//go through the vector of urls and add the new feeds

				try
	            {				
	                SyndFeedInput input = new SyndFeedInput();
	                SyndFeed feed = input.build(new XmlReader(new URL(urlOfFeed.elementAt(feedNumber))));

	                ListFeed listFeed = new ListFeed(feed);

	                channels.add(listFeed);

	                appWindow.channelsList.setListData(channels);
	                appWindow.channelsList.setSelectedValue(listFeed, true);
	                appWindow.showItemDetails(0.6);
	            }
	            catch (MalformedURLException e)
	            {
	                appWindow.showIncorrectFeedURLDialog();
	            }
	            catch (FeedException e)
	            {
	                appWindow.showInvalidFeedDialog();
	            }
	            catch (IOException e)
	            {
	                appWindow.showInaccessibleFeedDialog();
	            }

				System.out.println("Feed " + feedNumber + " refreshed successfully");
			}
        }   
    }

}