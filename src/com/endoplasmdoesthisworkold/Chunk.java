package com.endoplasmdoesthisworkold;

import java.util.ArrayList;

public class Chunk {
	
	//TODO seperate chunks into an abstract class, and use a worldChunk for the overworld with x/y info
	public int x;
	public int y;
	
	public long chunkSeed;
	
	public int biome = Generation.B_GRASSLAND;
	
	public ArrayList<Block> blocks = new ArrayList<Block>();
	
	public Chunk(int x, int y, long chunkSeed){
		this.x = x; 
		this.y = y;
		this.chunkSeed = chunkSeed;
	}
	
	public void render(){
		for(Block b: blocks){
			b.render();
		}
	}

}
