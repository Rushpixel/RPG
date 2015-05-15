package com.endoplasmdoesthisworkold;

import com.endoplasm.engine.Endogen;
import com.endoplasm.engine.Render3d;
import com.endoplasm.engine.Vertex3f;

public class Block {
	
	public Vertex3f pos;
	public float[] c;
	
	public Block(Vertex3f pos, float[] c){
		this.pos = pos;
		this.c = c;
	}
	
	public void render(){
		Render3d.Model(pos, Game.Assets.MODELS.BLOCKBASE, Game.Assets.TEXTURES.BLOCKBASE, null);
		}

}
