package com.endoplasm.game;

public class Cell {
	
	public Entity entity;
	public Detail detail;
	
	public void loadFromString(String s){
		s = s.replace("<", "");
		s = s.replaceAll("<", "");
		String entityString = s.split(":")[0];
		String detailString = s.split(":")[1];
		entity = Entity.makeFromString(entityString);
	}
	
	public void update(){
		if(entity != null) entity.update();
	}
	
	public void render(float x, float y, float z){
		if(entity != null) entity.render(x, y, z);
	}
	
	public void unload(){
		entity.unload();
		detail.unload();
	}

}
