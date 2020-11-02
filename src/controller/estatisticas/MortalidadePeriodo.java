package controller.estatisticas;

import model.Estatistica;
import model.Medicao;
import model.StatusCaso;

public class MortalidadePeriodo extends Estatistica {

	public MortalidadePeriodo(String nome) {
		super(nome);
	}

	@Override
	public float valor() {
		if (cacheAtualizado) return valorCache;
		
		int inicioConfirmados = 0;
		int inicioMortos = 0;
		int fimConfirmados = 0;
		int fimMortos = 0;
		
		for (Medicao m : this.getObservacoes()) {
			if (m.getStatus() == StatusCaso.CONFIRMADOS) {
				if (m.getMomento().toLocalDate().equals(this.dataInicio()))
					inicioConfirmados = m.getCasos();
				if (m.getMomento().toLocalDate().equals(this.dataFim()))
					fimConfirmados = m.getCasos();
			}
			if (m.getStatus() == StatusCaso.MORTOS) {
				if (m.getMomento().toLocalDate().equals(this.dataInicio()))
					inicioMortos = m.getCasos();
				if (m.getMomento().toLocalDate().equals(this.dataFim()))
					fimMortos = m.getCasos();
			}
		}
		
		int totalMortos = fimMortos - inicioMortos;
		int totalCasos = fimConfirmados - inicioConfirmados;
		
		Float valor = (totalCasos == 0)? -1f : ((float) totalMortos)/totalCasos;
		
		this.valorCache = valor;
		this.cacheAtualizado = true;
		
		return valor;
	}
}
