package edu.mines.nhnguyen.tnygren.pex3;

import javax.swing.*;
import java.awt.*;

public class Netflip extends JApplet {


	public void init() {
		// Execute a job on the event-dispatching thread; creating this applet's GUI.
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					JLabel lbl = new JLabel("Hello World");
					add(lbl);
				}
			});
		} catch (Exception e) {
			System.err.println("createGUI didn't complete successfully");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	

	private static final long serialVersionUID = 1L;
}
