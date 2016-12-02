package main;

import ctrl.GameCtrl;
import dto.GameDto;
import gui.Gaming;
import gui.Title;
import gui.Window;
import service.GameService;

/**
 * version 0.1.518
 * 
 * @author CSC-California Shin Chan
 *
 */
public class Run {

	public static void main(String[] args) {
		// 创建游戏数据源
		GameDto dto = new GameDto();
		Title title = new Title();
		Gaming game = new Gaming(dto);
		GameService severvice = new GameService(dto);
		GameCtrl controller = new GameCtrl(severvice);
		Window window = new Window(title, game);
		dto.controller = controller;
		game.logic = severvice;
		game.controller = controller;
		window.logic = severvice;
		window.dto = dto;
		for (int i = 0; i < 3; i++) {
			dto.gameMap.samurs[0][i].controller = controller;
			dto.gameMap.samurs[0][i].logic = severvice;
			dto.gameMap.samurs[1][i].controller = controller;
			dto.gameMap.samurs[1][i].logic = severvice;
			dto.gameMap.samurs[0][i].ofgame = game;
			dto.gameMap.samurs[1][i].ofgame = game;
		}
		window.toTitle();
	}

}
