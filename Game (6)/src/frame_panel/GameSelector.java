package frame_panel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	
	// 벌칙화면
	Sadari sadari;
	
	// 이미지 크기 조절
	private Image resizeImg;
	private ResizeImg rImg;

	private Font fnt;


	private final int WIDTH = 170;
	private final int HEIGHT = 170;

	public GameSelector(MainPanel m) {
		
		main = m;

		try {
			fnt = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("font/SSShinb7.ttf"))).deriveFont(Font.PLAIN,20);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(" not loaded.  Using serif font.");
			fnt = new Font("serif", Font.PLAIN, 24);
		}


		// 게임 라벨 add
		// bottlecap
		rImg = new ResizeImg("images/1.png", WIDTH-20,HEIGHT-20);
		resizeImg = rImg.getResizeImage();
		imgBottle = new ImageIcon(resizeImg);		
		playBottleCap = new JLabel("BottleCap", imgBottle, SwingConstants.CENTER);
		playBottleCap.setHorizontalTextPosition(SwingConstants.CENTER);
		playBottleCap.setVerticalTextPosition(SwingConstants.CENTER);
		playBottleCap.setFont(fnt);
		playBottleCap.setBounds(155,100,WIDTH,HEIGHT);
		main.add(playBottleCap);
		
		// catch!catch!
		rImg = new ResizeImg("images/catchPlay.png", WIDTH-20,HEIGHT-20);
		resizeImg = rImg.getResizeImage();
		imgCatch = new ImageIcon(resizeImg);
		playCatch = new JLabel("Catch!Catch!", imgCatch, SwingConstants.CENTER);
		playCatch.setHorizontalTextPosition(SwingConstants.CENTER);
		playCatch.setVerticalTextPosition(SwingConstants.CENTER);
		playCatch.setFont(fnt);
		playCatch.setBounds(340,100,WIDTH,HEIGHT);
		main.add(playCatch);
		
		// singcontest
		rImg = new ResizeImg("images/singcontestPlay.png", WIDTH-20,HEIGHT-20);
		resizeImg = rImg.getResizeImage();
		imgSingContest = new ImageIcon(resizeImg);
		playSingContest = new JLabel("Sing Contest", imgSingContest, SwingConstants.CENTER);
		playSingContest.setHorizontalTextPosition(SwingConstants.CENTER);
		playSingContest.setVerticalTextPosition(SwingConstants.CENTER);
		playSingContest.setFont(fnt);
		playSingContest.setBounds(525,100,WIDTH,HEIGHT);
		main.add(playSingContest);
		
		// hunmin
		rImg = new ResizeImg("images/hunminPlay.png", WIDTH-20,HEIGHT-20);
		resizeImg = rImg.getResizeImage();
		imgHunmin = new ImageIcon(resizeImg);
		playHunmin = new JLabel("Hunmin Jungum", imgHunmin, SwingConstants.CENTER);
		playHunmin.setHorizontalTextPosition(SwingConstants.CENTER);
		playHunmin.setVerticalTextPosition(SwingConstants.CENTER);
		playHunmin.setFont(fnt);
		playHunmin.setBounds(155,280,WIDTH,HEIGHT);
		main.add(playHunmin);
		
		// 결!합!
		rImg = new ResizeImg("images/randomPlay.png", WIDTH-20,HEIGHT-20);
		resizeImg = rImg.getResizeImage();
		imgRandom = new ImageIcon(resizeImg);
		playRandom = new JLabel("Random", imgRandom, SwingConstants.CENTER);
		playRandom.setHorizontalTextPosition(SwingConstants.CENTER);
		playRandom.setVerticalTextPosition(SwingConstants.CENTER);
		playRandom.setFont(fnt);
		playRandom.setBounds(340,280,WIDTH,HEIGHT);
		main.add(playRandom);
		
		// random
		rImg = new ResizeImg("images/randomPlay.png", WIDTH-20,HEIGHT-20);
		resizeImg = rImg.getResizeImage();
		imgRandom = new ImageIcon(resizeImg);
		playRandom = new JLabel("Random", imgRandom, SwingConstants.CENTER);
		playRandom.setHorizontalTextPosition(SwingConstants.CENTER);
		playRandom.setVerticalTextPosition(SwingConstants.CENTER);
		playRandom.setFont(fnt);
		playRandom.setBounds(525,280,WIDTH,HEIGHT);
		main.add(playRandom);
		
		playBottleCap.addMouseListener(new startListener());
		playCatch.addMouseListener(new startListener());
		playSingContest.addMouseListener(new startListener());
		playHunmin.addMouseListener(new startListener());
		playRandom.addMouseListener(new startListener());
		playRandom.addMouseListener(new startListener());
		
	}
	
	public void createBottleCap() {
		main.removeAll();
		//bottleCap = new BottleCapPanel();
		bottleCap = new BottleCapPanel(this); 
		main.add(bottleCap);
		main.addMainPanel();
		main.revalidate();
		main.repaint();
	}
	
	
	public void createCatchCatch() {
		main.removeAll();
		Catch = new CatchCatchPanel();
		main.add(Catch);
		main.addMainPanel();
		main.revalidate();
		main.repaint();
	}
	
	public void createSingContest() {
		main.removeAll();
		singer = new Singer();
		main.add(singer);
		main.addMainPanel();
		main.revalidate();
		main.repaint();
	}
	
	public void createHunmin() {
		main.removeAll();
		hunmin = new HunMinGame();
		main.add(hunmin);
		main.addMainPanel();
		main.revalidate();
		main.repaint();
	}
	
	public void createCombination() {
		main.removeAll();
		game = new Game(this); //게임 실행 패널 객체 생성
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
	
	// hover 효과 줄 경우 마우스 리스너로 변경하기
	private class startListener extends MouseAdapter {
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
			} else if (obj == playRandom) {
				createCombination();
			}

		}
		
	}
}

