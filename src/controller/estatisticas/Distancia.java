package controller.estatisticas;

import model.Estatistica;
import model.Medicao;

/**
 * Esta estatistica recebe duas medicoes calcula a distancia entre seus
 * respectivos paises. A distancia calculada eh dada em Km.
 * 
 * @author Gabriel Rodrigues Cunha - 119.143.696
 *
 */
public class Distancia extends Estatistica {

	/**
	 * Cria uma nova Distancia com o nome fornecido.
	 * 
	 * @param nome	nome da Distancia;
	 */
	public Distancia(String nome) {
		super(nome);
	}
	
	@Override
	public void inclui(Medicao observacao) {
		if (this.getObservacoes().size() == 2)
			throw new AssertionError("Distancia nao pode possuir mais que 2 medicoes");
		super.inclui(observacao);
	}

	@Override
	public float valor() {
		if (cacheAtualizado) return valorCache;
		if (this.getObservacoes().size() < 2) return 0;
		
		int raio = 6371;
		double long1 = Math.toRadians(this.getObservacoes().get(0)
				.getPais().getLongitude());
		double long2 = Math.toRadians(this.getObservacoes().get(1)
				.getPais().getLongitude());
		double lat1 = Math.toRadians(this.getObservacoes().get(0)
				.getPais().getLatitude());
		double lat2 = Math.toRadians(this.getObservacoes().get(1)
				.getPais().getLatitude());
		
		double distLong = (long2 - long1)*raio;
		double distLat = (lat2 - lat1)*raio;
		
		
		double distancia = Math.sqrt(Math.pow(distLong,2) + Math.pow(distLat,2));
		
		valorCache = (float) distancia;
		cacheAtualizado = true;
		
		return (float) distancia;
	}
	
	@Override
	public int compareTo(Estatistica o) {
		return Float.compare(this.valor(), o.valor());
	}

}