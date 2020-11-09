package controller;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.*;
import java.util.*;
import model.*;

/** Classe controller do historico de consultas.
 * Possui uma consulta atual que representa o estado atual do programa, em termos consulta ou entradas do usuario.
 * 
 * @author Raphael Mesquita &lt;raphafm.rf@gmail.com&gt;
 */
public class ConsultaController {
	/**
	 * Instancia unica de ConsultaController.
	 */
	private static final ConsultaController consultaController = new ConsultaController();
	
	private List<Consulta> consultas = new ArrayList<Consulta>();
	private int indexAtual;
	
	/**
	 * Metodo construtor. Carrega as consultas ja feitas e inicializa a consulta atual.
	 */
	private ConsultaController(){
		this.carregaConsultas();
		consultas.add(new Consulta());
		indexAtual = consultas.size() - 1;
	}
	
	/**
	 * Getter da instancia unica de ConsultaController no programa.
	 * @return A instancia unica de ConsultaController.
	 */
	public static ConsultaController getInstance() {
		return ConsultaController.consultaController;
	}
	
	
	/**
	 * Atualiza a consulta atual em termos do numero de casos, recuperados ou mortos.
	 * <p>
	 * 	Recebe o nome da JCheckBox e modifica seu boolean associado.
	 * </p>
	 * @param checkBoxName O nome da JCheckbox.
	 * @param stateChange O estado da JCheckBox associada (1 ou 2).
	 */
	public void atualizaNumeroDe(String checkBoxName, int stateChange) {
		Consulta consultaAtual = this.getConsultaAtual();
		boolean checked = stateChange == 1 ? true : false;
		
		switch(checkBoxName) {
			case "de casos":
				consultaAtual.alternateMapBoolean(consultaAtual.getNumeroDe(), StatusCaso.CONFIRMADOS, checked);
				break;
			case "de recuperados":
				consultaAtual.alternateMapBoolean(consultaAtual.getNumeroDe(), StatusCaso.RECUPERADOS, checked);
				break;
			case "de mortos":
				consultaAtual.alternateMapBoolean(consultaAtual.getNumeroDe(), StatusCaso.MORTOS, checked);
				break;
		}
	}
	
	/**
	 * Atualiza a consulta atual em termos do crecimento de casos, recuperados ou mortos.
	 * <p>
	 * 	Recebe o nome da JCheckBox e modifica seu boolean associado.
	 * </p>
	 * @param checkBoxName O nome da JCheckbox.
	 * @param stateChange O estado da JCheckBox associada (1 ou 2).
	 */
	public void atualizaCrescimentoDe(String checkBoxName, int stateChange) {
		Consulta consultaAtual = this.getConsultaAtual();
		boolean checked = stateChange == 1 ? true : false;
		switch(checkBoxName) {
			case "de casos":
				consultaAtual.alternateMapBoolean(consultaAtual.getCrescimentoDe(), StatusCaso.CONFIRMADOS, checked);
				break;
			case "de recuperados":
				consultaAtual.alternateMapBoolean(consultaAtual.getCrescimentoDe(), StatusCaso.RECUPERADOS, checked);
				break;
			case "de mortos":
				consultaAtual.alternateMapBoolean(consultaAtual.getCrescimentoDe(), StatusCaso.MORTOS, checked);
				break;
			}
	}
	
	/**
	 * Atualiza a consulta atual em termos de mortalidade.
	 * <p>
	 * 	Atribui o boolean associado a mortalidade na consulta atual.
	 * </p>
	 * @param stateChange O estado da JCheckBox associada (1 ou 2).
	 */
	public void atualizaMortalidade(int stateChange) {
		Consulta consultaAtual = this.getConsultaAtual();
		boolean checked = stateChange == 1 ? true : false;
		consultaAtual.setMortalidade(checked);
	}
	
	/**
	 * Atualiza a consulta atual em termos de locais mais proximos.
	 * <p>
	 * 	Atribui o boolean associado aos locais mais proximos na consulta atual.
	 * </p>
	 * @param stateChange O estado da JCheckBox associada (1 ou 2).
	 */
	public void atualizaLocaisMaisProximos(int stateChange) {
		Consulta consultaAtual = this.getConsultaAtual();
		boolean checked = stateChange == 1 ? true : false;
		consultaAtual.setLocaisMaisProximos(checked);
	}
	
	/**
	 * Atualiza a consulta atual em termos de periodo inicial ou final.
	 * <p>
	 * 	Recebe o nome da label associada ao periodo inicial ou final e a data. 
	 * 	Em seguida converte a data em <code>LocalDateTime</code> e atribui ao periodo associado a label.
	 * </p>
	 * @param labelPeriodo O nome da label associada ao periodo inicial ou final
	 * @param data A data do periodo.
	 */
	public void atualizaPeriodo(String labelPeriodo, String data) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate periodo = null;
		
		try {
			periodo = LocalDate.parse(data, formato);
		} catch (DateTimeParseException e) {
			 System.out.println("Data invalida");
		}
		
		Consulta consultaAtual = this.getConsultaAtual();
		
		switch(labelPeriodo) {
			case "Data Inicial":
				consultaAtual.setInicioPeriodo(periodo);
				break;
			case "Data Final":
				consultaAtual.setFimPeriodo(periodo);
				break;
		}
	}
	
	/**
	 * Carrega, a partir do arquivo "consultas.ser", todas as consultas salvas ate o momento.
	 */
	@SuppressWarnings("unchecked")
	public void carregaConsultas() {
		File pasta = new File("resources");
		if(!pasta.exists()) pasta.mkdirs();
		
		File arq = new File("resources"+File.separator+"consultas.ser");
		if(!arq.exists()) {
			try {
				arq.createNewFile();
			} catch (IOException e) {
				System.err.println("Nao foi possivel criar o arquivo consultas.ser");
			}
			return;
		}
		
		if(arq.length() <= 0) return;
		
		try(ObjectInputStream oos = new ObjectInputStream(new FileInputStream(arq))) {
			
			Object objectReaded = oos.readObject();
			this.setConsultas((List<Consulta>)objectReaded);
			
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo \"consultas.ser\" nao encontrado em resources/");
		} catch (IOException e) {
			System.err.println("Problema ao escrever arquivo. Verifique sua integridade.");
		} catch (ClassNotFoundException e) {
			System.err.println("Classe \"Consulta\" nao existente.");
		}
	}
	
	/**
	 * Guarda o historico de consultas realizadas no arquivo "consultas.ser".
	 */
	public void guardarConsultas() {
		File pasta = new File("resources");
		if(!pasta.exists()) pasta.mkdirs();
		
		File arq = new File("resources"+File.separator+"consultas.ser");
		if(!arq.exists()) {
			try {
				arq.createNewFile();
			} catch (IOException e) {
				System.err.println("Nao foi possivel criar o arquivo consultas.ser");
			}
			return;
		}
		
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("resources"+File.separator+"consultas.ser"))) {
			
			oos.writeObject(this.getConsultas());
			
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo \"consultas.ser\" nao encontrado em resources/");
		} catch (IOException e) {
			System.err.println("Problema ao escrever arquivo. Verifique sua integridade.");
		}
	}
	
	/**
	 * Cria uma String que mostra os campos da consulta indicada pelo index.
	 * @param index O indice da consulta a obter a String.
	 * @return A String que mostra os campos da consulta.
	 */
	public String consultaToString(int index) {
		Consulta consulta = consultas.get(index);
		
		String consultaString = "";
		
		consultaString += String.format("Inicio do periodo: %s, Fim do periodo: %s ", consulta.getInicioPeriodo().toString(), consulta.getFimPeriodo().toString());
		
		if(consulta.getNumeroDe().get(StatusCaso.CONFIRMADOS)) {
			consultaString += "Numero de casos, ";
		}
		if(consulta.getNumeroDe().get(StatusCaso.MORTOS)) {
			consultaString += "Numero de mortos, ";
		}
		if(consulta.getNumeroDe().get(StatusCaso.RECUPERADOS)) {
			consultaString += "Numero de recuperados, ";
		}
		
		if(consulta.getCrescimentoDe().get(StatusCaso.CONFIRMADOS)) {
			consultaString += "Crescimento de casos, ";
		}
		if(consulta.getCrescimentoDe().get(StatusCaso.MORTOS)) {
			consultaString += "Crescimento de mortos, ";
		}
		if(consulta.getCrescimentoDe().get(StatusCaso.RECUPERADOS)) {
			consultaString += "Crescimento de recuperados, ";
		}
		
		if(consulta.getMortalidade()) {
			consultaString += "Mortalidade, ";
		}
		if(consulta.getLocaisMaisProximos()) {
			consultaString += "Locais mais proximos, ";
		}
		
		return consultaString.substring(0, consultaString.length()-2);
	}
	
	/**
	 * Chama os metodos de rankings a partir da consulta relacionado ao indice passado pelo parametro.
	 * @param index O indice da consulta a se realizar.
	 * @return A lista de array bidimensional dos rankings.
	 */
	public List<String[][]> realizarConsulta(int index) {
		Consulta consulta = consultas.get(index);
		
		EstatisticaController estatisticas = EstatisticaController.getInstance();
		MedicaoController medicoes = MedicaoController.getInstance();
		
		ArrayList<Medicao> casos = new ArrayList<>(medicoes.getConfirmados());
		ArrayList<Medicao> mortos = new ArrayList<>(medicoes.getMortos());
		ArrayList<Medicao> recuperados = new ArrayList<>(medicoes.getRecuperados());
		
		LocalDateTime inicio = consulta.getInicioPeriodo().atStartOfDay();
		LocalDateTime fim = consulta.getFimPeriodo().atStartOfDay();
		
		List<String[][]> data = new ArrayList<>();
		
		if(consulta.getNumeroDe().get(StatusCaso.CONFIRMADOS)) {
			data.add(estatisticas.rankingToArray(estatisticas.rankingMaiorValor(casos, inicio, fim)));
		}
		if(consulta.getNumeroDe().get(StatusCaso.MORTOS)) {
			data.add(estatisticas.rankingToArray(estatisticas.rankingMaiorValor(mortos, inicio, fim)));
		}
		if(consulta.getNumeroDe().get(StatusCaso.RECUPERADOS)) {
			data.add(estatisticas.rankingToArray(estatisticas.rankingMaiorValor(recuperados, inicio, fim)));
		}
		
		if(consulta.getCrescimentoDe().get(StatusCaso.CONFIRMADOS)) {
			data.add(estatisticas.rankingToArray(estatisticas.rankingCrescimento(casos, inicio, fim)));
		}
		if(consulta.getCrescimentoDe().get(StatusCaso.MORTOS)) {
			data.add(estatisticas.rankingToArray(estatisticas.rankingCrescimento(mortos, inicio, fim)));
		}
		if(consulta.getCrescimentoDe().get(StatusCaso.RECUPERADOS)) {
			data.add(estatisticas.rankingToArray(estatisticas.rankingCrescimento(recuperados, inicio, fim)));
		}
		
		if(consulta.getMortalidade()) {
			data.add(estatisticas.rankingToArray(estatisticas.rankingMortalidade(mortos, casos, inicio, fim)));
		}
		if(consulta.getLocaisMaisProximos()) {
			data.add(estatisticas.rankingToArray(estatisticas.rankingLocaisProximos(casos, inicio, fim)));
		}

		return data;
	}
	
	/**
	 * Getter da consulta atual.
	 * @return A consulta atual.
	 */
	public Consulta getConsultaAtual() {
		return this.consultas.get(indexAtual);
	}
	
	/**
	 * Getter da lista de consultas ja feitas.
	 * @return A lista de consultas.
	 */
	public List<Consulta> getConsultas() {
		return this.consultas;
	}
	
	/**
	 * Setter da lista de consultas ja feitas.
	 * @param consultas A lista de consultas ja feitas.
	 */
	public void setConsultas(List<Consulta> consultas) {
		this.consultas = new ArrayList<Consulta>(consultas);
	}
	
	/**
	 * Getter do indice da consulta atual.
	 * @return O indice da consulta atual.
	 */
	public int getIndexAtual() {
		return this.indexAtual;
	}
}






