package feedmelite.models;

import com.sun.syndication.feed.synd.SyndFeed;

public class ListFeed
{
    public SyndFeed feed;
    
    public ListFeed(SyndFeed feed)
    {
        this.feed = feed;
    }
    
    public String toString()
    {
        return feed.getTitle();
    }
}