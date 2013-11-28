package client.home;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import client.general.ConnectAsync;
import client.general.SettingsPreferenceActivity;
import client.menu.MenuTab;
import client.orders.OrderTab;

import com.example.foodnow.R;

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
@SuppressWarnings( { "unused", "deprecation" } )
public class MainActivity extends TabActivity
{
    EditText ipAddress;
    EditText portNumber;
    Button connectButton;

    private String stringIP; // TODO: remove
    private String stringPort;

    static String IPandPort;
    ConnectAsync myActivity;
    TextView status;

    /**
     * settings menu Intent
     */
    Intent settingsIntention;

    // Keeps track of whether the server is available
    private boolean connectedToServer_ = false;

    // Instance to the settings activity
    private SettingsPreferenceActivity settings_;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_home );

        // //////////////////////////////////////////
        // WORKS BUT NO CHECK FOR SERVER NOT CONNECTED
        // ///////////////////////////////////////////
        createTabs();

        settings_ = new SettingsPreferenceActivity();
        settingsIntention =
                new Intent( MainActivity.this, SettingsPreferenceActivity.class );

        // //////////////////////////////////////////////
        // POSSIBLE IMPLEMENTATION OF CHECKING FOR SERVER CONNECTION
        // //////////////////////////////////////////////
        // TODO can not get client to fail if server is not up possibly scrap
        // this
        /*
         * if ( !connectServerSettings() ) { alertUserServerIsNotConnected();
         * 
         * MainActivity.this.startActivity( settingsIntention ); } else {
         * connectedToServer_ = true;
         * 
         * // Final destination for this item createTabs(); }
         */

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
        if ( attemptServerConnection() ) // TODO: test
        {
            // Attempt to connect
            return true;
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.settings, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        switch ( item.getItemId() )
        {
        case R.id.action_settings:
            MainActivity.this.startActivity( settingsIntention );
            break;
        }

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

            IPandPort = "54.201.86.1032:8080";
            myActivity.execute( IPandPort );

            if ( HomeTab.serverStatus.startsWith( "Hello from the Server," ) )
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch ( Exception ex )
        {
            System.err.print( "Error connecting to the server: " + ex );
        }

        return false;
    }

    private void createTabs()
    {
        TabHost tabHost = getTabHost();

        // Home tab
        Intent intentHome = new Intent().setClass( this, HomeTab.class );
        TabSpec tabSpecHome =
                tabHost.newTabSpec( "Home" ).setIndicator( "Home" )
                        .setContent( intentHome );

        // Menu tab
        Intent intentMenu = new Intent().setClass( this, MenuTab.class );
        TabSpec tabSpecMenu =
                tabHost.newTabSpec( "Menu" ).setIndicator( "Menu" )
                        .setContent( intentMenu );

        // Order tab
        Intent intentOrder = new Intent().setClass( this, OrderTab.class );
        TabSpec tabSpecOrder =
                tabHost.newTabSpec( "Order" ).setIndicator( "Order" )
                        .setContent( intentOrder );

        // add all tabs
        tabHost.addTab( tabSpecMenu );
        tabHost.addTab( tabSpecHome );
        tabHost.addTab( tabSpecOrder );

        // set HOme tab as default (zero based)
        tabHost.setCurrentTab( 2 );
        tabHost.setCurrentTab( 1 );
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.ActivityGroup#onResume()
     * 
     * Overrides the resume in order to raise a toast if it's not connected to
     * the server
     */
    @Override
    protected void onResume()
    {
        super.onResume();

        if ( !connectServerSettings() )
        {
            alertUserServerIsNotConnected();
        }
    }

    /**
     * Raises a toast informing the user that the application is not connected
     * successfully to the server.
     */
    private void alertUserServerIsNotConnected()
    {
        Toast.makeText( getApplicationContext(),
                "Not connected to server, check the settings.",
                Toast.LENGTH_LONG ).show();
    }
}