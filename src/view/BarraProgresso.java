package view;

import javax.swing.JPanel;
import javax.swing.ProgressMonitor;
import javax.swing.UIManager;

import controller.MedicaoController;

public class BarraProgresso extends JPanel{
	public static BarraProgresso barra = new BarraProgresso();
	private static final long serialVersionUID = 4L;
	static final int MINIMO = 0;
	static final int MAXIMO = 100;
	private ProgressMonitor barraProgresso;

	
	public BarraProgresso() {
		 String message = "Baixando Países:";
		    String note = "iniciando...";
		    String title = "Baixando";
		    UIManager.put("ProgressMonitor.progressText", title);
		    ProgressMonitor barraProgresso = new ProgressMonitor(null, message, note, MINIMO, MAXIMO);
		
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
	
	public static void main(String[] args) {
		
		MedicaoController cont = null;
		try {
			cont = MedicaoController.getInstance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
}
