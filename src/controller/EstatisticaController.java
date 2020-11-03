package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.estatisticas.*;

import model.*;

public class EstatisticaController {
		public List<Estatistica> rankingMaiorValor (List <Medicao> dadosTotal,
				LocalDateTime inicio, LocalDateTime fim){
			Collections.sort(dadosTotal);
			List <Estatistica> rankingMaiorValor = new ArrayList<>();
			Pais currPais = null;
			TotalPeriodo currEstatistica = null;
			
			for (int i = 0; i<dadosTotal.size(); i++) {
				//Atualiza qual pais está sendo lido.
				if (dadosTotal.get(i).getPais() != currPais) {
					if (currEstatistica != null) rankingMaiorValor.add(currEstatistica);
					currPais = dadosTotal.get(i).getPais();
					currEstatistica = new TotalPeriodo(currPais.getNome());
				}
				//pega todas as medicoes de um pais nesse intervalo de tempo
				if (dadosTotal.get(i).getMomento().isAfter(inicio) && 
						dadosTotal.get(i).getMomento().isBefore(fim)) {
					currEstatistica.inclui(dadosTotal.get(i)); //inclui medições  dentro do periodo definido.
				}
			}
			Collections.sort(rankingMaiorValor);
			return rankingMaiorValor;
		}
		
		
		
		
		
		public List<Estatistica> rankingMortalidade (List <Medicao> dadosMortes,
				List <Medicao> dadosCasos, LocalDateTime inicio, LocalDateTime fim){
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
		
		
		
		
		public List<Estatistica> rankingCrescimento (List <Medicao> dadosCasos,
				LocalDateTime inicio, LocalDateTime fim){
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
		
	  
		/*public List <Estatistica> rankingLocaisProximos (List <Medicao> dadosCasos, LocalDateTime inicio, LocalDateTime fim){
			List <Estatistica> rankingCrescimento = rankingCrescimento (dadosCasos, inicio, fim);
			List <Estatistica> rankingLocaisProximos;
			
		}*/
		
		/**
		 * Recebe uma lista de string que ser� escrita no arquivo, o caminho do arquivo
		 * e escreve cada conte�do da lista em uma arquivo, pulando linha a cada nova posi��o.
		 * 
		 * @param data Dados que ser�o escritos no arquivo.
		 * @param path Caminho do arquivo.
		 * @throws IOException Exce��o de falha na escrita do arquivo.
		 */
		public void write(List <String> data, String path) throws IOException {
			
			FileWriter writer = new FileWriter(path); 
			for(String str: data) {
			  writer.write(str + System.lineSeparator());
			}
			writer.close();

		}
		
		public static void main(String[] args) {
			EstatisticaController controler = new EstatisticaController();
			LocalDateTime inicio = LocalDateTime.parse("2020-01-01T00:00:01");
			LocalDateTime fim = LocalDateTime.parse("2021-01-02T23:59:59");
			MedicaoController cont = null;
			try {
				cont = MedicaoController.getInstance();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			ArrayList<Medicao> casos = new ArrayList<>(cont.getConfirmados());
			ArrayList<Estatistica> lista = new ArrayList<>(controler.rankingMaiorValor(casos, inicio, fim));
			
			System.out.println(lista.size());
			for (Estatistica est : lista) {
				System.out.println(est.toTSV());
			}
		}
		
}
