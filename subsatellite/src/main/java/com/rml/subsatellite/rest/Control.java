package com.rml.subsatellite.rest;

import java.util.ArrayList;
import java.util.Arrays;

import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;

import com.rml.subsatellite.audio.PlaylistController;
import com.rml.subsatellite.audio.SubsonicStreamingURL;
import com.rml.subsatellite.response.Playlist;
import com.rml.subsatellite.response.Response;
import com.rml.subsatellite.response.Status;

@Component
@RestxResource
public class Control {

	public static final String VERSION = "0.1b";
	public static final String API_VERSION = "0.2";
	
	private PlaylistController player = PlaylistController.getInstance();

	@GET("/satelliteControl.view/status")
	@PermitAll
	public String status() {
		return new Response().createOKResponse(new Status(this.player)
				.toXMLResponse());
	}

	@GET("/satelliteControl.view/set/{ids}")
	@PermitAll
	public String set(String ids) {
		ArrayList<String> items = new ArrayList<String>(Arrays.asList(ids
				.split(",")));
		this.player.setPlaylist(items);
		return new Response().createOKResponse(new Status(this.player)
				.toXMLResponse());
	}

	@GET("/satelliteControl.view/start")
	@PermitAll
	public String start() {
		this.player.startPlaying();
		return new Response().createOKResponse(new Status(this.player)
				.toXMLResponse());
	}

	@GET("/satelliteControl.view/stop")
	@PermitAll
	public String stop() {
		this.player.pause();
		return new Response().createOKResponse(new Status(this.player)
				.toXMLResponse());
	}

	@GET("/satelliteControl.view/resume")
	@PermitAll
	public String resume() {
		this.player.startPlaying();
		return new Response().createOKResponse(new Status(this.player)
				.toXMLResponse());
	}

	@GET("/satelliteControl.view/get")
	@PermitAll
	public String get() {
		return new Response().createOKResponse(new Playlist(this.player
				.getPlaylist(), this.player).toXMLResponse());
	}

	@GET("/satelliteControl.view/skip/{index}/{offset}")
	@PermitAll
	public String skip(int index, int offset) {
		this.player.setCurrentIndex(index);
		return new Response().createOKResponse(new Status(this.player)
				.toXMLResponse());
	}

	@GET("/satelliteControl.view/setGain/{gain}")
	@PermitAll
	public String gain(float gain) {
		this.player.setGain(gain);
		return new Response().createOKResponse(new Status(this.player)
				.toXMLResponse());
	}

	@GET("/satelliteControl.view/credentials/{host}/{username}/{password}")
	@PermitAll
	public String credentials(String host, String username, String password) {
		SubsonicStreamingURL.HOST=host;
		SubsonicStreamingURL.USERNAME=username;
		SubsonicStreamingURL.PASSWORD=password;
		return new Response().createOKResponse(new Status(this.player)
				.toXMLResponse());
	}

}
