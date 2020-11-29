package sadari;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Hashtable;

import javax.swing.*;

@SuppressWarnings("serial")
public class SadariPanel extends JPanel implements SadariInterFace{
	Sadari mainFrame;
	
	final int paddingX = 75;  //사다리 X축 공백
	final int paddingY = 100; //사다리 Y축 공백
	
	final int termX = 70;  //사다리 간격
	final int lengthY = 250;
	final int lineLength = 5;
	int[] y = new int[12];
	
	final int countBridge = 6;//6+4개 실제로  add Bridge Count 
	
	private JTextField txtPenalty1,txtPenalty2,txtPenalty3,txtPenalty4,txtPenalty5; //벌칙입력텍스트
	private JLabel txt; // 간단설명 텍스트
	
	int currentDrawX, currentDrawY;
	Hashtable<Integer,PointX> bridge; // 다리 정보 갖는 해쉬 
	private Color color = new Color(201,200,208);
	
	public SadariPanel(Sadari _mainFrame)
	{
		mainFrame = _mainFrame;

		txtPenalty1 = new JTextField();
		txtPenalty1.setBounds(55, 360, 45, 25);
		txtPenalty1.setHorizontalAlignment(SwingConstants.CENTER);
		mainFrame.add(txtPenalty1);

		txtPenalty2 = new JTextField();
		txtPenalty2.setBounds(125, 360, 45, 25);
		txtPenalty2.setHorizontalAlignment(SwingConstants.CENTER);
		mainFrame.add(txtPenalty2);

		txtPenalty3 = new JTextField();
		txtPenalty3.setBounds(195, 360, 45, 25);
		txtPenalty3.setHorizontalAlignment(SwingConstants.CENTER);
		mainFrame.add(txtPenalty3);

		txtPenalty4 = new JTextField();
		txtPenalty4.setBounds(265, 360, 45, 25);
		txtPenalty4.setHorizontalAlignment(SwingConstants.CENTER);
		mainFrame.add(txtPenalty4);

		txtPenalty5 = new JTextField();
		txtPenalty5.setBounds(335, 360, 45, 25);
		txtPenalty5.setHorizontalAlignment(SwingConstants.CENTER);
		mainFrame.add(txtPenalty5);

		//벌칙 입력 텍스트
		txt = new JLabel("↑ 벌칙 입력!");
		txt.setBounds(55, 390, 80, 20); //설명 텍스트
		mainFrame.add(txt);
		
	}
	
	public void setStartPosition(int start)
	{
		currentDrawX = paddingX + (start-1)*termX;
		currentDrawY = paddingY;		
	}
	
	public void paintComponent(Graphics g)
	{		

		Font fnt = new Font("Verdana", Font.BOLD, 15);

		if( mainFrame.mainStatus == STATUS.INIT)
		{
			super.paintComponent(g);  /* 화면삭제 */
			
			g.setColor(Color.BLACK);
			for(int i=0;i<5;i++)
			{				
				g.setColor(Color.BLACK);
				g.setFont(fnt);
				g.drawString(""+(i+1), paddingX-3+i*termX, paddingY-3);
				g.drawLine(paddingX+2+i*termX, paddingY+lineLength, paddingX+2+i*termX, paddingY+lineLength+lengthY);

			}// 기본 사다리폼  
			
			bridge = new Hashtable<Integer,PointX>();
			for(int i=0; i<4; i++) {
				int rdX = i;
				int rdY = (int)(Math.random()*(lengthY-lineLength));
				//랜덤한 Y생성
				
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
			
			}	 //다리가 전부다 골고루 존재하게 간격별로 한개씩 bridge 생성
			
			
			for(int i=0;i<countBridge;i++)
			{
				int rdX = (int)(Math.random()*4);
				int rdY = (int)(Math.random()*(lengthY-lineLength));
				//랜덤한 X,Y생성
				
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
			} //나머지 다리를 랜덤으로 생성
			
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(color);
		    g2.setStroke(new BasicStroke(10,BasicStroke.CAP_ROUND,0));
		    g2.drawLine(0, 0, 0, 400);
			g2.drawLine(0, 0, 450, 0);
			g2.drawLine(450, 0, 450, 400); //테두리 꾸미기
			
		}else if( mainFrame.mainStatus == STATUS.DRAWING)
		{
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(color);
		    g2.setStroke(new BasicStroke(10,BasicStroke.CAP_ROUND,0));
			
			g2.drawLine(0, 0, 0, 400);
			g2.drawLine(0, 0, 450, 0);
			g2.drawLine(450, 0, 450, 400);
			
			while(currentDrawY < paddingY+lengthY+lineLength)
			{
				g2.setColor(color.red);
				/* 생성된 브릿지 검색 */
				PointX value = bridge.get(currentDrawY);
				if( value != null )
				{
					// 브릿지가 있으면 해당 X 라인인지 검색 
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
			}
			repaint(); //사다리 타고 내려오는 길 굵은 빨간선으로 칠하는 과정
			setBackground(Color.red);
			mainFrame.mainStatus = STATUS.END;
		}

		
	}
}
	
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
//사다리 랜덤 작대기 보관을 위한 클래스 

