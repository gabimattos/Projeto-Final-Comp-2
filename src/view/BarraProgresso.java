package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.ProgressMonitor;
import javax.swing.UIManager;

public class BarraProgresso extends JPanel{

	private static final long serialVersionUID = 4L;
	static final int MINIMO = 0;
	static final int MAXIMO = 100;
	ProgressMonitor barraProgresso;

	
	public BarraProgresso() {
		 String message = "Baixando Países:";
		    String note = "iniciando...";
		    String title = "Baixando";
		    UIManager.put("ProgressMonitor.progressText", title);
		    barraProgresso = new ProgressMonitor(null, message, note, MINIMO, MAXIMO);
		
	}

	public void updateBar(int baixado, int total, int porcentagem) {
		if (!barraProgresso.isCanceled()) {
		System.out.println(porcentagem);
		barraProgresso.setNote("Progresso " + baixado + "/"+ total +" ("+ porcentagem +"%) de países\n");
		barraProgresso.setProgress(porcentagem);
		}
		else {
		System.exit(0);
		}
	}

}
