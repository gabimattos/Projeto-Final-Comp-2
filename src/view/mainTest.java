package view;


import java.awt.GridLayout;

import javax.swing.JFrame;

import controller.MedicaoController;

public class mainTest {
	public static  BarraProgresso barraProgresso;

    public static void main(String[] args) {
	    
        barraProgresso = new BarraProgresso();
	    
		MedicaoController cont = null;
		try {
			cont = MedicaoController.getInstance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	    
    }
}
