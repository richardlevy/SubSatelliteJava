package uk.co.avsubapp.subsatellite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.ProtectionDomain;

import org.apache.commons.io.FileUtils;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.HandlerList;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.thread.QueuedThreadPool;

public class Main {

	private final int port;
	private final String contextPath;
	private final String workPath;

	public static void main(String[] args) throws Exception {
		Main server = new Main();

		if (args.length != 1) {
			server.start();
		} else if ("stop".equals(args[0])) {
			server.stop();
		} else if ("start".equals(args[0])) {
			server.start();
		} else {
			server.usage();
		}
	}

	public Main() {
		try {
			String configFile = System
					.getProperty("config", "jetty.properties");
			System.getProperties().load(new FileInputStream(configFile));
		} catch (Exception ignored) {
		}

		port = Integer.parseInt(System.getProperty("jetty.port", "4141"));
		contextPath = System.getProperty("jetty.contextPath", "/");
		workPath = System.getProperty("jetty.workDir", null);

	}

	private void start() {
		// Start a Jetty server with some sensible(?) defaults
		try {
			Server srv = new Server();
			srv.setStopAtShutdown(true);

			// Allow 1/2 second to complete.
			// Adjust this to fit with your own webapp needs.
			// Remove this if you wish to shut down immediately (i.e. kill <pid>
			// or Ctrl+C).
			srv.setGracefulShutdown(500);

			// Increase thread pool
			QueuedThreadPool threadPool = new QueuedThreadPool();
			threadPool.setMaxThreads(100);
			srv.setThreadPool(threadPool);

			// Ensure using the non-blocking connector (NIO)
			Connector connector = new SelectChannelConnector();
			connector.setPort(port);
			connector.setMaxIdleTime(30000);
			srv.setConnectors(new Connector[] { connector });

			// Get the war-file
			ProtectionDomain protectionDomain = Main.class
					.getProtectionDomain();
			String warFile = protectionDomain.getCodeSource().getLocation()
					.toExternalForm();
			String currentDir = new File(protectionDomain.getCodeSource()
					.getLocation().getPath()).getParent();

			// Add the warFile (this jar)
			WebAppContext context = new WebAppContext(warFile, contextPath);
			context.setServer(srv);
			resetTempDirectory(context, currentDir);

			// Add the handlers
			HandlerList handlers = new HandlerList();
			handlers.addHandler(context);
			handlers.addHandler(new ShutdownHandler(srv, context));
			srv.setHandler(handlers);

			srv.start();
			srv.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void stop() {
		System.out.println(ShutdownHandler.shutdown(port));
	}

	private void usage() {
		System.out
				.println("Usage: java -jar <file.jar> [start|stop|\n\t"
						+ "start    Start the server (default)\n\t"
						+ "stop     Stop the server gracefully\n\t");
		System.exit(-1);
	}

	private void resetTempDirectory(WebAppContext context, String currentDir)
			throws IOException {
		File workDir;
		if (workPath != null) {
			workDir = new File(workPath);
		} else {
			workDir = new File(currentDir, "work");
		}
		FileUtils.deleteDirectory(workDir);
		context.setTempDirectory(workDir);
	}

}
