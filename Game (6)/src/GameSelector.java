
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import BottleCap.BottleCapPanel;
import Catch.CatchCatchPanel;
import Sadari.Sadari;
import combination.Game;

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
	private Image resizeImgBottle, resizeImgCatch, resizeImgSing, resizeImgHunmin, resizeImgCom, resizeImgRandom;
	private ResizeImg rImg;
	
	public GameSelector(MainPanel m) {
		
		main = m;

		Font fnt = new Font("Verdana", Font.BOLD, 20);
		
		// 게임 라벨 add
		// bottlecap
		rImg = new ResizeImg("images/1.png", 300, 240);
		resizeImgBottle = rImg.getResizeImage();
		imgBottle = new ImageIcon(resizeImgBottle);		
		playBottleCap = new JLabel("BottleCap", imgBottle, SwingConstants.CENTER);
		playBottleCap.setHorizontalTextPosition(SwingConstants.CENTER);
		playBottleCap.setVerticalTextPosition(SwingConstants.BOTTOM);
		playBottleCap.setFont(fnt);
		playBottleCap.setForeground(Color.green);
		playBottleCap.setBounds(50,120,300,270);
		main.add(playBottleCap);
		
		// catch!catch!
		rImg = new ResizeImg("images/catchPlay.png", 300, 240);
		resizeImgCatch = rImg.getResizeImage();
		imgCatch = new ImageIcon(resizeImgCatch);
		playCatch = new JLabel("Catch!Catch!", imgCatch, SwingConstants.CENTER);
		playCatch.setHorizontalTextPosition(SwingConstants.CENTER);
		playCatch.setVerticalTextPosition(SwingConstants.BOTTOM);
		playCatch.setFont(fnt);
		playCatch.setForeground(Color.green);
		playCatch.setBounds(375,120,300,270);
		main.add(playCatch);
		
		// singcontest
		rImg = new ResizeImg("images/singcontestPlay.png", 300, 240);
		resizeImgSing = rImg.getResizeImage();
		imgSingContest = new ImageIcon(resizeImgSing);
		playSingContest = new JLabel("Sing Contest", imgSingContest, SwingConstants.CENTER);
		playSingContest.setHorizontalTextPosition(SwingConstants.CENTER);
		playSingContest.setVerticalTextPosition(SwingConstants.BOTTOM);
		playSingContest.setFont(fnt);
		playSingContest.setForeground(Color.green);
		playSingContest.setBounds(700,120,300,270);
		main.add(playSingContest);
		
		// hunmin
		rImg = new ResizeImg("images/hunminPlay.png", 300, 240);
		resizeImgHunmin = rImg.getResizeImage();
		imgHunmin = new ImageIcon(resizeImgHunmin);
		playHunmin = new JLabel("Hunmin Jungum", imgHunmin, SwingConstants.CENTER);
		playHunmin.setHorizontalTextPosition(SwingConstants.CENTER);
		playHunmin.setVerticalTextPosition(SwingConstants.BOTTOM);
		playHunmin.setFont(fnt);
		playHunmin.setForeground(Color.green);
		playHunmin.setBounds(50,410,300,270);
		main.add(playHunmin);
		
		// 결!합!
		rImg = new ResizeImg("images/combinationPlay.png", 300, 240);
		resizeImgCom = rImg.getResizeImage();
		imgCombination = new ImageIcon(resizeImgCom);
		playCombination = new JLabel("Combination", imgCombination, SwingConstants.CENTER);
		playCombination.setHorizontalTextPosition(SwingConstants.CENTER);
		playCombination.setVerticalTextPosition(SwingConstants.BOTTOM);
		playCombination.setFont(fnt);
		playCombination.setForeground(Color.green);
		playCombination.setBounds(375,410,300,270);
		main.add(playCombination);
		
		// random
		rImg = new ResizeImg("images/randomPlay.png", 300, 240);
		resizeImgRandom = rImg.getResizeImage();
		imgRandom = new ImageIcon(resizeImgRandom);
		playRandom = new JLabel("Random", imgRandom, SwingConstants.CENTER);
		playRandom.setHorizontalTextPosition(SwingConstants.CENTER);
		playRandom.setVerticalTextPosition(SwingConstants.BOTTOM);
		playRandom.setFont(fnt);
		playRandom.setForeground(Color.green);
		playRandom.setBounds(700,410,300,270);
		main.add(playRandom);
		
		playBottleCap.addMouseListener(new startListener());
		playCatch.addMouseListener(new startListener());
		playSingContest.addMouseListener(new startListener());
		playHunmin.addMouseListener(new startListener());
		playCombination.addMouseListener(new startListener());
		playRandom.addMouseListener(new startListener());
		
	}
	
	public void createBottleCap() {
		main.removeAll();
		bottleCap = new BottleCapPanel();
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
		game = new Game(); //게임 실행 패널 객체 생성
		game.init();
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
			} else if (obj == playCombination) {
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
		
	}
}

