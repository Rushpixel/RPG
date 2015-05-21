package com.endoplasm.game;

import com.endoplasm.engine.Vertex3i;

public class Chunk {

	public String ID;
	public Cell[][][] cells;
	
	public boolean startedLoading = false;
	public boolean finishedLoading = false;
	public Chunk north, south, east, west, up, down;
	public String northID, southID, eastID, westID, upID, downID;
	public Vertex3i renderPos;
	
	public Chunk(String ID, Vertex3i renderPos){
		this.upID = upID;
		this.downID = downID;
		this.renderPos = renderPos;
		cells = new Cell[8][8][8];
		for(int ix = 0; ix < 8; ix++){
			for(int iy = 0; iy < 8; iy++){
				for(int iz = 0; iz < 8; iz++){
					cells[ix][iy][iz] = new Cell();
				}
			}
		}
		ChunkLoader c = new ChunkLoader(ID, this);
	}
	
	public void update(){
		for(Cell[][] cellx: cells){
			for(Cell[] celly: cellx){
				for(Cell cell: celly){
					if(cell != null)cell.update();
				}
			}
		}
	}
	
	public void render(){
		float x = 0;
		for(Cell[][] cellx: cells){
			float y = 0;
			for(Cell[] celly: cellx){
				float z = 0;
				for(Cell cell: celly){
					if(cell != null)cell.render(renderPos.getX() + x, renderPos.getY() + y, renderPos.getZ() + z);
					z++;
				}
				y++;
			}
			x++;
		}
	}
	
	public Cell getCell(int x, int y, int z){
		if(x >= 8){
			return north != null ? north.getCell(x-8, y, z) : null;
		}
		if(x < 0){
			return south != null ? south.getCell(x+8, y, z) : null;
		}
		if(y >= 8){
			return east != null ? east.getCell(x, y-8, z) : null;
		}
		if(y < 0){
			return west != null ? west.getCell(x, y+8, z) : null;
		}
		if(z >= 8){
			return up != null ? up.getCell(x, y, z-8) : null;
		}
		if(z < 0){
			return down != null ? down.getCell(x, y, z+8) : null;
		}
		return cells[x][y][z];
	}
	
	public void unload(){
		for(int ix = 0; ix < 8; ix++){
			for(int iy = 0; iy < 8; iy++){
				for(int iz = 0; iz < 8; iz++){
					cells[ix][iy][iz].unload();
					cells[ix][iy][iz] = null;
				}
			}
		}
	}
	
}
