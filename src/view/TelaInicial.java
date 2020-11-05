package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class TelaInicial {
	

	private JFrame janela;
	private JPanel janelinha;
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
    
    private JLabel status1;
    private JLabel status2;
    private JLabel status3;
    private JLabel status4;
    private JLabel status5;
    private JLabel status6;
    private JLabel status7;
    private JLabel statusGeral;
    
    private JFrame erro;
    private JLabel lblerror;

    
    public TelaInicial(){
        initWindow();
     }

    
    public static void main(String[] args) {
    	   TelaInicial swingControlDemo = new TelaInicial();
    	     
 
    }
    
    public class AcaoBotaoEnviar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	
        	if(status1.getText().equals("numcasos0 ") && status2.getText().equals("numrecuperados0 ")
        	&& status3.getText().equals("nummortes0 ") && status4.getText().equals("crescasos0 ")
        	&& status5.getText().equals("cresrecuperados0 ") && status6.getText().equals("cresmortes0 ")
        	&& status7.getText().equals("mortalidade0 ")) {
        		
        		
        		erro = new JFrame("ERRO");
                erro.setSize(400, 200);
                
                lblerror = new JLabel("ERRO!\n Nenhuma opção selecionada!");
                erro.add(lblerror);
                erro.setVisible(true);
        	}
        	else if(dataInicial.getText().equals("")|| dataFinal.getText().equals("")) {
        		erro = new JFrame("ERRO");
                erro.setSize(400, 200);
              
                lblerror = new JLabel("ERRO!\n Alguma data não foi preenchida!");
                erro.add(lblerror);
                erro.setVisible(true);
        	}
        	
        	else {
        	
	            try (PrintStream out = new PrintStream(new FileOutputStream("mensagem.txt"))){
	                out.print(dataInicial.getText());
	                out.print(dataFinal.getText());
	                out.print(status1.getText());
	                out.print(status2.getText());
	                out.print(status3.getText());
	                out.print(status4.getText());
	                out.print(status5.getText());
	                out.print(status6.getText());
	                out.print(status7.getText());
	                
	                System.out.println(dataInicial.getText());
	                System.out.println(dataFinal.getText());
	                System.out.println(status1.getText());
	                System.out.println(status2.getText());
	                System.out.println(status3.getText());
	                System.out.println(status4.getText());
	                System.out.println(status5.getText());
	                System.out.println(status6.getText());
	                System.out.println(status7.getText());
	            }
	            catch (FileNotFoundException fnfe) {
	                System.out.println("Não foi possível gravar no arquivo mensagem.txt");
	            }
        	} 
        }
    }
    
    private void initWindow() {
    	
    	 janela = new JFrame("Coronavírus no mundo.");
         janela.setSize(900, 600);
         
         statusGeral = new JLabel("",JLabel.CENTER);
         statusGeral.setSize(350,100);
               
         status1 = new JLabel("numcasos0 ");
         status2 = new JLabel("numrecuperados0 ");
         status3 = new JLabel("nummortes0 ");
         status4 = new JLabel("crescasos0 ");   
         status5 = new JLabel("cresrecuperados0 ");
         status6 = new JLabel("cresmortes0 ");
         status7 = new JLabel("mortalidade0 ");

         janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
         btnEnviar = new JButton("Enviar");
         btnEnviar.setSize(20,30);
         
         lblRanking = new JLabel("Rankings Internacionais.");
         lblTopico1 = new JLabel("Número");
         lblTopico2 = new JLabel("Crescimento");
         lblTopico3 = new JLabel("Mortalidade");
         lblDataInicial = new JLabel("Data Inicial");
         lblDataFinal = new JLabel("Data Final");
         final JCheckBox cbOpcao1 = new JCheckBox("de casos");
         final JCheckBox cbOpcao2 = new JCheckBox("de recuperados");
         final JCheckBox cbOpcao3 = new JCheckBox("de mortos");
         final JCheckBox cbOpcao4 = new JCheckBox("de casos");
         final JCheckBox cbOpcao5 = new JCheckBox("de recuperados");
         final JCheckBox cbOpcao6 = new JCheckBox("de mortos");
         final JCheckBox cbOpcao7 = new JCheckBox("taxa de mortalidade");
         
         cbOpcao1.setMnemonic(KeyEvent.VK_1);
         cbOpcao2.setMnemonic(KeyEvent.VK_2);
         cbOpcao3.setMnemonic(KeyEvent.VK_3);
         cbOpcao4.setMnemonic(KeyEvent.VK_4);
         cbOpcao5.setMnemonic(KeyEvent.VK_5);
         cbOpcao6.setMnemonic(KeyEvent.VK_6);
         cbOpcao7.setMnemonic(KeyEvent.VK_7);
         
         
         cbOpcao1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	
            	status1.setText("numcasos" + (e.getStateChange()==1?"1 ":"0 "));
            	statusGeral.setText("Número de casos: " + (e.getStateChange()==1?"checked":"unchecked"));
            	//System.out.println(status1.getText());
            }
         });
         cbOpcao2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            
            	status2.setText("numrecuperados" + (e.getStateChange()==1?"1 ":"0 "));
            	statusGeral.setText("Número de recuperados: " + (e.getStateChange()==1?"checked":"unchecked"));
            	//System.out.println(status2.getText());
            }
         });
         cbOpcao3.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            
            	status3.setText("nummortes"+ (e.getStateChange()==1?"1 ":"0 "));
            	statusGeral.setText("Número de mortes: " + (e.getStateChange()==1?"checked":"unchecked"));
            	//System.out.println(status3.getText());
            }
         });
         cbOpcao4.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	
            	status4.setText("crescasos" + (e.getStateChange()==1?"1 ":"0 "));
            	statusGeral.setText("Crescimento de casos: " + (e.getStateChange()==1?"checked":"unchecked"));
               //System.out.println(status4.getText());
            }
         });
         cbOpcao5.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	
            	status5.setText("cresrecuperados"+ (e.getStateChange()==1?"1 ":"0 "));
            	statusGeral.setText("Crescimento de recuperados: " + (e.getStateChange()==1?"checked":"unchecked"));
               //System.out.println(status5.getText());
            }
         });
         cbOpcao6.addItemListener(new ItemListener() {
             public void itemStateChanged(ItemEvent e) {
       
            	 status6.setText("cresmortes"+ (e.getStateChange()==1?"1":"0"));
            	 statusGeral.setText("Crescimento de recuperados: " + (e.getStateChange()==1?"checked":"unchecked"));
                //System.out.println(status6.getText());
             }
          });
         cbOpcao7.addItemListener(new ItemListener() {
             public void itemStateChanged(ItemEvent e) {
            	 
            	 status7.setText("mortalidade"+ (e.getStateChange()==1?"1":"0"));
            	 statusGeral.setText("Taxa de mortalidade: " + (e.getStateChange()==1?"checked":"unchecked"));
                
             }
          });
         
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
         
         btnEnviar.addActionListener(new AcaoBotaoEnviar());
         
         janela.add(BorderLayout.NORTH, mb);
         janela.add(conteinerRanking, BorderLayout.WEST);
         

         statusGeral = new JLabel("",JLabel.CENTER);
         statusGeral.setSize(350,100);
         
  
                
         janela.add(status1);   
         janela.add(status2);   
         janela.add(status3);   
         janela.add(status4);   
         janela.add(status5);   
         janela.add(status6);   
         janela.add(status7);
         janela.add(statusGeral);
         
         janela.setVisible(true);
    }

}


