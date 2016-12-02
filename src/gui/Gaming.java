package gui;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import javax.swing.JLayeredPane;

import ctrl.GameCtrl;
import dto.GameDto;
import entity.Samurai;
import gui.Gaming.mapMoveListener;
import service.GameService;

public class Gaming{
	Font font=new Font("Chiller",Font.BOLD,50);
	 public GameDto dto;
	 public GameCtrl controller;
	 public GameService logic;
	 
	 int actTeam=1;
	 JLabel red=new JLabel();
	 JLabel blue=new JLabel();
	 JLabel turn=new JLabel();
	 JLabel nowIron=new JLabel();
	 JLabel nowLog=new JLabel();
	 JLabel nowFood=new JLabel();
	 
	Window ofwindow;
	
	turnControllBoard tcb;
	JLayeredPane gameface;
	costBoard costboard;
	Systempage system;
	LevelUp levelup;
	
	boolean isTutorEnd=false;
	
	Pic top = new Pic("Picture/gaming/titantop.png", 0, 0);
	Pic bc = new Pic("Picture/gaming/图层 1.png", 0, 0);
	JLabel ball_blue = new JLabel(new ImageIcon("Picture/gaming/blueball.gif"));
	JLabel ball_red = new JLabel(new ImageIcon("Picture/gaming/redball.gif"));

	public Gaming(GameDto dto) {
		this.dto = dto;
		
		
		gameface = new JLayeredPane();
		system = new Systempage();
		costboard=new costBoard();
		levelup=new LevelUp();
		
		gameface.setBounds(0, 0, 1280, 800);
		gameface.setLayout(null);
		
		ball_red.setBounds(580, -16, 120, 120);
		ball_blue.setBounds(580, -16, 120, 120);
		
		//对顶部数据的预设
		red.setFont(font);
		blue.setFont(font);
		turn.setFont(font);
		nowIron.setFont(font);
		nowLog.setFont(font);
		nowFood.setFont(font);
		red.setBounds(85,15,80,50);
		blue.setBounds(245,15,80,50);
		turn.setBounds(470,15,80,50);
		nowIron.setBounds(800,15,100,50);
		nowLog.setBounds(1155,15,100,50);
		nowFood.setBounds(980,15,100,50);
		red.setText("0");
		blue.setText("0");
		turn.setText(String.valueOf(GameDto.TOTAL_TURNS-1));
		nowIron.setText("0");
		nowLog.setText("0");
		nowFood.setText("0");
		
		top.setLayout(null);
		top.add(red);
		top.add(blue, 0);
		top.add(turn, 0);
		top.add(nowIron, 0);
		top.add(nowLog, 0);
		top.add(nowFood, 0);
		
		gameface.add(top,1);
		gameface.add(ball_red,2);
		gameface.add(ball_blue,3);
		ball_blue.addMouseListener(new ballListen());
		ball_red.addMouseListener(new ballListen());
		//为得分画面保留第4层
		gameface.add(system,5);
		tcb=new turnControllBoard();
		gameface.add(levelup, 6);
		gameface.add(tcb,7);
		gameface.add(costboard,8);
		gameface.add(dto.gameMap,9);
		gameface.add(bc,10);
		
	}

	public void setWindow(Window window) {
		this.ofwindow = window;
	}

	public JLayeredPane getPanel() {
		return gameface;
	}

	public void reset() {
		ofwindow.addKeyListener(new mapMoveListener());
		system.reset();

	}
	//接下来是游戏流程的部分OAO
	public void getOrder(Samur samur){
		samur.order();
	}
	public void finishturn(){
		for(int i=0;i<4;i++){
			logic.actSamurai(dto.nowTurn).ofsamur.attackBlock[i].setVisible(false);
			logic.actSamurai(dto.nowTurn).ofsamur.moveBlock[i].setVisible(false);
		}
		tcb.picker.setVisible(false);
		switch(controller.finish()){
		case 1:
			// TODO 得分统计
			turn.setText("0");
			gameface.add(new finishPage(),4);
			break;
		case 0:
			if(logic.actSamurai(dto.nowTurn).cure==0)
				getOrder(logic.actSamurai(dto.nowTurn).ofsamur);
			
			dto.gameMap.moveToCenter(logic.actSamurai(dto.nowTurn).ofsamur);
			actTeam=logic.actSamurai(dto.nowTurn).team;
			new ballChangeThread().start();
			turn.setText(String.valueOf(GameDto.TOTAL_TURNS-dto.nowTurn));
			
			
			costboard.updatecost();
			updateTop();
			logic.actSamurai(dto.nowTurn-1).ofsamur.active=false;
			logic.actSamurai(dto.nowTurn).ofsamur.active=true;
			SE.dong.play();
		}
	}
	public void updateTop(){
		red.setText(String.valueOf(dto.teamRed.score));
		blue.setText(String.valueOf(dto.teamBlue.score));
		switch(actTeam){
		case 1:
			nowIron.setText(String.valueOf(dto.teamRed.iron));
			nowLog.setText(String.valueOf(dto.teamRed.wood));
			nowFood.setText(String.valueOf(dto.teamRed.food));
			break;
		case 2:
			nowIron.setText(String.valueOf(dto.teamBlue.iron));
			nowLog.setText(String.valueOf(dto.teamBlue.wood));
			nowFood.setText(String.valueOf(dto.teamBlue.food));
			break;
		}
	}
	public void newGame(){
		gameface.add(new tutorPage(), 0);
		turn.setText(String.valueOf(GameDto.TOTAL_TURNS-dto.nowTurn));
		updateTop();
	}
	
	class ballChangeThread extends Thread{
		public void run(){
			switch(actTeam){
			case 2:
				if(ball_red.getY()==-136)
					break;
				for(int i=0;i<20;i++){
					ball_red.setBounds(580,ball_red.getY()-6,120,120);
					//ball_blue.setBounds(580,ball_red.getY()+6,120,120);
					try{
						Thread.sleep(16);
					}catch(Exception e){};
				}
				break;
			case 1:
				if(ball_red.getY()==-16)
					break;
				for(int i=0;i<20;i++){
					//ball_blue.setBounds(580,ball_red.getY()-6,120,120);
					ball_red.setBounds(580,ball_red.getY()+6,120,120);
					try{
						Thread.sleep(16);
					}catch(Exception e){};
				}
				break;
			}
		}
	}
	
	class moveMapUpThread extends Thread{
		public void run(){
			for(int i=-30;i<=0;i+=1){
				dto.gameMap.setLocation(dto.gameMap.getX(),dto.gameMap.getY()+(int)(Math.pow(i, 2)/45));
				try{
					Thread.sleep(10);
				}catch(Exception e){};
			}
		}
	}
	class moveMapDownThread extends Thread{
		public void run(){
			for(int i=-30;i<=0;i+=1){
				dto.gameMap.setLocation(dto.gameMap.getX(),dto.gameMap.getY()-(int)(Math.pow(i, 2)/45));
				try{
					Thread.sleep(10);
				}catch(Exception e){};
			}
		}
	}
	class moveMapLeftThread extends Thread{
		public void run(){
			for(int i=-30;i<=0;i+=1){
				dto.gameMap.setLocation(dto.gameMap.getX()+(int)(Math.pow(i, 2)/45),dto.gameMap.getY());
				try{
					Thread.sleep(10);
				}catch(Exception e){};
			}
		}
	}
	class moveMapRightThread extends Thread{
		public void run(){
			for(int i=-30;i<=0;i+=1){
				dto.gameMap.setLocation(dto.gameMap.getX()-(int)(Math.pow(i, 2)/45),dto.gameMap.getY());
				try{
					Thread.sleep(10);
				}catch(Exception e){};
			}
		}
	}

	class Systempage extends JLayeredPane{
		Pic bc = new Pic("Picture/gaming/system.png", 0, 0);
		Pic up = new Pic("Picture/gaming/uper.png", 34, 30);
		Pic down = new Pic("Picture/gaming/downer.png", 34, 30);

		public Systempage() {
			down.setVisible(false);
			up.addMouseListener(new uplisten());
			down.addMouseListener(new downlisten());
			this.setVisible(true);
			this.setLayout(null);
			this.setBounds(75, 735, 152, 440);
			this.add(up);
			this.add(down);
			this.add(new titlebutton());
			this.add(bc);
		}

		public void reset() {
			setBounds(75, 735, 152, 440);
			up.setVisible(true);
			down.setVisible(false);
		}
		
		class downlistenthread extends Thread {
			public void run() {
				for (int i = 1; i <= 375; i += 5) {
					setBounds(75, 360 + i, 152, 440);
					try {
						Thread.sleep(4);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		class uplistenthread extends Thread {
			public void run() {
				
				for (int i = 1; i <= 375; i += 5) {
					setBounds(75, 735 - i, 152, 440);
					try {
						Thread.sleep(4);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			}
		}


		class uplisten implements MouseListener {
			public void mouseClicked(MouseEvent e) {
				up.setVisible(false);
				down.setVisible(true);
				new uplistenthread().start();
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

		}

		class downlisten implements MouseListener {
			public void mouseClicked(MouseEvent e) {
				up.setVisible(true);
				down.setVisible(false);
				new downlistenthread().start();
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		}
	}

	
	class titlebutton extends EmpButton{

		public titlebutton() {
			super("picture/gaming/title.png", "picture/gaming/title_enter.png", 23, 217);
			
		}
		public void action(){
			ofwindow.toTitle();
			for(int i=0;i<4;i++){
				logic.actSamurai(dto.nowTurn).ofsamur.attackBlock[i].setVisible(false);
				logic.actSamurai(dto.nowTurn).ofsamur.moveBlock[i].setVisible(false);
			}
			system.reset();
		}
	}
	class mapMoveListener implements KeyListener{
		public void keyPressed(KeyEvent arg0) {
			// TODO 自动生成的方法存根
				
		}
		public void keyReleased(KeyEvent arg0) {
			// TODO 自动生成的方法存根
			if(arg0.getKeyChar()==' '){
				dto.gameMap.moveToCenter(logic.actSamurai(dto.nowTurn).ofsamur);
			}
		}
		public void keyTyped(KeyEvent ke) {
			// TODO 自动生成的方法存根
		
				switch (ke.getKeyChar()){
				case 'w' :
					if(dto.gameMap.getY()<200)
						new moveMapUpThread().start();
					break;			
				case 's' :
					if(dto.gameMap.getY()>-dto.gameMap.getHeight()+700)
						new moveMapDownThread().start();
					break;
				case 'a' :
					if(dto.gameMap.getX()<200)
						new moveMapLeftThread().start();
					break;
				case 'd' :
					if(dto.gameMap.getX()>-dto.gameMap.getWidth()+1000)
						new moveMapRightThread().start();
					break;
					
				}
			
		}
	}
	class ballListen implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO 自动生成的方法存根
			if(!logic.isGameOver()&&isTutorEnd)
				finishturn();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO 自动生成的方法存根
			if(isTutorEnd)
				tcb.hidethread();
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
	class turnControllBoard extends JLabel{
		boolean hiden=false;
		
		Font font=new Font("Chiller Regular",Font.PLAIN,30);
		Pic bc=new Pic("picture/gaming/controllboard/bc.png",0,0);
		Pic picker=new Pic("picture/gaming/controllboard/picked.png",30,70);
		public turnControllBoard(){
			this.setLayout(null);
			this.setBounds(396,70,488,124);
			this.addMouseListener(new ControlBoardListener());
			this.add(picker);
			this.add(new attackButton());
			this.add(new upLevelButton());
			this.add(new moveButton());
			
			this.add(bc);
			
		}
		public void hidethread(){
			new hideThread().start();
		}
		class attackButton extends EmpButton{
			public attackButton(){
				super("picture/gaming/controllboard/attack.png","picture/gaming/controllboard/attack.png",16,52);
			}
			public void action(){
				picker.setBounds(30,70,picker.getWidth(),picker.getHeight());
				picker.setVisible(true);
				if(logic.actSamurai(dto.nowTurn).cost!=0){
					for(int i=0;i<4;i++){
						logic.actSamurai(dto.nowTurn).ofsamur.attackBlock[i].setVisible(true);
						logic.actSamurai(dto.nowTurn).ofsamur.moveBlock[i].setVisible(false);
					}
				}
				dto.gameMap.setBounds(dto.gameMap.getX(),dto.gameMap.getY()-1,dto.gameMap.getWidth(),dto.gameMap.getHeight());
				dto.gameMap.setBounds(dto.gameMap.getX(),dto.gameMap.getY()+1,dto.gameMap.getWidth(),dto.gameMap.getHeight());
				hidethread();
			}
		}
		class upLevelButton extends EmpButton{
			public upLevelButton(){
				super("picture/gaming/controllboard/levelup.png","picture/gaming/controllboard/levelup.png",184,52);
			}
			public void action(){
					levelup.getready();
				}
			}
		class moveButton extends EmpButton{
			public moveButton(){
				super("picture/gaming/controllboard/move.png","picture/gaming/controllboard/move.png",352,52);
			}
			public void action(){
				picker.setBounds(366,70,picker.getWidth(),picker.getHeight());
				picker.setVisible(true);
				if(logic.actSamurai(dto.nowTurn).cost!=0){
					for(int i=0;i<4;i++){
						logic.actSamurai(dto.nowTurn).ofsamur.moveBlock[i].setVisible(true);
						logic.actSamurai(dto.nowTurn).ofsamur.attackBlock[i].setVisible(false);
					}
				}
				dto.gameMap.setBounds(dto.gameMap.getX(),dto.gameMap.getY()-1,dto.gameMap.getWidth(),dto.gameMap.getHeight());
				dto.gameMap.setBounds(dto.gameMap.getX(),dto.gameMap.getY()+1,dto.gameMap.getWidth(),dto.gameMap.getHeight());
				hidethread();
			}
		}
		class hideThread extends Thread{
			public void run(){
				if(!hiden){
						while(turnControllBoard.this.getY()>=(-20)){
							turnControllBoard.this.setBounds(396,turnControllBoard.this.getY()-10,488,124);
							try{
								Thread.sleep(16);
						}catch(Exception e){};
						}
						hiden=true;
					}else{
						while(turnControllBoard.this.getY()<=70){
							turnControllBoard.this.setBounds(396,turnControllBoard.this.getY()+10,488,124);
							try{
								Thread.sleep(16);
						}catch(Exception e){};
						}
						hiden=false;
					}
				}
			}
		class ControlBoardListener implements MouseListener{

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				new hideThread().start();
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

	class costBoard extends JLabel{
		Pic bc=new Pic("picture/gaming/costBoard/bc.png",0,0);
		Font font=new Font("Chiller",Font.BOLD,30);
		public JLabel cost=new JLabel(),nowCost=new JLabel();
		public costBoard(){
			this.setBounds(555,70,170,124);
			this.setLayout(null);
			nowCost.setBounds(29, 62, 40, 35);
			cost.setBounds(100, 62, 40, 35);
			nowCost.setFont(font);
			cost.setFont(font);
			cost.setText(String.valueOf(7));
			nowCost.setText(String.valueOf(7));
			this.add(cost);
			this.add(nowCost);
			this.add(bc);
		}
		public void updatecost(){
			if(logic.actSamurai(dto.nowTurn).cure==0){
			nowCost.setText(String.valueOf(logic.actSamurai(dto.nowTurn).cost));
			cost.setText(String.valueOf(logic.actSamurai(dto.nowTurn).maxCost));
			}else{
				nowCost.setText(String.valueOf(logic.actSamurai(dto.nowTurn).cure));
				cost.setText("12");
			}
		}
	}
	class LevelUp extends JLabel{
		Pic bc=new Pic("picture/gaming/leveluppage/bc.png",0,0);
		JLabel iron=new JLabel("0");
		JLabel log=new JLabel("0");
		JLabel food=new JLabel("0");
		JLabel unable=new JLabel("资源不足");
		
		OKbutton okbutton=new OKbutton();
		Cancelbutton cancelbutton=new Cancelbutton();
		
		public LevelUp(){
			super();
			this.setLayout(null);
			this.setBounds(396,250,bc.getWidth(),bc.getHeight());
			iron.setFont(costboard.font);
			log.setFont(costboard.font);
			food.setFont(costboard.font);
			unable.setFont(new Font("宋体",Font.PLAIN,20));
			iron.setBounds(75,170,100,40);
			log.setBounds(385,170,100,40);
			food.setBounds(230,170,100,40);
			unable.setBounds(80,252,100,40);
			this.add(iron);
			this.add(food);
			this.add(log);
			this.add(okbutton);
			this.add(cancelbutton);
			this.add(unable);
			this.add(bc);
			this.setVisible(false);
		}
		public void getready(){
			int ironfore=0;
			int foodfore=0;
			int logfore=0;
			Samurai samurai=logic.actSamurai(dto.nowTurn);
			switch(samurai.weapon){
			case 0://spear
				foodfore=GameService.function(logic.actSamurai(dto.nowTurn).maxCost);
				logfore=GameService.function(logic.actSamurai(dto.nowTurn).maxCost);
				break;
			case 1://sword
				logfore=GameService.function(logic.actSamurai(dto.nowTurn).maxCost);
				ironfore=GameService.function(logic.actSamurai(dto.nowTurn).maxCost);
				break;
			case 2://axe
				foodfore=GameService.function(logic.actSamurai(dto.nowTurn).maxCost);
				ironfore=GameService.function(logic.actSamurai(dto.nowTurn).maxCost);
				break;
			}
			iron.setText(String.valueOf(ironfore));
			food.setText(String.valueOf(foodfore));
			log.setText(String.valueOf(logfore));
			if(!logic.canUpdate()){
				unable.setVisible(true);
				okbutton.setVisible(false);
			}else{
				unable.setVisible(false);
				okbutton.setVisible(true);
			}
			this.setVisible(true);
		}
		class OKbutton extends EmpButton{
			public OKbutton(){
				super("picture/gaming/leveluppage/pick.png","picture/gaming/leveluppage/pick_enter.png",96,252);
			}

			@Override
			public void action() {
				// TODO 自动生成的方法存根
				logic.update();
				LevelUp.this.setVisible(false);
				updateTop();
			}
		}
		class Cancelbutton extends EmpButton{
			public Cancelbutton(){
				super("picture/gaming/leveluppage/X.png","picture/gaming/leveluppage/X_enter.png",321,238);
			}

			@Override
			public void action() {
				// TODO 自动生成的方法存根
				LevelUp.this.setVisible(false);
			}
		}
	}
	class finishPage extends JPanel{
		/*
		 * 领地的分得分，[team][part]
		 * team: 0=red 1=blue
		 * part: 0=plain 1=wood 2=hill
		 */
		JLabel[][] partScore=new JLabel[2][3];
		JLabel[] totalScore=new JLabel[2];
		
		Pic bc=new Pic("picture/gaming/finish/bc.png",0,0);
		public finishPage(){
			partScore[0][0]=new JLabel(String.valueOf(dto.teamRed.mountain));
			partScore[0][0].setBounds(305,190,60,30);
			partScore[0][1]=new JLabel(String.valueOf(dto.teamRed.plain));
			partScore[0][1].setBounds(530, 190, 60, 30);
			partScore[0][2]=new JLabel(String.valueOf(dto.teamRed.forest));
			partScore[0][2].setBounds(750, 190, 60, 30);
			partScore[1][0]=new JLabel(String.valueOf(dto.teamBlue.mountain));
			partScore[1][0].setBounds(305, 375, 60, 30);
			partScore[1][1]=new JLabel(String.valueOf(dto.teamBlue.plain));
			partScore[1][1].setBounds(530, 375, 60, 30);
			partScore[1][2]=new JLabel(String.valueOf(dto.teamBlue.forest));
			partScore[1][2].setBounds(750, 375, 60, 30);
			totalScore[0]=new JLabel(String.valueOf(dto.teamRed.score));
			totalScore[0].setBounds(950, 190, 60, 30);
			totalScore[1]=new JLabel(String.valueOf(dto.teamBlue.score));
			totalScore[1].setBounds(950, 375, 60, 30);
			this.add(new againButton());
			this.add(new titleButton());
			this.setBounds(-5,800,1280,695);
			this.setLayout(null);
			for(int i=0;i<2;i++){
				for(int j=0;j<3;j++){
					partScore[i][j].setFont(new Font("Chiller",Font.PLAIN,30));
					this.add(partScore[i][j]);
				}
				totalScore[i].setFont(new Font("Chiller",Font.PLAIN,30));
				this.add(totalScore[i]);
			}
			this.add(bc);
			new appearThread().start();
			
			
		}
		class appearThread extends Thread{
			public void run(){
				for(int i=0;i<=30;i++){
					finishPage.this.setLocation(-5,(1000-(int)Math.pow(i, 2)));
					try{
						Thread.sleep(16);
					}catch(Exception e){};
				}
				for(int i=5;i>=0;i--){
					finishPage.this.setLocation(-5,(125-(int)Math.pow(i, 2)));
					try{
						Thread.sleep(16);
					}catch(Exception e){};
				}
				for(int i=0;i<=5;i++){
					finishPage.this.setLocation(-5,(125-(int)Math.pow(i, 2)));
					try{
						Thread.sleep(16);
					}catch(Exception e){};
				}
			}
		}
		class againButton extends EmpButton{
			public againButton(){
				super("picture/gaming/finish/again.png","picture/gaming/finish/again_enter.png",978,539);
			}

			@Override
			public void action() {
				// TODO 自动生成的方法存根
				finishPage.this.setVisible(false);
				ofwindow.toGame();
			}
		}
		class titleButton extends EmpButton{
			public titleButton(){
				super("picture/gaming/finish/title.png","picture/gaming/finish/title_enter.png",978,588);
			}

			@Override
			public void action() {
				// TODO 自动生成的方法存根
				finishPage.this.setVisible(false);
				ofwindow.toTitle();
			}
		}
			
	}
	class tutorPage extends JLabel{

		Pic[] step=new Pic[2];
		
		public tutorPage(){
			step[0]=new Pic("picture/gaming/tutor/step_0.png",0,0);
			step[1]=new Pic("picture/gaming/tutor/battlestart.png",416,248);
			this.setLayout(null);
			this.setBounds(0,105,1280,695);
			this.add(step[0]);
			this.add(step[1]);
			step[1].setVisible(false);
			this.addMouseListener(new clickListen());
		}
		class startgo extends Thread{
			public void run(){
				step[1].setVisible(true);
				try{
					Thread.sleep(1000);
				}catch(Exception e){};
				isTutorEnd=true;
				tutorPage.this.setVisible(false);
				dto.gameMap.moveToCenter(logic.actSamurai(dto.nowTurn).ofsamur);
				getOrder(logic.actSamurai(dto.nowTurn).ofsamur);
			}
			
		}
		class clickListen implements MouseListener{

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				step[0].setVisible(false);
				new startgo().start();
				logic.actSamurai(dto.nowTurn).ofsamur.active=true;
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
}

	
