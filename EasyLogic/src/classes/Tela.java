package classes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;	
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

public class Tela extends JFrame implements KeyListener, MouseListener, CaretListener, ActionListener, WindowListener {

	private static Tela tela;
	
	JButton newCode;
	JButton saveCode;
	JButton runCode;
	JButton manual;
	JButton animeCode;
	JButton minmax;
	JButton fecha;
	
	JLabel txtnewCode;
	JLabel txtsaveCode;
	JLabel txtrunCode;
	JLabel txtmanual;
	JLabel txtanimeCode;
	JLabel elog;
	JLabel lblBg = new JLabel();
	JLabel arqs = new JLabel("Arquivos");
	JLabel gif = new JLabel();
	JLabel compilando = new JLabel("Compilando...");
	JLabel ops;
	
	JPanel menuTop;
	JPanel menuLeft;
	JPanel menuRight;

	JFrame frameLoading = new JFrame();

	int height, width;
	int lineCaret;
	boolean alterConsole = true;

	int contId = 0;
	String path;
	ArrayList<File> files;
	ArrayList<Arquivo> btnsArquivos = new ArrayList<Arquivo>();
	ArrayList<Tab> tabs = new ArrayList<Tab>();
	Tab tabSelecionado;
	Arquivo arquivoSelecionado;

	JTextPane view;
	JTextArea code;
	JTextArea java;
	JScrollPane scrollView;
	JScrollPane scrollCode;
	JScrollPane scrollJava;

	Highlighter highlighter;
	HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(
			new Color(232, 242, 254));
	

	public static Tela getInstance(String path, ArrayList<File> files) {
		if (tela == null)
			tela = new Tela(path, files);
		return tela;
	}

	public static Tela getInstance() {
		return tela;
	}

	private Tela(String path, ArrayList<File> files) {
		setTitle("EasyLogic");
		setSize(1000, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Tela.class.getResource("/images/icon.png")));
		setLocationRelativeTo(null);
		setMinimumSize(this.getSize());
		setLayout(null);
		setExtendedState(tela.MAXIMIZED_BOTH);
//		setUndecorated(true);
		height = getHeight();
		width = getWidth();
		
		getContentPane().setBackground(new Color(0, 137, 176)); //Cor do Fundo ~trxiez
		
		frameLoading.setLayout(null);
		frameLoading.setSize(getWidth()-14, getHeight()-36);
		frameLoading.getContentPane().setBackground(Color.white);
		frameLoading.setUndecorated(true);
		
		frameLoading.setLocation(getX()+7, getY()+29);

		gif.setIcon(new ImageIcon(Tela.class.getResource("/images/loading.gif")));
		gif.setBounds(256, 256, 256, 256);
		gif.setLocation(frameLoading.getWidth()/2, frameLoading.getHeight()/2);
		frameLoading.add(gif);
		
		this.path = path;
		this.files = files;
		init();

		setVisible(true);
		code.setCaretPosition(code.getText().length());
		compilando.setHorizontalTextPosition(SwingConstants.CENTER);

		new Thread(new Runnable() {
			public void run() {
				boolean flag = alterConsole;
				int nTabs = 0;
				compilando.setFont(new Font("Arial", 1, 30));
				compilando.setForeground(Color.gray);
				while (true) {
					frameLoading.setLocation(getX()+7, getY()+28);
					frameLoading.setSize(getWidth()-14, getHeight()-35);
					gif.setBounds((frameLoading.getWidth()/2)-(100), (frameLoading.getHeight()/2)-100, 200, 200);
					
					if (height != getHeight() || width != getWidth() || flag != alterConsole || nTabs != tabs.size()) {

						flag = alterConsole;
						height = getHeight();
						width = getWidth();
						nTabs = tabs.size();

						position();

					}
					try {
						Thread.sleep(350);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}).start();
	}

	private void position() {
		lblBg.setBounds(-1, 0, getWidth(), getHeight());

		menuTop.setBounds(0, 0, getWidth()-15, (getHeight() / 100) * 7);
		menuLeft.setBounds(5,(menuTop.getY() + menuTop.getHeight()) + 5,(getWidth() / 100) * 18,(getHeight() - menuTop.getHeight()) - 50);
		menuRight.setBounds((menuLeft.getX() + menuLeft.getWidth()) + 5, menuLeft.getY() + 30, (getWidth() - menuLeft.getWidth()) - 32,	menuLeft.getHeight() - 30);

		newCode.setBounds(10, (menuTop.getHeight() / 2) - 20, 40, 40);
		saveCode.setBounds((newCode.getX() + newCode.getWidth())+ (newCode.getWidth() / 2),(menuTop.getHeight() / 2) - 20, 40, 40);
		runCode.setBounds((saveCode.getX() + saveCode.getWidth())	+ (saveCode.getWidth() / 2),(menuTop.getHeight() / 2) - 20, 40, 40);
		animeCode.setBounds((runCode.getX() + runCode.getWidth())	+ (runCode.getWidth() / 2),(menuTop.getHeight() / 2) - 20, 40, 40);
		manual.setBounds((animeCode.getX() + animeCode.getWidth())	+ (animeCode.getWidth() / 2),(menuTop.getHeight() / 2) - 20, 40, 40);
		
		txtnewCode.setBounds(txtnewCode.getX(), newCode.getY()+25, 25, 25);
		txtsaveCode.setBounds(txtsaveCode.getX(), saveCode.getY()+25, 30,25);
		txtrunCode.setBounds(txtrunCode.getX(), runCode.getY()+25, 50,25);
		txtanimeCode.setBounds(txtanimeCode.getX(), animeCode.getY()+25, 50,25);
		txtmanual.setBounds(txtmanual.getX(), manual.getY()+25, 50,25);
		
		code.setBounds(10, 10, menuRight.getWidth() - 20,(menuRight.getHeight() / 2) - 20);
		view.setBounds(10, (code.getY() + code.getHeight()) + 10,(menuRight.getWidth() / 2) - 10,(menuRight.getHeight() / 2) - 10);
		java.setBounds(view.getWidth() + 20,(code.getY() + code.getHeight()) + 10,(menuRight.getWidth() / 2) - 20,(menuRight.getHeight() / 2) - 10);
		
		elog.setLocation(menuTop.getWidth()/2 - elog.getWidth(), elog.getY());
		
		ops.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/404.png")).getImage().getScaledInstance(1100, 600, 4)));
		ops.setBounds(0, 0, menuRight.getWidth(), menuRight.getHeight());
//		ops.setLocation((menuRight.getWidth()/2) + 25, (menuRight.getHeight()/2) + 25);
		
		if (tabs.size() == 0) {
			code.setSize(0, 0);
			view.setSize(0, 0);
			java.setSize(0, 0);
			ops.setVisible(true);
		}

		scrollCode.setBounds(code.getBounds());
		scrollView.setBounds(view.getBounds());
		scrollJava.setBounds(java.getBounds());

		for (int i = 0; i < tabs.size(); i++) {
			tabs.get(i).atualizar();

			if (i == 0)
				tabs.get(i).setLocation((menuLeft.getX() + menuLeft.getWidth()),menuLeft.getY());
			else
				tabs.get(i).setLocation(
						tabs.get(i - 1).getX() + tabs.get(i - 1).getWidth(),menuLeft.getY());
		}

		arqs.setBounds(0, 0, menuLeft.getWidth(), 25);
		int y = 40;
		for (Arquivo arquivo : btnsArquivos) {
			arquivo.setBounds(2, y, menuLeft.getWidth()-4, 25);
			y += 30;
		}
		revalidate();
		repaint();
		java.revalidate();
		java.repaint();
		view.revalidate();
		view.repaint();
	}

	private void init() {

		// Alterando o Look and Feel da Janela
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Criando o menu do topo
		menuTop = new RoundedPanel(null, 5, new Color(255, 255, 255));
		menuTop.setOpaque(false);
		add(menuTop);
		
		// Criando os botoes do menu do topo
		newCode = new JButton();
		newCode.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/novo.png")).getImage().getScaledInstance(30, 30, 4)));
		newCode.setOpaque(false);
		newCode.setContentAreaFilled(false);
		newCode.setBorderPainted(false);
		newCode.setFocusable(false);
		newCode.setCursor(new Cursor(Cursor.HAND_CURSOR));
		newCode.addMouseListener(this);
		newCode.addActionListener(this);
		newCode.setToolTipText("Novo Arquivo");
		txtnewCode = new JLabel("Novo", JLabel.CENTER);
		txtnewCode.setFont(new Font("Arial", 0, 10));
		txtnewCode.setForeground(new Color(0, 79, 176));
		txtnewCode.setBounds((menuTop.getWidth() + 18), (menuTop.getHeight() + 30), 25, 25);
		menuTop.add(newCode);
		menuTop.add(txtnewCode);
		
		saveCode = new JButton();
		saveCode.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/salvar.png")).getImage().getScaledInstance(30, 30, 4)));
		saveCode.setOpaque(false);
		saveCode.setContentAreaFilled(false);
		saveCode.setBorderPainted(false);
		saveCode.setFocusable(false);
		saveCode.setCursor(new Cursor(Cursor.HAND_CURSOR));
		saveCode.addMouseListener(this);
		saveCode.addActionListener(this);
		saveCode.setToolTipText("Salvar");
		txtsaveCode = new JLabel("Salvar", JLabel.CENTER);
		txtsaveCode.setFont(new Font("Arial", 0, 10));
		txtsaveCode.setForeground(new Color(231, 151, 48));
		txtsaveCode.setBounds((menuTop.getWidth() + 75), (menuTop.getHeight() + 30), 30, 25);
		menuTop.add(txtsaveCode);		
		menuTop.add(saveCode);

		runCode = new JButton();
		runCode.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/play.png")).getImage().getScaledInstance(30, 30, 4)));
		runCode.setOpaque(false);
		runCode.setContentAreaFilled(false);
		runCode.setBorderPainted(false);
		runCode.setFocusable(false);
		runCode.setCursor(new Cursor(Cursor.HAND_CURSOR));
		runCode.addMouseListener(this);
		runCode.setToolTipText("Compilar");
		runCode.addActionListener(this);
		txtrunCode = new JLabel("Compilar", JLabel.CENTER);
		txtrunCode.setFont(new Font("Arial", 0, 10));
		txtrunCode.setForeground(new Color(56, 134, 50));
		txtrunCode.setBounds((menuTop.getWidth() + 122), (menuTop.getHeight() + 30), 50, 25);
		menuTop.add(txtrunCode);		
		menuTop.add(runCode);

		animeCode = new JButton();
		animeCode.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/robo.png")).getImage().getScaledInstance(30, 30, 4)));
		animeCode.setOpaque(false);
		animeCode.setContentAreaFilled(false);
		animeCode.setBorderPainted(false);
		animeCode.setFocusable(false);
		animeCode.setCursor(new Cursor(Cursor.HAND_CURSOR));
		animeCode.addMouseListener(this);
		animeCode.setToolTipText("Animar");
		animeCode.addActionListener(this);
		txtanimeCode = new JLabel("Animar", JLabel.CENTER);
		txtanimeCode.setFont(new Font("Arial", 0, 10));
		txtanimeCode.setForeground(new Color(238, 205, 74));
		txtanimeCode.setBounds((menuTop.getWidth() + 184), (menuTop.getHeight() + 30), 50, 25);
		menuTop.add(txtanimeCode);	
		menuTop.add(animeCode);
		
		manual = new JButton();
		manual.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/info.png")).getImage().getScaledInstance(30, 30, 4)));
		manual.setOpaque(false);
		manual.setContentAreaFilled(false);
		manual.setBorderPainted(false);
		manual.setFocusable(false);
		manual.setCursor(new Cursor(Cursor.HAND_CURSOR));
		manual.addMouseListener(this);
		manual.setToolTipText("Lista de Comandos");
		manual.addActionListener(this);
		txtmanual = new JLabel("Manual", JLabel.CENTER);
		txtmanual.setFont(new Font("Arial", 0, 10));
		txtmanual.setForeground(new Color(119, 46, 253));
		txtmanual.setBounds((menuTop.getWidth() + 245), (menuTop.getHeight() + 30), 50, 25);
		menuTop.add(txtmanual);	
		menuTop.add(manual);
		
//		Botão de Fechar
//		fecha = new JButton();
//		fecha.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/nclose.png")).getImage().getScaledInstance(40,40, 4)));
//		fecha.setCursor(new Cursor(Cursor.HAND_CURSOR));
//		fecha.setContentAreaFilled(false);
//		fecha.setBorderPainted(false);
//		fecha.setFocusable(false);
//		fecha.setBounds(40, 40, 40, 40);
//		fecha.setLocation(1310,10);
//		fecha.addMouseListener(new MouseListener() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				 System.exit(0);
//			}
//			@Override
//			public void mouseEntered(MouseEvent arg0) {
//				fecha.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/nclose-hover.png")).getImage().getScaledInstance(40, 40, 4)));
//			}
//			@Override
//			public void mouseExited(MouseEvent arg0) {
//			fecha.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/nclose.png")).getImage().getScaledInstance(40, 40, 4)));
//				}
//			@Override
//			public void mousePressed(MouseEvent arg0) {}
//			
//			@Override
//			public void mouseReleased(MouseEvent arg0) {}
//			});		
//			fecha.setVisible(true);
//			menuTop.add(fecha);

		//Logo Elog
		elog = new JLabel();
		elog.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/elog.png")).getImage().getScaledInstance(50, 25, 150)));
		elog.setBounds(50, 10, 50, 25);
		elog.setLocation(700,15);
		elog.setVisible(true);
		menuTop.add(elog);
				
		// Criando o menu da arvore
		menuLeft = new RoundedPanel(null, 10, new Color(255, 255, 255));
		menuLeft.setOpaque(false);
		add(menuLeft);

		// Criando o menu dos campos de texto
		menuRight = new RoundedPanel(null, 10, new Color(255, 255, 255));
		menuRight.setOpaque(false);
		add(menuRight);

		// Criando o campo de texto corretor
		view = new JTextPane();
		view.setBounds(getWidth() / 2,(menuTop.getY() + menuTop.getHeight()) + 2,(getWidth() / 2) - 36, getHeight() - 36);
		scrollView = new JScrollPane(view);
		view.setContentType("text/html");
		view.addKeyListener(this);
		//		view.addMouseListener(this);
		String htmlInit = "<html>"
				+ "<head>"
				+ "<title></title>"
				+ "<style>"
				+ "body{font-family: 'courier new'; font-color:'000000'; font-size:12px}"
				+ "</style>" + "</head>" + "<body></body></html>";
		view.setText(htmlInit);
		view.setEditable(false);

		// Criando o campo de texto do Java
		java = new JTextArea();
		scrollJava = new JScrollPane(java);
		java.setFont(new Font("courier new", 1, 12));
		java.setTabSize(2);
		java.setEditable(false);

		// Criando o campo de texto onde sera inserido o código
		code = new JTextArea();
		scrollCode = new JScrollPane(code);
		scrollCode.addKeyListener(this);
		code.addKeyListener(this);
		code.setText("iniciando");
		code.setFont(new Font("courier new", 0, 15));
		code.setTabSize(1);
		code.addCaretListener(this);
		TextLineNumber contLinesCode = new TextLineNumber(code);
		scrollCode.setRowHeaderView(contLinesCode);
		
		//Criando página 404
		ops = new JLabel();
		ops.setVisible(true);
		
		menuRight.add(scrollCode);
		menuRight.add(scrollView);
		menuRight.add(scrollJava);
		menuRight.add(ops);

		// Criando a arvore
		arqs.setBounds(0, 0, menuLeft.getWidth(), 25);
		arqs.setFont(new Font("Arial", 1, 16));
		arqs.setForeground(new Color(0,86,106));
		arqs.setHorizontalAlignment(SwingConstants.CENTER);
		menuLeft.add(arqs);

		int y = 40;
		for (File file : files) {
			StringBuffer text = new StringBuffer();
			try {
				BufferedReader br = new BufferedReader((new FileReader(file)));
				String linha = "";
				while ((linha = br.readLine()) != null) {
					text.append(linha + "\n");
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Arquivo arquivo = new Arquivo(file.getName(), path, contId++,text.toString());
			arquivo.setHorizontalAlignment(SwingConstants.LEFT);
			arquivo.setBounds(10, y, menuLeft.getWidth(), 25);
			menuLeft.add(arquivo);
			btnsArquivos.add(arquivo);
			y += 30;

		}
		// posicionando os componentes
		position();
		click();
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	public void click() {
		if ((int) code.getText().charAt(code.getText().length() - 1) > 32) {
			int caretP = code.getCaretPosition();
			code.insert("\n", code.getText().length());
			code.setCaretPosition(caretP);
		}

		view.setText(Corretor.getInstance().corrigirParaJava(code.getText(), "")[0]);
		//		view.setText(Corretor.getInstance().corrigirHtml(code.getText()));

		if (arquivoSelecionado != null) {
			java.setText(Corretor.getInstance().corrigirParaJava(code.getText(),arquivoSelecionado.nome.replaceAll(".elog", ""))[2]+"\t}\n}");
			arquivoSelecionado.conteudoDigitado = code.getText();
			ops.setVisible(false);
		}
	}

	public void setLineHighlight(int line) {
		highlighter = code.getHighlighter();
		((DefaultHighlighter) highlighter).setDrawsLayeredHighlights(false);
		String selected = code.getSelectedText();
		if (selected == null) {

			int startLine = 0, endLine = 0;
			try {
				startLine = code.getLineStartOffset(line);
				endLine = code.getLineEndOffset(line);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}

			highlighter.removeAllHighlights();

			try {
				highlighter.addHighlight(startLine, endLine, painter);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (!code.isFocusOwner()) {
			code.grabFocus();
			code.setCaretPosition(view.getCaretPosition() - 3);
		}
		click();

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(view)) {
			code.setCaretPosition(view.getCaretPosition() - 3);
			code.grabFocus();
		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource().equals(newCode)) {
			newCode.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/novo-hover.png")).getImage().getScaledInstance(30, 30, 4)));			
		} else if (e.getSource().equals(saveCode)) {
			saveCode.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/salvar-hover.png")).getImage().getScaledInstance(30, 30, 4)));
		} else if (e.getSource().equals(runCode)) {
			runCode.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/play-hover.png")).getImage().getScaledInstance(30,30, 4)));
		}else if (e.getSource().equals(animeCode)) {
			animeCode.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/robo-hover.png")).getImage().getScaledInstance(30, 30, 4)));
		}else if (e.getSource().equals(manual)) {
			manual.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/info-hover.png")).getImage().getScaledInstance(30, 30, 4)));
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource().equals(newCode)) {
			newCode.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/novo.png")).getImage().getScaledInstance(30,30, 4)));			
		} else if (e.getSource().equals(saveCode)) {
			saveCode.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/salvar.png")).getImage().getScaledInstance(30, 30, 4)));
		} else if (e.getSource().equals(runCode)) {
			runCode.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/play.png")).getImage().getScaledInstance(30, 30, 4)));
		}else if (e.getSource().equals(animeCode)) {
			animeCode.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/robo.png")).getImage().getScaledInstance(30, 30, 4)));
		}else if (e.getSource().equals(manual)) {
			manual.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/info.png")).getImage().getScaledInstance(30, 30, 4)));
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource().equals(view)) {
			code.setCaretPosition(view.getCaretPosition() - 3);
			code.setSelectionStart(view.getSelectionStart() - 3);
			code.setSelectionEnd(view.getSelectionEnd() - 3);
			code.grabFocus();
		}

	}

	@Override
	public void caretUpdate(CaretEvent e) {
		try {
			lineCaret = code.getLineOfOffset(code.getCaretPosition());
			setLineHighlight(lineCaret);
		} catch (BadLocationException exception) {
			exception.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(runCode)) {
			arquivoSelecionado.salvar();
			
			if(arquivoSelecionado != null){
				try {
					File file = new File(arquivoSelecionado.nome.replaceAll(".elog", ".java"));
					try {
//						System.out.println(Corretor.getInstance().corrigirParaJava(code.getText(),file.getName().replaceAll(".java", ""))[1]);
						FileWriter fw = new FileWriter(file);
						fw.append(Corretor.getInstance().corrigirParaJava(code.getText(),file.getName().replaceAll(".java", ""))[1]+""+"System.out.println();System.out.println();System.out.println("+'"'+"Press enter to continue..."+'"'+");System.in.read();\nfw.close();\n\t}\n}");	
						fw.close();
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
					String jdk = FindJDK.getPathForWindows();
					if(jdk.length()==0){
						JOptionPane.showMessageDialog(null, "JDK não encontrada");
					}else{
						new Thread(new Runnable() {
							public void run() {
								frameLoading = new JFrame();
								frameLoading.setLayout(null);
								frameLoading.setSize(getWidth()-14, getHeight()-36);
								frameLoading.getContentPane().setBackground(Color.white);
								frameLoading.setUndecorated(true);
								frameLoading.setOpacity(0.6f);
								
								frameLoading.setLocation(getX()+7, getY()+29);

								gif.setIcon(new ImageIcon(Tela.class.getResource("/images/loading.gif")));
								gif.setBounds((frameLoading.getWidth()/2)-(100), (frameLoading.getHeight()/2)-100, 200, 200);
								compilando.setBounds(0, (frameLoading.getHeight()/2)+100, getWidth(), 50);
								compilando.setHorizontalAlignment(SwingConstants.CENTER);
								
								frameLoading.add(gif);
								frameLoading.add(compilando);
								
								frameLoading.setVisible(true);
								frameLoading.setEnabled(true);
								compilar(jdk, file, false);
								try {Thread.sleep(3000);} catch (InterruptedException e2) {e2.printStackTrace();}
								frameLoading.setEnabled(false);
								frameLoading.setVisible(false);
							}
						}).start();

					}
				} catch (Exception e1) {

					e1.printStackTrace();
				}				
			}else{
				JOptionPane.showMessageDialog(null, "Selecione um arquivo para poder compilar", "Error", 0);
			}

		} else if (e.getSource().equals(saveCode)) {
			if(arquivoSelecionado != null){
				arquivoSelecionado.salvar();
				JOptionPane.showMessageDialog(null, "Salvo com sucesso");
			}else{
				JOptionPane.showMessageDialog(null, "Selecione um arquivo para poder salvar", "Error", 0);
			}

		} else if (e.getSource().equals(newCode)) {

			JFrame frame = new JFrame();
			frame.setBackground(Color.WHITE);
			frame.setSize(250,200);
			frame.setLocationRelativeTo(null);
			frame.setLayout(null);
			frame.setResizable(false);
			frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Tela.class.getResource("/images/icon.png")));
			

			JLabel txt = new JLabel("Digite o nome do arquivo:");
			txt.setBounds(0, 0, frame.getWidth(), 50);
			txt.setHorizontalAlignment(SwingConstants.CENTER);
			txt.setFont(new Font("Arial", 1, 15));
			txt.setForeground(new Color(0,86,106));

			frame.add(txt);

			JButton salvar = new JButton("Salvar");
		    salvar.setContentAreaFilled(false);
			salvar.setOpaque(true);
		    salvar.setFont(new Font("Arial", 0, 13));
			
			JTextField nome = new JTextField();
			nome.setBounds(10, 60, frame.getWidth() - 35, 20);
			
			frame.add(nome);
			nome.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent arg0) {

				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					if(nome.getText().equalsIgnoreCase("teste") || nome.getText().contains(" ") || nome.getText().contains("\n") || nome.getText().contains("\t") || nome.getText().equals("")){
						salvar.setEnabled(false);
					}else{
						if((int)nome.getText().charAt(0) >= 48 && (int)nome.getText().charAt(0) <= 57){
							salvar.setEnabled(false);
							
						}else{
							salvar.setBackground(new Color(0,86,106));
						    salvar.setForeground(Color.WHITE);
						    
							boolean flag=false;
							for (int j = 0; j <= 176; j++) {
								if(nome.getText().contains((char)j+"")){
									flag=true;
									salvar.setEnabled(false);
									break;
								}

								if(j == 31 || j == 43 || j == 8)
									j++;
								if(j == 47)
									j+=11;
								if(j == 64 || j == 96)
									j+=25;								

							}
							if(!flag){
								for (Arquivo arquivo : btnsArquivos) {
									if(arquivo.nome.replaceAll(".elog", "").equalsIgnoreCase(nome.getText())){
										flag = true;
										salvar.setEnabled(false);
										break;
									}
								}
								if(!flag)
									salvar.setEnabled(true);
							}
						}
					}				
				}

				@Override
				public void keyPressed(KeyEvent arg0) {
				}
			});

			salvar.setEnabled(false);
			salvar.setBounds(70, 105, frame.getWidth() - 150, 30);
			salvar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					Arquivo a = new Arquivo(nome.getText() + ".elog", path,
							contId++, "Variaveis\n\nInicio\n\nFim");
					a.setHorizontalAlignment(SwingConstants.LEFT);
					btnsArquivos.add(a);
					File file = new File(path + "/" + nome.getText() + ".elog");
					files.add(file);
					menuLeft.add(a);
					try {
						FileWriter fw = new FileWriter(file);
						fw.append("Variaveis\n\nInicio\n\nFim");
						fw.close();
					} catch (Exception e2) {
					}
					alterConsole = !alterConsole;
					frame.dispose();
					JOptionPane.showMessageDialog(null, "Arquivo criado com sucesso");
				}
			});
			frame.add(salvar);
			frame.setVisible(true);
		
		}else if(e.getSource().equals(animeCode)) {
			arquivoSelecionado.salvar();
			
			if(arquivoSelecionado != null){
				try {
					File file = new File(arquivoSelecionado.nome.replaceAll(".elog", ".java"));
					try {
//						System.out.println(Corretor.getInstance().corrigirParaJava(code.getText(),file.getName().replaceAll(".java", ""))[1]);
						FileWriter fw = new FileWriter(file);
						fw.append(Corretor.getInstance().corrigirParaJava(code.getText(),file.getName().replaceAll(".java", ""))[1]+""+"System.out.println();System.out.println();System.out.println("+'"'+"Press enter to continue..."+'"'+");System.in.read();\nfw.close();\n\t}\n}");	
						fw.close();
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
					String jdk = FindJDK.getPathForWindows();
					if(jdk.length()==0){
						JOptionPane.showMessageDialog(null, "JDK não encontrada");
					}else{
						new Thread(new Runnable() {
							public void run() {
								frameLoading = new JFrame();
								frameLoading.setLayout(null);
								frameLoading.setSize(getWidth()-14, getHeight()-36);
								frameLoading.getContentPane().setBackground(Color.white);
								frameLoading.setUndecorated(true);
								frameLoading.setOpacity(0.6f);
								
								frameLoading.setLocation(getX()+7, getY()+29);

								gif.setIcon(new ImageIcon(Tela.class.getResource("/images/loading.gif")));
								gif.setBounds((frameLoading.getWidth()/2)-(100), (frameLoading.getHeight()/2)-100, 200, 200);
								compilando.setBounds(0, (frameLoading.getHeight()/2)+100, getWidth(), 50);
								compilando.setHorizontalAlignment(SwingConstants.CENTER);
								
								frameLoading.add(gif);
								frameLoading.add(compilando);
								
								frameLoading.setVisible(true);
								frameLoading.setEnabled(true);
								compilar(jdk, file, true);
								try {Thread.sleep(3000);} catch (InterruptedException e2) {e2.printStackTrace();}
								frameLoading.setEnabled(false);
								frameLoading.setVisible(false);
							}
						}).start();

					}
				} catch (Exception e1) {

					e1.printStackTrace();
				}				
			}else{
				JOptionPane.showMessageDialog(null, "Selecione um arquivo para poder compilar", "Error", 0);
			}

		}else if(e.getSource().equals(manual)) {
			new Manual();
		}

	}
	
	public void compilar(String jdk, File file, boolean animar){
		new Thread(new Runnable() {
			public void run() {
				try {
					
					Runtime.getRuntime().exec(jdk+" "+file.getName());
					Thread.sleep(3000);
					Process pro = Runtime.getRuntime().exec("cmd /c start java "+file.getName().replaceAll(".java", ""));
					Thread.sleep(4000);
					
					new File(file.getName()).delete();
					new File(file.getName().replace(".java", ".class")).delete();
					
					if(animar){
						Animacao animacao = new Animacao();
					}
				} catch (Exception e2) {
					System.out.println();
				}
			
			}
		}).start();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		File f = new File("listaDeComandos.txt");
		if(f.exists()){
			f.delete();
		}
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
