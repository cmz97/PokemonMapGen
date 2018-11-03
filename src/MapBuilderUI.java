

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class MapBuilderUI extends JFrame implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private BufferedImage selector;
	private BufferedImage selected;
	public static Tiles[][] tiles;
	public final int WIDTH = 1200;
	public final int HEIGHT = 600;
	public final int pixelDim = 16;

	public JLabel[][] imageLabel;
	public JLabel selectorIcon;
	public JLabel selectedIcon;
 
	private BufferedImage emptytile;
	public static int[][] blockID;
	private static final int WIDTH_TILES = 50;
	private static final int HEIGHT_TILES = 50;
	public static CollisionMapUI colli;
	public static MyCanvas c;

	

	public static void main(String[] args){
		blockID = new int[1][2];
		blockID[0][0] = 3;
		blockID[0][1] = 3;
		
		MyCanvas c = new MyCanvas(WIDTH_TILES,HEIGHT_TILES);
		c.setVisible(true);
		colli = new CollisionMapUI(WIDTH_TILES,HEIGHT_TILES);
		colli.setVisible(false);
		MapBuilderUI m = new MapBuilderUI();
		m.setVisible(true);
		
	}

	public MapBuilderUI(){
		imageLoader();

		this.setTitle("RPG Map Builder");
		this.setSize(((image.getWidth()+1)/(pixelDim+1))*16, ((image.getHeight()+1)/(pixelDim+1))*16+22);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setAlwaysOnTop(true);
		
				
		imageSlicer();
		this.setVisible(true);

	}

	
	private void imageLoader(){
		try {
			image = ImageIO.read(getClass().getClassLoader().getResource("resource/xt.png").openStream());
			selector = ImageIO.read(getClass().getClassLoader().getResource("resource/Selector.png").openStream());
			selected = ImageIO.read(getClass().getClassLoader().getResource("resource/Selected.png").openStream());
			emptytile = ImageIO.read(getClass().getClassLoader().getResource("resource/empty.png").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void imageSlicer(){
		tiles = new Tiles[(image.getHeight()+1)/(pixelDim+1)][(image.getWidth()+1)/(pixelDim+1)];
		imageLabel = new JLabel[tiles.length][tiles[0].length]; 
		selectorIcon = new JLabel(new ImageIcon(selector));
		selectedIcon = new JLabel(new ImageIcon(selected));
		this.add(selectorIcon);
		this.add(selectedIcon);

		for(int i = 0; i < tiles.length;i++){
			for(int j = 0; j<tiles[0].length; j++){
				tiles[i][j] = new Tiles(image.getSubimage(j*16+j, i*16+i, pixelDim, pixelDim));
				imageLabel[i][j] = new JLabel(tiles[i][j].getIcon()); 
				imageLabel[i][j].setBounds(j*16, i*16, 16, 16);
				this.add(imageLabel[i][j]);
				imageLabel[i][j].addMouseListener(this);
			}
		}
		
		tiles[tiles.length-1][tiles[0].length-1] = new Tiles(emptytile);

	}




	@Override
	public void mouseClicked(MouseEvent e) {}

	@SuppressWarnings("deprecation")
	public void mousePressed(MouseEvent e) {
		this.setCursor(DEFAULT_CURSOR);
		for(int i = 0; i < imageLabel.length;i++){
			for(int j = 0; j<imageLabel[0].length; j++){
				if(e.getSource().equals(imageLabel[i][j])){
					selectedIcon.setBounds(j*16, i*16, 16, 16);
					blockID[0][0] = i;
					blockID[0][1] = j;
				}
			}
		}

	}

	@Override
	@SuppressWarnings("deprecation")
	public void mouseReleased(MouseEvent e) {
		this.setCursor(HAND_CURSOR);


	}

	@Override
	@SuppressWarnings("deprecation")

	public void mouseEntered(MouseEvent e) {
		this.setCursor(HAND_CURSOR);
		for(int i = 0; i < imageLabel.length;i++){
			for(int j = 0; j<imageLabel[0].length; j++){
				if(e.getSource().equals(imageLabel[i][j])){
					selectorIcon.setBounds(j*16, i*16, 16, 16);
				}
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}


} 
