package nl.tudelft.jpacman.sprite; import java.awt.Graphics;

import nl.tudelft.jpacman.board.Board; import static nl.dennis.api.CoverageChecker.hit; public class EmptySprite implements Sprite {

	@Override
	public void draw(Graphics g, int x, int y, int width, int height) {
		// nothing to draw.
	}
	
	public void x( Board b ) {
	  
	hit("nl.tudelft.jpacman.sprite.EmptySprite","x",1);}

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
