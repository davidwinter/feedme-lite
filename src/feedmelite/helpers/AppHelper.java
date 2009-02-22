package feedmelite.helpers;

import java.util.Iterator;
import java.util.Vector;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndEntry;

import feedmelite.helpers.NonEditableTableModel;

import feedmelite.models.ListFeed;

public abstract class AppHelper
{
    public static NonEditableTableModel channelToTable(ListFeed channel)
    {
        // this holds the row and column data
        Vector<Vector<String>> tableChannel = new Vector<Vector<String>>();
        
        // get the syndfeed from the listfeed object
        SyndFeed feed = channel.feed;
        
        // loop through each item in the channel
        Iterator entryIter = feed.getEntries().iterator();
        while (entryIter.hasNext())
        {
            SyndEntry entry = (SyndEntry) entryIter.next();
            Vector<String> itemVector = new Vector<String>();
            // add title
            itemVector.add(entry.getTitle());
            // add date
            itemVector.add(entry.getPublishedDate().toString());
            tableChannel.add(itemVector);
        }
        
        // column names
        Vector<String> columns = new Vector<String>();
        columns.add("Title");
        columns.add("Date");
        
        // create table model
        NonEditableTableModel tableItems = 
            new NonEditableTableModel(tableChannel, columns);
        
        return tableItems;
    }
}