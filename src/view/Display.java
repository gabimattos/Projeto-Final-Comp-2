package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import controller.EstatisticaController;

public class Display {
	private JPanel topPanel;
	private JPanel btnPanel;
	JFrame frame = null;
	JTable table = null;
	JScrollPane scroll = null;
	FlowLayout layout = new FlowLayout();
	
	private JFrame janela;

	public Display(JFrame janela) {
		this.janela = janela;
	}
	
	public void display(String Titulo, String[][] newDados, String[] colunas, String dados[][]) {
		topPanel = new JPanel();
		btnPanel = new JPanel();
		frame = new JFrame();
		table = new JTable(newDados, colunas);
		scroll = new JScrollPane(table);

		frame.setLayout(layout);
		frame.setSize(800, 600);
		frame.setTitle(Titulo);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		table = centralizeText(table, colunas);
		table.setPreferredScrollableViewportSize(new Dimension(700, 400));
		table.setFillsViewportHeight(true);
		table.setEnabled(false);

		topPanel.setLayout(new BorderLayout());

		topPanel.add(scroll, BorderLayout.CENTER);
		frame.add(scroll);
		frame.getContentPane().add(topPanel, BorderLayout.CENTER);
		frame.getContentPane().add(btnPanel, BorderLayout.SOUTH);

		backButton();
		exportButton(dados, colunas);

		frame.setVisible(true);
	}

	private JTable centralizeText(JTable table, String[] header) {
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		for (String s : header) {
			table.getColumn(s).setCellRenderer(render);
		}

		return table;
	}

	public void backButton() {
		JButton back = new JButton("Voltar");
		btnPanel.add(back);

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				janela.setVisible(true);
				frame.setVisible(false);
			}
		});
	}

	public void exportButton(String dados[][], String[] colunas) {
		JButton export = new JButton("Exportar");
		btnPanel.add(export);

		export.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EstatisticaController.getInstance().toTSV(dados, colunas[1]);
			}
		});
	}
	
	public void trataDados(String dados[][]) {		
		String colunas[] = null;
		colunas = dados[0];
		
		String newDados[][] = new String[dados.length-1][2];
		
		for(int i = 1; i<dados.length; i++) {
			newDados[i-1] = dados[i];
		}
		
		display(colunas[1],newDados,colunas,dados);
	}
}