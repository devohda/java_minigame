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
	
	private JButton bgm; // ������� ��ư
	private JButton intro; // ��Ʈ�� ��ư
	private JButton gameStart; // ���ӽ��� ��ư
	private JButton goToHome; // ùȭ������ ���ư��� ��ư
	private JButton help; // ���� �������� �ҷ����� ��ư
	private ButtonListener buttonL; // �̺�Ʈ ������
	private JButton penalty; // ��Ģ Ŭ������ �ҷ����� ��ư
	private int bgmOn = 0; // �̺�Ʈ �ڵ鷯

	private Sound music; // ���� Ŭ���� ��ü ����
	private GameSelector game; // ���� ����â �г� ��ü ����
			
	private JButton InsertPeople ; // �Է��ϱ� ��ư ����
	private JTextField PeopleField; // �ο��� �Է� �ʵ� 
	
	private Sadari sadari;
	private Help helpFrame;
	
	// �̹��� ũ�� ����
	private Image resizeImg;
	private ResizeImg rImg;

	private int people = 0;
	private JLabel lbl;

	private ImageIcon icon;
	private ImageIcon musicOn;
	private ImageIcon musicOff;

	public MainPanel() {
		
		setLayout(null); // ���̾ƿ� ��
		setBackground(Color.gray); // ���� no �߿�
		

		rImg = new ResizeImg("images/backimg.jpg", 1050, 800);
		resizeImg = rImg.getResizeImage();
		icon = new ImageIcon(resizeImg);
		lbl = new JLabel("", icon, SwingConstants.RIGHT); // ����̹��� �󺧿� ����
		lbl.setBounds(0, 0, 1050, 800); // ����̹��� ��ġ �� ������ ����
		
		gameStart = new JButton("���ӽ���"); // ���� ���� �г� ��ü�� �ҷ����� ��ư
		gameStart.setBounds(420,350,210,40); // ��ư ��ġ �� ������ ����
		gameStart.addActionListener(buttonL);
		
		gameStart.setBounds(420,300,210,80); // ��ư ��ġ �� ������ ����
		gameStart.addActionListener(buttonL);


		

		music = new Sound("sounds/1.wav"); // ������ǿ� ���� ���� �����ϸ� ��ü ����
		bgm = new JButton("BGM"); // ������� ��ư
		bgm.setBounds(830,30,80,50); // ��ư ��ġ �� ������ ����
		bgm.setHorizontalTextPosition(JButton.CENTER);
		bgm.setVerticalTextPosition(JButton.BOTTOM);

		InsertPeople = new JButton("�ο��� �Է��ϱ�"); // �ο��� �Է� ��ư ����
		InsertPeople.setBounds(530, 400, 100, 40); // ��ġ �� ������ ����
		InsertPeople.setFont(new Font("Serif", Font.BOLD, 8));
		PeopleField = new JTextField(); // �ο��� �Է� �ʵ� ����
		PeopleField.setBounds(420, 400, 100, 40); // ��ġ �� ������ ����
		
		
		
		buttonL = new ButtonListener(); // ������ ��ü ����
		bgm.addActionListener(buttonL); // ��ư�� ������ ����
		gameStart.addActionListener(buttonL); // ��ư�� ������ ����
		InsertPeople.addActionListener(buttonL); // ��ư�� ������ ����
		
		goToHome = new JButton("ó������");
		goToHome.setBounds(30, 650, 100, 100);
		goToHome.addActionListener(buttonL);
		goToHome.setVerticalTextPosition(JButton.TOP);
		goToHome.setHorizontalTextPosition(JButton.CENTER);
		
		penalty = new JButton("��Ģ����");
		penalty.setBounds(140, 650, 100, 100);
		penalty.addActionListener(buttonL);
		penalty.setVerticalTextPosition(JButton.TOP);
		penalty.setHorizontalTextPosition(JButton.CENTER);


		help = new JButton("����");
		help.setBounds(900, 650, 100, 100);
		help.addActionListener(buttonL);
		help.setVerticalTextPosition(JButton.TOP);
		help.setHorizontalTextPosition(JButton.CENTER);

		intro = new JButton("INTRO"); // ���� ��Ʈ�� ��ư ����
		intro.setBounds(920,30,80,40); // ��ư ��ġ �� ������ ����
		
		addMainPanel();


		/********************************/
		/******* ��ư�� �̹��� �߰� ********/
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
