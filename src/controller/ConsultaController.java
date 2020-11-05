package controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import javax.swing.text.DateFormatter;

import model.*;

/** Classe controller do hist�rico de consultas.
 * 
 * @author Raphael Mesquita &lt;raphafm.rf@gmail.com&gt;
 */
public class ConsultaController {
	private static final ConsultaController consultaController = new ConsultaController();
	
	private List<Consulta> consultas;
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
	 * Atualiza a consulta atual em termos do n�mero de casos, recuperados ou mortos.
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
	 * 	Nega o boolean associado � mortalidade na consulta atual.
	 * </p>
	 */
	public void atualizaMortalidade(int stateChange) {
		Consulta consultaAtual = this.consultas.get(indexAtual);
		boolean checked = stateChange == 1 ? true : false;
		consultaAtual.setMortalidade(checked);
	}
	
	/**
	 * Atualiza a consulta atual em termos de locais mais pr�ximos.
	 * <p>
	 * 	Nega o boolean associado aos locais mais pr�ximos na consulta atual.
	 * </p>
	 */
	public void atualizaLocaisMaisProximos(int stateChange) {
		Consulta consultaAtual = this.consultas.get(indexAtual);
		boolean checked = stateChange == 1 ? true : false;
		consultaAtual.setLocaisMaisProximos(checked);
	}
	
	/**
	 * Atualiza a consulta atual em termos de per�odo inicial ou final.
	 * <p>
	 * 	Recebe o nome da label associada ao per�odo inicial ou final e a data. 
	 * 	Em seguida converte a data em <code>LocalDateTime</code> e atribui ao per�odo associado � label.
	 * </p>
	 * @param labelPeriodo O nome da label associada ao per�odo inicial ou final
	 * @param data A data do per�odo.
	 */
	public void atualizaPeriodo(String labelPeriodo, String data) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate periodo = null;
		
		try {
			periodo = LocalDate.parse(data, formato);
		} catch (DateTimeParseException e) {
			 System.out.println("Data inválida");
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
	 * Carrega, a partir do arquivo "consultas.ser", todas as consultas salvas at� o momento.
	 */
	@SuppressWarnings("unchecked")
	public void carregaConsultas() {
		try(ObjectInputStream oos = new ObjectInputStream(new FileInputStream("resources"+File.separator+"consultas.ser"))) {
			this.setConsultas((List<Consulta>)oos.readObject());
			
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo \"consultas.ser\" n�o encontrado em resources/");
		} catch (IOException e) {
			System.err.println("Problema ao escrever arquivo. Verifique sua integridade.");
		} catch (ClassNotFoundException e) {
			System.err.println("Classe \"Consulta\" n�o existente.");
		}
	}
	
	/**
	 * Guarda o hist�rico de consultas realizadas no arquivo "consultas.ser".
	 */
	public void guardarConsultas() {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("resources"+File.separator+"consultas.ser"))) {
			oos.writeObject(this.getConsultas());
			
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo \"consultas.ser\" n�o encontrado em resources/");
		} catch (IOException e) {
			System.err.println("Problema ao escrever arquivo. Verifique sua integridade.");
		}
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
