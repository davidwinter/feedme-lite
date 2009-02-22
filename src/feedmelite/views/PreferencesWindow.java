package feedmelite.views;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.text.NumberFormatter;

import feedmelite.controllers.*;
import feedmelite.views.*;

public class PreferencesWindow extends Dialog
{
	//initialise the items needed on the preferences pane
   	private Button buttonApply = new Button( "Apply" );
	private JSlider refreshSlider= new JSlider( 0,0,60,10 ) ;  
	private Label titleLabel = new Label( "Settings For Feedmelite" );
	private Label refreshLabel = new Label( "Refresh Rate For RSS Feeds");
	static public JFormattedTextField refreshMins = new JFormattedTextField();
	
   	public PreferencesWindow(Frame parent, String caption, boolean bModal)
   	{
      	super(parent, caption, true);
      	setSize(515, 200);
        setLocationRelativeTo(null);
      	setResizable(false);

		PreferencesController settingsController = new PreferencesController();

		//add the action listeners to the buttons
      	buttonApply . addActionListener(settingsController);
   		
		//build the slider
		buildSlider();
		
		//set the layout for the preferences pane
		JPanel titlePane = new JPanel();
		titlePane.add(titleLabel, BorderLayout.NORTH);
		
      	JPanel buttons = new JPanel( );
      	buttons.add( buttonApply , BorderLayout.CENTER);
		
		JPanel refreshRate = new JPanel();
		refreshRate.add( refreshLabel, BorderLayout.NORTH);
		refreshRate.add( refreshSlider, BorderLayout.CENTER);
		
		JPanel preferencesPane = new JPanel();
		preferencesPane.add( refreshRate, BorderLayout.NORTH );
		preferencesPane.add( refreshMins, BorderLayout.CENTER );
		preferencesPane.add( buttons , BorderLayout.SOUTH);
		
	
      	setLayout(new BorderLayout());
		add(titlePane, BorderLayout.NORTH);
      	add(preferencesPane, BorderLayout.CENTER);
		
		preferencesPane.setBorder(new TitledBorder("Settings"));

      	addWindowListener(new WindowCatcher());
   	}
	
	public void buildSlider()
	{
		//set the properties for the slider and the text area
		
		java.text.NumberFormat numberFormat = java.text.NumberFormat.getIntegerInstance();
		NumberFormatter formatter = new NumberFormatter(numberFormat);
		formatter.setMinimum(new Integer(0));
		formatter.setMaximum(new Integer(60));
		refreshMins = new JFormattedTextField(formatter);
		refreshMins.setValue(new Integer(10));
		refreshMins.setColumns(5);
		refreshSlider.setMajorTickSpacing( 2 ) ;
		refreshSlider.setPaintTicks( true ) ;
		refreshSlider.setMajorTickSpacing(10);
		refreshSlider.setMinorTickSpacing(2);
		refreshSlider.setPaintLabels(true);
		refreshSlider.addChangeListener( new SliderListener())	;
	}
	
	static public void setTime(int time)
	{
		//use to set the time in the text area when the slider has been changed
		refreshMins.setText(String.valueOf(time)) ; 
	}
	
	static public int getTime()
	{
		//retrieve the current time in the text area
		int currentTime = Integer.parseInt(refreshMins.getText());
		return currentTime;
	}

}
	class SliderListener implements ChangeListener 
	{		
		//if the slider value is changed, get the new value from the slider and
		//call the setTime function to re-assign the current time value in the box
		
    	public void stateChanged(ChangeEvent e) 
		{
			int time = PreferencesWindow.getTime();
        	JSlider source = (JSlider)e.getSource();
        	if (!source.getValueIsAdjusting()) 
			{
            	time = (int)source.getValue();
			}
			PreferencesWindow.setTime(time);
		}
	}
	

   	class WindowCatcher extends WindowAdapter
   	{
    	public void windowClosing( WindowEvent evt ) 
      	{
        	( evt.getWindow()).dispose();
      	}
   	}