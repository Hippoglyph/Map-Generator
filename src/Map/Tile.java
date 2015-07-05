package Map;

import java.awt.Color;

public class Tile {
	
	public final static int WATER = 0;
	public final static int GRASS = 1;
	
	
	private int typeId;
	public int xPos;
	public int yPos;
	
	public Tile(int x, int y){
		typeId = 0;
		xPos = x;
		yPos = y;
	}
	
	public int getTypeId(){
		return typeId;
	}
	
	public void setTypeId(int id){
		typeId = id;
	}
	
	public Color getColor(){
		if(typeId == GRASS)
			return Color.GREEN;
		return Color.BLUE;
	}
	
	public boolean isWater(){
		if(typeId == 0)
			return true;
		return false;
	}
	
}
