package ctrl;

import service.GameService;

public class GameCtrl {

	/*
	 * ��Ϸ�߼������
	 */
	public GameService gameService;
	/*
	 * TODO ��Ϸ��������
	 */

	/*
	 * ���췽��
	 */
	public GameCtrl(GameService gameService) {
		// ��ȡ��Ϸ�߼���
		this.gameService = gameService;
		// TODO ��ȡ��������
		
	}

	/*
	 * �ƶ�
	 */
	public void move(int direction) {
		if(gameService.canMove(direction))
			gameService.move(direction);
		// ���ƽ�������������Ӧ
		// TODO
	}
	/*
	 * ��ռ
	 */
	public void occupy(int direction) {
		if(gameService.canOccupy())
			gameService.occupy(direction);
		// ���ƽ�������������Ӧ
		// TODO
	}
	
	/*
	 * �����غ�/������Ϸ
	 */
	public int finish() {
		gameService.finishTurn();
		// ���ƽ�������������Ӧ TODO
		if(gameService.isGameOver()) {
			// ���ƽ������������Ϸ
			return 1;
		}
		return 0;
	}
	/*
	 * ��������
	 */
	public void update() {
		// TODO
		if(gameService.canUpdate())
			gameService.update();
	}
	

}
