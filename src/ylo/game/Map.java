package ylo.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Map {
	public void updateToEachTick() {
		
	}
	
	public void render(Graphics g) {		
		g.setColor(Color.white);
		g.fillRect(0, 0, Config.WIDTH_SCALABLE, Config.HEIGHT_SCALABLE);
		
		g.setColor(Color.black);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.drawString(Config.NAME, (Config.WIDTH-40)/2, 10);
		
		g.setColor(Color.GREEN);
		g.fillOval(50, 50, 50, 50);
		
	}
}
