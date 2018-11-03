import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MyCanvas extends JFrame implements MouseListener, KeyListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int WIDTH_TILES;
	private final int HEIGHT_TILES;
	
	public static boolean colliButtonToggle = false;
	
	public static String[][] canvasBlockIDx; 	
	public static String[][] canvasBlockIDy;

	private BufferedImage emptyTile;
	private BufferedImage selector;

	public static JLabel[][] canvasImageLabel;
	
	private JLabel canvasSelectorIcon;

	private JButton save;
	private JButton wipe;
	private JButton collisionMap;
	private JButton fill;
	private JButton load;

	private JPanel imageMatrix; 
	private boolean pressed = false;
	private int tempx;
	private int tempy;
	final JFileChooser fc;

	private File savePath;
	private File readPath;
	private JFileChooser fl;
	
	
	public static int[][] collisionID;

	public MyCanvas(int widthTiles, int heightTiles){

		canvasBlockIDx = new String[heightTiles][widthTiles];
		canvasBlockIDy = new String[heightTiles][widthTiles];
		
		collisionID = new int[heightTiles][widthTiles];

		for(int i=0;i<canvasBlockIDx.length;i++){
			for(int j=0;j<canvasBlockIDx[0].length;j++){
				canvasBlockIDx[i][j] = "x";
				canvasBlockIDy[i][j] = "x";
				collisionID[i][j]=0;
			}
		}


		this.setLayout(null);
		emptyTtileLoader();
		this.WIDTH_TILES = widthTiles;
		this.HEIGHT_TILES = heightTiles;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setBackground(Color.WHITE);
		this.setResizable(false);

		this.setBounds(0,0, WIDTH_TILES*16+100, HEIGHT_TILES*16+22);
		this.setTitle("RPG Map Canvas");

		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();

		imageMatrix = new JPanel();
		imageMatrix.setLayout(null);
		imageMatrix.setBounds(0, 0, widthTiles*16, heightTiles*16);
		imageMatrix.setVisible(true);

		canvasImageLabel = new JLabel[heightTiles][widthTiles];
	

		for(int i = 0; i < canvasImageLabel.length;i++){
			for(int j = 0; j<canvasImageLabel[0].length; j++){
				canvasImageLabel[i][j] = new JLabel(new ImageIcon(emptyTile)); 
				canvasImageLabel[i][j].setBounds(j*16, i*16, 16, 16);
				canvasImageLabel[i][j].setOpaque(false);
				imageMatrix.add(canvasImageLabel[i][j]);
				canvasImageLabel[i][j].addMouseListener(this);
			}
		}
		this.add(imageMatrix);

		canvasSelectorIcon = new JLabel(new ImageIcon(selector));
		canvasSelectorIcon.setVisible(true);
		imageMatrix.add(canvasSelectorIcon);


		save = new JButton("Save");
		save.setBounds(widthTiles*16 +14, 10, 70, 50);
		save.addActionListener(this);
		this.add(save);

		load = new JButton("Load");
		load.setBounds(widthTiles*16 +14, 60, 70, 50);
		load.addActionListener(this);
		this.add(load);
		
		collisionMap = new JButton("Collision");
		collisionMap.setBounds(widthTiles*16 +14, 110, 70, 50);
		collisionMap.addActionListener(this);
		this.add(collisionMap);
		
	


		wipe = new JButton("Wipe");
		wipe.setBounds(widthTiles*16 +14, 160, 70, 50);
		wipe.addActionListener(this);
		this.add(wipe);

		fill = new JButton("Fill");
		fill.setBounds(widthTiles*16 +14, 210, 70, 50);
		fill.addActionListener(this);
		this.add(fill);

		fc = new JFileChooser();
		fc.setDialogTitle("Select a path to save file");
		fc.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
		
		fl = new JFileChooser();
		fl.setDialogTitle("Select a path to load file");
		fl.setAcceptAllFileFilterUsed(false);
		fl.addChoosableFileFilter(new FileNameExtensionFilter("AlKevinMap","akmap"));
		
	}

	private void emptyTtileLoader(){
		try {
			emptyTile = ImageIO.read(getClass().getClassLoader().getResource("resource/empty.png").openStream());
			selector = ImageIO.read(getClass().getClassLoader().getResource("resource/Selector.png").openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;
		placeTile(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
			for(int i = 0; i < canvasImageLabel.length;i++){
				for(int j = 0; j<canvasImageLabel[0].length; j++){
					if(e.getSource().equals(canvasImageLabel[i][j])){
						canvasSelectorIcon.setBounds(j*16, i*16, 16, 16);
					}
				}
			}
	
			if(pressed)placeTile(e);
	}


	private void placeTile(MouseEvent e){
		for(int i = 0; i < canvasImageLabel.length;i++){
			for(int j = 0; j<canvasImageLabel[0].length; j++){
				if(e.getSource().equals(canvasImageLabel[i][j])){
					canvasImageLabel[i][j].setIcon(MapBuilderUI.tiles[MapBuilderUI.blockID[0][0]][MapBuilderUI.blockID[0][1]].getIcon());
					if(MapBuilderUI.blockID[0][0] != 27 && MapBuilderUI.blockID[0][1] != 60){
						canvasBlockIDx[i][j] =  MapBuilderUI.blockID[0][1]+"";
						canvasBlockIDy[i][j] =  MapBuilderUI.blockID[0][0]+"";
					}else{
						canvasBlockIDx[i][j] =  "x";
						canvasBlockIDy[i][j] =  "x";
					}
				}
			}
		}
	}
	
	
	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_E){
			tempx = MapBuilderUI.blockID[0][0];
			tempy = MapBuilderUI.blockID[0][1];
			MapBuilderUI.blockID[0][0] = MapBuilderUI.tiles.length-1;
			MapBuilderUI.blockID[0][1] = MapBuilderUI.tiles[0].length-1;			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_E){
			MapBuilderUI.blockID[0][0] = tempx;
			MapBuilderUI.blockID[0][1] = tempy;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == save){
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				savePath = fc.getSelectedFile();
				new MapImageGenerater(savePath,canvasBlockIDx,canvasBlockIDy,collisionID);
				JOptionPane.showMessageDialog(this,
						"File saved to" + savePath,
						"Success!",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this,
						"File saved Fail",
						"Failed!",
						JOptionPane.INFORMATION_MESSAGE);

			}

		}
		
		if(e.getSource() == load){
			int returnVal = fl.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				readPath = fl.getSelectedFile();
				MapLoader loaded = new MapLoader(readPath,WIDTH_TILES,HEIGHT_TILES);
				canvasBlockIDx = loaded.getCanvasBlockIDx();
				canvasBlockIDy = loaded.getCanvasBlockIDy();
				collisionID = loaded.getCollisionID();
				for(int i = 0; i < canvasImageLabel.length;i++){
					for(int j = 0; j<canvasImageLabel[0].length; j++){
							if(canvasBlockIDx[i][j].equals("x") || canvasBlockIDy[i][j].equals("x")){
								canvasImageLabel[i][j].setIcon(new ImageIcon(emptyTile));
							}else{
								canvasImageLabel[i][j].setIcon(MapBuilderUI.tiles[Integer.parseInt(canvasBlockIDy[i][j])][Integer.parseInt(canvasBlockIDx[i][j])].getIcon());
							}
							if(collisionID[i][j]==0){
								CollisionMapUI.collisionMapLabel[i][j].setForeground(Color.BLACK);
							}else if(collisionID[i][j]==1){
								CollisionMapUI.collisionMapLabel[i][j].setForeground(Color.RED);
							}else{
								CollisionMapUI.collisionMapLabel[i][j].setForeground(Color.YELLOW);
							}
							CollisionMapUI.collisionMapLabel[i][j].setText(" "+collisionID[i][j]);
					}
				}
			    this.repaint();
				JOptionPane.showMessageDialog(this,
						"File Read Success",
						"Success!",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this,
						"File Read Fail",
						"Failed!",
						JOptionPane.INFORMATION_MESSAGE);

			}
		}

		if(e.getSource() == collisionMap){
			MapBuilderUI.colli.setVisible(!MapBuilderUI.colli.isVisible());
		}

		if(e.getSource() == wipe){
			ImageIcon temp = new ImageIcon(emptyTile);
			for(int i=0;i<canvasImageLabel.length;i++){
				for(int j=0;j<canvasImageLabel[0].length;j++){
					canvasImageLabel[i][j].setIcon(temp);
					canvasBlockIDx[i][j] = "x";
					canvasBlockIDy[i][j] = "x";
				}
			}
			this.repaint();
		}

		if(e.getSource() == fill){
			for(int i=0;i<canvasImageLabel.length;i++){
				for(int j=0;j<canvasImageLabel[0].length;j++){
					canvasImageLabel[i][j].setIcon(MapBuilderUI.tiles[MapBuilderUI.blockID[0][0]][MapBuilderUI.blockID[0][1]].getIcon());
					canvasBlockIDy[i][j] =  MapBuilderUI.blockID[0][0]+"";
					canvasBlockIDx[i][j] =  MapBuilderUI.blockID[0][1]+"";
				}
			}
			this.repaint();
		}
		
		

	}

}
