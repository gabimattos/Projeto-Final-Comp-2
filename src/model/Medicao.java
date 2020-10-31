package model;

import java.time.LocalDateTime;

public class Medicao implements Comparable<Medicao>{
	private Pais pais;
	private LocalDateTime momento;
	private int casos;
	private StatusCaso status;
	
	public Medicao(Pais pais, LocalDateTime momento, int casos, StatusCaso status) {
		this.pais = pais;
		this.momento = momento;
		this.casos = casos;
		this.status = status;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public LocalDateTime getMomento() {
		return momento;
	}

	public void setMomento(LocalDateTime momento) {
		this.momento = momento;
	}

	public int getCasos() {
		return casos;
	}

	public void setCasos(int casos) {
		this.casos = casos;
	}

	public StatusCaso getStatus() {
		return status;
	}

	public void setStatus(StatusCaso status) {
		this.status = status;
	}

	@Override
	public int compareTo(Medicao o) {
		return this.getMomento().compareTo(o.getMomento());
	}
	
	
}
