package client.home;

import client.general.ConnectAsync;
import client.general.SettingsActivity;

import com.example.foodnow.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 
 * @author Miguel Suarez
 * @author James Dagres
 * @author Carl Barbee
 * @author Matt Luckam
 * 
 *         This is the first Activity where the client inputs the IP and port.
 * 
 */
public class MainActivity extends Activity
{
    EditText ipAddress;
    EditText portNumber;
    Button connectButton;

    private String stringIP; // TODO: remove
    private String stringPort;

    static String IPandPort;
    ConnectAsync myActivity;
    TextView status;

    // Keeps track of whether the server is available
    private boolean connectedToServer_ = false;

    // Instance to the settings activity
    private SettingsActivity settings_;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_home );

        // settings_ = new SettingsActivity(); // TODO: settings initialization
        // causes an error

        // starts the layout objects
        ipAddress = (EditText) findViewById( R.id.IpAddress );
        portNumber = (EditText) findViewById( R.id.IpPort );
        connectButton = (Button) findViewById( R.id.connectButton );
        status = (TextView) findViewById( R.id.connectionStatus );

        // TODO: Checking for server causes an error
        // Attempt to connect to the server with the saved settings if it
        // fails then display the settings menu to get the port and IP
        // number If it succeeds then continue to display the main menu
        if ( connectServerSettings() )
        {
            // TODO
        }
        else
        {
            // // Start the settings activity
            // FragmentManager mFragmentManager = getFragmentManager();
            // FragmentTransaction mFragmentTransaction = mFragmentManager
            // .beginTransaction();
            //
            // SettingsActivity mPrefsFragment = new SettingsActivity();
            // mFragmentTransaction
            // .replace( android.R.id.content, mPrefsFragment );
            // mFragmentTransaction.commit();

            // We could have condensed the 5 lines into 1 line of code.
            // getFragmentManager().beginTransaction()
            // .replace(android.R.id.content, new PrefsFragment()).commit();
        }

        // listener function gets called
        // buttonPressed();
    }

    /**
     * This function checks to see if IP and Server information has been saved
     * in the settings profile if they are saved then try and connect to the
     * server, if connection is successful then return true. If not then return
     * false
     * 
     * @return the status of the server connection
     */
    private boolean connectServerSettings()
    {
        // if ( settings_.iPandPortValueAreSet() ) // TODO
        // {
        // // Attempt to connect
        // return attemptServerConnection();
        // }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main_home, menu );
        return true;
    }

    /**
     * Sets up and handles the button event
     */
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

                    // // get values from Text edit
                    // myActivity = new ConnectAsync( MainActivity.this );
                    // stringPort = portNumber.getText().toString();
                    // stringIP = ipAddress.getText().toString();
                    //
                    // IPandPort = stringIP + ":" + stringPort;
                    // // start async task
                    // myActivity.execute( IPandPort );

                    attemptServerConnection();
                }
            }
        } );

    }

    /**
     * Attempts to connect to the server, if successful then it returns true,
     * else it returns false
     * 
     * @return the result of the server connection attempt
     */
    private boolean attemptServerConnection()
    {
        try
        {
            // get values from Text edit
            myActivity = new ConnectAsync( MainActivity.this );
            stringPort = portNumber.getText().toString();
            stringIP = ipAddress.getText().toString();

            IPandPort = stringIP + ":" + stringPort;
            // start async task
            myActivity.execute( IPandPort );
            return true;
        }
        catch ( Exception ex )
        {
            System.err.print( "Error connecting to the server: " + ex );
        }
        return false;
    }
}
