package model;

import java.time.LocalDate;
import java.util.*;

public abstract class Estatistica implements Comparable<Estatistica>{
	private String nome;
	private List<Medicao> observacoes;
	protected float valorCache;
	protected boolean cacheAtualizado;
	
	public Estatistica(String nome) {
		this.nome = nome;
		this.observacoes = new ArrayList<>();
		this.valorCache = 0;
		this.cacheAtualizado = false;
	}
	
	public void inclui(Medicao observacao) {
		observacoes.add(observacao);
		Collections.sort(observacoes);
		cacheAtualizado = false;
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
	
	@Override
	public int compareTo(Estatistica o) {
		return Float.compare(this.valor(), o.valor());
	}
}