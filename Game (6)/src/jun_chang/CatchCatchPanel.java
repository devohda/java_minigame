package jun_chang;

import frame_panel.GameSelector;
import frame_panel.MainPanel;
import frame_panel.Player;
import tool.Customfont;
import tool.ResizeImg;
import tool.RoundedButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.*;

public class CatchCatchPanel extends JPanel {

	private int turnNum; // ���� ����Ƚ������ �����ϰ� �� �� ������ ���ڸ���� ���������� �����ϴ��� ������ ����.
	private int nBox=25; // ���� ���� ���ڰ���
	private String boxString1="", boxString2="";
	private JLabel boxStringLabel1, boxStringLabel2;
	
	private ImageIcon background, jewel, candy, bomb, skul, boxImg, hoverBoxImg;
	private EtchedBorder eborder;
	private JTextArea scoreArea;
	private RoundedButton resetButton;
	private JLabel back, backGameBox;
	private Point pt[];
	private Box box[] = new Box[25]; 
	private String scoreString = " ";
	private int turn = 1;
	private int k;
	public int personNum;
	private Player player[] = new Player[100]; //�ִ� 100����� �Ҽ� �ְ� ����
	
	// ���Ӽ���ȭ�� 
	private GameSelector gameselector;
	// ���� ����� ���Ӽ���ȭ������ ���� ���Ǵ� ��ü
	private MainPanel main;
	
	// ���������ϴ� JTextArea ������
	private Image jtaImg;
	
	//��Ʈ ����
    private Customfont makeFnt;
    Font fnt;
    Font fnt2;
    
    // �̹��� ũ�� ����
    private Image resizeImg;
    private ResizeImg rImg;
    
    // ���� �̹���
    private ImageIcon reset;
	
	public CatchCatchPanel(GameSelector gs, MainPanel m) {
		
		gameselector = gs;
		main = m;
		personNum = gs.getPeopleNum();
		
		turnNum = 25%personNum; //���� ���� ���� �̶����� ����
		
		//�ڽ� ��� ���������� ǥ���ϴ� ��
		boxString1 = "���ڰ� " + turnNum + " �� ���� ������ �����մϴ�.";
		boxString2 = "���� ���� ���� : " + nBox;
		
		boxStringLabel1 = new JLabel(boxString1);
		boxStringLabel2 = new JLabel(boxString2);
		
		boxStringLabel1.setBounds(330, 300, 250, 50);
		boxStringLabel1.setVerticalTextPosition(SwingConstants.NORTH);
		boxStringLabel1.setForeground(Color.white);
		add(boxStringLabel1);
		
		boxStringLabel2.setBounds(330, 340, 250, 50);
		boxStringLabel2.setVerticalTextPosition(SwingConstants.NORTH);
		boxStringLabel2.setForeground(Color.white);
		add(boxStringLabel2);
		
		pt = new Point[25];
		for(int i=0; i<25; i++) {
			pt[i] = new Point();
		}
		
		//Image
		rImg = new ResizeImg("images/bg_catch.png", 600, 460);
        resizeImg = rImg.getResizeImage();
        background = new ImageIcon(resizeImg);  //����̹���
		
		//ImageIcon
		jewel = new ImageIcon("images/Jewel.png");
		candy = new ImageIcon("images/Candy.png");
		bomb = new ImageIcon("images/Bomb.png");
		skul = new ImageIcon("images/Skul.png");
				
		boxImg = new ImageIcon("images/Box.png"); 
		hoverBoxImg = new ImageIcon("images/hoverBox.png");
		
		//Class �ʱ�ȭ �� ����
		for(int i=0; i<25; i++) {
			box[i] = new Box("", boxImg, SwingConstants.CENTER);
		}
		
		for(int i=0; i<personNum; i++) { // >> ���� personNum ��ŭ�� ���ڷ�
			player[i] = new Player();
			player[i].setOrder(i+1);
		}
		
		//border
		eborder=new EtchedBorder(EtchedBorder.RAISED);
			
		//Panel
		setBounds(225, 170, 600, 460);
		setPreferredSize(new Dimension(600, 460));
		setLayout(null);
		
		//Panel+Label
		jtaImg = new ImageIcon("images/jtaCatch_bg.png").getImage();	

		scoreArea = new JTextArea(scoreString) {
			{ setOpaque(false) ;}
			public void paintComponent(Graphics g) {
				g.drawImage(jtaImg, 0, 0, this);
				super.paintComponent(g);
			}
		}; 
		scoreArea.setEditable(false); // ������ ���� �Ұ�
		scoreArea.setForeground(Color.white);
		scoreArea.setBounds(330, 50, 220, 250);
		add(scoreArea);
		
		 /************* ��Ʈ ���� **************/
        makeFnt = new Customfont();
        fnt = makeFnt.getCustomFont("font/���ü.ttf", Font.BOLD, 15);
        fnt2 = makeFnt.getCustomFont("font/������.ttf", Font.PLAIN, 20);
        scoreArea.setFont(fnt);
        boxStringLabel1.setFont(fnt);
        boxStringLabel2.setFont(fnt);
        boxStringLabel2.setForeground(Color.red);
        
		//�ʹ� ������ �ʱ�ȭ 
		scoreString += "���� ������ " + turn + "�� �÷��̾��Դϴ�.\r\n\n" + " ";
				
		for(int i=0; i<personNum; i++) {// >> ���� personNum ��ŭ�� ���ڷ�
			scoreString += player[i].getOrder() + "�� [Player " + (i+1) + "]" + " : " + player[i].getScore() + "\r\n ";
		}	
		scoreArea.setText(scoreString);
		
		//Box Display  // <- MakingBoard ��ü
		{
			int x=0;
			for(int i=0; i<5; i++) {  // �ڽ� ��ġ �ϴ� �� ���� �� ũ�� 32���� 50���� ������ 
				for(int j=0; j<5; j++) {
					pt[x].x = 50 + (50*j);
					pt[x].y = 50 + (50*i);
					box[x].setBounds(pt[x].x, pt[x].y, 50, 50);
					box[x].setBorder(eborder);
					
					add(box[x]);
					x++; 
				}	
			}
			init(); // �ڽ� ������ġ 
		}
		
		resetButton = new RoundedButton("Reset");
		resetButton.setBounds(50, 360, 70, 50);
		resetButton.setBackground(new Color(111, 110, 40));
		resetButton.setFont(fnt2);
		add(resetButton);
		
		back = new JLabel("", background, SwingConstants.CENTER);
		back.setBounds(0, 0, 600, 460);
		back.setBorder(new TitledBorder(new LineBorder(new Color(111, 110, 40),5), "")); // ���� �׵θ� ����
		add(back);		
		
		resetButton.addActionListener(new ResetListener());
		
		for(int i=0; i<25; i++) {
			box[i].addMouseListener(new RewardListener());
		}
	
		
	} // construct
	
	// method
	public void init() {
		Random generator = new Random();
		int point[] = new int[25]; // 0~4 : 2�� , 5~9 : 1�� , 10~14 : 0�� , 15~19 : -1��, 20~24 : -2��
		
		for(int i=0; i<25; i++) {
			point[i] = generator.nextInt(25);
			for(int j=0; j<i; j++) {
				if(point[i] == point[j]) {
					i--;
				}
			}
		}
		
		for(int i=0; i<5; i++) {
			box[point[i]].setScore(2);
		}
		for(int i=5; i<10; i++) {
			box[point[i]].setScore(1);
		}
		for(int i=10; i<15; i++) {
			box[point[i]].setScore(0);
		}
		for(int i=15; i<20; i++) {
			box[point[i]].setScore(-1);
		}
		for(int i=20; i<25; i++) {
			box[point[i]].setScore(-2);
		}
	}
	
	public void initGame(JTextArea scoreArea) {
		turn = 1;
		
		ImageIcon boximg = new ImageIcon("images/Box.png");
		for(int i=0; i<personNum; i++) { // �������� 4������ ��  // >> ���� personNum ��ŭ�� ���ڷ�
			player[i].setScore(0); // �÷��̾� �� ���� ���� 0���ʱ�ȭ
			player[i].setOrder(i+1);
		}
		for(int i=0; i<25; i++) {
			box[i].setIcon(boximg);
			box[i].setCount(0);
		}

		scoreString = "";
		scoreString += "���� ������ " + turn + "�� �÷��̾��Դϴ�.\r\n\n" + " ";
		
		for(int i=0; i<personNum; i++) {// >> ���� personNum ��ŭ�� ���ڷ�
			scoreString += player[i].getOrder() + "�� [Player " + (i+1) + "]" + " : " + player[i].getScore() + "\r\n ";
		}	
		scoreArea.setText(scoreString);
		
		//����
		turnNum = 25%personNum; //���� ���� ���� �̶����� ����
		nBox=25;
		boxStringLabel2.setText("���� ���� ���� : " + nBox);
		
		
		init();
		
		
	}
	
	public void sortPlayer(JTextArea scoreArea) {

		scoreString = "";
		int turntmp = turn+1;
		if(turntmp == personNum+1)
			turntmp=1;
		scoreString += "���� ������ " + turntmp + "�� �÷��̾��Դϴ�.\r\n\n" + " ";
		
		int emp, j, arr[];
		arr = new int[personNum]; // >> ���� personNum ��ŭ�� ���ڷ�
		
		for(int i=0; i<personNum; i++) {// >> ���� personNum ��ŭ�� ���ڷ�
			arr[i] = player[i].getScore();
		}
		
		// ���� ������ �̿��� sorting 
		for(int i=1; i<personNum; i++) {// >> ���� personNum ��ŭ�� ���ڷ�
			emp = arr[i];
			j = i-1;
			while(j>=0 && (arr[j]>emp)) {
				arr[j+1] = arr[j];
				j--;
			}
			arr[j+1] = emp;
		}
		
		for(int i=0; i<personNum; i++) {// >> ���� personNum ��ŭ�� ���ڷ�
			for(j=0; j<personNum; j++) {// >> ���� personNum ��ŭ�� ���ڷ�
				if(arr[j]==player[i].getScore()) player[i].setOrder(personNum-j);
			}
		}
		
		for(int i=0; i<personNum; i++) {// >> ���� personNum ��ŭ�� ���ڷ�
			scoreString += player[i].getOrder() + "�� [Player " + (i+1) + "]" + " : " + player[i].getScore() + "\r\n ";
		}	
				
		scoreArea.setText(scoreString);
		System.out.println(turn);
		System.out.println(scoreString);
		scoreString=" ";
		
	}	
	
	public void gameEnd() {
		String[] option = {"�ٽ� ����", "����"};
		int select = JOptionPane.showOptionDialog(this, "������ ����Ǿ����ϴ�", "��������", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, option[0]);

		if (select == 0) {
			initGame(scoreArea);
		} else if (select == JOptionPane.NO_OPTION) {
			main.createGameSelector();
        	main.addMainPanel();		
		} else {
			System.out.println("CANCLE");
		}
	}
	
	// Listener class
	private class ResetListener implements ActionListener { // ���� �ʱ�ȭ
		public void actionPerformed(ActionEvent e) {
			try {
				initGame(scoreArea);
			} catch(Exception e1) {
				System.out.println("���� �߻�! >> " + e1); 
			}
		}
		
	}
	
	// MouseAdapter ��� �� �ʿ��� �޼��常 �����ؼ� ����� ������
	private class RewardListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			
			Object obj = e.getSource();
			if(nBox != turnNum) {
			if(((Box) obj).getScore() == 2 ) {				nBox--;
			boxStringLabel2.setText("���� ���� ���� : " + nBox);
				player[turn-1].setScore(player[turn-1].getScore() + 2);
				((Box) obj).setIcon(jewel);
			}
			else if(((Box) obj).getScore() == 1 ) {				nBox--;
			boxStringLabel2.setText("���� ���� ���� : " + nBox);
				player[turn-1].setScore(player[turn-1].getScore() + 1);
				((Box) obj).setIcon(candy);
			}
			else if(((Box) obj).getScore() == 0) {				nBox--;
			boxStringLabel2.setText("���� ���� ���� : " + nBox);
				player[turn-1].setScore(player[turn-1].getScore() + 0);
				if (((Box) obj).getCount() == 0) ((Box) obj).setIcon(null);
			}
			else if(((Box) obj).getScore() == -1 ) {				nBox--;
			boxStringLabel2.setText("���� ���� ���� : " + nBox);
				player[turn-1].setScore(player[turn-1].getScore() + -1);
				((Box) obj).setIcon(bomb);
			}
			else if(((Box) obj).getScore() == -2 ) {				nBox--;
			boxStringLabel2.setText("���� ���� ���� : " + nBox);
				player[turn-1].setScore(player[turn-1].getScore() + -2);
				((Box) obj).setIcon(skul);
			}
			
			
			if(nBox == turnNum) {
				boxStringLabel2.setText("������ ����Ǿ����ϴ�.");
				gameEnd();
				turn--;
			}
			sortPlayer(scoreArea);
			
			if (((Box) obj).getCount() == 0) {

				turn++;
			}
			
			if(turn==personNum+1) turn=1;
			
			((Box) obj).setScore(0);
			((Box) obj).setCount(1);
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			Object obj = e.getSource();
			if (((Box) obj).getCount() == 0) ((Box) obj).setIcon(hoverBoxImg);
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			Object obj = e.getSource();
			if (((Box) obj).getCount() == 0) ((Box) obj).setIcon(boxImg);
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {}

		
	}
	
}

