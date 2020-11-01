package model;

import java.time.LocalDate;
import java.util.*;

public abstract class Estatistica {
	private String nome;
	private List<Medicao> observacoes;
	
	public Estatistica(String nome, List<Medicao> observacoes) {
		this.nome = nome;
		this.observacoes = observacoes;
		Collections.sort(observacoes);
	}
	
	public boolean inclui(Medicao observacao) {
		return observacoes.contains(observacao);
	}
	
	public LocalDate dataInicio() {
		return observacoes.get(0).getMomento().toLocalDate();
	}
	
	public LocalDate dataFim() {
		return observacoes.get(observacoes.size()-1).getMomento().toLocalDate();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Medicao> getObservacoes() {
		return new ArrayList<>(observacoes);
	}
	
	public abstract float valor();
}
