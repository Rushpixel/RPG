package com.endoplasm.editor;

import org.lwjgl.input.Keyboard;

import com.endoplasm.engine.Endogen;
import com.endoplasm.engine.MathUtil;
import com.endoplasm.engine.Render2d;
import com.endoplasm.engine.Render3d;
import com.endoplasm.engine.Text;
import com.endoplasm.engine.Vertex3f;
import com.endoplasm.engine.Vertex3i;
import com.endoplasm.game.Chunk;

public class Viewer {
	
	public static Vertex3i pos = new Vertex3i(4,4,4);
	public static Vertex3f posFloat = new Vertex3f(4,4,4);
	public static final float speed  = 0.1f;
	public static float rotation = 180;
	
	public static Chunk currentChunk;
	
	public static void update(){
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			posFloat.setX(posFloat.getX() - speed);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			posFloat.setX(posFloat.getX() + speed);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			posFloat.setY(posFloat.getY() - speed);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			posFloat.setY(posFloat.getY() + speed);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_R)){
			posFloat.setZ(posFloat.getZ() + speed);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F)){
			posFloat.setZ(posFloat.getZ() - speed);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			rotation -= 1;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E)){
			rotation += 1;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_T)){
			rotation = 180;
		}
		
		pos = new Vertex3i(Math.round(posFloat.getX()), Math.round(posFloat.getY()), Math.round(posFloat.getZ()));
		if(pos.getX() >= 8){
			// check if the cell the viewer is in is in a valid chunk, else move back a cell
		}
		
		float scale = Endogen.WIDTH / 32f;
		Endogen.camera.setPos1(new Vertex3f(MathUtil.getXSpeed(rotation, 300) + posFloat.getX()*scale, MathUtil.getYSpeed(rotation, 300) + posFloat.getY()*scale,300 + posFloat.getZ()*scale));
		Endogen.camera.setPos2(new Vertex3f(posFloat.getX()*scale, posFloat.getY()*scale, posFloat.getZ()*scale));
	}
	
	public static void render(){
		float r = (float) (System.currentTimeMillis() % 63 / 63f);
		float g = (float) (System.currentTimeMillis() % 84 / 84f);
		float b = (float) (System.currentTimeMillis() % 91 / 91f);
		Render3d.cube(pos.getX() - .1f, pos.getY() - .1f, pos.getZ() - .1f, pos.getX() + 1.1f, pos.getY() + 1.1f, pos.getZ() + 1.1f, r, g, b, 1, Endogen.SystemAssets.mask.BLANK);
	}

}
