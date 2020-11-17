package frame_panel;

import javax.swing.*;

import jun_chang.Sadari;
import tool.ResizeImg;
import tool.Sound;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import tool.*;


public class MainPanel extends JPanel {
	
	private JButton bgm; // 배경음악 버튼
	private JButton intro; // 인트로 버튼
	private JButton gameStart; // 게임시작 버튼
	private JButton goToHome; // 첫화면으로 돌아가는 버튼
	private JButton help; // 도움말 프레임을 불러내는 버튼
	private ButtonListener buttonL; // 이벤트 리스터
	private JButton penalty; // 벌칙 클래스를 불러내는 버튼
	private int bgmOn = 0; // 이벤트 핸들러

	private Sound music; // 음악 클래스 객체 선언
	private GameSelector game; // 게임 선택창 패널 객체 선언
			
	private JButton InsertPeople ; // 입력하기 버튼 선언
	private JTextField PeopleField; // 인원수 입력 필드 
	
	private Sadari sadari;
	private Help helpFrame;
	
	// 이미지 크기 조절
	private Image resizeImg;
	private ResizeImg rImg;

	private int people = 0;
	private JLabel lbl;

	private ImageIcon icon;
	private ImageIcon musicOn;
	private ImageIcon musicOff;

	public MainPanel() {
		
		setLayout(null); // 레이아웃 널
		setBackground(Color.gray); // 배경색 no 중요
		

		rImg = new ResizeImg("images/backimg.jpg", 1050, 800);
		resizeImg = rImg.getResizeImage();
		icon = new ImageIcon(resizeImg);
		lbl = new JLabel("", icon, SwingConstants.RIGHT); // 배경이미지 라벨에 삽입
		lbl.setBounds(0, 0, 1050, 800); // 배경이미지 위치 및 사이즈 조절
		
		gameStart = new JButton("게임시작"); // 게임 선택 패널 객체를 불러오는 버튼
		gameStart.setBounds(420,350,210,40); // 버튼 위치 및 사이즈 조절
		gameStart.addActionListener(buttonL);
		
		gameStart.setBounds(420,300,210,80); // 버튼 위치 및 사이즈 조절
		gameStart.addActionListener(buttonL);


		

		music = new Sound("sounds/1.wav"); // 배경음악에 넣을 음악 지정하며 객체 생성
		bgm = new JButton("BGM"); // 배경음악 버튼
		bgm.setBounds(830,30,80,50); // 버튼 위치 및 사이즈 조절
		bgm.setHorizontalTextPosition(JButton.CENTER);
		bgm.setVerticalTextPosition(JButton.BOTTOM);

		InsertPeople = new JButton("인원수 입력하기"); // 인원수 입력 버튼 생성
		InsertPeople.setBounds(530, 400, 100, 40); // 위치 및 사이즈 조절
		InsertPeople.setFont(new Font("Serif", Font.BOLD, 8));
		PeopleField = new JTextField(); // 인원수 입력 필드 생성
		PeopleField.setBounds(420, 400, 100, 40); // 위치 및 사이즈 조절
		
		
		
		buttonL = new ButtonListener(); // 리스너 객체 생성
		bgm.addActionListener(buttonL); // 버튼에 리스너 삽입
		gameStart.addActionListener(buttonL); // 버튼에 리스너 삽입
		InsertPeople.addActionListener(buttonL); // 버튼에 리스너 삽입
		
		goToHome = new JButton("처음으로");
		goToHome.setBounds(30, 650, 100, 100);
		goToHome.addActionListener(buttonL);
		goToHome.setVerticalTextPosition(JButton.TOP);
		goToHome.setHorizontalTextPosition(JButton.CENTER);
		
		penalty = new JButton("벌칙으로");
		penalty.setBounds(140, 650, 100, 100);
		penalty.addActionListener(buttonL);
		penalty.setVerticalTextPosition(JButton.TOP);
		penalty.setHorizontalTextPosition(JButton.CENTER);


		help = new JButton("도움말");
		help.setBounds(900, 650, 100, 100);
		help.addActionListener(buttonL);
		help.setVerticalTextPosition(JButton.TOP);
		help.setHorizontalTextPosition(JButton.CENTER);

		intro = new JButton("INTRO"); // 게임 인트로 버튼 생성
		intro.setBounds(920,30,80,40); // 버튼 위치 및 사이즈 조절
		
		addMainPanel();


		/********************************/
		/******* 버튼에 이미지 추가 ********/
		/********************************/

		rImg = new ResizeImg("images/mute.png", 30, 30);
		resizeImg = rImg.getResizeImage();
		musicOff = new ImageIcon(resizeImg);

		rImg = new ResizeImg("images/volume.png", 30, 30);
		resizeImg = rImg.getResizeImage();
		musicOn = new ImageIcon(resizeImg);

		bgm.setIcon(musicOff);
		bgm.setBorderPainted(false);
		bgm.setContentAreaFilled(false);
		bgm.setFocusPainted(false);

		rImg = new ResizeImg("images/btn_gamestart2.png", 210, 80);
		resizeImg = rImg.getResizeImage();
		icon = new ImageIcon(resizeImg);
		gameStart.setIcon(icon);
		gameStart.setBorderPainted(false);
		gameStart.setContentAreaFilled(false);
		gameStart.setFocusPainted(false);

		rImg = new ResizeImg("images/home.png", 50,50);
		resizeImg = rImg.getResizeImage();
		icon = new ImageIcon(resizeImg);
		goToHome.setIcon(icon);
		goToHome.setBorderPainted(false);
		goToHome.setContentAreaFilled(false);
		goToHome.setFocusPainted(false);

		rImg = new ResizeImg("images/cheers.png", 50,50);
		resizeImg = rImg.getResizeImage();
		icon = new ImageIcon(resizeImg);
		penalty.setIcon(icon);
		penalty.setBorderPainted(false);
		penalty.setContentAreaFilled(false);
		penalty.setFocusPainted(false);

		rImg = new ResizeImg("images/question-mark.png", 50,50);
		resizeImg = rImg.getResizeImage();
		icon = new ImageIcon(resizeImg);
		help.setIcon(icon);
		help.setBorderPainted(false);
		help.setContentAreaFilled(false);
		help.setFocusPainted(false);

	}
	
	public void addMainPanel() {
		add(help);
		add(bgm); 
		add(intro); 
		add(gameStart);
		add(PeopleField);
		add(InsertPeople); 
		add(goToHome);
		add(penalty);
		add(lbl); 
		revalidate();
		repaint();
	}
	
	public void createGameSelector() {		
		removeAll();
		game = new GameSelector(this);
		revalidate();
		repaint();
	}
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Object object = event.getSource();
			if(object == bgm) {
				if(bgmOn==0) {
					music.On();
					bgmOn=1;
					System.out.println("music on");
					bgm.setIcon(musicOn);
				}
				else {
					music.Off();
					bgmOn=0;
					System.out.println("music off");
					bgm.setIcon(musicOff);
				}
			}
			else if(object == gameStart) {
				createGameSelector();
				addMainPanel();
	
				gameStart.setVisible(false);
				PeopleField.setVisible(false);
				InsertPeople.setVisible(false);
			}
			else if(object == goToHome) {
				removeAll();
				addMainPanel();
				revalidate();							
				repaint();
			
				gameStart.setVisible(true);
				PeopleField.setVisible(true);
				InsertPeople.setVisible(true);
			}
			else if(object == InsertPeople) {
				String output = PeopleField.getText();
				people = Integer.parseInt(output);
			}
			else if(object == help) {
				helpFrame = new Help();
			}
			else if(object == penalty) {
				createSadari();
			}
		}
	}
	
	public void createSadari() {
		removeAll();
		sadari = new Sadari();
		add(sadari);
		addMainPanel();
		revalidate();
		repaint();
		gameStart.setVisible(false);
		PeopleField.setVisible(false);
		InsertPeople.setVisible(false);
	}
}
