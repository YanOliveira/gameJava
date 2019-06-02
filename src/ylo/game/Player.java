package ylo.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Player{
	private Spritesheet sheet;
	private BufferedImage[] player;		
	private int frames = 0, maxFrames = 20;
	private int curAnimation = 1, maxAnimation = 2;
	
	public Player() {
		sheet = new Spritesheet("/spritesheet.png");	
		player = new BufferedImage[3];
		for(int i=0; i <= 2; i++) {			
			player[i] = sheet.getSprite(i*16, 0, 16, 16);
		}
	}
	
	public void updateToEachTick() {
		this.frames++;
		if(this.frames >= this.maxFrames) {
			this.frames = 0;
			this.curAnimation++;
			if(this.curAnimation > this.maxAnimation ) {
				this.curAnimation = 1;
			}				
		}
		
	}	
	public void render(Graphics g) {			
		Graphics2D graphic2d = (Graphics2D) g;
		graphic2d.rotate(Math.toRadians(0), 48, 48);
		graphic2d.drawImage(player[this.curAnimation], 40, 40, null);
	}
}
