package frame_panel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import sadari.Sadari;
import socket.ClientGame;
import tool.*;


public class MainPanel extends JPanel {
	
	private JButton bgm; // 배경음악 버튼
	private JButton intro; // 인트로 버튼
	private RoundedButton gameStart; // 게임시작 버튼
	private JButton goToHome;
	private JButton help; // 도움말 프레임을 불러내는 버튼
	private ButtonListener buttonL; // 이벤트 리스터
	private JButton penalty; // 벌칙 클래스를 불러내는 버튼
	private int bgmOn = 0, introOn = 0, introNumber = 0; // 이벤트 핸들러

	private Sound music, intro_0; // 음악 클래스 객체 선언
	private GameSelector game; // 게임 선택창 패널 객체 선언
			
	private RoundedButton insertPeople ; // 입력하기 버튼 선언
	private JTextField peopleField; // 인원수 입력 필드 
	private JLabel lblstatePeople;
    private int people = 0;
    private JLabel lbl;
	
	private Sadari sadari;
	private Help helpFrame;
	
	// 이미지 크기 조절
    private Image resizeImg; // 재조정된 이미지
    private ResizeImg rImg; // 클래스 객체
	
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
        hover = new hoverListener(); // 마우스 리스너 객체 생성
		
        // 배경 이미지 삽입
        rImg = new ResizeImg("images/bg_2.jpg", 1050, 800); // 사이즈 조절할 이미지 클래스에 넣어주기
        resizeImg = rImg.getResizeImage(); // 재조정 이미지 반환
        icon = new ImageIcon(resizeImg); // 아이콘에 넣어주기
        lbl = new JLabel("", icon, SwingConstants.RIGHT); // 배경이미지 라벨에 삽입
        lbl.setBounds(0, 0, 1050, 800); // 배경이미지 위치 및 사이즈 조절
		
        // 객체 만들기
        gameStart = new RoundedButton("게임시작"); // 게임 선택 패널 객체를 불러오는 버튼
        gameStart.setBounds(420, 340, 210, 70); // 버튼 위치 및 사이즈 조절
        gameStart.setBackground(new Color(237, 248, 141));
        gameStart.addActionListener(buttonL); // 버튼에 리스너 삽입
        gameStart.setFont(fnt2); // 폰트 설정
        gameStart.setVisible(false); // 보이지 않게
		
        lblstatePeople = new JLabel("인원수를 입력하세요"); // 안내 글
        lblstatePeople.setBounds(420, 350, 210, 40); // 사이즈 및 위치 조절
        lblstatePeople.setHorizontalAlignment(JLabel.CENTER);// 위치조절
        lblstatePeople.setVerticalAlignment(JLabel.CENTER);// 위치조절
        lblstatePeople.setFont(fnt2);// 보이지 않게
        
        music = new Sound("sounds/bgm.wav"); // 배경음악에 넣을 음악 지정하며 객체 생성
        bgm = new JButton("BGM"); // 배경음악 버튼
        bgm.setBounds(810, 20, 80, 80); // 버튼 위치 및 사이즈 조절
        bgm.setFont(fnt);//폰트 설정
        bgm.setHorizontalTextPosition(JButton.CENTER);// 위치조절
        bgm.setVerticalTextPosition(JButton.BOTTOM);// 위치조절
        bgm.addActionListener(buttonL); // 버튼에 리스너 삽입
        bgm.addMouseListener(hover); //마우스 리스너 더해주기

        intro = new JButton("INTRO"); // 게임 인트로 버튼 생성
        intro.setBounds(900, 20, 100, 80); // 버튼 위치 및 사이즈 조절
        intro.setFont(fnt);// 폰트 설정
        intro.setHorizontalTextPosition(JButton.CENTER);// 위치조절
        intro.setVerticalTextPosition(JButton.BOTTOM);// 위치조절
        intro.addMouseListener(hover);//마우스 리스너 더해주기
        intro.addActionListener(buttonL);//액션리스너 더해주기


        insertPeople = new RoundedButton("입력"); // 인원수 입력 버튼 생성
        insertPeople.setBounds(530, 420, 100, 40); // 위치 및 사이즈 조절
        insertPeople.setFont(fnt2);// 폰트 설정
        insertPeople.addActionListener(buttonL); // 버튼에 리스너 삽입

        peopleField = new JTextField(); // 인원수 입력 필드 생성
        peopleField.setBounds(420, 420, 100, 40); // 위치 및 사이즈 조절
        peopleField.setFont(fnt2);// 폰트 설정
        peopleField.addActionListener(buttonL); // 버튼에 리스너 삽입



        // 하단 버튼 생성
        goToHome = new JButton("처음으로");// 메인화면으로 가는 버튼
        goToHome.setBounds(30, 650, 100, 100);// 위치 및 사이즈 조절
        goToHome.addActionListener(buttonL); // 버튼에 리스너 삽입
        goToHome.addMouseListener(hover);//마우스 리스너 더해주기
        
        goToHome.setVerticalTextPosition(JButton.TOP);// 위치조절
        goToHome.setHorizontalTextPosition(JButton.CENTER);// 위치조절
        goToHome.setFont(fnt);// 폰트 설정

        penalty = new JButton("벌칙으로");//벌칙 버튼
        penalty.setBounds(140, 650, 100, 100);// 위치 및 사이즈 조절
        penalty.addActionListener(buttonL); // 버튼에 리스너 삽입
        penalty.addMouseListener(hover);//마우스 리스너 더해주기
        
        penalty.setVerticalTextPosition(JButton.TOP);// 위치조절
        penalty.setHorizontalTextPosition(JButton.CENTER);// 위치조절
        penalty.setFont(fnt);

        help = new JButton("도움말");//도움말 버튼
        help.setBounds(900, 650, 100, 100);// 위치 및 사이즈 조절
        help.addActionListener(buttonL); // 버튼에 리스너 삽입
        help.addMouseListener(hover);//마우스 리스너 더해주기
        
        help.setVerticalTextPosition(JButton.TOP);// 위치조절
        help.setHorizontalTextPosition(JButton.CENTER);// 위치조절
        help.setFont(fnt);// 폰트 설정

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
	
	// 메인 패널구성하는 라벨, 버튼 추가
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
		revalidate(); // 새롭게 적용
		repaint();
	}
	
	public void createGameSelector() {		
		removeAll(); // 현재 메인패널에 있는 객체들을 모두 지움
		game = new GameSelector(this, client); // 게임셀렉터 생성
		revalidate(); // 새롭게 적용
		repaint();
	}
	
	private class ButtonListener implements ActionListener { // 액션 리스너
        public void actionPerformed(ActionEvent event) {
            Object object = event.getSource(); // 이벤트 소스 받아오기
            if (object == bgm) {  // bgm 눌리면
                if (bgmOn == 0) { // bgmOn이 0 이면
                    music.On(); // 음악 켜짐
                    bgmOn = 1; // bgmOn 1로
                    bgm.setIcon(musicOn); // 아이콘 바꿔주기
                } else {
                    music.Off(); //음악 꺼주기
                    bgmOn = 0; // bgmOn 0로
                    bgm.setIcon(musicOff);// 아이콘 바꿔주기
                }
            } else if (object == gameStart) {//---------------------
                createGameSelector();
                addMainPanel();
                
                gameStart.setVisible(false);
                peopleField.setVisible(false);
                insertPeople.setVisible(false);//---------------------
            } else if (object == goToHome) {//---------------------
                removeAll();
                addMainPanel();
                revalidate();
                repaint();
                offMainIntro();
                introNumber = 0;
                lblstatePeople.setVisible(true);
                gameStart.setVisible(false);
                insertPeople.setVisible(true);
                peopleField.setVisible(true);
            	if (game != null) game.setgameNumZero(); // 아직 게임셀렉터 객체가 생성되지 않은 경우 실행 하지 않음
                peopleField.setEnabled(true);
                insertPeople.setEnabled(true); // 사용자 입력 못하도록 //---------------------
            } else if (object == insertPeople || object == peopleField) {//---------------------
                String output = peopleField.getText();
                people = Integer.parseInt(output);
                peopleField.setText(""); //텍스트 창 비우기

                lblstatePeople.setVisible(false);
                gameStart.setVisible(true);

                peopleField.setEnabled(false);
                insertPeople.setEnabled(false); // 사용자 입력 못하도록//---------------------
            } else if (object == help) { // help가 눌리면
                helpFrame = new Help(); // 도움말 frame이 생성
            } else if (object == penalty) {//벌칙이 눌리면
                createSadari(); // 사다리 클래스 실행
            }
            else if (object == intro) { // 인트로 버튼 눌리면
                if (introOn == 0) {//introOn이 0이면
                    introOn = 1; //introOn 1
                    if(game==null) { // 게임셀렉터 생성이 안 되어있으면
                    	intro_0 = new Sound("sounds/main.wav"); // 이 음악파일 인트로로
                    	intro_0.On_1(); // 실행
                    }
                    else { // 게임셀렉터가 생성 되었으면
                    	introNumber = game.getIntroNumber(); // 게임 값을 받아와서 
                    	if(introNumber==0) {
                        	intro_0 = new Sound("sounds/main.wav");// 게임에 맞는 인트로를 틀어준다
                        	intro_0.On_1();
                    	}
                    	else if(introNumber==1) {
                        	intro_0 = new Sound("sounds/gamestart.wav");// 게임에 맞는 인트로를 틀어준다
                        	intro_0.On_1();
                    	}
                    	else if(introNumber==2) {
                        	intro_0 = new Sound("sounds/gamestart.wav");// 게임에 맞는 인트로를 틀어준다
                        	intro_0.On_1();
                    	}
                    	else if(introNumber==3) {
                        	intro_0 = new Sound("sounds/sing.wav");// 게임에 맞는 인트로를 틀어준다
                        	intro_0.On_1();
                    	}
                    	else if(introNumber==4) {
                        	intro_0 = new Sound("sounds/hun.wav");// 게임에 맞는 인트로를 틀어준다
                        	intro_0.On_1();
                    	}
                    	else if(introNumber==5) {
                        	intro_0 = new Sound("sounds/genius.wav");// 게임에 맞는 인트로를 틀어준다
                        	intro_0.On();
                    	}
                    }
                } else {//introOn이 1이면
                    introOn = 0;//introOn 0
                    if(game==null) { // 게임 셀렉터가 없으면
                    	intro_0.Off(); // 이미 지정되었던 노래 꺼주기
                    }
                    else { // 게임 셀렉터가 생성되었으면
                    	intro_0.Off(); // 이미 지정되었던 노래를 꺼주거나
                        game.onOff(introOn); // 함수 실행
                    }
                }
            }
        }
    }
    
    private class hoverListener implements MouseListener {//---------------------

		@Override
		public void mouseEntered(MouseEvent e) {
			
			// 마우스가 라벨에 들어올 경우 호버 효과 적용 (투명도 50%)
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
			
		}//---------------------

		@Override
		public void mouseExited(MouseEvent e) {//---------------------
			
			// 마우스가 라벨을 벗어날 경우 호버효과 해제
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
			}//---------------------
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {}
		
		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}
    	
    }

    // 위와 마찬가지로 메인패널을 모두 지우고 사다리 패널 객체 생성 후 추가 및 새롭게 적용
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

    public int getPeopleNum(){ // 인원수 반환 함수
        return people;
    }
    
    public void offMainIntro() { // 인트로 꺼주는 함수 
    	if(intro_0!=null) {
        	intro_0.Off();
    	}
    	introOn = 0;
    }
    public void setGameNum(int num) { // 받아온 값을 인트로 설정 값으로 설정하는 함수
    	introNumber = num; 
    }
}