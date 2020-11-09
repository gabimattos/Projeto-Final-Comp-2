package utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;

import view.TelaInicial;

/**
 * Classe que implementa o metodo GET para COVID19 API.
 * @author Raphael Mesquita &lt;raphafm.rf@gmail.com&gt;
 *
 */
public class APIConsumer {
	
	/**
	 * Executa uma requisicao GET na URI passada pelo parametro e recebe sua resposta.
	 * @param uri A URI de requisicao.
	 * @return A resposta da requisicao executada.
	 */
	public static HttpResponse<String> httpGet(String uri) {
		HttpClient client = HttpClient.newBuilder()
				.version(Version.HTTP_2)
				.followRedirects(Redirect.ALWAYS)
				.build();
		
		HttpRequest request = HttpRequest.newBuilder()
				 .uri(URI.create(uri))
				 .build();
		while(true) {
			try {
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
				int statusCode = response.statusCode();
				
				while(statusCode < 200 && statusCode >= 300) {
					response = client.send(request, HttpResponse.BodyHandlers.ofString());
					statusCode = response.statusCode();
				}
				
				return response;
			} catch(IOException e) {
				System.err.println("Problema ao enviar ou receber da requisicao. Tentando novamente.");
				TelaInicial.barra.barraProgresso.setNote("Problema ao enviar ou receber da requisicao. Tentando novamente.");
			} catch (InterruptedException e) {
				System.err.println("A operacao doi interrompida.");
			}
		}
	}
}