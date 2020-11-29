package tool;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton { //둥근 버튼 만드는 클래스

    public RoundedButton() {
        super();
        decorate();
    } //constructor

    public RoundedButton(String text) {
        super(text);
        decorate();
    } //constructor

    public RoundedButton(Action action) {
        super(action);
        decorate();
    } //constructor

    public RoundedButton(Icon icon) {
        super(icon);
        decorate();
    } //constructor

    public RoundedButton(String text, Icon icon) {
        super(text, icon);
        decorate();
    } //constructor

    protected void decorate() { //기본 모양 없애고 불투명하게 만들기
        setBorderPainted(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {

        
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //가장자리 부드럽게 만들기

        // 버튼 상태에 따라 배경색 달라지게 하기
        if (getModel().isArmed()) { // 
            graphics.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            graphics.setColor(getBackground().brighter());
        } else {
            graphics.setColor(getBackground());
        }

        int width = getWidth();
        int height = getHeight();

        graphics.fillRoundRect(0, 0, width, height, 10, 10);
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();
        int textX = (width - stringBounds.width) / 2;
        int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent() + 5;
        graphics.setColor(getForeground());
        graphics.setFont(getFont());
        graphics.drawString(getText(), textX, textY);
        graphics.dispose();
        super.paintComponent(g);
    }
}



