package gui;

import java.applet.AudioClip;

public enum SE {
	titlebgm("Music/bgm/titlebgm.wav"),
	optionflag("Music/se/book.wav"),
	optionflag_("Music/se/book_.wav"),
	step("Music/se/step.wav"),
	click("Music/se/click.wav"),
	sword("Music/se/sword.wav"),
	axe("Music/se/axe.wav"),
	spear("Music/se/spear.wav"),
	
	ka("Music/se/ka.wav"),
     dong("Music/se/dong.wav");
     private AudioClip ac;
     private SE(String string){
    	 this.ac=MusicPlay.getAC(string);
     }
     public void play(){
    	 this.ac.play();
     }
     public void stop(){
    	 this.ac.stop();
     }
}
