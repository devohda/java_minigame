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
	
	private JButton bgm; // ������� ��ư
	private JButton intro; // ��Ʈ�� ��ư
	private RoundedButton gameStart; // ���ӽ��� ��ư
	private JButton goToHome;
	private JButton help; // ���� �������� �ҷ����� ��ư
	private ButtonListener buttonL; // �̺�Ʈ ������
	private JButton penalty; // ��Ģ Ŭ������ �ҷ����� ��ư
	private int bgmOn = 0, introOn = 0, introNumber = 0; // �̺�Ʈ �ڵ鷯

	private Sound music, intro_0; // ���� Ŭ���� ��ü ����
	private GameSelector game; // ���� ����â �г� ��ü ����
			
	private RoundedButton insertPeople ; // �Է��ϱ� ��ư ����
	private JTextField peopleField; // �ο��� �Է� �ʵ� 
	private JLabel lblstatePeople;
    private int people = 0;
    private JLabel lbl;
	
	private Sadari sadari;
	private Help helpFrame;
	
	// �̹��� ũ�� ����
    private Image resizeImg; // �������� �̹���
    private ResizeImg rImg; // Ŭ���� ��ü
	
	private ImageIcon icon, home, cheers, question, introImg;
    private ImageIcon musicOn;
    private ImageIcon musicOff;
    

    private Customfont makeFnt;
    private Font fnt;
    private Font fnt2;
    
    // hover �̹���
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
		
		setLayout(null); // ���̾ƿ� ��
		setBackground(Color.gray); // ���� no �߿�
				
		makeFnt = new Customfont(); //�ܺ� ��Ʈ �����
        fnt = makeFnt.getCustomFont("font/SSShinb7.ttf", Font.PLAIN, 20); //��Ʈ ����
        fnt2 = makeFnt.getCustomFont("font/������.ttf", Font.PLAIN, 20);
        buttonL = new ButtonListener(); // ������ ��ü ����
        hover = new hoverListener(); // ���콺 ������ ��ü ����
		
        // ��� �̹��� ����
        rImg = new ResizeImg("images/bg_2.jpg", 1050, 800); // ������ ������ �̹��� Ŭ������ �־��ֱ�
        resizeImg = rImg.getResizeImage(); // ������ �̹��� ��ȯ
        icon = new ImageIcon(resizeImg); // �����ܿ� �־��ֱ�
        lbl = new JLabel("", icon, SwingConstants.RIGHT); // ����̹��� �󺧿� ����
        lbl.setBounds(0, 0, 1050, 800); // ����̹��� ��ġ �� ������ ����
		
        // ��ü �����
        gameStart = new RoundedButton("���ӽ���"); // ���� ���� �г� ��ü�� �ҷ����� ��ư
        gameStart.setBounds(420, 340, 210, 70); // ��ư ��ġ �� ������ ����
        gameStart.setBackground(new Color(237, 248, 141));
        gameStart.addActionListener(buttonL); // ��ư�� ������ ����
        gameStart.setFont(fnt2); // ��Ʈ ����
        gameStart.setVisible(false); // ������ �ʰ�
		
        lblstatePeople = new JLabel("�ο����� �Է��ϼ���"); // �ȳ� ��
        lblstatePeople.setBounds(420, 350, 210, 40); // ������ �� ��ġ ����
        lblstatePeople.setHorizontalAlignment(JLabel.CENTER);// ��ġ����
        lblstatePeople.setVerticalAlignment(JLabel.CENTER);// ��ġ����
        lblstatePeople.setFont(fnt2);// ������ �ʰ�
        
        music = new Sound("sounds/bgm.wav"); // ������ǿ� ���� ���� �����ϸ� ��ü ����
        bgm = new JButton("BGM"); // ������� ��ư
        bgm.setBounds(810, 20, 80, 80); // ��ư ��ġ �� ������ ����
        bgm.setFont(fnt);//��Ʈ ����
        bgm.setHorizontalTextPosition(JButton.CENTER);// ��ġ����
        bgm.setVerticalTextPosition(JButton.BOTTOM);// ��ġ����
        bgm.addActionListener(buttonL); // ��ư�� ������ ����
        bgm.addMouseListener(hover); //���콺 ������ �����ֱ�

        intro = new JButton("INTRO"); // ���� ��Ʈ�� ��ư ����
        intro.setBounds(900, 20, 100, 80); // ��ư ��ġ �� ������ ����
        intro.setFont(fnt);// ��Ʈ ����
        intro.setHorizontalTextPosition(JButton.CENTER);// ��ġ����
        intro.setVerticalTextPosition(JButton.BOTTOM);// ��ġ����
        intro.addMouseListener(hover);//���콺 ������ �����ֱ�
        intro.addActionListener(buttonL);//�׼Ǹ����� �����ֱ�


        insertPeople = new RoundedButton("�Է�"); // �ο��� �Է� ��ư ����
        insertPeople.setBounds(530, 420, 100, 40); // ��ġ �� ������ ����
        insertPeople.setFont(fnt2);// ��Ʈ ����
        insertPeople.addActionListener(buttonL); // ��ư�� ������ ����

        peopleField = new JTextField(); // �ο��� �Է� �ʵ� ����
        peopleField.setBounds(420, 420, 100, 40); // ��ġ �� ������ ����
        peopleField.setFont(fnt2);// ��Ʈ ����
        peopleField.addActionListener(buttonL); // ��ư�� ������ ����



        // �ϴ� ��ư ����
        goToHome = new JButton("ó������");// ����ȭ������ ���� ��ư
        goToHome.setBounds(30, 650, 100, 100);// ��ġ �� ������ ����
        goToHome.addActionListener(buttonL); // ��ư�� ������ ����
        goToHome.addMouseListener(hover);//���콺 ������ �����ֱ�
        
        goToHome.setVerticalTextPosition(JButton.TOP);// ��ġ����
        goToHome.setHorizontalTextPosition(JButton.CENTER);// ��ġ����
        goToHome.setFont(fnt);// ��Ʈ ����

        penalty = new JButton("��Ģ����");//��Ģ ��ư
        penalty.setBounds(140, 650, 100, 100);// ��ġ �� ������ ����
        penalty.addActionListener(buttonL); // ��ư�� ������ ����
        penalty.addMouseListener(hover);//���콺 ������ �����ֱ�
        
        penalty.setVerticalTextPosition(JButton.TOP);// ��ġ����
        penalty.setHorizontalTextPosition(JButton.CENTER);// ��ġ����
        penalty.setFont(fnt);

        help = new JButton("����");//���� ��ư
        help.setBounds(900, 650, 100, 100);// ��ġ �� ������ ����
        help.addActionListener(buttonL); // ��ư�� ������ ����
        help.addMouseListener(hover);//���콺 ������ �����ֱ�
        
        help.setVerticalTextPosition(JButton.TOP);// ��ġ����
        help.setHorizontalTextPosition(JButton.CENTER);// ��ġ����
        help.setFont(fnt);// ��Ʈ ����

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
        
        // hover �̹���
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
	
	// ���� �гα����ϴ� ��, ��ư �߰�
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
		revalidate(); // ���Ӱ� ����
		repaint();
	}
	
	public void createGameSelector() {		
		removeAll(); // ���� �����гο� �ִ� ��ü���� ��� ����
		game = new GameSelector(this, client); // ���Ӽ����� ����
		revalidate(); // ���Ӱ� ����
		repaint();
	}
	
	private class ButtonListener implements ActionListener { // �׼� ������
        public void actionPerformed(ActionEvent event) {
            Object object = event.getSource(); // �̺�Ʈ �ҽ� �޾ƿ���
            if (object == bgm) {  // bgm ������
                if (bgmOn == 0) { // bgmOn�� 0 �̸�
                    music.On(); // ���� ����
                    bgmOn = 1; // bgmOn 1��
                    bgm.setIcon(musicOn); // ������ �ٲ��ֱ�
                } else {
                    music.Off(); //���� ���ֱ�
                    bgmOn = 0; // bgmOn 0��
                    bgm.setIcon(musicOff);// ������ �ٲ��ֱ�
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
            	if (game != null) game.setgameNumZero(); // ���� ���Ӽ����� ��ü�� �������� ���� ��� ���� ���� ����
                peopleField.setEnabled(true);
                insertPeople.setEnabled(true); // ����� �Է� ���ϵ��� //---------------------
            } else if (object == insertPeople || object == peopleField) {//---------------------
                String output = peopleField.getText();
                people = Integer.parseInt(output);
                peopleField.setText(""); //�ؽ�Ʈ â ����

                lblstatePeople.setVisible(false);
                gameStart.setVisible(true);

                peopleField.setEnabled(false);
                insertPeople.setEnabled(false); // ����� �Է� ���ϵ���//---------------------
            } else if (object == help) { // help�� ������
                helpFrame = new Help(); // ���� frame�� ����
            } else if (object == penalty) {//��Ģ�� ������
                createSadari(); // ��ٸ� Ŭ���� ����
            }
            else if (object == intro) { // ��Ʈ�� ��ư ������
                if (introOn == 0) {//introOn�� 0�̸�
                    introOn = 1; //introOn 1
                    if(game==null) { // ���Ӽ����� ������ �� �Ǿ�������
                    	intro_0 = new Sound("sounds/main.wav"); // �� �������� ��Ʈ�η�
                    	intro_0.On_1(); // ����
                    }
                    else { // ���Ӽ����Ͱ� ���� �Ǿ�����
                    	introNumber = game.getIntroNumber(); // ���� ���� �޾ƿͼ� 
                    	if(introNumber==0) {
                        	intro_0 = new Sound("sounds/main.wav");// ���ӿ� �´� ��Ʈ�θ� Ʋ���ش�
                        	intro_0.On_1();
                    	}
                    	else if(introNumber==1) {
                        	intro_0 = new Sound("sounds/gamestart.wav");// ���ӿ� �´� ��Ʈ�θ� Ʋ���ش�
                        	intro_0.On_1();
                    	}
                    	else if(introNumber==2) {
                        	intro_0 = new Sound("sounds/gamestart.wav");// ���ӿ� �´� ��Ʈ�θ� Ʋ���ش�
                        	intro_0.On_1();
                    	}
                    	else if(introNumber==3) {
                        	intro_0 = new Sound("sounds/sing.wav");// ���ӿ� �´� ��Ʈ�θ� Ʋ���ش�
                        	intro_0.On_1();
                    	}
                    	else if(introNumber==4) {
                        	intro_0 = new Sound("sounds/hun.wav");// ���ӿ� �´� ��Ʈ�θ� Ʋ���ش�
                        	intro_0.On_1();
                    	}
                    	else if(introNumber==5) {
                        	intro_0 = new Sound("sounds/genius.wav");// ���ӿ� �´� ��Ʈ�θ� Ʋ���ش�
                        	intro_0.On();
                    	}
                    }
                } else {//introOn�� 1�̸�
                    introOn = 0;//introOn 0
                    if(game==null) { // ���� �����Ͱ� ������
                    	intro_0.Off(); // �̹� �����Ǿ��� �뷡 ���ֱ�
                    }
                    else { // ���� �����Ͱ� �����Ǿ�����
                    	intro_0.Off(); // �̹� �����Ǿ��� �뷡�� ���ְų�
                        game.onOff(introOn); // �Լ� ����
                    }
                }
            }
        }
    }
    
    private class hoverListener implements MouseListener {//---------------------

		@Override
		public void mouseEntered(MouseEvent e) {
			
			// ���콺�� �󺧿� ���� ��� ȣ�� ȿ�� ���� (���� 50%)
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
			
			// ���콺�� ���� ��� ��� ȣ��ȿ�� ����
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

    // ���� ���������� �����г��� ��� ����� ��ٸ� �г� ��ü ���� �� �߰� �� ���Ӱ� ����
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

    public int getPeopleNum(){ // �ο��� ��ȯ �Լ�
        return people;
    }
    
    public void offMainIntro() { // ��Ʈ�� ���ִ� �Լ� 
    	if(intro_0!=null) {
        	intro_0.Off();
    	}
    	introOn = 0;
    }
    public void setGameNum(int num) { // �޾ƿ� ���� ��Ʈ�� ���� ������ �����ϴ� �Լ�
    	introNumber = num; 
    }
}