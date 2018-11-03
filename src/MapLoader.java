import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class MapLoader {

	private int[][] collisionID; 
	private String[][] canvasBlockIDx; 
	private String[][] canvasBlockIDy; 
	private Scanner scanner;
	
	public MapLoader(File filePath,int widthTile,int heightTile){
		collisionID = new int[heightTile][widthTile];
		canvasBlockIDx = new String[heightTile][widthTile];
		canvasBlockIDy = new String[heightTile][widthTile];

		try {
			scanner = new Scanner(filePath);
			scanner.useDelimiter("\n");
			int x = 0;
			int y = 0;
			while (scanner.hasNext()) 
			{
				String data = scanner.next();
				String[] values = data.split(" ");

				x = Integer.parseInt(values[0]);
				System.out.print(x+"|");

				y = Integer.parseInt(values[1]);
				System.out.print(y+"|");

				canvasBlockIDx[y][x] = values[2];
				System.out.print(canvasBlockIDx[y][x]+"|");

				canvasBlockIDy[y][x] = values[3];
				System.out.print(canvasBlockIDy[y][x]+"|");

				collisionID[y][x] = Integer.parseInt(values[4]);
				System.out.println(collisionID[y][x]);

			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


	}

	public String[][] getCanvasBlockIDx(){
		return canvasBlockIDx;
	}

	public String[][] getCanvasBlockIDy(){
		return canvasBlockIDy;
	}

	public int[][] getCollisionID(){
		return collisionID;
	}	

}
