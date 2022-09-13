package classes;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;

public class Animacao extends JFrame {

	private JPanel contentPane;
	private final JTextArea vars = new JTextArea();
	private int velocidade = 4000;

	/**
	 * Create the frame.
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public Animacao() throws InterruptedException, IOException {
		setResizable(false);
		setBounds(100, 100, 771, 622);
		// Alterando o Look and Feel da Janela
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		setTitle("Animação");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Tela.class.getResource("/images/icon.png")));
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		vars.setFont(new Font("Arial", 0, 16));
		vars.setForeground(Color.BLACK);
		vars.setEditable(false);
		vars.setBounds(1, 0, 237, 548);
		contentPane.add(vars);
		
		JScrollPane scroll = new JScrollPane(vars);
		scroll.setBounds(new Rectangle(1, 35, 237, 548));
		contentPane.add(scroll);
		
		JLabel robot = new JLabel(". . .");
		robot.setFont(new Font("Arial", 0, 22));
		robot.setHorizontalAlignment(SwingConstants.CENTER);
		robot.setHorizontalTextPosition(SwingConstants.CENTER);
		robot.setForeground(new Color(0, 137, 176));
		robot.setBounds(247, 23, 498, 73);
		contentPane.add(robot);
		
		JLabel comando = new JLabel("");
		comando.setFont(new Font("DialogInput", Font.BOLD, 20));
		comando.setForeground(new Color(0, 137, 176));
		comando.setHorizontalAlignment(SwingConstants.CENTER);
		comando.setBounds(519, 251, 226, 110);
		contentPane.add(comando);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Animacao.class.getResource("/images/bgAnime.png")));
		label.setBounds(243, 0, 512, 489);
		contentPane.add(label);
		
		JButton btnRecomear = new JButton("Recome\u00E7ar");
		btnRecomear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new Animacao();
					setVisible(false);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnRecomear.setBounds(333, 521, 89, 41);
		contentPane.add(btnRecomear);
		
		JLabel lbls = new JLabel("4");
		JButton btnNewButton = new JButton("-");
		JButton btnNewButton_1 = new JButton("+");

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				velocidade = velocidade+1000;
				if(velocidade == 10000){
					btnNewButton_1.setEnabled(false);
				}
				
				if(velocidade > 1000){
					btnNewButton.setEnabled(true);
				}
				
				lbls.setText((velocidade/1000)+"");
			}
		});

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				velocidade = velocidade-1000;
				if(velocidade == 1000){
					btnNewButton.setEnabled(false);
				}
				
				if(velocidade < 10000){
					btnNewButton_1.setEnabled(true);
				}
				
				lbls.setText((velocidade/1000)+"");
			}
		});
		btnNewButton.setBounds(477, 530, 89, 23);
		contentPane.add(btnNewButton);
		
		btnNewButton_1.setBounds(632, 530, 89, 23);
		contentPane.add(btnNewButton_1);
		
		lbls.setHorizontalAlignment(SwingConstants.CENTER);
		lbls.setHorizontalTextPosition(SwingConstants.CENTER);
		lbls.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbls.setBounds(576, 540, 46, 14);
		contentPane.add(lbls);
		
		JLabel lblVelocidade = new JLabel("Velocidade");
		lblVelocidade.setBounds(576, 520, 51, 14);
		contentPane.add(lblVelocidade);
		
		JLabel lblVariaveis = new JLabel("Variaveis");
		lblVariaveis.setFont(new Font("Arial", Font.BOLD, 16));
		lblVariaveis.setBounds(80, 10, 70, 19);
		contentPane.add(lblVariaveis);
		
		Thread.sleep(3000);
		
		new Thread(new Runnable() {
			public void run() {
				int contLinhas=0;
				ArrayList<String> linhas = new ArrayList<String>();
				try {
					while(contLinhas < 3){
						
						Scanner in = null;
						if(new File("listaDeComandos.txt").exists())in = new Scanner(new FileReader("listaDeComandos.txt"));
						
						if(in != null){
							while (in.hasNext()) {
								contLinhas++;
								linhas.add(in.nextLine());
							}	
						}
						in.close();
					}
//					new File("listaDeComandos.txt").delete();
					setVisible(true);
					
					for (int i = 0; i < linhas.size(); i++) {
						if(linhas.get(i).length()>10) {
							comando.setFont(new Font("DialogInput", Font.BOLD, 13));
						}else {
							comando.setFont(new Font("DialogInput", Font.BOLD, 20));
						}
						if(i < 3){
							if(!linhas.get(i).trim().equals("")){
								String variaveis[] = linhas.get(i).split(",");
								for (int j = 0; j < variaveis.length; j++) {
									comando.setText(variaveis[j].trim());
									robot.setText("Variavel "+variaveis[j].trim()+" declarada.");
									vars.setText(vars.getText()+variaveis[j].trim()+"\n");
									Thread.sleep(velocidade);
								}								
							}
						}else if(linhas.get(i).contains("(") && linhas.get(i).contains(")")){
							String comandoResposta[] = linhas.get(i).split("#");
							comando.setText(comandoResposta[0]);
							robot.setText(comandoResposta[0].trim()+" ???");
							Thread.sleep(velocidade);
							robot.setText(comandoResposta[1].trim().toUpperCase());
							Thread.sleep(velocidade);
						
						}else if(linhas.get(i).contains("=")){
							String aux[] = linhas.get(i).split("=");
							String linhaVars[] = vars.getText().split("\n");
							vars.setText("");
							for (int j = 0; j < linhaVars.length; j++) {
								if(linhaVars[j].contains(aux[0].trim())){
									vars.setText(vars.getText()+linhas.get(i)+"\n");
								}else{
									vars.setText(vars.getText()+linhaVars[j]+"\n");
								}
							}
							
							robot.setText("Valor: "+aux[1].trim()+" atribuido a variavel: "+aux[0]);
							comando.setText(linhas.get(i));
							Thread.sleep(velocidade);
						}else if(linhas.get(i).trim().equals("fim")){
							robot.setText("Programa finalizado.");
							comando.setText("");
						}else{
							robot.setText(linhas.get(i));
							comando.setText("escreva(\""+linhas.get(i)+"\")");
							Thread.sleep(velocidade);
						}
					}
					
				} catch (FileNotFoundException | InterruptedException e) {	System.out.println(e.getMessage());}
			}
		}).start();
	}
}
