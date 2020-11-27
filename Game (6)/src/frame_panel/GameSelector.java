package frame_panel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.*;

import jun_chang.BottleCapPanel;
import jun_chang.CatchCatchPanel;
import jun_chang.Sadari;
import combination.Game;
import hunmingame.HunMinGame;
import singer.Singer;

import tool.ResizeImg;

public class GameSelector  {
	
	private JLabel playBottleCap, playCatch, playRandom, playSingContest, playHunmin, playCombination;
	private ImageIcon imgBottle, imgCatch, imgRandom, imgSingContest, imgHunmin, imgCombination;	
	private BottleCapPanel bottleCap;
	private CatchCatchPanel Catch;
	private Singer singer;
	private HunMinGame hunmin;
	private Game game;
	
	private MainPanel main;
	
	private startListener start;
	
	// 벌칙화면
	Sadari sadari;
	
	// 이미지 크기 조절
	private Image resizeImgBottle, resizeImgCatch, resizeImgSing, resizeImgHunmin, resizeImgCom, resizeImgRandom;
	private ResizeImg rImg;

	private Font fnt;


	private final int WIDTH = 230;
	private final int HEIGHT = 230;

	private int people;
	
	private int game_number=0;

	public GameSelector(MainPanel m) {
		
		
		main = m;
		people = m.getPeopleNum(); //사용자 수 가져오기
		
		try {
			fnt = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("font/SSShinb7.ttf"))).deriveFont(Font.PLAIN,20);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(" not loaded.  Using serif font.");
			fnt = new Font("serif", Font.PLAIN, 24);
		}


		// 게임 라벨 add
		// bottlecap
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
        start = new startListener();
        playBottleCap.addMouseListener(start);
        playCatch.addMouseListener(start);
		playSingContest.addMouseListener(start);
		playHunmin.addMouseListener(start);
		playCombination.addMouseListener(start);
		playRandom.addMouseListener(start);
		
		
	}
	
	public void createBottleCap() {
		main.removeAll();
		//bottleCap = new BottleCapPanel();
		bottleCap = new BottleCapPanel(this, main); 
		main.add(bottleCap);
		main.addMainPanel();
		main.revalidate();
		main.repaint();
	}
	
	
	public void createCatchCatch() {
		main.removeAll();
		Catch = new CatchCatchPanel(this, main);
		main.add(Catch);
		main.addMainPanel();
		main.revalidate();
		main.repaint();
	}
	
	public void createSingContest() {
		main.removeAll();
		game_number = 3;
		singer = new Singer(main);
		main.add(singer);
		main.addMainPanel();
		main.revalidate();
		main.repaint();
	}
	
	public void createHunmin() {
		main.removeAll();
		game_number = 4;
		hunmin = new HunMinGame(main);
		main.add(hunmin);
		main.addMainPanel();
		main.revalidate();
		main.repaint();
	}
	
	public void createCombination() {
		main.removeAll();
		game = new Game(this, main); //게임 실행 패널 객체 생성
		main.add(game);
		main.addMainPanel();
		main.revalidate();
		main.repaint();
	}
	
	public void createSadari() {
		main.removeAll();
		sadari = new Sadari();
		main.add(sadari);
		main.addMainPanel();
		main.revalidate();
		main.repaint();
	}
	
	public int getPeopleNum(){
		return people;

	} 
	
	public int getIntroNumber() {
		return game_number;
	}
	
	private class startListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			Object obj = e.getSource();
			
			if (obj == playBottleCap) {
				createBottleCap();				
			} else if (obj == playCatch) {
				createCatchCatch();
			} else if (obj == playSingContest) {
				createSingContest();
			} else if (obj == playHunmin) {
				createHunmin();
			} else if (obj == playCombination) {
				System.out.println("hello");
				createCombination();
			} else if (obj == playRandom) {
				
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

}

