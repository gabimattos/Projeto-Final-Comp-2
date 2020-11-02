package utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;

/**
 * Classe que implementa o m�todo GET para COVID19 API.
 * @author Raphael Mesquita
 *
 */
public class APIConsumer {
	
	/**
	 * Executa uma requisi��o GET na URI passada pelo par�metro e recebe sua resposta.
	 * @param uri A URI de requisi��o.
	 * @return A resposta da requisi��o executada.
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
				System.err.println("Problema ao enviar ou receber da requisi��o. Tentando novamente.");
			} catch (InterruptedException e) {
				System.err.println("A opera��o doi interrompida.");
			}
		}
	}
}