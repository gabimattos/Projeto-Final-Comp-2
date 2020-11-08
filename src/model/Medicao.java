package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Esta classe representa uma leitura dos dados de COVID-19, para um determinado
 * pais, em um determinado momento. Uma medicao pode representar total de mortos,
 * casos confirmados ou recuperados.
 *
 * @author Gabriel Rodrigues Cunha - 119.143.696
 *
 */
public class Medicao implements Comparable<Medicao>, Serializable{
	private static final long serialVersionUID = 1000L;
	private Pais pais;
	private LocalDateTime momento;
	private int casos;
	private StatusCaso status;
	
	/**
	 * Cria uma instancia de Medicao com os dados fornecidos.
	 * 
	 * @param pais	Pais da medicao.
	 * @param momento	Momento em que a medicao ocorreu.
	 * @param casos	Numero de casos registrado pela medicao.
	 * @param status	Tipo da medicao (mortos, casos confirmados ou recuperados).
	 */
	public Medicao(Pais pais, LocalDateTime momento, int casos, StatusCaso status) {
		this.pais = pais;
		this.momento = momento;
		this.casos = casos;
		this.status = status;
	}

	/**
	 * Funcao getter do pais da medicao.
	 * 
	 * @return	Pais da medicao.
	 */
	public Pais getPais() {
		return pais;
	}

	/**
	 * Funcao setter do pais da Medicao.
	 * 
	 * @param pais	Novo Pais da medicao.
	 */
	public void setPais(Pais pais) {
		this.pais = pais;
	}

	/**
	 * Funcao getter do momento da medicao.
	 * 
	 * @return	Momento da medicao.
	 */
	public LocalDateTime getMomento() {
		return momento;
	}

	/**
	 * Funcao setter do momento da Medicao.
	 * 
	 * @param momento	Novo momento da medicao.
	 */
	public void setMomento(LocalDateTime momento) {
		this.momento = momento;
	}

	/**
	 * Funcao getter do No. de casos da medicao.
	 * 
	 * @return	No. de casos da medicao.
	 */
	public int getCasos() {
		return casos;
	}

	/**
	 * Funcao setter do No. de casos da Medicao.
	 * 
	 * @param casos	Novo No. de casos da medicao.
	 */
	public void setCasos(int casos) {
		this.casos = casos;
	}

	/**
	 * Funcao getter do tipo da medicao.
	 * 
	 * @return	tipo da medicao (mortos, casos confirmados ou recuperados).
	 */
	public StatusCaso getStatus() {
		return status;
	}

	/**
	 * Funcao setter do tipo da Medicao.
	 * 
	 * @param status	Novo tipo da medicao mortos, casos confirmados ou recuperados).
	 */
	public void setStatus(StatusCaso status) {
		this.status = status;
	}

	@Override
	public int compareTo(Medicao o) {
		return  (pais.compareTo(o.pais) == 0)?
				this.getMomento().compareTo(o.getMomento()) :
				pais.compareTo(o.pais);
	}
	
	
}
