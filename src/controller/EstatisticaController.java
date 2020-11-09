package controller;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import controller.estatisticas.*;
import model.*;

/**
 * Classe controller das classes Estatistica.
 * <p>
 * Gera as listas que serao usadas na construcao dos
 * rankings.
 * 
 * @author Victoria Almeida - 118.140.336,
 * Gabriel Rodrigues Cunha - 119.143.696
 *
 */
public class EstatisticaController {
	private static final EstatisticaController estatisticaController =
			new EstatisticaController();
	
	private EstatisticaController() { };
	
	public static EstatisticaController getInstance() {
		return EstatisticaController.estatisticaController;
	}
	
	/**
	 * Metodo que recebe os datas da consulta e os dados de casos total
	 * da API (confirmados, mortos ou recuperados), itera pelos dados pegando 
	 * as medicoes do intervalo desejado, cria uma estatistica para cada pais 
	 * com sua respectivas medicoes e ordena essas estatistica em uma lista 
	 * de acordo com o numero de casos para se formar o ranking e retorna essa lista.
	 * 
	 * @param dadosTotal Dados do casos total da API.
	 * @param inicio Inicio das medicoes.
	 * @param fim Fim das medicoes.
	 * @return Lista de estatisticas de cada pais.
	 */
	public List<Estatistica> rankingMaiorValor (List <Medicao> dadosTotal,
			LocalDateTime inicio, LocalDateTime fim){
		Collections.sort(dadosTotal);
		List <Estatistica> rankingMaiorValor = new ArrayList<>();
		Pais currPais = null;
		TotalPeriodo currEstatistica = null;
		
		for (int i = 0; i<dadosTotal.size(); i++) {
			//Atualiza qual pais esta sendo lido.
			if (dadosTotal.get(i).getPais() != currPais) {
				if (currEstatistica != null) rankingMaiorValor.add(currEstatistica);
				currPais = dadosTotal.get(i).getPais();
				currEstatistica = new TotalPeriodo(currPais.getNome());
			}
			//pega todas as medicoes de um pais nesse intervalo de tempo
			if (!dadosTotal.get(i).getMomento().isBefore(inicio) && 
					!dadosTotal.get(i).getMomento().isAfter(fim)) {
				currEstatistica.inclui(dadosTotal.get(i)); //inclui medicoes  dentro do periodo definido.
			}
		}
		Collections.sort(rankingMaiorValor);
		return rankingMaiorValor;
	}
	
	/**
	 * Metodo que recebe os datas da consulta e os dados de mortes e casos total 
	 * (confirmados, mortos ou recuperados) da API, itera pelos dados pegando 
	 * as medicoes do intervalo desejado, cria uma estatistica para cada pais 
	 * com sua respectivas medicoes e ordena essas estatistica em uma lista 
	 * de acorde com o numero de mortalidades em rela��o aos casos para se formar 
	 * o ranking e retorna essa lista.
	 * 
	 * @param dadosMortes Dados do total de mortes da API.
	 * @param dadosTotal Dados do total de casos da API.
	 * @param inicio Inicio das medicoes.
	 * @param fim Fim das medicoes.
	 * @return Lista de estatisticas de cada pais.
	 */
	public List<Estatistica> rankingMortalidade (List <Medicao> dadosMortes,
			List <Medicao> dadosCasos, LocalDateTime inicio, LocalDateTime fim){
		
		List <Medicao> dados = new ArrayList<>(dadosCasos);
		dados.addAll(dadosMortes);
		Collections.sort(dados);
		List <Estatistica> rankingMortalidade = new ArrayList<>();
		Pais currPais = null;
		MortalidadePeriodo currEstatisticaMortalidade = null;

		for (int i = 0; i<dados.size(); i++) {
			//Atualiza qual pais esta sendo lido.
			if (currPais == null || !dados.get(i).getPais().getSlug()
					.equals(currPais.getSlug())) {
				if (currEstatisticaMortalidade != null) rankingMortalidade.add(currEstatisticaMortalidade);
				currPais = dados.get(i).getPais();
				currEstatisticaMortalidade = new MortalidadePeriodo(currPais.getNome());
			}
			//Pega todas as medicoes de um pais nesse intervalo de tempo
			if (!dados.get(i).getMomento().isBefore(inicio) && 
					!dados.get(i).getMomento().isAfter(fim)) {
				currEstatisticaMortalidade.inclui(dados.get(i)); //Inclui medicoes dentro do periodo definido.
			}
		}

		Collections.sort(rankingMortalidade);
		return rankingMortalidade;
	}
	
	/**
	 * Metodo que recebe os datas da consulta e os dados de casos total
	 * da API (confirmados, mortos ou recuperados), itera pelos dados pegando 
	 * as medicoes do intervalo desejado, cria uma estatistica para cada pais 
	 * com sua respectivas medicoes e ordena essas estatistica em uma lista 
	 * de acordo com o crescimento de casos para se formar o ranking e retorna
	 * essa lista.
	 * 
	 * @param dadosTotal Dados do casos total da API.
	 * @param inicio Inicio das medicoes.
	 * @param fim Fim das medicoes.
	 * @return Lista de estatisticas de cada pais.
	 */
	public List<Estatistica> rankingCrescimento (List <Medicao> dadosCasos,
			LocalDateTime inicio, LocalDateTime fim){
		Collections.sort(dadosCasos);
		List <Estatistica> rankingCrescimento = new ArrayList<>();
		Pais currPais = null;
		CrescimentoPeriodo currEstatistica = null;
		
		for (int i = 0; i<dadosCasos.size(); i++) {
			//Atualiza qual pais esta sendo lido.
			if (dadosCasos.get(i).getPais() != currPais) {
				if (currEstatistica != null) rankingCrescimento.add(currEstatistica);
				currPais = dadosCasos.get(i).getPais();
				currEstatistica = new CrescimentoPeriodo(currPais.getNome());
			}
			//pega todas as medicoes de um pais nesse intervalo de tempo
			if (!dadosCasos.get(i).getMomento().isBefore(inicio) && 
					!dadosCasos.get(i).getMomento().isAfter(fim)) {
				currEstatistica.inclui(dadosCasos.get(i)); //inclui medicoes dentro do periodo definido.
			}
		}
		Collections.sort(rankingCrescimento);
		return rankingCrescimento;
	}
	
	/**
	 * Metodo que recebe os datas da consulta e os dados de casos total
	 * da API (confirmados, mortos ou recuperados), descobre o pais com maior 
	 * crescimento de casos no intervalo desejado, itera pelos paises colocando-os
	 * em uma lista de estatisticas para cada pais com seus respectivos dados
	 * e ordena essas estatisticas de acordo com a distancia at� o pais com maior
	 * crescimento de casos para se formar o ranking e retorna essa lista. 
	 * 
	 * @param dadosTotal Dados do casos total da API.
	 * @param inicio Inicio das medicoes.
	 * @param fim Fim das medicoes.
	 * @return Lista de estatisticas de cada pais.
	 */
	public List <Estatistica> rankingLocaisProximos (List <Medicao> dadosCasos,
			LocalDateTime inicio, LocalDateTime fim){
		List <Estatistica> rankingCrescimento = rankingCrescimento (dadosCasos, inicio, fim);
		Estatistica maiorCrescimento = rankingCrescimento.get(0);
		List <Estatistica> rankingLocaisProximos = new ArrayList<>();
		
		if  (maiorCrescimento.getObservacoes().size() == 0)
			return new ArrayList<>();
		
		for (Estatistica e : rankingCrescimento) {
			Distancia currDist = new Distancia(e.getNome());
			currDist.inclui(maiorCrescimento.getObservacoes().get(0));
			currDist.inclui(e.getObservacoes().get(0));
			rankingLocaisProximos.add(currDist);
		}
		Collections.sort(rankingLocaisProximos);
		return rankingLocaisProximos;
	}
	
	/**
	 * Recebe um ranking (lista de Estatisticas) e cria um array bidimensional
	 * com os valores desse ranking. A primeira linha representa o Header do
	 * ranking e as demais representam as entradas.
	 * 
	 * @param ranking	Ranking a ser estatistica.
	 * @return	Ranking em um array bidimensional.
	 */
	public String[][] rankingToArray(List <Estatistica> ranking) {
		String[][] rankingArr = new String[ranking.size() + 1][2];
		boolean isInt = ranking.get(0) instanceof TotalPeriodo;
		boolean isPorcento = ranking.get(0) instanceof MortalidadePeriodo ||
				ranking.get(0) instanceof CrescimentoPeriodo;
		rankingArr[0] = getHeader(ranking.get(0));
		
		for (int i = 1; i < rankingArr.length; i++) {
			Estatistica curr = ranking.get(i - 1);
			
			String valor = "";
			if (isInt) valor = (int) curr.valor() + "";
			else if (isPorcento) valor = curr.valor()*100 + "%";
			else valor = curr.valor() + "";
			
			rankingArr[i] = new String[] {curr.getNome(), valor};
		}
		
		return rankingArr;
	}
	
	/**
	 * Recebe uma estatistica de um determinado ranking e, a partir
	 * dela descobre o header que ser� usado na montagem desse ranking,
	 * retornando esse header no formato de um array de String unidimensional,
	 * onde cada posi��o � o titulo de uma determinada coluna.
	 * 
	 * @param exemplo Estatistica que ser� analisada
	 * @return Header do ranking a ser montado.
	 */
	private String[] getHeader(Estatistica exemplo) {
		String valor = null;
		String tipo = (exemplo.getObservacoes().size() == 0)?
				"" : "de " + exemplo.getObservacoes().get(0).getStatus().getNome();
		
		if (exemplo instanceof TotalPeriodo) {
			valor = "Total " + tipo;
		} else if (exemplo instanceof CrescimentoPeriodo) {
			valor = "Taxa de crescimento " + tipo;
		} else if(exemplo instanceof MortalidadePeriodo) {
			valor = "Taxa de mortalidade";
		} else {
			valor = "Distancia do epicentro (Km)";
		}
		
		return new String[] {"Pais", valor};
	}
	
	/**
	 * Recebe um ranking (array bidimensional) e salva o seu conteudo em um
	 * arquivo TSV, cujo nome tambem deve ser fornecido. O arquivo gerado eh
	 * colocado na pasta rankings.
	 * 
	 * @param dados	Dados do ranking a serem convertidos em TSV.
	 * @param nome	Nome do arquivo a ser gerado
	 * @return	true se a operacao foi bem sucedida, caso contrario retorna false.
	 */
	public boolean toTSV(String[][] dados, String nome) {
		File pasta = new File("rankings");
		File arquivo = new File(pasta, nome + ".tsv");
		
		try {
			if (!pasta.exists()) pasta.mkdir();
			arquivo.createNewFile();
			PrintStream out = new PrintStream(arquivo);

			for (String[] dado : dados) {
				out.println(dado[0]+"\t"+dado[1]);
			}
			out.close();
			
		} catch(IOException e) {
			return false;
		}
		
		return true;
	}
	
}
