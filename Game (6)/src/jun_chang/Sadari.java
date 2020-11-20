package jun_chang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import tool.ResizeImg;

@SuppressWarnings("serial")
public class Sadari extends JPanel implements SadariInterFace{
	

	public STATUS mainStatus = STATUS.INIT;
	
	private SadariPanel mainPanel;
	
	private JPanel inputPanel;
	private JTextField inputNumber;
	private JButton startButton;
	private JButton resetButton;
	private JLabel borderLab;
	
	private Color color,colorborder;
	
	
	public Sadari()
	{	
		
		color= new Color(255, 255, 224);
		colorborder = new Color(201,200,208);
		
		borderLab = new JLabel("");
		borderLab.setBorder(new TitledBorder(new LineBorder(colorborder,5), ""));
		borderLab.setBounds(0, 0, 450, 500);
		add(borderLab);
		
		setBounds(300, 170, 450, 500);
		setLayout(null);
		this.setBackground(color);
		
		mainPanel = new SadariPanel(this);
		mainPanel.setBackground(Color.white);
		mainPanel.setBounds(0, 0, 450, 360);

		mainPanel.setBackground(color);
		mainPanel.setLayout(null);
		

		
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());
		
		inputNumber = new JTextField(1);		
		startButton = new JButton("START");
		resetButton = new JButton("RESET");
		
		add(mainPanel);
		
		inputPanel.add(inputNumber);
		inputPanel.add(startButton);
		inputPanel.add(resetButton);
		inputPanel.setBounds(0, 450, 450, 50);
		add(inputPanel);	
		

		startButton.addActionListener(new StartListener());
		resetButton.addActionListener(new ResetListener());
		
		
		

	}
	
	private class StartListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {
			String text = inputNumber.getText().trim();
			inputNumber.setText(" ");
			
			if( text.equals("")) text = "1";
			int input = Integer.valueOf(text);
			if( input < 1 ) input = 1;
			if( input > 5 ) input = 5;
			mainPanel.setStartPosition(input);
			
			mainStatus=STATUS.DRAWING;
			mainPanel.repaint();	
			
		} 	
	} 
	
	private class ResetListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) {
			inputNumber.setText(" ");
			
			
			
			mainStatus = STATUS.INIT;
			mainPanel.repaint();
			
			
		
		} 	
	} 


}

