package subsatellite.audio;

import java.net.MalformedURLException;
import java.net.URL;

public class SubsonicStreamingURL {

	public static String HOST="http://192.168.1.97:4040";
	public static String USERNAME="subsat";
	public static String PASSWORD="subsat";
	
	private URL url;
	
	public SubsonicStreamingURL(String playlistID) throws MalformedURLException{
		StringBuilder strb = new StringBuilder();
		strb.append(HOST);
		strb.append("/rest/stream.view?v=1.7.0&c=subSatellite&u=");
		strb.append(USERNAME);
		strb.append("&p=");
		strb.append(PASSWORD);
		strb.append("&id=");
		strb.append(playlistID);
		this.url = new URL(strb.toString());
	}
	
	public URL getURL(){
		return this.url;
	}
}
