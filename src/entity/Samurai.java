package entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import gui.Samur;

public class Samurai {
	/*
	 * ��ʿ��ռ�õ�����ͼ��
	 */
	public Samur ofsamur;
	/*
	 * �������� 1���췽 2������
	 */
	public int team;
	/*
	 * ������λ������
	 */
	public int homeX;
	public int homeY;
	/*
	 * ������� 0:Spear 1:Sword 2:Battle ax
	 */
	public int weapon;
	/*
	 * ��ǰλ������
	 */
	public int curX;
	public int curY;
	/*
	 * ����ֵ
	 */
	public int cost;
	/*
	 * ���������
	 */
	public int maxCost;
	/*
	 * �ָ�����
	 */
	public int cure;
	/*
	 * ��һ��������Ҫ����Դ
	 */
	public int[] nextLvResourse;
	
	public Samurai(int team, int x, int y, int weapon) {
		this.team = team;
		this.homeX = x;
		this.homeY = y;
		this.weapon = weapon;
		this.curX = this.homeX;
		this.curY = this.homeY;
		this.cost = 7;
		this.maxCost=7;
		this.cure = 0;
		this.nextLvResourse = new int[]{5, 5, 5};
	}
	
	/*
	 * �ƶ�
	 */
	public void move(int direction) {
		switch (direction) {
		case 0: // ��
			curY --; break;
		case 1: // ��
			curY ++; break;
		case 2: // ��
			curX --; break;
		case 3: // ��
			curX ++; break;
		default: break;
		}
	}
	/*
	 * ��ռ�����ع�����Χ�漰���������꣬����������ͼ�߽�����꣩
	 */
	public List<Point> occupy(int direction) {
		// range���湥���漰������������
		List<Point> range = new ArrayList<Point>();
		// weapon = spear
		if(weapon == 0) {
			if(direction == 0 || direction == 1) {
				range.add(new Point(curX, (direction == 0 ? curY - 1 : curY + 1)));
				range.add(new Point(curX, (direction == 0 ? curY - 2 : curY + 2)));
				range.add(new Point(curX, (direction == 0 ? curY - 3 : curY + 3)));
				range.add(new Point(curX, (direction == 0 ? curY - 4 : curY + 4)));
			}
			else {
				range.add(new Point((direction == 2 ? curX - 1 : curX + 1), curY));
				range.add(new Point((direction == 2 ? curX - 2 : curX + 2), curY));
				range.add(new Point((direction == 2 ? curX - 3 : curX + 3), curY));
				range.add(new Point((direction == 2 ? curX - 4 : curX + 4), curY));
			}
		}
		// weapon = sword
		if(weapon == 1) {
			switch(direction) {
			case 0: range.add(new Point(curX - 1, curY - 1)); break;
			case 1: range.add(new Point(curX + 1, curY + 1)); break;
			case 2: range.add(new Point(curX - 1, curY + 1)); break;
			case 3: range.add(new Point(curX + 1, curY - 1)); break;
			}
			if(direction == 0 || direction == 3) {
				range.add(new Point(curX, curY - 1)); range.add(new Point(curX, curY - 2));
			}
			else {
				range.add(new Point(curX, curY + 1)); range.add(new Point(curX, curY + 2));
			}
			if(direction == 0 || direction == 2) {
				range.add(new Point(curX - 1, curY)); range.add(new Point(curX - 2, curY));
			}
			else {
				range.add(new Point(curX + 1, curY)); range.add(new Point(curX + 2, curY));
			}
		}
		// weapon = battleax
		if(weapon == 2) {
			for(int i = curX - 1; i <= curX + 1; i++) {
				for(int j = curY - 1; j <= curY + 1; j++) {
					if(i == curX && j == curY) continue;
					if(direction == 0 && i == curX && j == curY + 1) continue;
					if(direction == 1 && i == curX && j == curY - 1) continue;
					if(direction == 2 && i == curX + 1 && j == curY) continue;
					if(direction == 3 && i == curX - 1 && j == curY) continue;
					range.add(new Point(i, j));
				}
			}
		}
		return range;
	}
	/*
	 * ���س�����
	 */
	public void goHome() {
		curX = homeX;
		curY = homeY;
		cure = 12;
		ofsamur.gohome();
	}
}
