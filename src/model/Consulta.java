package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.TreeMap;

public class Consulta implements Serializable {
	private static final long serialVersionUID = 6523835329789813037L;
	
	private LocalDate inicioPeriodo;
	private LocalDate fimPeriodo;
	
	private TreeMap<StatusCaso, Boolean> numeroDe = new TreeMap<>();
	private TreeMap<StatusCaso, Boolean> crescimentoDe = new TreeMap<>();
	
	private boolean mortalidade;
	private boolean locaisMaisProximos;
	
	public Consulta() {
		numeroDe.put(StatusCaso.CONFIRMADOS, false);
		numeroDe.put(StatusCaso.MORTOS, false);
		numeroDe.put(StatusCaso.RECUPERADOS, false);
		
		crescimentoDe.put(StatusCaso.CONFIRMADOS, false);
		crescimentoDe.put(StatusCaso.MORTOS, false);
		crescimentoDe.put(StatusCaso.RECUPERADOS, false);
	}

	public void alternateMapBoolean(TreeMap<StatusCaso, Boolean> status, StatusCaso key) {
		boolean b = status.get(key);
		status.put(key, !b);
	}
	
	public LocalDate getInicioPeriodo() {
		return inicioPeriodo;
	}

	public void setInicioPeriodo(LocalDate inicioPeriodo) {
		this.inicioPeriodo = inicioPeriodo;
	}

	public LocalDate getFimPeriodo() {
		return fimPeriodo;
	}

	public void setFimPeriodo(LocalDate fimPeriodo) {
		this.fimPeriodo = fimPeriodo;
	}

	public TreeMap<StatusCaso, Boolean> getNumeroDe() {
		return new TreeMap<StatusCaso, Boolean>(numeroDe);
	}

	public void setNumeroDe(TreeMap<StatusCaso, Boolean> numeroDe) {
		this.numeroDe = new TreeMap<StatusCaso, Boolean>(numeroDe);
	}

	public TreeMap<StatusCaso, Boolean> getCrescimentoDe() {
		return new TreeMap<StatusCaso, Boolean>(crescimentoDe);
	}

	public void setCrescimentoDe(TreeMap<StatusCaso, Boolean> crescimentoDe) {
		this.crescimentoDe = new TreeMap<StatusCaso, Boolean>(crescimentoDe);
	}

	public boolean getMortalidade() {
		return mortalidade;
	}

	public void setMortalidade(boolean mortalidade) {
		this.mortalidade = mortalidade;
	}

	public boolean getLocaisMaisProximos() {
		return locaisMaisProximos;
	}

	public void setLocaisMaisProximos(boolean locaisMaisProximos) {
		this.locaisMaisProximos = locaisMaisProximos;
	}
}
