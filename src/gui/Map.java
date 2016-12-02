package gui;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ctrl.GameCtrl;
import dto.GameDto;
public class Map extends JPanel{
	GameDto dto;
	JPanel ofpanel;
	
	public Block[][] blocks;
	public Conditionpage condition;
	public Samur[][] samurs;
	
	public Map(int[][] serial,GameDto dto) {
		samurs=new Samur[2][3];
		this.dto=dto;
		for(int j=0;j<3;j++){
			samurs[0][j]=new Samur(dto.teamRed.samurais.get(j),this);
			samurs[1][j]=new Samur(dto.teamBlue.samurais.get(j),this);
		}
		
		condition=new Conditionpage();
		this.setLayout(null);
		this.add(condition);
		for(int i=0;i<2;i++){
			for(int j=0;j<3;j++){
				this.add(samurs[i][j]);
			}
		}
		this.setBounds(200, 200, serial.length * 100, serial[0].length * 100);
		blocks = new Block[serial.length][serial[0].length];
		for (int i = 0; i < serial.length; i++) {
			for (int j = 0; j < serial[0].length; j++) {
				blocks[i][j] = new Block(serial[i][j], i, j,this);
				this.add(blocks[i][j].land);
			}
		}
		this.addMouseMotionListener(new dragListen());
	}
	public void moveToCenter(Samur samur){
		new moveToCenterThread(samur).start();
	}
	public void newGame(int[][] serial){
		this.setBounds(200, 200, serial.length * 100, serial[0].length * 100);
		for(int i=0;i<blocks.length;i++){
			for(int j=0;j<blocks[0].length;j++){
				this.remove(blocks[i][j].land);
			}
		}
		blocks = null;
		blocks = new Block[serial.length][serial[0].length];
		for (int i = 0; i < serial.length; i++) {
			for (int j = 0; j < serial[0].length; j++) {
				
				blocks[i][j] = new Block(serial[i][j], i, j,this);
				this.add(blocks[i][j].land);
			}
		}

			for(int j=0;j<3;j++){
				samurs[0][j].samurai=dto.teamRed.samurais.get(j);
				samurs[1][j].samurai=dto.teamBlue.samurais.get(j);
			}
		for(int i=0;i<2;i++){
			for(int j=0;j<3;j++){
				samurs[i][j].setLocation(samurs[i][j].samurai.homeX*100-100,samurs[i][j].samurai.homeY*100-100);
			}
		}
	}
	class moveToCenterThread extends Thread{
		Samur samur;
		public moveToCenterThread(Samur samur){
			super();
			this.samur=samur;
		}
		public void run(){
			int speedX=(Map.this.getX()-(510-samur.getX()))/30;
			int speedY=(Map.this.getY()-(300-samur.getY()))/30;
				for(int j=0;j<30;j++){
					Map.this.setBounds(Map.this.getX()-speedX,Map.this.getY()-speedY,Map.this.getWidth(),Map.this.getHeight());
					try{
						Thread.sleep(16);
					}catch(Exception e){};
				}
		}
	}

	class Conditionpage extends JPanel implements MouseListener{
		Pic bc=new Pic("picture/gaming/instru.png",0,0);
		JLabel name;
		JLabel source;
		JLabel belong;
		Font word = new Font("宋体", Font.BOLD, 30);
		Font word_ = new Font("宋体", Font.BOLD, 12);
		Font word__ = new Font("宋体", Font.BOLD, 24);
		public Conditionpage(){
			this.setLayout(null);
			this.setBounds(300,300,100,100);
			this.setVisible(false);
			name=new JLabel("");
			source=new JLabel("");
			belong=new JLabel("无");
			belong.setFont(word__);
			name.setFont(word);
			source.setFont(word_);
			belong.setBounds(65,36,30,25);
			name.setBounds(6, 9, 70, 27);
			source.setBounds(7, 78, 40, 15);
			this.add(belong);
			this.add(name);
			this.add(source);
			this.add(bc);
			this.addMouseListener(this);
		}
		public void showup(int x,int y,int type,int belong){
			switch(type){
			case 0:
				name.setText("平原");
				source.setText("食物");
				break;
			case 1:
				name.setText("森林");
				source.setText("木材");
				break;
			case 2:
				name.setText("山地");
				source.setText("生铁");
				break;
			}
			switch(belong){
			case 0:
				this.belong.setText("无");
				break;
			case 1:
				this.belong.setText("红");
				break;
			case 2:
				this.belong.setText("蓝");
				break;
			}
			this.setBounds(x*100,y*100,100,100);
			this.setVisible(true);
		}
		public void mouseClicked(MouseEvent e) {
			this.setVisible(false);
		}
		public void mouseEntered(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {
			this.setVisible(false);
		}
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
	class dragListen implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO 自动生成的方法存根
			Map.this.setLocation(arg0.getX(),arg0.getY());
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO 自动生成的方法存根
			
		}
		
	}
}
