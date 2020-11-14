import java.awt.*;
import java.io.File; 
import javax.imageio.ImageIO;
 
public class ResizeImg {
	private int newH, newW;
	private Image img;
	private Image resizeImg;
	public ResizeImg(String filename, int w, int h) {
		try {
			img = ImageIO.read(new File(filename));
			newH = h;
			newW = w;
			resizeImg = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		}
		catch (Exception e){
            e.printStackTrace();
        }
		
	}
	public Image getResizeImage() {
		return resizeImg;
	}
	
}

