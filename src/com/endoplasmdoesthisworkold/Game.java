package com.endoplasmdoesthisworkold;

import org.lwjgl.opengl.Display;

import com.endoplasm.engine.Endogen;
import com.endoplasm.engine.GInit;
import com.endoplasm.engine.Render2d;
import com.endoplasm.engine.Render3d;
import com.endoplasm.engine.Text;
import com.endoplasm.engine.Vertex3f;

public class Game extends GInit {
	
	public static Assets Assets = new Assets(null);

	public Game() {
	}

	@Override
	public void init() {
		Assets.load();
		SceneGraph.newGame();
	}

	@Override
	public void update() {
		SceneGraph.update();
	}

	@Override
	public void render3D() {
		SceneGraph.render();
		SceneGraph.renderTSprites();

	}

	@Override
	public void render2D() {
		Text.renderTextFromString("FPS " + Endogen.lastFPS + " UPS " + Endogen.lastUPS, 2, 286, 4, 6, -1, 0, Endogen.SystemAssets.mask.FONT1, new float[]{1,1,1,1});
		Render2d.square(0, 0, 0, 0, new float[]{0,0,0,0}, Endogen.SystemAssets.mask.BLANK);
		// TODO Auto-generated method stub

	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

}
