package game.bottlecap;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import frame_panel.GameSelector;
import frame_panel.MainPanel;
import sadari.Sadari;
import socket.ClientGame;
import tool.Customfont;
import tool.ResizeImg;
import tool.RoundedButton;

public class BottleCapPanel extends JPanel {

	private JPanel blank;
	private JLabel blankLab, sojuLabel, capLabel, num, wrongBoard, wrong, wrongAnswerLabel, back;
	private JTextField enterNum;
	private RoundedButton enterNumButton, init;
	private String WrongAnswers= "";
	private int filled = 0;
	private int iWrongAns[] = new int[50];
	private int iRanNum;
	
	// ���Ӽ���ȭ�� 
	private GameSelector gameselector;
	// ��Ģȭ��
	private Sadari sadari;
	
	// ���� ����� ���Ӽ���ȭ������ ���� ���Ǵ� ��ü
	private MainPanel main; 
	// �÷��̾���� ���� �� �ο��� ����
	private int personNum; 
	private JLabel personNumLabel;
	private int n=1; // ����
		
	// ������
	private ImageIcon wrongBoardImg;
	// �̹��� ũ�� ����
	private Image resizeImg;
	private ResizeImg rImg;
	    
	//��Ʈ ����
	private Customfont makeFnt;
	Font fnt;
	Font fnt2;
	
	// ä�ñ���
	private JTextArea chatArea;
	private JScrollPane scrollPane;
	private JTextField chat;
	private String nickName; 
		    
	private String name; 
	private ClientGame cl;
	
	public BottleCapPanel(ClientGame c, GameSelector gs, MainPanel m) {
		
		gameselector = gs; // ���Ӽ��� Ŭ������ ��ü ���� �ޱ�
		main = m; // �����г��� ��ü ���� �ޱ�
		
		cl = c; // Ŭ���̾�Ʈ ��ü ���� �ޱ�
		
		iRanNum = 7; // 7�� �ʱ�ȭ reset ��ư Ŭ�� �� ��� ����ɿ��� (�Ʒ� �����ʷ� ó��)
		
		setBounds(225, 200, 600, 400);
		setPreferredSize(new Dimension(400, 400));
		setLayout(null);
		
		// �÷��̾��� ������ ��Ÿ���� ��
		personNumLabel = new JLabel("1 ��° ����� �����Դϴ�.");
		personNumLabel.setBounds(230, 140, 150, 20);
		personNumLabel.setForeground(Color.black);
		add(personNumLabel);
		
		//��ȣ������ �ǳ� + ��
		blank = new JPanel();
		blank.setBounds(185, 190, 20, 20);
		blank.setBackground(Color.black);
		blank.setLayout(null);
		add(blank);
		
		blankLab = new JLabel("?", SwingConstants.CENTER);
		blankLab.setBounds(0, 0, 20, 20);
		blankLab.setForeground(Color.white);
		blank.add(blankLab);

		//Image
		ImageIcon background = new ImageIcon("images/Background.png");//����̹���
		ImageIcon cap = new ImageIcon("images/Cap1.png");//���Ѳ��̹���
		ImageIcon soju = new ImageIcon("images/ms21.png");//�����̹���
		
		//Label
		sojuLabel = new JLabel("", soju, SwingConstants.CENTER	); // ���Ѳ� �̹���
		sojuLabel.setBounds(20, 70, 100, 250);
		add(sojuLabel);
		
		capLabel = new JLabel("", cap, SwingConstants.CENTER	); // ��� �̹���
		capLabel.setBounds(162, 178, 70, 80);
		add(capLabel);
			
		
		num = new JLabel("" + iRanNum);  //���� ���� ǥ��
		num.setBounds(191, 190, 20, 20);
		add(num);
		
		// ���� ǥ�ø� ���� �� 3�� --------------------
		wrong = new JLabel("<����>");
		wrong.setBounds(240, 30, 50, 30);
		wrong.setForeground(Color.white);
		add(wrong);
				
		wrongAnswerLabel = new JLabel(); // ���� ���� ǥ��
		wrongAnswerLabel.setBounds(240, 10, 146, 126);
		wrongAnswerLabel.setForeground(Color.white);
		add(wrongAnswerLabel);
				
		rImg = new ResizeImg("images/wrongBoard.png", 170, 130);
		resizeImg = rImg.getResizeImage();
		wrongBoardImg = new ImageIcon(resizeImg);  //����̹���
				
		wrongBoard = new JLabel("", wrongBoardImg, SwingConstants.CENTER); //���� ĥ���̹���
		wrongBoard.setBounds(220, 10, 170, 130);
		add(wrongBoard);
				
		 /************* ��Ʈ ���� **************/
		makeFnt = new Customfont();
		fnt = makeFnt.getCustomFont("font/���ü.ttf", Font.BOLD, 15);
		fnt2 = makeFnt.getCustomFont("font/������.ttf", Font.PLAIN, 20);
		wrong.setFont(fnt);
		wrongAnswerLabel.setFont(fnt);
		personNumLabel.setFont(fnt);
		        
		// ���� ǥ�ø� ���� �� 3�� ������� ----------------------------------
		
		
		//TextField + Button
		enterNum = new JTextField(); // �����Է��ʵ�
		enterNum.setBounds(165, 360, 30, 32);
		add(enterNum);
		
		enterNumButton = new RoundedButton("OK"); // �����Է� Ȯ�� ��ư
		enterNumButton.setFont(new Font("Arial", Font.BOLD, 10));
		enterNumButton.setBounds(195, 360, 49, 30);
		enterNumButton.setBackground(new Color(200, 160, 16));
		enterNumButton.setFont(fnt2);
		add(enterNumButton);
		
		init = new RoundedButton("Reset"); // ���� �ʱ�ȭ ��ư
		init.setFont(new Font("Arial", Font.BOLD, 10));
		init.setBounds(10, 360, 65, 30);
		init.setBackground(new Color(200, 160, 16));
		init.setFont(fnt2);
		add(init);
		
		//����̹��� �켱���� ����
		back = new JLabel("", background, SwingConstants.CENTER);
		back.setBounds(0, 0, 400, 400);
		back.setBorder(new TitledBorder(new LineBorder(new Color(200, 160, 16),5), ""));
		add(back);
		
		// ä��â ����
		chatArea = new JTextArea(); // ä�ó����� ��Ÿ�� ��ü ����
		scrollPane = new JScrollPane(chatArea); // JScrollPane�� �̿��Ͽ� �� ���뵵 ǥ�ð���
		chatArea.setEditable(false); // ������ ���� �Ұ�
		scrollPane.setBounds(400, 0, 200, 375);
		add(scrollPane);
				
		chat = new JTextField(); //ä���� �Է��� ��ü ����
		chat.setBounds(400, 375, 200, 25);
		add(chat);
		
		init.addActionListener(new InitListener()); // ���� ������� ���� ������
		enterNumButton.addActionListener(new AnwserListener()); // �����Է� �� ����Ǵ� ������
		chat.addActionListener(new sendMessage()); // ä���Է½� ����Ǵ� ������

		cl.setGuiBottle(this); // Ŭ���̾�Ʈ ������ ���� ��Ʋĸ ��ü ����
		name = cl.getNickname(); // ó�� ���� ���� �� �Է� �� �г��� ����
		
	} // construct
	
	// ����ȭ�� �����Ϸ��� �߰��� get/set
	public void setWrongList(int[] list) { iWrongAns = list; }
	
	
	
	public void init() {
		iRanNum = cl.getRandNum(); // ClientGame Ŭ�����κ��� ���� ���� ��������
		enterNum.setText(""); // �Է��ϴ� ĭ ����
		wrongAnswerLabel.setText(""); // ���� ĭ ����
		blank.setVisible(true); // ���� ���� ������ 
		
		// �߰��� �κ� �ʱ�ȭ �� ���� 1��
		n = 1; 
		personNumLabel.setText("" + n + " ��° ����� �����Դϴ�.");
	}
	
	public void initTurn() {
		// ���� ����
		if(n != personNum) {
			n++;
			personNumLabel.setText("" + n + " ��° ����� �����Դϴ�.");
		}
		if( n==personNum) n=0;
	}
	
	public void exit() {
		
		// ���� �� ����Ǵ� JOptionPane
		String[] option = {"�ٽ� ����", "����"};
		int select = JOptionPane.showOptionDialog(this, "������ ����Ǿ����ϴ�", "��������", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, option[0]);

		// �ٽ� �����ϴ� ���
		if (select == 0) {
			cl.sendMessage("[initBottle]"); // ������ �ʱ�ȭ �޼��� ������
			gameselector.offIntro(); // ��Ʈ�β���
        	main.offMainIntro();
		} 
		// �����ϴ� ���
		else if (select == JOptionPane.NO_OPTION) {
			main.createGameSelector(); // ���� ����ȭ������ �̵��ϱ� ���� ��ü ����
        	main.addMainPanel(); // �����г��� �����ϴ� ��, ��ư�� �߰�
        	gameselector.offIntro(); // ��Ʈ�β���
        	main.offMainIntro();
        	gameselector.setgameNumZero();		
		} else {
			System.out.println("CANCLE");
		}
	}
	
	// ���� ��  ����Ǵ� �����
	public void audio1()
	{ 	
		 File bgm;
         AudioInputStream stream;
         AudioFormat format;
         DataLine.Info info;
         
         bgm = new File("sounds/beep.wav"); // ���ÿ��� ���� ������ ������ ��
         
         Clip clip;
         
         try {
                stream = AudioSystem.getAudioInputStream(bgm);
                format = stream.getFormat();
                info = new DataLine.Info(Clip.class, format);
                clip = (Clip)AudioSystem.getLine(info);
                clip.open(stream);
                clip.start();
                
         } catch (Exception e) {
                System.out.println("�Ҹ�����ȵ�" + e);
         }
		
	}
	
	// ä��â�� ���� �Ǵ� ������ �Է��� �޼��� ���
	public void appendMsg(String msg) {
        chatArea.append(msg);
    }     
	
	// ������ ���� ���޹��� ���� �Ǵ� ���� �޼����� ���� ���� ĥ�ǿ� ���
	public void appendWrongAns(String msg) {
		if ( msg.startsWith("[TRUE]")) msg = msg.substring(6);
		else if (msg.startsWith("[FALSE]")) msg = msg.substring(7); 
    	wrongAnswerLabel.setText(msg); // ���� ĥ�ǿ� ���
    }
	
	// ������ ä��â�� �Է��� ������ ������ ����
	private class sendMessage implements ActionListener {
		public void actionPerformed(ActionEvent e) {
	        String msg = name + " : " + chat.getText() + "\n";
	        cl.sendMessage(msg);
	        chat.setText("");
	    }
	}
	
	private class InitListener implements ActionListener { // ���� �ʱ�ȭ
		public void actionPerformed(ActionEvent e) {
			try {
				cl.sendMessage("[initBottle]"); // ������ ��Ʋĸ ���� �ʱ�ȭ �޼��� ����
			} catch (Exception e1) {
	            System.out.println("���� �߻�! >> " + e1); 
			}
			
		}
		
	}
	
	private class AnwserListener implements ActionListener { // ���� �Է� ��, ���� �� ����
		public void actionPerformed(ActionEvent e) {
			try {
				cl.sendMessage("[Answer]" + enterNum.getText()); // ������ �Է��� ������ ���� ���� �Ǵ��� �䱸�ϴ� �޼��� ����
				enterNum.setText("");
			} catch (Exception e1) {
				 System.out.println("���� �߻�! >> " + e1); 
			}
		}
		
	}

	
}

