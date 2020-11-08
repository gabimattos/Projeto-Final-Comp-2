package controller.estatisticas;

import java.util.ArrayList;
import model.Estatistica;
import model.Medicao;

/**
 * Esta estatistica representa o total de casos que ocorreram no periodo
 * determinado pelas medicoes fornecidas.
 *
 * @author Gabriel Rodrigues Cunha - 119.143.696
 *
 */
public class TotalPeriodo extends Estatistica {

	/**
	 * Cria um novo TotalPeriodo com o nome fornecido.
	 * 
	 * @param nome	nome do TotalPeriodo;
	 */
	public TotalPeriodo(String nome) {
		super(nome);
	}
	
	@Override
	public float valor() {
		if (getObservacoes().size() < 2) return 0;
		if (cacheAtualizado) return valorCache;
		
		ArrayList<Medicao> medicoes = new ArrayList<Medicao>(getObservacoes());
		Medicao primeira = medicoes.get(0);
		Medicao ultima = medicoes.get(medicoes.size() - 1);
		
		Float valor = (float) ultima.getCasos() - primeira.getCasos();
		
		this.valorCache = valor;
		this.cacheAtualizado = true;
		
		return valor;
	}

}
