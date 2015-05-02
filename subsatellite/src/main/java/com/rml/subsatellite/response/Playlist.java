package com.rml.subsatellite.response;

import java.util.ArrayList;

import com.rml.subsatellite.audio.PlaylistController;

public class Playlist {

	private ArrayList<String> playlist;
	private PlaylistController player;

	public Playlist(ArrayList<String> playlist, PlaylistController player){
		this.playlist=playlist;
		this.player=player;
	}
	
	public String toXMLResponse(){
		StringBuilder strb = new StringBuilder();
		
		// Same as status
		strb.append("<satellitePlaylist");
		strb.append(" currentIndex=\"").append(this.player.getCurrentIndex());
		strb.append("\" playing=\"").append(this.player.isPlaying());
		strb.append("\" gain=\"").append(this.player.getGain());
		strb.append("\" position=\"").append(this.player.getCurrentOffset());
		strb.append("\">");
		
		for (String id : playlist) {
			strb.append("<entry id=\"").append(id).append("\"/>");
		}
		
		strb.append("</satellitePlaylist>");
		
		return strb.toString();
	}
}
