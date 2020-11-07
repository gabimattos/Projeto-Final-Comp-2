package model;

public enum StatusCaso {
	CONFIRMADOS("confirmados"),
	RECUPERADOS("recuperados"),
	MORTOS("mortos");
	
	private String nome;
	
	private StatusCaso(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}