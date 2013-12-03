package client.home;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
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
 *         Activity that contains the 3 tabs ( HomeTab, MenuTab, and OrderTab )
 *         of the GUI and ConnectAsync. This class builds the 3 tabs and
 *         launches ConnectAsync to determine if the server is running. If the
 *         server is not running an alertbox notifies the user of this and then
 *         closes the app.
 * 
 */
@SuppressWarnings( { "unused", "deprecation" } )
public class MainActivity extends TabActivity
{
    ConnectAsync myActivity;

    public static boolean inputCorrect;
    
    private static boolean initialSetup;

    // ////////////////////////////
    // possibly unneeded

    /**
     * settings menu Intent
     */
    private Intent settingsIntention;
    /**
     * Instance to the settings activity
     */
    private SettingsPreferenceActivity settings_;

    // ////////////////////////////

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_home );

        inputCorrect = false;
        initialSetup = true;
        initializeSettingsActivity();

        // createTabs();
        // ////////////////////////////////
        // possibly unneeded
        // ///////////////////////////////
        // initializeSettingsActivity();

        // isServerOn();
    }

    /**
     * Attempts to connect to the server, if successful then the app is
     * launched, else it an alertbox is alerts the user the server is not active
     * and the application is closed
     * 
     * @return the result of the server connection attempt
     */
    public void isServerOn()
    {
        try
        {
            // get values from Text edit
            myActivity = new ConnectAsync( MainActivity.this );

            String IPandPort = "172.31.172.58:8080"; // 54.201.86.1032:8080"; //
            myActivity.execute( IPandPort );
        }
        catch ( Exception ex )
        {
            System.err.print( "Error connecting to the server: " + ex );
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.settings, menu );
        return true;
    }

    // ////////////////////////////////
    // possibly unneeded
    // ///////////////////////////////
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
     * creates the 3 tabs ( HomeTab, MenuTab, OrderTab ) and starts the app
     */
    public void createTabs()
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

    /**
     * Raises an alertbox informing the user that the server is not active and
     * then closes
     */
    public void alertUserServerIsNotConnected()
    {

        // prepare the alert box
        AlertDialog.Builder alertbox =
                new AlertDialog.Builder( MainActivity.this );
        // set the message to display
        alertbox.setMessage( "The server is currently not on, sorry for the inconvenience.\nPlease try again shortly." );
        // set a positive/yes button and create a listener
        alertbox.setPositiveButton( "Ok", new DialogInterface.OnClickListener()
        {
            public void onClick( DialogInterface arg0, int arg1 )
            {
                MainActivity.this.finish();
            }
        } );
        alertbox.show();
    }

    // ////////////////////////////////
    // possibly unneeded
    // ///////////////////////////////
    private void initializeSettingsActivity()
    {
        settings_ = new SettingsPreferenceActivity();
        settingsIntention =
                new Intent( MainActivity.this, SettingsPreferenceActivity.class );
        MainActivity.this.startActivity( settingsIntention );
    }

    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        if ( inputCorrect && initialSetup )
        {
            isServerOn();
            inputCorrect = false;
            initialSetup = false;
            
        }
    }

}