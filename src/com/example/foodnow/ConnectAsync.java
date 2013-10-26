package com.example.foodnow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.drm.DrmStore.Action;
import android.os.AsyncTask;
import android.text.Html;
import android.text.StaticLayout;
import android.util.Log;
import android.view.View;

/**
 * @author Miguel S 
 * This asynchronous activity connects the app with the server and
 *         gets the number of users already in the server
 */
public class ConnectAsync extends AsyncTask<String, Void, String>
{

	protected String ipAndPort;
	// main activity to call back
	MainActivity activity_;

	/**
	 * 
	 * @param nextActivity
	 *            ConnectAsync constructor
	 */
	public ConnectAsync( MainActivity nextActivity )
	{
		// main activity instance to start next activity
		activity_ = nextActivity;
	}

	/**
	 * 
	 * @param IPAndPort
	 * @return response from server
	 * @throws Exception
	 */
	public String getInternetData( String IPAndPort ) throws Exception
	{
		BufferedReader in = null;
		String data = "";
		try
		{
			// setup http client
			HttpClient client = new DefaultHttpClient();
			// process data from
			URI website = new URI( "http://" + IPAndPort );
			// request using get method
			HttpGet request = new HttpGet( website );
			HttpResponse response = client.execute( request );
			// string using buffered reader
			// streamreader bytes into characters
			in =
					new BufferedReader( new InputStreamReader( response
							.getEntity().getContent() ) );
			StringBuffer sb = new StringBuffer( "" );
			String l = "";
			String newline = System.getProperty( "line.separator" );
			while ( (l = in.readLine()) != null )
			{
				sb.append( l + newline );
			}
			in.close();
			data = sb.toString();
			// returns responser from the server
			return parseData( data );

		} finally
		{
			if ( in != null )
			{
				try
				{
					in.close();
					return parseData( data );

				} catch ( Exception e )
				{
					e.printStackTrace();

				}

			}

		}
	}

	@Override
	protected String doInBackground( String... params )
	{

		ipAndPort = params[0];

		// getting info from server
		String data = "";
		try
		{
			data = getInternetData( ipAndPort );

		}
		catch ( Exception e )
		{

			e.printStackTrace();
		}

		return data;

	}

	@Override
	protected void onPostExecute( String fromParseData )
	{

		// calls next activity to display server response and number of users if 
		//there was a connection

		Intent in = new Intent( activity_, FromServer1.class );

		in.putExtra( "Data", fromParseData );

		activity_.startActivity( in );
	

	}

	private String parseData( String data )
	{

		// check if server is up
		
		return data;
	}
}
