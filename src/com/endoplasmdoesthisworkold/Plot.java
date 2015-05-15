package com.endoplasmdoesthisworkold;

import java.util.Random;

public class Plot{
	
	public int x;
	public int y;
	public long seed;
	public Random r;
	public float altitude = 0;
	public float moisture = 0;
	
	//stage 0 = altitude and moisture calculated
	//stage 1 = Biome, sentient activity calculated
	//stage 2 = Chunk generated
	public int stage = 0;
	
	public Plot(int x, int y, long WorldSeed){
		this.x = x;
		this.y = y;
		seed = WorldSeed >> x * y;
		r = new Random(seed);
		calcStats();
	}
	
	public void calcStats(){
		if(stage < 1){
			altitude = r.nextFloat();
			moisture = r.nextFloat();
			stage = 1;
		}
	}
	
	public void calcBiome(){
		if(stage < 2){
			calcStats();
			
		}
	}
	
	public int getBiome(){
		return 0;
	}

}
