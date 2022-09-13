package classes;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import java.awt.Font;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Manual extends JFrame {
	
	JLabel interacao;
	JLabel condicao;
	JLabel repeticao;
	JLabel vetor;
	
	private JPanel contentPane;

	// Create the frame.
	public Manual() {
		setResizable(false);
		setTitle("Lista de Comandos");
		setBounds(100, 100, 550, 400);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Tela.class.getResource("/images/icon.png")));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Alterando o Look and Feel da Janela
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
			
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 544, 372);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Comandos de Interação", null, panel, null);
		tabbedPane.setForegroundAt(0, Color.BLACK);
		panel.setLayout(null);
//		
//		JTextArea txtrEscrevaComandoUtilizado = new JTextArea();
//		txtrEscrevaComandoUtilizado.setEditable(false);
//		txtrEscrevaComandoUtilizado.setFont(new Font("Arial", 0, 17));
//		txtrEscrevaComandoUtilizado.setLineWrap(true);
//		txtrEscrevaComandoUtilizado.setText("ESCREVA:\r\nComando utilizado para escrever uma mensagem no console.\r\nComando: \r\nescreva(\"exemplo\")\r\n\r\nLEIA:\r\nComando utilizado para ler um valor que sera atribuido a \r\numa variavel.\r\nComando:\r\nleia(exemplo)\r\n\r\nATRIBUI\u00C7\u00C3O:\r\nComando utilizado para atribuir um valor a uma variavel.\r\nComando:\r\nvariavel <- \"Hello World\"");
//		txtrEscrevaComandoUtilizado.setBounds(0, 0, 539, 344);
//		panel.add(txtrEscrevaComandoUtilizado);
//		
		JLabel interacao = new JLabel();
		interacao.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/interação.png")).getImage().getScaledInstance(500, 400, 0)));
		interacao.setBounds(500, 400, 500, 400);
		interacao.setLocation(-1,0);
		interacao.setVisible(true);
		panel.add(interacao);
//		
//		
		JPanel panel2 = new JPanel();
		tabbedPane.addTab("Estruturas de Condição", null, panel2, null);
		panel2.setLayout(null);
//		
//		JTextArea tfCondicao = new JTextArea();
//		tfCondicao.setText("SE:\r\nComando utilizado para verificar se uma condi\u00E7\u00E3o \u00E9 \r\nverdadeira.\r\nComando: \r\nse (  i == 5 )ent\u00E3o\r\nfim_se\r\n\r\n\r\nSENAO:\r\nComando utilizado caso a condi\u00E7\u00E3o de um IF seja falsa. \r\nComando:\r\nsenao ( i == 5 ) fa\u00E7a\r\nfim_se\r\n\r\n\r\nSENAO_SE:\r\nComando utilizado caso a condi\u00E7\u00E3o de um IF seja falsa e \r\ndeseja fazer uma nova\r\nverifica\u00E7\u00E3o.\r\nComando:\r\nse ( i == 5 ) fa\u00E7a \r\nsenao_se  ( i == 10 ) fa\u00E7a \r\nfim_se\r\n");
//		tfCondicao.setLineWrap(true);
//		tfCondicao.setFont(new Font("Arial", 0, 17));
//		tfCondicao.setEditable(false);
//		tfCondicao.setBounds(0, 0, 529, 334);
//		panel2.add(tfCondicao);	
//
//		JScrollPane scroll1 = new JScrollPane(tfCondicao);
//		scroll1.setBounds(new Rectangle(0, 0, 539, 344));
//		panel2.add(scroll1);

		JLabel condicao = new JLabel();
		condicao.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/condição.png")).getImage().getScaledInstance(500, 400, 0)));
		condicao.setBounds(500, 400, 500, 400);
		condicao.setLocation(-1,0);
		condicao.setVisible(true);
		
		JScrollPane scroll1 = new JScrollPane(condicao);
		scroll1.setBounds(new Rectangle(0, 0, 530, 344));
		scroll1.setBorder(null);
		panel2.add(scroll1);
		
		
		//Laços de repetição
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Laços de Repetição", null, panel_2, null);
		panel_2.setLayout(null);
//		
//		JTextArea tfLaço = new JTextArea();
//		tfLaço.setEditable(false);
//		tfLaço.setFont(new Font("Arial", 0, 17));
//		tfLaço.setText("ENQUANTO:\r\nLa\u00E7o de repeti\u00E7\u00E3o com teste de condi\u00E7\u00E3o antes de cada \r\nvolta.\r\nComando:\r\nenquanto( i < 10 ) fa\u00E7a\r\nfim_enquanto\r\n\r\n\r\nFA\u00C7A ENQUANTO:\r\nLa\u00E7o de repeti\u00E7\u00E3o com teste de condi\u00E7\u00E3o depois de cada \r\nvolta.\r\nComando:\r\nfa\u00E7a { } enquanto ( i < 10 );\r\n\r\n\r\nPARA:\r\nLa\u00E7o de repeti\u00E7\u00E3o com teste antes de cada volta onde \r\ntambem \u00E9 possivel declarar uma variavel e auto incrementar.\r\nComando:\r\npara ( i = 0; i < 10; i++ ) fa\u00E7a\r\nfim_para\r\n");
//		tfLaço.setBounds(0, 0, 529, 334);
//		panel_2.add(tfLaço);
//		
//		JScrollPane scroll2 = new JScrollPane(repeticao);
		JLabel repeticao = new JLabel();
		repeticao.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/repetição.png")).getImage().getScaledInstance(500, 400, 0)));
		repeticao.setBounds(500, 400, 500, 400);
		repeticao.setLocation(-1,0);
		repeticao.setVisible(true);
				
		JScrollPane scroll2 = new JScrollPane(repeticao);
		scroll2.setBounds(new Rectangle(0, 0, 530, 344));
		scroll2.setBorder(null);
		panel_2.add(scroll2);
		
		//Vetor e Matriz		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Vetor e Matriz", null, panel_3, null);
		panel_3.setLayout(null);
//	
//		JTextArea tfVetor = new JTextArea();
//		tfVetor.setFont(new Font("Arial", 0, 17));
//		tfVetor.setText("\r\nVETOR:\r\nCole\u00E7\u00E3o de variaveis de mesmo tipo, contendo apenas linha.\r\nComando:\r\nvetor [ ] <- Novo Inteiro[ n ]\r\n\r\n\r\nMATRIZ:\r\nCole\u00E7\u00E3o de variaveis de mesmo tipo, contendo linha e coluna.\r\nComando:\r\nmatriz [ ] [ ] <- Novo Inteiro[ n ] [ n ]");
//		tfVetor.setEditable(false);
//		tfVetor.setBounds(0, 0, 539, 344);
//		
		JLabel vetor = new JLabel();
		vetor.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/vetorematriz.png")).getImage().getScaledInstance(500, 400, 0)));
		vetor.setBounds(500, 400, 500, 400);
		vetor.setLocation(-1,0);
		vetor.setVisible(true);		
		panel_3.setVisible(true);
		panel_3.add(vetor);
		
		setVisible(true);
	}
}
