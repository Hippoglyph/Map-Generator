package Map;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Map extends JPanel {
	
	private GameMap gameMap;
	
	public Map(){
		makeMap();
	}

	private void makeMap() {
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		setFocusable(true);
		
		gameMap = new GameMap();
		repaint();
	}

	public void paint(Graphics g){
		super.paint(g);
		int tileWidth = super.getWidth()/gameMap.getRowSize()+1;
		int tileHeight = super.getHeight()/gameMap.getColumnSize()+1;
		
		for(int x = 0; x < gameMap.getRowSize(); x++){
			for(int y = 0; y < gameMap.getColumnSize(); y++){
				drawTile(g, x, y, x*tileWidth, y*tileHeight, tileWidth, tileHeight);
			}
		}
		g.dispose();
	}
	
	public void drawTile(Graphics g,int x, int y, int xPos, int yPos, int width, int height){
		g.setColor(gameMap.getTileColor(x, y));
		g.fillRect(xPos, yPos, width, height);
	}
}
