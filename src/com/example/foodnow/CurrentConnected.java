package com.example.foodnow;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author Miguel S 
 * Confirmation of the order activity before posting to the 
 * server
 */
public class CurrentConnected extends Activity
{

	Intent in;
	Bundle b;
	String thisOption;
	String thisName;
	TextView orderAndName;
	Button areYouSure;
	ConnectAsyncCurrentConnected postOrder;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_current_connected );
		orderAndName = (TextView) findViewById( R.id.orderOutput );
		areYouSure = (Button) findViewById( R.id.confirmButton );
		// getting information from the previous activity (name and order)
		in = getIntent();
		b = in.getExtras();

		if ( b != null )
		{
			thisOption = b.get( "order" ).toString();
			thisName = b.get( "name" ).toString();
			//updates the Text view
			updateGUI( thisOption + " from " + thisName );

		}
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.current_connected, menu );
		return true;
	}

	public void updateGUI( String thisOption1 )
	{

		final String sendIt = thisOption1;
		orderAndName.setText( thisOption1 );

		areYouSure.setOnClickListener( new View.OnClickListener()
		{

			public void onClick( View view )
			{
				//vibrates 3 seconds once order is submitted
				Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				v.vibrate(300);
				//start async activity to post information to the server
				postOrder =
						new ConnectAsyncCurrentConnected( CurrentConnected.this );
				postOrder.execute( sendIt );

			}
		} );

	}

}
