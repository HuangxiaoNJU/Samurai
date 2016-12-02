package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public abstract class EmpButton extends JLabel {
	Pic iniPic;
	Pic enterPic;
	
	public EmpButton(String ini,String enter,int x,int y){
		iniPic=new Pic(ini,0,0);
		enterPic=new Pic(enter,0,0);
		this.setLayout(null);
		setBounds(x,y,iniPic.getWidth(),iniPic.getHeight());
		iniPic.addMouseListener(new Enterlisten());
		enterPic.addMouseListener(new Clicklisten());
		add(iniPic);
		add(enterPic);
		this.setVisible(true);

	}
	
	public abstract void action();
	
	class Enterlisten implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {}
		public void mouseEntered(MouseEvent arg0) {
			iniPic.setVisible(false);
		}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
		
	}
	class Clicklisten implements MouseListener{
		public void mouseClicked(MouseEvent arg0) {
			
		}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {
			iniPic.setVisible(true);
		}
		public void mousePressed(MouseEvent arg0) {
			action();
		}
		public void mouseReleased(MouseEvent arg0) {}
		
	}
}
