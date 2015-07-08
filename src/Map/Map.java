package Map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
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
		scroll = 50;
		posX = gameMap.getRowSize()/2;
		posY = gameMap.getColumnSize()/2;
	}
	
	public void paint(Graphics g){
		super.paint(g);
		int tileWidth = super.getWidth()/(scroll*2)+1;
		int tileHeight = super.getHeight()/(scroll*2)+1;
		int screenX = -1;
		for(int x = posX-scroll; x < posX+scroll; x++){
			screenX++;
			int screenY=-1;
			for(int y = posY-scroll; y < posY+scroll; y++){
				screenY++;
				drawTile(g, x, y, screenX*tileWidth, screenY*tileHeight, tileWidth, tileHeight);
			}
		}
		drawMinimap(g);
	}
	
	private void drawMinimap(Graphics g) {
		int tileWidth = super.getWidth()/1000;
		int tileHeight = super.getHeight()/1000;
		for(int x = 0; x < gameMap.getRowSize(); x++){
			for(int y = 0; y < gameMap.getColumnSize(); y++){
				drawTile(g,x,y, x*tileWidth, (super.getHeight()-gameMap.getColumnSize()+y)*tileHeight, tileWidth, tileHeight);
			}
		}
		drawMinimapBorder(g, tileWidth, tileHeight);
		drawMinimapPos(g, tileWidth, tileHeight);
	}

	private void drawMinimapPos(Graphics g, int tileWidth, int tileHeight) {
		g.drawRect(tileWidth*(posX-scroll), super.getHeight()-gameMap.getColumnSize()+tileHeight*(posY-scroll), 2*scroll*tileWidth, 2*scroll*tileHeight);
	}

	private void drawMinimapBorder(Graphics g, int tileWidth, int tileHeight) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		Stroke oldS = g2.getStroke();
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(0, super.getHeight()-gameMap.getColumnSize(), tileWidth*gameMap.getColumnSize(), tileHeight*gameMap.getRowSize());
		g2.setStroke(oldS);
	}

	public void drawTile(Graphics g,int x, int y, int xPos, int yPos, int width, int height){
		if(gameMap.isInXBounds(x) && gameMap.isInYBounds(y)){
			g.setColor(gameMap.getTileColor(x, y));
			g.fillRect(xPos, yPos, width, height);
		}
	}
	
	public void addToPosX(int n){
		if((posX+n+scroll) >= gameMap.getRowSize())
			return;
		if((posX+n-scroll) <= 0)
			return;
		posX = posX+n;
	}
	
	public void addToPosY(int n){
		if((posY+n+scroll) >= gameMap.getColumnSize())
			return;
		if((posY+n-scroll) <= 0)
			return;
		posY = posY+n;
	}
	
	public void addToScroll(int n){
		if(n > 0){
			if((posY+n+scroll) >= gameMap.getColumnSize())
				return;
			if((posY+n-scroll) <= 1)
				return;
			if((posX+n+scroll) >= gameMap.getRowSize())
				return;
			if((posX+n-scroll) <= 1)
				return;
		}
		scroll = scroll + n;
	}
	
	public void newMap(){
		gameMap = new GameMap();
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
