package com.example.foodnow;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 
 * @author Miguel S 
 * This is the first Activity where the client inputs the IP and
 * port.
 * 
 * 
 */
public class MainActivity extends Activity
{
	EditText ipAddress;
	EditText portNumber;
	Button connectButton;
	String stringIP;
	String stringPort;
	static String IPandPort;
	ConnectAsync myActivity;
	TextView status;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		// starts the layout objects
		ipAddress = (EditText) findViewById( R.id.IpAddress );
		portNumber = (EditText) findViewById( R.id.IpPort );
		connectButton = (Button) findViewById( R.id.connectButton );
		status = (TextView) findViewById( R.id.connectionStatus );
		//listener function gets called
		buttonPressed();
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.main, menu );
		return true;
	}

	@SuppressLint( "NewApi" )
	@TargetApi( Build.VERSION_CODES.GINGERBREAD )
	public void buttonPressed()
	{

		connectButton.setOnClickListener( new View.OnClickListener()
		{

			public void onClick( View view )
			{
				// if fields are not empty
				if ( (!ipAddress.getText().toString().isEmpty()) &&

				(!portNumber.getText().toString().isEmpty()) )
				{

					// get values from Text edit
					myActivity = new ConnectAsync( MainActivity.this );
					stringPort = portNumber.getText().toString();
					stringIP = ipAddress.getText().toString();
					
					IPandPort = stringIP + ":" + stringPort;
					//start async task
					myActivity.execute( IPandPort );
				}
			}
		} );

	}


}
