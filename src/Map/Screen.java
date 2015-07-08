package Map;

import java.awt.event.KeyEvent;

public class Screen {

	private boolean scDown, scUp,right,left,up,down=false;
	private Map map;
	
	public Screen(Map map){
		this.map = map;
	}
	
	public void update(){
		pressKeys();
	}
	
	private void pressKeys() {
		int xMulti = 0;
		int yMulti = 0;
		int scroll = 0;
		
		if(right)
			xMulti += 1;
		if(left)
			xMulti -= 1;
		if(up)
			yMulti -= 1;
		if(down)
			yMulti += 1;
		if(scUp)
			scroll -= 1;
		if(scDown)
			scroll += 1;
		
		if(xMulti != 0)
			map.addToPosX(xMulti);
		if(yMulti != 0)
			map.addToPosY(yMulti);
		if(scroll != 0)
			map.addToScroll(scroll);
	}

	public void keyPressed(int keyCode) {
		
		if(keyCode == KeyEvent.VK_RIGHT)
			right = true;
		else if(keyCode == KeyEvent.VK_LEFT)
			left = true;
		else if(keyCode == KeyEvent.VK_UP)
			up = true;
		else if(keyCode == KeyEvent.VK_DOWN)
			down = true;
		else if(keyCode == KeyEvent.VK_NUMPAD1)
			scDown = true;
		else if(keyCode == KeyEvent.VK_NUMPAD4)
			scUp = true;
	}

	public void keyReleased(int keyCode) {
		if(keyCode == KeyEvent.VK_RIGHT)
			right = false;
		else if(keyCode == KeyEvent.VK_LEFT)
			left = false;
		else if(keyCode == KeyEvent.VK_UP)
			up = false;
		else if(keyCode == KeyEvent.VK_DOWN)
			down = false;
		else if(keyCode == KeyEvent.VK_NUMPAD1)
			scDown = false;
		else if(keyCode == KeyEvent.VK_NUMPAD4)
			scUp = false;
		else if(keyCode == KeyEvent.VK_NUMPAD5)
			map.newMap();
	}

}
