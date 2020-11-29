package combination;

import javax.swing.*;
import java.awt.*;

public class GameBoardPanel extends JPanel {
    public GameBoardPanel(Card boardInfo[]){

        setBackground(Color.CYAN);
        setLayout(null); //���̾ƿ� ��ġ ������ ����

        int cnt = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                CardPanel cardPanel = new CardPanel(boardInfo[cnt++]);
                cardPanel.setBounds(100*(j) + 35, 100*(i) + 35, 80, 80);
                add(cardPanel);
            }
        }

    }//constructor
    public void paintComponent(Graphics paint){

        super.paintComponent(paint); //�г� �� �ܻ� �����
        super.setBackground(new Color(115, 15, 15));

    }//paintComponent()
}
