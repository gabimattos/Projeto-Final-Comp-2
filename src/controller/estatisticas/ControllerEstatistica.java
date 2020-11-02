package controller.estatisticas;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.*;

public class ControllerEstatistica {
		public Map<String, Integer> rankingMaiorValor (List <Medicao> dadosTotal, LocalDateTime inicio, LocalDateTime fim){
			Map <String, Integer> rankingMaiorValor = new HashMap<>();
			montarEstatisticas (rankingMaiorValor, dadosTotal, inicio, fim);
		
			//organiza o ranking por maior valor em uma lista
			Map<String, Integer> rankingMaiorValorSorted = sortPorValor(rankingMaiorValor);
			
			return rankingMaiorValorSorted;
		}
		
		public Map<String, Integer> rankingMortalidade (List <Medicao> dadosMortes, List <Medicao> dadosCasos, LocalDateTime inicio, LocalDateTime fim){
			Map <String, Integer> rankingMortalidade = new HashMap<>();
			montarEstatisticas (rankingMortalidade, dadosMortes, inicio, fim);
			montarEstatisticas (rankingMortalidade, dadosCasos, inicio, fim);
			
			//organiza o ranking por maior valor em uma lista
			Map<String, Integer> rankingMortalidadeSorted = sortPorValor(rankingMortalidade);
			
			return rankingMortalidadeSorted;
		}
		
		public Map<String, Integer> rankingCrescimento (List <Medicao> dadosCasos, LocalDateTime inicio, LocalDateTime fim){
			Map <String, Integer> rankingCrescimento = new HashMap<>();
			montarEstatisticas (rankingCrescimento, dadosCasos, inicio, fim);
			
			//organiza o ranking por maior valor em uma lista
			Map<String, Integer> rankingCrescimentoSorted = sortPorValor(rankingCrescimento);
			
			return rankingCrescimentoSorted;
		}
		
		public double distance(double lat1, double lat2, double lon1, double lon2) {

			// converte raios para radianos
			lon1 = Math.toRadians(lon1);
			lon2 = Math.toRadians(lon2);
			lat1 = Math.toRadians(lat1);
			lat2 = Math.toRadians(lat2);

			// formula Haversine
			double dlon = lon2 - lon1;
			double dlat = lat2 - lat1;
			double a = Math.pow(Math.sin(dlat / 2), 2)
					+ Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);

			double c = 2 * Math.asin(Math.sqrt(a));

			// raio da terra em quilometros
			double r = 6371;

			// calcula o resultado
			return (c * r);
		}
		
		private void montarEstatisticas (Map <String, Integer> ranking, List <Medicao> dados, LocalDateTime inicio, LocalDateTime fim){
			for (int i = 0; i<dados.size(); i++) {
				//pega todas as medicoes de um pais nesse intervalo de tempo
				if (dados.get(i).getMomento().equals(inicio)) {
					TotalPeriodo estatistica = new TotalPeriodo(dados.get(i).getPais().getNome());
					estatistica.inclui(dados.get(i)); //inclui a data de inicio da medicao
					i++; //passa para a próxima data do país
					while (!dados.get(i).getMomento().equals(fim)) {
						estatistica.inclui(dados.get(i));
						i++;
					}
					estatistica.inclui(dados.get(i)); //inclui a data de fim da medicao	
					ranking.put(estatistica.getNome(), 
							(int) estatistica.valor()); //inclui a estatistica do pais no ranking
				}
			}
			
		}
		
		
		private  Map<String, Integer> sortPorValor(Map<String, Integer> unsortMap) {

	        //converte o map para uma list de maps
	        List<Map.Entry<String, Integer>> list =
	                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

	        //organiza a list, com um comparador especifico
	        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
	            public int compare(Map.Entry<String, Integer> o1,
	                               Map.Entry<String, Integer> o2) {
	                return (o1.getValue()).compareTo(o2.getValue());
	            }
	        });

	        //itera pela list organizada colocando os valores e chaves novo LinkedHashMap
	        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
	        for (Map.Entry<String, Integer> entry : list) {
	            sortedMap.put(entry.getKey(), entry.getValue());
	        }
	        return sortedMap;

	    }
	  
}
