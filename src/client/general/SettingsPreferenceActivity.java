package client.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodnow.R;

/**
 * @author Jimmy Dagres
 * @author Carl Barbee
 * @author Matt Luckam
 * @author Miguel Suarez
 * 
 * @version Nov 8, 2013
 * 
 *          This activity displays a list of all of the setting preferecnes.
 *          When selected a dialog appears allowing the user to change the
 *          setting. Each setting is stored in a non volatile shared preference
 *          file.
 * 
 */
public class SettingsPreferenceActivity extends Activity
{
    private String stringIP_;
    private String stringPort_;

    private ListView settingsListView_;

    // Common handles to the preference file
    SharedPreferences preference_;
    SharedPreferences.Editor preferenceEditor_;

    /**
     * @param stringIP_
     *            the Port
     */
    public void setStringIP( String stringIP_ )
    {
        this.stringIP_ = stringIP_;
    }
    
    /**
     * @return the IP
     */
    public String getStringIP()
    {
        return preference_.getString(
                getString( R.string.pref_title_ip ),
                getString( R.string.pref_title_ip ) ) ;
    }

    /**
     * @return the IP
     */
    public String getStringPort()
    {
        return preference_.getString(
                getString( R.string.pref_title_port ),
                getString( R.string.pref_title_port ) ) ;
    }
    
    /**
     * @return the name saved in the preferences
     */
    public String getName()
    {
        return preference_.getString(
                getString( R.string.pref_title_name ),
                getString( R.string.pref_title_name ) ) ;
    }
    
    /**
     * @return the name saved in the preferences
     */
    public String getStringHomeLocation()
    {
        return preference_.getString(
                getString( R.string.pref_title_home_location ),
                getString( R.string.pref_title_home_location ) ) ;
    }
    
    /**
     * @return the name saved in the preferences
     */
    public String getPhoneNumber()
    {
        return preference_.getString(
                getString( R.string.pref_title_phone_number ),
                getString( R.string.pref_title_phone_number ) ) ;
    }
    
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings );

        settingsListView_ =
                (ListView) findViewById( R.id.settingsListView1 );

        setupSimplePreferencesScreen();

        preference_ =
                getSharedPreferences( getString( R.string.pref_title_file ),
                        Context.MODE_PRIVATE );

        try
        {
            preferenceEditor_ = preference_.edit();
        }
        catch ( Exception ex )
        {
            System.err.print( "Error fetching preference file" );
        }

        initializeSettingsList();

        initializeSettingsValue();
    }

    /**
     * Initializes the settings list and sets up it's on click event
     */
    private void initializeSettingsList()
    {
        String[] settings_Array =
                getResources().getStringArray( R.array.settings_array );

        final ArrayList<String> list = new ArrayList<String>();

        for ( int i = 0; i < settings_Array.length; ++i )
        {
            list.add( settings_Array[i] );
        }

        final StableArrayAdapter adapter = new StableArrayAdapter( this,
                android.R.layout.simple_list_item_1, list );
        settingsListView_.setAdapter( adapter );

        settingsListView_.setOnItemClickListener( new OnItemClickListener()
        {
            @Override
            public void onItemClick( AdapterView<?> parent, View view,
                    int position, long id )
            {
                // Call the function to handle the updated preference
                handleUpdatePreferenceSelection( position );
            }
        } );
    }

    /**
     * This function is called when any on click event for the settings is hit,
     * it then handles the appropriate action for modified the specifically
     * selected preference.
     * 
     * @param positionOfSettingToUpdate
     */
    private void
            handleUpdatePreferenceSelection( int positionOfSettingToUpdate )
    {
        switch ( positionOfSettingToUpdate )
        {
        case 0: // The name preference
            displayUpdateSettingsDialog(
                    getString( R.string.pref_title_name ),
                    preference_.getString(
                            getString( R.string.pref_title_name ),
                            getString( R.string.pref_title_name ) ) );
            break;

        case 1: // The phone number preference
            displayUpdateSettingsDialog(
                    getString( R.string.pref_title_phone_number ),
                    preference_.getString(
                            getString( R.string.pref_title_phone_number ),
                            getString( R.string.pref_title_phone_number ) ) );
            break;

        case 2: // The payment preference
            // TODO: Call a unique payment dialog and Integrate paypal
            break;

        case 3: // The home location preference
            // TODO: an interface to select only locations of existing
            // restaurants.
            displayUpdateSettingsDialog(
                    getString( R.string.pref_title_home_location ),
                    preference_.getString(
                            getString( R.string.pref_title_home_location ),
                            getString( R.string.pref_title_home_location ) ) );
            break;

        case 4: // The Port preference
            displayUpdateSettingsDialog(
                    getString( R.string.pref_title_port ),
                    preference_.getString(
                            getString( R.string.pref_title_port ),
                            getString( R.string.pref_title_port ) ) );
            break;

        case 5: // The IP preference
            displayUpdateSettingsDialog(
                    getString( R.string.pref_title_ip ),
                    preference_.getString(
                            getString( R.string.pref_title_ip ),
                            getString( R.string.pref_title_ip ) ) );
            break;
        }

    }

    /**
     * This function will create a dialog with the passed setting to be updated
     * and if it is updated then it will return the newly updated settings
     * string
     * 
     * @param settingsToBeUpdated
     *            this is the string the dialog will list is being updated
     * @param currentStringForTheSetting
     */
    private void displayUpdateSettingsDialog(
            final String settingsToBeUpdated,
            String currentStringForTheSetting )
    {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from( getBaseContext() );
        View promptsView =
                li.inflate( R.layout.dialog_update_settings, null
                        );

        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder( this );

        // set prompts.xml to alert dialog builder and sets the title
        alertDialogBuilder.setView( promptsView );
        alertDialogBuilder.setTitle( "Update stored " + settingsToBeUpdated );

        // Display text with the settings to be updated.
        final TextView tv1;
        tv1 = (TextView) promptsView.findViewById( R.id.updateSettingsText );
        tv1.setText( "New " + settingsToBeUpdated + ":" );

        // Display the current settings in the edit text box
        final EditText result = (EditText)
                promptsView.findViewById( R.id.editTextDialogUserInput );
        result.setHint( currentStringForTheSetting );

        // set dialog message
        alertDialogBuilder
                .setCancelable( false )
                .setPositiveButton( "OK",
                        new DialogInterface.OnClickListener()
                        {
                            public void
                                    onClick( DialogInterface dialog, int id )
                            {
                                String newSettingsValue =
                                        result.getText().toString().trim();

                                if ( !"".equals( newSettingsValue ) )
                                {
                                    updatePreference( settingsToBeUpdated,
                                            newSettingsValue );
                                }
                            }
                        } )
                .setNegativeButton( "Cancel",
                        new DialogInterface.OnClickListener()
                        {
                            public void
                                    onClick( DialogInterface dialog, int id )
                            {
                                dialog.cancel();
                            }
                        } );

        // Create the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Show the dialog
        alertDialog.show();
    }

    /**
     * Sets up the edit texts and their event listeners, puts the array into the
     * listview
     */
    private void initializeSettingsValue()
    {
        try
        {
            // TODO: need this?
            // nameText_ = (EditText) findViewById( R.id.nameEditText );
            // // nameText_.setText( preference_
            // // .getString( getString( R.id.nameEditText ),
            // // getString( R.id.nameEditText ) ) );
            //
            // phoneNumberText_ =
            // (EditText) findViewById( R.id.phoneNumberEditText );
            // // phoneNumberText_.setText( preference_
            // // .getString( getString( R.id.phoneNumberEditText ),
            // // getString( R.id.phoneNumberEditText ) ) );
            //
            // homeLocationText_ =
            // (EditText) findViewById( R.id.homeLocationEditText );
            // // homeLocationText_.setText( preference_
            // // .getString( getString( R.id.homeLocationEditText ),
            // // getString( R.id.homeLocationEditText ) ) );
            //
            // portText_ = (EditText) findViewById( R.id.portEditText );
            // // portText_.setText( preference_
            // // .getString( getString( R.id.portEditText ),
            // // getString( R.id.portEditText ) ) );
            //
            // ipText_ = (EditText) findViewById( R.id.iPEditText );
            // // portText_.setText( preference_
            // // .getString( getString( R.id.iPEditText ),
            // // getString( R.id.iPEditText ) ) );
        }
        catch ( Exception ex )
        {

        }
    }

    /**
     * Updates the preference of the passed preference
     * 
     * @param preferenceToUpdate
     * @param newPreferenceValue
     */
    private void updatePreference( String preferenceToUpdate,
            String newPreferenceValue )
    {
        preferenceEditor_.putString( preferenceToUpdate, newPreferenceValue );

        preferenceEditor_.commit();
        Toast.makeText( getApplicationContext(),
                preferenceToUpdate + " has been updated.",
                Toast.LENGTH_SHORT )
                .show();

        preference_ =
                getSharedPreferences( getString( R.string.pref_title_file ),
                        Context.MODE_PRIVATE );
    }

    /**
     * Shows the simplified settings UI if the device configuration if the
     * device configuration dictates that a simplified, single-pane UI should be
     * shown.
     */
    private void setupSimplePreferencesScreen()
    {
        // TODO: do I need this

        // In the simplified UI, fragments are not used at all and we instead
        // use the older PreferenceActivity APIs.

        // Add 'general' preferences.
        // getting preferences from a specified file

        // // Bind the summaries of EditText/List/Dialog preferences to
        // // their values. When their values change, their summaries are
        // updated
        // // to reflect the new value, per the Android Design guidelines.
        // bindPreferenceSummaryToValue( findPreference( getString(
        // R.string.pref_title_ip ) ) );
        // bindPreferenceSummaryToValue( findPreference( getString(
        // R.string.pref_title_name ) ) );
        // bindPreferenceSummaryToValue( findPreference( getString(
        // R.string.pref_title_payment ) ) );
        // bindPreferenceSummaryToValue( findPreference( getString(
        // R.string.pref_title_port ) ) );
        // bindPreferenceSummaryToValue( findPreference( getString(
        // R.string.pref_title_phone_number ) ) );
    }

    /**
     * Checks to ensure the IP isn't the default value, then checks to see if
     * the Port isn't the default value
     * 
     * @return if the IP and Port values have been set
     */
    public boolean iPandPortValueAreSet()
    {
        try
        {
            stringIP_ =
                    preference_.getString(
                            getString( R.string.pref_title_ip ),
                            getString( R.string.pref_title_ip ) );

            if ( null != stringIP_
                    && getString( R.string.pref_default_ip ) != stringIP_ )
            {
                stringPort_ =
                        preference_.getString(
                                getString( R.string.pref_title_port ),
                                getString( R.string.pref_title_port ) );

                if ( null != stringPort_
                        && getString( R.string.pref_default_port ) != stringPort_ )
                {
                    return true;
                }
            }
        }
        catch ( Exception ex )
        {
            System.err.print( "Error in checking Port and Ip value: " + ex );
        }

        return false;
    }

    /**
     * @author Jimmy Dagres
     * 
     * @version Nov 19, 2013
     * 
     *          Handles inserting the array into the listview
     * 
     */
    private class StableArrayAdapter extends ArrayAdapter<String>
    {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter( Context context, int textViewResourceId,
                List<String> objects )
        {
            super( context, textViewResourceId, objects );
            for ( int i = 0; i < objects.size(); ++i )
            {
                mIdMap.put( objects.get( i ), i );
            }
        }

        @Override
        public long getItemId( int position )
        {
            String item = getItem( position );
            return mIdMap.get( item );
        }

        @Override
        public boolean hasStableIds()
        {
            return true;
        }
    }
}