package com.endoplasm.game;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

import com.endoplasm.engine.VectorMath;
import com.endoplasm.engine.Vertex3i;

public class ChunkLoader implements Runnable{
	
	public final static String chunkStart = "chunk";
	public final static String chunkEnd = "endChunk";
	public static final int LOADRANGE = 5;

	// format ID:lineNum:northID:eastID:southID:westID:upID:downID
	public static ArrayList<String> HASH = new ArrayList<String>();
	public static String File;
	
	// List of chunks currently accessible to the game, 
	// despite the names not all these chunks are finished loading, but they all should of started loading.
	public static ArrayList<Chunk> loadedChunks = new ArrayList<Chunk>();
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
					HASH.add(chunkID + ":" + reader.getLineNumber() + ":"+bits[1] + ":"+bits[2] + ":"+bits[3] + ":"+bits[4] + ":"+bits[5] + ":"+bits[6]);
					System.out.println(chunkID + ":" + reader.getLineNumber());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Chunk TransitionToChunk(String chunkID){
		usedChunks.clear();
		central = null;
		Chunk firstChunk = makeChunk(chunkID, new Vertex3i(0,0,0));
		central = firstChunk;
		return firstChunk;
	}
	
	public static void manageChunks(){
		if(central != null){
			for(int i = 0; i < usedChunks.size(); i++){
				Chunk chunk = usedChunks.get(i);
				loadAdjacentChunks(chunk, central.renderPos.getX(), central.renderPos.getY(), central.renderPos.getZ(), LOADRANGE);
			}
		}else{
			usedChunks.clear();
		}

		for(Chunk chunk: loadedChunks){
			if(!usedChunks.contains(chunk)) chunk.unload();
		}
	}
	
	public static void loadAdjacentChunks(Chunk chunk, int X, int Y, int Z, int Range){
			//down
			if(chunk.renderPos.getZ() < Z+Range && chunk.renderPos.getZ() > Z-Range)
				if(chunk.down == null && !chunk.downID.equals("n"))
				makeChunk(chunk.downID, VectorMath.add(chunk.renderPos, new Vertex3i(0,0,-1)));
			//up
			if(chunk.renderPos.getZ() < Z+Range && chunk.renderPos.getZ() > Z-Range)
				if(chunk.up == null && !chunk.upID.equals("n"))
				makeChunk(chunk.upID, VectorMath.add(chunk.renderPos, new Vertex3i(0,0,1)));
			//north
			if(chunk.renderPos.getX() < X+Range && chunk.renderPos.getX() > X-Range)
				if(chunk.north == null && !chunk.northID.equals("n"))
				makeChunk(chunk.northID, VectorMath.add(chunk.renderPos, new Vertex3i(1,0,0)));
			//east
			if(chunk.renderPos.getY() < Y+Range && chunk.renderPos.getY() > Y-Range)
				if(chunk.east == null && !chunk.eastID.equals("n"))
				makeChunk(chunk.eastID, VectorMath.add(chunk.renderPos, new Vertex3i(0,-1,0)));
			//south
			if(chunk.renderPos.getX() < X+Range && chunk.renderPos.getX() > X-Range)
				if(chunk.south == null && !chunk.southID.equals("n"))
				makeChunk(chunk.southID, VectorMath.add(chunk.renderPos, new Vertex3i(-1,0,0)));
			//west
			if(chunk.renderPos.getY() < Y+Range && chunk.renderPos.getY() > Y-Range)
				if(chunk.west == null && !chunk.westID.equals("n"))
				makeChunk(chunk.westID, VectorMath.add(chunk.renderPos, new Vertex3i(0,1,0)));
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
		if(ID == null || ID.equals("n")) return null;
		Chunk newChunk = new Chunk(ID, renderPos);
		usedChunks.add(newChunk);
		return newChunk;
	}
	
	public ChunkLoader(String ID, Chunk chunk){
		this.ID = ID;
		this.chunk = chunk;
		
		String Hash = HashFull(ID);
		if(!Hash.equals("n")){
			String[] s = Hash.split(":");
			chunk.northID = s[2];
			chunk.eastID = s[3];
			chunk.southID = s[4];
			chunk.westID = s[5];
			chunk.upID = s[6];
			chunk.downID = s[7];
		}
		Chunk Adj = null;
		Adj = getChunk(chunk.renderPos.getX()+1,chunk.renderPos.getY(),chunk.renderPos.getZ());
		if(Adj != null){
			chunk.north = Adj;
			Adj.south = chunk;
		}
		Adj = getChunk(chunk.renderPos.getX(),chunk.renderPos.getY()-1,chunk.renderPos.getZ());
		if(Adj != null){
			chunk.east = Adj;
			Adj.west = chunk;
		}
		Adj = getChunk(chunk.renderPos.getX()-1,chunk.renderPos.getY(),chunk.renderPos.getZ());
		if(Adj != null){
			chunk.south = Adj;
			Adj.north = chunk;
		}
		Adj = getChunk(chunk.renderPos.getX(),chunk.renderPos.getY()+1,chunk.renderPos.getZ());
		if(Adj != null){
			chunk.west = Adj;
			Adj.east = chunk;
		}
		Adj = getChunk(chunk.renderPos.getX(),chunk.renderPos.getY(),chunk.renderPos.getZ()+1);
		if(Adj != null){
			chunk.up = Adj;
			Adj.down = chunk;
		}
		Adj = getChunk(chunk.renderPos.getX(),chunk.renderPos.getY(),chunk.renderPos.getZ()-1);
		if(Adj != null){
			chunk.down = Adj;
			Adj.up = chunk;
		}
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
		if(lineNum == -1) return;
		try(LineNumberReader reader = new LineNumberReader(new InputStreamReader(Chunk.class.getResourceAsStream(File)))) {
			for(int i = 0; i < lineNum; i++) reader.readLine();
			String line;
			int ColumnNum = 0;
			while ((line = reader.readLine()) != null && !line.equals(chunkEnd)) {
				if(line.startsWith("RC")){
					loadRow(ColumnNum, line);
					ColumnNum++;
				}
			}
			chunk.finishedLoading = true;
		} catch(Exception e){
			System.err.println("Loading error in chunk " + ID);
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
		if(ID == null) return -1;
		for(String s: HASH){
			if(s.startsWith(ID)){
				return Integer.parseInt(s.split(":")[1]);
			}
		}
		return -1;
	}

	public static String HashFull(String ID){
		if(ID == null) return "n";
		for(String s: HASH){
			if(s.startsWith(ID)){
				return s;
			}
		}
		return "n";
	}

	

}
