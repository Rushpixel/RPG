package com.endoplasm.editor;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.endoplasm.engine.Endogen;
import com.endoplasm.engine.GInit;
import com.endoplasm.engine.Render2d;
import com.endoplasm.engine.Render3d;
import com.endoplasm.engine.Text;
import com.endoplasm.engine.Vertex3f;
import com.endoplasm.game.*;

public class Simulation extends GInit{
	
	public static final Assets Assets = new Assets(null);

	@Override
	public void init() {
		Display.setTitle("Level Editor v0");
		Assets.load();
		ChunkLoader.preLoad("/Resources/Maps/blank.map");
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		Viewer.update();
	}

	@Override
	public void render3D() {
		GL11.glPushMatrix();
		float scale = Endogen.WIDTH / 32f;
		GL11.glScalef(scale, scale, scale);
		Viewer.render();
		GL11.glPopMatrix();
	}

	@Override
	public void render2D() {
		Text.renderTextFromString("FPS " + Endogen.lastFPS + " UPS " + Endogen.lastUPS, 2, 286, 4, 6, -1, 0, Endogen.SystemAssets.mask.FONT1, new float[]{1,1,1,1});
		Render2d.square(0, 0, 0, 0, new float[]{1,1,1,1}, Assets.CHARACTERSHEET);
		
	}

}
