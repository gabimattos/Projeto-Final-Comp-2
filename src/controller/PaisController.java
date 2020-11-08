package controller;

import java.net.http.HttpResponse;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.*;

import model.Pais;
import utils.APIConsumer;
import view.TelaInicial;

/**
 * Classe que baixa, salva e carrega os dados dos paises.
 * @author Raphael Mesquita &lt;raphafm.rf@gmail.com&gt;
 *
 */
public class PaisController {
	/**
	 * Istancia unica da classe PaisController.
	 */
	private static final PaisController paisController = new PaisController();
	
	private List<Pais> paises;
	
	/**
	 * Metodo construtor. Carrega os dados dos paises.
	 */
	private PaisController() {
		this.carregaPaises();
	}
	
	/**
	 * Metodo que retorna a instancia unica da classe PaisController.
	 * @return A instancia unica da classe PaisController.
	 */
	public static PaisController getInstance() {
		return PaisController.paisController;
	}
	
	/**
	 * Metodo que obtem os slugs de todos os paises registrados na api COVID19 atraves da classe APIConsumer.
	 * @return A lista de String com os slugs de todos os paises da api.
	 */
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
	
	/**
	 *	Metodo que carrega os dados dos paises atraves da classe APIConsumer.
	 *<p>
	 *	Baixa as medicoes da api, sinalizando o progresso do download.
	 *</p>
	 */
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
			System.out.printf("Progresso %d/%d(%d%%) de paises\n", baixado, total, porcentagem);

			TelaInicial.barra.updateBarPaises(baixado, total, porcentagem);
		}
		long fim = new Date().getTime();
		float duracao = (float)(fim - inicio)/1000.0f;
		
		System.out.printf("Paises baixados em %.2f segundos\n", duracao);
		this.setPaises(paises);
	}
	
	/**
	 * Setter da lista de paises.
	 * @param paises A lista de paises a se definir.
	 */
	private void setPaises(List<Pais> paises) {
		this.paises = new ArrayList<Pais>(paises);
	}
	
	/**
	 * Getter da lista de paises.
	 * @return A lista de paises.
	 */
	public List<Pais> getPaises(){
		return new ArrayList<Pais>(this.paises);
	}
	
}