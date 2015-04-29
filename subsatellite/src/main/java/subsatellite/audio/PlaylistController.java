package subsatellite.audio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import subsatellite.rest.Control;

// add listener for events to this too
public class PlaylistController implements BasicPlayerListener{

	private static final Logger LOG = LoggerFactory.getLogger(PlaylistController.class);
	
	private static PlaylistController INSTANCE = null;
	
	private BasicPlayer player;
	private long currentOffset=0;
	
	private ArrayList<String> playlist = new ArrayList<String>();
	private int currentIndex=0;

	public static PlaylistController getInstance(){
		if (INSTANCE==null){
			INSTANCE=new PlaylistController();
			showStartupMessage();
		}
		return INSTANCE;
	}
	
	private static void showStartupMessage(){
		LOG.info ("SubSatellite Java");
		LOG.info ("-----------------");
		LOG.info ("Version " + Control.VERSION);
		LOG.info ("API Version " + Control.API_VERSION);		
	}
	
	private PlaylistController() {
		this.player = new BasicPlayer();
		this.player.addBasicPlayerListener(this);
		
	}
	
	public ArrayList<String>getPlaylist(){
		return this.playlist;
	}
	
	public void setPlaylist(ArrayList<String> playlist){
		this.playlist=playlist;
	}
	
	public int getCurrentIndex(){
		return this.currentIndex;
	}
	
	public void setCurrentIndex(int currentIndex){
		this.currentIndex=currentIndex;
	}

	public boolean isPlaying(){
		return this.player.getStatus()==BasicPlayer.PLAYING;
	}
	
	public boolean isPaused(){
		return this.player.getStatus()==BasicPlayer.PAUSED;
	}

	public float getGain(){
		float v = this.player.getGainValue();
		return v;
	}
	
	public int getCurrentOffset(){
		return (int) (this.currentOffset/1000000);
	}
	
	/** Read sampled audio data from the specified URL and play it */
	public void startPlaying() {
		try {
			if (this.isPaused()){
				this.player.resume();
			} else {
				SubsonicStreamingURL url = new SubsonicStreamingURL(this.playlist.get(this.currentIndex));
				this.player.open(url.getURL());
				this.player.play();
				LOG.info("Playing stream - " + url.getURL() );
			}
		} catch (IOException | BasicPlayerException e) {
			e.printStackTrace();
		}
	}

	public void stop(){
		try {
			this.player.stop();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pause(){
		try {
			this.player.pause();
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setGain(float gain){
		try {
			this.player.setGain(gain);
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void moveNext(){
		// If we're not playing the last item
		this.currentOffset=0;
		if (this.currentIndex < (this.playlist.size()-1)){
			currentIndex++;
			this.startPlaying();
		} else {
			this.currentIndex=0;
		}
	}
	// Interface implementations
	@Override
	public void opened(Object stream, Map props) {
	}

	@Override
	public void progress(int bytesRead, long microseconds, byte[] pcmdata, Map props) {
		this.currentOffset=microseconds;
	}

	@Override
	public void setController(BasicController controller) {
	}

	@Override
	public void stateUpdated(BasicPlayerEvent event) {
		if (event.getCode()==BasicPlayerEvent.EOM){
			this.moveNext();
		}
	}
}
