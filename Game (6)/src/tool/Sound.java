package tool;

import java.io.*;
import javax.sound.sampled.*;

public class Sound { // ���� ���� Ŭ����
	AudioInputStream audioInputStream;
	Clip clip; 

	
	public Sound( String fileName ) { // ������
		try {
	        audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile());
	        clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	
	
	public void On() { // ���� ��� �Լ�
        clip.start();
        clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	public void Off() { // ���� ���� �Լ�
		clip.stop();
	}
	public void On_1() {
		clip.start();
	}
}