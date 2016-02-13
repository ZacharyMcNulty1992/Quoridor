package main.java.client.gui;

import java.awt.BorderLayout;

import javax.swing.*;

public class Main {

	//the frame
	public static JFrame frame; //this is what all the component objects get added to 
	
	//Panels
	public static JPanel buttonPanel; //where we add buttons
	public static JPanel instPanel; //where the instructions go
	public static JPanel BoardPanel; //where the board will be desplayed
	public static JPanel inputPanel; //where the input will go
	
	//Buttons
	public static JButton SinglePlayerButton; //button for single player menu
	public static JButton multiplayerButton; //button for the multiplayer menu
	
	//labels
	public static JLabel instLabel;
	
	public static void main(String[] args) {
		//init the frame
		frame = new JFrame();

		//Makes the red X at the top actually kill the process
		//It does not do so by default. The process keeps running
		//without this.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//init the panels we need for the main menu
		buttonPanel = new JPanel();
		instPanel = new JPanel();
		BoardPanel = new JPanel();
		inputPanel = new JPanel();
		
		//init the buttons
		SinglePlayerButton = new JButton("Single Player");
		multiplayerButton = new JButton("Multiplayer");
		
		//allternately we can set text to a button using:
		//SinglePlayerButton.setText("text goes here")
		
		//init our label
		instLabel = new JLabel("Here's some instructions");
		
		//now we will populate the panels
		initialPopulationPanels();
		
		//now our panels for buttons and instructions are populated
		
		//now we add the panels to the frame in the arangment we want
		frame.setLayout(new BorderLayout()); //we add a layout manager to the frame
		frame.add(buttonPanel, BorderLayout.SOUTH); //add the button panel to the bottom of the GUI
		frame.add(instPanel, BorderLayout.CENTER); //add the instructions to the frame 
		
		frame.setVisible(true); //this makes the GUI visible 
		frame.pack(); // if we pack then our GUI components will be tightly packed together
	}
	
	public static void initialPopulationPanels(){
		
		//buttons first
		buttonPanel.setLayout(new BorderLayout()); //we set the layout
		buttonPanel.add(SinglePlayerButton, BorderLayout.WEST); //add a button to the left side of the panel
		buttonPanel.add(multiplayerButton, BorderLayout.EAST); //add a button to the right side of the panel
		
		//now we add the instructions to the instPanel
		instPanel.add(instLabel);
		
		//thats all for the initial setup of the panels
	}
	
	
}
