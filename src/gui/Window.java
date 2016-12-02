package gui;

import javax.swing.*;

import dto.GameDto;
import gui.Gaming.mapMoveListener;
import service.GameService;
public class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel title;
	Gaming gaming;
	public GameDto dto;
	public GameService logic;
	

	public Window(Title titlein, Gaming gamein) {
		super();
		gaming = gamein;
		this.setTitle("Samurai");
		this.setSize(1280, 820);
		this.setResizable(false);
		this.setLayout(null);
		title = titlein.getTitle();
		gaming.setWindow(this);
		titlein.setWindow(this);
		this.add(gaming.gameface);
		this.add(title);
		gaming.gameface.setVisible(false);
		title.setVisible(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void toTitle() {
		SE.titlebgm.play();
		gaming.reset();
		gaming.gameface.setVisible(false);
		title.setVisible(true);

	}

	public void toGame() {
		SE.titlebgm.stop();
		newGame();
		title.setVisible(false);
		gaming.gameface.setVisible(true);
	}
	public void newGame(){
		//TODO
		dto.newGame();
		gaming.newGame();
	}
	public static void Op(){};
	
}
