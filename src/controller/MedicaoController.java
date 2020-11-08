package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.http.HttpResponse;
import java.time.LocalDate;
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
		File pasta = new File("cache/medicoes");
		if(!pasta.exists()) pasta.mkdirs();
		
		File confirmadosFile = new File("cache/medicoes/" + LocalDate.now() + " - confirmados.ser");
		File mortosFile = new File("cache/medicoes/" + LocalDate.now() + " - mortos.ser");
		File recuperadosFile = new File("cache/medicoes/" + LocalDate.now() + " - recuperados.ser");
		

		if (confirmadosFile.isFile() && mortosFile.isFile() &&  recuperadosFile.isFile()) {
			System.out.println("Carregando dados j� baixados.");
			this.setConfirmados(deserialize(confirmadosFile));
			this.setMortos(deserialize(mortosFile));
			this.setRecuperados(deserialize(recuperadosFile));
		}

		else {
			List<Pais> paises = PaisController.getInstance().getPaises();

			System.out.println("Baixando medi��es dos pa�ses...");
			long inicio = new Date().getTime();

			List<Medicao> confirmados = new ArrayList<>();
			List<Medicao> mortos = new ArrayList<>();
			List<Medicao> recuperados = new ArrayList<>();

			for (Pais pais : paises) {
				HttpResponse<String> response = APIConsumer
						.httpGet(String.format("https://api.covid19api.com/total/country/%s", pais.getSlug()));

				try {
					JSONArray resArray = (JSONArray) new JSONParser().parse(response.body());

					for (Object o : resArray) {
						JSONObject medicao = (JSONObject) o;

						LocalDateTime momento = LocalDateTime.parse(((String) medicao.get("Date")).replace("Z", ""));
						confirmados.add(new Medicao(pais, momento, (int) ((long) medicao.get("Confirmed")),
								StatusCaso.CONFIRMADOS));
						mortos.add(new Medicao(pais, momento, (int) ((long) medicao.get("Deaths")), StatusCaso.MORTOS));
						recuperados.add(new Medicao(pais, momento, (int) ((long) medicao.get("Recovered")),
								StatusCaso.RECUPERADOS));

					}
				} catch (ParseException e) {
					System.err.println("Problemas ao converter JSON em objeto.");
				}
				System.out.println(pais.getSlug());
				int total = paises.size();
				int baixado = paises.indexOf(pais) + 1;

				int porcentagem = (int) (baixado / ((float) total) * 100);
				System.out.printf("Progresso %d/%d(%d%%) de medi��es de pa�ses.\n", baixado, total, porcentagem);
//				if (pais.getSlug().equals("haiti")) {
//					break;
//				}
			}

			this.setConfirmados(confirmados);
			this.setMortos(mortos);
			this.setRecuperados(recuperados);
			
			serialize(confirmadosFile, this.getConfirmados());
			serialize(mortosFile, this.getMortos());
			serialize(recuperadosFile, this.getRecuperados());

			long fim = new Date().getTime();
			float duracao = (float) (fim - inicio) / 1000.0f;

			System.out.printf("Medi��es dos pa�ses baixados em %.2f segundos\n", duracao);
		}
	}

	private <T> void serialize(File file, List<T> objects) {
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(objects);
			out.close();
			fileOut.close();
			System.out.println("\nSerializa��o realizada com sucesso\n");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Problema ao abrir ou fechar o arquivo.");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Medicao> deserialize(File file) {
		ArrayList<Medicao> medicoes = new ArrayList<>();

		try {
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			medicoes = (ArrayList<Medicao>) in.readObject();
			in.close();
			fileIn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return medicoes;
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