package com.endoplasm.editor;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.endoplasm.engine.Endogen;
import com.endoplasm.engine.GInit;
import com.endoplasm.engine.Render2d;
import com.endoplasm.engine.Render3d;
import com.endoplasm.engine.Text;
import com.endoplasm.engine.Vertex3f;
import com.endoplasm.engine.Vertex3i;
import com.endoplasm.game.*;

public class Simulation extends GInit {

	public static final Assets Assets = new Assets(null);

	@Override
	public void init() {
		Display.setTitle("Level Editor v0");
		Assets.load();
		ChunkLoader.preLoad("/Resources/Maps/World1.map");
		Chunk c = ChunkLoader.TransitionToChunk("c1");
		Viewer.currentChunk = c;
		Viewer.family.load(ChunkLoader.File);
		Viewer.family.save("/Users/BirdLab2/Desktop/map1.map");
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		ChunkLoader.manageChunks();
		for (Chunk chunk : ChunkLoader.usedChunks) {
			chunk.update();
		}
		Viewer.update();
	}

	@Override
	public void render3D() {
		GL11.glPushMatrix();
		float scale = Endogen.WIDTH / 32f;
		GL11.glScalef(scale, scale, scale);
		Viewer.render();
		for (Chunk chunk : ChunkLoader.usedChunks) {
			chunk.render();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			for (Chunk chunk : ChunkLoader.usedChunks) {
				if (chunk == Viewer.currentChunk)
					chunk.specialRender(1, 1, 1);
				else
					chunk.specialRender(0.5f, 0.5f, 1);
			}
		}
		GL11.glPopMatrix();
	}

	@Override
	public void render2D() {
		String Details = "FPS " + Endogen.lastFPS + " UPS " + Endogen.lastUPS;
		Details = Details + " \nLoaded Chunks:" + ChunkLoader.loadedChunks.size();
		Details = Details + " \nUsed Chunks:" + ChunkLoader.usedChunks.size();
		Text.renderTextFromString(Details, 2, 286, 4, 6, -1, 0, Endogen.SystemAssets.mask.FONT1, new float[] { 1, 1, 1, 1 });
		Render2d.square(0, 0, 0, 0, new float[] { 1, 1, 1, 1 }, Assets.CHARACTERSHEET);

	}

}
