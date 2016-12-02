package service;

import java.awt.Point;
import java.util.List;

import dto.GameDto;
import dto.TeamDto;
import entity.Samurai;

public class GameService {

	/*
	 * ��Ϸ����Դ
	 */
	public GameDto dto;

	public GameService(GameDto dto) {
		this.dto = dto;
	}

	/*
	 * �жϵ�ǰ�غ����ж�����ʿ һ��ѭ����A0 B0 B1 A1 A2 B2 B0 A0 A1 B1 B2 A2
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
	 * �ж��Ƿ�����ƶ� direction 0:up 1:down 2:left 3:right
	 */
	public boolean canMove(int direction) {
		// ��õ�ǰ�غ��ж�����ʿ����
		Samurai actSamurai = actSamurai(dto.nowTurn);
		// �����ʿ�����������޷��ƶ�
		if (actSamurai.cure != 0) return false;
		// ��������������޷��ƶ�
		if (actSamurai.cost < GameDto.MOVE_COST) return false;
		// �ƶ������λ�����꣨��ʼ��Ϊ��ǰ���꣩
		int newX = actSamurai.curX;
		int newY = actSamurai.curY;
		switch (direction) {
		case 0: // ��
			newY--; break;
		case 1: // ��
			newY++; break;
		case 2: // ��
			newX--; break;
		case 3: // ��
			newX++; break;
		default: break;
		}
		// ���������ͼ�߽磬���޷��ƶ�
		if (newX < 0 || newX >= GameDto.MAP_WIDTH || newY < 0 || newY >= GameDto.MAP_HEIGHT)
			return false;
		// �����������ʿλ���ص������޷��ƶ�
		for (int i = 0; i < 3; i++) {
			if (newX == dto.teamRed.samurais.get(i).curX && newY == dto.teamRed.samurais.get(i).curY)
				return false;
			if (newX == dto.teamBlue.samurais.get(i).curX && newY == dto.teamBlue.samurais.get(i).curY)
				return false;
		}
		// �����������ʿ�ĳ����㣬���޷��ƶ�
		for (int i = 0; i < 3; i++) {
			Samurai red = dto.teamRed.samurais.get(i);
			Samurai blue = dto.teamBlue.samurais.get(i);
			// ��ʿ�����ߵ��Լ��ĳ�����
			if (red.equals(actSamurai) || blue.equals(actSamurai))
				continue;
			if ((red.homeX == newX && red.homeY == newY) || (blue.homeX == newX && blue.homeY == newY))
				return false;
		}
		return true;
	}

	/*
	 * �ƶ�
	 */
	public void move(int direction) {
		Samurai actSamurai = this.actSamurai(dto.nowTurn);
		actSamurai.move(direction);
		// ������Ӧ����ֵ
		actSamurai.cost -= GameDto.MOVE_COST;
	}

	/*
	 * �ж���ʿ�Ƿ���Թ�ռ
	 */
	public boolean canOccupy() {
		// ��õ�ǰ�غ��ж�����ʿ����
		Samurai actSamurai = actSamurai(dto.nowTurn);
		// �����ʿ�����������޷���ռ
		if (actSamurai.cure != 0)
			return false;
		// ��������������޷���ռ
		if (actSamurai.cost < GameDto.OCCUPY_COST)
			return false;
		return true;
	}

	/*
	 * ��ռ
	 */
	public void occupy(int direction) {
		Samurai actSamurai = this.actSamurai(dto.nowTurn);
		// ��ǰ������Ϣ
		TeamDto team = (actSamurai.team == 1 ? dto.teamRed : dto.teamBlue);
		// �жԶ�����Ϣ
		TeamDto opposite = (actSamurai.team == 1 ? dto.teamBlue : dto.teamRed);
		// ������Ƶ��ķ�Χ
		List<Point> range = actSamurai.occupy(direction);
		// ������Ӧ����ֵ
		actSamurai.cost -= GameDto.OCCUPY_COST;

		for (int i = 0; i < range.size(); i++) {
			int ranX = (int) range.get(i).getX();
			int ranY = (int) range.get(i).getY();
			// ���������Χ������ͼ�߽�������
			if (ranX < 0 || ranX >= GameDto.MAP_WIDTH || ranY < 0 || ranY >= GameDto.MAP_HEIGHT)
				continue;
			// ����ез���ʿ�ܵ�������ؼ�����
			for (int j = 0; j < 3; j++) {
				Samurai oppSamurai = opposite.samurais.get(j);
				// ����з���ʿ�����������򲻻��ܵ�����
				if (oppSamurai.cure != 0)
					continue;
				int oppX = oppSamurai.curX;
				int oppY = oppSamurai.curY;
				if (oppX == ranX && oppY == ranY) {
					oppSamurai.goHome();
				}
			}
			int belong = dto.gameMap.blocks[ranX][ranY].belong;
			// ����õ�ԭ����Ϊ����ʿһ����ռ��������
			if(belong == actSamurai.team) continue;
			else {
				// ����ռ������
				dto.gameMap.blocks[ranX][ranY].changeBelong(actSamurai.team);
				// ����ռ�첻ͬ���������
				switch (dto.gameMap.blocks[ranX][ranY].type) {
				case 0: team.plain++; break;
				case 1: team.forest++; break;
				case 2: team.mountain++; break;
				}
				// ���¶���÷�
				team.score++;
				// �������ԭ�����ط�ռ�죬��жԶ��������1
				if (belong != 0) 
					opposite.score--;
			}
		}
	}

	/*
	 * �ж���Ϸ�Ƿ����
	 */
	public boolean isGameOver() {
		return dto.nowTurn == GameDto.TOTAL_TURNS;
	}

	/*
	 * �����غ�
	 */
	public void finishTurn() {
		
		// ������Դ
		for(int i = 0; i < 1; i++) {
			dto.teamRed.food += dto.teamRed.plain * GameDto.INCREASE_RESOURCE;
			dto.teamRed.wood += dto.teamRed.forest * GameDto.INCREASE_RESOURCE;
			dto.teamRed.iron += dto.teamRed.mountain * GameDto.INCREASE_RESOURCE;
			dto.teamBlue.food += dto.teamBlue.plain * GameDto.INCREASE_RESOURCE;
			dto.teamBlue.wood += dto.teamBlue.forest * GameDto.INCREASE_RESOURCE;
			dto.teamBlue.iron += dto.teamBlue.mountain * GameDto.INCREASE_RESOURCE;
		}
		// �����е���ʿ��Ϣ�غϼ�1
		for (int i = 0; i < 3; i++) {
			Samurai redSamurai = dto.teamRed.samurais.get(i);
			Samurai blueSamurai = dto.teamBlue.samurais.get(i);
			if (redSamurai.cure != 0)
				redSamurai.cure--;
			if (blueSamurai.cure != 0)
				blueSamurai.cure--;
			
		}
		// ���¸ûغ���ʿ����ֵ
				Samurai actSamurai = this.actSamurai(dto.nowTurn+1);
				if(actSamurai.cure==0)	{
					actSamurai.cost = actSamurai.maxCost;
					actSamurai.ofsamur.cure();
				}else
					actSamurai.cost=0;
		// �غ�����1
		dto.nowTurn++;
	}
	
	/*
	 * TODO
	 * ������Դ��������ֵ�������� ���Ժ�����R = 2C
	 */
	public static int function(int cost) {
		return (int)Math.pow(cost-2,2);
	}

	/*
	 * �ж��Ƿ��������
	 */
	public boolean canUpdate() {
		Samurai actSamurai = this.actSamurai(dto.nowTurn);
		TeamDto team = (actSamurai.team == 1 ? dto.teamRed : dto.teamBlue);
		// ��ǰ����ֵ����Ҫ���ĵ���Դ��
		int needResource = function(actSamurai.maxCost);
		switch (actSamurai.weapon) {
		case 0: // ��ì����ʳ����ľ��
			if(team.food -	needResource  < 0 || team.wood - needResource < 0)
				return false;
			break;
		case 1: // ������ľ��������
			if(team.wood - needResource < 0 || team.iron - needResource < 0)
				return false;
			break;
		case 2: // ս������ʳ��������
			if(team.food - needResource < 0 || team.iron - needResource < 0)
				return false;
			break;
		}
		return true;
	}

	/*
	 * ��������ֵ
	 */
	public void update() {
		Samurai actSamurai = this.actSamurai(dto.nowTurn);
		TeamDto team = (actSamurai.team == 1 ? dto.teamRed : dto.teamBlue);
		// ��ǰ����ֵ����Ҫ���ĵ���Դ��
		int needResource = function(actSamurai.maxCost);
		// ��ȥ��Ӧ��Դ 
		switch(actSamurai.weapon) {
		case 0: // ��ì����ʳ����ľ��
			team.food -= needResource;
			team.wood -= needResource;
			break;
		case 1: // ������ľ��������
			team.wood -= needResource;
			team.iron -= needResource;
			break;
		case 2: // ս������ʳ��������
			team.food -= needResource;
			team.iron -= needResource;
			break;
		}
		// ����ʿ��ǰ�غϿ�������ֵ����
		//actSamurai.cost ++; //������п���Ӱ��ƽ����
		// ����ʿ�˺�ÿ�غ�������ֵ����
		actSamurai.maxCost++;
	}

}
