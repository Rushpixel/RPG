package com.endoplasm.editor;

import com.endoplasm.engine.Endogen;

public class Editor {

	public static void main(String[] args){
		Simulation game = new Simulation();
		Endogen endogen = new Endogen(game);
		endogen.Begin();
	}
	
}
