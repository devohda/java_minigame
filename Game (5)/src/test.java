
import java.awt.*;
import javax.swing.*;

public class test {

	public static void main(String[] args) {
		
		// frame
		JFrame frame = new JFrame("ONE SHOT");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1050,800));
		
		// main panel
		MainPanel Main = new MainPanel();
		
		// add panel to frame
		frame.getContentPane().add(Main);
		frame.pack();// 포장
		frame.setVisible(true); // 프레임 보이게 설정 
	}
}