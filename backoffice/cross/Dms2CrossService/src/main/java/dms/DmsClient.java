package dms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DmsClient {

	public static void main(String[] args) {

		try {

			URL url = new URL("https://host/ri/1.0.0/get_dettaglio_impresa/");
			String tokenKey = "Authorization";
			String tokenValue = "Bearer ";

			url = new URL("http://localhost:8080/getallpratiche?anno=\"2000\"&offset=\"1\"&limit=\"20\"");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty(tokenKey, tokenValue);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
