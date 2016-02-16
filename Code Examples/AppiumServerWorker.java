import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.github.genium_framework.appium.support.server.AppiumServer;
import com.github.genium_framework.server.ServerArguments;
import com.google.gson.JsonObject;

/**
 * This class programmatically starts and stops an Appium server. 
 */
public class AppiumServerWorker {

	private AppiumServer appiumServer;
	private String appiumDir;
	
	/**
	 * Constructor.
	 * 
	 * @param appiumDir	root Appium directory on the local machine
	 */
	public AppiumServerWorker( String appiumDir ) {
		this.appiumDir = appiumDir;
	}
	
	/**
	 * Programmatically starts an Appium server.
	 */
	public void startServer() {
		
		ServerArguments serverArguments = new ServerArguments();
		serverArguments.setArgument("--address", "127.0.0.1");
		//serverArguments.setArgument("--chromedriver-port", 4723);
		serverArguments.setArgument("--port", 4723);
		serverArguments.setArgument("--no-reset", true);
		serverArguments.setArgument("--local-timezone", true);

		appiumServer = new AppiumServer(new File(appiumDir), serverArguments);

		appiumServer.startServer();
		System.out.println("Server started...");
	}
	
	public void getServerResponse(String serverURL) throws ClientProtocolException, IOException {
		
		/*
		HttpGet request = new HttpGet(serverURL + "/sessions");
		@SuppressWarnings("resource")
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		JSONObject jsonObject = (JsonObject) new JsonObject().parse(EntityUtils.toString(entity));
		*/
	}
	
	/**
	 * Stop the Appium server.
	 */
	public void stopServer() {
		appiumServer.stopServer();
	}
	
	/**
	 * Change the root Appium directory
	 * @param dir
	 */
	public void setAppiumDirectory( String dir ) {
		appiumDir = dir;
	}
}