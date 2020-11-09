package view;

import javax.swing.JFrame;
import javax.swing.ProgressMonitor;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 * Classe que mostra visualmente a progressao dos arquivos baixados pela API.
 * 
 * @author Victoria Almeida - 118.140.336
 *
 */
public class BarraProgresso extends JFrame {
	private static final long serialVersionUID = 4L;
	static final int MINIMO = 0;
	static final int MAXIMO = 100;
	public ProgressMonitor barraProgresso;

	/**
	 * Classe contrutora da barra de progresso.
	 */
	public BarraProgresso() {
		String message = "Baixando paises e suas medicoes:";
		String note = "iniciando...";
		String title = "Baixando";
		UIManager.put("ProgressMonitor.progressText", title);

		barraProgresso = new ProgressMonitor(this, message, note, MINIMO, MAXIMO);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * Atualiza a porcentagem da barra de acordo com o quanto
	 * a API ja foi lida. Considerando o total de leitura de paises 50%.
	 * 
	 * @param baixado Total de paises baixados.
	 * @param total Total de paises que serao baixados.
	 * @param porcentagem Porcentagem dos paises que ja foram baixados.
	 */
	public void updateBarPaises(int baixado, int total, int porcentagem) {
		if (!barraProgresso.isCanceled()) {
			//subtrai por 1 para so chegar em 100% apos a serializacao
			int newPorcentagem = Math.max(0, porcentagem - 1) / 2;
			barraProgresso.setNote("Progresso " + baixado + "/" + total + " (" + newPorcentagem + "%) de paises\n");
			barraProgresso.setProgress(newPorcentagem);
		} else {
			System.exit(0);
		}
	}

	/**
	 * Atualiza a porcentagem da barra de acordo com o quanto
	 * a API j� foi lida. Considerando o total de leitura de medicoes 100%.
	 * 
	 * @param baixado Total de medicoes baixadas.
	 * @param total Total de medicoes que ser�o baixadas.
	 * @param porcentagem Porcentagem das medicoes que j� foram baixadas.
	 */
	public void updateBarMedicoes(int baixado, int total, int porcentagem) {
		if (!barraProgresso.isCanceled()) {
			//subtrai por 1 para s� chegar em 100% ap�s a serializa��o
			int newPorcentagem = Math.max(0, porcentagem - 1) / 2 + 50;
			barraProgresso.setNote(
					"Progresso " + baixado + "/" + total + " (" + newPorcentagem + "%) de medicoes dos paises\n");
			barraProgresso.setProgress(newPorcentagem);
			if (porcentagem == 100)
				barraProgresso.close();
		} else {
			System.exit(0);
		}
	}
}