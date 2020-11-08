package controller;

import java.net.http.HttpResponse;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.*;

import model.Pais;
import utils.APIConsumer;
import view.TelaInicial;

public class PaisController {
	private static final PaisController paisController = new PaisController();
	
	private List<Pais> paises;
	
	private PaisController() {
		this.carregaPaises();
	}
	
	public static PaisController getInstance() {
		return PaisController.paisController;
	}
	
	private List<String> getCountriesSlug(){
		ArrayList<String> countriesSlug = new ArrayList<>();
		
		HttpResponse<String> response = APIConsumer.httpGet("https://api.covid19api.com/countries");
		
		try {
			JSONArray countries = (JSONArray) new JSONParser().parse(response.body());
			for(Object country: countries) {
				String countrySlug = (String)((JSONObject)country).get("Slug");
				countriesSlug.add(countrySlug);
			}
		} catch (ParseException e) {
			System.err.println("Problema ao parsear resposta da api.");
		}
		return countriesSlug;
	}
	
	private void carregaPaises() {
		System.out.println("Baixando paises...");
		long inicio = new Date().getTime();
		
		List<String> countriesSlug = this.getCountriesSlug();
		List<Pais> paises = new ArrayList<>();
		
		for(String slug: countriesSlug) {			
			HttpResponse<String> countryStatusResponse = APIConsumer.httpGet(String.format("https://api.covid19api.com/country/%s/status/confirmed?from=2020-04-01T10:01:00Z&to=2020-04-02T00:00:00Z", slug));
			
			try {
				JSONArray resArray = (JSONArray) new JSONParser().parse(countryStatusResponse.body());
				if(resArray.size() > 0) {
					for(Object o: resArray) {
						JSONObject pais = (JSONObject)o;
						if(pais.get("Province").equals("")) {
							String country = (String)pais.get("Country");
							String countryCode = (String)pais.get("CountryCode");
							float lat = Float.parseFloat((String)pais.get("Lat"));
							float lon = Float.parseFloat((String)pais.get("Lon"));
							
							paises.add(new Pais(country, countryCode, slug, lat, lon));
							break;
						}
					}
				}
			} catch(ParseException e) {
				System.err.println("Problemas ao converter JSON em objeto.");
			}
			System.out.println(slug);
			
			int total = countriesSlug.size();
			int baixado = countriesSlug.indexOf(slug)+1;
			
			int porcentagem = (int) (baixado/((float)total)*100);
			TelaInicial.barra.updateBarPaises(baixado, total, porcentagem);
			System.out.printf("Progresso %d/%d(%d%%) de paises\n", baixado, total, porcentagem);

//			if (slug.equals("france")) {
//				break;
//			}
		}
		long fim = new Date().getTime();
		float duracao = (float)(fim - inicio)/1000.0f;
		
		System.out.printf("Paises baixados em %.2f segundos\n", duracao);
		this.setPaises(paises);
	}
	
	private void setPaises(List<Pais> paises) {
		this.paises = new ArrayList<Pais>(paises);
	}
	
	public List<Pais> getPaises(){
		return new ArrayList<Pais>(this.paises);
	}
	
}