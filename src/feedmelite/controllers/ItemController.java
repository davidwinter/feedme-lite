package feedmelite.controllers;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Point;

import java.net.URL;

import feedmelite.views.AppWindow;

import feedmelite.models.ListFeed;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * This class will only manage the Item table mouse actions.
 */
public class ItemController extends ActionController 
    implements MouseListener
{
	
    public ItemController(AppWindow appWindow)
    {
        super(appWindow);
    }

    public void mouseClicked(MouseEvent e)
    {
        Point p = e.getPoint();
        
		int row = appWindow.feedItems.rowAtPoint(p);
        int column = appWindow.feedItems.columnAtPoint(p);
		
		ListFeed channel = (ListFeed) appWindow.channelsList.getSelectedValue();
		SyndFeed feed = channel.feed;
		SyndEntry item = (SyndEntry) feed.getEntries().get(row);
		
		if (e.getClickCount() == 2)
		{
			// open in browser window
		}
		else if (e.getClickCount() == 1)
		{
			appWindow.itemDetails.setText(item.getDescription().getValue());
			
		}
	}
	
	public void mouseExited(MouseEvent e) { }
 	public void mouseEntered(MouseEvent e) { }
 	public void mousePressed(MouseEvent e) { }
 	public void mouseReleased(MouseEvent e) { }

}