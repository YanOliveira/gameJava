package ylo.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Core{	
	private int frames = 0, maxFrames = 20;
	private int curAnimation = 1, maxAnimation = 2;	
	private BufferedImage[] player;	
	private Spritesheet sheet;
		
	public Game() {	
		super.initialSetup();
		
		/*
		 * SPRITESHEET
		 * */
		sheet = new Spritesheet("/spritesheet.png");	
		player = new BufferedImage[3];
		for(int i=0; i <= 2; i++) {			
			player[i] = sheet.getSprite(i*16, 0, 16, 16);
		}				
		/* ------------------------------------------------------------------------- */
	}		
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}			
	
	protected void updateGame() {
		this.frames++;
		if(this.frames >= this.maxFrames) {
			this.frames = 0;
			this.curAnimation++;
			if(this.curAnimation > this.maxAnimation ) {
				this.curAnimation = 1;
			}				
		}
	}	
	
	protected void render() {
		BufferStrategy bufferStrategy = this.getBufferStrategy();
		if(bufferStrategy == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		/*
		 * CRIAÇÃO DOS COMPONENTES QUE SERÃO RENDERIZADOS
		 * */
		Graphics graphic = bufferedImage.getGraphics();
		graphic.setColor(Color.white);
		graphic.fillRect(0, 0, WIDTH, HEIGHT);
				
		graphic.setColor(Color.GREEN);
		graphic.fillOval(50, 50, 50, 50);	
				
		graphic.setColor(Color.black);
		graphic.setFont(new Font("arial", Font.BOLD, 8));
		graphic.drawString("GAME #1", (WIDTH-40)/2, 10);
				
		Graphics2D graphic2d = (Graphics2D) graphic;
		graphic2d.rotate(Math.toRadians(0), 48, 48);
		graphic2d.drawImage(player[this.curAnimation], 40, 40, null);		
						
		graphic.dispose();
		graphic = bufferStrategy.getDrawGraphics();
		graphic.drawImage(bufferedImage, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		/* ------------------------------------------------------------------------- */
		
		bufferStrategy.show();			
	}
	
}
