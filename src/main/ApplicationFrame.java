package main;

import javax.swing.JFrame;

import Map.Map;

public class ApplicationFrame {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Map Generator test");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.add(new Map());
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

}
