package tool;

import java.awt.*;
import java.io.File; 
import javax.imageio.ImageIO;
 
public class ResizeImg {
	private int newH, newW; // ���Ӱ� �� ���̿� �ʺ�
	private Image img; // ���� �̹���
	private Image resizeImg; // ������ ���� �� �̹���
	public ResizeImg(String filename, int w, int h) { // ���� ��ο� �̸�, ���Ӱ� �� ���̿� �ʺ� ����
		try {
			img = ImageIO.read(new File(filename)); // �̹��� ������ �޴´�
			newH = h;// ���ο� ���� ����
			newW = w;// ���ο� �ʺ� ����
			resizeImg = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH); // ���� ������ �̹��� �缳��
		}
		catch (Exception e){
            e.printStackTrace(); // exception handle
        }
		
	}
	public Image getResizeImage() { 
		return resizeImg; // �缳���� �̹��� ��ȯ
	}
	
}

