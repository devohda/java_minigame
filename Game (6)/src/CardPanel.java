

import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {

    private Card card;

    public CardPanel(Card c){
        setPreferredSize(new Dimension(100,100));
        card = c;
    }//constructor
    
    //ī�� �г� GUI �����
    public void paintComponent(Graphics paint){

        super.paintComponent(paint); //�г� �� �ܻ� �����

        // ���� ���� �����ϱ�
        switch (card.getCardColor()){
            case RED : {
                paint.setColor(Color.RED);
            }
            case YELLOW : {
                paint.setColor(Color.YELLOW);
            }
            case GREEN : {
                paint.setColor(Color.GREEN);
            }
        }

        //���� �׸���
        switch (card.getShape()){
            case CIRCLE : { //�� �׸���
                paint.fillOval(15,15,70,70);
            }
            case SQUARE : { // �簢�� �׸���
                paint.fillRect(15,15,70,70);
            }
            case TRIANGLE : { //�ﰢ�� �׸���
                int x[] = { 15, 50, 85 };
                int y[] = { 85, 15, 85 };
                paint.fillPolygon( x, y, 3 );
            }
        }

        //ī�� ��� ĥ�ϱ�
        switch (card.getBackgroundColor()){
            case WHITE : {
                super.setBackground(Color.WHITE);
            }
            case GRAY : {
                super.setBackground(Color.GRAY);
            }
            case BLACK : {
                super.setBackground(Color.BLACK);
            }
        }

    }//paintComponent()
}
