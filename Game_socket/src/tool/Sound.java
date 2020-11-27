package tool;

import java.io.*;
import javax.sound.sampled.*;

public class Sound { // ���� ���� Ŭ����
	AudioInputStream audioInputStream;
	Clip clip; 

	
	public Sound( String fileName ) { // ������, ���ϰ�ο� �̸��� �������ش�
		try {
	        audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile()); // ������ �о� ���δ�
	        clip = AudioSystem.getClip(); // Ŭ���� ����� �� �ְ� �������ش�.
	        clip.open(audioInputStream);
	    } catch(Exception ex) { // ������ ���� �� exception handle
	        System.out.println("Error with playing sound."); // ������ ���ٴ� ���� �˸���.
	        ex.printStackTrace();
	    }
	}
	
	
	public void On() { // ������ ���ѹݺ����� ����ϴ� �Լ�
        clip.start();
        clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	public void Off() { // ���� ���� �Լ�
		clip.stop();
	}
	public void On_1() {// ���� �ѹ��� ����ϴ� �Լ�
		clip.start();
	}
}