package com.endoplasm.game;

import com.endoplasm.engine.Endogen;
import com.endoplasm.engine.Render3d;

public abstract class Block extends Entity {

	public static Block getBlock(String s) {
		switch (s) {
		case "" : return null;
		case "BGrass": {
			return new Grass();
		}
		default: {
			return new Void();
		}
		}
	}
	
	public abstract String getAsString();

	/*
	 * Above is all the static and abstract methods for the block super
	 * 
	 * 
	 * 
	 * ***************************************************************************************************
	 * 
	 * 
	 * 
	 * Below is a list of all the block classes in no particular order, have fun finding things.....
	 */

	
	/**
	 * Void Block
	 * Copyright EndoPlasm Gaming ©2013
	 * Edited 15 May 2015
	 */
	public static class Void extends Block {
		@Override
		public void update() {
		}
		@Override
		public void render(float x, float y, float z) {
			float r = (float) (System.currentTimeMillis() % 63 / 63f);
			float g = (float) (System.currentTimeMillis() % 84 / 84f);
			float b = (float) (System.currentTimeMillis() % 91 / 91f);
			Render3d.cube(x, y, z, x + 1, y + 1, z + 1, r, g, b, 1, Endogen.SystemAssets.mask.BLANK);
		}
		@Override
		public String getAsString(){
			return "Void";
		}
	}
	
	/**
	 * Grass Block
	 * Copyright EndoPlasm Gaming ©2013
	 * Edited 15 May 2015
	 */
	public static class Grass extends Block {
		@Override
		public void update() {
		}

		@Override
		public void render(float x, float y, float z) {
			Render3d.cube(x, y, z, x + 1, y + 1, z + 1, x / 8, y / 8, z / 8, 1, Endogen.SystemAssets.mask.BLANK);
		}
		
		@Override
		public String getAsString(){
			return "BGrass";
		}
	}

}
