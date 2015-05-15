package com.endoplasmdoesthisworkold;

import java.util.Random;

import com.endoplasm.engine.Vertex3f;

public class Generation {
	
	public static final int A_FLAT = 100;
	public static final int A_HILL = 101;
	public static final int A_MOUNTAIN = 102;
	public static final int M_DRY = 200;
	public static final int M_MOIST = 201;
	public static final int M_WET = 202;
	public static final int M_SOAKING = 203;
	public static final int B_DESERT = 0;
	public static final int B_DESERTHILLS = 1;
	public static final int B_MOUNTAINROCKY = 2;
	public static final int B_GRASSLAND = 3;
	public static final int B_MOUNTAINFOREST = 4;
	public static final int B_FOREST = 5;
	public static final int B_SWAMP = 6;
	public static final int B_FORESTTHICK = 7;
	public static final int B_MOUNTAINICE = 8;
	
	public static Chunk getNewChunk(int x, int y){
		int[][] heightMap = new int[0][0];
		long chunkSeed = (SceneGraph.worldSeed * x + y) * y - x;
		Chunk chunk = new Chunk(x, y, chunkSeed);
		Random r = new Random(chunkSeed);
		//chunk.biome = r.nextInt(9);
		
		// get heightMap
		switch(chunk.biome){
		case B_GRASSLAND:{
			heightMap = getFlatMap();
		}
		break;
		}
		
		// build chunk
		switch(chunk.biome){
		case B_GRASSLAND:{
			getFlatMap(chunk, heightMap, new float[]{0,1,0,1});
		}
		break;
		}

		return chunk;
	}
	
	public static int[][] getFlatMap(){
		int size = 28;
		int[][] heightMap = new int[size][size];
		
		for(int ix = 0; ix < size; ix++){
			for(int iy = 0; iy < size; iy++){
				heightMap[ix][iy] = 0;
			}
		}
		
		return heightMap;
	}
	
	public static void getFlatMap(Chunk chunk, int[][] heightMap, float[] color){
		for(int ix = 0; ix < 28; ix++){
			for(int iy = 0; iy < 28; iy++){
				chunk.blocks.add(new Block(new Vertex3f(ix * 8 - (28 * 4), iy * 8 - (28 * 4), heightMap[ix][iy] * 4 - 2), color));
			}
		}
	}
	

}
