package com.endoplasm.editor;

import org.lwjgl.input.Keyboard;

import com.endoplasm.engine.Endogen;
import com.endoplasm.engine.MathUtil;
import com.endoplasm.engine.Render2d;
import com.endoplasm.engine.Render3d;
import com.endoplasm.engine.Text;
import com.endoplasm.engine.Vertex3f;
import com.endoplasm.engine.Vertex3i;
import com.endoplasm.game.Assets;
import com.endoplasm.game.Chunk;
import com.endoplasm.game.ChunkFamily;
import com.endoplasm.game.ChunkLoader;

public class Viewer {

	public static Vertex3i pos = new Vertex3i(4, 4, 4);
	public static Vertex3f posFloat = new Vertex3f(0.5f, 0.5f, 0.5f);
	public static final float speed = 0.1f;
	public static float rotation = 180;
	public static boolean hasPressed = false;

	public static final int MODE_CELL = 000;
	public static final int MODE_CHUNK = 100;
	public static int MODE = MODE_CELL;

	public static Chunk currentChunk;
	public static ChunkFamily family = new ChunkFamily();

	public static void update() {

		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			rotation -= 1;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			rotation += 1;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
			rotation = 180;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			MODE = MODE_CHUNK;
		} else {
			MODE = MODE_CELL;
		}

		if (MODE == MODE_CELL) {
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				posFloat.setX(posFloat.getX() - speed);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				posFloat.setX(posFloat.getX() + speed);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				posFloat.setY(posFloat.getY() - speed);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				posFloat.setY(posFloat.getY() + speed);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
				posFloat.setZ(posFloat.getZ() + speed);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
				posFloat.setZ(posFloat.getZ() - speed);
			}

			if (posFloat.getX() > 1) {
				posFloat.setX(posFloat.getX() - 1);
				pos.addX(1);
				// move
			}
			if (posFloat.getX() < 0) {
				posFloat.setX(posFloat.getX() + 1);
				pos.addX(-1);
				// move
			}
			if (posFloat.getY() > 1) {
				posFloat.setY(posFloat.getY() - 1);
				pos.addY(1);
				// move
			}
			if (posFloat.getY() < 0) {
				posFloat.setY(posFloat.getY() + 1);
				pos.addY(-1);
				// move
			}
			if (posFloat.getZ() > 1) {
				posFloat.setZ(posFloat.getZ() - 1);
				pos.addZ(1);
				// move
			}
			if (posFloat.getZ() < 0) {
				posFloat.setZ(posFloat.getZ() + 1);
				pos.addZ(-1);
				// move
			}

			if (pos.getX() >= 8) {
				Chunk nextChunk = ChunkLoader.getChunk(currentChunk.renderPos.getX() + 1, currentChunk.renderPos.getY(), currentChunk.renderPos.getZ());
				if (moveTo(nextChunk))
					pos.addX(-8);
				else
					pos.addX(-1);
			}
			if (pos.getX() < 0) {
				Chunk nextChunk = ChunkLoader.getChunk(currentChunk.renderPos.getX() - 1, currentChunk.renderPos.getY(), currentChunk.renderPos.getZ());
				if (moveTo(nextChunk))
					pos.addX(8);
				else
					pos.addX(1);
			}
			if (pos.getY() >= 8) {
				Chunk nextChunk = ChunkLoader.getChunk(currentChunk.renderPos.getX(), currentChunk.renderPos.getY() + 1, currentChunk.renderPos.getZ());
				if (moveTo(nextChunk))
					pos.addY(-8);
				else
					pos.addY(-1);
			}
			if (pos.getY() < 0) {
				Chunk nextChunk = ChunkLoader.getChunk(currentChunk.renderPos.getX(), currentChunk.renderPos.getY() - 1, currentChunk.renderPos.getZ());
				if (moveTo(nextChunk))
					pos.addY(8);
				else
					pos.addY(1);
			}
			if (pos.getZ() >= 8) {
				Chunk nextChunk = ChunkLoader.getChunk(currentChunk.renderPos.getX(), currentChunk.renderPos.getY(), currentChunk.renderPos.getZ() + 1);
				if (moveTo(nextChunk))
					pos.addZ(-8);
				else
					pos.addZ(-1);
			}
			if (pos.getZ() < 0) {
				Chunk nextChunk = ChunkLoader.getChunk(currentChunk.renderPos.getX(), currentChunk.renderPos.getY(), currentChunk.renderPos.getZ() - 1);
				if (moveTo(nextChunk))
					pos.addZ(8);
				else
					pos.addZ(1);
			}
		} else if (MODE == MODE_CHUNK) {
			
			if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_R) || Keyboard.isKeyDown(Keyboard.KEY_F)) {
				if(hasPressed) return;
				hasPressed = true;
			} else{
				hasPressed = false;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
				if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
					makeChunkAt(currentChunk.renderPos.getX() + 1, currentChunk.renderPos.getY(), currentChunk.renderPos.getZ());
				} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
					makeChunkAt(currentChunk.renderPos.getX() - 1, currentChunk.renderPos.getY(), currentChunk.renderPos.getZ());

				} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
					makeChunkAt(currentChunk.renderPos.getX(), currentChunk.renderPos.getY() + 1, currentChunk.renderPos.getZ());

				} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
					makeChunkAt(currentChunk.renderPos.getX(), currentChunk.renderPos.getY() - 1, currentChunk.renderPos.getZ());

				} else if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
					makeChunkAt(currentChunk.renderPos.getX(), currentChunk.renderPos.getY(), currentChunk.renderPos.getZ() + 1);

				} else if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
					makeChunkAt(currentChunk.renderPos.getX(), currentChunk.renderPos.getY(), currentChunk.renderPos.getZ() - 1);

				}
			}
		}

		float scale = Endogen.WIDTH / 32f;
		float x = (pos.getX() + posFloat.getX() + currentChunk.renderPos.getX() * 8) * scale;
		float y = (pos.getY() + posFloat.getY() + currentChunk.renderPos.getY() * 8) * scale;
		float z = (pos.getZ() + posFloat.getZ() + currentChunk.renderPos.getZ() * 8) * scale;
		Endogen.camera.setPos1(new Vertex3f(MathUtil.getXSpeed(rotation, 300) + x, MathUtil.getYSpeed(rotation, 300) + y, 300 + z));
		Endogen.camera.setPos2(new Vertex3f(x, y, z));
	}
	
	public static boolean makeChunkAt(int x, int y, int z){
		if(ChunkLoader.getChunk(x, y, z) == null){
			String name = "c";
			boolean finished = false;
			int num = 0;
			while(!finished){
				for(int i = 0; i < ChunkLoader.loadedChunks.size(); i++){
					if(ChunkLoader.loadedChunks.get(i).ID.startsWith(name+num)) {
						num++;
					}
				}
				finished = true;
				for(int i = 0; i < ChunkLoader.loadedChunks.size(); i++){
					if(ChunkLoader.loadedChunks.get(i).ID.startsWith(name+num)) {
						finished = false;
					}
				}
			}
			Chunk chunk = new Chunk(name+num, new Vertex3i(x,y,z), false);
			Chunk Adj = null;
			Adj = ChunkLoader.getChunk(chunk.renderPos.getX()+1,chunk.renderPos.getY(),chunk.renderPos.getZ());
			if(Adj != null){
				chunk.north = Adj;
				Adj.south = chunk;
			}
			Adj = ChunkLoader.getChunk(chunk.renderPos.getX(),chunk.renderPos.getY()-1,chunk.renderPos.getZ());
			if(Adj != null){
				chunk.east = Adj;
				Adj.west = chunk;
			}
			Adj = ChunkLoader.getChunk(chunk.renderPos.getX()-1,chunk.renderPos.getY(),chunk.renderPos.getZ());
			if(Adj != null){
				chunk.south = Adj;
				Adj.north = chunk;
			}
			Adj = ChunkLoader.getChunk(chunk.renderPos.getX(),chunk.renderPos.getY()+1,chunk.renderPos.getZ());
			if(Adj != null){
				chunk.west = Adj;
				Adj.east = chunk;
			}
			Adj = ChunkLoader.getChunk(chunk.renderPos.getX(),chunk.renderPos.getY(),chunk.renderPos.getZ()+1);
			if(Adj != null){
				chunk.up = Adj;
				Adj.down = chunk;
			}
			Adj = ChunkLoader.getChunk(chunk.renderPos.getX(),chunk.renderPos.getY(),chunk.renderPos.getZ()-1);
			if(Adj != null){
				chunk.down = Adj;
				Adj.up = chunk;
			}
			ChunkLoader.loadedChunks.add(chunk);
			ChunkLoader.usedChunks.add(chunk);
			chunk.startedLoading = true;
			chunk.startedLoading = false;
		}
		return false;
	}

	public static boolean moveTo(Chunk nextChunk) {
		if (nextChunk != null) {
			currentChunk = nextChunk;
			ChunkLoader.central = nextChunk;
			System.out.println(currentChunk.ID + " at " + currentChunk.renderPos.getX() + "," + currentChunk.renderPos.getY() + "," + currentChunk.renderPos.getZ());
			return true;
		}
		return false;
	}
	
	public static void changeCell(){
		
	}

	public static void render() {
		float r = (float) (System.currentTimeMillis() % 630 / 630f);
		float g = (float) (System.currentTimeMillis() % 840 / 840f);
		float b = (float) (System.currentTimeMillis() % 910 / 910f);
		float x = currentChunk.renderPos.getX() * 8;
		float y = currentChunk.renderPos.getY() * 8;
		float z = currentChunk.renderPos.getZ() * 8;
		Render3d.cube(pos.getX() - .1f + x, pos.getY() - .1f + y, pos.getZ() - .1f + z, pos.getX() + 1.1f + x, pos.getY() + 1.1f + y, pos.getZ() + 1.1f + z, r, g, b, 1, Simulation.Assets.SELECTION);
	}

}
