package controller.estatisticas;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import model.Estatistica;
import model.Medicao;

public class CrescimentoPeriodo extends Estatistica {

	public CrescimentoPeriodo(String nome) {
		super(nome);
	}

	@Override
	public float valor() {
		ArrayList<Medicao> medicoes = new ArrayList<Medicao>(getObservacoes());
		Medicao primeira = medicoes.get(0);
		Medicao ultima = medicoes.get(medicoes.size() - 1);
		long dias = primeira.getMomento().until(ultima.getMomento(), ChronoUnit.DAYS);
		
		if (dias == 0) return 0;
		
		return ((float) ultima.getCasos() - primeira.getCasos())/dias;
	}

}
