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
	
	// ��Ģȭ��
	Sadari sadari;
	
	// �̹��� ũ�� ����
	private Image resizeImg;
	private ResizeImg rImg;

	private Font fnt;


	private final int WIDTH = 230;
	private final int HEIGHT = 230;

	public GameSelector(MainPanel m) {
		
		main = m;

		try {
			fnt = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("font/SSShinb7.ttf"))).deriveFont(Font.PLAIN,20);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(" not loaded.  Using serif font.");
			fnt = new Font("serif", Font.PLAIN, 24);
		}


		// ���� �� add
		// bottlecap
		rImg = new ResizeImg("images/1.png", WIDTH-20,HEIGHT-20);
		resizeImg = rImg.getResizeImage();
		imgBottle = new ImageIcon(resizeImg);		
		playBottleCap = new JLabel("BottleCap", imgBottle, SwingConstants.CENTER);
		playBottleCap.setHorizontalTextPosition(SwingConstants.CENTER);
		playBottleCap.setVerticalTextPosition(SwingConstants.BOTTOM);
		playBottleCap.setFont(fnt);
		playBottleCap.setBounds(180,150,WIDTH,HEIGHT+10);
		main.add(playBottleCap);
		
		// catch!catch!
		rImg = new ResizeImg("images/catchPlay.png", WIDTH-20,HEIGHT-20);
		resizeImg = rImg.getResizeImage();
		imgCatch = new ImageIcon(resizeImg);
		playCatch = new JLabel("Catch!Catch!", imgCatch, SwingConstants.CENTER);
		playCatch.setHorizontalTextPosition(SwingConstants.CENTER);
		playCatch.setVerticalTextPosition(SwingConstants.BOTTOM);
		playCatch.setFont(fnt);
		playCatch.setBounds(180+WIDTH+15,150,WIDTH,HEIGHT+10);
		main.add(playCatch);
		
		// singcontest
		rImg = new ResizeImg("images/singcontestPlay.png", WIDTH-20,HEIGHT-20);
		resizeImg = rImg.getResizeImage();
		imgSingContest = new ImageIcon(resizeImg);
		playSingContest = new JLabel("Sing Contest", imgSingContest, SwingConstants.CENTER);
		playSingContest.setHorizontalTextPosition(SwingConstants.CENTER);
		playSingContest.setVerticalTextPosition(SwingConstants.BOTTOM);
		playSingContest.setFont(fnt);
		playSingContest.setBounds(180+WIDTH*2+30,150,WIDTH,HEIGHT+10);
		main.add(playSingContest);
		
		// hunmin
		rImg = new ResizeImg("images/hunminPlay.png", WIDTH-20,HEIGHT-20);
		resizeImg = rImg.getResizeImage();
		imgHunmin = new ImageIcon(resizeImg);
		playHunmin = new JLabel("Hunmin Jungum", imgHunmin, SwingConstants.CENTER);
		playHunmin.setHorizontalTextPosition(SwingConstants.CENTER);
		playHunmin.setVerticalTextPosition(SwingConstants.BOTTOM);
		playHunmin.setFont(fnt);
		playHunmin.setBounds(180,170+HEIGHT,WIDTH,HEIGHT+10);
		main.add(playHunmin);
		
		// ��!��!
		rImg = new ResizeImg("images/randomPlay.png", WIDTH-20,HEIGHT-20);
		resizeImg = rImg.getResizeImage();
		imgRandom = new ImageIcon(resizeImg);
		playRandom = new JLabel("Random", imgRandom, SwingConstants.CENTER);
		playRandom.setHorizontalTextPosition(SwingConstants.CENTER);
		playRandom.setVerticalTextPosition(SwingConstants.BOTTOM);
		playRandom.setFont(fnt);
		playRandom.setBounds(180+WIDTH+15,170+HEIGHT,WIDTH,HEIGHT+10);
		main.add(playRandom);
		
		// random
		rImg = new ResizeImg("images/randomPlay.png", WIDTH-20,HEIGHT-20);
		resizeImg = rImg.getResizeImage();
		imgRandom = new ImageIcon(resizeImg);
		playRandom = new JLabel("Random", imgRandom, SwingConstants.CENTER);
		playRandom.setHorizontalTextPosition(SwingConstants.CENTER);
		playRandom.setVerticalTextPosition(SwingConstants.BOTTOM);
		playRandom.setFont(fnt);
		playRandom.setBounds(180 + WIDTH*2 + 30,170+HEIGHT,WIDTH,HEIGHT+10);
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
		game = new Game(this); //���� ���� �г� ��ü ����
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
	
	// hover ȿ�� �� ��� ���콺 �����ʷ� �����ϱ�
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

