package com.endoplasmdoesthisworkold;

import com.endoplasm.engine.Endogen;

public class Main {
	
	public static void main(String[] args){
		Game game = new Game();
		Endogen endogen = new Endogen(game);
		endogen.Begin();
	}

}
