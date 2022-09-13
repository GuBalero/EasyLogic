package classes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SelecionaPasta extends JFrame implements ActionListener {

	JLabel text;
	JLabel text2;
	JLabel bg;
	JFileChooser selectDir;
	JTextField tfDirSelected;
	JButton ret;
	File fileCurrent;
	JButton next;
	JLabel elog;
	JButton fecha;

	
	public SelecionaPasta() {
		setTitle("Easy Logic");
		setSize(600, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(SelecionaPasta.class.getResource("/images/icon.png")));
		setUndecorated(true);
		init();

		setVisible(true);
	}

	private void init() {

		// Alterando o Look and Feel da Janela
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//Logo Elog
		elog = new JLabel();
		elog.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/elog.png")).getImage().getScaledInstance(50, 25, 150)));
		elog.setBounds(50, 10, 50, 25);
		elog.setLocation(275,9);
		elog.setVisible(true);
		add(elog);
		
		//Botão de Fechar
		fecha = new JButton();
		fecha.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/close.png")).getImage().getScaledInstance(20, 20, 4)));
		fecha.setCursor(new Cursor(Cursor.HAND_CURSOR));
		fecha.setContentAreaFilled(false);
		fecha.setBorderPainted(false);
		fecha.setFocusable(false);
		fecha.setBounds(50, 10, 50, 20);
		fecha.setLocation(550,10);
		fecha.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				 System.exit(0);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				fecha.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/close-hover.png")).getImage().getScaledInstance(20, 20, 4)));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				fecha.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/close.png")).getImage().getScaledInstance(20, 20, 4)));
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});		
		fecha.setVisible(true);
		add(fecha);
		
		text = new JLabel("Selecione um diretório como Workspace para começar:", JLabel.LEFT);
		text.setFont(new Font("Arial", 1, 15));
		text.setForeground(new Color(0,86,106));
		text.setBounds(20, 50, getWidth(), 25);
		
		text2 = new JLabel("O EasyLogic utilizará o diretório selecionado para armazenar suas preferências e arquivos.", JLabel.LEFT);
		text2.setFont(new Font("Arial", 0, 12));
		text2.setForeground(new Color(13, 13, 13));
		text2.setBounds(25, 70, getWidth(), 25);
		
		add(text);
		add(text2);
		
		fileCurrent = new File (System.getProperty("user.home") + System.getProperty("file.separator")+ "Desktop");
		selectDir = new JFileChooser();
		selectDir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		selectDir.setCurrentDirectory(fileCurrent);

		tfDirSelected = new JTextField(System.getProperty("user.home") + System.getProperty("file.separator")+ "Desktop");
		tfDirSelected.setBounds(15, 100, getWidth() - 100, 20);
		tfDirSelected.setEditable(false);
		add(tfDirSelected);

		ret = new JButton("...");
		ret.setContentAreaFilled(false);
	    ret.setOpaque(true);
		ret.setBackground(new Color(0,137,186));
		ret.setForeground(Color.WHITE);
		ret.setFont(new Font("Arial", 0, 20));
		ret.setBounds((tfDirSelected.getX() + tfDirSelected.getWidth()) + 10, tfDirSelected.getY() - 1, 50,
				tfDirSelected.getHeight() + 2);
		ret.addActionListener(this);
		add(ret);
		
		next = new JButton("Continuar");
		next.setContentAreaFilled(false);
	    next.setOpaque(true);
		next.setBackground(new Color(0,86,106));
		next.setForeground(Color.WHITE);
		next.setFont(new Font("Arial", 0, 14));
		next.setSize(100 , 40);
		next.setLocation((getWidth()/2)-(next.getWidth()/2), 140);
		
		next.addActionListener(this);
		add(next);

		bg = new JLabel();
		bg.setBounds(0, 0, getWidth(), getHeight());
		bg.setBackground(Color.WHITE);
		bg.setOpaque(true);
		add(bg);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//escolher uma nova pasta
		if (e.getSource().equals(ret)) {
			selectDir.showOpenDialog(this);

			if(selectDir.getSelectedFile() != null) tfDirSelected.setText(selectDir.getSelectedFile().getPath());
		
		//continuar
		}else if(e.getSource().equals(next)) {
			if(tfDirSelected.getText().equals("Nenhuma pasta selecionada")) {
				JOptionPane.showMessageDialog(null, "Selecione uma pasta antes de prosseguir!");
			}else {
				ArrayList<File> files = new ArrayList<File>();
				if(selectDir.getSelectedFile()!=null){
					fileCurrent = selectDir.getSelectedFile();
				}
				for( File f : fileCurrent.listFiles()) {
					String aux="";
					for (int i = f.getName().length(), cont=0; i > 0; i--, cont++) {
						aux = f.getName().charAt(i-1)+aux;
						if(cont == 3)
							break;
					}
					if(aux.equalsIgnoreCase("elog"))
						files.add(f);
				}
				Tela.getInstance(fileCurrent.getPath(), files);
				dispose();
			}
		}

	}
}
