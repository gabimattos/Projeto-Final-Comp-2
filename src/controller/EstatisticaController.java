package controller;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import controller.estatisticas.*;

import model.*;

public class EstatisticaController {
	private static final EstatisticaController estatisticaController =
			new EstatisticaController();
	
	private EstatisticaController() { };
	
	public List<Estatistica> rankingMaiorValor (List <Medicao> dadosTotal,
			LocalDateTime inicio, LocalDateTime fim){
		Collections.sort(dadosTotal);
		List <Estatistica> rankingMaiorValor = new ArrayList<>();
		Pais currPais = null;
		TotalPeriodo currEstatistica = null;
		
		for (int i = 0; i<dadosTotal.size(); i++) {
			//Atualiza qual pais est� sendo lido.
			if (dadosTotal.get(i).getPais() != currPais) {
				if (currEstatistica != null) rankingMaiorValor.add(currEstatistica);
				currPais = dadosTotal.get(i).getPais();
				currEstatistica = new TotalPeriodo(currPais.getNome());
			}
			//pega todas as medicoes de um pais nesse intervalo de tempo
			if (dadosTotal.get(i).getMomento().isAfter(inicio) && 
					dadosTotal.get(i).getMomento().isBefore(fim)) {
				currEstatistica.inclui(dadosTotal.get(i)); //inclui medi��es  dentro do periodo definido.
			}
		}
		Collections.sort(rankingMaiorValor);
		return rankingMaiorValor;
	}
	
	public static EstatisticaController getInstance() {
		return EstatisticaController.estatisticaController;
	}
	
	public List<Estatistica> rankingMortalidade (List <Medicao> dadosMortes,
			List <Medicao> dadosCasos, LocalDateTime inicio, LocalDateTime fim){
		
		List <Medicao> dados = new ArrayList<>(dadosCasos);
		dados.addAll(dadosMortes);
		Collections.sort(dados);
		List <Estatistica> rankingMortalidade = new ArrayList<>();
		Pais currPais = null;
		MortalidadePeriodo currEstatisticaMortalidade = null;

		for (int i = 0; i<dados.size(); i++) {
			//Atualiza qual pais está sendo lido.
			if (currPais == null || !dados.get(i).getPais().getSlug()
					.equals(currPais.getSlug())) {
				if (currEstatisticaMortalidade != null) rankingMortalidade.add(currEstatisticaMortalidade);
				currPais = dados.get(i).getPais();
				currEstatisticaMortalidade = new MortalidadePeriodo(currPais.getNome());
			}
			//pega todas as medicoes de um pais nesse intervalo de tempo
			if (dados.get(i).getMomento().isAfter(inicio) && 
					dados.get(i).getMomento().isBefore(fim)) {
				currEstatisticaMortalidade.inclui(dados.get(i)); //inclui medições  dentro do periodo definido.
			}
		}

		Collections.sort(rankingMortalidade);
		return rankingMortalidade;
	}
	
	public List<Estatistica> rankingCrescimento (List <Medicao> dadosCasos,
			LocalDateTime inicio, LocalDateTime fim){
		Collections.sort(dadosCasos);
		List <Estatistica> rankingCrescimento = new ArrayList<>();
		Pais currPais = null;
		CrescimentoPeriodo currEstatistica = null;
		
		for (int i = 0; i<dadosCasos.size(); i++) {
			//Atualiza qual pais est� sendo lido.
			if (dadosCasos.get(i).getPais() != currPais) {
				if (currEstatistica != null) rankingCrescimento.add(currEstatistica);
				currPais = dadosCasos.get(i).getPais();
				currEstatistica = new CrescimentoPeriodo(currPais.getNome());
			}
			//pega todas as medicoes de um pais nesse intervalo de tempo
			if (dadosCasos.get(i).getMomento().isAfter(inicio) && 
					dadosCasos.get(i).getMomento().isBefore(fim)) {
				currEstatistica.inclui(dadosCasos.get(i)); //inclui medi��es  dentro do periodo definido.
			}
		}
		Collections.sort(rankingCrescimento);
		return rankingCrescimento;
	}
	
  
	public List <Estatistica> rankingLocaisProximos (List <Medicao> dadosCasos,
			LocalDateTime inicio, LocalDateTime fim){
		List <Estatistica> rankingCrescimento = rankingCrescimento (dadosCasos, inicio, fim);
		Estatistica maiorCrescimento = rankingCrescimento.get(0);
		List <Estatistica> rankingLocaisProximos = new ArrayList<>();
		
		for (Estatistica e : rankingCrescimento) {
			Distancia currDist = new Distancia(e.getNome());
			currDist.inclui(maiorCrescimento.getObservacoes().get(0));
			currDist.inclui(e.getObservacoes().get(0));
			rankingLocaisProximos.add(currDist);
		}
		Collections.sort(rankingLocaisProximos);
		return rankingLocaisProximos;
	}
	
	public String[][] rankingToArray(List <Estatistica> ranking) {
		String[][] rankingArr = new String[ranking.size() + 1][2];
		boolean isInt = ranking.get(0) instanceof TotalPeriodo;
		boolean isPorcento = ranking.get(0) instanceof MortalidadePeriodo;
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
	
	private String[] getHeader(Estatistica exemplo) {
		String valor = null;
		if (exemplo instanceof TotalPeriodo) {
			valor = "Total de " + exemplo.getObservacoes().get(0).getStatus().getNome();
		} else if (exemplo instanceof CrescimentoPeriodo) {
			valor = "Crescimeto de " + exemplo.getObservacoes().get(0).getStatus().getNome() + 
					" (Novos casos por dia)";
		} else if(exemplo instanceof MortalidadePeriodo) {
			valor = "Taxa de mortalidade";
		} else {
			valor = "Distância do epicentro (Km)";
		}
		
		return new String[] {"Pais", valor};
	}
	
	public boolean toTSV(List<Estatistica> dados, String nome) {
		File pasta = new File("rankings");
		File arquivo = new File(pasta, nome + ".tsv");
		Collections.sort(dados);
		
		try {
			if (!pasta.exists()) pasta.mkdir();
			arquivo.createNewFile();
			PrintStream out = new PrintStream(arquivo);
			
			out.println("Pais\tValor");
			for (Estatistica dado : dados) {
				out.println(dado.toTSV());
			}
			out.close();
			
		} catch(IOException e) {
			return false;
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		EstatisticaController controler = EstatisticaController.getInstance();
		LocalDateTime inicio = LocalDateTime.parse("2020-01-01T00:00:01");
		LocalDateTime fim = LocalDateTime.parse("2021-01-02T23:59:59");
		MedicaoController cont = null;
		try {
			cont = MedicaoController.getInstance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		ArrayList<Medicao> casos = new ArrayList<>(cont.getConfirmados());
		ArrayList<Medicao> mortos = new ArrayList<>(cont.getMortos());
		ArrayList<Medicao> recuperados = new ArrayList<>(cont.getRecuperados());
		ArrayList<Estatistica> lista = new ArrayList<>(controler.rankingMaiorValor(recuperados, inicio, fim));
		ArrayList<Estatistica> lista2 = new ArrayList<>(controler.rankingCrescimento(mortos, inicio, fim));
		ArrayList<Estatistica> lista3 = new ArrayList<>(controler.rankingMortalidade(mortos, casos, inicio, fim));
		ArrayList<Estatistica> lista4 = new ArrayList<>(controler.rankingLocaisProximos(casos, inicio, fim));
		
		System.out.println(lista.size());
		for (Estatistica est : lista) {
			System.out.println(est.toTSV());
		}
		System.out.println(controler.toTSV(lista, "teste"));
		System.out.println(controler.toTSV(lista2, "teste2"));
		
		for (String[] linha : controler.rankingToArray(lista2)) {
			System.out.println(linha[0]+ "\t" + linha[1]);
		}
	}
	
}

