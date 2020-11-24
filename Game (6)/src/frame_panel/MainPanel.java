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

    private JButton bgm; // 배경음악 버튼
    private JButton intro; // 인트로 버튼
    private RoundedButton gameStart; // 게임시작 버튼
    private JButton goToHome; // 첫화면으로 돌아가는 버튼
    private JButton help; // 도움말 프레임을 불러내는 버튼
    private ButtonListener buttonL; // 이벤트 리스터
    private JButton penalty; // 벌칙 클래스를 불러내는 버튼
    private int bgmOn = 0, introOn = 0, introNumber = 0; // 이벤트 핸들러

    private Sound music, intro_0, intro_3, intro_4; // 음악 클래스 객체 선언
    private GameSelector game; // 게임 선택창 패널 객체 선언

    private RoundedButton insertPeople; // 입력하기 버튼 선언
    private JTextField peopleField; // 인원수 입력 필드
    private JLabel lblstatePeople;
    private int people = 0;
    private JLabel lbl;

    private Sadari sadari;
    private Help helpFrame;

    // 이미지 크기 조절
    private Image resizeImg;
    private ResizeImg rImg;


    private ImageIcon icon;
    private ImageIcon musicOn;
    private ImageIcon musicOff;

    private Customfont makeFnt;
    private Font fnt;
    private Font fnt2;

    public MainPanel() {

        setLayout(null); // 레이아웃 널
        setBackground(Color.gray); // 배경색 no 중요

        makeFnt = new Customfont(); //외부 폰트 만들기
        fnt = makeFnt.getCustomFont("font/SSShinb7.ttf", Font.PLAIN, 20); //폰트 지정
        fnt2 = makeFnt.getCustomFont("font/지마켓.ttf", Font.PLAIN, 20);
        buttonL = new ButtonListener(); // 리스너 객체 생성


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
        bgm.setBounds(800, 30, 80, 50); // 버튼 위치 및 사이즈 조절
        bgm.setFont(fnt);
        bgm.setHorizontalTextPosition(JButton.CENTER);
        bgm.setVerticalTextPosition(JButton.BOTTOM);
        bgm.addActionListener(buttonL); // 버튼에 리스너 삽입

        intro = new JButton("INTRO"); // 게임 인트로 버튼 생성
        intro.setBounds(900, 30, 80, 40); // 버튼 위치 및 사이즈 조절
        intro.addActionListener(buttonL);

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
        goToHome.setVerticalTextPosition(JButton.TOP);
        goToHome.setHorizontalTextPosition(JButton.CENTER);
        goToHome.setFont(fnt);

        penalty = new JButton("벌칙으로");
        penalty.setBounds(140, 650, 100, 100);
        penalty.addActionListener(buttonL);
        penalty.setVerticalTextPosition(JButton.TOP);
        penalty.setHorizontalTextPosition(JButton.CENTER);
        penalty.setFont(fnt);

        help = new JButton("도움말");
        help.setBounds(900, 650, 100, 100);
        help.addActionListener(buttonL);
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
        
        rImg = new ResizeImg("images/btn_gamestart2.png", 210, 80);
        resizeImg = rImg.getResizeImage();
        icon = new ImageIcon(resizeImg);
        gameStart.setIcon(icon);
        gameStart.setBorderPainted(false);
        gameStart.setContentAreaFilled(false);
        gameStart.setFocusPainted(false);

        rImg = new ResizeImg("images/home.png", 50, 50);
        resizeImg = rImg.getResizeImage();
        icon = new ImageIcon(resizeImg);
        goToHome.setIcon(icon);
        goToHome.setBorderPainted(false);
        goToHome.setContentAreaFilled(false);
        goToHome.setFocusPainted(false);

        rImg = new ResizeImg("images/cheers.png", 50, 50);
        resizeImg = rImg.getResizeImage();
        icon = new ImageIcon(resizeImg);
        penalty.setIcon(icon);
        penalty.setBorderPainted(false);
        penalty.setContentAreaFilled(false);
        penalty.setFocusPainted(false);

        rImg = new ResizeImg("images/question-mark.png", 50, 50);
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
        game = new GameSelector(this);
        revalidate();
        repaint();
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            Object object = event.getSource();
            if (object == bgm) {
                if (bgmOn == 0) {
                    music.On();
                    bgmOn = 1;
                    System.out.println("music on");
                    bgm.setIcon(musicOn);
                } else {
                    music.Off();
                    bgmOn = 0;
                    System.out.println("music off");
                    bgm.setIcon(musicOff);
                }
            } else if (object == gameStart) {
                createGameSelector();
                addMainPanel();

                gameStart.setVisible(false);
                peopleField.setVisible(false);
                insertPeople.setVisible(false);
            } else if (object == goToHome) {
                removeAll();
                addMainPanel();
                revalidate();
                repaint();

                lblstatePeople.setVisible(true);
                gameStart.setVisible(false);
                insertPeople.setVisible(true);
                peopleField.setVisible(true);

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
            else if (object == intro) {
                if (introOn == 0) {
                	if(game!=null) {
                		introNumber = game.getIntroNumber();
                	}
                	System.out.println(introNumber);
                	if(introNumber == 0) {
                		intro_0.On_1();
                	}
                	else if(introNumber == 3) {
                		intro_3.On_1();
                	}
                	else if(introNumber == 4) {
                		intro_4.On_1();
                	}
                    introOn = 1;
                    System.out.println("music on");
                    intro.setIcon(musicOn);
                } else {
                	if(introNumber == 0) {
                		intro_0.Off();
                	}
                	else if(introNumber == 3) {
                		intro_3.Off();
                	}
                	else if(introNumber == 4) {
                		intro_4.Off();
                	}
                    introOn = 0;
                    System.out.println("music off");
                    intro.setIcon(musicOff);
                }
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
        peopleField.setVisible(false);
        insertPeople.setVisible(false);
    }

    public int getPeopleNum(){
        return people;
    }
}
