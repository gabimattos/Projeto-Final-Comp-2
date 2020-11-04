package main;

import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.*;

public class Display{
	
	public void frame() {
		JFrame frame;
		JLabel label;
		JTextArea text;
		StringBuilder ranking = new StringBuilder();
		Map <String, Double> demo = new HashMap<>();
		demo.put("Arroz", 1.0);
		demo.put("Feijao", 2.0);
		demo.put("Sardinha", 3.0);
		
		frame = new JFrame("Display Ranking comida");
		text = new JTextArea();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(text);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setFont(new Font("Arial",Font.BOLD,20));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setVisible(true);
		
		for(Entry<String, Double> key : demo.entrySet()) {
			String temp = key.getKey()+": "+key.getValue().toString()+"\n";
			ranking.append(temp);
			
		}
		text.setText(ranking.toString());
		System.out.println(ranking);
	}
	
	public static void main(String[] args) {
		Display display = new Display();
		display.frame();
	}
}
