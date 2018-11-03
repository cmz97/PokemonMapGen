import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Tiles {
	
	private BufferedImage image;
	private ImageIcon tileLabel;
	
	public Tiles(BufferedImage subImage){
		this.image = subImage;
		tileLabel = new ImageIcon(subImage);
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public ImageIcon getIcon(){
		return tileLabel;
	}
	
	
}
