package com.endoplasm.game;

public abstract class Entity {
	
	public abstract void update();
	public abstract void render(float x, float y, float z);
	
	public static Entity makeFromString(String s){
		if(s.equals("")) return null;
		switch(s.substring(0, 1)){
		case "B" : return Block.getBlock(s);
		}
		return null;
	}
	
}
