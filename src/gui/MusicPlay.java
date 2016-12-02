package gui;
import java.applet.AudioClip;
import java.applet.Applet;
import java.net.URL;
public class MusicPlay {

	 
		public static AudioClip getAC(String filename){ 
		 try{
		   URL url = new URL("file:" + filename); 
		   AudioClip ac = Applet.newAudioClip(url); 
		   return ac;
		  }catch(Exception e){
			  	e.printStackTrace();
			  	return null;
		  	}
	 }
}
