package singer;

import tool.LabelThread;
import tool.ResizeImg;
import tool.Sound;
import tool.LabelThread;
import javax.swing.*;

import frame_panel.GameSelector;
import frame_panel.MainPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Singer extends JPanel {
	private String singerList; // ���� ���
	private String[] pickSinger; // ���� ���� ������
	private JLabel word, boardimg, board; // ���� ���þ�, ��� �̹���, Ÿ�̸� �ֱ� ����
	private ImageIcon icon; // �̹��� ������
	private Image resizeimg; // �̹��� ������ �����뵵
	private int index;// �������� ","�� �������� ������� ����� �ε���
	private JButton reset, back; // ����, �ڷΰ��� ��ư
	private LabelThread time; // Ÿ�̸�
	private ButtonListener Listen; // ������
	private MainPanel main; // �����г�
    private ResizeImg rImg; // �̹��� ������ �����뵵 
    private Image resizeImg;// �̹��� ������ �����뵵
    private GameSelector game; // ���Ӽ�����
	
	public Singer(MainPanel m, GameSelector g) { // �����г�, ���Ӽ����� Ŭ���� �޾ƿ�
		
		main = m;
		game = g;
		
		Listen = new ButtonListener(); // ������ �Ҵ�
		
		setBounds(50, 100, 950, 550); // ���� �г� ��ġ �� ������ ����
		this.setLayout(null); // Layout ���� null
		singerList = "����ũ,�ҳ�ô�,  BTS,Ʈ���̽�,���座��, ������,  EXO,  ����, ������, ������,��θ���, ���Ͽ�,  ����,����ģ��, ������,��𷣵�, ���̵�,Ű��и�,�������ƽ�,  ����, �����, �鿹��,  �¿�,�����̰�, �ܳ���"; // ������ ����
		pickSinger = singerList.split(",");
		
		index = (int)(Math.random()*26); // 25���� ���ڸ� ���� ����� �ε��� �������� ����
		
		ResizeImg bImg = new ResizeImg("images/sing_backimg3.png",950,550); // ������ ������ 950*550���� �� ����
		resizeimg = bImg.getResizeImage(); // �缳���� �̹��� ��ȯ
		icon = new ImageIcon(resizeimg); // �̹��� �����ܿ��� ����� �� �ְ� ����
		boardimg = new JLabel("",icon,SwingConstants.CENTER); // j�󺧿� ������ ����
		boardimg.setBounds(0, 0, 950, 550); // ��ġ �� ������ ����
				
		word = new JLabel(pickSinger[index]); //j�󺧿� ���� ���� ������ ����
		word.setBounds(300, 10, 500, 200);// ������ �� ��ġ ����
		word.setFont(new Font("Ÿ����_����ǿ��� �׵θ�M", Font.BOLD, 100));// ���� ��Ÿ�� ����
		word.setForeground(new Color(0,0,255)); // ���ڻ� �Ķ������� ����
		
		reset = new JButton("");// ����� ��ư
		reset.setBounds(800, 50, 100, 50); // ��ġ �� ������ ����
		reset.addActionListener(Listen); // ������ �߰�
		
		back = new JButton(""); // �ڷΰ��� ��ư
		back.setBounds(50, 450, 100, 50); // ��ġ �� ������ ����
		back.addActionListener(Listen); // ������ �߰�
	
		board = new JLabel(""); //Ÿ�̸� �ֱ� 
		board.setBounds(0, 0, 950, 550); // ��ġ �� ������ ����
		
		time = new LabelThread(10); // Ÿ�̸� 10�ʿ��� ����
		time.setBounds(400, 150, 500, 200); // ��ġ �� ������ ����
		time.setFont(new Font("MD��ü", Font.BOLD, 150)); // Ÿ�̸� ��Ʈ ����
		board.add(time); // Ÿ�̸� add
		time.start(); // Ÿ�̸� ����
		
        rImg = new ResizeImg("images/back.png", 50, 50); // �ڷΰ��� ��ư ������ ������ �̹��� �߰� �� �׵θ� �����ֱ�
        resizeImg = rImg.getResizeImage();
        icon = new ImageIcon(resizeImg);
        back.setIcon(icon);
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
        
        rImg = new ResizeImg("images/rotate.png", 50, 50); // ����� ��ư ������ ������ �̹��� �߰� �� �׵θ� �����ֱ�
        resizeImg = rImg.getResizeImage();
        icon = new ImageIcon(resizeImg);
        reset.setIcon(icon);
        reset.setBorderPainted(false);
        reset.setContentAreaFilled(false);
        reset.setFocusPainted(false);
		
		this.add(back); // �ڷΰ��� add
		this.add(reset); // ����� add
		this.add(board); // Ÿ�̸� �ֱ�� add
		this.add(word); // ���þ� add
		this.add(time); // Ÿ�̸� add
		this.add(boardimg);	// ������ add
	}
	
	public String getList() {
		return singerList; // ���� ���� ��ȯ
	}
	
	public class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(obj == reset) { // ����� ��ư�� ������
				index = (int)(Math.random()*26); // �ε��� �ٽ� �̱�
				word.setText(pickSinger[index]); // �ش� �ε����� ������ ���þ� �缳��
				
				if(time.getThread().isAlive()){
					time.getThread().interrupt(); 
				}
				remove(time); // Ÿ�̸� ����
				time = new LabelThread(10); // Ÿ�̸� �缺
				time.setBounds(400, 150, 500, 200); // ������ �� ��ġ ����
				time.setFont(new Font("MD��ü", Font.BOLD, 150)); // ��Ʈ ����
				add(time); // Ÿ�̸� �����ֱ�
				add(board); // ���� �����ֱ�
				add(boardimg);//������ ���ϱ�
				revalidate();
				repaint();
				time.start();//Ÿ�̸� ����
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
