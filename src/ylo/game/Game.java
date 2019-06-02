package ylo.game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Core{		
	private Player player;
	private Map map;
	public Game() {	
		super.initialSetup();						
		
		/*
		 * INSTANCIAS DOS COMPONENTES QUE SERÂO UTILIZADOS
		 * */
		this.player = new Player();
		this.map = new Map();
		/* ------------------------------------------------------------------------- */
	}		
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}			
	
	protected void updateComponentsToEachTick() {
		/*
		 * COMPONENTES QUE SERÃO ATUALIZADOS
		 * */
		this.player.updateToEachTick();
		/* ------------------------------------------------------------------------- */
		
	}	
	
	protected void renderAllComponents() {
		BufferStrategy bufferStrategy = this.getBufferStrategy();
		if(bufferStrategy == null) {
			this.createBufferStrategy(3);
			return;
		}				
		Graphics graphic = bufferedImage.getGraphics();
		
		/*
		 * COMPONENTES QUE SERÃO RENDERIZADOS
		 * */
		this.map.render(graphic);													
		this.player.render(graphic);		
		/* ------------------------------------------------------------------------- */
						
		graphic.dispose();
		graphic = bufferStrategy.getDrawGraphics();
		graphic.drawImage(bufferedImage, 0, 0, Config.WIDTH_SCALABLE, Config.HEIGHT_SCALABLE, null);		
		bufferStrategy.show();			
	}	
}
