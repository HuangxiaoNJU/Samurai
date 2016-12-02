package dto;

import java.util.Random;

import ctrl.GameCtrl;
import gui.Map;

public class GameDto {
	/*
	 * 逻辑模组
	 */
	public GameCtrl controller;
	/*
	 * 游戏地图信息
	 */
	public Map gameMap;
	/*
	 * 游戏地图宽度 TODO
	 */
	public static int MAP_WIDTH = 12;
	/*
	 * 游戏地图高度 TODO
	 */
	public static int MAP_HEIGHT = 12;
	/*
	 * 初始体力数 TODO
	 */
	public static final int INIT_COST = 7;
	/*
	 * 移动消耗的体力数 TODO
	 */
	public static final int MOVE_COST = 2;
	/*
	 * 攻占消耗的体力数 TODO
	 */
	public static final int OCCUPY_COST = 3;
	/*
	 * 队伍初始各类资源数 TODO
	 */
	public static final int INIT_RESOURCE = 0;
	/*
	 * 每回合增加的资源数 TODO
	 */
	public static final int INCREASE_RESOURCE = 1;
	/*
	 * 游戏总轮数 TODO
	 */
	public static int TOTAL_TURNS = 11;
	/*
	 * 当前回合数
	 */
	public int nowTurn;
	/*
	 * 红方队伍信息
	 */
	public TeamDto teamRed;
	/*
	 * 蓝方队伍信息
	 */
	public TeamDto teamBlue;

	/*
	 * 构造方法
	 */
	public GameDto() {
		// 红方出生点坐标
		int[][] homeRed = { { 0, 0 }, { MAP_WIDTH % 2 != 0 ? MAP_WIDTH >> 1 : MAP_WIDTH - 1 >> 1, 0 },
				{ MAP_WIDTH - 1, 0 } };
		// 蓝方出生点坐标
		int[][] homeBlue = { { 0, MAP_HEIGHT - 1 },
				{ MAP_WIDTH % 2 != 0 ? MAP_WIDTH >> 1 : MAP_WIDTH + 1 >> 1, MAP_HEIGHT - 1 },
				{ MAP_WIDTH - 1, MAP_HEIGHT - 1 } };

		// 随机生成地图
		Random random = new Random();
		int[][] mapInfo = new int[MAP_WIDTH][MAP_HEIGHT];
		for (int i = 0; i < MAP_WIDTH; i++) {
			for (int j = 0; j < MAP_HEIGHT; j++) {
				// 武士出生点都设置为平原
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

		// 当前轮数初始化为1
		this.nowTurn = 1;
		// 创建红方和蓝方队伍信息对象 TODO

		this.teamRed = new TeamDto(1, homeRed);

		this.teamBlue = new TeamDto(2, homeBlue);

		this.gameMap = new Map(mapInfo, this);
		// 设置出生点已被各自队伍占领
		for (int i = 0; i < 3; i++) {
			this.gameMap.blocks[homeRed[i][0]][homeRed[i][1]].setHome(1);
			this.gameMap.blocks[homeRed[i][0]][homeRed[i][1]].changeBelong(1);
			this.gameMap.blocks[homeBlue[i][0]][homeBlue[i][1]].setHome(2);
			this.gameMap.blocks[homeBlue[i][0]][homeBlue[i][1]].changeBelong(2);

		}
	}

	public void newGame() {
		// 红方出生点坐标
		int[][] homeRed = { { 0, 0 }, { MAP_WIDTH % 2 != 0 ? MAP_WIDTH >> 1 : MAP_WIDTH - 1 >> 1, 0 },
				{ MAP_WIDTH - 1, 0 } };
		// 蓝方出生点坐标
		int[][] homeBlue = { { 0, MAP_HEIGHT - 1 },
				{ MAP_WIDTH % 2 != 0 ? MAP_WIDTH >> 1 : MAP_WIDTH + 1 >> 1, MAP_HEIGHT - 1 },
				{ MAP_WIDTH - 1, MAP_HEIGHT - 1 } };

		// 随机生成地图
		Random random = new Random();
		int[][] mapInfo = new int[MAP_WIDTH][MAP_HEIGHT];
		for (int i = 0; i < MAP_WIDTH; i++) {
			for (int j = 0; j < MAP_HEIGHT; j++) {
				// 武士出生点都设置为平原
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
		// 当前轮数初始化为1
		this.nowTurn = 1;
		// 创建红方和蓝方队伍信息对象 TODO
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
		// 设置出生点已被各自队伍占领
		for (int i = 0; i < 3; i++) {
			this.gameMap.blocks[homeRed[i][0]][homeRed[i][1]].setHome(1);
			this.gameMap.blocks[homeRed[i][0]][homeRed[i][1]].changeBelong(1);
			this.gameMap.blocks[homeBlue[i][0]][homeBlue[i][1]].setHome(2);
			this.gameMap.blocks[homeBlue[i][0]][homeBlue[i][1]].changeBelong(2);

		}
	}
}
