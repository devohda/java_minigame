package frame_panel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.*;

import game.bottlecap.BottleCapPanel;
import game.catchCatch.CatchCatchPanel;
import game.combination.Game;
import game.hunmingame.HunMinGame;
import sadari.Sadari;
import game.singer.Singer;
import socket.ClientGame;
import tool.*;

public class GameSelector  {
	
	private JLabel playBottleCap, playCatch, playRandom, playSingContest, playHunmin, playCombination; // 각 게임들을 위한 라벨들
	private ImageIcon imgBottle, imgCatch, imgRandom, imgSingContest, imgHunmin, imgCombination; // 각 게임 선택에 쓰이는 이미지들
	private BottleCapPanel bottleCap; // 보틀캡 객체 선언
	private CatchCatchPanel Catch; // 캐치캐치 객체 선언
	private Singer singer; // 전국노래자랑 객체 선언
	private HunMinGame hunmin; // 훈민정음 객체 선언
	private Game game; // 결합게임 객체 선언
	private Sound intro; // 시운드 객체 선언
	
	private MainPanel main; // 클래스 생성 시 인자로 전달받는 메인패널 위한 객체
	
	private startListener start; // startListener 객체 선언
	
	// 벌칙화면
	Sadari sadari;
	
	// 이미지 크기 조절
	private Image resizeImgBottle, resizeImgCatch, resizeImgSing, resizeImgHunmin, resizeImgCom, resizeImgRandom;
	private ResizeImg rImg;
	
	private Font fnt; // 폰트 객체 선언
	
	private final int WIDTH = 230; // 각 게임별 라벨의 크기
	private final int HEIGHT = 230;

	private int people; // 메인패널에서 입력받은 플레이어 수를 위한 변수
	
	private int game_number=0; // 인트로 번호
	
	private ClientGame client; // 클라이언트 구현을 위한 객체 선언
	
	public GameSelector(MainPanel m, ClientGame c) {
		
		main = m; // 메인패널의 객체 전달 받기
		people = m.getPeopleNum(); //사용자 수 가져오기	
		
		// 폰트 불러오기
		try {
			fnt = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("font/SSShinb7.ttf"))).deriveFont(Font.PLAIN,20);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(" not loaded.  Using serif font.");
			fnt = new Font("serif", Font.PLAIN, 24);
		}
		
		client = c; // 클라이언트 객체 전달 받기
		
		// 각 게임별 라벨 생성 후 메인패널에 add
		// ----------------------------------------
		// ----------------------------------------
		
		// game.bottlecap
		rImg = new ResizeImg("images/bottlePlay.png", WIDTH-20,HEIGHT-20);
		resizeImgBottle = rImg.getResizeImage();
		imgBottle = new ImageIcon(resizeImgBottle);		
		playBottleCap = new JLabel("BottleCap", imgBottle, SwingConstants.CENTER);
		playBottleCap.setHorizontalTextPosition(SwingConstants.CENTER);
		playBottleCap.setVerticalTextPosition(SwingConstants.BOTTOM);
		playBottleCap.setFont(fnt);
		playBottleCap.setBounds(180,150,WIDTH,HEIGHT+10);
		main.add(playBottleCap);
				
		// catch!catch!
		rImg = new ResizeImg("images/catchPlay.png", WIDTH-20,HEIGHT-20);
		resizeImgCatch = rImg.getResizeImage();
		imgCatch = new ImageIcon(resizeImgCatch);
		playCatch = new JLabel("Catch!Catch!", imgCatch, SwingConstants.CENTER);
		playCatch.setHorizontalTextPosition(SwingConstants.CENTER);
		playCatch.setVerticalTextPosition(SwingConstants.BOTTOM);
		playCatch.setFont(fnt);
		playCatch.setBounds(180+WIDTH+15,150,WIDTH,HEIGHT+10);
		main.add(playCatch);
				
		// singcontest
		rImg = new ResizeImg("images/singcontestPlay.png", WIDTH-20,HEIGHT-20);
		resizeImgSing = rImg.getResizeImage();
		imgSingContest = new ImageIcon(resizeImgSing);
		playSingContest = new JLabel("Sing Contest", imgSingContest, SwingConstants.CENTER);
		playSingContest.setHorizontalTextPosition(SwingConstants.CENTER);
		playSingContest.setVerticalTextPosition(SwingConstants.BOTTOM);
		playSingContest.setFont(fnt);
		playSingContest.setBounds(180+WIDTH*2+30,150,WIDTH,HEIGHT+10);
		main.add(playSingContest);
				
		// hunmin
		rImg = new ResizeImg("images/hunminPlay.png", WIDTH-20,HEIGHT-20);
		resizeImgHunmin = rImg.getResizeImage();
		imgHunmin = new ImageIcon(resizeImgHunmin);
		playHunmin = new JLabel("Hunmin Jungum", imgHunmin, SwingConstants.CENTER);
		playHunmin.setHorizontalTextPosition(SwingConstants.CENTER);
		playHunmin.setVerticalTextPosition(SwingConstants.BOTTOM);
		playHunmin.setFont(fnt);
		playHunmin.setBounds(180,170+HEIGHT,WIDTH,HEIGHT+10);
		main.add(playHunmin);
				
		// 결!합!
		rImg = new ResizeImg("images/combiPlay.png", WIDTH-20,HEIGHT-20);
		resizeImgCom = rImg.getResizeImage();
		imgCombination = new ImageIcon(resizeImgCom);
		playCombination = new JLabel("Combination", imgCombination, SwingConstants.CENTER);
		playCombination.setHorizontalTextPosition(SwingConstants.CENTER);
		playCombination.setVerticalTextPosition(SwingConstants.BOTTOM);
		playCombination.setFont(fnt);
		playCombination.setBounds(180+WIDTH+15,170+HEIGHT,WIDTH,HEIGHT+10);
		main.add(playCombination);
				
		// random
		rImg = new ResizeImg("images/randomPlay.png", WIDTH-20,HEIGHT-20);
		resizeImgRandom = rImg.getResizeImage();
		imgRandom = new ImageIcon(resizeImgRandom);
		playRandom = new JLabel("Random", imgRandom, SwingConstants.CENTER);
		playRandom.setHorizontalTextPosition(SwingConstants.CENTER);
		playRandom.setVerticalTextPosition(SwingConstants.BOTTOM);
		playRandom.setFont(fnt);
		playRandom.setBounds(180 + WIDTH*2 + 30,170+HEIGHT,WIDTH,HEIGHT+10);
		main.add(playRandom);
				
		//add action listener
		start = new startListener(); // startListener 객체 생성
		playBottleCap.addMouseListener(start);
		playCatch.addMouseListener(start);
		playSingContest.addMouseListener(start);
		playHunmin.addMouseListener(start);
		playCombination.addMouseListener(start);
		playRandom.addMouseListener(start);
		
	}
	
	public void createBottleCap() {
		main.removeAll(); // 현재 메인패널에 있는 객체들 모두 지우기
		bottleCap = new BottleCapPanel(client, this, main); // game.bottlecap 객체 생성
		main.offMainIntro();// 게임 시작되면 이전에 틀어진 인트로 꺼주기
		game_number = 1;// 인트로 번호
		main.add(bottleCap); // 메인패널에 보틀캡 패널 추가
		main.addMainPanel(); // 메인패널에 기존에 있던 라벨, 버튼들 추가
		main.revalidate(); // 새롭게 적용
		main.repaint();
	}
	
	
	public void createCatchCatch() {
		main.removeAll(); // 현재 메인패널에 있는 객체들 모두 지우기
		Catch = new CatchCatchPanel(client, this, main); // catchcatch 객체 재생성
		main.offMainIntro();// 게임 시작되면 이전에 틀어진 인트로 꺼주기
		game_number = 2;// 인트로 번호
		main.add(Catch); // 메인패널에 캐치캐치 패널 추가
		main.addMainPanel(); // 메인패널에 기존에 있던 라벨, 버튼들 추가
		main.revalidate(); // 새롭게 적용
		main.repaint();
	}
	
	public void createSingContest() {
		main.removeAll(); // 현재 메인패널에 있는 객체들 모두 지우기
		singer = new Singer(client, this, main); //전국노래자랑 실행 패널 객체 생성
		main.offMainIntro();// 게임 시작되면 이전에 틀어진 인트로 꺼주기
		game_number = 3;// 인트로 번호
		main.add(singer);
		main.addMainPanel(); // 메인패널에 기존에 있던 라벨, 버튼들 추가
		main.revalidate(); // 새롭게 적용
		main.repaint();
	}
	
	public void createHunmin() {
		main.removeAll(); // 현재 메인패널에 있는 객체들 모두 지우기
		hunmin = new HunMinGame(client, this, main); //훈민정음 실행 패널 객체 생성
		main.offMainIntro();// 게임 시작되면 이전에 틀어진 인트로 꺼주기
		game_number = 4;// 인트로 번호
		main.add(hunmin); // 메인패널에 훈민 패널 추가
		main.addMainPanel(); // 메인패널에 기존에 있던 라벨, 버튼들 추가
		main.revalidate(); // 새롭게 적용
		main.repaint();
	}
	
	public void createCombination() {
		main.removeAll(); // 현재 메인패널에 있는 객체들 모두 지우기
		game = new Game(client, this, main); //게임 실행 패널 객체 생성
		main.offMainIntro(); // 게임 시작되면 이전에 틀어진 인트로 꺼주기
		game_number = 5; // 인트로 번호
		main.add(game); // 메인패널에 결합 패널 추가
		main.addMainPanel(); // 메인패널에 기존에 있던 라벨, 버튼들 추가
		main.revalidate(); // 새롭게 적용
		main.repaint();
	}
	
	public void createSadari() {
		main.removeAll(); // 현재 메인패널에 있는 객체들 모두 지우기
		sadari = new Sadari(); //사다리 실행 패널 객체 생성
		main.add(sadari); // 메인패널에 사다리 패널 추가
		main.addMainPanel(); // 메인패널에 기존에 있던 라벨, 버튼들 추가
		main.revalidate(); // 새롭게 적용
		main.repaint();
	}
	
	public int getPeopleNum(){
		return people;

	} 
	
	// 각 게임별 라벨 클릭 시 해당 게임 실행을 위한 리스너
	private class startListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			// 마우스로 클릭한 경우로 해당 게임 실행
			Object obj = e.getSource();
			
			if (obj == playBottleCap) {
				createBottleCap(); // 병뚜껑 숫자 피하기 게임 실행을 위한 함수 호출
			} else if (obj == playCatch) {
				createCatchCatch(); // 캐치캐치 게임 실행을 위한 함수 호출
			} else if (obj == playSingContest) {
				createSingContest(); // 전국노래자랑 게임 실행을 위한 함수 호출
			} else if (obj == playHunmin) {
				createHunmin(); // 훈민정음 게임 실행을 위한 함수 호출
			} else if (obj == playCombination) {
				createCombination(); // 결합 게임 실행을 위한 함수 호출
			} else if (obj == playRandom) {
				
				// 랜덤으로 인덱스 생성 후 해당 숫자에 따른 게임 실행을 위한 함수 호출
				int _randNum = (int)(Math.random()*5) + 1;
				
				if (_randNum == 1) createBottleCap();
				else if (_randNum == 2) createCatchCatch();
				else if (_randNum == 3) createSingContest();
				else if (_randNum == 4) createHunmin();
				else if (_randNum == 5) createCombination();
			}
		}
	
		@Override
		public void mouseEntered(MouseEvent e) {
			
			// 마우스가 라벨에 들어올 경우 호버 효과 적용 (투명도 50%)
			Object obj = e.getSource();
			
			if (obj == playBottleCap) {
				rImg = new ResizeImg("images/hoverBottle.png", WIDTH-20,HEIGHT-20);
				resizeImgBottle = rImg.getResizeImage();
				imgBottle = new ImageIcon(resizeImgBottle);	
				playBottleCap.setIcon(imgBottle);
				
			} else if (obj == playCatch) {
				rImg = new ResizeImg("images/hoverCatch.png", WIDTH-20,HEIGHT-20);
				resizeImgCatch = rImg.getResizeImage();
				imgCatch = new ImageIcon(resizeImgCatch);	
				playCatch.setIcon(imgCatch);
			} else if (obj == playSingContest) {
				rImg = new ResizeImg("images/hoverSinger.png", WIDTH-20,HEIGHT-20);
				resizeImgSing = rImg.getResizeImage();
				imgSingContest = new ImageIcon(resizeImgSing);	
				playSingContest.setIcon(imgSingContest);
			} else if (obj == playHunmin) {
				rImg = new ResizeImg("images/hoverHunmin.png", WIDTH-20,HEIGHT-20);
				resizeImgHunmin = rImg.getResizeImage();
				imgHunmin = new ImageIcon(resizeImgHunmin);	
				playHunmin.setIcon(imgHunmin);
			} else if (obj == playCombination) {
				rImg = new ResizeImg("images/hoverCombination.png", WIDTH-20,HEIGHT-20);
				resizeImgCom = rImg.getResizeImage();
				imgCombination = new ImageIcon(resizeImgCom);	
				playCombination.setIcon(imgCombination);
			} else if (obj == playRandom) {
				rImg = new ResizeImg("images/hoverRandom.png", WIDTH-20,HEIGHT-20);
				resizeImgRandom = rImg.getResizeImage();
				imgRandom = new ImageIcon(resizeImgRandom);	
				playRandom.setIcon(imgRandom);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
			// 마우스가 라벨을 벗어날 경우 호버효과 해제
			Object obj = e.getSource();
			
			if (obj == playBottleCap) {
				rImg = new ResizeImg("images/bottlePlay.png", WIDTH-20,HEIGHT-20);
				resizeImgBottle = rImg.getResizeImage();
				imgBottle = new ImageIcon(resizeImgBottle);	
				playBottleCap.setIcon(imgBottle);				
			} else if (obj == playCatch) {
				rImg = new ResizeImg("images/catchPlay.png", WIDTH-20,HEIGHT-20);
				resizeImgCatch = rImg.getResizeImage();
				imgCatch = new ImageIcon(resizeImgCatch);	
				playCatch.setIcon(imgCatch);
			} else if (obj == playSingContest) {
				rImg = new ResizeImg("images/singcontestPlay.png", WIDTH-20,HEIGHT-20);
				resizeImgSing = rImg.getResizeImage();
				imgSingContest = new ImageIcon(resizeImgSing);	
				playSingContest.setIcon(imgSingContest);
			} else if (obj == playHunmin) {
				rImg = new ResizeImg("images/hunminPlay.png", WIDTH-20,HEIGHT-20);
				resizeImgHunmin = rImg.getResizeImage();
				imgHunmin = new ImageIcon(resizeImgHunmin);	
				playHunmin.setIcon(imgHunmin);
			} else if (obj == playCombination) {
				rImg = new ResizeImg("images/combiPlay.png", WIDTH-20,HEIGHT-20);
				resizeImgCom = rImg.getResizeImage();
				imgCombination = new ImageIcon(resizeImgCom);	
				playCombination.setIcon(imgCombination);
			} else if (obj == playRandom) {
				rImg = new ResizeImg("images/randomPlay.png", WIDTH-20,HEIGHT-20);
				resizeImgRandom = rImg.getResizeImage();
				imgRandom = new ImageIcon(resizeImgRandom);	
				playRandom.setIcon(imgRandom);
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}


	}
	public void offIntro() { // 게임을 나갈 때 게임의 인트로를 꺼주기 위함
		if(intro!=null) {
			intro.Off();
		}
		main.offMainIntro(); // 메인의 노래도 확실하게 꺼주기 위함
	}
	
	public int getgameNum() { // 게임을 선택하면 게임에 해당하는 인트로를 얻기 위한 함수
		return game_number;
	}
	
	public void setgameNumZero() {
		game_number = 0; // 게임이 끝나거나 메인화면으로 넘어갈 시 인트로 번호 0 으로 넘겨주기 위함
	}
	public int getIntroNumber() { // 게임을 선택하면 게임에 해당하는 인트로를 얻기 위한 함수 // 함수 확인 실수로 같은 기능으로 재생성
		return game_number;
	}
	
	public void onOff(int onOff) { // 게임을 나갈 때 게임의 인트로를 꺼주기 위함  // 함수 확인 실수로 같은 기능으로 재생성
		if(intro!=null) {
			intro.Off();
		}
	}

}

