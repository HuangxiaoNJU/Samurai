package dto;

import java.util.ArrayList;
import java.util.List;

import entity.Samurai;

public class TeamDto {

	/*
	 * 游戏数据源
	 */
	public GameDto dto;
	/*
	 * 武士列表
	 */
	public List<Samurai> samurais = new ArrayList<Samurai>(3);
	/*
	 * 得分
	 */
	public int score;
	/*
	 * 生铁资源
	 */
	public int iron;
	/*
	 * 食物资源
	 */
	public int food;
	/*
	 * 木头资源
	 */
	public int wood;
	/*
	 * 所占平原区域数
	 */
	public int plain;
	/*
	 * 所占山地区域数
	 */
	public int mountain;
	/*
	 * 所占森林区域数
	 */
	public int forest;
	/*
	 * 三种不同武士每回合的体力值上限
	 */
	public int[] cost;
	
	/*
	 * 构造方法
	 * team: 1 红方 2 蓝方
	 * (home[i][0],home[i][1])表示i号武士出生点坐标
	 */
	public TeamDto(int team, int[][] home) {
		// 依次创建武士对象
		this.samurais.add(new Samurai(team, home[0][0], home[0][1], 0));
		this.samurais.add(new Samurai(team, home[1][0], home[1][1], 1));
		this.samurais.add(new Samurai(team, home[2][0], home[2][1], 2));
		
		this.score = 0;
		this.iron = GameDto.INIT_RESOURCE;
		this.food = GameDto.INIT_RESOURCE;
		this.wood = GameDto.INIT_RESOURCE;
		this.plain = 0;
		this.mountain = 0;
		this.forest = 0;
	}
	public void reset(){
		this.score = 0;
		this.iron = GameDto.INIT_RESOURCE;
		this.food = GameDto.INIT_RESOURCE;
		this.wood = GameDto.INIT_RESOURCE;
		this.plain = 0;
		this.mountain = 0;
		this.forest = 0;
		for(int i=0;i<3;i++){
			samurais.get(i).maxCost=7;
		}
	}
	
}
