package Sadari;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Hashtable;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SadariPanel extends JPanel implements SadariInterFace{
	Sadari mainFrame;
	
	final int paddingX = 75; 
	final int paddingY = 100;
	
	final int termX = 70;
	final int lengthY = 250;
	final int lineLength = 5;
	int y[] = new int[12];
	
	final int countBridge = 6;//6+4개 실제로  add Bridge Count 
	
	int currentDrawX, currentDrawY;
	Hashtable<Integer,PointX> bridge; // 다리 정보 갖는 해쉬 
	
	public SadariPanel(Sadari _mainFrame)
	{
		mainFrame = _mainFrame;
	}
	
	public void setStartPosition(int start)
	{
		currentDrawX = paddingX + (start-1)*termX;
		currentDrawY = paddingY;		
	}
	public void paintComponent(Graphics g)
	{
		Font fnt = new Font("Verdana", Font.BOLD, 15);
		Font fnt2 = new Font("Verdana", Font.ITALIC, 12);
		if( mainFrame.mainStatus == STATUS.INIT)
		{			
			super.paintComponent(g);  /* 화면삭제 */
			/* 기본 사다리폼 */
			String pass = "PaSS!";
			g.setColor(Color.BLACK);
			String[] loto = {"Drink!","Drink!","Drink!","Drink!","Drink!"};
			loto[(int)(Math.random() * loto.length)] = pass;
			for(int i=0;i<5;i++)
			{				
				g.setColor(Color.BLACK);
				g.setFont(fnt);
				g.drawString(""+(i+1), paddingX+i*termX, paddingY-3);
				g.drawLine(paddingX+2+i*termX, paddingY+lineLength, paddingX+2+i*termX, paddingY+lineLength+lengthY);
				
				g.setFont(fnt2);
				if( loto[i] == pass ) {g.setFont(fnt);
					}
				
				g.drawString(loto[i], paddingX-5+i*termX, paddingY+10+lineLength+lengthY+lineLength*2);
			}
			
			bridge = new Hashtable<Integer,PointX>();
			for(int i=0; i<4; i++) {
				int rdX = i;
				
				int rdY = (int)(Math.random()*(lengthY-lineLength));
				
				for(int x=0; x<i; x++) {
					if(y[x] - rdY < 5 && y[x] - rdY > -5 ) {
						rdY = (int)(Math.random()*(lengthY-lineLength));
						x=0;
					}
				}
				y[i] = rdY;
				g.drawLine(paddingX+2+rdX*termX, paddingY+rdY+lineLength, paddingX+2+rdX*termX+termX, paddingY+rdY+lineLength);
				/* 랜덤생성한 브릿지를 저장 */
				bridge.put(paddingY+rdY+lineLength, new PointX(paddingX+rdX*termX,paddingX+rdX*termX+termX));
			}	
			
			for(int i=0;i<countBridge;i++)
			{
				int rdX = (int)(Math.random()*4);
				int rdY = (int)(Math.random()*(lengthY-lineLength));
				for(int x=0; x<countBridge+4; x++) {
					if(y[x] - rdY < 5 && y[x] - rdY > -5 ) {
						rdY = (int)(Math.random()*(lengthY-lineLength));
						x=0;
					}
				}
				y[i+4] = rdY;
				
				g.drawLine(paddingX+2+rdX*termX, paddingY+rdY+lineLength, paddingX+2+rdX*termX+termX, paddingY+rdY+lineLength);
				/* 랜덤생성한 브릿지를 저장 */
				bridge.put(paddingY+rdY+lineLength, new PointX(paddingX+rdX*termX,paddingX+rdX*termX+termX));
			}

		}
		else if( mainFrame.mainStatus == STATUS.DRAWING)
		{
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(Color.red);
		
			while(currentDrawY < paddingY+lengthY+lineLength)
			{
				/* 생성된 브릿지 검색 */
				PointX value = bridge.get(currentDrawY);
				if( value != null )
				{
					/* 브릿지가 있으면 해당 X 라인인지 검색 */
					if( currentDrawX == value.startX)
					{
					    g2.setStroke(new BasicStroke(3,BasicStroke.CAP_ROUND,0));
						g2.drawLine(value.startX+3, currentDrawY, value.endX+3, currentDrawY);
						currentDrawX += termX;
					}
					else if( currentDrawX == value.endX )
					{
					    g2.setStroke(new BasicStroke(3,BasicStroke.CAP_ROUND,0));
						g2.drawLine(value.startX+3, currentDrawY, value.endX+3, currentDrawY);
						currentDrawX -= termX;
					}
				}
			    g2.setStroke(new BasicStroke(3,BasicStroke.CAP_ROUND,0));
				g2.drawLine(currentDrawX+3, currentDrawY, currentDrawX+3, ++currentDrawY);
				repaint();
			}
			
			
			mainFrame.mainStatus = STATUS.END;
		}
		
	}
}
	
/* 사다리 랜덤 작대기 보관을 위한 클래스 */
class PointX
{
	public int startX;
	public int endX;
	
	public PointX(int _startX, int _endX )
	{
		startX = _startX;
		endX   = _endX;
	}
}

