package com.endoplasmdoesthisworkold;

import org.lwjgl.input.Keyboard;

import com.endoplasm.engine.Endogen;
import com.endoplasm.engine.MathUtil;
import com.endoplasm.engine.Render3d;
import com.endoplasm.engine.Vertex3f;

public class Player {
	
	public Chunk CurrentChunk;
	public Vertex3f pos;
	public Vertex3f vel;
	public Vertex3f gravity;
	public Vertex3f friction;
	public float walkSpeed = 1.5f;
	public float turnSpeed = 2.5f;
	
	public float bodRot = 0;
	public float headRot = 0;
	
	public Player(Vertex3f pos){
		this.pos = pos.clone();
	}
	
	public void update(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			pos.addX(MathUtil.getXSpeed(bodRot, walkSpeed));
			pos.addY(MathUtil.getYSpeed(bodRot, walkSpeed));
		} else 
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			pos.addX(MathUtil.getXSpeed(bodRot, -walkSpeed));
			pos.addY(MathUtil.getYSpeed(bodRot, -walkSpeed));
		} else{
			if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			pos.addX(MathUtil.getXSpeed(bodRot - 90, walkSpeed));
			pos.addY(MathUtil.getYSpeed(bodRot - 90, walkSpeed));
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_A)){
				pos.addX(MathUtil.getXSpeed(bodRot + 90, walkSpeed));
				pos.addY(MathUtil.getYSpeed(bodRot + 90, walkSpeed));
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RMENU) && Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			bodRot += turnSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RMENU) && Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			bodRot -= turnSpeed;
		}
		
		headRot -= MathUtil.getAngleBetween(headRot, bodRot) * .1f;
		float camX = MathUtil.getXSpeed(headRot, -300);
		float camY = MathUtil.getYSpeed(headRot, -300);
		Endogen.camera.setPos1(new Vertex3f(camX, camY, pos.getZ()+300));
		Endogen.camera.setPos2(new Vertex3f(0, 0, pos.getZ()));
	}
	
	public void render(){
		Render3d.cube(pos.getX() - 4, pos.getY()-4, pos.getZ()-8, pos.getX() + 4, pos.getY() + 4, pos.getZ() + 8, 1, 1, 1, 1, Endogen.SystemAssets.mask.BLANK);
	}

}
