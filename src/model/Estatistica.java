package model;

import java.time.LocalDate;
import java.util.*;

/**
 * Esta classe representa um pais e uma determinada informacao desse pais para
 * um determinado periodo de tempo.
 * Esta classe recebe uma lista de medicoes e um nome. o valor da estatistica e
 * calculada a partir das suas medicoes
 * 
 * @author Gabriel Rodrigues Cunha - 119.143.696
 *
 */
public abstract class Estatistica implements Comparable<Estatistica>{
	private String nome;
	private List<Medicao> observacoes;
	protected float valorCache;
	protected boolean cacheAtualizado;
	
	/**
	 * Inicia uma instancia de estatistica com o nome fornecido
	 * 
	 * @param nome	Nome da estatistica
	 */
	public Estatistica(String nome) {
		this.nome = nome;
		this.observacoes = new ArrayList<>();
		this.valorCache = 0;
		this.cacheAtualizado = false;
	}
	
	/**
	 * Adiciona uma medicao a estatistica.
	 * 
	 * @param observacao	medicao a ser incluida.
	 */
	public void inclui(Medicao observacao) {
		observacoes.add(observacao);
		Collections.sort(observacoes);
		cacheAtualizado = false;
	}
	
	/**
	 * Retorna a data da medicao mas antiga da estatistica.
	 * 
	 * @return	data mais antiga na estatistica.
	 */
	public LocalDate dataInicio() {
		return observacoes.get(0).getMomento().toLocalDate();
	}

	/**
	 * Retorna a data da medicao mas recente da estatistica.
	 * 
	 * @return	data mais recente na estatistica.
	 */
	public LocalDate dataFim() {
		return observacoes.get(observacoes.size()-1).getMomento().toLocalDate();
	}

	/**
	 * Funcao getter do atributo nome.
	 * 
	 * @return	nome da estatistica.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Funcao setter do atributo nome.
	 * 
	 * @param nome	novo nome da estatisca.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Funcao getter das medicoes contidas na estatistica.
	 * 
	 * @return	medicoes contidas na estatistica.
	 */
	public List<Medicao> getObservacoes() {
		return new ArrayList<>(observacoes);
	}
	
	/**
	 * Calcula e retorna o valor que esta estatica representa.
	 * 
	 * @return	valor que esta estatica representa.
	 */
	public abstract float valor();
	
	@Override
	public int compareTo(Estatistica o) {
		return Float.compare(o.valor(), this.valor());
	}
	
	/**
	 * Converte a estatistica para uma entrada TSV.
	 * 
	 * @return	entrda TSV que representa a estatisttica.
	 */
	public String toTSV() {
		return nome + "\t" + valor();
	}
}
