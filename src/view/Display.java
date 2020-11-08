package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class Display {
	private JPanel topPanel;
	private JPanel btnPanel;
	JFrame frame = null;
	JTable table = null;
	JScrollPane scroll = null;
	FlowLayout layout = new FlowLayout();

	public void display(String Titulo, String[][] dados, String[] colunas) {
		topPanel = new JPanel();
		btnPanel = new JPanel();
		frame = new JFrame();
		table = new JTable(dados, colunas);
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
		exportButton();

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
			}
		});
	}

	public void exportButton() {
		JButton export = new JButton("Exportar");
		// frame.add(export);
		// layout.setAlignment(FlowLayout.RIGHT);
		// layout.setAlignment(FlowLayout.CENTER);
		btnPanel.add(export);

		export.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
	}

	public static void main(String[] args) {
		String colunas[] = { "Paises", "Numero de mortos" };
		String dados[][] = { { "Estados unidos", "22" }, { "Brasil", "32" }, { "Inglaterra", "25" } };

		Display display = new Display();
		display.display("Ranking", dados, colunas);
	}
}
