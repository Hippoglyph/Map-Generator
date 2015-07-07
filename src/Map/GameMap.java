package Map;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

public class GameMap {
	
	private Tile[][] tiles;
	private int mapSize = 256;
	private Random rnd;
	
	public GameMap(){
		rnd = new Random();
		createMap();
	}

	private void createMap() {
		tiles = new Tile[mapSize][mapSize];
		for(int x = 0; x < mapSize; x++){
			for(int y = 0; y < mapSize; y++){
				tiles[x][y] = new Tile(x,y);
			}
		}
		generateIslands();
		cleanUpSingleWaterTiles();
		generateLakes();
		generateShallowWater();
	}

	private void generateLakes() {
		int numOfLakes = rnd.nextInt(13)+2;
		for(int i = 0; i < numOfLakes; i++)
			createLakes();
	}

	private void createLakes() {
		int x = rnd.nextInt(getRowSize());
		int y = rnd.nextInt(getColumnSize());
		
		int tries = 0;
		while(tiles[x][y].getTypeId() != Tile.GRASS){
			x = rnd.nextInt(getRowSize());
			y = rnd.nextInt(getColumnSize());
			if(tries > 10)
				return;
			tries++;
		}
		
		lakebfs(tiles[x][y]);
	}
	
	private void lakebfs(Tile tile){
		Queue<Tile> q = new ArrayDeque<>();
		q.add(tile);
		int count = 0;
		int maxTiles = numOfLakeTiles();
		while (!q.isEmpty()){
			tile = q.remove();
			tile.setTypeId(Tile.WATER);
			count++;
			if(count > maxTiles)
				return;
			for(int checkX = tile.xPos-1; checkX<tile.xPos+2;checkX++){
				if(!isInXBounds(checkX))
					continue;
				for(int checkY = tile.yPos-1; checkY<tile.yPos+2;checkY++){
					if(!isInYBounds(checkY))
						continue;
					if(tiles[checkX][checkY].isWater())
						continue;
					if(rnd.nextInt(3) < 1)
						q.add(tiles[checkX][checkY]);
				}
			}
		}
	}

	private int numOfLakeTiles() {
		return getRowSize()*getColumnSize()/(rnd.nextInt(100)+5);
	}

	private void generateIslands() {
		int numOfIslands = rnd.nextInt(10)+15;
		for(int i = 0; i < numOfIslands; i++)
			createIsland();
	}

	private void createIsland() {
		int x = rnd.nextInt(getRowSize());
		int y = rnd.nextInt(getColumnSize());
		
		int tries = 0;
		while(!tiles[x][y].isWater()){
			x = rnd.nextInt(getRowSize());
			y = rnd.nextInt(getColumnSize());
			if(tries > 10)
				return;
			tries++;
		}
		
		bfs(tiles[x][y]);
	}
	
	private void bfs(Tile tile){
		Queue<Tile> q = new ArrayDeque<>();
		q.add(tile);
		int count = 0;
		int maxTiles = numOfTiles();
		int type = getTileType(tile);
		while (!q.isEmpty()){
			tile = q.remove();
			tile.setTypeId(type);
			count++;
			if(count > maxTiles)
				return;
			for(int checkX = tile.xPos-1; checkX<tile.xPos+2;checkX++){
				if(!isInXBounds(checkX))
					continue;
				for(int checkY = tile.yPos-1; checkY<tile.yPos+2;checkY++){
					if(!isInYBounds(checkY))
						continue;
					if(!tiles[checkX][checkY].isWater())
						continue;
					if(rnd.nextInt(3) < 1)
						q.add(tiles[checkX][checkY]);
				}
			}
		}
	}

	private int getTileType(Tile tile) {
		if(tile.yPos < getColumnSize()*0.1 || tile.yPos > getColumnSize()*0.9)
			return Tile.ICE;
		if(tile.yPos < getColumnSize()*0.4 || tile.yPos > getColumnSize()*0.6){
			if(rnd.nextInt(3) < 1)
				return Tile.STONE;
		}
		if(tile.yPos < getColumnSize()*0.6 && tile.yPos > getColumnSize()*0.4){
			if(rnd.nextInt(3) < 1)
				return Tile.SAND;
		}
		return Tile.GRASS;
	}
	
	private void generateShallowWater() {
		for(int x = 0; x < getRowSize(); x++){
			for(int y = 0; y < getColumnSize(); y++){
				if(!tiles[x][y].isWater())
					continue;
				if(hasNonWaterNeigbor(tiles[x][y]))
					tiles[x][y].setTypeId(Tile.SHALLOW_WATER);
			}
		}
	}

	private boolean hasNonWaterNeigbor(Tile tile) {
		for(int checkX = tile.xPos-1; checkX<tile.xPos+2;checkX++){
			if(!isInXBounds(checkX))
				continue;
			for(int checkY = tile.yPos-1; checkY<tile.yPos+2;checkY++){
				if(!isInYBounds(checkY))
					continue;
				if(!tiles[checkX][checkY].isWater())
					return true;
			}
		}
		for(int checkX = tile.xPos-2; checkX<tile.xPos+3;checkX++){
			if(!isInXBounds(checkX))
				continue;
			for(int checkY = tile.yPos-2; checkY<tile.yPos+3;checkY++){
				if(!isInYBounds(checkY))
					continue;
				if(!tiles[checkX][checkY].isWater()){
					if(rnd.nextInt(2) < 1)
						return true;
				}
			}
		}
		return false;
	}
	
	private void cleanUpSingleWaterTiles(){
		for(int x = 0; x < getRowSize(); x++){
			for(int y = 0; y < getColumnSize(); y++){
				if(!tiles[x][y].isWater())
					continue;
				Tile tile = tiles[x][y];
				int count = 0;
				for(int checkX = tile.xPos-1; checkX<tile.xPos+2;checkX++){
					if(!isInXBounds(checkX))
						continue;
					for(int checkY = tile.yPos-1; checkY<tile.yPos+2;checkY++){
						if(!isInYBounds(checkY))
							continue;
						if(!tiles[checkX][checkY].isWater())
							count++;
					}
				}
				if(count > 6){//If has 6 non water neighbors
					tile.setTypeId(getNeigborTile(tile));
				}
			}
		}
	}

	private int getNeigborTile(Tile tile) {
		for(int checkX = tile.xPos-1; checkX<tile.xPos+2;checkX++){
			if(!isInXBounds(checkX))
				continue;
			for(int checkY = tile.yPos-1; checkY<tile.yPos+2;checkY++){
				if(!isInYBounds(checkY))
					continue;
				if(!tiles[checkX][checkY].isWater())
					return tiles[checkX][checkY].getTypeId();
			}
		}
		return 0;//should not happend
	}
	
	public boolean isInXBounds(int x){
		if(x < 0 || x >= getRowSize())
			return false;
		return true;
	}
	
	public boolean isInYBounds(int y){
		if(y< 0 || y >= getColumnSize())
			return false;
		return true;
	}

	private int numOfTiles() {
		return (int)1.5*getRowSize()*getColumnSize()/(rnd.nextInt(20)+5);
	}

	public int getRowSize(){
		return mapSize;
	}
	
	public int getColumnSize(){
		return mapSize;
	}
	
	public Color getTileColor(int x, int y){
		return tiles[x][y].getColor();
	}
}
