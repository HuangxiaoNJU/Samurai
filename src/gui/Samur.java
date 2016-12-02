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
 * 一个武士对应一个JLabel 大小为300*300
 * 包括了所有的可能出现的该武士的动画
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
	
	String[] weaponPath=new String[4];//对应的武器的攻击动画的路径
	
	int direction;//武士的方向
	Pic[] samur=new Pic[4];//四个方向的角色本身的图像
	Pic down;
	List<Image>[] attackAction=new List[4];//用于存储武士攻击动画的List
	AtPlay[] attacking=new AtPlay[4];//用于表示武士攻击的动画层
	/*
	 * 层次：
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
		//设置给予的图层大小和初始位置以及布局管理器
		this.samurai=samurai;
		this.setLayout(null);
		this.setBounds(samurai.homeX*100-100,samurai.homeY*100-100,300,300);
		
		//添加设定武士的人物图片
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
		//添加武士的攻击动画
		
			//初始化调整用于播放动画的图层:
		for(int i=0;i<4;i++){	
			attacking[i]=new AtPlay();
			attacking[i].setLayout(null);
			attacking[i].setBounds(0,0,300,300);
		}
		this.weapon=samurai.weapon;
		switch(weapon){
		case 0://spear
			//添加面向北的动作
			attackAction[0]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[0].add(new ImageIcon("picture/char/attack/spear_north/"+i+".png").getImage());
			}
			//添加面向南的动作
			attackAction[1]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[1].add(new ImageIcon("picture/char/attack/spear_south/"+i+".png").getImage());
			}
			//添加面向西的动作
			attackAction[2]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[2].add(new ImageIcon("picture/char/attack/spear_west/"+i+".png").getImage());
			}
			//添加面向东的动作
			attackAction[3]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[3].add(new ImageIcon("picture/char/attack/spear_east/"+i+".png").getImage());
			}
			break;
		case 1://sword
			//添加面向北的动作
			attackAction[0]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[0].add(new ImageIcon("picture/char/attack/sword_north/"+i+".png").getImage());
			}
			//添加面向南的动作
			attackAction[1]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[1].add(new ImageIcon("picture/char/attack/sword_south/"+i+".png").getImage());
			}
			//添加面向西的动作
			attackAction[2]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[2].add(new ImageIcon("picture/char/attack/sword_west/"+i+".png").getImage());
			}
			//添加面向东的动作
			attackAction[3]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[3].add(new ImageIcon("picture/char/attack/sword_east/"+i+".png").getImage());
			}
			break;
		case 2://axe
			//添加面向北的动作
			attackAction[0]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[0].add(new ImageIcon("picture/char/attack/axe_north/"+i+".png").getImage());
			}
			//添加面向南的动作
			attackAction[1]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[1].add(new ImageIcon("picture/char/attack/axe_south/"+i+".png").getImage());
			}
			//添加面向西的动作
			attackAction[2]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[2].add(new ImageIcon("picture/char/attack/axe_west/"+i+".png").getImage());
			}
			//添加面向东的动作
			attackAction[3]=new ArrayList<Image>(180);
			for(int i=0;i<=180;i++){
				attackAction[3].add(new ImageIcon("picture/char/attack/axe_east/"+i+".png").getImage());
			}
			break;
		}
		
		//添加在请求指示时的各图层
		moveBlock[0]=new Pic_move("picture/gaming/move_choice.png",100,0,0);
		moveBlock[1]=new Pic_move("picture/gaming/move_choice.png",100,200,1);
		moveBlock[2]=new Pic_move("picture/gaming/move_choice.png",0,100,2);
		moveBlock[3]=new Pic_move("picture/gaming/move_choice.png",200,100,3);
		attackBlock[0]=new Pic_attack("picture/gaming/attack_choice_"+samurai.weapon+".png",100,0,0);
		attackBlock[1]=new Pic_attack("picture/gaming/attack_choice_"+samurai.weapon+".png",100,200,1);
		attackBlock[2]=new Pic_attack("picture/gaming/attack_choice_"+samurai.weapon+".png",0,100,2);
		attackBlock[3]=new Pic_attack("picture/gaming/attack_choice_"+samurai.weapon+".png",200,100,3);
		//添加图层
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
	//调用方法显示攻击
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
	//用于播放攻击动画的线程
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

	//调用方法表示移动
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
	//用于播放移动动画的线程
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

	//用于请求命令的部分
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
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO 自动生成的方法存根
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
			// TODO 自动生成的方法存根
			
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
