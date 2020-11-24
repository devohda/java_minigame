package jun_chang;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.*;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import frame_panel.GameSelector;
import frame_panel.MainPanel;
import tool.Customfont;
import tool.ResizeImg;
import tool.RoundedButton;

import clientgame.ClientGame;

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
		
		gameselector = gs;
		main = m;
		
		cl = c;
		
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
		
		// ���� ǥ�� �� 3��		--------------------
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
		        
		// ����..              --------------------
		
		
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
		chatArea = new JTextArea(); // ������ �۾� ����
		scrollPane = new JScrollPane(chatArea);
		chatArea.setEditable(false); // ������ ���� �Ұ�
		scrollPane.setBounds(400, 0, 200, 375);
		add(scrollPane);
				
		chat = new JTextField();
		chat.setBounds(400, 375, 200, 25);
		add(chat);
		
		init.addActionListener(new InitListener());
		enterNumButton.addActionListener(new AnwserListener());
		chat.addActionListener(new sendMessage());

		cl.setGuiBottle(this);
		name = cl.getNickname();
		
	} // construct
	
	// ����ȭ�� �����Ϸ��� �߰��� get/set
	public void setWrongList(int[] list) { iWrongAns = list; }
	
	
	
	public void init() {
		iRanNum = cl.getRandNum();
		enterNum.setText("");
		wrongAnswerLabel.setText("");
		blank.setVisible(true);
		
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
		String[] option = {"�ٽ� ����", "����"};
		int select = JOptionPane.showOptionDialog(this, "������ ����Ǿ����ϴ�", "��������", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, option[0]);

		if (select == 0) {
			cl.sendMessage("[initBottle]");
		} else if (select == JOptionPane.NO_OPTION) {
			main.createGameSelector();
        	main.addMainPanel();		
		} else {
			System.out.println("CANCLE");
		}
	}
	
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
	
	// ��Ʈ��ũ
	public void appendMsg(String msg) {
        chatArea.append(msg);
    }     
	
	public void appendWrongAns(String msg) {
		if ( msg.startsWith("[TRUE OR FALSE]")) msg = msg.substring(15);
    	wrongAnswerLabel.setText(msg);
    }
	
	private class sendMessage implements ActionListener {
		public void actionPerformed(ActionEvent e) {
	        String msg = name + " : " + chat.getText() + "\n";
	        cl.sendMessage(msg);
	        chat.setText("");
	    }
	}
    // ������� ��Ʈ��ũ 
	
	private class InitListener implements ActionListener { // ���� �ʱ�ȭ
		public void actionPerformed(ActionEvent e) {
			try {
				cl.sendMessage("[initBottle]");
			} catch (Exception e1) {
	            System.out.println("���� �߻�! >> " + e1); 
			}
			
		}
		
	}
	
	private class AnwserListener implements ActionListener { // ���� �Է� ��, ���� �� ����
		public void actionPerformed(ActionEvent e) {
			try {
				cl.sendMessage("[Answer]" + enterNum.getText());
				enterNum.setText("");
			} catch (Exception e1) {
				 System.out.println("���� �߻�! >> " + e1); 
			}
		}
		
	}

	
}

