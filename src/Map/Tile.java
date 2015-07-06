package Map;

import java.awt.Color;

public class Tile {
	
	public final static int WATER = 0;
	public final static int GRASS = 1;
	public final static int ICE = 2;
	public final static int STONE = 3;
	public final static int SAND = 4;
	public final static int SHALLOW_WATER = 5;
	
	
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
			return new Color(20,236, 10);
		if(typeId == ICE)
			return new Color(204, 242, 243);
		if(typeId == STONE)
			return new Color(139,141,122);
		if(typeId == SAND)
			return new Color(239, 221, 111);
		if(typeId == SHALLOW_WATER)
			return new Color(79, 213, 214);
		return Color.BLUE;
	}
	
	public boolean isWater(){
		if(typeId == WATER || typeId == SHALLOW_WATER)
			return true;
		return false;
	}
	
}
