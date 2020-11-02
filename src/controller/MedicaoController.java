package controller;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.*;

import model.*;
import utils.APIConsumer;

public class MedicaoController {
	private static final MedicaoController medicaoController = new MedicaoController();
	
	private List<Medicao> confirmados;
	private List<Medicao> mortos;
	private List<Medicao> recuperados;
	
	private MedicaoController() {
		this.carregaMedicoes();
	}
	
	public static MedicaoController getInstance() {
		return MedicaoController.medicaoController;
	}
	
	private void carregaMedicoes() {
		List<Pais> paises = PaisController.getInstance().getPaises();
		
		System.out.println("Baixando medições dos países...");
		long inicio = new Date().getTime();
		
		List<Medicao> confirmados = new ArrayList<>();
		List<Medicao> mortos = new ArrayList<>();
		List<Medicao> recuperados = new ArrayList<>();
		
		for(Pais pais: paises) {
			HttpResponse<String> response = APIConsumer.httpGet(String.format("https://api.covid19api.com/total/country/%s", pais.getSlug()));
			
			try {
				JSONArray resArray = (JSONArray) new JSONParser().parse(response.body());
				
				for(Object o: resArray) {
					JSONObject medicao = (JSONObject)o;
					
					LocalDateTime momento = LocalDateTime.parse(((String)medicao.get("Date")).replace("Z", ""));
					confirmados.add(new Medicao(pais, momento, (int)((long)medicao.get("Confirmed")), StatusCaso.CONFIRMADOS));
					mortos.add(new Medicao(pais, momento, (int)((long)medicao.get("Deaths")), StatusCaso.MORTOS));
					recuperados.add(new Medicao(pais, momento, (int)((long)medicao.get("Recovered")), StatusCaso.RECUPERADOS));
					
					
				}
			} catch (ParseException e) {
				System.err.println("Problemas ao converter JSON em objeto.");
			}
			System.out.println(pais.getSlug());
			int total = paises.size();
			int baixado = paises.indexOf(pais)+1;
			
			int porcentagem = (int) (baixado/((float)total)*100);
			System.out.printf("Progresso %d/%d(%d%%) de medições de países.\n", baixado, total, porcentagem);
		}
		
		this.setConfirmados(confirmados);
		this.setMortos(mortos);
		this.setRecuperados(recuperados);
		
		long fim = new Date().getTime();
		float duracao = (float)(fim - inicio)/1000.0f;
		
		System.out.printf("Medições dos países baixados em %.2f segundos\n", duracao);
	}

	public List<Medicao> getConfirmados() {
		return new ArrayList<Medicao>(this.confirmados);
	}

	public void setConfirmados(List<Medicao> confirmados) {
		this.confirmados = new ArrayList<Medicao>(confirmados);
	}
	
	public List<Medicao> getMortos() {
		return new ArrayList<Medicao>(this.mortos);
	}
	
	public void setMortos(List<Medicao> mortos) {
		this.mortos = new ArrayList<Medicao>(mortos);
	}
	
	public List<Medicao> getRecuperados() {
		return new ArrayList<Medicao>(this.recuperados);
	}
	
	public void setRecuperados(List<Medicao> recuperados) {
		this.recuperados = new ArrayList<Medicao>(recuperados);
	}
	
	
	
}