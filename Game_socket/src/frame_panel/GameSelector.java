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
	
	private JLabel playBottleCap, playCatch, playRandom, playSingContest, playHunmin, playCombination; // �� ���ӵ��� ���� �󺧵�
	private ImageIcon imgBottle, imgCatch, imgRandom, imgSingContest, imgHunmin, imgCombination; // �� ���� ���ÿ� ���̴� �̹�����
	private BottleCapPanel bottleCap; // ��Ʋĸ ��ü ����
	private CatchCatchPanel Catch; // ĳġĳġ ��ü ����
	private Singer singer; // �����뷡�ڶ� ��ü ����
	private HunMinGame hunmin; // �ƹ����� ��ü ����
	private Game game; // ���հ��� ��ü ����
	private Sound intro; // �ÿ�� ��ü ����
	
	private MainPanel main; // Ŭ���� ���� �� ���ڷ� ���޹޴� �����г� ���� ��ü
	
	private startListener start; // startListener ��ü ����
	
	// ��Ģȭ��
	Sadari sadari;
	
	// �̹��� ũ�� ����
	private Image resizeImgBottle, resizeImgCatch, resizeImgSing, resizeImgHunmin, resizeImgCom, resizeImgRandom;
	private ResizeImg rImg;
	
	private Font fnt; // ��Ʈ ��ü ����
	
	private final int WIDTH = 230; // �� ���Ӻ� ���� ũ��
	private final int HEIGHT = 230;

	private int people; // �����гο��� �Է¹��� �÷��̾� ���� ���� ����
	
	private int game_number=0; // ��Ʈ�� ��ȣ
	
	private ClientGame client; // Ŭ���̾�Ʈ ������ ���� ��ü ����
	
	public GameSelector(MainPanel m, ClientGame c) {
		
		main = m; // �����г��� ��ü ���� �ޱ�
		people = m.getPeopleNum(); //����� �� ��������	
		
		// ��Ʈ �ҷ�����
		try {
			fnt = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("font/SSShinb7.ttf"))).deriveFont(Font.PLAIN,20);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(" not loaded.  Using serif font.");
			fnt = new Font("serif", Font.PLAIN, 24);
		}
		
		client = c; // Ŭ���̾�Ʈ ��ü ���� �ޱ�
		
		// �� ���Ӻ� �� ���� �� �����гο� add
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
				
		// ��!��!
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
		start = new startListener(); // startListener ��ü ����
		playBottleCap.addMouseListener(start);
		playCatch.addMouseListener(start);
		playSingContest.addMouseListener(start);
		playHunmin.addMouseListener(start);
		playCombination.addMouseListener(start);
		playRandom.addMouseListener(start);
		
	}
	
	public void createBottleCap() {
		main.removeAll(); // ���� �����гο� �ִ� ��ü�� ��� �����
		bottleCap = new BottleCapPanel(client, this, main); // game.bottlecap ��ü ����
		main.offMainIntro();// ���� ���۵Ǹ� ������ Ʋ���� ��Ʈ�� ���ֱ�
		game_number = 1;// ��Ʈ�� ��ȣ
		main.add(bottleCap); // �����гο� ��Ʋĸ �г� �߰�
		main.addMainPanel(); // �����гο� ������ �ִ� ��, ��ư�� �߰�
		main.revalidate(); // ���Ӱ� ����
		main.repaint();
	}
	
	
	public void createCatchCatch() {
		main.removeAll(); // ���� �����гο� �ִ� ��ü�� ��� �����
		Catch = new CatchCatchPanel(client, this, main); // catchcatch ��ü �����
		main.offMainIntro();// ���� ���۵Ǹ� ������ Ʋ���� ��Ʈ�� ���ֱ�
		game_number = 2;// ��Ʈ�� ��ȣ
		main.add(Catch); // �����гο� ĳġĳġ �г� �߰�
		main.addMainPanel(); // �����гο� ������ �ִ� ��, ��ư�� �߰�
		main.revalidate(); // ���Ӱ� ����
		main.repaint();
	}
	
	public void createSingContest() {
		main.removeAll(); // ���� �����гο� �ִ� ��ü�� ��� �����
		singer = new Singer(client, this, main); //�����뷡�ڶ� ���� �г� ��ü ����
		main.offMainIntro();// ���� ���۵Ǹ� ������ Ʋ���� ��Ʈ�� ���ֱ�
		game_number = 3;// ��Ʈ�� ��ȣ
		main.add(singer);
		main.addMainPanel(); // �����гο� ������ �ִ� ��, ��ư�� �߰�
		main.revalidate(); // ���Ӱ� ����
		main.repaint();
	}
	
	public void createHunmin() {
		main.removeAll(); // ���� �����гο� �ִ� ��ü�� ��� �����
		hunmin = new HunMinGame(client, this, main); //�ƹ����� ���� �г� ��ü ����
		main.offMainIntro();// ���� ���۵Ǹ� ������ Ʋ���� ��Ʈ�� ���ֱ�
		game_number = 4;// ��Ʈ�� ��ȣ
		main.add(hunmin); // �����гο� �ƹ� �г� �߰�
		main.addMainPanel(); // �����гο� ������ �ִ� ��, ��ư�� �߰�
		main.revalidate(); // ���Ӱ� ����
		main.repaint();
	}
	
	public void createCombination() {
		main.removeAll(); // ���� �����гο� �ִ� ��ü�� ��� �����
		game = new Game(client, this, main); //���� ���� �г� ��ü ����
		main.offMainIntro(); // ���� ���۵Ǹ� ������ Ʋ���� ��Ʈ�� ���ֱ�
		game_number = 5; // ��Ʈ�� ��ȣ
		main.add(game); // �����гο� ���� �г� �߰�
		main.addMainPanel(); // �����гο� ������ �ִ� ��, ��ư�� �߰�
		main.revalidate(); // ���Ӱ� ����
		main.repaint();
	}
	
	public void createSadari() {
		main.removeAll(); // ���� �����гο� �ִ� ��ü�� ��� �����
		sadari = new Sadari(); //��ٸ� ���� �г� ��ü ����
		main.add(sadari); // �����гο� ��ٸ� �г� �߰�
		main.addMainPanel(); // �����гο� ������ �ִ� ��, ��ư�� �߰�
		main.revalidate(); // ���Ӱ� ����
		main.repaint();
	}
	
	public int getPeopleNum(){
		return people;

	} 
	
	// �� ���Ӻ� �� Ŭ�� �� �ش� ���� ������ ���� ������
	private class startListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			// ���콺�� Ŭ���� ���� �ش� ���� ����
			Object obj = e.getSource();
			
			if (obj == playBottleCap) {
				createBottleCap(); // ���Ѳ� ���� ���ϱ� ���� ������ ���� �Լ� ȣ��
			} else if (obj == playCatch) {
				createCatchCatch(); // ĳġĳġ ���� ������ ���� �Լ� ȣ��
			} else if (obj == playSingContest) {
				createSingContest(); // �����뷡�ڶ� ���� ������ ���� �Լ� ȣ��
			} else if (obj == playHunmin) {
				createHunmin(); // �ƹ����� ���� ������ ���� �Լ� ȣ��
			} else if (obj == playCombination) {
				createCombination(); // ���� ���� ������ ���� �Լ� ȣ��
			} else if (obj == playRandom) {
				
				// �������� �ε��� ���� �� �ش� ���ڿ� ���� ���� ������ ���� �Լ� ȣ��
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
			
			// ���콺�� �󺧿� ���� ��� ȣ�� ȿ�� ���� (���� 50%)
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
			
			// ���콺�� ���� ��� ��� ȣ��ȿ�� ����
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
	public void offIntro() { // ������ ���� �� ������ ��Ʈ�θ� ���ֱ� ����
		if(intro!=null) {
			intro.Off();
		}
		main.offMainIntro(); // ������ �뷡�� Ȯ���ϰ� ���ֱ� ����
	}
	
	public int getgameNum() { // ������ �����ϸ� ���ӿ� �ش��ϴ� ��Ʈ�θ� ��� ���� �Լ�
		return game_number;
	}
	
	public void setgameNumZero() {
		game_number = 0; // ������ �����ų� ����ȭ������ �Ѿ �� ��Ʈ�� ��ȣ 0 ���� �Ѱ��ֱ� ����
	}
	public int getIntroNumber() { // ������ �����ϸ� ���ӿ� �ش��ϴ� ��Ʈ�θ� ��� ���� �Լ� // �Լ� Ȯ�� �Ǽ��� ���� ������� �����
		return game_number;
	}
	
	public void onOff(int onOff) { // ������ ���� �� ������ ��Ʈ�θ� ���ֱ� ����  // �Լ� Ȯ�� �Ǽ��� ���� ������� �����
		if(intro!=null) {
			intro.Off();
		}
	}

}

