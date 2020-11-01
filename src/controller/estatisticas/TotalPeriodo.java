package controller.estatisticas;

import java.util.ArrayList;
import java.util.List;

import model.Estatistica;
import model.Medicao;

public class TotalPeriodo extends Estatistica {

	public TotalPeriodo(String nome, List<Medicao> observacoes) {
		super(nome, observacoes);
	}
	
	@Override
	public float valor() {
		ArrayList<Medicao> medicoes = new ArrayList<Medicao>(getObservacoes());
		Medicao primeira = medicoes.get(0);
		Medicao ultima = medicoes.get(medicoes.size() - 1);
		
		return ultima.getCasos() - primeira.getCasos();
	}

}
