package com.endoplasmdoesthisworkold;

import org.newdawn.slick.opengl.Texture;

import com.endoplasm.engine.Endogen;
import com.endoplasm.engine.MathUtil;
import com.endoplasm.engine.Vertex3f;

public class Sprite {
	
	public Texture tex;
	public Vertex3f pos;
	public float width;
	public float height;
	public float Rotation;
	public float r = 1;
	public float g = 1;
	public float b = 1;
	public float a = 1;
	
	public float disToCamera;
	
	public Sprite(Vertex3f pos, float width, float height, float Rotation, Texture tex){
		this.pos = pos;
		this.height = height;
		this.width = width;
		this.Rotation = Rotation;
		this.tex = tex;
		
		disToCamera = MathUtil.distance(Endogen.camera.getPos1().getX(), Endogen.camera.getPos1().getY(), pos.getX(), pos.getY());
	}
	
	public Sprite(Vertex3f pos, float width, float height, float Rotation, Texture tex, float r, float g, float b, float a){
		this.pos = pos;
		this.height = height;
		this.width = width;
		this.Rotation = Rotation;
		this.tex = tex;
		
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		
		disToCamera = MathUtil.distance(Endogen.camera.getPos1().getX(), Endogen.camera.getPos1().getY(), pos.getX(), pos.getY());
	}

}
