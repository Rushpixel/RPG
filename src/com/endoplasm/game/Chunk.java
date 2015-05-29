package com.endoplasm.game;

import com.endoplasm.editor.Simulation;
import com.endoplasm.engine.Endogen;
import com.endoplasm.engine.Render3d;
import com.endoplasm.engine.Vertex3i;

public class Chunk {

	public String ID = "SDfsadf";
	public Cell[][][] cells;
	
	public boolean startedLoading = false;
	public boolean finishedLoading = false;
	public Chunk north, south, east, west, up, down;
	public String northID="n", southID="n", eastID="n", westID="n", upID="n", downID="n";
	public Vertex3i renderPos;
	
	public Chunk(String ID, Vertex3i renderPos){
		this.ID = ID;
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
	
	public Chunk(String ID, Vertex3i renderPos, boolean empty){
		this.ID = ID;
		this.renderPos = renderPos;
		cells = new Cell[8][8][8];
		for(int ix = 0; ix < 8; ix++){
			for(int iy = 0; iy < 8; iy++){
				for(int iz = 0; iz < 8; iz++){
					cells[ix][iy][iz] = new Cell();
				}
			}
		}
		if(empty){
			ChunkLoader c = new ChunkLoader(ID, this);
		}
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
					if(cell != null)cell.render(renderPos.getX()*8 + x, renderPos.getY()*8 + y, renderPos.getZ()*8 + z);
					z++;
				}
				y++;
			}
			x++;
		}
	}
	
	public void specialRender(float r, float g, float b){
		Render3d.cube(renderPos.getX()*8, renderPos.getY()*8, renderPos.getZ()*8, renderPos.getX()*8+8, renderPos.getY()*8+8, renderPos.getZ()*8+8, r, g, b, 1, Simulation.Assets.SELECTION);
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
