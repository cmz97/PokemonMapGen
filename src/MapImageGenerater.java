import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MapImageGenerater {
	public MapImageGenerater(File savePath,String [][] xPix, String [][] yPix, int[][] colli){
		try {

			File file = new File(savePath.getPath());
			if (!file.exists()) {
				file.createNewFile();
			}
			System.out.println(file.getAbsoluteFile());
			FileWriter fw = new FileWriter(file.getAbsoluteFile()+"/SavedMap.akmap");
			BufferedWriter bw = new BufferedWriter(fw);
	
			for(int i=0; i<xPix.length;i++){
				for(int j=0; j<xPix[0].length; j++){
					bw.write(j+" "+i+" "+xPix[i][j]+" "+yPix[i][j]+" "+colli[i][j]+" ");
					bw.newLine();
				}
			}
			bw.close();
			
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
