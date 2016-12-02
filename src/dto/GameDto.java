package dto;

import java.util.Random;

import ctrl.GameCtrl;
import gui.Map;

public class GameDto {
	/*
	 * �߼�ģ��
	 */
	public GameCtrl controller;
	/*
	 * ��Ϸ��ͼ��Ϣ
	 */
	public Map gameMap;
	/*
	 * ��Ϸ��ͼ��� TODO
	 */
	public static int MAP_WIDTH = 12;
	/*
	 * ��Ϸ��ͼ�߶� TODO
	 */
	public static int MAP_HEIGHT = 12;
	/*
	 * ��ʼ������ TODO
	 */
	public static final int INIT_COST = 7;
	/*
	 * �ƶ����ĵ������� TODO
	 */
	public static final int MOVE_COST = 2;
	/*
	 * ��ռ���ĵ������� TODO
	 */
	public static final int OCCUPY_COST = 3;
	/*
	 * �����ʼ������Դ�� TODO
	 */
	public static final int INIT_RESOURCE = 0;
	/*
	 * ÿ�غ����ӵ���Դ�� TODO
	 */
	public static final int INCREASE_RESOURCE = 1;
	/*
	 * ��Ϸ������ TODO
	 */
	public static int TOTAL_TURNS = 11;
	/*
	 * ��ǰ�غ���
	 */
	public int nowTurn;
	/*
	 * �췽������Ϣ
	 */
	public TeamDto teamRed;
	/*
	 * ����������Ϣ
	 */
	public TeamDto teamBlue;

	/*
	 * ���췽��
	 */
	public GameDto() {
		// �췽����������
		int[][] homeRed = { { 0, 0 }, { MAP_WIDTH % 2 != 0 ? MAP_WIDTH >> 1 : MAP_WIDTH - 1 >> 1, 0 },
				{ MAP_WIDTH - 1, 0 } };
		// ��������������
		int[][] homeBlue = { { 0, MAP_HEIGHT - 1 },
				{ MAP_WIDTH % 2 != 0 ? MAP_WIDTH >> 1 : MAP_WIDTH + 1 >> 1, MAP_HEIGHT - 1 },
				{ MAP_WIDTH - 1, MAP_HEIGHT - 1 } };

		// ������ɵ�ͼ
		Random random = new Random();
		int[][] mapInfo = new int[MAP_WIDTH][MAP_HEIGHT];
		for (int i = 0; i < MAP_WIDTH; i++) {
			for (int j = 0; j < MAP_HEIGHT; j++) {
				// ��ʿ�����㶼����Ϊƽԭ
				boolean ifHome = false;
				for (int k = 0; k < 3; k++) {
					if (i == homeRed[k][0] && j == homeRed[k][1] || i == homeBlue[k][0] && j == homeBlue[k][1]) {
						mapInfo[i][j] = 0;
						ifHome = true;
					}
				}
				if (!ifHome)
					mapInfo[i][j] = random.nextInt(3);
			}
		}

		// ��ǰ������ʼ��Ϊ1
		this.nowTurn = 1;
		// �����췽������������Ϣ���� TODO

		this.teamRed = new TeamDto(1, homeRed);

		this.teamBlue = new TeamDto(2, homeBlue);

		this.gameMap = new Map(mapInfo, this);
		// ���ó������ѱ����Զ���ռ��
		for (int i = 0; i < 3; i++) {
			this.gameMap.blocks[homeRed[i][0]][homeRed[i][1]].setHome(1);
			this.gameMap.blocks[homeRed[i][0]][homeRed[i][1]].changeBelong(1);
			this.gameMap.blocks[homeBlue[i][0]][homeBlue[i][1]].setHome(2);
			this.gameMap.blocks[homeBlue[i][0]][homeBlue[i][1]].changeBelong(2);

		}
	}

	public void newGame() {
		// �췽����������
		int[][] homeRed = { { 0, 0 }, { MAP_WIDTH % 2 != 0 ? MAP_WIDTH >> 1 : MAP_WIDTH - 1 >> 1, 0 },
				{ MAP_WIDTH - 1, 0 } };
		// ��������������
		int[][] homeBlue = { { 0, MAP_HEIGHT - 1 },
				{ MAP_WIDTH % 2 != 0 ? MAP_WIDTH >> 1 : MAP_WIDTH + 1 >> 1, MAP_HEIGHT - 1 },
				{ MAP_WIDTH - 1, MAP_HEIGHT - 1 } };

		// ������ɵ�ͼ
		Random random = new Random();
		int[][] mapInfo = new int[MAP_WIDTH][MAP_HEIGHT];
		for (int i = 0; i < MAP_WIDTH; i++) {
			for (int j = 0; j < MAP_HEIGHT; j++) {
				// ��ʿ�����㶼����Ϊƽԭ
				boolean ifHome = false;
				for (int k = 0; k < 3; k++) {
					if (i == homeRed[k][0] && j == homeRed[k][1] || i == homeBlue[k][0] && j == homeBlue[k][1]) {
						mapInfo[i][j] = 0;
						ifHome = true;
					}
				}
				if (!ifHome)
					mapInfo[i][j] = random.nextInt(3);
			}

		}
		// ��ǰ������ʼ��Ϊ1
		this.nowTurn = 1;
		// �����췽������������Ϣ���� TODO
		teamRed.reset();
		teamBlue.reset();
		for (int i = 0; i < 3; i++) {

			teamRed.samurais.get(i).homeX = homeRed[i][0];
			teamBlue.samurais.get(i).homeY = homeBlue[i][1];
			teamBlue.samurais.get(i).homeX = homeBlue[i][0];
			teamBlue.samurais.get(i).homeY = homeBlue[i][1];
			teamRed.samurais.get(i).curX = teamRed.samurais.get(i).homeX;
			teamRed.samurais.get(i).curY = teamRed.samurais.get(i).homeY;
			teamBlue.samurais.get(i).curX = teamBlue.samurais.get(i).homeX;
			teamBlue.samurais.get(i).curY = teamBlue.samurais.get(i).homeY;
		}

		gameMap.newGame(mapInfo);
		// ���ó������ѱ����Զ���ռ��
		for (int i = 0; i < 3; i++) {
			this.gameMap.blocks[homeRed[i][0]][homeRed[i][1]].setHome(1);
			this.gameMap.blocks[homeRed[i][0]][homeRed[i][1]].changeBelong(1);
			this.gameMap.blocks[homeBlue[i][0]][homeBlue[i][1]].setHome(2);
			this.gameMap.blocks[homeBlue[i][0]][homeBlue[i][1]].changeBelong(2);

		}
	}
}
