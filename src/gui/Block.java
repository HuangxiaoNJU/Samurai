package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Block{
	Map ofmap;
	public int type;// 0=平原 1=森林 2=山地
	public int belong = 0;// 0=无阵营 1=红方 2=蓝方
	JLayeredPane land = new JLayeredPane();
	Pic icon;
	Pic blueborder = new Pic("picture/map/borderblue.png", 0, 0);
	Pic redborder = new Pic("picture/map/borderred.png", 0, 0);
	Pic redFlag=new Pic("picture/map/redFlag.png",0,0);
	Pic blueFlag=new Pic("picture/map/blueFlag.png",0,0);
	int x,y;

	boolean haveSamurai=false;
	
	public Block(int type, int x, int y,Map map) {
		ofmap=map;
		this.x=x;
		this.y=y;
		this.type = type;
		switch (type) {
		case 0:
			icon = new Pic("picture/map/plain.jpg", 0, 0);
			break;
		case 1:
			icon = new Pic("picture/map/wood.jpg", 0, 0);
			break;
		case 2:
			icon = new Pic("picture/map/hill.jpg", 0, 0);
			break;
		}
		land.addMouseListener(new clickListener());
		land.setLayout(null);
		land.setBounds(x * 100, y * 100, 100, 100);
		land.add(blueborder,1);
		land.add(redborder,2);
		land.add(icon,4);
		
		blueborder.setVisible(false);
		redborder.setVisible(false);
	}

	public void newSelf(int i){
		this.type=i;
		switch (i) {
		case 0:
			icon = new Pic("picture/map/plain.jpg", 0, 0);
			break;
		case 1:
			icon = new Pic("picture/map/wood.jpg", 0, 0);
			break;
		case 2:
			icon = new Pic("picture/map/hill.jpg", 0, 0);
			break;
		}
		land.remove(icon);
		land.add(icon, 4);
	}
	
	public void changeBelong(int i) {
		this.belong = i;
		switch (i) {
		case 0:
			blueborder.setVisible(false);
			redborder.setVisible(false);
			break;
		case 1:
			blueborder.setVisible(false);
			redborder.setVisible(true);
			break;
		case 2:
			blueborder.setVisible(true);
			redborder.setVisible(false);
			break;
		}
		
	}
	public void setHome(int team){

		switch(team){
		case 1:
			this.land.add(redFlag,3);
			land.remove(icon);
			break;
		case 2:
			this.land.add(blueFlag,3);
			land.remove(icon);
			break;
		}
	}
	class clickListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO 自动生成的方法存根
			if(arg0.getButton()==MouseEvent.BUTTON1){
				ofmap.condition.showup(x, y, type,belong);
				ofmap.setBounds(ofmap.getX(),ofmap.getY()-1,ofmap.getWidth(),ofmap.getHeight());
				ofmap.setBounds(ofmap.getX(),ofmap.getY()+1,ofmap.getWidth(),ofmap.getHeight());
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO 自动生成的方法存根
			
		}
		
	}
}
