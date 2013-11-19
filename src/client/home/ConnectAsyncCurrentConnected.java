package client.home;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.text.InputFilter.LengthFilter;
import android.widget.Toast;

/**
 * 
 * @author Miguel S 
 * Posts client.orders to the server
 */

public class ConnectAsyncCurrentConnected extends
		AsyncTask<String, Void, String>
{

	String nameAndOrder;
	CurrentConnected currentConnected_;
	static JSONObject jObj = null;
	JSONArray orderJsonArray = null;
	String order = "";
	String name = "";
	MainActivity startActivityAgain;

	public ConnectAsyncCurrentConnected( CurrentConnected currentConnected )
	{
		// activity to call back
		currentConnected_ = currentConnected;
	}

	public String post( String nameAndOrderFromPrev )
	{

		// splits client.orders from name into an client.orders and a name
		String[] nameAndOrderArray = nameAndOrderFromPrev.split( "from" );
		order = nameAndOrderArray[0];
		name = nameAndOrderArray[1];
		// posting to server
		HttpClient client = new DefaultHttpClient();
		HttpPost post =
				new HttpPost( "http://" + MainActivity.IPandPort + "/client" );
		String data = "";
		try
		{
			// three parameters are passed
			List<NameValuePair> nameValuePairs =
					new ArrayList<NameValuePair>( 1 );
			nameValuePairs.add( new BasicNameValuePair( "username", name ) );
			nameValuePairs.add( new BasicNameValuePair( "client.orders", order ) );
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			dateFormatter.setLenient(false);
			Date today = new Date();
			String s = dateFormatter.format(today);
			nameValuePairs.add( new BasicNameValuePair( "location",  s  ) );
			post.setEntity( new UrlEncodedFormEntity( nameValuePairs ) );

			HttpResponse response = client.execute( post );
			BufferedReader rd =
					new BufferedReader( new InputStreamReader( response
							.getEntity().getContent() ) );
			// read from server
			String line = "";
			StringBuffer sb = new StringBuffer( "" );
			String newline = System.getProperty( "line.separator" );
			while ( (line = rd.readLine()) != null )
			{
				sb.append( line + newline );

			}
			rd.close();
			data = sb.toString();
			// get client.orders status
			return (data);
		} catch ( IOException e )
		{
			data = "ERROR FROM SERVER";
			e.printStackTrace();
		}
		return data;

	}



	@Override
	protected String doInBackground( String... params )
	{
		// receives string containing name and client.orders
		String fromServer = "";

		try
		{
			fromServer = post( params[0] );

		} catch ( Exception e )
		{

			e.printStackTrace();
		}

		return fromServer;

	}

	@SuppressLint( "NewApi" )
	@Override
	protected void onPostExecute( String data )
	{
		currentConnected_.runOnUiThread( new Runnable()
		{
			@Override
			public void run()
			{
				// displays toast after successfully posting it to the server
				Toast.makeText( currentConnected_,
						"Your client.orders was sent successfully, " + name,
						Toast.LENGTH_SHORT ).show();
				
			}
		} );
		



		Intent in = new Intent( currentConnected_, MainActivity.class );

		currentConnected_.startActivity( in );
	}

}
