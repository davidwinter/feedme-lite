package feedmelite.views;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.event.KeyEvent;

import java.net.URL;

import feedmelite.controllers.*;
import feedmelite.views.PreferencesWindow;

public class AppWindow extends JFrame
{
    
    /**
     * We want these public so we can control them via the controller.
     */
    public JList channelsList = new JList();
    public JSplitPane itemPane = new JSplitPane();
    public JTable feedItems = new JTable();
    public JEditorPane itemDetails = new JEditorPane();
    public JProgressBar refreshProgress = new JProgressBar();
    
    public JMenuItem toggleItemDetailsMenuItem = 
        new JMenuItem("Show Item Details");
    
    public AppWindow()
    {
        super("FeedMe Lite");
        
        /**
         * Frame properties
         */
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        /**
         * Menu Bar
         */
        JMenuBar menuBar = new JMenuBar();
        
        // File
        JMenu fileMenu = new JMenu("File");
        JMenuItem newFeedMenuItem = new JMenuItem("Add Feed");
        fileMenu.add(newFeedMenuItem);
        menuBar.add(fileMenu);
        
        // Feeds
        JMenu feedsMenu = new JMenu("Feeds");
        JMenuItem removeFeedMenuItem = new JMenuItem("Remove Selected Feed");
		JMenuItem refreshFeedsMenuItem = new JMenuItem("Refresh Feeds");
        feedsMenu.add(removeFeedMenuItem);
		feedsMenu.add(refreshFeedsMenuItem);
        menuBar.add(feedsMenu);
        
        // View
        JMenu viewMenu = new JMenu("View");
        viewMenu.add(toggleItemDetailsMenuItem);
        menuBar.add(viewMenu);
        
        
        
		//Preferences
		JMenu preferencesMenu = new JMenu("Settings");
		JMenuItem preferencesMenuItem = new JMenuItem("Preferences");
		preferencesMenu.add(preferencesMenuItem);
		menuBar.add(preferencesMenu);
		
        setJMenuBar(menuBar);
        
        /**
         * Keyboard Shortcuts
         */
        newFeedMenuItem.setAccelerator(
            KeyStroke.getKeyStroke(KeyEvent.VK_N,                                            
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
                  
        removeFeedMenuItem.setAccelerator(
            KeyStroke.getKeyStroke(KeyEvent.VK_D,                                            
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		
		refreshFeedsMenuItem.setAccelerator(
            KeyStroke.getKeyStroke(KeyEvent.VK_R,                                            
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));        

        toggleItemDetailsMenuItem.setAccelerator(
            KeyStroke.getKeyStroke(KeyEvent.VK_I,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		preferencesMenuItem.setAccelerator(
           	KeyStroke.getKeyStroke(KeyEvent.VK_P,                                            
	             Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        
        /** 
         * Split pane for items and item details.
         */
        itemDetails.setContentType("text/html");
        welcomeItemDetails();
         
        
        
        itemPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            new JScrollPane(feedItems),
            new JScrollPane(itemDetails));
        
        /**
         * Construct split pane for Channel + Items.
         */
        JSplitPane mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            new JScrollPane(channelsList), 
            itemPane);
        add(mainPane, BorderLayout.CENTER);
        
        /**
         * Construct lower button panel/toolbar for Add and progress bar.
         * Used for status on feed updates.
         */
        JPanel options = new JPanel();
        JButton addFeedButton = new JButton("Add Feed");
        options.add(addFeedButton);
        options.add(refreshProgress);
        add(options, BorderLayout.SOUTH);
        
        /**
         * Set the controller for the various components.
         */
        ToolBarController toolbarController = new ToolBarController(this);
        addFeedButton.addActionListener(toolbarController);
        
        ChannelListController channelController = 
            new ChannelListController(this);
        channelsList.addListSelectionListener(channelController);

		ItemController itemController = new ItemController(this);
		feedItems.addMouseListener(itemController);
        
        MenuController menuController = new MenuController(this);
        newFeedMenuItem.addActionListener(menuController);
        removeFeedMenuItem.addActionListener(menuController);
        toggleItemDetailsMenuItem.addActionListener(menuController);
		preferencesMenuItem.addActionListener(menuController);
		refreshFeedsMenuItem.addActionListener(menuController);
		
		TimerController timer = new TimerController(this);
        
        ItemDividerController itemDividerController = 
            new ItemDividerController(this);
       ((BasicSplitPaneUI)itemPane.getUI()).getDivider()
            .addComponentListener(itemDividerController);
        
        /**
         * Default GUI settings.
         */
        setVisible(true);
        channelsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mainPane.setDividerLocation(0.25);
        itemPane.setDividerLocation(1.0);
        refreshProgress.setIndeterminate(false);


    }
    
    public void showIncorrectFeedURLDialog()
    {
        JOptionPane.showMessageDialog(null, "The URL you entered does not appear to be in the correct format. \nThis is common when forgetting to include http:// at the start.", "Feed URL Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void showInvalidFeedDialog()
    {
        JOptionPane.showMessageDialog(null, "The feed you entered couldn't be read. \nThis normally happens if it doesn't validate.", "Feed Validation", JOptionPane.ERROR_MESSAGE);
    }
    
    public void showInaccessibleFeedDialog()
    {
        JOptionPane.showMessageDialog(null, "The feed you entered could not be accessed. \nPlease ensure you're connected to the Internet and that you entered the correct location of the feed.", "Feed Access", JOptionPane.ERROR_MESSAGE);
    }
    
    public String addFeedDialog()
    {
        String feedURL = (String) JOptionPane.showInputDialog(
            null,
            "Please enter the Feed URL",
            "Add new Feed",
            JOptionPane.PLAIN_MESSAGE, null, null, null);
        
        return feedURL;
    }
    
    public void showItemDetails(double position)
    {
        itemPane.setDividerLocation(position);
    }
    
    public void hideItemDetails()
    {
        itemPane.setDividerLocation(1.0);
    }
    
    public void hideFeedItems()
    {
        itemPane.setDividerLocation(0.0);
    }
    
    public void showPreferencesWindow()
    {
        PreferencesWindow settings = new PreferencesWindow(this, "Preferences", true);
		settings.setVisible(true);
    }
    
    public void welcomeItemDetails()
    {
        itemDetails.setText("<html><body style='font-family: \"Lucida Grande\", \"Lucida Sans Unicode\", verdana, geneva, helvetica, sans-serif; padding: 1px 20px;'><h1>FeedMe Lite</h1><p>Welcome to FeedMe Lite. Start adding some feeds by clicking the 'Add' button below!</p></body></html>");
    }
    
    public int confirmRemoveFeed()
    {
        return JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected feed?", "Confirm Feed Removal", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    }
}