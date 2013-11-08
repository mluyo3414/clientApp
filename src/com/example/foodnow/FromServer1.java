package com.example.foodnow;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodnow.R;
import com.example.foodnow.UserInput;

/**
 * 
 * @author Miguel Suarez
 * @author James Dagres
 * 
 *         This activity displays the response from the server to the client.
 */
public class FromServer1 extends Activity
{

    TextView updateInfo;
    Intent in;
    Bundle b;
    String infoFromServer;
    Button userInput;

    @SuppressLint( "NewApi" )
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_from_server1 );
        updateInfo = (TextView) findViewById( R.id.receivedData );
        userInput = (Button) findViewById( R.id.userInfo );
        userInput.setEnabled( false );

        // getting information from previous activity
        in = getIntent();
        b = in.getExtras();

        if ( !b.isEmpty() )
        {
            // using "Data" as the key
            infoFromServer = b.get( "Data" ).toString();
            // if server returned data, update GUI with information update it
            // else display that server is not available
            if ( !infoFromServer.isEmpty() )
                updateGUI( infoFromServer );

            else
                updateGIUnoResponse();

        }

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.from_server1, menu );
        return true;
    }

    /**
     * 
     * @param dataFromServer
     *            Updates the Textview with the new information
     */
    public void updateGUI( String dataFromServer )
    {
        // enable button so we can order
        userInput.setEnabled( true );
        // start new intent if we are connected to the server
        buttonPressed();
        // update information from the server
        updateInfo.setText( dataFromServer );

    }

    /**
     * Waits for the user to press the button so the order can be started by
     * going into the next activity
     */
    public void buttonPressed()
    {
        userInput.setOnClickListener( new View.OnClickListener()
        {

            public void onClick( View view )
            {
                Intent in = new Intent( FromServer1.this, UserInput.class );
                startActivity( in );
            }
        } );
    }

    /**
     * If server is not up
     */
    public void updateGIUnoResponse()
    {
        updateInfo.setText( "Server is not up :(" );
    }

}
