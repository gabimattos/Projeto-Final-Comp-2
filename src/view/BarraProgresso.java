package view;

import javax.swing.JPanel;
import javax.swing.ProgressMonitor;
import javax.swing.UIManager;

public class BarraProgresso extends JPanel{
	//public static BarraProgresso barra = new BarraProgresso();
	private static final long serialVersionUID = 4L;
	static final int MINIMO = 0;
	static final int MAXIMO = 100;
	private ProgressMonitor barraProgresso;

	
	public BarraProgresso() {
		 String message = "Baixando Pa�ses:";
		    String note = "iniciando...";
		    String title = "Baixando";
		    UIManager.put("ProgressMonitor.progressText", title);
		    barraProgresso = new ProgressMonitor(null, message, note, MINIMO, MAXIMO);
		
	}

	public void updateBarPaises(int baixado, int total, int porcentagem) {
		if (!barraProgresso.isCanceled()) {
			System.out.println(porcentagem);
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
			System.out.println(porcentagem);
			int newPorcentagem = Math.max(0, porcentagem - 1) / 2 + 50;
			barraProgresso.setNote("Progresso " + baixado + "/"+ total +" ("+ newPorcentagem +"%) de medicoes dos paises\n");
			barraProgresso.setProgress(newPorcentagem);
		}
		else {
			System.exit(0);
		}
	}
	
//	public static void main(String[] args) {
//		
//		MedicaoController cont = null;
//		try {
//			cont = MedicaoController.getInstance();
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//  }
}