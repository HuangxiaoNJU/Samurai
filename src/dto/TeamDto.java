package dto;

import java.util.ArrayList;
import java.util.List;

import entity.Samurai;

public class TeamDto {

	/*
	 * ��Ϸ����Դ
	 */
	public GameDto dto;
	/*
	 * ��ʿ�б�
	 */
	public List<Samurai> samurais = new ArrayList<Samurai>(3);
	/*
	 * �÷�
	 */
	public int score;
	/*
	 * ������Դ
	 */
	public int iron;
	/*
	 * ʳ����Դ
	 */
	public int food;
	/*
	 * ľͷ��Դ
	 */
	public int wood;
	/*
	 * ��ռƽԭ������
	 */
	public int plain;
	/*
	 * ��ռɽ��������
	 */
	public int mountain;
	/*
	 * ��ռɭ��������
	 */
	public int forest;
	/*
	 * ���ֲ�ͬ��ʿÿ�غϵ�����ֵ����
	 */
	public int[] cost;
	
	/*
	 * ���췽��
	 * team: 1 �췽 2 ����
	 * (home[i][0],home[i][1])��ʾi����ʿ����������
	 */
	public TeamDto(int team, int[][] home) {
		// ���δ�����ʿ����
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
