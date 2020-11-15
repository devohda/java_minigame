package combination;

import javax.swing.*;
import java.awt.*;

public class CardPanel extends JPanel {

    private Card card;

    public CardPanel(Card c){
        setPreferredSize(new Dimension(50,50));
        card = c;
    }//constructor
    
    //ī�� �г� GUI �����
    public void paintComponent(Graphics paint){

        super.paintComponent(paint); //�г� �� �ܻ� �����

        // ���� ���� �����ϱ�
        switch (card.getCardColor()){
            case RED : {
                paint.setColor(Color.RED);
                break;
            }
            case YELLOW : {
                paint.setColor(Color.YELLOW);
                break;
            }
            case GREEN : {
                paint.setColor(Color.GREEN);
                break;
            }
        }

        //���� �׸���
        switch (card.getShape()){
            case CIRCLE : { //�� �׸���
                paint.fillOval(10,10,40,40);
                break;
            }
            case SQUARE : { // �簢�� �׸���
                paint.fillRect(10,10,40,40);
                break;
            }
            case TRIANGLE : { //�ﰢ�� �׸���
                int[] x = { 0, 10, 0 };
                int[] y = { 75, 10, 75 };
                paint.fillPolygon( x, y, 3 );
                System.out.println(card);
                break;
            }
        }

        //ī�� ��� ĥ�ϱ�
        switch (card.getBackgroundColor()){
            case WHITE : {
                super.setBackground(Color.WHITE);
                break;
            }
            case GRAY : {
                super.setBackground(Color.GRAY);
                break;
            }
            case BLACK : {
                super.setBackground(Color.BLACK);
                break;
            }
        }

    }//paintComponent()
}
