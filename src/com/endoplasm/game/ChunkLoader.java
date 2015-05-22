package com.endoplasm.game;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

import com.endoplasm.engine.Vertex3i;

public class ChunkLoader implements Runnable{
	
	public final static String chunkStart = "chunk";
	public final static String chunkEnd = "chunkEnd";
	public static final int LOADRANGE = 5;

	// format ID:lineNum:northID:eastID:southID:westID:upID:downID
	public static ArrayList<String> HASH = new ArrayList<String>();
	public static String File;
	
	// List of chunks currently accessible to the game, 
	// despite the names not all these chunks are finished loading, but they all should of started loading.
	private static ArrayList<Chunk> loadedChunks = new ArrayList<Chunk>();
	// A list of chunks that should remain loaded, 
	// if a chunk is in the loaded chunks list but not this list it should be unloaded.
	public static ArrayList<Chunk> usedChunks = new ArrayList<Chunk>();
	// 
	public static Chunk central;

	public static void preLoad(String File) {
		ChunkLoader.File = File;
		System.out.println("Preloading map chunks");
		

		try(LineNumberReader reader = new LineNumberReader(new InputStreamReader(Chunk.class.getResourceAsStream(File)))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if(line.startsWith(chunkStart)){
					String[] bits = line.split(":")[1].split(",");
					String chunkID = bits[0];
					HASH.add(chunkID + ":" + reader.getLineNumber() + bits[1] + bits[2] + bits[3] + bits[4] + bits[5] + bits[6]);
					System.out.println(chunkID + ":" + reader.getLineNumber());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void TransitionToChunk(String chunkID){
		usedChunks.clear();
		central = null;
		Chunk firstChunk = makeChunk(chunkID, new Vertex3i(0,0,0));
		central = firstChunk;
	}
	
	public static void manageChunks(){
		if(central != null){
			for(int x = -LOADRANGE; x < LOADRANGE; x++){
				for(int y = -LOADRANGE; y < LOADRANGE; y++){
					for(int z = -LOADRANGE; z < LOADRANGE; z++){
						Chunk chunk = getChunk(x, y, z);
						if(chunk == null){
							loadChunkFromAdjacent(x,y,z);
						}
					}
				}
			}
		}else{
			usedChunks.clear();
		}

		for(Chunk chunk: loadedChunks){
			if(!usedChunks.contains(chunk)) chunk.unload();
		}
	}
	
	public static void loadChunkFromAdjacent(int x, int y, int z){
		for(Chunk chunk: usedChunks){
			//north
			if(chunk.renderPos.getX() == x && chunk.renderPos.getY() == y && chunk.renderPos.getZ() == z){
				//return chunk;
			}
		}
	}
	
	public static Chunk getChunk(int x, int y, int z){
		for(Chunk chunk: usedChunks){
			if(chunk.renderPos.getX() == x && chunk.renderPos.getY() == y && chunk.renderPos.getZ() == z){
				return chunk;
			}
		}
		return null;
	}
	
	public static Chunk makeChunk(String ID, Vertex3i renderPos){
		Chunk newChunk = new Chunk(ID, renderPos);
		usedChunks.add(newChunk);
		return newChunk;
	}
	
	public ChunkLoader(String ID, Chunk chunk){
		this.ID = ID;
		this.chunk = chunk;
		loadedChunks.add(chunk);
		Thread serverThread = new Thread(this);
		serverThread.start();
	}
	
	public String ID;
	public Chunk chunk;
	@Override
	public void run() {
		chunk.startedLoading = true;
		int lineNum = Hash(ID);
		try(LineNumberReader reader = new LineNumberReader(new InputStreamReader(Chunk.class.getResourceAsStream(File)))) {
			for(int i = 0; i < lineNum; i++) reader.readLine();
			String line;
			int ColumnNum = 0;
			while ((line = reader.readLine()) != null) {
				if(line.startsWith("RC")){
					loadRow(ColumnNum, line);
					ColumnNum++;
				}
			}
			chunk.finishedLoading = true;
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void loadRow(int column, String line){
		line = line.replaceFirst("RC", "");
		String cells[] = line.split(",");
		int i = 0;
		for(String s: cells){
			chunk.cells[column%8][column/8][i].loadFromString(s);
			i++;
		}
	}
	
	public static int Hash(String ID){
		for(String s: HASH){
			if(s.startsWith(ID)){
				return Integer.parseInt(s.split(":")[1]);
			}
		}
		return -1;
	}



	

}
