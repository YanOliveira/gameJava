package ylo.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{	
	private final int WIDTH = 240;
	private final int HEIGHT = 160;
	private final int SCALE = 3;
	private boolean isRunning;
	private int frames = 0, maxFrames = 20;
	private int curAnimation = 1, maxAnimation = 2;
	private JFrame frame;
	private Thread thread;		
	private BufferedImage bufferedImage;
	private BufferedImage[] player;	
	private Spritesheet sheet;	
		
	public Game() {
		sheet = new Spritesheet("/spritesheet.png");	
		player = new BufferedImage[3];
		for(int i=0; i <= 2; i++) {			
			player[i] = sheet.getSprite(i*16, 0, 16, 16);
		}				
		
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		this.initFrame();					
		bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		thread = new Thread(this);
	}	
	
	private void initFrame() {		
		frame = new JFrame("Game #1");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}		
	
	private synchronized void start() {
		this.isRunning = true;			
		this.thread.start();
	}
	
	private synchronized void stop() {		
		try {
			this.isRunning = false;
			this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfFlicks = 60.0;
		double ns = 1000000000 / amountOfFlicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while(this.isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >=1 ) {
				this.updateGame();
				this.render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}			
		}
		this.stop();
	}
	
	private void updateGame() {
		this.frames++;
		if(this.frames >= this.maxFrames) {
			this.frames = 0;
			this.curAnimation++;
			if(this.curAnimation > this.maxAnimation ) {
				this.curAnimation = 1;
			}
				
		}
	}	
	
	private void render() {
		BufferStrategy bufferStrategy = this.getBufferStrategy();
		if(bufferStrategy == null) {
			this.createBufferStrategy(3);
			return;
		}
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
		bufferStrategy.show();			
	}
	
}
