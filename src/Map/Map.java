package Map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Map extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private GameMap gameMap;
	private Screen screen;
	private int posX,posY,scroll;
	private Timer timer;
	
	public Map(){
		screen = new Screen(this);
		makeMap();
		addKeyListener(new TAdapter());
		
		timer = new Timer(1000/60, this);
		timer.start();
	}

	private void makeMap() {
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		setFocusable(true);
		
		gameMap = new GameMap();
		scroll = 100;
		posX = gameMap.getRowSize()/2;
		posY = gameMap.getColumnSize()/2;
	}
	
	public void paint(Graphics g){
		super.paint(g);
		int tileWidth = super.getWidth()/(scroll*2)+1;
		int tileHeight = super.getHeight()/(scroll*2)+1;
		int screenX = -1;
		int screenY = -1;
		for(int x = posX-scroll; x < posX+scroll; x++){
			screenX++;
			screenY=-1;
			for(int y = posY-scroll; y < posY+scroll; y++){
				screenY++;
				drawTile(g, x, y, screenX*tileWidth, screenY*tileHeight, tileWidth, tileHeight);
			}
		}	
	}
	
	public void drawTile(Graphics g,int x, int y, int xPos, int yPos, int width, int height){
		if(gameMap.isInXBounds(x) && gameMap.isInYBounds(y)){
			g.setColor(gameMap.getTileColor(x, y));
			g.fillRect(xPos, yPos, width, height);
		}
	}
	
	public void addToPosX(int n){
		posX = posX+n;
	}
	
	public void addToPosY(int n){
		posY = posY+n;
	}
	
	public void addToScroll(int n){
		scroll = scroll + n;
	}
	
	private class TAdapter extends KeyAdapter {
		public void keyReleased(KeyEvent e){
			screen.keyReleased(e.getKeyCode());
		}
		
		public void keyPressed(KeyEvent e){
			screen.keyPressed(e.getKeyCode());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		screen.update();
		repaint();
	}
}
