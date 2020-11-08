package controller.estatisticas;

import java.util.ArrayList;
import model.Estatistica;
import model.Medicao;

/**
 * Esta estatistica representa um aumento no numero de casos, em um determinado
 * periodo determinado pelas medicoes fornecidas.
 * 
 * @author Gabriel Rodrigues Cunha - 119.143.696
 *
 */
public class CrescimentoPeriodo extends Estatistica {

	/**
	 * Cria um novo CrescimentoPeriodo com o nome fornecido.
	 * 
	 * @param nome	nome do CrescimentoPeriodo;
	 */
	public CrescimentoPeriodo(String nome) {
		super(nome);
	}

	@Override
	public float valor() {
		if (cacheAtualizado) return valorCache;
		if (getObservacoes().size() == 0) return 0;
		
		ArrayList<Medicao> medicoes = new ArrayList<Medicao>(getObservacoes());
		float primeiro = (medicoes.get(0).getCasos() == 0)?
				1f:medicoes.get(0).getCasos();
		float ultimo = medicoes.get(medicoes.size() - 1).getCasos();
		
		
		Float valor = (ultimo - primeiro)/primeiro;
		
		this.valorCache = valor;
		this.cacheAtualizado = true;
		return valor;
	}

}