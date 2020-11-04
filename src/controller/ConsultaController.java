package controller;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import model.*;

/** Classe controller do histórico de consultas.
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
	 * Atualiza a consulta atual em termos do número de casos, recuperados ou mortos.
	 * <p>
	 * 	Recebe o nome da JCheckBox e nega seu boolean associado.
	 * </p>
	 * @param checkBoxName O nome da JCheckbox.
	 */
	public void atualizaNumeroDe(String checkBoxName) {
		Consulta consultaAtual = this.consultas.get(indexAtual);
		switch(checkBoxName) {
			case "de casos":
				consultaAtual.alternateMapBoolean(consultaAtual.getNumeroDe(), StatusCaso.CONFIRMADOS);
				break;
			case "de recuperados":
				consultaAtual.alternateMapBoolean(consultaAtual.getNumeroDe(), StatusCaso.RECUPERADOS);
				break;
			case "de mortos":
				consultaAtual.alternateMapBoolean(consultaAtual.getNumeroDe(), StatusCaso.MORTOS);
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
	public void atualizaCrescimentoDe(String checkBoxName) {
		Consulta consultaAtual = this.consultas.get(indexAtual);
		switch(checkBoxName) {
			case "de casos":
				consultaAtual.alternateMapBoolean(consultaAtual.getCrescimentoDe(), StatusCaso.CONFIRMADOS);
				break;
			case "de recuperados":
				consultaAtual.alternateMapBoolean(consultaAtual.getCrescimentoDe(), StatusCaso.RECUPERADOS);
				break;
			case "de mortos":
				consultaAtual.alternateMapBoolean(consultaAtual.getCrescimentoDe(), StatusCaso.MORTOS);
				break;
			}
	}
	
	/**
	 * Atualiza a consulta atual em termos de mortalidade.
	 * <p>
	 * 	Nega o boolean associado à mortalidade na consulta atual.
	 * </p>
	 */
	public void atualizaMortalidade() {
		Consulta consultaAtual = this.consultas.get(indexAtual);
		
		consultaAtual.setMortalidade(!consultaAtual.getMortalidade());
	}
	
	/**
	 * Atualiza a consulta atual em termos de locais mais próximos.
	 * <p>
	 * 	Nega o boolean associado aos locais mais próximos na consulta atual.
	 * </p>
	 */
	public void atualizaLocaisMaisProximos() {
		Consulta consultaAtual = this.consultas.get(indexAtual);
		
		consultaAtual.setLocaisMaisProximos(!consultaAtual.getLocaisMaisProximos());
	}
	
	/**
	 * Atualiza a consulta atual em termos de período inicial ou final.
	 * <p>
	 * 	Recebe o nome da label associada ao período inicial ou final e a data. 
	 * 	Em seguida converte a data em <code>LocalDateTime</code> e atribui ao período associado à label.
	 * </p>
	 * @param labelPeriodo O nome da label associada ao período inicial ou final
	 * @param data A data do período.
	 */
	public void atualizaPeriodo(String labelPeriodo, String data) {
		Consulta consultaAtual = this.consultas.get(indexAtual);
		
		LocalDateTime periodo = LocalDateTime.parse(data);
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
	 * Carrega, a partir do arquivo "consultas.ser", todas as consultas salvas até o momento.
	 */
	@SuppressWarnings("unchecked")
	public void carregaConsultas() {
		try(ObjectInputStream oos = new ObjectInputStream(new FileInputStream("resources"+File.separator+"consultas.ser"))) {
			this.setConsultas((List<Consulta>)oos.readObject());
			
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo \"consultas.ser\" não encontrado em resources/");
		} catch (IOException e) {
			System.err.println("Problema ao escrever arquivo. Verifique sua integridade.");
		} catch (ClassNotFoundException e) {
			System.err.println("Classe \"Consulta\" não existente.");
		}
	}
	
	/**
	 * Guarda o histórico de consultas realizadas no arquivo "consultas.ser".
	 */
	public void guardarConsultas() {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("resources"+File.separator+"consultas.ser"))) {
			oos.writeObject(this.getConsultas());
			
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo \"consultas.ser\" não encontrado em resources/");
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
