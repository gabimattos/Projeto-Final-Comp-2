package main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class Display {
	JFrame frame = null;
	JTable table = null;
	JScrollPane scroll = null;
	FlowLayout layout = new FlowLayout();
	public  void Display(String Titulo, String[][] dados, String[] colunas) {
		
		frame = new JFrame();
		table = new JTable(dados,colunas);
		frame.setLayout(layout);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setTitle(Titulo);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		table.setPreferredScrollableViewportSize(new Dimension(700,400));
		table.setFillsViewportHeight(true);
		table.setEnabled(false);
		scroll = new JScrollPane(table);
		frame.add(scroll);
		frame.setVisible(true);
		
		backButton();
		exportButton();
		
	}
	
	public void backButton() {
		JButton back = new JButton("Voltar");
		frame.add(back);
		layout.setAlignment(FlowLayout.LEFT);
		
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		
	
	}
		);
	}
	
	public void exportButton() {
		JButton export = new JButton("Exportar");
		frame.add(export);
		layout.setAlignment(FlowLayout.RIGHT);
		layout.setAlignment(FlowLayout.CENTER);
		export.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		
	
	}
		);
	}
	
	public static void main(String[] args) {
		String colunas[] = {"Países","Numero de mortos"};
		String dados[][] = {{"Estados unidos","22"},
				{"Brasil","32"},
				{"Inglaterra","25"}
		};
		
		Display display = new Display();
		display.Display("Ranking", dados, colunas);
	}
}
