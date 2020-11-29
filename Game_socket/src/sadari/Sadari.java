package sadari;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import tool.*;

@SuppressWarnings("serial")
public class Sadari extends JPanel implements SadariInterFace{
	

	public STATUS mainStatus = STATUS.INIT; //�⺻���� INIT
	
	private SadariPanel mainPanel;
	
	private JPanel inputPanel;  // �Ʒ� �ؽ�Ʈ�ʵ�� ��ư �ִ� �г�
	private JTextField inputNumber; //�����Է� �ؽ�Ʈ�ʵ�
	private JButton startButton; // ���۹�ư�� �ʱ�ȭ��ư
	private JButton resetButton; //���¹�ư
	
	private JLabel borderLab; //�׵θ� �ٹ̱�
	private Color color,colorborder; //�׵θ� �ٹ̱�
	

    private Customfont makeFnt;
    private Font fnt;
    
    
	public Sadari()
	{		
		
        makeFnt = new Customfont(); //�ܺ� ��Ʈ �����
        fnt = makeFnt.getCustomFont("font/SSShinb7.ttf", Font.PLAIN, 20); //��Ʈ ����
        
		color= new Color(255, 255, 224);
		colorborder = new Color(201,200,208);
		
		borderLab = new JLabel("");
		borderLab.setBorder(new TitledBorder(new LineBorder(colorborder,5), ""));
		borderLab.setBounds(0, 0, 450, 500);
		add(borderLab); // �׵θ� �ٹ̱�
		
		setBounds(300, 170, 450, 500);
		setLayout(null);
		setBackground(color); //��������

		inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout()); // ���ʿ��� �����ʹ�ġ

		inputNumber = new JTextField(2);
		inputNumber.setHorizontalAlignment(SwingConstants.CENTER);
		inputNumber.setSize(new Dimension(10,10)); //���� �Է� �ؽ�Ʈ �ʵ� ����


		startButton = new JButton("START");
		startButton.setFont(fnt);
		resetButton = new JButton("RESET");
		resetButton.setFont(fnt);


		inputPanel.setBounds(0, 450, 450, 50);
		inputPanel.add(inputNumber);
		inputPanel.add(startButton);
		inputPanel.add(resetButton);

		add(inputPanel);	 //inputPanel ����

		mainPanel = new SadariPanel(this);
		mainPanel.setLayout(null);

		mainPanel.setBounds(0, 0, 450, 360); //�����г� ��ġ ����

		mainPanel.setBackground(color); //�����г� ��������
		add(mainPanel);   //��ŸƮ ��ư�� ���¹�ư ������ �۲� ����


		startButton.addActionListener(new StartListener());
		resetButton.addActionListener(new ResetListener());

	}
	
	private class StartListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {
			String text = inputNumber.getText().trim();
			inputNumber.setText(" ");
			
			if( text.equals("")) text = "1"; //�����Է� �����̸� 1�� ġȯ
			int input = Integer.valueOf(text);
			if( input < 1 ) input = 1; 
			if( input > 5 ) input = 5;
			mainPanel.setStartPosition(input); //���� ���� ����

			mainStatus=STATUS.DRAWING;
			mainPanel.repaint();	 //��ٸ� ������Ʈ
		} 	 
	} 
	
	private class ResetListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {
			inputNumber.setText(" ");
			
			mainStatus = STATUS.INIT; // ��ٸ� �ʱ�ȭ
			mainPanel.repaint(); //�ٽñ׸���
		} 	
	} 
	
}

