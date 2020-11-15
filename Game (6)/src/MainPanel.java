
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import tool.*;

public class MainPanel extends JPanel {
	
	private JButton bgm; // ������� ��ư
	private JButton intro; // ��Ʈ�� ��ư
	private JButton gameStart; // ���ӽ��� ��ư
	private JButton goToHome;
	private JButton help; // ���� �������� �ҷ����� ��ư
	private ButtonListener buttonL; // �̺�Ʈ ������
	private int bgmOn = 0; // �̺�Ʈ �ڵ鷯

	private Sound music; // ���� Ŭ���� ��ü ����
	private GameSelector game; // ���� ����â �г� ��ü ����
			
	private JButton InsertPeople ; // �Է��ϱ� ��ư ����
	private JTextField PeopleField; // �ο��� �Է� �ʵ� 
	
	private Help helpFrame;
	
	// �̹��� ũ�� ����
	private Image resizeImg;
	private ResizeImg rImg;
	
	private int people = 0;
	private JLabel lbl;
	
	public MainPanel() {
		
		setLayout(null); // ���̾ƿ� ��
		setBackground(Color.gray); // ���� no �߿�
				
		rImg = new ResizeImg("images/backimg.jpg", 1050, 800);
		resizeImg = rImg.getResizeImage();
		
		ImageIcon icon = new ImageIcon("images/backimg.jpg"); // ��� �̹���
		icon = new ImageIcon(resizeImg);
		lbl = new JLabel("", icon, SwingConstants.RIGHT); // ����̹��� �󺧿� ����
		lbl.setBounds(0, 0, 1050, 800); // ����̹��� ��ġ �� ������ ����
		
		gameStart = new JButton("���ӽ���"); // ���� ���� �г� ��ü�� �ҷ����� ��ư
		gameStart.setBounds(420,300,210,80); // ��ư ��ġ �� ������ ����
		gameStart.addActionListener(buttonL);


		rImg = new ResizeImg("images/btn_gamestart2.png", 210, 80);
		resizeImg = rImg.getResizeImage();
		icon = new ImageIcon(resizeImg);
		gameStart.setIcon(icon);
		gameStart.setBorderPainted(false);
		gameStart.setContentAreaFilled(false);
		gameStart.setFocusPainted(false);



		music = new Sound("sounds/1.wav"); // ������ǿ� ���� ���� �����ϸ� ��ü ����
		bgm = new JButton("BGM"); // ������� ��ư
		bgm.setBounds(830,30,80,40); // ��ư ��ġ �� ������ ����
		
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
		goToHome.setBounds(50, 700, 100, 40);
		goToHome.addActionListener(buttonL);
		
		help = new JButton("����");
		help.setBounds(900, 700, 100, 40);
		help.addActionListener(buttonL);
		

		intro = new JButton("INTRO"); // ���� ��Ʈ�� ��ư ����
		intro.setBounds(920,30,80,40); // ��ư ��ġ �� ������ ����
		
		addMainPanel();
		
	}
	
	public void addMainPanel() {
		add(help);
		add(bgm); 
		add(intro); 
		add(gameStart);
		add(PeopleField);
		add(InsertPeople); 
		add(goToHome); 
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
				}
				else {
					music.Off();
					bgmOn=0;
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
		}
	}
}
