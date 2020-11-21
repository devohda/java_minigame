package tool;

import java.awt.*;

import javax.swing.JLabel;

public class LabelThread extends JLabel implements Runnable {
	private int _start, _finish;
	private Thread myThread;
	private int _delayTime;
	public LabelThread(){
		_start = _finish = 0;
		myThread = null;
		_delayTime = 1000;
	}
	
	public LabelThread(int start){
		_start = start;
		_finish = 0;
		myThread = null;
		_delayTime = 1000;
	}
	
	public LabelThread(String label){
		super(label); ///////////////////////////////
		_start = _finish = 0;
		_delayTime = 1000;
		myThread = null;
	}
	public LabelThread(String label, int start){
		super(label);
		_start = start;
		_finish = 0;
		_delayTime = 1000;
		myThread = null;
	}
	public int getStart() { return _start;}
	public int getFinish() { return _finish;}
	public int getDelayTime() { return _delayTime;}
	public Thread getThread() { return myThread;}
	
	public void setStart(int start) {_start = start;}
	public void setFinish(int finish) { _finish = finish;}
	public void setNumber(int start, int finish) { _start = start; _finish = finish;}
	public void setDelayTime(int time) {_delayTime = time; }
	
	public void start() {
		if (myThread == null) { myThread = new Thread(this);} ////////////////////
		myThread.start(); /// runÀ¸·Î °¨
	}
	
	@Override
	public void run() {
		for( int i=_start; i>=_finish;i--) {
			setText(Integer.toString(i));
			try {
				myThread.sleep(_delayTime);
			}catch(Exception e) {}
		}
		
		setForeground(Color.red);
		for( int i=0; i<10;i++) {
			setVisible(false);
			try {
				myThread.sleep(_delayTime-300);
			}catch(Exception e) {}
			setVisible(true);
			try {
				myThread.sleep(_delayTime-300);
			}catch(Exception e) {}
		}
	}
}
