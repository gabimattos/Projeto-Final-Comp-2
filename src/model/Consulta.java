package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.TreeMap;

/** Classe model de consulta. Guarda a entrada do usuario.
 * 	Cada campo boolean representa se o usuario deseja o ranking associado.
 * 
 * @author Raphael Mesquita - 118.020.104
 */
public class Consulta implements Serializable {
	private static final long serialVersionUID = 6523835329789813037L;
	
	private LocalDate inicioPeriodo;
	private LocalDate fimPeriodo;
	
	private TreeMap<StatusCaso, Boolean> numeroDe = new TreeMap<>();
	private TreeMap<StatusCaso, Boolean> crescimentoDe = new TreeMap<>();
	
	private boolean mortalidade;
	private boolean locaisMaisProximos;
	
	/**
	 * Metodo construtor. Inicializa os campos do objeto.
	 */
	public Consulta() {
		numeroDe.put(StatusCaso.CONFIRMADOS, false);
		numeroDe.put(StatusCaso.MORTOS, false);
		numeroDe.put(StatusCaso.RECUPERADOS, false);
		
		crescimentoDe.put(StatusCaso.CONFIRMADOS, false);
		crescimentoDe.put(StatusCaso.MORTOS, false);
		crescimentoDe.put(StatusCaso.RECUPERADOS, false);
	}

	/**
	 * Altera o boolean de um valor do mapa relacionado a uma chave, baseando-se no checked, passado por parametro.
	 * @param status O mapa a se alterar um par chave-valor.
	 * @param key A chave associada ao valor a se alterar.
	 * @param checked O boolean a se atribuir ao valor associado a chave.
	 */
	public void alternateMapBoolean(TreeMap<StatusCaso, Boolean> status, StatusCaso key, boolean checked) {
		status.put(key, checked);
	}
	
	/**
	 * Getter da data de inicio de periodo.
	 * @return A data de inicio de periodo.
	 */
	public LocalDate getInicioPeriodo() {
		return inicioPeriodo;
	}

	/**
	 * Setter da data de inicio de periodo.
	 * @param inicioPeriodo A data de inicio de periodo a se definir.
	 */
	public void setInicioPeriodo(LocalDate inicioPeriodo) {
		this.inicioPeriodo = inicioPeriodo;
	}

	/**
	 * Getter da data de fim de periodo.
	 * @return A data de fim de periodo.
	 */
	public LocalDate getFimPeriodo() {
		return fimPeriodo;
	}

	/**
	 * Setter da data de fim de periodo.
	 * @param fimPeriodo A data de fim de periodo a se definir.
	 */
	public void setFimPeriodo(LocalDate fimPeriodo) {
		this.fimPeriodo = fimPeriodo;
	}

	/**
	 * Getter do mapa (StatusCaso:Boolean) de numero de: casos/recuperados/mortos.
	 * @return O mapa de numero de: casos/recuperados/mortos.
	 */
	public TreeMap<StatusCaso, Boolean> getNumeroDe() {
		return numeroDe;
	}

	/**
	 * Setter do mapa (StatusCaso:Boolean) de numero de: casos/recuperados/mortos.
	 * @param numeroDe O mapa de numero de: casos/recuperados/mortos a se definir.
	 */
	public void setNumeroDe(TreeMap<StatusCaso, Boolean> numeroDe) {
		this.numeroDe = new TreeMap<StatusCaso, Boolean>(numeroDe);
	}

	/**
	 * Getter do mapa (StatusCaso:Boolean) de crescimento de: casos/recuperados/mortos.
	 * @return O mapa de crescimento de: casos/recuperados/mortos.
	 */
	public TreeMap<StatusCaso, Boolean> getCrescimentoDe() {
		return crescimentoDe;
	}

	/**
	 * Setter do mapa (StatusCaso:Boolean) de crescimento de: casos/recuperados/mortos.
	 * @param crescimentoDe O mapa de crescimento de: casos/recuperados/mortos a se definir.
	 */
	public void setCrescimentoDe(TreeMap<StatusCaso, Boolean> crescimentoDe) {
		this.crescimentoDe = new TreeMap<StatusCaso, Boolean>(crescimentoDe);
	}

	/**
	 * Getter do boolean associado a mortalidade.
	 * @return O boolean associado a mortalidade.
	 */
	public boolean getMortalidade() {
		return mortalidade;
	}

	/**
	 * Setter do boolean associado a mortalidade.
	 * @param mortalidade O boolean associado a mortalidade a se definir.
	 */
	public void setMortalidade(boolean mortalidade) {
		this.mortalidade = mortalidade;
	}

	/**
	 * Getter do boolean associado a locais mais proximos.
	 * @return O boolean associado a locais mais proximos.
	 */
	public boolean getLocaisMaisProximos() {
		return locaisMaisProximos;
	}

	/**
	 * Setter do boolean associado a locais mais proximos.
	 * @param locaisMaisProximos O boolean associado a locais mais proximos a se definir.
	 */
	public void setLocaisMaisProximos(boolean locaisMaisProximos) {
		this.locaisMaisProximos = locaisMaisProximos;
	}
}