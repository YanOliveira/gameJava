package ylo.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{	
	private final int WIDTH = 160;
	private final int HEIGHT = 120;
	private final int SCALE = 4;
	
	private JFrame frame;
	private Thread thread;	
	private boolean isRunning;
	private BufferedImage bufferedImage;
	
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		this.initFrame();					
		bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
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
	
	public synchronized void start() {
		this.isRunning = true;
		thread = new Thread(this);		
		thread.start();
	}
	
	public synchronized void stop() {
		
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
	}
	
	public void updateGame() {
		
	}
	
	public void render() {
		BufferStrategy bufferStrategy = this.getBufferStrategy();
		if(bufferStrategy == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics graphic = bufferedImage.getGraphics();
		graphic.setColor(new Color(19, 19, 19));
		graphic.fillRect(0, 0, WIDTH, HEIGHT);
		graphic = bufferStrategy.getDrawGraphics();
		graphic.drawImage(bufferedImage, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		bufferStrategy.show();			
	}
	
}
