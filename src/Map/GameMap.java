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
	}
	
	private void generateIslands() {
		int numOfIslands = rnd.nextInt(10)+20;
		for(int i = 0; i < numOfIslands; i++)
			createIsland();
	}

	private void createIsland() {
		int x = rnd.nextInt(getRowSize());
		int y = rnd.nextInt(getColumnSize());
		
		if(!tiles[x][y].isWater())
			return;
		
		bfs(tiles[x][y]);
	}
	
	private void bfs(Tile tile){
		Queue<Tile> q = new ArrayDeque<>();
		q.add(tile);
		int count = 0;
		int maxTiles = numOfTiles();
		while (!q.isEmpty()){
			tile = q.remove();
			count++;
			if(count > maxTiles)
				return;
			tile.setTypeId(Tile.GRASS);
			for(int checkX = tile.xPos-1; checkX<tile.xPos+2;checkX++){
				if(checkX < 0 || checkX >= getRowSize())
					continue;
				for(int checkY = tile.yPos-1; checkY<tile.yPos+2;checkY++){
					if(checkY < 0 || checkY >= getColumnSize())
						continue;
					if(!tiles[checkX][checkY].isWater())
						continue;
					if(rnd.nextInt(3) < 1)
						q.add(tiles[checkX][checkY]);
				}
			}
		}
	}

	private int numOfTiles() {
		return getRowSize()*getColumnSize()/(rnd.nextInt(20)+5);
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
