package feedmelite.controllers;


import java.awt.event.*;

import javax.swing.JOptionPane;

import feedmelite.views.AppWindow;


public class TimerController extends ActionController
{	
	//create a new timer object and set the initial refresh rate to be 10 mins

	public int minutes = 10; //the default refresh rate
	public javax.swing.Timer timer;
	
	public TimerController(AppWindow appWindow)
    {
        super(appWindow);
		startTimer();
    }

	//create the timer and designate the operations to carry out
	public void startTimer()
	{
		timer = new javax.swing.Timer(minutes * 60000, new ActionListener() 
		{
	    	public void actionPerformed(ActionEvent e) 
			{
	            updateFeeds();
				System.out.println("Timer Implemented - Reload Timer");
	        }
	    });
	
		timer.start();
		timer.setRepeats(true);
	}

	//stop the timer
	public void stopTimer()
	{
		timer.stop();
	}
	
	//update the timer with the new value obtained from the preferences window
	public int updateTimer(int mins)
	{
		timer.stop();
		System.out.println("Timer Stopped");
		minutes = mins;
		System.out.println("Timer updated to: " + minutes);
		startTimer();
		System.out.println("Timer Restarted");
		return 0;
	}

	
}