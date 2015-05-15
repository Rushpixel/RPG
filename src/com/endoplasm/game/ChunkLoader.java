package com.endoplasm.game;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class ChunkLoader implements Runnable{
	
	public final static String chunkStart = "chunk";
	public final static String chunkEnd = "chunkEnd";

	// format ID:lineNum
	public static ArrayList<String> HASH = new ArrayList<String>();
	public static String File;

	public static void preLoad(String File) {
		ChunkLoader.File = File;
		System.out.println("Preloading map chunks");
		

		try(LineNumberReader reader = new LineNumberReader(new InputStreamReader(Chunk.class.getResourceAsStream(File)))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if(line.startsWith(chunkStart)){
					String chunkID = line.split(":")[1].split(",")[0];
					HASH.add(chunkID + ":" + reader.getLineNumber());
					System.out.println(chunkID + ":" + reader.getLineNumber());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ChunkLoader(String ID, Chunk chunk){
		this.ID = ID;
		this.chunk = chunk;
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
