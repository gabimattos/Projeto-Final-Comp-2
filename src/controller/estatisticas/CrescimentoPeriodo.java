package controller.estatisticas;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import model.Estatistica;
import model.Medicao;

public class CrescimentoPeriodo extends Estatistica {

	public CrescimentoPeriodo(String nome) {
		super(nome);
	}

	@Override
	public float valor() {
		if (cacheAtualizado) return valorCache;
		
		ArrayList<Medicao> medicoes = new ArrayList<Medicao>(getObservacoes());
		Medicao primeira = medicoes.get(0);
		Medicao ultima = medicoes.get(medicoes.size() - 1);
		long dias = primeira.getMomento().until(ultima.getMomento(), ChronoUnit.DAYS);
		
		Float valor = (dias == 0)? 0f :
				((float) ultima.getCasos() - primeira.getCasos())/dias;
		
		this.valorCache = valor;
		this.cacheAtualizado = true;
		return valor;
	}

}
