package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import controller.EstatisticaController;
/**
 * <p>Esta classe gera a tela de ranking em forma de tabela e mostra pro usuario as seguintes opcoes:</p>
 * <p>Exportar o ranking que foi demonstrado ou voltar para o menu principal.</p>
 * @author Mariane Ferreira - 118.154.254
 * @author Victoria Almeida - 118.140.336
 *
 */
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
	/**
	 * <p>Este metodo faz o design da janela de ranking, cria a tabela e adiciona os botoes "exportar" e "voltar.</p>
	 * @param Titulo Titulo do ranking selecionado no menu principal.
	 * @param newDados Dados tratados pelo metodo trataDados.
	 * @param colunas Titulo das celulas.
	 * @param dados Dados nao tratados pelo metodo trataDados.
	 * 
	 * @see Display#trataDados(String[][])
	 */
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
/**
 * <p>Este metodo cria o botao de voltar e atribui sua funcao de voltar para o menu principal.</p>
 */
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
/**
 * <p>Este metodo cria o botao de exportar ranking e atribui a funcao de exportar o ranking atual para um arquivo tsv.</p>
 * @param dados Dados do ranking.
 * @param colunas Titulo das celulas.
 */
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
	/**
	 * <p>Este metodo trata os dados antes de serem mostrados no ranking. O metodo recebe um vetor bidimensional,</p>
	 * <p>o divide em um vetor unidimensional para ser usado como titulo das celulas e outro bidimensional para ser</p>
	 * <p>mostrado como os dados da tabela. Os dados devem ser tratados para serem mostrados corretamente.</p>
	 * @param dados Dados nao tratados
	 */
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