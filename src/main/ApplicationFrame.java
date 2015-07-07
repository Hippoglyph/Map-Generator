package main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import Map.Map;

public class ApplicationFrame {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Map Generator test");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0, 0, (int)dim.getWidth(), (int)dim.getHeight());
		//frame.setLocationRelativeTo(null);
		frame.add(new Map());
		frame.setResizable(false);
		//frame.pack();
		frame.setVisible(true);
	}

}
