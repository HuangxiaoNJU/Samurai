package gui;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

import ctrl.GameCtrl;
import entity.Samurai;
import service.GameService;
import dto.*;

/*
 * һ����ʿ��Ӧһ��JLabel ��СΪ300*300
 * ���������еĿ��ܳ��ֵĸ���ʿ�Ķ���
 */
public class Samur extends JLabel {
	boolean active=false;
	public GameCtrl controller;
	public GameService logic;
	public Gaming ofgame;
	Samurai samurai;
	GameDto gdto;
	TeamDto tdto;
	Map ofmap;
	int weapon;
	boolean inAction=false;
	
	Pic_move[] moveBlock=new Pic_move[4];
	Pic_attack[] attackBlock=new Pic_attack[4];
	
	String[] weaponPath=new String[4];//��Ӧ�������Ĺ���������·��
	
	int direction;//��ʿ�ķ���
	Pic[] samur=new Pic[4];//�ĸ�����Ľ�ɫ�����ͼ��
	Pic down;
	List<Image>[] attackAction=new List[4];//���ڴ洢��ʿ����������List
	AtPlay[] attacking=new AtPlay[4];//���ڱ�ʾ��ʿ�����Ķ�����
	/*
	 * ��Σ�
	 * attacking[1]
	 * attacking[2]
	 * attacking[3]
	 * samur[0]
	 * samur[1]
	 * samur[2]
	 * samur[3]
	 * attacking[0]
	 * 
	 * attackBlock/moveBlock
	 */
	
	public Samur(Samurai samurai,Map map){
		ofmap=map;
		//���ø����ͼ���С�ͳ�ʼλ���Լ����ֹ�����
		this.samurai=samurai;
		this.setLayout(null);
		this.setBounds(samurai.homeX*100-100,samurai.homeY*100-100,300,300);
		
		//����趨��ʿ������ͼƬ
		if(samurai.team==1){
			for(int i=0;i<4;i++){
				samur[i]=new Pic("picture/char/red_"+i+".png",100,100);
				samur[i].setVisible(false);
			}
			down=new Pic("picture/char/red_down.png",100,100);
			samur[1].setVisible(true);
		}else{
			for(int i=0;i<4;i++){
				samur[i]=new Pic("picture/char/blue_"+i+".png",100,100);
				samur[i].setVisible(false);
			}
			down=new Pic("picture/char/blue_down.png",100,100);
			samur[0].setVisible(true);
		}
		for(int i=0;i<4;i++){
			samur[i].addMouseListener(new changeActionListen());
		}
		this.add(down);
		down.setVisible(false);
		//�����ʿ�Ĺ�������
		
			//��ʼ���������ڲ��Ŷ�����ͼ��:
		for(int i=0;i<4;i++){	
			attacking[i]=new AtPlay();
			attacking[i].setLayout(null);
			attacking[i].setBounds(0,0,300,300);
		}
		this.weapon=samurai.weapon;
		switch(weapon){
		case 0://spear
			//������򱱵Ķ���
			attackAction[0]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[0].add(new ImageIcon("picture/char/attack/spear_north/"+i+".png").getImage());
			}
			//��������ϵĶ���
			attackAction[1]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[1].add(new ImageIcon("picture/char/attack/spear_south/"+i+".png").getImage());
			}
			//����������Ķ���
			attackAction[2]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[2].add(new ImageIcon("picture/char/attack/spear_west/"+i+".png").getImage());
			}
			//������򶫵Ķ���
			attackAction[3]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[3].add(new ImageIcon("picture/char/attack/spear_east/"+i+".png").getImage());
			}
			break;
		case 1://sword
			//������򱱵Ķ���
			attackAction[0]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[0].add(new ImageIcon("picture/char/attack/sword_north/"+i+".png").getImage());
			}
			//��������ϵĶ���
			attackAction[1]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[1].add(new ImageIcon("picture/char/attack/sword_south/"+i+".png").getImage());
			}
			//����������Ķ���
			attackAction[2]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[2].add(new ImageIcon("picture/char/attack/sword_west/"+i+".png").getImage());
			}
			//������򶫵Ķ���
			attackAction[3]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[3].add(new ImageIcon("picture/char/attack/sword_east/"+i+".png").getImage());
			}
			break;
		case 2://axe
			//������򱱵Ķ���
			attackAction[0]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[0].add(new ImageIcon("picture/char/attack/axe_north/"+i+".png").getImage());
			}
			//��������ϵĶ���
			attackAction[1]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[1].add(new ImageIcon("picture/char/attack/axe_south/"+i+".png").getImage());
			}
			//����������Ķ���
			attackAction[2]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[2].add(new ImageIcon("picture/char/attack/axe_west/"+i+".png").getImage());
			}
			//������򶫵Ķ���
			attackAction[3]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[3].add(new ImageIcon("picture/char/attack/axe_east/"+i+".png").getImage());
			}
			break;
		}
		
		//���������ָʾʱ�ĸ�ͼ��
		moveBlock[0]=new Pic_move("picture/gaming/move_choice.png",100,0,0);
		moveBlock[1]=new Pic_move("picture/gaming/move_choice.png",100,200,1);
		moveBlock[2]=new Pic_move("picture/gaming/move_choice.png",0,100,2);
		moveBlock[3]=new Pic_move("picture/gaming/move_choice.png",200,100,3);
		attackBlock[0]=new Pic_attack("picture/gaming/attack_choice_"+samurai.weapon+".png",100,0,0);
		attackBlock[1]=new Pic_attack("picture/gaming/attack_choice_"+samurai.weapon+".png",100,200,1);
		attackBlock[2]=new Pic_attack("picture/gaming/attack_choice_"+samurai.weapon+".png",0,100,2);
		attackBlock[3]=new Pic_attack("picture/gaming/attack_choice_"+samurai.weapon+".png",200,100,3);
		//���ͼ��
		for(int i=1;i<4;i++){
			this.add(attacking[i]);
		}
		for(int i=0;i<4;i++){
			this.add(samur[i]);
		}
		this.add(attacking[0]);
		for(int i=0;i<4;i++){
			this.add(moveBlock[i]);
			this.add(attackBlock[i]);
			moveBlock[i].setVisible(false);
			attackBlock[i].setVisible(false);
		}
		samurai.ofsamur=this;
	}
	
	private void tiredcheck(){
		if(samurai.cost==0){
			for(int i=0;i<4;i++){
				moveBlock[i].setVisible(false);
				attackBlock[i].setVisible(false);
			}
		}
	}
	public void gohome(){
		this.setBounds(samurai.homeX*100-100,samurai.homeY*100-100,300,300);
		ofmap.setBounds(ofmap.getX(),ofmap.getY()-1,ofmap.getWidth(),ofmap.getHeight());
		ofmap.setBounds(ofmap.getX(),ofmap.getY()+1,ofmap.getWidth(),ofmap.getHeight());
		down();
	}
	//���÷�����ʾ����
	private void attack(int direction){
		if(!inAction){
			if(logic.canOccupy()){
				new AttackThread(direction).start();
				
			}
			
		}
		tiredcheck();
	}
	public void down(){
		for(int i=0;i<4;i++){
			samur[i].setVisible(false);
		}
		down.setVisible(true);
		ofmap.setBounds(ofmap.getX(),ofmap.getY()-1,ofmap.getWidth(),ofmap.getHeight());
		ofmap.setBounds(ofmap.getX(),ofmap.getY()+1,ofmap.getWidth(),ofmap.getHeight());
	}
	public void cure(){
		down.setVisible(false);
		samur[1].setVisible(true);
			ofmap.setBounds(ofmap.getX(),ofmap.getY()-1,ofmap.getWidth(),ofmap.getHeight());
			ofmap.setBounds(ofmap.getX(),ofmap.getY()+1,ofmap.getWidth(),ofmap.getHeight());
	}
	//���ڲ��Ź����������߳�
	class AttackThread extends Thread{
		int direction;
		public AttackThread(int direction){
			super();
			this.direction=direction;
		}
		public void run(){
			inAction=true;
			for(int i=0;i<4;i++){
				Samur.this.samur[i].setVisible(false);
			}
			Samur.this.samur[direction].setVisible(true);
			switch(weapon){
			case 0:
				SE.spear.play();
				break;
			case 1:
				SE.sword.play();
				break;
			case 2:
				SE.axe.play();
				break;
			}
				for(int i=0;i<=180;i++){
						
					Samur.this.attacking[direction].go(attackAction[direction].get(i));
					try{
						Thread.sleep(12);
					}catch(Exception e){};
				}
				Samur.this.controller.occupy(direction);
				inAction=false;
				ofgame.costboard.updatecost();
		}
	}

	//���÷�����ʾ�ƶ�
	private void move(int direction){
		if(!inAction){
			if(logic.canMove(direction)){
				new MoveThread(direction).start();
				controller.move(direction);
				ofgame.costboard.updatecost();
			}
		}
		tiredcheck();
	}
	//���ڲ����ƶ��������߳�
	class MoveThread extends Thread{
		int direction;
		public MoveThread(int direction){
			super();
			this.direction=direction;
		}
		public void run(){
			int jForMap=7;
			int x=Samur.this.getX();
			int y=Samur.this.getY();
			int mapX=ofmap.getX();
			int mapY=ofmap.getY();
			inAction=true;
			switch(direction){
			case 0:
				for(int i=10;i>=-4;i-=1){
					ofmap.setBounds(mapX,ofmap.getY()+7,ofmap.getWidth(),ofmap.getHeight());
					
					Samur.this.setLocation(x,y+((int)Math.pow(i, 2)-116));
					try{
						Thread.sleep(16);
					}catch(Exception e){};
				}
				Samur.this.setLocation(x,y-100);
				ofmap.setBounds(ofmap.getX(),ofmap.getY()-1,ofmap.getWidth(),ofmap.getHeight());
				ofmap.setBounds(ofmap.getX(),ofmap.getY()+1,ofmap.getWidth(),ofmap.getHeight());
				break;
			case 1:
				for(int i=-4;i<=10;i+=1){
					ofmap.setBounds(mapX,ofmap.getY()-7,ofmap.getWidth(),ofmap.getHeight());
					Samur.this.setLocation(x,y+((int)Math.pow(i, 2)-16));
					try{
						Thread.sleep(16);
					}catch(Exception e){};
				}
				Samur.this.setLocation(x,y+100);
				ofmap.setBounds(ofmap.getX(),ofmap.getY()-1,ofmap.getWidth(),ofmap.getHeight());
				ofmap.setBounds(ofmap.getX(),ofmap.getY()+1,ofmap.getWidth(),ofmap.getHeight());
				break;
			case 2:
				for(int i=-5;i<=5;i++){
					ofmap.setBounds(mapX+(5+i)*10,mapY,ofmap.getWidth(),ofmap.getHeight());
					Samur.this.setLocation(x-(5+i)*10,y-(25-(int)Math.pow(i, 2)));
					try{
						Thread.sleep(16);
					}catch(Exception e){};
				}
				Samur.this.setLocation(x-100,y);
				ofmap.setBounds(ofmap.getX(),ofmap.getY()-1,ofmap.getWidth(),ofmap.getHeight());
				ofmap.setBounds(ofmap.getX(),ofmap.getY()+1,ofmap.getWidth(),ofmap.getHeight());
				break;
			case 3:
				for(int i=-5;i<=5;i++){
					ofmap.setBounds(mapX-(5+i)*10,mapY,ofmap.getWidth(),ofmap.getHeight());
					Samur.this.setLocation(x+(5+i)*10,y-(25-(int)Math.pow(i, 2)));
					try{
						Thread.sleep(16);
					}catch(Exception e){};
				}
				Samur.this.setLocation(x+100,y);
				ofmap.setBounds(ofmap.getX(),ofmap.getY()-1,ofmap.getWidth(),ofmap.getHeight());
				ofmap.setBounds(ofmap.getX(),ofmap.getY()+1,ofmap.getWidth(),ofmap.getHeight());
				break;
			}
			SE.step.play();
			inAction=false;
		}
	}

	//������������Ĳ���
	public void order(){
		ofmap.add(this,0);
		for(int i=0;i<4;i++){
			moveBlock[i].setVisible(true);
		}
	}
	class Pic_move extends Pic{
		int direction;
		public Pic_move(String str,int x,int y,int direction){
			super(str,x,y);
			this.direction=direction;
			this.addMouseListener(new moveListen());
		}
		class moveListen implements MouseListener{

			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
			public void mouseEntered(MouseEvent e) {
				if(!Samur.this.inAction){
					for(int i=0;i<4;i++){
						Samur.this.samur[i].setVisible(false);
					}
					Samur.this.samur[direction].setVisible(true);
				}
				ofmap.setBounds(ofmap.getX(),ofmap.getY()-1,ofmap.getWidth(),ofmap.getHeight());
				ofmap.setBounds(ofmap.getX(),ofmap.getY()+1,ofmap.getWidth(),ofmap.getHeight());
			}
			public void mouseExited(MouseEvent e) {
				
			}
			public void mousePressed(MouseEvent e) {
				Samur.this.move(direction);
			}
			public void mouseReleased(MouseEvent e) {
				
			}
			
		}
	}
	class Pic_attack extends Pic{
		int direction;
		public Pic_attack(String str,int x,int y,int direction){
			super(str,x,y);
			this.direction=direction;
			this.addMouseListener(new attackListen());
		}
		class attackListen implements MouseListener{

			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
			public void mouseEntered(MouseEvent e) {
				if(!Samur.this.inAction){
				for(int i=0;i<4;i++){
					Samur.this.samur[i].setVisible(false);
				}
				Samur.this.samur[direction].setVisible(true);
				}
				ofmap.setBounds(ofmap.getX(),ofmap.getY()-1,ofmap.getWidth(),ofmap.getHeight());
				ofmap.setBounds(ofmap.getX(),ofmap.getY()+1,ofmap.getWidth(),ofmap.getHeight());
			}
			public void mouseExited(MouseEvent e) {
				
			}
			public void mousePressed(MouseEvent e) {
				Samur.this.attack(direction);
			}
			public void mouseReleased(MouseEvent e) {
				
			}
			
		}
	}
	class changeActionListen implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO �Զ����ɵķ������
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO �Զ����ɵķ������
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO �Զ����ɵķ������
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO �Զ����ɵķ������
			if(e.getButton()==MouseEvent.BUTTON3&&samurai.cure==0&&active){
				if(attackBlock[1].isVisible()){
					SE.click.play();
					for(int i=0;i<4;i++){
						attackBlock[i].setVisible(false);
						moveBlock[i].setVisible(true);
					}
				}else{
					SE.click.play();
					for(int i=0;i<4;i++){
						attackBlock[i].setVisible(true);
						moveBlock[i].setVisible(false);
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO �Զ����ɵķ������
			
		}
		
	}
}
class AtPlay extends JLabel{
	Image image;
	public void paintComponent(Graphics g){
		g.drawImage(image, 0, 0, this);
	}
	public void go(Image i){
		image=i;
		this.repaint();
	}
}
