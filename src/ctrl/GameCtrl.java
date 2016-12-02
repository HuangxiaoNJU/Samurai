package ctrl;

import service.GameService;

public class GameCtrl {

	/*
	 * 游戏逻辑层对象
	 */
	public GameService gameService;
	/*
	 * TODO 游戏界面层对象
	 */

	/*
	 * 构造方法
	 */
	public GameCtrl(GameService gameService) {
		// 获取游戏逻辑层
		this.gameService = gameService;
		// TODO 获取界面层对象
		
	}

	/*
	 * 移动
	 */
	public void move(int direction) {
		if(gameService.canMove(direction))
			gameService.move(direction);
		// 控制界面层对象作出回应
		// TODO
	}
	/*
	 * 攻占
	 */
	public void occupy(int direction) {
		if(gameService.canOccupy())
			gameService.occupy(direction);
		// 控制界面层对象作出回应
		// TODO
	}
	
	/*
	 * 结束回合/结束游戏
	 */
	public int finish() {
		gameService.finishTurn();
		// 控制界面层对象作出回应 TODO
		if(gameService.isGameOver()) {
			// 控制界面层对象结束游戏
			return 1;
		}
		return 0;
	}
	/*
	 * 升级体力
	 */
	public void update() {
		// TODO
		if(gameService.canUpdate())
			gameService.update();
	}
	

}
