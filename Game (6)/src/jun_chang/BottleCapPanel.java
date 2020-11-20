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
	
	public BottleCapPanel(GameSelector gs, MainPanel m) {
		
		gameselector = gs;
		main = m;
		
		personNum = gs.getPeopleNum(); // ó�� ȭ�鿡�� �Է¹��� �ο��� 
		
		Random generator = new Random();
		iRanNum = generator.nextInt(50)+1; //1~50 �������� ����.. 50�� ���� �ʹ� Ŀ�� �����ϴ°� ��������?
		
		setBounds(325, 200, 400, 400);
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
		enterNum.setBounds(180, 350, 30, 32);
		add(enterNum);
		
		enterNumButton = new RoundedButton("OK"); // �����Է� Ȯ�� ��ư
		enterNumButton.setFont(new Font("Arial", Font.BOLD, 10));
		enterNumButton.setBounds(210, 350, 49, 30);
		enterNumButton.setBackground(new Color(200, 160, 16));
		enterNumButton.setFont(fnt2);
		add(enterNumButton);
		
		init = new RoundedButton("Reset"); // ���� �ʱ�ȭ ��ư
		init.setFont(new Font("Arial", Font.BOLD, 10));
		init.setBounds(40, 350, 65, 30);
		init.setBackground(new Color(200, 160, 16));
		init.setFont(fnt2);
		add(init);
		
		//����̹��� �켱���� ����
		back = new JLabel("", background, SwingConstants.CENTER);
		back.setBounds(0, 0, 400, 400);
		back.setBorder(new TitledBorder(new LineBorder(new Color(200, 160, 16),5), ""));
		add(back);
		
		init.addActionListener(new InitListener());
		enterNumButton.addActionListener(new AnwserListener());

		
	} // construct
	
	// ����ȭ�� �����Ϸ��� �߰��� get/set
	public void setWrongList(int[] list) { iWrongAns = list; }
	
	
	
	public void init(JLabel num, JLabel wrongAnswerLabel, JPanel blank) {
		Random generator = new Random();
		
		int[] ar = new int[50];
		filled=0;
		iWrongAns= ar;
		iRanNum = generator.nextInt(10)+1;
		System.out.println(iRanNum);
		WrongAnswers = "";
		num.setText("" + iRanNum);
		wrongAnswerLabel.setText(WrongAnswers);
		blank.setVisible(true);
		
		// �߰��� �κ� �ʱ�ȭ �� ���� 1��
		
		n = 1; 
		personNumLabel.setText("" + n + " ��° ����� �����Դϴ�.");
	}
	
	
	private  String result(int iRandNum, int iAnswer, String wrongStr, JPanel blank) {

		int tmp,i,j;
		String str ="";
		
		if( iRandNum != iAnswer) {	
			iWrongAns[filled] = iAnswer; 
			filled++;
			
			for(i=filled-1; i>0; i--) { // Ʋ�� ����� ������������ �����ϴ� Bubble Sort
				for(j=0; j<i; j++) {
					if(iWrongAns[j] > iWrongAns[j+1] ) {
						tmp = iWrongAns[j];
						iWrongAns[j] = iWrongAns[j+1];
						iWrongAns[j+1] = tmp;
					}
				}
			}
			for(i=0; i<filled; i++) { // Label�� ǥ���ϱ����� ���� int�迭�� ���ڿ��� ����
				str += String.valueOf(iWrongAns[i]) + " ";
			}
		}
		else {
			str = " ����! ";
			blank.setVisible(false);
			String[] option = {"�ٽ� ����", "����"};
			int select = JOptionPane.showOptionDialog(this, "������ ����Ǿ����ϴ�", "��������", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, option, option[0]);

			if (select == 0) {
				init(num, wrongAnswerLabel, blank);
			} else if (select == JOptionPane.NO_OPTION) {
				main.createGameSelector();
	        	main.addMainPanel();		
			} else {
				System.out.println("CANCLE");
			}
		}
		return str;

	}
	
	private static void audio1()
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

	private class InitListener implements ActionListener { // ���� �ʱ�ȭ
		public void actionPerformed(ActionEvent e) {
			try {
				init(num, wrongAnswerLabel, blank);
				enterNum.setText("");
			} catch (Exception e1) {
	            System.out.println("���� �߻�! >> " + e1); 
			}
			
		}
		
	}
	
	private class AnwserListener implements ActionListener { // ���� �Է� ��, ���� �� ����
		public void actionPerformed(ActionEvent e) {
			try {
				// ���� ����
				if(n != personNum) {
					n++;
					personNumLabel.setText("" + n + " ��° ����� �����Դϴ�.");
				}
				if( n==personNum) n=0;
				
				WrongAnswers = result(iRanNum, Integer.valueOf(enterNum.getText()), WrongAnswers, blank);
				if(WrongAnswers!=" ����! ") {
					wrongAnswerLabel.setText(WrongAnswers);
					audio1();
				}
				enterNum.setText("");

				
			} catch (Exception e1) {
				 System.out.println("���� �߻�! >> " + e1); 
			}
		}
		
	}

	
}

