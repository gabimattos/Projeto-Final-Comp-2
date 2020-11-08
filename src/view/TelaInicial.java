package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.*;

import controller.ConsultaController;
import controller.MedicaoController;

import model.*;

/**
 * Essa classe gera uma interface de interacao com o usuario, onde ele pode
 * selecionar algumas opcoes e receber diferentes informacoes sobre a Covid19 em
 * todo o mundo. Entre as opcoes existentes estao Ranking internacional de
 * numero de (casos/recuperados/mortos), por periodo selecionado, Ranking
 * internacional de crescimento de (casos/recuperados/mortos), por periodo
 * selecionado, Ranking internacional de mortalidade, por periodo selecionado,
 * Locais mais proximos do local com maior crescimento de casos confirmados em
 * um periodo de tempo, ate um raio r (km). Alem disso o usuario tem a opcao de
 * apenas enviar suas opcoes ou de enviar e salvar suas opcoes.
 * 
 * @author Gabriela Mattos
 */
public class TelaInicial {

	public static BarraProgresso barra = new BarraProgresso();
	private ConsultaController consulta = ConsultaController.getInstance();

	private JFrame janela;
	private JFrame erro;

	private JButton btnEnviarRanking;
	private JButton btnEnviarSalvar;

	private JLabel lblRanking;
	private JLabel lblTopico1;
	private JLabel lblTopico2;
	private JLabel lblTopico3;
	private JLabel lblTopico4;
	private JLabel lblDataInicial;
	private JLabel lblDataFinal;
	private JLabel lblerror;

	private JTextField dataInicial;
	private JTextField dataFinal;

	public MedicaoController medicao;

	public static void main(String[] args) {
		new TelaInicial().initWindow();
	}

	/**
	 * Classe 'AcaoBotaoEnviarRanking()' Classe interna da classe 'TelaInicial()'
	 * responsavel pelo funcionamento do botao 'btnEnviarRanking'. Dentro dessa
	 * classe verifica-se se alguma opcao de consulta foi selecionada e se os campos
	 * das datas (dataInicial e dataFinal), por onde identifica-se o periodo em que
	 * se deseja obter aquela informacao (ranking), foram preenchidos. Caso nenhuma
	 * opcao seja selecionada ou um dos campos de data nao sejam preenchidos eh
	 * notificado um erro e a consulta nao eh feita. Caso alguma opcao seja
	 * selecionada e o campo das datas sejam preenchidos a consulta eh feita
	 * corretamente.
	 * 
	 * @author Gabriela Mattos
	 *
	 */
	public class AcaoBotaoEnviarRanking implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Consulta consultaAtual = consulta.getConsultaAtual();

			consulta.atualizaPeriodo("Data Inicial", dataInicial.getText());
			consulta.atualizaPeriodo("Data Final", dataFinal.getText());

			if (!consultaAtual.getNumeroDe().get(StatusCaso.CONFIRMADOS)
					&& !consultaAtual.getNumeroDe().get(StatusCaso.RECUPERADOS)
					&& !consultaAtual.getNumeroDe().get(StatusCaso.MORTOS)
					&& !consultaAtual.getCrescimentoDe().get(StatusCaso.CONFIRMADOS)
					&& !consultaAtual.getCrescimentoDe().get(StatusCaso.RECUPERADOS)
					&& !consultaAtual.getCrescimentoDe().get(StatusCaso.MORTOS) && !consultaAtual.getMortalidade()
					&& !consultaAtual.getLocaisMaisProximos()) {

				erro = new JFrame("ERRO");
				erro.setSize(450, 200);

				lblerror = new JLabel("ERRO!\n Nenhuma opcao selecionada!", JLabel.CENTER);
				erro.add(lblerror);
				erro.setVisible(true);
			} else if (consultaAtual.getInicioPeriodo() == null || consultaAtual.getFimPeriodo() == null) {
				erro = new JFrame("ERRO");
				erro.setSize(450, 200);

				lblerror = new JLabel("ERRO!\n Alguma data nao foi preenchida corretamente! (Ex.: 08/11/2020)",
						JLabel.CENTER);
				erro.add(lblerror);
				erro.setVisible(true);
			}

			else {
				// Chama o Display para cada ranking.
				List<String[][]> datas = consulta.realizarConsulta(consulta.getIndexAtual());

				for (String dados[][] : datas) {
					Display display = new Display(janela);
					display.trataDados(dados);
				}

				janela.setVisible(false);
			}
		}
	}

	/**
	 * Classe 'AcaoBotaoEnviarSalvar()'
	 * 
	 * Classe interna da classe 'TelaInicial()' responsavel pelo funcionamento do
	 * botao 'btnEnviarSalvar'. Dentro dessa classe verifica-se se alguma opcao de
	 * consulta foi selecionada e se os campos das datas (dataInicial e dataFinal),
	 * por onde identifica-se o periodo em que se deseja obter aquela informacao
	 * (ranking), foram preenchidos. Caso nenhuma opcao seja selecionada ou um dos
	 * campos de data nao sejam preenchidos eh notificado um erro e a consulta nao
	 * eh feita. Caso alguma opcao seja selecionada e o campo das datas sejam
	 * preenchidos a consulta eh feita corretamente e as opcoes selecionadas e as
	 * datas entradas (dataInicial e dataFinal) sao salvas.
	 * 
	 * @author Gabriela Mattos
	 *
	 */
	public class AcaoBotaoEnviarSalvar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Consulta consultaAtual = consulta.getConsultaAtual();

			consulta.atualizaPeriodo("Data Inicial", dataInicial.getText());
			consulta.atualizaPeriodo("Data Final", dataFinal.getText());

			if (!consultaAtual.getNumeroDe().get(StatusCaso.CONFIRMADOS)
					&& !consultaAtual.getNumeroDe().get(StatusCaso.RECUPERADOS)
					&& !consultaAtual.getNumeroDe().get(StatusCaso.MORTOS)
					&& !consultaAtual.getCrescimentoDe().get(StatusCaso.CONFIRMADOS)
					&& !consultaAtual.getCrescimentoDe().get(StatusCaso.RECUPERADOS)
					&& !consultaAtual.getCrescimentoDe().get(StatusCaso.MORTOS) && !consultaAtual.getMortalidade()
					&& !consultaAtual.getLocaisMaisProximos()) {

				erro = new JFrame("ERRO");
				erro.setSize(400, 200);

				lblerror = new JLabel("ERRO!\n Nenhuma opcao selecionada!");
				erro.add(lblerror);
				erro.setVisible(true);
			} else if (consultaAtual.getInicioPeriodo() == null || consultaAtual.getFimPeriodo() == null) {
				erro = new JFrame("ERRO");
				erro.setSize(400, 200);

				lblerror = new JLabel("ERRO!\n Alguma data nao foi preenchida!");
				erro.add(lblerror);
				erro.setVisible(true);
			}

			else {

				consulta.guardarConsultas();
				List<String[][]> datas = consulta.realizarConsulta(consulta.getIndexAtual());

				for (String dados[][] : datas) {
					Display display = new Display(janela);
					display.trataDados(dados);
				}
				janela.setVisible(false);
			}
		}
	}

	private void initWindow() {

		janela = new JFrame("Coronavirus no mundo.");
		janela.setSize(900, 600);
		janela.setResizable(false);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnEnviarRanking = new JButton("Enviar");
		btnEnviarSalvar = new JButton("Enviar e Salvar");

		lblRanking = new JLabel("Rankings Internacionais.");
		lblTopico1 = new JLabel("Numero");
		lblTopico2 = new JLabel("Crescimento");
		lblTopico3 = new JLabel("Mortalidade");
		lblTopico4 = new JLabel("Locais Proximos");
		lblDataInicial = new JLabel("Data Inicial");
		lblDataFinal = new JLabel("Data Final");

		dataInicial = new JTextField();
		dataFinal = new JTextField();

		final JCheckBox cbOpcao1 = new JCheckBox("de casos");
		final JCheckBox cbOpcao2 = new JCheckBox("de recuperados");
		final JCheckBox cbOpcao3 = new JCheckBox("de mortos");
		final JCheckBox cbOpcao4 = new JCheckBox("de casos");
		final JCheckBox cbOpcao5 = new JCheckBox("de recuperados");
		final JCheckBox cbOpcao6 = new JCheckBox("de mortos");
		final JCheckBox cbOpcao7 = new JCheckBox("taxa de mortalidade");
		final JCheckBox cbOpcao8 = new JCheckBox("locais proximos");

		cbOpcao1.setMnemonic(KeyEvent.VK_1);
		cbOpcao2.setMnemonic(KeyEvent.VK_2);
		cbOpcao3.setMnemonic(KeyEvent.VK_3);
		cbOpcao4.setMnemonic(KeyEvent.VK_4);
		cbOpcao5.setMnemonic(KeyEvent.VK_5);
		cbOpcao6.setMnemonic(KeyEvent.VK_6);
		cbOpcao7.setMnemonic(KeyEvent.VK_7);
		cbOpcao8.setMnemonic(KeyEvent.VK_8);

		cbOpcao1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				consulta.atualizaNumeroDe("de casos", e.getStateChange());

			}
		});
		cbOpcao2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				consulta.atualizaNumeroDe("de recuperados", e.getStateChange());

			}
		});
		cbOpcao3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				consulta.atualizaNumeroDe("de mortos", e.getStateChange());

			}
		});
		cbOpcao4.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				consulta.atualizaCrescimentoDe("de casos", e.getStateChange());

			}
		});
		cbOpcao5.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				consulta.atualizaCrescimentoDe("de recuperados", e.getStateChange());

			}
		});
		cbOpcao6.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				consulta.atualizaCrescimentoDe("de mortos", e.getStateChange());

			}
		});
		cbOpcao7.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				consulta.atualizaMortalidade(e.getStateChange());

			}
		});

		cbOpcao8.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				consulta.atualizaLocaisMaisProximos(e.getStateChange());

			}
		});

		JPanel conteinerRanking = new JPanel();
		JPanel jpRanking = new JPanel();
		JPanel jpOpcoes1 = new JPanel();
		JPanel jpOpcoes2 = new JPanel();
		JPanel jpOpcoes3 = new JPanel();
		JPanel jpOpcoes4 = new JPanel();
		JPanel jpDatas = new JPanel();
		JPanel jpBotaoRanking = new JPanel();

		conteinerRanking.setLayout(new GridLayout(0, 1, 0, 0));
		jpRanking.setLayout(new GridLayout(0, 1, 0, 0));
		jpOpcoes1.setLayout(new GridLayout(0, 3, 0, 1));
		jpOpcoes2.setLayout(new GridLayout(0, 3, 0, 2));
		jpOpcoes3.setLayout(new GridLayout(0, 2, 0, 0));
		jpOpcoes4.setLayout(new GridLayout(0, 2, 0, 0));
		jpDatas.setLayout(new GridLayout(0, 2, 0, 0));
		jpBotaoRanking.setLayout(new GridLayout(0, 2, 0, 0));

		jpRanking.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 30));
		jpBotaoRanking.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 30));
		conteinerRanking.setBorder(BorderFactory.createEtchedBorder());

		jpRanking.add(lblRanking);

		jpRanking.add(lblTopico1);
		jpOpcoes1.add(cbOpcao1);
		jpOpcoes1.add(cbOpcao2);
		jpOpcoes1.add(cbOpcao3);
		jpRanking.add(jpOpcoes1);

		jpRanking.add(lblTopico2);
		jpOpcoes2.add(cbOpcao4);
		jpOpcoes2.add(cbOpcao5);
		jpOpcoes2.add(cbOpcao6);
		jpRanking.add(jpOpcoes2);

		jpRanking.add(lblTopico3);
		jpOpcoes3.add(cbOpcao7);
		jpRanking.add(jpOpcoes3);

		jpRanking.add(lblTopico4);
		jpOpcoes4.add(cbOpcao8);
		jpRanking.add(jpOpcoes4);

		jpDatas.add(lblDataInicial);
		jpDatas.add(lblDataFinal);
		jpDatas.add(dataInicial);
		jpDatas.add(dataFinal);
		jpRanking.add(jpDatas);

		jpBotaoRanking.add(btnEnviarRanking);
		jpBotaoRanking.add(btnEnviarSalvar);
		jpRanking.add(jpBotaoRanking);

		conteinerRanking.add(jpRanking);

		btnEnviarRanking.addActionListener(new AcaoBotaoEnviarRanking());
		btnEnviarSalvar.addActionListener(new AcaoBotaoEnviarSalvar());

		janela.add(conteinerRanking, BorderLayout.WEST);

		janela.add(initHistorico(consulta.getConsultas()));

		this.medicao = MedicaoController.getInstance();

		janela.setVisible(true);
	}

	/**
	 * Metodo para mostrar o historico de consultas salvas. 
	 * Cria JList com os dados da lista de consultas salvas.
	 * Adiciona essa JList a um Panel junto com um botão para confirmar
	 * a consulta selecionada pelo usuário e retorna esse Panel.
	 *  
	 * @param elementos Lista de consultas salvas.
	 * @return Panel com os elemento para selecionar a consulta.
	 */
	private JPanel initHistorico(List<Consulta> elementos) {
		String elements[] = new String[elementos.size()];

		for (int i = 0; i < elementos.size(); i++) {
			if (i == consulta.getIndexAtual())
				continue;
			elements[i] = consulta.consultaToString(i);
		}

		final JList<String> list = new JList<>(elements);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		final JScrollPane pane = new JScrollPane(list);
		pane.setPreferredSize(new Dimension(400, 480));

		JPanel panel = new JPanel();

		final JButton button = new JButton("Pegar Ranking");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indicesSeleciodado[] = list.getSelectedIndices();

				List<String[][]> datas = consulta.realizarConsulta(indicesSeleciodado[0]);

				for (String dados[][] : datas) {
					Display display = new Display(janela);
					display.trataDados(dados);
				}
				janela.setVisible(false);
			}
		});
		button.setPreferredSize(new Dimension(150, 40));
		panel.add(new JLabel("Historico de consultas"));
		panel.add(pane, BorderLayout.CENTER);
		panel.add(button, BorderLayout.SOUTH);

		return panel;
	}
}
