package com.endoplasmdoesthisworkold;

import java.util.ArrayList;
import java.util.Random;

import com.endoplasm.engine.Render3d;
import com.endoplasm.engine.Vertex3f;

public class SceneGraph {
	
	public static Player player;
	public static ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	public static long worldSeed;
	
	public static void newGame(){
		chunks.clear();
		worldSeed = new Random().nextLong();
		chunks.add(Generation.getNewChunk(0, 0));
		player = new Player(new Vertex3f(0,0,16));
		player.CurrentChunk = chunks.get(0);
	}
	
	public static void update(){
		player.update();
	}
	
	public static void render(){
		if(player.CurrentChunk != null)player.CurrentChunk.render();
		player.render();
	}
	
	public static ArrayList<Sprite> tSprites = new ArrayList<Sprite>();

	public static void addTransperantSprite(Sprite sprite){
		for(int i = 0; i < tSprites.size(); i++){
			if(sprite.disToCamera > tSprites.get(i).disToCamera){
				tSprites.add(i, sprite);
				return;
			}
		}
		tSprites.add(sprite);
	}
	
	public static void renderTSprites(){
		for(Sprite s: tSprites){
			//Render3d.Sprite(s.pos, s.width, s.height, s.Rotation, s.tex, s.r, s.g, s.b, s.a);
		}
		tSprites.clear();
	}

}
