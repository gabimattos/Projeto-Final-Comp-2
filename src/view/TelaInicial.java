package view;

import controller.ConsultaController;

import javax.swing.*;

import controller.ConsultaController;
import controller.MedicaoController;
import model.Consulta;
import model.StatusCaso;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.List;
import java.awt.event.*;
import java.io.*;

public class TelaInicial {
	
	ConsultaController consulta = ConsultaController.getInstance();
	

	private JFrame janela;
	private JFrame erro;
	
    private JButton btnEnviarRanking;
    
    private JLabel lblRanking;
    private JLabel lblTopico1;
    private JLabel lblTopico2;
    private JLabel lblTopico3;
    private JLabel lblTopico4;
    private JLabel lblDataInicial;
    private JLabel lblDataFinal;
    private JLabel status1;
    private JLabel status2;
    private JLabel status3;
    private JLabel status4;
    private JLabel status5;
    private JLabel status6;
    private JLabel status7;
    private JLabel status8;
    private JLabel statusGeral;
    private JLabel lblerror;
    
    private JCheckBox cbOpcao1;
    private JCheckBox cbOpcao2;
    private JCheckBox cbOpcao3;
    private JCheckBox cbOpcao4;
    private JCheckBox cbOpcao5;
    private JCheckBox cbOpcao6;
    private JCheckBox cbOpcao7;
    private JCheckBox cbOpcao8;
    
    private JTextField dataInicial;
    private JTextField dataFinal;
     
    public MedicaoController medicao;
        
    public static void main(String[] args) {
    	   new TelaInicial().initWindow();
    }
    
    public class AcaoBotaoEnviarRanking implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	Consulta consultaAtual = consulta.getConsultaAtual();

        	consulta.atualizaPeriodo("Data Inicial", dataInicial.getText());
        	consulta.atualizaPeriodo("Data Final", dataFinal.getText());
        	
        	if(!consultaAtual.getNumeroDe().get(StatusCaso.CONFIRMADOS) && !consultaAtual.getNumeroDe().get(StatusCaso.RECUPERADOS)
        	&& !consultaAtual.getNumeroDe().get(StatusCaso.MORTOS) && !consultaAtual.getCrescimentoDe().get(StatusCaso.CONFIRMADOS)
        	&& !consultaAtual.getCrescimentoDe().get(StatusCaso.RECUPERADOS) && !consultaAtual.getCrescimentoDe().get(StatusCaso.MORTOS)
        	&& !consultaAtual.getMortalidade() && !consultaAtual.getLocaisMaisProximos()) {        		
       		
        		erro = new JFrame("ERRO");
                erro.setSize(400, 200);
                
                lblerror = new JLabel("ERRO!\n Nenhuma opcao selecionada!");
                erro.add(lblerror);
                erro.setVisible(true);
        	}
        	else if(consultaAtual.getInicioPeriodo() == null || consultaAtual.getFimPeriodo() == null) {
        		erro = new JFrame("ERRO");
                erro.setSize(400, 200);
              
                lblerror = new JLabel("ERRO!\n Alguma data nao foi preenchida!");
                erro.add(lblerror);
                erro.setVisible(true);
        	}
        	
        	else {

        		System.out.println("teste");
	            List<String[][]> datas = consulta.realizarConsulta(consulta.getIndexAtual());
	            
	            for(String[][] data: datas) {
	            	for(int i = 0; i < data.length; i++) {
	            		System.out.println(data[i][0]+" "+data[i][1]);
	            	}

	            }
        	} 
        }
    }
    
    
    private void initWindow() {
    	
    	 janela = new JFrame("Coronavírus no mundo.");
         janela.setSize(900, 600);
         janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
         statusGeral = new JLabel("",JLabel.CENTER);
         statusGeral.setSize(350,100);
         
               
         status1 = new JLabel("");
         status2 = new JLabel("");
         status3 = new JLabel("");
         status4 = new JLabel("");   
         status5 = new JLabel("");
         status6 = new JLabel("");
         status7 = new JLabel("");
         status8 = new JLabel("");

                
         btnEnviarRanking = new JButton("Enviar");
                
         lblRanking = new JLabel("Rankings Internacionais.");
         lblTopico1 = new JLabel("Número");
         lblTopico2 = new JLabel("Crescimento");
         lblTopico3 = new JLabel("Mortalidade");
         lblTopico4 = new JLabel("Locais Próximos");
         lblDataInicial = new JLabel("Data Inicial");
         lblDataFinal = new JLabel("Data Final");
         
         dataInicial = new JTextField();
         dataFinal = new JTextField();
        
         
         final JCheckBox cbOpcao1 = new JCheckBox("de casos");
         final JCheckBox cbOpcao2 = new JCheckBox("de recuperados");
         final JCheckBox cbOpcao3 = new JCheckBox("de mortos");
         final JCheckBox cbOpcao4 = new JCheckBox("de casos");
         final JCheckBox cbOpcao5 = new JCheckBox("de recuperados");
         final JCheckBox cbOpcao6 = new JCheckBox("de mortos");
         final JCheckBox cbOpcao7 = new JCheckBox("taxa de mortalidade");
         final JCheckBox cbOpcao8 = new JCheckBox("locais próximos");
         
         cbOpcao1.setMnemonic(KeyEvent.VK_1);
         cbOpcao2.setMnemonic(KeyEvent.VK_2);
         cbOpcao3.setMnemonic(KeyEvent.VK_3);
         cbOpcao4.setMnemonic(KeyEvent.VK_4);
         cbOpcao5.setMnemonic(KeyEvent.VK_5);
         cbOpcao6.setMnemonic(KeyEvent.VK_6);
         cbOpcao7.setMnemonic(KeyEvent.VK_7);
         cbOpcao8.setMnemonic(KeyEvent.VK_8);
         
         
         cbOpcao1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	
            	consulta.atualizaNumeroDe("de casos", e.getStateChange());
            	
            	status1.setText("numcasos" + (e.getStateChange()==1?"1 ":"0 "));
            	statusGeral.setText("Número de casos: " + (e.getStateChange()==1?"checked":"unchecked"));
            	
            }
         });
         cbOpcao2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	
            	consulta.atualizaNumeroDe("de recuperados", e.getStateChange());
            
            	consulta.atualizaNumeroDe("de recuperados", e.getStateChange());
            	
            	status2.setText("numrecuperados" + (e.getStateChange()==1?"1 ":"0 "));
            	statusGeral.setText("Número de recuperados: " + (e.getStateChange()==1?"checked":"unchecked"));
            	
            }
         });
         cbOpcao3.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	
            	consulta.atualizaNumeroDe("de mortos", e.getStateChange());
            
            	consulta.atualizaNumeroDe("de mortos", e.getStateChange());
            	status3.setText("nummortes"+ (e.getStateChange()==1?"1 ":"0 "));
            	statusGeral.setText("Número de mortes: " + (e.getStateChange()==1?"checked":"unchecked"));
            	
            }
         });
         cbOpcao4.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	
            	consulta.atualizaCrescimentoDe("de casos", e.getStateChange());
            	
            	status4.setText("crescasos" + (e.getStateChange()==1?"1 ":"0 "));
            	statusGeral.setText("Crescimento de casos: " + (e.getStateChange()==1?"checked":"unchecked"));
               
            }
         });
         cbOpcao5.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	
            	consulta.atualizaCrescimentoDe("de recuperados", e.getStateChange());
            	
            	status5.setText("cresrecuperados"+ (e.getStateChange()==1?"1 ":"0 "));
            	statusGeral.setText("Crescimento de recuperados: " + (e.getStateChange()==1?"checked":"unchecked"));
               
            }
         });
         cbOpcao6.addItemListener(new ItemListener() {
             public void itemStateChanged(ItemEvent e) {
            	 
            	 consulta.atualizaCrescimentoDe("de mortos", e.getStateChange());
            	 
            	 status6.setText("cresmortes"+ (e.getStateChange()==1?"1":"0"));
            	 statusGeral.setText("Crescimento de recuperados: " + (e.getStateChange()==1?"checked":"unchecked"));

             }
          });
         cbOpcao7.addItemListener(new ItemListener() {
             public void itemStateChanged(ItemEvent e) {
            	 
            	 consulta.atualizaMortalidade(e.getStateChange());
            	 
            	 status7.setText("mortalidade"+ (e.getStateChange()==1?"1":"0"));
            	 statusGeral.setText("Taxa de mortalidade: " + (e.getStateChange()==1?"checked":"unchecked"));
                
             }
          });
         
         cbOpcao8.addItemListener(new ItemListener() {
             public void itemStateChanged(ItemEvent e) {
            	 
            	 consulta.atualizaLocaisMaisProximos(e.getStateChange());
            	 
            	 status8.setText("locais"+ (e.getStateChange()==1?"1":"0"));
            	 statusGeral.setText("Locais mais próximos: " + (e.getStateChange()==1?"checked":"unchecked"));
                
             }
          });
         
         JMenuBar mb = new JMenuBar();
         JMenu m1 = new JMenu("Salvar Consultas");
         mb.add(m1);
         
       
         JPanel conteinerRanking = new JPanel();
         JPanel jpRanking = new JPanel();
         JPanel jpOpcoes1 = new JPanel();
         JPanel jpOpcoes2 = new JPanel();
         JPanel jpOpcoes3 = new JPanel();
         JPanel jpOpcoes4 = new JPanel();
         JPanel jpDatas = new JPanel();
         JPanel jpBotaoRanking = new JPanel();
         JPanel conteinerStatus = new JPanel();
         
   
         conteinerRanking.setLayout(new GridLayout(0,1,0,0));
         jpRanking.setLayout(new GridLayout(0,1,0,0));
         jpOpcoes1.setLayout(new GridLayout(0,3,0,1));
         jpOpcoes2.setLayout(new GridLayout(0,3,0,2));
         jpOpcoes3.setLayout(new GridLayout(0,2,0,0));
         jpOpcoes4.setLayout(new GridLayout(0,2,0,0));
         jpDatas.setLayout(new GridLayout(0,2,0,0));
         jpBotaoRanking.setLayout(new GridLayout(0,1,0,0));   
         
         jpRanking.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 30));
         jpBotaoRanking.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 30));
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
         
         jpRanking.add(lblTopico4);
         jpOpcoes4.add(cbOpcao8);
         jpRanking.add(jpOpcoes4);
         
         jpDatas.add(lblDataInicial);
         jpDatas.add(lblDataFinal);
         jpDatas.add(dataInicial);
         jpDatas.add(dataFinal);  
         jpRanking.add(jpDatas);
         
         jpBotaoRanking.add(btnEnviarRanking);
         jpRanking.add(jpBotaoRanking);
                  
         conteinerRanking.add(jpRanking);
         conteinerStatus.add(statusGeral);

         btnEnviarRanking.addActionListener(new AcaoBotaoEnviarRanking()); 
         
         janela.add(BorderLayout.NORTH, mb);
         janela.add(conteinerRanking, BorderLayout.WEST); 
         janela.add(conteinerStatus);
         janela.setVisible(true);
         
         this.medicao = MedicaoController.getInstance();
    }

}


