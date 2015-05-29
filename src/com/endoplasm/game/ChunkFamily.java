package com.endoplasm.game;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ChunkFamily {

	public ArrayList<String[]> File = new ArrayList<String[]>();

	public void load(String path) {
		try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(ChunkFamily.class.getResourceAsStream(path)))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith(ChunkLoader.chunkStart)) {
					String chunkS[] = new String[67];
					chunkS[0] = line;
					int lineNum = 1;
					while (!(line = reader.readLine()).startsWith(ChunkLoader.chunkEnd)) {
						chunkS[lineNum] = line;
						lineNum++;
					}
					chunkS[66] = line;
					File.add(chunkS);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 for(String sa[]: File){
			 System.out.println("New Chunk");
			 for(String s: sa){
				 System.out.println(s);
			 }
		 }
	}
	
	public void save(String path){
		try( BufferedWriter writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(path), "utf-8"));){
			 for(String sa[]: File){
				 writer.write("\n");
				 for(String s: sa){
					 writer.write(s+"\n");
				 }
			 }
		} catch(Exception e){
			e.printStackTrace();
		}
	}

}
