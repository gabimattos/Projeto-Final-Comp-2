package view;

import javax.swing.*;
import java.awt.*;

public class TelaInicial {
	
	private JFrame janela;
    private JButton btnEnviar;
    private JLabel lblRanking;
    private JLabel lblTopico1;
    private JLabel lblTopico2;
    private JLabel lblTopico3;
    private JLabel lblDataInicial;
    private JLabel lblDataFinal;
    private JCheckBox cbOpcao1;
    private JCheckBox cbOpcao2;
    private JCheckBox cbOpcao3;
    private JCheckBox cbOpcao4;
    private JCheckBox cbOpcao5;
    private JCheckBox cbOpcao6;
    private JCheckBox cbOpcao7;
    private JTextField dataInicial;
    private JTextField dataFinal;
    
    private void initWindow() {
    	
    	 janela = new JFrame("Coronavírus no mundo.");
         janela.setSize(800, 600);
         janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
         btnEnviar = new JButton("Enviar");
         btnEnviar.setSize(20,30);
         
         lblRanking = new JLabel("Rankings Internacionais.");
         lblTopico1 = new JLabel("Número");
         lblTopico2 = new JLabel("Crescimento");
         lblTopico3 = new JLabel("Mortalidade");
         lblDataInicial = new JLabel("Data Inicial");
         lblDataFinal = new JLabel("Data Final");
         cbOpcao1 = new JCheckBox("de casos");
         cbOpcao2 = new JCheckBox("de recuperados");
         cbOpcao3 = new JCheckBox("de mortos");
         cbOpcao4 = new JCheckBox("de casos");
         cbOpcao5 = new JCheckBox("de recuperados");
         cbOpcao6 = new JCheckBox("de mortos");
         cbOpcao7 = new JCheckBox("taxa de mortalidade");
         
         JMenuBar mb = new JMenuBar();
         JMenu m1 = new JMenu("Salvar Consultas");
         mb.add(m1);
         
         dataInicial = new JTextField();
         dataFinal = new JTextField();
         
         JPanel conteinerRanking = new JPanel();
         JPanel jpRanking = new JPanel();
         JPanel jpOpcoes1 = new JPanel();
         JPanel jpOpcoes2 = new JPanel();
         JPanel jpOpcoes3 = new JPanel();
         JPanel jpDatas = new JPanel();
         JPanel jpBotao = new JPanel();
         
         conteinerRanking.setLayout(new GridLayout(0,1,0,0));
         jpRanking.setLayout(new GridLayout(0,1,0,0));
         jpOpcoes1.setLayout(new GridLayout(0,3,0,1));
         jpOpcoes2.setLayout(new GridLayout(0,3,0,2));
         jpOpcoes3.setLayout(new GridLayout(0,2,0,0));
         jpDatas.setLayout(new GridLayout(0,2,0,0));
         jpBotao.setLayout(new GridLayout(0,2,0,0));
         
         jpRanking.setBorder(BorderFactory.createEmptyBorder(0, 50, 10, 70));
         conteinerRanking.setBorder(BorderFactory.createEtchedBorder());
         
         jpRanking.add(lblRanking);
         jpRanking.add(lblTopico1);
         jpOpcoes1.add(cbOpcao1);
         jpOpcoes1.add(cbOpcao2);
         jpOpcoes1.add(cbOpcao3);
         jpRanking.add(jpOpcoes1);
         
         jpRanking.add(lblTopico2);
         jpOpcoes2.add(cbOpcao4);
         jpOpcoes2.add(cbOpcao5);
         jpOpcoes2.add(cbOpcao6);
         jpRanking.add(jpOpcoes2);
         
         jpRanking.add(lblTopico3);
         jpOpcoes3.add(cbOpcao7);
         jpRanking.add(jpOpcoes3);
         
         jpDatas.add(lblDataInicial);
         jpDatas.add(lblDataFinal);
         jpDatas.add(dataInicial);
         jpDatas.add(dataFinal);  
         jpRanking.add(jpDatas);
         
         jpBotao.add(btnEnviar);
         jpRanking.add(jpBotao);
         
         
         conteinerRanking.add(jpRanking);
         
         janela.add(BorderLayout.NORTH, mb);
         janela.add(conteinerRanking, BorderLayout.WEST);
         
         janela.setVisible(true);
    }
     
    public static void main(String[] args) {
        new TelaInicial().initWindow();
 
    }
    

}
