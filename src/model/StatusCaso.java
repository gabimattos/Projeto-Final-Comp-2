package model;

/**
 * Este enum representa os diferentes tipos de medicoes de casos da COVID-19.
 *
 * @author Gabriel Rodrigues Cunha - 119.143.696
 *
 */
public enum StatusCaso {
	CONFIRMADOS("confirmados"),
	RECUPERADOS("recuperados"),
	MORTOS("mortos");
	
	private String nome;
	
	private StatusCaso(String nome) {
		this.nome = nome;
	}

	/**
	 * Rertorna o nome do StatusCaso.
	 * 
	 * @return nome do StatusCaso.
	 */
	public String getNome() {
		return nome;
	}
}