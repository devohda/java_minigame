package hunmingame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import frame_panel.GameSelector;
import frame_panel.MainPanel;
import tool.LabelThread;
import tool.ResizeImg;

public class HunMinGame extends JPanel {
	private String hangul, added; // ��~�� ���� ����, 2���� ���� ����
	private int index; // �迭 �ε���
	private char one, two; // �������� ���� ����
	private JLabel word, board; // �ʼ�, Ÿ�̸� ���� ��
	private ImageIcon icon; // ������
    private ResizeImg rImg;// ���������̹��� Ŭ���� 
	private Image resizeimg; // ������ �� ���� �̹���
	private JButton reset, back; // �����, �ڷΰ��� ��ư
	private Listener Listen; // ������
	private LabelThread time;;// Ÿ�̸�
	private MainPanel main; // �����г� Ŭ����
    private GameSelector game; // ���Ӽ����� ����
    
	public HunMinGame(MainPanel m, GameSelector g) { // �����г�, ���� ������ �޾ƿ��鼭 ����
		
		main = m; 
		game = g;
		
		setBounds(50, 100, 950, 550);// �ƹ����� �г� ��ġ �� ������ ����
		this.setLayout(null); // ���̾ƿ� ���� ��
		
		Listen = new Listener(); // ������ ����
		
		ResizeImg bImg = new ResizeImg("images/board.jpg",950,550);// ����̹��� ������ ��ü ����
		resizeimg = bImg.getResizeImage(); // �������� �̹��� ��ȯ
		icon = new ImageIcon(resizeimg); // �̹��� ������ȭ
		board = new JLabel("",icon,SwingConstants.CENTER); // �������� ������ �󺧷� ���ȭ�� ����
		board.setBounds(0, 0, 950, 550); // ��ġ �� ������ ����
		
		
		hangul = "����������������������������"; // ���� ����
		index = (int)(Math.random()*14); // �� ~ ������ �ε��� ������ �̱�
		one = hangul.charAt(index); // ���� �̱�
		index = (int)(Math.random()*14); // �� ~ ������ �ε��� ������ �̱�
		two = hangul.charAt(index); // ���� �̱�
		added = String.valueOf(one); // ���� ���� �ֱ�
		added = added + two; // ���� �ΰ� ���ϱ�
		System.out.println(added); 
		
		word = new JLabel(added); // ������ �ʼ� �󺧿� �ֱ�
		word.setBounds(250, 150, 500, 300); // ��ġ �� ������ ����
		word.setFont(new Font("MD��ü", Font.BOLD, 200)); // ��Ʈ ����
		word.setForeground(Color.white); // ���� �� ����
		word.setHorizontalAlignment(SwingConstants.CENTER); // ��ġ ����
		word.setVerticalAlignment(SwingConstants.CENTER); // ��ġ ����
		board.add(word); // �� �����ֱ�
		
		time = new LabelThread(10); // Ÿ�̸� 10�ʺ��� ����
		time.setBounds(400, 50, 500, 200); // ��ġ �� ������ ����
		time.setFont(new Font("MD��ü", Font.BOLD, 150)); // ��Ʈ ����
		board.add(time); // Ÿ�̸� �����ֱ�
		time.start(); // Ÿ�̸� ����
		
		back = new JButton(""); // �ڷΰ��� ��ư
		back.setBounds(50, 450, 100, 50); // ��ġ �� ������ ����
		back.addActionListener(Listen); // ������ �����ֱ�
		board.add(back); // ��ư ȭ�鿡 ���ϱ�
	
		reset = new JButton(""); // ����� ��ư
		reset.setBounds(800, 50, 100, 50); // ��ġ �� ������ ����
		reset.addActionListener(Listen);// ������ �����ֱ�
		board.add(reset);// ��ư ȭ�鿡 ���ϱ�
		
        rImg = new ResizeImg("images/back.png", 50, 50); // ��ư �̹��� ������ �������ֱ�
        resizeimg = rImg.getResizeImage();
        icon = new ImageIcon(resizeimg);
        back.setIcon(icon);// ��ư�� ������ �������� �̹��� ����
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
		
        rImg = new ResizeImg("images/rotate.png", 50, 50);  // ��ư �̹��� ������ �������ֱ�
        resizeimg = rImg.getResizeImage();
        icon = new ImageIcon(resizeimg);
        reset.setIcon(icon);
        reset.setBorderPainted(false);// ��ư�� ������ �������� �̹��� ����
        reset.setContentAreaFilled(false);
        reset.setFocusPainted(false);
        
		this.add(board);// ���ȭ�� �гο� �����ֱ�
	}
	public class Listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(obj == reset) { // ����� ��ư ������
				index = (int)(Math.random()*14); // �ε��� �ٽ� �̾��ֱ�
				one = hangul.charAt(index); // ù ����
				index = (int)(Math.random()*14); // �ε��� �ٽ� �̾��ֱ�
				two = hangul.charAt(index);// �ι�° ����
				added = String.valueOf(one);
				added = added + two;
				word.setText(added);// ���� ���� �־��ֱ�
				
				if(time.getThread().isAlive()){
					time.getThread().interrupt(); // Ÿ�̸� �۵� ���̸� ������
				}
				board.remove(time);//Ÿ�̸� ����
				time = new LabelThread(10); // Ÿ�̸� �� ����
				time.setBounds(400, 50, 500, 200); // ��ġ �� ������ ����
				time.setFont(new Font("MD��ü", Font.BOLD, 150)); // ��Ʈ ����
				board.add(time);// Ÿ�̸� �����ֱ�
				revalidate();
				repaint();
				time.start(); // Ÿ�̸� ����
			}
			else if(obj == back) { // �ڷΰ��� ��ư ������
				main.createGameSelector();  // ���Ӽ����ͷ� ���ư�
	        	main.addMainPanel(); // ���� �г��� �������
	        	main.offMainIntro(); // ��Ʈ�� ���ֱ�
	        	game.offIntro(); // ��Ʈ�� ���ֱ�
	        	game.setgameNumZero(); // ��Ʈ�� ���� ��ȣ 0���� ����
			}
		}
	}

}
