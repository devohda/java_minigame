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
    private RoundedButton gameStart; // ���ӽ��� ��ư
    private JButton goToHome; // ùȭ������ ���ư��� ��ư
    private JButton help; // ���� �������� �ҷ����� ��ư
    private ButtonListener buttonL; // �̺�Ʈ ������
    private JButton penalty; // ��Ģ Ŭ������ �ҷ����� ��ư
    private int bgmOn = 0, introOn = 0, introNumber = 0; // �̺�Ʈ �ڵ鷯

    private Sound music, intro_0, intro_3, intro_4; // ���� Ŭ���� ��ü ����
    private GameSelector game; // ���� ����â �г� ��ü ����

    private RoundedButton insertPeople; // �Է��ϱ� ��ư ����
    private JTextField peopleField; // �ο��� �Է� �ʵ�
    private JLabel lblstatePeople;
    private int people = 0;
    private JLabel lbl;

    private Sadari sadari;
    private Help helpFrame;

    // �̹��� ũ�� ����
    private Image resizeImg;
    private ResizeImg rImg;


    private ImageIcon icon;
    private ImageIcon musicOn;
    private ImageIcon musicOff;

    private Customfont makeFnt;
    private Font fnt;
    private Font fnt2;

    public MainPanel() {

        setLayout(null); // ���̾ƿ� ��
        setBackground(Color.gray); // ���� no �߿�

        makeFnt = new Customfont(); //�ܺ� ��Ʈ �����
        fnt = makeFnt.getCustomFont("font/SSShinb7.ttf", Font.PLAIN, 20); //��Ʈ ����
        fnt2 = makeFnt.getCustomFont("font/������.ttf", Font.PLAIN, 20);
        buttonL = new ButtonListener(); // ������ ��ü ����


        // ��� �̹��� ����
        rImg = new ResizeImg("images/bg_2.jpg", 1050, 800);
        resizeImg = rImg.getResizeImage();
        icon = new ImageIcon(resizeImg);
        lbl = new JLabel("", icon, SwingConstants.RIGHT); // ����̹��� �󺧿� ����
        lbl.setBounds(0, 0, 1050, 800); // ����̹��� ��ġ �� ������ ����


        // ��ü �����
        gameStart = new RoundedButton("���ӽ���"); // ���� ���� �г� ��ü�� �ҷ����� ��ư
        gameStart.setBounds(420, 340, 210, 70); // ��ư ��ġ �� ������ ����
        gameStart.setBackground(new Color(237, 248, 141));
        gameStart.addActionListener(buttonL); // ��ư�� ������ ����
        gameStart.setFont(fnt2);
        gameStart.setVisible(false);

        lblstatePeople = new JLabel("�ο����� �Է��ϼ���");
        lblstatePeople.setBounds(420, 350, 210, 40);
        lblstatePeople.setHorizontalAlignment(JLabel.CENTER);
        lblstatePeople.setVerticalAlignment(JLabel.CENTER);
        lblstatePeople.setFont(fnt2);

        music = new Sound("sounds/1.wav"); // ������ǿ� ���� ���� �����ϸ� ��ü ����
        bgm = new JButton("BGM"); // ������� ��ư
        bgm.setBounds(800, 30, 80, 50); // ��ư ��ġ �� ������ ����
        bgm.setFont(fnt);
        bgm.setHorizontalTextPosition(JButton.CENTER);
        bgm.setVerticalTextPosition(JButton.BOTTOM);
        bgm.addActionListener(buttonL); // ��ư�� ������ ����

        intro = new JButton("INTRO"); // ���� ��Ʈ�� ��ư ����
        intro.setBounds(900, 30, 80, 40); // ��ư ��ġ �� ������ ����
        intro.addActionListener(buttonL);

        insertPeople = new RoundedButton("�Է�"); // �ο��� �Է� ��ư ����
        insertPeople.setBounds(530, 420, 100, 40); // ��ġ �� ������ ����
        insertPeople.setFont(fnt2);
        insertPeople.addActionListener(buttonL); // ��ư�� ������ ����

        peopleField = new JTextField(); // �ο��� �Է� �ʵ� ����
        peopleField.setBounds(420, 420, 100, 40); // ��ġ �� ������ ����
        peopleField.setFont(fnt2);
        peopleField.addActionListener(buttonL);


        // �ϴ� ��ư ����
        goToHome = new JButton("ó������");
        goToHome.setBounds(30, 650, 100, 100);
        goToHome.addActionListener(buttonL);
        goToHome.setVerticalTextPosition(JButton.TOP);
        goToHome.setHorizontalTextPosition(JButton.CENTER);
        goToHome.setFont(fnt);

        penalty = new JButton("��Ģ����");
        penalty.setBounds(140, 650, 100, 100);
        penalty.addActionListener(buttonL);
        penalty.setVerticalTextPosition(JButton.TOP);
        penalty.setHorizontalTextPosition(JButton.CENTER);
        penalty.setFont(fnt);

        help = new JButton("����");
        help.setBounds(900, 650, 100, 100);
        help.addActionListener(buttonL);
        help.setVerticalTextPosition(JButton.TOP);
        help.setHorizontalTextPosition(JButton.CENTER);
        help.setFont(fnt);

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
                insertPeople.setEnabled(true); // ����� �Է� ���ϵ���
            } else if (object == insertPeople || object == peopleField) {
                String output = peopleField.getText();
                people = Integer.parseInt(output);
                peopleField.setText(""); //�ؽ�Ʈ â ����

                lblstatePeople.setVisible(false);
                gameStart.setVisible(true);

                peopleField.setEnabled(false);
                insertPeople.setEnabled(false); // ����� �Է� ���ϵ���
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
