package service;

import java.awt.Point;
import java.util.List;

import dto.GameDto;
import dto.TeamDto;
import entity.Samurai;

public class GameService {

	/*
	 * 游戏数据源
	 */
	public GameDto dto;

	public GameService(GameDto dto) {
		this.dto = dto;
	}

	/*
	 * 判断当前回合所行动的武士 一次循环：A0 B0 B1 A1 A2 B2 B0 A0 A1 B1 B2 A2
	 */
	public Samurai actSamurai(int turn) {
		switch (turn % 12) {
		case 1: case 8: return dto.teamRed.samurais.get(0); // A0
		case 4: case 9: return dto.teamRed.samurais.get(1); // A1
		case 0: case 5: return dto.teamRed.samurais.get(2); // A2
		case 2: case 7: return dto.teamBlue.samurais.get(0); // B0
		case 3: case 10: return dto.teamBlue.samurais.get(1); // B1
		case 6: case 11: return dto.teamBlue.samurais.get(2); // B2
		default: return null;
		}
	}

	/*
	 * 判断是否可以移动 direction 0:up 1:down 2:left 3:right
	 */
	public boolean canMove(int direction) {
		// 获得当前回合行动的武士对象
		Samurai actSamurai = actSamurai(dto.nowTurn);
		// 如果武士正在治疗则无法移动
		if (actSamurai.cure != 0) return false;
		// 如果体力不够则无法移动
		if (actSamurai.cost < GameDto.MOVE_COST) return false;
		// 移动后的新位置坐标（初始化为当前坐标）
		int newX = actSamurai.curX;
		int newY = actSamurai.curY;
		switch (direction) {
		case 0: // 上
			newY--; break;
		case 1: // 下
			newY++; break;
		case 2: // 左
			newX--; break;
		case 3: // 右
			newX++; break;
		default: break;
		}
		// 如果超出地图边界，则无法移动
		if (newX < 0 || newX >= GameDto.MAP_WIDTH || newY < 0 || newY >= GameDto.MAP_HEIGHT)
			return false;
		// 如果与其他武士位置重叠，则无法移动
		for (int i = 0; i < 3; i++) {
			if (newX == dto.teamRed.samurais.get(i).curX && newY == dto.teamRed.samurais.get(i).curY)
				return false;
			if (newX == dto.teamBlue.samurais.get(i).curX && newY == dto.teamBlue.samurais.get(i).curY)
				return false;
		}
		// 如果到其他武士的出生点，则无法移动
		for (int i = 0; i < 3; i++) {
			Samurai red = dto.teamRed.samurais.get(i);
			Samurai blue = dto.teamBlue.samurais.get(i);
			// 武士可以走到自己的出发点
			if (red.equals(actSamurai) || blue.equals(actSamurai))
				continue;
			if ((red.homeX == newX && red.homeY == newY) || (blue.homeX == newX && blue.homeY == newY))
				return false;
		}
		return true;
	}

	/*
	 * 移动
	 */
	public void move(int direction) {
		Samurai actSamurai = this.actSamurai(dto.nowTurn);
		actSamurai.move(direction);
		// 消耗相应体力值
		actSamurai.cost -= GameDto.MOVE_COST;
	}

	/*
	 * 判断武士是否可以攻占
	 */
	public boolean canOccupy() {
		// 获得当前回合行动的武士对象
		Samurai actSamurai = actSamurai(dto.nowTurn);
		// 如果武士正在治疗则无法攻占
		if (actSamurai.cure != 0)
			return false;
		// 如果体力不够则无法攻占
		if (actSamurai.cost < GameDto.OCCUPY_COST)
			return false;
		return true;
	}

	/*
	 * 攻占
	 */
	public void occupy(int direction) {
		Samurai actSamurai = this.actSamurai(dto.nowTurn);
		// 当前队伍信息
		TeamDto team = (actSamurai.team == 1 ? dto.teamRed : dto.teamBlue);
		// 敌对队伍信息
		TeamDto opposite = (actSamurai.team == 1 ? dto.teamBlue : dto.teamRed);
		// 攻击设计到的范围
		List<Point> range = actSamurai.occupy(direction);
		// 消耗相应体力值
		actSamurai.cost -= GameDto.OCCUPY_COST;

		for (int i = 0; i < range.size(); i++) {
			int ranX = (int) range.get(i).getX();
			int ranY = (int) range.get(i).getY();
			// 如果攻击范围超出地图边界则跳过
			if (ranX < 0 || ranX >= GameDto.MAP_WIDTH || ranY < 0 || ranY >= GameDto.MAP_HEIGHT)
				continue;
			// 如果有敌方武士受到攻击则回家治疗
			for (int j = 0; j < 3; j++) {
				Samurai oppSamurai = opposite.samurais.get(j);
				// 如果敌方武士正在治疗中则不会受到攻击
				if (oppSamurai.cure != 0)
					continue;
				int oppX = oppSamurai.curX;
				int oppY = oppSamurai.curY;
				if (oppX == ranX && oppY == ranY) {
					oppSamurai.goHome();
				}
			}
			int belong = dto.gameMap.blocks[ranX][ranY].belong;
			// 如果该地原本就为该武士一方所占领则跳过
			if(belong == actSamurai.team) continue;
			else {
				// 更改占领区域
				dto.gameMap.blocks[ranX][ranY].changeBelong(actSamurai.team);
				// 队伍占领不同地形数变更
				switch (dto.gameMap.blocks[ranX][ranY].type) {
				case 0: team.plain++; break;
				case 1: team.forest++; break;
				case 2: team.mountain++; break;
				}
				// 更新队伍得分
				team.score++;
				// 如果区域原本被地方占领，则敌对队伍分数减1
				if (belong != 0) 
					opposite.score--;
			}
		}
	}

	/*
	 * 判断游戏是否结束
	 */
	public boolean isGameOver() {
		return dto.nowTurn == GameDto.TOTAL_TURNS;
	}

	/*
	 * 结束回合
	 */
	public void finishTurn() {
		
		// 更新资源
		for(int i = 0; i < 1; i++) {
			dto.teamRed.food += dto.teamRed.plain * GameDto.INCREASE_RESOURCE;
			dto.teamRed.wood += dto.teamRed.forest * GameDto.INCREASE_RESOURCE;
			dto.teamRed.iron += dto.teamRed.mountain * GameDto.INCREASE_RESOURCE;
			dto.teamBlue.food += dto.teamBlue.plain * GameDto.INCREASE_RESOURCE;
			dto.teamBlue.wood += dto.teamBlue.forest * GameDto.INCREASE_RESOURCE;
			dto.teamBlue.iron += dto.teamBlue.mountain * GameDto.INCREASE_RESOURCE;
		}
		// 治疗中的武士休息回合减1
		for (int i = 0; i < 3; i++) {
			Samurai redSamurai = dto.teamRed.samurais.get(i);
			Samurai blueSamurai = dto.teamBlue.samurais.get(i);
			if (redSamurai.cure != 0)
				redSamurai.cure--;
			if (blueSamurai.cure != 0)
				blueSamurai.cure--;
			
		}
		// 更新该回合武士体力值
				Samurai actSamurai = this.actSamurai(dto.nowTurn+1);
				if(actSamurai.cure==0)	{
					actSamurai.cost = actSamurai.maxCost;
					actSamurai.ofsamur.cure();
				}else
					actSamurai.cost=0;
		// 回合数加1
		dto.nowTurn++;
	}
	
	/*
	 * TODO
	 * 消耗资源随体力总值增长函数 测试函数：R = 2C
	 */
	public static int function(int cost) {
		return (int)Math.pow(cost-2,2);
	}

	/*
	 * 判断是否可以升级
	 */
	public boolean canUpdate() {
		Samurai actSamurai = this.actSamurai(dto.nowTurn);
		TeamDto team = (actSamurai.team == 1 ? dto.teamRed : dto.teamBlue);
		// 当前体力值下需要消耗的资源量
		int needResource = function(actSamurai.maxCost);
		switch (actSamurai.weapon) {
		case 0: // 长矛消耗食物与木材
			if(team.food -	needResource  < 0 || team.wood - needResource < 0)
				return false;
			break;
		case 1: // 剑消耗木材与生铁
			if(team.wood - needResource < 0 || team.iron - needResource < 0)
				return false;
			break;
		case 2: // 战斧消耗食物与生铁
			if(team.food - needResource < 0 || team.iron - needResource < 0)
				return false;
			break;
		}
		return true;
	}

	/*
	 * 升级体力值
	 */
	public void update() {
		Samurai actSamurai = this.actSamurai(dto.nowTurn);
		TeamDto team = (actSamurai.team == 1 ? dto.teamRed : dto.teamBlue);
		// 当前体力值下需要消耗的资源量
		int needResource = function(actSamurai.maxCost);
		// 减去相应资源 
		switch(actSamurai.weapon) {
		case 0: // 长矛消耗食物与木材
			team.food -= needResource;
			team.wood -= needResource;
			break;
		case 1: // 剑消耗木材与生铁
			team.wood -= needResource;
			team.iron -= needResource;
			break;
		case 2: // 战斧消耗食物与生铁
			team.food -= needResource;
			team.iron -= needResource;
			break;
		}
		// 该武士当前回合可用体力值增加
		//actSamurai.cost ++; //这项很有可能影响平衡性
		// 该武士此后每回合体力总值增加
		actSamurai.maxCost++;
	}

}
