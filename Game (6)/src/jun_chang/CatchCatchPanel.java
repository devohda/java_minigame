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

	private int turnNum; // 상자 남은횟수까지 진행하게 됨 이 변수는 상자몇개까지 남을때가지 진행하는지 저장할 변수.
	private int nBox=25; // 현재 남은 상자갯수
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
	private Player player[] = new Player[100]; //최대 100명까지 할수 있게 설정
	
	// 게임선택화면 
	private GameSelector gameselector;
	// 게임 종료시 게임선택화면으로 갈때 사용되는 객체
	private MainPanel main;
	
	// 점수나열하는 JTextArea 디자인
	private Image jtaImg;
	
	//폰트 지정
    private Customfont makeFnt;
    Font fnt;
    Font fnt2;
    
    // 이미지 크기 조절
    private Image resizeImg;
    private ResizeImg rImg;
    
    // 리셋 이미지
    private ImageIcon reset;
	
	public CatchCatchPanel(GameSelector gs, MainPanel m) {
		
		gameselector = gs;
		main = m;
		personNum = gs.getPeopleNum();
		
		turnNum = 25%personNum; //상자 남은 갯수 이때까지 진행
		
		//박스 몇개가 남을때까지 표시하는 라벨
		boxString1 = "상자가 " + turnNum + " 개 남을 때까지 진행합니다.";
		boxString2 = "남은 상자 갯수 : " + nBox;
		
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
        background = new ImageIcon(resizeImg);  //배경이미지
		
		//ImageIcon
		jewel = new ImageIcon("images/Jewel.png");
		candy = new ImageIcon("images/Candy.png");
		bomb = new ImageIcon("images/Bomb.png");
		skul = new ImageIcon("images/Skul.png");
				
		boxImg = new ImageIcon("images/Box.png"); 
		hoverBoxImg = new ImageIcon("images/hoverBox.png");
		
		//Class 초기화 및 생성
		for(int i=0; i<25; i++) {
			box[i] = new Box("", boxImg, SwingConstants.CENTER);
		}
		
		for(int i=0; i<personNum; i++) { // >> 수정 personNum 만큼의 숫자로
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
		scoreArea.setEditable(false); // 점수판 편집 불가
		scoreArea.setForeground(Color.white);
		scoreArea.setBounds(330, 50, 220, 250);
		add(scoreArea);
		
		 /************* 폰트 지정 **************/
        makeFnt = new Customfont();
        fnt = makeFnt.getCustomFont("font/고양체.ttf", Font.BOLD, 15);
        fnt2 = makeFnt.getCustomFont("font/지마켓.ttf", Font.PLAIN, 20);
        scoreArea.setFont(fnt);
        boxStringLabel1.setFont(fnt);
        boxStringLabel2.setFont(fnt);
        boxStringLabel2.setForeground(Color.red);
        
		//초반 점수판 초기화 
		scoreString += "현재 순서는 " + turn + "번 플레이어입니다.\r\n\n" + " ";
				
		for(int i=0; i<personNum; i++) {// >> 수정 personNum 만큼의 숫자로
			scoreString += player[i].getOrder() + "등 [Player " + (i+1) + "]" + " : " + player[i].getScore() + "\r\n ";
		}	
		scoreArea.setText(scoreString);
		
		//Box Display  // <- MakingBoard 대체
		{
			int x=0;
			for(int i=0; i<5; i++) {  // 박스 배치 하는 곳 간격 및 크기 32에서 50으로 수정함 
				for(int j=0; j<5; j++) {
					pt[x].x = 50 + (50*j);
					pt[x].y = 50 + (50*i);
					box[x].setBounds(pt[x].x, pt[x].y, 50, 50);
					box[x].setBorder(eborder);
					
					add(box[x]);
					x++; 
				}	
			}
			init(); // 박스 랜덤배치 
		}
		
		resetButton = new RoundedButton("Reset");
		resetButton.setBounds(50, 360, 70, 50);
		resetButton.setBackground(new Color(111, 110, 40));
		resetButton.setFont(fnt2);
		add(resetButton);
		
		back = new JLabel("", background, SwingConstants.CENTER);
		back.setBounds(0, 0, 600, 460);
		back.setBorder(new TitledBorder(new LineBorder(new Color(111, 110, 40),5), "")); // 게임 테두리 설정
		add(back);		
		
		resetButton.addActionListener(new ResetListener());
		
		for(int i=0; i<25; i++) {
			box[i].addMouseListener(new RewardListener());
		}
	
		
	} // construct
	
	// method
	public void init() {
		Random generator = new Random();
		int point[] = new int[25]; // 0~4 : 2점 , 5~9 : 1점 , 10~14 : 0점 , 15~19 : -1점, 20~24 : -2점
		
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
		for(int i=0; i<personNum; i++) { // 지금현재 4명임의 수  // >> 수정 personNum 만큼의 숫자로
			player[i].setScore(0); // 플레이어 수 점수 전부 0점초기화
			player[i].setOrder(i+1);
		}
		for(int i=0; i<25; i++) {
			box[i].setIcon(boximg);
			box[i].setCount(0);
		}

		scoreString = "";
		scoreString += "현재 순서는 " + turn + "번 플레이어입니다.\r\n\n" + " ";
		
		for(int i=0; i<personNum; i++) {// >> 수정 personNum 만큼의 숫자로
			scoreString += player[i].getOrder() + "등 [Player " + (i+1) + "]" + " : " + player[i].getScore() + "\r\n ";
		}	
		scoreArea.setText(scoreString);
		
		//수정
		turnNum = 25%personNum; //상자 남은 갯수 이때까지 진행
		nBox=25;
		boxStringLabel2.setText("남은 상자 갯수 : " + nBox);
		
		
		init();
		
		
	}
	
	public void sortPlayer(JTextArea scoreArea) {

		scoreString = "";
		int turntmp = turn+1;
		if(turntmp == personNum+1)
			turntmp=1;
		scoreString += "현재 순서는 " + turntmp + "번 플레이어입니다.\r\n\n" + " ";
		
		int emp, j, arr[];
		arr = new int[personNum]; // >> 수정 personNum 만큼의 숫자로
		
		for(int i=0; i<personNum; i++) {// >> 수정 personNum 만큼의 숫자로
			arr[i] = player[i].getScore();
		}
		
		// 삽입 정렬을 이용한 sorting 
		for(int i=1; i<personNum; i++) {// >> 수정 personNum 만큼의 숫자로
			emp = arr[i];
			j = i-1;
			while(j>=0 && (arr[j]>emp)) {
				arr[j+1] = arr[j];
				j--;
			}
			arr[j+1] = emp;
		}
		
		for(int i=0; i<personNum; i++) {// >> 수정 personNum 만큼의 숫자로
			for(j=0; j<personNum; j++) {// >> 수정 personNum 만큼의 숫자로
				if(arr[j]==player[i].getScore()) player[i].setOrder(personNum-j);
			}
		}
		
		for(int i=0; i<personNum; i++) {// >> 수정 personNum 만큼의 숫자로
			scoreString += player[i].getOrder() + "등 [Player " + (i+1) + "]" + " : " + player[i].getScore() + "\r\n ";
		}	
				
		scoreArea.setText(scoreString);
		System.out.println(turn);
		System.out.println(scoreString);
		scoreString=" ";
		
	}	
	
	public void gameEnd() {
		String[] option = {"다시 시작", "종료"};
		int select = JOptionPane.showOptionDialog(this, "게임이 종료되었습니다", "게임종료", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, option[0]);

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
	private class ResetListener implements ActionListener { // 게임 초기화
		public void actionPerformed(ActionEvent e) {
			try {
				initGame(scoreArea);
			} catch(Exception e1) {
				System.out.println("오류 발생! >> " + e1); 
			}
		}
		
	}
	
	// MouseAdapter 사용 시 필요한 메서드만 구현해서 사용이 가능함
	private class RewardListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			
			Object obj = e.getSource();
			if(nBox != turnNum) {
			if(((Box) obj).getScore() == 2 ) {				nBox--;
			boxStringLabel2.setText("남은 상자 갯수 : " + nBox);
				player[turn-1].setScore(player[turn-1].getScore() + 2);
				((Box) obj).setIcon(jewel);
			}
			else if(((Box) obj).getScore() == 1 ) {				nBox--;
			boxStringLabel2.setText("남은 상자 갯수 : " + nBox);
				player[turn-1].setScore(player[turn-1].getScore() + 1);
				((Box) obj).setIcon(candy);
			}
			else if(((Box) obj).getScore() == 0) {				nBox--;
			boxStringLabel2.setText("남은 상자 갯수 : " + nBox);
				player[turn-1].setScore(player[turn-1].getScore() + 0);
				if (((Box) obj).getCount() == 0) ((Box) obj).setIcon(null);
			}
			else if(((Box) obj).getScore() == -1 ) {				nBox--;
			boxStringLabel2.setText("남은 상자 갯수 : " + nBox);
				player[turn-1].setScore(player[turn-1].getScore() + -1);
				((Box) obj).setIcon(bomb);
			}
			else if(((Box) obj).getScore() == -2 ) {				nBox--;
			boxStringLabel2.setText("남은 상자 갯수 : " + nBox);
				player[turn-1].setScore(player[turn-1].getScore() + -2);
				((Box) obj).setIcon(skul);
			}
			
			
			if(nBox == turnNum) {
				boxStringLabel2.setText("게임이 종료되었습니다.");
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

