package gui;

import javax.swing.*;
import java.awt.*;

public class Pic extends JPanel {
	private static final long serialVersionUID = 1L;
	Image image;
	public Pic(String str,int x,int y){
		super();
		image=new ImageIcon(str).getImage();
		this.setBounds(x,y,image.getWidth(this),image.getHeight(this));
		
	}
	public void paintComponent(Graphics g){
		g.drawImage(image, 0, 0, this);
	}
}
