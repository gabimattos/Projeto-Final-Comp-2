package view;
import javax.swing.*;


import java.awt.event.*;
import java.awt.*;


public class Historico{

	public Historico (){
		final int MAX = 10;
        // inicializa os elementos da list
        String[] listElems = new String[MAX];
        for (int i = 0; i < MAX; i++) {
            listElems[i] = "dia " + i;
        }
 
        final JList<String> list = new JList<>(listElems);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JScrollPane pane = new JScrollPane(list);
        final JFrame frame = new JFrame("Histórico");
 
        // cria botão e adiciona listener de ação
        final JButton button = new JButton("Pegar Ranking");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String elementoSelecionado = "";
                int indicesSeleciodado[] = list.getSelectedIndices();
                for (int j = 0; j < indicesSeleciodado.length; j++) {
                    String elem =
                            (String) list.getModel().getElementAt(indicesSeleciodado[j]);
                    elementoSelecionado += "\n" + elem;
 
                }
                JOptionPane.showMessageDialog(frame,
                        "Voce selecionou:" + elementoSelecionado);
            }// termina actionPerformed
        });
 
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(pane, BorderLayout.CENTER);
        frame.getContentPane().add(button, BorderLayout.SOUTH);
        frame.setSize(250, 200);
        frame.setVisible(true);
	}
 
    public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Historico();
            }
        });
    }
}