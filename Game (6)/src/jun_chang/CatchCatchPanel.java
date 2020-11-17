package jun_chang;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.*;

public class CatchCatchPanel extends JPanel {

	private ImageIcon background, jewel, candy, bomb, skul, boxImg;
	private EtchedBorder eborder;
	private JPanel scorePanel;
	private JTextArea scoreArea;
	private JScrollPane scrollPane;
	private JButton resetButton;
	private JLabel back;
	private Point pt[];
	private Box box[] = new Box[25]; 
	private int peopleNum = 4; // ���� ����
	private Player player[] = new Player[4];
	private String scoreString = " ";
	private int turn = 1;
	private int k;
	
	public CatchCatchPanel() {
		
		pt = new Point[25];
		for(int i=0; i<25; i++) {
			pt[i] = new Point();
		}
		
		//Image
		background = new ImageIcon("images/bg_catch.png"); //����̹���
		
		//ImageIcon
		jewel = new ImageIcon("images/Jewel.jpg");
		candy = new ImageIcon("images/Candy.png");
		bomb = new ImageIcon("images/Bomb.jpg");
		skul = new ImageIcon("images/Skul.png");
				
		boxImg = new ImageIcon("images/Box.png"); //test
		
		//Class �ʱ�ȭ �� ����
		for(int i=0; i<25; i++) {
			box[i] = new Box("", boxImg, SwingConstants.CENTER);
		}
		
		for(int i=0; i<4; i++) {
			player[i] = new Player();
			player[i].setOrder(i+1);
		}
		
		//border
		eborder=new EtchedBorder(EtchedBorder.RAISED);;
			
		//Panel
		setBounds(225, 170, 600, 460);
		setPreferredSize(new Dimension(600, 460));
		setLayout(null);
		
		//Panel+Label
		scorePanel = new JPanel(); // ������ �г�
		scorePanel.setBounds(330, 50, 220, 250);
		scorePanel.setBorder(eborder);
		scorePanel.setLayout(null);
		add(scorePanel);
		
		// JLabel scoreArea = new JLabel(scoreString); // ������ �۾�  ��â
		// scoreArea.setBounds(0, 0, 250, 300);
		// scorePanel.add(scoreArea);
		scoreArea = new JTextArea(scoreString); // ������ �۾� ����
		scrollPane = new JScrollPane(scoreArea);
		scoreArea.setEditable(false); // ������ ���� �Ұ�
		scrollPane.setBounds(0, 0, 220, 250);
		scorePanel.add(scrollPane);

		//Box Display  // <- MakingBoard ��ü
		{
			int x=0;
			for(int i=0; i<5; i++) {  // �ڽ� ��ġ �ϴ� �� ���� �� ũ�� 32���� 50���� ������ 
				for(int j=0; j<5; j++) {
					pt[x].x = 50 + (50*j);
					pt[x].y = 50 + (50*i);
					box[x].setBounds(pt[x].x, pt[x].y, 50, 50);
					box[x].setBorder(eborder);
					
					add(box[x]);
					x++; 
				}	
			}
			init(); // �ڽ� ������ġ 
		}
		
		//Button , ������ ����
		resetButton = new JButton("Reset");
		resetButton.setBounds(50, 360, 70, 50);
		add(resetButton);
		
		// ��� �̹��� �������� ��ġ -> ���� ���ʿ� ��ġ�ϱ� ����
		back = new JLabel("", background, SwingConstants.CENTER);
		back.setBounds(0, 0, 600, 460);
		add(back);		
		
		resetButton.addActionListener(new ResetListener());
		
		for(int i=0; i<25; i++) {
			box[i].addMouseListener(new RewardListener());
		}
	
		
	} // construct
	
	// method
	public void init() {
		Random generator = new Random();
		int point[] = new int[25]; // 0~4 : 2�� , 5~9 : 1�� , 10~14 : 0�� , 15~19 : -1��, 20~24 : -2��
		
		for(int i=0; i<25; i++) {
			point[i] = generator.nextInt(25);
			for(int j=0; j<i; j++) {
				if(point[i] == point[j]) {
					i--;
				}
			}
		}
		
		for(int i=0; i<5; i++) {
			box[point[i]].setScore(2);
		}
		for(int i=5; i<10; i++) {
			box[point[i]].setScore(1);
		}
		for(int i=10; i<15; i++) {
			box[point[i]].setScore(0);
		}
		for(int i=15; i<20; i++) {
			box[point[i]].setScore(-1);
		}
		for(int i=20; i<25; i++) {
			box[point[i]].setScore(-2);
		}
	}
	
	public void initGame(JTextArea scoreArea) {
		ImageIcon boximg = new ImageIcon("images/Box.png");
		for(int i=0; i<4; i++) { // �������� 4������ ��
			player[i].setScore(0); // �÷��̾� �� ���� ���� 0���ʱ�ȭ
		}
		for(int i=0; i<25; i++) {
			box[i].setIcon(boximg);
			box[i].setCount(0);
		}
		scoreArea.setText(" ");
		turn = 1;
		init();
		
	}
	
	public void sortPlayer(JTextArea scoreArea) {

		scoreString += "���� ������ " + turn + "�� �÷��̾��Դϴ�.\r\n\n" + " ";
		
		int emp, j, arr[];
		arr = new int[4];
		
		for(int i=0; i<4; i++) {
			arr[i] = player[i].getScore();
		}
		
		// ���� ������ �̿��� sorting 
		for(int i=1; i<4; i++) {
			emp = arr[i];
			j = i-1;
			while(j>=0 && (arr[j]>emp)) {
				arr[j+1] = arr[j];
				j--;
			}
			arr[j+1] = emp;
		}
		
		for(int i=0; i<4; i++) {
			for(j=0; j<4; j++) {
				if(arr[j]==player[i].getScore()) player[i].setOrder(4-j);
			}
		}
		
		for(int i=0; i<4; i++) {
			scoreString += player[i].getOrder() + "�� [Player " + (i+1) + "]" + " : " + player[i].getScore() + "\r\n ";
		}	
				
		scoreArea.setText(scoreString);
		System.out.println(turn);
		System.out.println(scoreString);
		scoreString=" ";
		
	}	
	
	// Listener class
	private class ResetListener implements ActionListener { // ���� �ʱ�ȭ
		public void actionPerformed(ActionEvent e) {
			try {
				initGame(scoreArea);
			} catch(Exception e1) {
				System.out.println("���� �߻�! >> " + e1); 
			}
		}
		
	}
	
	// MouseAdapter ��� �� �ʿ��� �޼��常 �����ؼ� ����� ������
	private class RewardListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			
			Object obj = e.getSource();
			
			if(((Box) obj).getScore() == 2 ) {
				player[turn-1].setScore(player[turn-1].getScore() + 2);
				((Box) obj).setIcon(jewel);
			}
			else if(((Box) obj).getScore() == 1 ) {
				player[turn-1].setScore(player[turn-1].getScore() + 1);
				((Box) obj).setIcon(candy);
			}
			else if(((Box) obj).getScore() == 0) {
				player[turn-1].setScore(player[turn-1].getScore() + 0);
				if (((Box) obj).getCount() == 0) ((Box) obj).setIcon(null);
			}
			else if(((Box) obj).getScore() == -1 ) {
				player[turn-1].setScore(player[turn-1].getScore() + -1);
				((Box) obj).setIcon(bomb);
			}
			else if(((Box) obj).getScore() == -2 ) {
				player[turn-1].setScore(player[turn-1].getScore() + -2);
				((Box) obj).setIcon(skul);
			}
			sortPlayer(scoreArea);
			
			
			if (((Box) obj).getCount() == 0) turn++;
			
			if(turn==5) turn=1;
			
			((Box) obj).setScore(0);
			((Box) obj).setCount(1);
			
		}
		
	}
	
}

