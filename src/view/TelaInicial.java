package view;


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
	

	private JFrame janela;
	//private JPanel conteinerKm;
    private JButton btnEnviarRanking;
    private JButton btnEnviarKm;
    private JLabel lblRanking;
    private JLabel lblTopico1;
    private JLabel lblTopico2;
    private JLabel lblTopico3;
    private JLabel lblDataInicial;
    private JLabel lblDataFinal;
    private JLabel lblKmConteiner;
    
    private JLabel lblkm;
    private JLabel lblDtFinal;
    private JLabel lblDtInicial;
    
    private JCheckBox cbOpcao1;
    private JCheckBox cbOpcao2;
    private JCheckBox cbOpcao3;
    private JCheckBox cbOpcao4;
    private JCheckBox cbOpcao5;
    private JCheckBox cbOpcao6;
    private JCheckBox cbOpcao7;
    private JTextField dataInicial;
    private JTextField dataFinal;
    
    private JTextField km;
    private JTextField dtInicial;
    private JTextField dtFinal;
    
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

    public MedicaoController medicao;
    private ConsultaController consulta = ConsultaController.getInstance();
    
    public TelaInicial(){
        initWindow();
     }

    
    public static void main(String[] args) {
    	   TelaInicial swingControlDemo = new TelaInicial();
    	     
 
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
    
    public class AcaoBotaoEnviarKm implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	
        	if(km.getText().equals("") || dtInicial.getText().equals("") || dtFinal.getText().equals("")) {
        		
        		
        		erro = new JFrame("ERRO");
                erro.setSize(400, 200);
                
                lblerror = new JLabel("ERRO!\n Você deixou de preencher algum campo!");
                erro.add(lblerror);
                erro.setVisible(true);
        	}

        	else {
        	
	            try (PrintStream out = new PrintStream(new FileOutputStream("infoLocais.txt"))){
	                out.print(km.getText());
	                out.print(dtInicial.getText());
	                out.print(dtFinal.getText());
	                //System.out.println(km.getText());

	               
	            }
	            catch (FileNotFoundException fnfe) {
	                System.out.println("Não foi possível gravar no arquivo infoLocais.txt");
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
         
         btnEnviarRanking = new JButton("Enviar");
         btnEnviarRanking.setSize(10,20);
         
         btnEnviarKm = new JButton("Enviar");
         btnEnviarKm.setSize(5,2);

         
         lblRanking = new JLabel("Rankings Internacionais.");
         lblTopico1 = new JLabel("Número");
         lblTopico2 = new JLabel("Crescimento");
         lblTopico3 = new JLabel("Mortalidade");
         lblDataInicial = new JLabel("Data Inicial");
         lblDataFinal = new JLabel("Data Final");
         
         lblkm = new JLabel("Raio em km");
         lblDtInicial = new JLabel("Data Inicial");
         lblDtFinal = new JLabel("Data Final");
         
         JPanel pnlKm = new JPanel();
         
         lblKmConteiner = new JLabel("Locais próximos do local com maior crescimento de casos.");
         //pnlKm.add(lblKmConteiner);
         
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
            	
            	consulta.atualizaNumeroDe("de casos", e.getStateChange());
            	
            	status1.setText("numcasos" + (e.getStateChange()==1?"1 ":"0 "));
            	statusGeral.setText("Número de casos: " + (e.getStateChange()==1?"checked":"unchecked"));
            	//System.out.println(status1.getText());
            }
         });
         cbOpcao2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            
            	consulta.atualizaNumeroDe("de recuperados", e.getStateChange());
            	
            	status2.setText("numrecuperados" + (e.getStateChange()==1?"1 ":"0 "));
            	statusGeral.setText("Número de recuperados: " + (e.getStateChange()==1?"checked":"unchecked"));
            	//System.out.println(status2.getText());
            }
         });
         cbOpcao3.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            
            	consulta.atualizaNumeroDe("de mortos", e.getStateChange());
            	status3.setText("nummortes"+ (e.getStateChange()==1?"1 ":"0 "));
            	statusGeral.setText("Número de mortes: " + (e.getStateChange()==1?"checked":"unchecked"));
            	//System.out.println(status3.getText());
            }
         });
         cbOpcao4.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	
            	consulta.atualizaCrescimentoDe("de casos", e.getStateChange());
            	
            	status4.setText("crescasos" + (e.getStateChange()==1?"1 ":"0 "));
            	statusGeral.setText("Crescimento de casos: " + (e.getStateChange()==1?"checked":"unchecked"));
               //System.out.println(status4.getText());
            }
         });
         cbOpcao5.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	
            	consulta.atualizaCrescimentoDe("de recuperados", e.getStateChange());
            	
            	status5.setText("cresrecuperados"+ (e.getStateChange()==1?"1 ":"0 "));
            	statusGeral.setText("Crescimento de recuperados: " + (e.getStateChange()==1?"checked":"unchecked"));
               //System.out.println(status5.getText());
            }
         });
         cbOpcao6.addItemListener(new ItemListener() {
             public void itemStateChanged(ItemEvent e) {
       
            	 consulta.atualizaCrescimentoDe("de mortos", e.getStateChange());
            	 
            	 status6.setText("cresmortes"+ (e.getStateChange()==1?"1":"0"));
            	 statusGeral.setText("Crescimento de recuperados: " + (e.getStateChange()==1?"checked":"unchecked"));
                //System.out.println(status6.getText());
             }
          });
         cbOpcao7.addItemListener(new ItemListener() {
             public void itemStateChanged(ItemEvent e) {
            	 
            	 consulta.atualizaMortalidade(e.getStateChange());
            	 
            	 status7.setText("mortalidade"+ (e.getStateChange()==1?"1":"0"));
            	 statusGeral.setText("Taxa de mortalidade: " + (e.getStateChange()==1?"checked":"unchecked"));
                
             }
          });
         
         JMenuBar mb = new JMenuBar();
         JMenu m1 = new JMenu("Salvar Consultas");
         mb.add(m1);
         
         dataInicial = new JTextField();
         dataFinal = new JTextField();
         
         km = new JTextField();
         dtInicial = new JTextField();
         dtFinal = new JTextField();
         
         
         JPanel conteinerEAST = new JPanel();
         JPanel conteinerRanking = new JPanel();
         JPanel jpRanking = new JPanel();
         JPanel jpOpcoes1 = new JPanel();
         JPanel jpOpcoes2 = new JPanel();
         JPanel jpOpcoes3 = new JPanel();
         JPanel jpDatas = new JPanel();
         JPanel jpBotaoRanking = new JPanel();
         JPanel jpBotaoLocais = new JPanel();
         JPanel jpCampos = new JPanel();
         
         JPanel conteinerKm = new JPanel();
         JPanel conteinerStatus = new JPanel();
         
         conteinerEAST.setLayout(new GridLayout(0,1,0,0));
         conteinerRanking.setLayout(new GridLayout(0,1,0,0));
         jpRanking.setLayout(new GridLayout(0,1,0,0));
         jpOpcoes1.setLayout(new GridLayout(0,3,0,1));
         jpOpcoes2.setLayout(new GridLayout(0,3,0,2));
         jpOpcoes3.setLayout(new GridLayout(0,2,0,0));
         jpDatas.setLayout(new GridLayout(0,2,0,0));
         jpBotaoRanking.setLayout(new GridLayout(0,1,0,0));
         jpBotaoLocais.setLayout(new GridLayout(0,1,0,0));
         
         jpCampos.setLayout(new GridLayout(0,3,0,2));
         
         conteinerKm.setLayout(new GridLayout(0,1,1,0));
         
         jpCampos.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 30));
         jpRanking.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 30));
         jpBotaoRanking.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 30));
         jpBotaoLocais.setBorder(BorderFactory.createEmptyBorder(0, 30, 50, 30));
         conteinerRanking.setBorder(BorderFactory.createEtchedBorder());
         conteinerEAST.setBorder(BorderFactory.createEtchedBorder());
         
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
         
         jpBotaoRanking.add(btnEnviarRanking);
         jpRanking.add(jpBotaoRanking);
         
         
         conteinerRanking.add(jpRanking);
         
         conteinerKm.add(lblKmConteiner);
         
         jpCampos.add(lblkm);
         jpCampos.add(lblDtInicial);
         jpCampos.add(lblDtFinal);
         jpCampos.add(km);
         jpCampos.add(dtInicial);
         jpCampos.add(dtFinal);
         
         jpBotaoLocais.add(btnEnviarKm);
         
         conteinerKm.add(jpCampos);
         conteinerKm.add(jpBotaoLocais);
         
         btnEnviarRanking.addActionListener(new AcaoBotaoEnviarRanking());
         btnEnviarKm.addActionListener(new AcaoBotaoEnviarKm());
         
         janela.add(BorderLayout.NORTH, mb);
         janela.add(conteinerRanking, BorderLayout.WEST);
         

         statusGeral = new JLabel("",JLabel.CENTER);
         statusGeral.setSize(350,100);
         
         conteinerStatus.add(statusGeral);
         conteinerEAST.add(conteinerKm, BorderLayout.NORTH);
         conteinerEAST.add(conteinerStatus, BorderLayout.NORTH);
         
       
         janela.add(conteinerEAST);
         
         janela.setVisible(true);
         
         this.medicao = MedicaoController.getInstance();
    }

}


