import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;


public class CollisionMapUI extends JFrame implements MouseListener, KeyListener, WindowFocusListener{
	
	private static final long serialVersionUID = 1L;
	public static JLabel[][] collisionMapLabel;
	private BufferedImage selector;
	private boolean pressed = false;
	private JLabel canvasSelectorIcon;
	private BufferedImage backGround;
	private BufferedImage emptyTile;
	private BufferedImage temp;
	private JLabel back;
	private JLayeredPane layeredPane;
	private int WIDTH_TILES;
	private int HEIGHT_TILES;
	private int KEY = 1;
	
	
	public CollisionMapUI(int widthTiles,int heightTiles){
		
		WIDTH_TILES = widthTiles;
		HEIGHT_TILES = heightTiles;
	
		this.setTitle("Collision Map Editor");
		this.setLayout(null);

		this.setLocationRelativeTo(MapBuilderUI.c);
		
		this.setBounds(0, 0, widthTiles*16, heightTiles*16+22);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		
		layeredPane = new JLayeredPane();
		layeredPane.setSize(widthTiles*16, heightTiles*16);
		this.addKeyListener(this);
		this.addWindowFocusListener(this);
		this.add(layeredPane);
		TtileLoader();
		
		back = new JLabel();
		back.setBounds(0, 0, WIDTH_TILES*16, HEIGHT_TILES*16);

		
		collisionMapLabel = new JLabel[heightTiles][widthTiles];
		for(int i = 0; i < collisionMapLabel.length;i++){
			for(int j = 0; j<collisionMapLabel[0].length; j++){
				collisionMapLabel[i][j] = new JLabel(" 0");  
				collisionMapLabel[i][j].setBounds(j*16, i*16, 16, 16);
				collisionMapLabel[i][j].setOpaque(false);
				layeredPane.add(collisionMapLabel[i][j], 2);
				collisionMapLabel[i][j].addMouseListener(this);
			}
		}
		
		this.add(layeredPane);
		this.repaint();
		backGroundImageUpdate();
	}
	
	private void TtileLoader(){
		try {
			emptyTile = ImageIO.read(getClass().getClassLoader().getResource("resource/emptyColli.png").openStream());
			selector = ImageIO.read(getClass().getClassLoader().getResource("resource/Selector.png").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		canvasSelectorIcon = new JLabel(new ImageIcon(selector));
	}

	public void backGroundImageUpdate(){
		backGround = new BufferedImage(WIDTH_TILES*16,HEIGHT_TILES*16,BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = backGround.createGraphics();
		int x=0;
		int y=0;
		for(int i=0;i<MyCanvas.canvasImageLabel.length;i++){
			for(int j=0;j<MyCanvas.canvasImageLabel[0].length;j++){
				if(MyCanvas.canvasBlockIDx[i][j].equals("x")){
					g.drawImage(emptyTile, j*16, i*16,null);
				}else{
					x=Integer.parseInt(MyCanvas.canvasBlockIDx[i][j]);
					y=Integer.parseInt(MyCanvas.canvasBlockIDy[i][j]);
					temp = MapBuilderUI.tiles[y][x].getImage();
					AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
					((Graphics2D) g).setComposite(ac);
					g.drawImage(temp, j*16, i*16,null);
				}
				
			}
		}
		g.dispose();
		back.setIcon(new ImageIcon(backGround));
	    
	    this.add(back);
	    this.repaint();
	 }
	

	public void mouseClicked(MouseEvent e) {}


	public void mousePressed(MouseEvent e) {
		pressed = true;
		placeText(e);
		this.repaint();
		
	}
	
	public void mouseExited(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {
			for(int i = 0; i < collisionMapLabel.length;i++){
				for(int j = 0; j<collisionMapLabel[0].length; j++){
					if(e.getSource().equals(collisionMapLabel[i][j])){
						canvasSelectorIcon.setBounds(j*16, i*16, 16, 16);
					}
				}
			}
	
			if(pressed)placeText(e);
	}


	private void placeText(MouseEvent e){
		for(int i = 0; i < collisionMapLabel.length;i++){
			for(int j = 0; j<collisionMapLabel[0].length; j++){
				if(e.getSource().equals(collisionMapLabel[i][j])){
					if(KEY==0){
						collisionMapLabel[i][j].setForeground(Color.BLACK);
						MyCanvas.collisionID[i][j] = 0;
					}else if(KEY==1){
						collisionMapLabel[i][j].setForeground(Color.RED);
						MyCanvas.collisionID[i][j] = 1;
					}else{
						collisionMapLabel[i][j].setForeground(Color.YELLOW);
						MyCanvas.collisionID[i][j] = 2;
					}
					collisionMapLabel[i][j].setText(" "+KEY);
					
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		pressed = false;
	}

	
	public void keyTyped(KeyEvent e) {}

	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_1){
			KEY = 1;
		}
		if(e.getKeyCode() == KeyEvent.VK_E){
			KEY = 0;
		}
		if(e.getKeyCode() == KeyEvent.VK_2){
			KEY = 2;
		}
		
	}

	
	public void keyReleased(KeyEvent e) {}

	
	public void windowGainedFocus(WindowEvent e) {
		backGroundImageUpdate();
	}

	
	public void windowLostFocus(WindowEvent e) {}
}
