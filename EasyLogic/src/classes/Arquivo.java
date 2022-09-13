package classes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Arquivo extends JButton implements ActionListener , MouseListener{
	
	String nome;
	String path;
	String conteudo;
	String conteudoDigitado;
	JLabel icon;
	int id;
	
	public Arquivo(String nome, String path, int id, String conteudo){
		this.nome = nome;
		this.path = path;
		this.id = id;
		this.conteudo = conteudo;
		
		icon = new JLabel();
		icon.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/file.png")).getImage().getScaledInstance(16, 16, 4)));
		icon.setBounds(0, 1, 16, 16);
		add(icon);
		
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusable(false);
		
		setBackground(new Color(155, 195, 204));
		setText("       "+nome);
		
		addActionListener(this);
		addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(Tela.getInstance().tabs.size()<10){
			boolean flag=false;
			for (Tab tab : Tela.getInstance().tabs) {
				if(tab.arquivo == id){
					flag = true;
					tab.selecionar();
					break;
				}
			}
			if(!flag){
				Tab t = new Tab(nome, id);
				Tela.getInstance().tabs.add(t);
				Tela.getInstance().add(t);
				t.selecionar();
				for (Tab tab : Tela.getInstance().tabs) {
					tab.atualizar();
				}
			}
		}else{
			JOptionPane.showMessageDialog(null, "Há muitos arquivos abertos!\n\t\tTente fechar alguns.", "Error Tab", 0);
		}			
	}
	
	public void openContent(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(conteudoDigitado == null)
			Tela.getInstance().code.setText(conteudo);
		else{
			boolean flag = false;
			for (Tab tab : Tela.getInstance().tabs) {
				if(tab.arquivo == id){
					flag = true;
					break;
				}
			}
			if(flag)
				Tela.getInstance().code.setText(conteudoDigitado);
			else
				Tela.getInstance().code.setText(conteudo);
		}
		
		
		Tela.getInstance().click();
		
		
	}

	public void salvar(){
		conteudo = conteudoDigitado;
		File file = new File(path+"/"+nome);
		try {
			FileWriter fw = new FileWriter(file);
			fw.append(conteudo);	
			fw.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setOpaque(true);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setOpaque(false);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}