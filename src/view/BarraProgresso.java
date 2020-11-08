package view;

import javax.swing.JFrame;
import javax.swing.ProgressMonitor;
import javax.swing.UIManager;

public class BarraProgresso extends JFrame {
	private static final long serialVersionUID = 4L;
	static final int MINIMO = 0;
	static final int MAXIMO = 100;
	private ProgressMonitor barraProgresso;
	//private JPanel panel;
	
	public BarraProgresso() {
		 String message = "Baixando Paises e suas medicoes:";
		    String note = "iniciando...";
		    String title = "Baixando";
		    UIManager.put("ProgressMonitor.progressText", title);
		    
		    barraProgresso = new ProgressMonitor(this, message, note, MINIMO, MAXIMO);
	}

	public void updateBarPaises(int baixado, int total, int porcentagem) {
		if (!barraProgresso.isCanceled()) {
			int newPorcentagem = Math.max(0, porcentagem - 1) / 2;
			barraProgresso.setNote("Progresso " + baixado + "/"+ total +" ("+ newPorcentagem +"%) de paises\n");
			barraProgresso.setProgress(newPorcentagem);
		}
		else {
			System.exit(0);
		}
	}
	
	public void updateBarMedicoes(int baixado, int total, int porcentagem) {
		if (!barraProgresso.isCanceled()) {
			int newPorcentagem = Math.max(0, porcentagem - 1) / 2 + 50;
			barraProgresso.setNote("Progresso " + baixado + "/"+ total +" ("+ newPorcentagem +"%) de medicoes dos paises\n");
			barraProgresso.setProgress(newPorcentagem);
			if(porcentagem == 100) barraProgresso.close();
		}
		else {
			System.exit(0);
		}
	}
}