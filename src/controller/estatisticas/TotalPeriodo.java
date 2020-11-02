package controller.estatisticas;

import java.util.ArrayList;
import model.Estatistica;
import model.Medicao;

public class TotalPeriodo extends Estatistica {

	public TotalPeriodo(String nome) {
		super(nome);
	}
	
	@Override
	public float valor() {
		ArrayList<Medicao> medicoes = new ArrayList<Medicao>(getObservacoes());
		Medicao primeira = medicoes.get(0);
		Medicao ultima = medicoes.get(medicoes.size() - 1);
		
		return ultima.getCasos() - primeira.getCasos();
	}

}
