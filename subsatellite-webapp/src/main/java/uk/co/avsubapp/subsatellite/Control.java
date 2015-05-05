package uk.co.avsubapp.subsatellite;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;

import java.util.ArrayList;
import java.util.Arrays;

import uk.co.avsubapp.subsatellite.audio.PlaylistController;
import uk.co.avsubapp.subsatellite.audio.SubsonicStreamingURL;
import uk.co.avsubapp.subsatellite.response.Playlist;
import uk.co.avsubapp.subsatellite.response.Response;
import uk.co.avsubapp.subsatellite.response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/rest/satelliteControl.view")
public class Control {

	private static final Logger LOG = LoggerFactory.getLogger(Control.class);

	public static final String VERSION = "0.1b";
	public static final String API_VERSION = "0.2";

	private PlaylistController player = PlaylistController.getInstance();

	@GET
	@Path("/status")
	@Produces("application/xml")
	public String status() {
		return new Response().createOKResponse(new Status(this.player)
				.toXMLResponse());
	}

	@GET
	@Produces("application/xml")
	@Path("/set/{ids}")
	public String set(@PathParam("ids") String ids) {
		ArrayList<String> items = new ArrayList<String>(Arrays.asList(ids
				.split(",")));
		this.player.setPlaylist(items);
		return new Response().createOKResponse(new Status(this.player)
				.toXMLResponse());
	}

	@GET
	@Produces("application/xml")
	@Path("/start")
	public String start() {
		this.player.startPlaying();
		return new Response().createOKResponse(new Status(this.player)
				.toXMLResponse());
	}

	@GET
	@Produces("application/xml")
	@Path("/stop")
	public String stop() {
		this.player.pause();
		return new Response().createOKResponse(new Status(this.player)
				.toXMLResponse());
	}

	@GET
	@Produces("application/xml")
	@Path("/resume")
	public String resume() {
		this.player.startPlaying();
		return new Response().createOKResponse(new Status(this.player)
				.toXMLResponse());
	}

	@GET
	@Produces("application/xml")
	@Path("/get")
	public String get() {
		return new Response().createOKResponse(new Playlist(this.player
				.getPlaylist(), this.player).toXMLResponse());
	}

	@GET
	@Produces("application/xml")
	@Path("/skip/{index}/{offset}")
	public String skip(@PathParam("index") int index, @PathParam("offset") int offset) {
		this.player.setCurrentIndex(index);
		return new Response().createOKResponse(new Status(this.player)
				.toXMLResponse());
	}

	@GET
	@Produces("application/xml")
	@Path("/setGain/{gain}")
	public String gain(@PathParam("gain") float gain) {
		this.player.setGain(gain);
		return new Response().createOKResponse(new Status(this.player)
				.toXMLResponse());
	}

	@GET
	@Produces("application/xml")
	@Path("/credentials/{host}/{username}/{password}/{secure}")
	public String credentials(@PathParam("host") String host, @PathParam("username") String username, @PathParam("password") String password, @PathParam("secure") boolean secure ) {
		SubsonicStreamingURL.IS_SECURE=secure;
		SubsonicStreamingURL.HOST=host;
		SubsonicStreamingURL.USERNAME=username;
		SubsonicStreamingURL.PASSWORD=password;

		LOG.debug("New credentials set:");
		LOG.debug("Host: " + SubsonicStreamingURL.HOST);
		LOG.debug("Username: " + SubsonicStreamingURL.USERNAME);
		LOG.debug("Password: " + SubsonicStreamingURL.PASSWORD);

		return new Response().createOKResponse(new Status(this.player)
				.toXMLResponse());
	}

}
