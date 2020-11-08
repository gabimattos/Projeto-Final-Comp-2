package controller;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import model.*;

/** Classe controller do histï¿½rico de consultas.
 * 
 * @author Raphael Mesquita &lt;raphafm.rf@gmail.com&gt;
 */
public class ConsultaController {
	private static final ConsultaController consultaController = new ConsultaController();
	
	private List<Consulta> consultas = new ArrayList<Consulta>();
	private int indexAtual;
	
	private ConsultaController(){
		this.carregaConsultas();
		consultas.add(new Consulta());
		indexAtual = consultas.size() - 1;
	}
	
	public static ConsultaController getInstance() {
		return ConsultaController.consultaController;
	}
	
	
	/**
	 * Atualiza a consulta atual em termos do nï¿½mero de casos, recuperados ou mortos.
	 * <p>
	 * 	Recebe o nome da JCheckBox e nega seu boolean associado.
	 * </p>
	 * @param checkBoxName O nome da JCheckbox.
	 */
	public void atualizaNumeroDe(String checkBoxName, int stateChange) {
		Consulta consultaAtual = this.consultas.get(indexAtual);
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
	 * 	Recebe o nome da JCheckBox e nega seu boolean associado.
	 * </p>
	 * @param checkBoxName O nome da JCheckbox.
	 */
	public void atualizaCrescimentoDe(String checkBoxName, int stateChange) {
		Consulta consultaAtual = this.consultas.get(indexAtual);
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
	 * 	Nega o boolean associado ï¿½ mortalidade na consulta atual.
	 * </p>
	 */
	public void atualizaMortalidade(int stateChange) {
		Consulta consultaAtual = this.consultas.get(indexAtual);
		boolean checked = stateChange == 1 ? true : false;
		consultaAtual.setMortalidade(checked);
	}
	
	/**
	 * Atualiza a consulta atual em termos de locais mais prï¿½ximos.
	 * <p>
	 * 	Nega o boolean associado aos locais mais prï¿½ximos na consulta atual.
	 * </p>
	 */
	public void atualizaLocaisMaisProximos(int stateChange) {
		Consulta consultaAtual = this.consultas.get(indexAtual);
		boolean checked = stateChange == 1 ? true : false;
		consultaAtual.setLocaisMaisProximos(checked);
	}
	
	/**
	 * Atualiza a consulta atual em termos de perï¿½odo inicial ou final.
	 * <p>
	 * 	Recebe o nome da label associada ao perï¿½odo inicial ou final e a data. 
	 * 	Em seguida converte a data em <code>LocalDateTime</code> e atribui ao perï¿½odo associado ï¿½ label.
	 * </p>
	 * @param labelPeriodo O nome da label associada ao perï¿½odo inicial ou final
	 * @param data A data do perï¿½odo.
	 */
	public void atualizaPeriodo(String labelPeriodo, String data) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate periodo = null;
		
		try {
			periodo = LocalDate.parse(data, formato);
		} catch (DateTimeParseException e) {
			 System.out.println("Data invÃ¡lida");
			 e.printStackTrace();
		}
		
		Consulta consultaAtual = this.consultas.get(indexAtual);
		
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
	 * Carrega, a partir do arquivo "consultas.ser", todas as consultas salvas atï¿½ o momento.
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
		
		try(ObjectInputStream oos = new ObjectInputStream(new FileInputStream(arq))) {
			this.setConsultas((List<Consulta>)oos.readObject());
			
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo \"consultas.ser\" nï¿½o encontrado em resources/");
		} catch (IOException e) {
			System.err.println("Problema ao escrever arquivo. Verifique sua integridade.");
		} catch (ClassNotFoundException e) {
			System.err.println("Classe \"Consulta\" nï¿½o existente.");
		}
	}
	
	/**
	 * Guarda o histï¿½rico de consultas realizadas no arquivo "consultas.ser".
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
			System.err.println("Arquivo \"consultas.ser\" nï¿½o encontrado em resources/");
		} catch (IOException e) {
			System.err.println("Problema ao escrever arquivo. Verifique sua integridade.");
		}
	}
	
	/**
	 * Cria uma String que mostra os campos da consulta indicada pela index.
	 * @param index O índice da consulta a obter a String.
	 * @return A String que mostra os campos da consulta.
	 */
	public String consultaToString(int index) {
		Consulta consulta = consultas.get(index);
		
		String consultaString = "";
		
		consultaString += String.format("Início do período: %s, Fim do período: %s", consulta.getInicioPeriodo().toString(), consulta.getFimPeriodo().toString());
		
		if(consulta.getNumeroDe().get(StatusCaso.CONFIRMADOS)) {
			consultaString += "Número de casos, ";
		}
		if(consulta.getNumeroDe().get(StatusCaso.MORTOS)) {
			consultaString += "Número de mortos, ";
		}
		if(consulta.getNumeroDe().get(StatusCaso.RECUPERADOS)) {
			consultaString += "Número de recuperados, ";
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
			consultaString += "Locais mais próximos, ";
		}
		
		return consultaString;
	}
	
	/**
	 * Chama os métodos de rankings a partir da consulta relacionado ao indice passado pelo parametro.
	 * @param index O indice da consulta a se realizar.
	 */
	public List<String[][]> realizarConsulta(int index) {
		Consulta consulta = consultas.get(index);
		
		EstatisticaController estatisticas = EstatisticaController.getInstance();
		MedicaoController medicoes = MedicaoController.getInstance();
		
		List<Medicao> casos = new ArrayList<>(medicoes.getConfirmados());
		List<Medicao> mortos = new ArrayList<>(medicoes.getMortos());
		List<Medicao> recuperados = new ArrayList<>(medicoes.getRecuperados());
		
		LocalDateTime inicio = consulta.getInicioPeriodo().atStartOfDay();
		LocalDateTime fim = consulta.getFimPeriodo().atStartOfDay();
		
		List<String[][]> data = new ArrayList<>();
		
		if(consulta.getNumeroDe().get(StatusCaso.CONFIRMADOS)) {
			estatisticas.rankingToArray(estatisticas.rankingMaiorValor(casos, inicio, fim));
		}
		if(consulta.getNumeroDe().get(StatusCaso.MORTOS)) {
			estatisticas.rankingToArray(estatisticas.rankingMaiorValor(mortos, inicio, fim));
		}
		if(consulta.getNumeroDe().get(StatusCaso.RECUPERADOS)) {
			estatisticas.rankingToArray(estatisticas.rankingMaiorValor(recuperados, inicio, fim));
		}
		
		if(consulta.getCrescimentoDe().get(StatusCaso.CONFIRMADOS)) {
			estatisticas.rankingToArray(estatisticas.rankingCrescimento(casos, inicio, fim));
		}
		if(consulta.getCrescimentoDe().get(StatusCaso.MORTOS)) {
			estatisticas.rankingToArray(estatisticas.rankingCrescimento(mortos, inicio, fim));
		}
		if(consulta.getCrescimentoDe().get(StatusCaso.RECUPERADOS)) {
			estatisticas.rankingToArray(estatisticas.rankingCrescimento(recuperados, inicio, fim));
		}
		
		if(consulta.getMortalidade()) {
			estatisticas.rankingToArray(estatisticas.rankingMortalidade(mortos, casos, inicio, fim));
		}
		if(consulta.getLocaisMaisProximos()) {
			estatisticas.rankingToArray(estatisticas.rankingLocaisProximos(casos, inicio, fim));
		}
		
		return data;
	}
	
	public Consulta getConsultaAtual() {
		return this.consultas.get(indexAtual);
	}
	
	public List<Consulta> getConsultas() {
		return new ArrayList<Consulta>(this.consultas);
	}
	
	public void setConsultas(List<Consulta> consultas) {
		this.consultas = new ArrayList<Consulta>(consultas);
	}
}






