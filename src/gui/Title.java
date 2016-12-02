package gui;

import java.awt.event.*;
import java.applet.AudioClip;
import java.awt.*;
import javax.swing.*;

public class Title {
	Window window;
	JPanel titlesurface;
	JPanel option_page;
	Pic background = new Pic("Picture/title/bg.png", 0, 0);
	Pic logo = new Pic("picture/title/icon.png", 600, 110);

	// 开始建立option界面
	Optionpage optionpage = new Optionpage();

	public void setWindow(Window window) {
		this.window = window;
	}

	public JPanel getTitle() {
		return titlesurface;
	}

	public Title() {
		titlesurface = new JPanel();
		titlesurface.setBounds(0, 0, 1280, 820);
		titlesurface.setLayout(null);
		option_page = optionpage.getPanel();
		titlesurface.add(option_page);
		titlesurface.add(new startbutton());
		titlesurface.add(new loadbutton());
		titlesurface.add(new optionbutton());
		titlesurface.add(new endbutton());
		titlesurface.add(logo);
		titlesurface.add(background);

	}

	public void playbgm() {
		SE.titlebgm.play();
	}

	public void stopbgm() {
		SE.titlebgm.stop();
	}

	class startbutton extends EmpButton {
		public startbutton() {
			super("picture/title/start.png", "picture/title/start_enter.png", 25, 200);
		}

		public void action() {
			SE.titlebgm.stop();
			SE.ka.play();
			try{
				Thread.sleep(1000);
			}catch(Exception e){};
			window.toGame();
		}
	}

	class loadbutton extends EmpButton {
		public loadbutton() {
			super("picture/title/load.png", "picture/title/load_enter.png", 25, 325);
		}

		public void action() {

		}
	}

	class optionbutton extends EmpButton {
		public optionbutton() {
			super("picture/title/option.png", "picture/title/option_enter.png", 25, 450);
		}

		public void action() {
			if (option_page.isVisible()) {
				option_page.setVisible(false);
				SE.optionflag_.play();
			} else {
				option_page.setVisible(true);
				SE.optionflag.play();
			}
		}
	}

	class endbutton extends EmpButton {
		public endbutton() {
			super("picture/title/end.png", "picture/title/end_enter.png", 25, 575);
		}

		public void action() {
			titlesurface.setVisible(false);
			stopbgm();
			window.dispose();
		}
	}
}

class Optionpage {
	JPanel page;
	Font num = new Font("造字工房童心（非商用）常规体", Font.PLAIN, 30);
	JLabel bgmnum = new JLabel("10");
	JLabel semnum = new JLabel("10");
	Pic shoter = new Pic("Picture/title/关闭符.png", 425, 24);
	Pic shoter_ = new Pic("Picture/title/关闭符enter.png", 425, 24);
	Pic bg = new Pic("Picture/title/optionpage.png", 0, 0);
	Pic ele1 = new Pic("Picture/title/frame.png", 205, 337);
	Pic ele2 = new Pic("Picture/title/frame.png", 205, 396);
	Pic ele3 = new Pic("Picture/title/frame.png", 205, 458);
	Pic gou1 = new Pic("Picture/title/picked.png", 210, 329);
	Pic gou2 = new Pic("Picture/title/picked.png", 210, 388);
	Pic gou3 = new Pic("Picture/title/picked	.png", 210, 450);
	boolean g1 = true, g2 = true, g3 = true;
	int width = 10, height = 10;

	public Optionpage() {
		bgmnum.setFont(num);
		semnum.setFont(num);
		bgmnum.setBounds(150, 118, 43, 36);
		semnum.setBounds(150, 232, 43, 36);
		// new 一个
		page = new JPanel();
		page.setLayout(null);

		// 增添图片组件、设置组件的监听
		page.add(ele1);
		ele1.addMouseListener(new MouseEle1());
		page.add(ele2);
		ele2.addMouseListener(new MouseEle2());
		page.add(ele3);
		ele3.addMouseListener(new MouseEle3());
		page.add(gou1);
		page.add(gou2);
		page.add(gou3);
		page.add(new shoter());
		page.add(new bgmplus());
		page.add(new bgmde());
		page.add(new seplus());
		page.add(new sede());

		page.add(bgmnum);
		page.add(semnum);

		page.add(bg);

		page.setBounds(390, 30, 500, 730);
		// 设置初始的可视性
		page.setVisible(false);
	}

	public JPanel getPanel() {
		return page;
	}

	class MouseEle1 implements MouseListener {
		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {

			if (gou1.isVisible()) {
				gou1.setVisible(false);
				g1 = false;
			} else {
				gou1.setVisible(true);
				g1 = true;
			}
		}

	}

	class MouseEle2 implements MouseListener {
		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
			if (gou2.isVisible()) {
				gou2.setVisible(false);
				g2 = false;
			} else {
				gou2.setVisible(true);
				g2 = true;
			}
		}
	}

	class MouseEle3 implements MouseListener {
		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
			if (gou3.isVisible()) {
				gou3.setVisible(false);
				g3 = false;
			} else {
				gou3.setVisible(true);
				g3 = true;
			}
		}
	}

	class shoter extends EmpButton {
		public shoter() {
			super("picture/title/close.png", "picture/title/close_enter.png", 425, 24);
		}

		public void action() {
			SE.optionflag_.play();
			page.setVisible(false);
			dto.GameDto.MAP_WIDTH=width;
			dto.GameDto.MAP_HEIGHT=height;
			dto.GameDto.TOTAL_TURNS=60*(int)Math.sqrt(width*height)/10+1;
		}
	}

	class bgmplus extends EmpButton {
		public bgmplus() {
			super("picture/title/R.png", "picture/title/R_enter.png", 250, 114);
		}

		public void action() {
			if (width != 30) {
				width++;
				bgmnum.setText(String.valueOf(width));
			}
		}
	}

	class bgmde extends EmpButton {
		public bgmde() {
			super("picture/title/L.png", "picture/title/L_enter.png", 67, 114);
		}

		public void action() {
			if (width != 10) {
				width -= 1;
				bgmnum.setText(String.valueOf(width));
			}
		}
	}

	class seplus extends EmpButton {
		public seplus() {
			super("picture/title/R.png", "picture/title/R_enter.png", 250, 228);
		}

		public void action() {
			if (height != 30) {
				height += 1;
				semnum.setText(String.valueOf(height));
			}
		}
	}

	class sede extends EmpButton {
		public sede() {
			super("picture/title/L.png", "picture/title/L_enter.png", 67, 228);
		}

		public void action() {
			if (height != 10) {
				height -= 1;
				semnum.setText(String.valueOf(height));
			}
		}
	}
}
