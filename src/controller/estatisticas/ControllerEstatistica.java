package controller.estatisticas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.*;

public class ControllerEstatistica {
		public List<Estatistica> rankingMaiorValor (List <Medicao> dadosTotal, LocalDateTime inicio, LocalDateTime fim){
			List <Estatistica> rankingMaiorValor = new ArrayList<>();
			
			for (int i = 0; i<dadosTotal.size(); i++) {
				//pega todas as medicoes de um pais nesse intervalo de tempo
				if (dadosTotal.get(i).getMomento().equals(inicio)) {
					TotalPeriodo estatistica = new TotalPeriodo(dadosTotal.get(i).getPais().getNome());
					estatistica.inclui(dadosTotal.get(i)); //inclui a data de inicio da medicao
					while (dadosTotal.get(i).getPais().getNome().equals(estatistica.getNome()) 
							&& dadosTotal.get(i).getMomento().isBefore(fim)) {
						i++;

					}
					estatistica.inclui(dadosTotal.get(i-1));
					rankingMaiorValor.add(estatistica); //inclui a estatistica do pais no ranking
				}
			}
			Collections.sort(rankingMaiorValor);
			return rankingMaiorValor;
		}
		
		
		
		
		
		public List<Estatistica> rankingMortalidade (List <Medicao> dadosMortes, List <Medicao> dadosCasos, LocalDateTime inicio, LocalDateTime fim){
			List <Estatistica> rankingMortalidade = new ArrayList<>();

			for (int i = 0; i<dadosMortes.size(); i++) {
				//pega todas as medicoes de um pais nesse intervalo de tempo
				if (dadosMortes.get(i).getMomento().equals(inicio)) {
					MortalidadePeriodo estatistica = new MortalidadePeriodo(dadosMortes.get(i).getPais().getNome());
					estatistica.inclui(dadosMortes.get(i)); //inclui a data de inicio da medicao
					while (dadosMortes.get(i).getPais().getNome().equals(estatistica.getNome()) 
							&& dadosMortes.get(i).getMomento().isBefore(fim)) {
						i++;

					}
					estatistica.inclui(dadosMortes.get(i-1));
					rankingMortalidade.add(estatistica); //inclui a estatistica do pais no ranking
				}
			}
			for (int i = 0; i<dadosCasos.size(); i++) {
				//pega todas as medicoes de um pais nesse intervalo de tempo
				if (dadosCasos.get(i).getMomento().equals(inicio)) {
					MortalidadePeriodo estatistica = new MortalidadePeriodo(dadosCasos.get(i).getPais().getNome());
					estatistica.inclui(dadosCasos.get(i)); //inclui a data de inicio da medicao
					while (dadosCasos.get(i).getPais().getNome().equals(estatistica.getNome()) 
							&& dadosCasos.get(i).getMomento().isBefore(fim)) {
						i++;

					}
					estatistica.inclui(dadosCasos.get(i-1));
					rankingMortalidade.add(estatistica); //inclui a estatistica do pais no ranking
				}
			}
			Collections.sort(rankingMortalidade);
			return rankingMortalidade;
		}
		
		
		
		
		public List<Estatistica> rankingCrescimento (List <Medicao> dadosCasos, LocalDateTime inicio, LocalDateTime fim){
			List <Estatistica> rankingCrescimento = new ArrayList<>();
			
			for (int i = 0; i<dadosCasos.size(); i++) {
				//pega todas as medicoes de um pais nesse intervalo de tempo
				if (dadosCasos.get(i).getMomento().equals(inicio)) {
					CrescimentoPeriodo estatistica = new CrescimentoPeriodo(dadosCasos.get(i).getPais().getNome());
					estatistica.inclui(dadosCasos.get(i)); //inclui a data de inicio da medicao
					while (dadosCasos.get(i).getPais().getNome().equals(estatistica.getNome()) 
							&& dadosCasos.get(i).getMomento().isBefore(fim)) {
						i++;

					}
					estatistica.inclui(dadosCasos.get(i-1));
					rankingCrescimento.add(estatistica); //inclui a estatistica do pais no ranking
				}
			}
			Collections.sort(rankingCrescimento);
			return rankingCrescimento;
		}
		
	  
}
