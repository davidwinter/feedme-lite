package feedmelite.controllers;

import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

import feedmelite.views.AppWindow;

/**
 * This class will only manage the item divider actions.
 * When moved, it needs to update the menu bar with the correct text.
 * Whether or not to show or hide the item details.
 */
public class ItemDividerController extends ActionController 
    implements ComponentListener
{
    public ItemDividerController(AppWindow appWindow)
    {
        super(appWindow);
    }
    
    public void componentMoved(ComponentEvent ce)
    {        
        double dividerProportion = 
            (double) appWindow.itemPane.getDividerLocation() / (double)
                ((double) appWindow.itemPane.getHeight() -
                    (double) appWindow.itemPane.getDividerSize());
        
        if (appWindow.channelsList.getModel().getSize() >= 1)
        {
            
        }
        else
        {
            appWindow.itemPane.setDividerLocation(0.0);
            // disable hide item details in menu bar
            appWindow.toggleItemDetailsMenuItem.setEnabled(false);
        }
        
        if (dividerProportion == 1.0)
        {
            appWindow.toggleItemDetailsMenuItem.setText("Show Item Details");
        }
        else if (dividerProportion < 1.0)
        {
            userPrefs.putDouble("item_divider_location", dividerProportion);
            appWindow.toggleItemDetailsMenuItem.setText("Hide Item Details");
        }  
    }
    
    public void componentHidden(ComponentEvent ce) { }
    public void componentResized(ComponentEvent ce) { }
    public void componentShown(ComponentEvent ce) { }
}