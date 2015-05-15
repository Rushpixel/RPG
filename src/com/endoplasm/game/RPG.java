package com.endoplasm.game;

import com.endoplasm.engine.Endogen;


public class RPG {
	
	public static void main(String args[]){
		Game game = new Game();
		Endogen endogen = new Endogen(game);
		endogen.Begin();
	}

}
