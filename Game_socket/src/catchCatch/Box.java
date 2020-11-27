package catchCatch;

import javax.swing.*;
import java.awt.*;

public class Box extends JLabel {

	private int score;
	private int order;
	private int count;		
	private int index;
	
	public Box(String s, Icon icon, int horizontalAlignment) {		
		setIcon(icon);
		setHorizontalAlignment(horizontalAlignment);
		count = 0;
	}
	
	// get,set
	public int getScore() {	return score; }
	public int getOrder() {	return order; }
	public int getCount() { return count; } 
	public int getIndex() { return index; } 
	public void setScore(int scr) { score = scr; }
	public void setOrder(int ord) { order = ord; }
	public void setCount(int cnt) { count = cnt; }
	public void setIndex(int idx) { index = idx; }
	
}




