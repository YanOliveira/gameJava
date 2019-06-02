package ylo.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public abstract class Core extends Canvas implements Runnable{
	protected final int WIDTH = 240;
	protected final int HEIGHT = 160;
	protected final int SCALE = 3;
	protected boolean isRunning;	
	protected JFrame frame;
	protected Thread thread;		
	protected BufferedImage bufferedImage;	
	protected BufferStrategy bufferStrategy;
	
	protected abstract void updateGame();
	protected abstract void render();
	
	protected void initialSetup() {		
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
	
	protected synchronized void start() {
		this.isRunning = true;			
		this.thread.start();
	}
	
	protected synchronized void stop() {		
		try {
			this.isRunning = false;
			this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
}
