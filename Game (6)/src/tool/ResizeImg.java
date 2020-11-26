package tool;

import java.awt.*;
import java.io.File; 
import javax.imageio.ImageIO;
 
public class ResizeImg {
	private int newH, newW; // 새롭게 할 높이와 너비
	private Image img; // 원본 이미지
	private Image resizeImg; // 사이즈 조절 된 이미지
	public ResizeImg(String filename, int w, int h) { // 파일 경로와 이름, 새롭게 할 높이와 너비를 받음
		try {
			img = ImageIO.read(new File(filename)); // 이미지 파일을 받는다
			newH = h;// 새로운 높이 지정
			newW = w;// 새로운 너비 지정
			resizeImg = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH); // 받은 정보르 이미지 재설정
		}
		catch (Exception e){
            e.printStackTrace(); // exception handle
        }
		
	}
	public Image getResizeImage() { 
		return resizeImg; // 재설정된 이미지 반환
	}
	
}

