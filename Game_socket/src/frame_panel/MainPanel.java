package frame_panel;

import javax.swing.*;

import jun_chang.Sadari;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import clientgame.ClientGame;
import tool.*;


public class MainPanel extends JPanel {
	
	private JButton bgm; // 배경음악 버튼
	private JButton intro; // 인트로 버튼
	private RoundedButton gameStart; // 게임시작 버튼
	private JButton goToHome;
	private JButton help; // 도움말 프레임을 불러내는 버튼
	private ButtonListener buttonL; // 이벤트 리스터
	private JButton penalty; // 벌칙 클래스를 불러내는 버튼
	private int bgmOn = 0; // 이벤트 핸들러

	private Sound music; // 음악 클래스 객체 선언
	private GameSelector game; // 게임 선택창 패널 객체 선언
			
	private RoundedButton insertPeople ; // 입력하기 버튼 선언
	private JTextField peopleField; // 인원수 입력 필드 
	private JLabel lblstatePeople;
    private int people = 0;
    private JLabel lbl;
	
	private Sadari sadari;
	private Help helpFrame;
	
	// 이미지 크기 조절
	private Image resizeImg;
	private ResizeImg rImg;
	
	private ImageIcon icon, home, cheers, question, introImg;
    private ImageIcon musicOn;
    private ImageIcon musicOff;
    

    private Customfont makeFnt;
    private Font fnt;
    private Font fnt2;
    
    // hover 이미지
    private ImageIcon hoverMusicOn;
    private ImageIcon hoverMusicOff;
    private ImageIcon hoverHome;
    private ImageIcon hoverCheers;
    private ImageIcon hoverHelp;
    private ImageIcon hoverIntro;
    
    private hoverListener hover;
	
	private ClientGame client;
	
	public MainPanel(ClientGame c) {
		
		client = c;
		
		setLayout(null); // 레이아웃 널
		setBackground(Color.gray); // 배경색 no 중요
				
		makeFnt = new Customfont(); //외부 폰트 만들기
        fnt = makeFnt.getCustomFont("font/SSShinb7.ttf", Font.PLAIN, 20); //폰트 지정
        fnt2 = makeFnt.getCustomFont("font/지마켓.ttf", Font.PLAIN, 20);
        buttonL = new ButtonListener(); // 리스너 객체 생성
        hover = new hoverListener();
		
        // 배경 이미지 삽입
        rImg = new ResizeImg("images/bg_2.jpg", 1050, 800);
        resizeImg = rImg.getResizeImage();
        icon = new ImageIcon(resizeImg);
        lbl = new JLabel("", icon, SwingConstants.RIGHT); // 배경이미지 라벨에 삽입
        lbl.setBounds(0, 0, 1050, 800); // 배경이미지 위치 및 사이즈 조절
		
        // 객체 만들기
        gameStart = new RoundedButton("게임시작"); // 게임 선택 패널 객체를 불러오는 버튼
        gameStart.setBounds(420, 340, 210, 70); // 버튼 위치 및 사이즈 조절
        gameStart.setBackground(new Color(237, 248, 141));
        gameStart.addActionListener(buttonL); // 버튼에 리스너 삽입
        gameStart.setFont(fnt2);
        gameStart.setVisible(false);
		
        lblstatePeople = new JLabel("인원수를 입력하세요");
        lblstatePeople.setBounds(420, 350, 210, 40);
        lblstatePeople.setHorizontalAlignment(JLabel.CENTER);
        lblstatePeople.setVerticalAlignment(JLabel.CENTER);
        lblstatePeople.setFont(fnt2);
        
        music = new Sound("sounds/1.wav"); // 배경음악에 넣을 음악 지정하며 객체 생성
        bgm = new JButton("BGM"); // 배경음악 버튼
        bgm.setBounds(810, 20, 80, 80); // 버튼 위치 및 사이즈 조절
        bgm.setFont(fnt);
        bgm.setHorizontalTextPosition(JButton.CENTER);
        bgm.setVerticalTextPosition(JButton.BOTTOM);
        bgm.addActionListener(buttonL); // 버튼에 리스너 삽입
        bgm.addMouseListener(hover);

        intro = new JButton("INTRO"); // 게임 인트로 버튼 생성
        intro.setBounds(900, 20, 100, 80); // 버튼 위치 및 사이즈 조절
        intro.setFont(fnt);
        intro.setHorizontalTextPosition(JButton.CENTER);
        intro.setVerticalTextPosition(JButton.BOTTOM);
        intro.addMouseListener(hover);


        insertPeople = new RoundedButton("입력"); // 인원수 입력 버튼 생성
        insertPeople.setBounds(530, 420, 100, 40); // 위치 및 사이즈 조절
        insertPeople.setFont(fnt2);
        insertPeople.addActionListener(buttonL); // 버튼에 리스너 삽입

        peopleField = new JTextField(); // 인원수 입력 필드 생성
        peopleField.setBounds(420, 420, 100, 40); // 위치 및 사이즈 조절
        peopleField.setFont(fnt2);
        peopleField.addActionListener(buttonL);


        // 하단 버튼 생성
        goToHome = new JButton("처음으로");
        goToHome.setBounds(30, 650, 100, 100);
        goToHome.addActionListener(buttonL);
        goToHome.addMouseListener(hover);
        
        goToHome.setVerticalTextPosition(JButton.TOP);
        goToHome.setHorizontalTextPosition(JButton.CENTER);
        goToHome.setFont(fnt);

        penalty = new JButton("벌칙으로");
        penalty.setBounds(140, 650, 100, 100);
        penalty.addActionListener(buttonL);
        penalty.addMouseListener(hover);
        
        penalty.setVerticalTextPosition(JButton.TOP);
        penalty.setHorizontalTextPosition(JButton.CENTER);
        penalty.setFont(fnt);

        help = new JButton("도움말");
        help.setBounds(900, 650, 100, 100);
        help.addActionListener(buttonL);
        help.addMouseListener(hover);
        
        help.setVerticalTextPosition(JButton.TOP);
        help.setHorizontalTextPosition(JButton.CENTER);
        help.setFont(fnt);

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
        
        rImg = new ResizeImg("images/introImg.png", 50, 30);
        resizeImg = rImg.getResizeImage();
        introImg = new ImageIcon(resizeImg);
        
        intro.setIcon(introImg);
        intro.setBorderPainted(false);
        intro.setContentAreaFilled(false);
        intro.setFocusPainted(false);

        rImg = new ResizeImg("images/btn_gamestart2.png", 210, 80);
        resizeImg = rImg.getResizeImage();
        icon = new ImageIcon(resizeImg);
        gameStart.setIcon(icon);
        gameStart.setBorderPainted(false);
        gameStart.setContentAreaFilled(false);
        gameStart.setFocusPainted(false);

        rImg = new ResizeImg("images/home.png", 50, 50);
        resizeImg = rImg.getResizeImage();
        home = new ImageIcon(resizeImg);
        goToHome.setIcon(home);
        goToHome.setBorderPainted(false);
        goToHome.setContentAreaFilled(false);
        goToHome.setFocusPainted(false);

        rImg = new ResizeImg("images/cheers.png", 50, 50);
        resizeImg = rImg.getResizeImage();
        cheers = new ImageIcon(resizeImg);
        penalty.setIcon(cheers);
        penalty.setBorderPainted(false);
        penalty.setContentAreaFilled(false);
        penalty.setFocusPainted(false);

        rImg = new ResizeImg("images/question-mark.png", 50, 50);
        resizeImg = rImg.getResizeImage();
        question = new ImageIcon(resizeImg);
        help.setIcon(question);
        help.setBorderPainted(false);
        help.setContentAreaFilled(false);
        help.setFocusPainted(false);
        
        // hover 이미지
        rImg = new ResizeImg("images/hoverMute.png", 30, 30);
        resizeImg = rImg.getResizeImage();
        hoverMusicOff = new ImageIcon(resizeImg);
        
        rImg = new ResizeImg("images/hoverVolume.png", 30, 30);
        resizeImg = rImg.getResizeImage();
        hoverMusicOn = new ImageIcon(resizeImg);
        
        rImg = new ResizeImg("images/hoverIntro.png", 50, 30);
        resizeImg = rImg.getResizeImage();
        hoverIntro = new ImageIcon(resizeImg);
        
        rImg = new ResizeImg("images/hoverHome.png", 50, 50);
        resizeImg = rImg.getResizeImage();
        hoverHome = new ImageIcon(resizeImg);

        rImg = new ResizeImg("images/hoverCheers.png", 50, 50);
        resizeImg = rImg.getResizeImage();
        hoverCheers = new ImageIcon(resizeImg);

        rImg = new ResizeImg("images/hoverQuestion-mark.png", 50, 50);
        resizeImg = rImg.getResizeImage();
        hoverHelp = new ImageIcon(resizeImg);
		
	}
	
	public void addMainPanel() {
		add(help);
		add(bgm); 
		add(intro); 
		add(gameStart);
		add(peopleField);
		add(insertPeople); 
		add(lblstatePeople);
		add(goToHome); 
		add(penalty);
		add(lbl); 
		revalidate();
		repaint();
	}
	
	public void createGameSelector() {		
		removeAll();
		game = new GameSelector(this, client);
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
				peopleField.setVisible(false);
				insertPeople.setVisible(false);
			}
			else if(object == goToHome) {
				removeAll();
				addMainPanel();
				revalidate();							
				repaint();
			
				lblstatePeople.setVisible(true);
				gameStart.setVisible(true);
				peopleField.setVisible(true);
				insertPeople.setVisible(true);
				
				peopleField.setEnabled(true);
                insertPeople.setEnabled(true); // 사용자 입력 못하도록
			} else if (object == insertPeople || object == peopleField) {
                String output = peopleField.getText();
                people = Integer.parseInt(output);
                peopleField.setText(""); //텍스트 창 비우기

                lblstatePeople.setVisible(false);
                gameStart.setVisible(true);

                peopleField.setEnabled(false);
                insertPeople.setEnabled(false); // 사용자 입력 못하도록
            } else if (object == help) {
                helpFrame = new Help();
            } else if (object == penalty) {
                createSadari();
            }
		}
	}
	
	private class hoverListener implements MouseListener {

		@Override
		public void mouseEntered(MouseEvent e) {
			Object obj = e.getSource();
			if (obj == goToHome) {
				goToHome.setIcon(hoverHome);
				goToHome.setBorderPainted(false);
		        goToHome.setContentAreaFilled(false);
		        goToHome.setFocusPainted(false);
			} else if (obj == penalty) {
				penalty.setIcon(hoverCheers);
				penalty.setBorderPainted(false);
				penalty.setContentAreaFilled(false);
				penalty.setFocusPainted(false);
			} else if (obj == help) {
				help.setIcon(hoverHelp);
				help.setBorderPainted(false);
				help.setContentAreaFilled(false);
				help.setFocusPainted(false);
			} else if (obj == intro) {
				intro.setIcon(hoverIntro);
				intro.setBorderPainted(false);
				intro.setContentAreaFilled(false);
				intro.setFocusPainted(false);
			} else if (obj == bgm) {
				if (bgmOn == 0) {
                    bgm.setIcon(hoverMusicOff);
                } else {
                    bgm.setIcon(hoverMusicOn);
                }
				bgm.setBorderPainted(false);
				bgm.setContentAreaFilled(false);
				bgm.setFocusPainted(false);
			}
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			Object obj = e.getSource();
			if (obj == goToHome) {
				goToHome.setIcon(home);
				goToHome.setBorderPainted(false);
		        goToHome.setContentAreaFilled(false);
		        goToHome.setFocusPainted(false);
			} else if (obj == penalty) {
				penalty.setIcon(cheers);
				penalty.setBorderPainted(false);
				penalty.setContentAreaFilled(false);
				penalty.setFocusPainted(false);
			} else if (obj == help) {
				help.setIcon(question);
				help.setBorderPainted(false);
				help.setContentAreaFilled(false);
				help.setFocusPainted(false);
			} else if (obj == intro) {
				intro.setIcon(introImg);
				intro.setBorderPainted(false);
				intro.setContentAreaFilled(false);
				intro.setFocusPainted(false);
			} else if (obj == bgm) {
				if (bgmOn == 0) {
                    bgm.setIcon(musicOff);
                } else {
                    bgm.setIcon(musicOn);
                }
				bgm.setBorderPainted(false);
				bgm.setContentAreaFilled(false);
				bgm.setFocusPainted(false);
			}
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {}
		
		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}
    	
    }
	
	public void createSadari() {
        removeAll();
        sadari = new Sadari();
        add(sadari);
        addMainPanel();
        revalidate();
        repaint();
        gameStart.setVisible(false);
        peopleField.setVisible(false);
        insertPeople.setVisible(false);
    }

    public int getPeopleNum(){
        return people;
    }
}