package classes;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Tab extends JButton{
	int arquivo;
	boolean selecionado;
	int w;
	JButton close = new JButton();
	JLabel txt = new JLabel();
	JLabel icon;
	
	public Tab(String text, int arquivo) {
			this.arquivo = arquivo;
			setContentAreaFilled(false);
			setFocusable(false);
			setBorderPainted(false);
			setLayout(null);
			
			icon = new JLabel();
			icon.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/file.png")).getImage().getScaledInstance(16, 16, 4)));
			icon.setBounds(15, 8, 16, 16);
			add(icon);
			
			addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mousePressed(MouseEvent e) {}
				
				@Override
				public void mouseExited(MouseEvent e) {
					if(!selecionado)
						setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/abaNormal.png")).getImage().getScaledInstance(w, 40, 4)));
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					if(!selecionado)
						setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/abaHover.png")).getImage().getScaledInstance(w, 40, 4)));
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {	
					selecionar();
				}
			});
			
			txt.setSize(getWidth()-32,40);
			txt.setLocation(40, 2);
			txt.setText(text);
			add(txt);
			
			close.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/close.png")).getImage().getScaledInstance(20, 20, 4)));
			close.setContentAreaFilled(false);
			close.setCursor(new Cursor(Cursor.HAND_CURSOR));
			close.setBorderPainted(false);
			close.setFocusable(false);
			close.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mousePressed(MouseEvent e) {}
				
				@Override
				public void mouseExited(MouseEvent e) {
					close.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/close.png")).getImage().getScaledInstance(20, 20, 4)));
					if(!selecionado)
						setIcon(null);
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					close.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/close-hover.png")).getImage().getScaledInstance(20, 20, 4)));
					if(!selecionado)
						setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/abaHover.png")).getImage().getScaledInstance(w, 40, 4)));
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					remove();
				}
			});
			add(close);
			atualizar();
	}
	
	public void selecionar(){
		for (Tab tab : Tela.getInstance().tabs) {
			tab.selecionado = false;
			tab.setIcon(null);
		}
		
		setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/abaSelecionada.png")).getImage().getScaledInstance(w, 40, 4)));
		for (Tab tab : Tela.getInstance().tabs) {
			if(tab == this)
				selecionado = true;
			else{
				tab.selecionado = false;
				tab.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/abaNormal.png")).getImage().getScaledInstance(w, 40, 4)));
			}
		}

		for (int i = 0; i <Tela.getInstance().btnsArquivos.size(); i++) {
			if(Tela.getInstance().btnsArquivos.get(i).id == arquivo){
				Tela.getInstance().arquivoSelecionado = Tela.getInstance().btnsArquivos.get(i);
				Tela.getInstance().arquivoSelecionado.openContent();
				break;
			}
		}
		Tela.getInstance().tabSelecionado = this;
		revalidate();
		repaint();
		Tela.getInstance().repaint();
		Tela.getInstance().revalidate();
	}
	
	public void remove(){
		Tela.getInstance().tabs.remove(this);
		Tela.getInstance().remove(this);
		if(selecionado && Tela.getInstance().tabs.size() > 0)
			Tela.getInstance().tabs.get(0).selecionar();
	}
	
	public void atualizar(){
		setSize((Tela.getInstance().tabs.size()<=3)?200 : Tela.getInstance().menuRight.getWidth()/Tela.getInstance().tabs.size(), 40);
		w = getWidth()-10;
		setIcon(null);
		if(selecionado)
			setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/abaSelecionada.png")).getImage().getScaledInstance(w, 40, 4)));
		else
			setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/abaNormal.png")).getImage().getScaledInstance(w, 40, 4)));
		close.setBounds(getWidth()-40, 8, 20, 20);
		txt.setSize(getWidth()-35,30);
		
		Tela.getInstance().repaint();
		Tela.getInstance().revalidate();
	}
}
