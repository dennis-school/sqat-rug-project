package nl.tudelft.jpacman.sprite;

import java.awt.Graphics;

import nl.tudelft.jpacman.board.Board;

/**
 * Empty Sprite which does not contain any data. When this sprite is drawn,
 * nothing happens.
 * 
 * @author Jeroen Roosen 
 */
public class EmptySprite implements Sprite {

	@Override
	public void draw(Graphics g, int x, int y, int width, int height) {
		// nothing to draw.
	}
	
	public void x( Board b ) {
	  
	}

	@Override
	public Sprite split(int x, int y, int width, int height) {
		return new EmptySprite();
	}

	@Override
	public int getWidth() {
		return 0;
	}

	@Override
	public int getHeight() {
		return 0;
	}

}
