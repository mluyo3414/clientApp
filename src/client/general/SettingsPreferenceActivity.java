package client.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import client.home.MainActivity;

import com.example.foodnow.R;

/**
 * @author Jimmy Dagres
 * @author Carl Barbee
 * @author Matt Luckam
 * @author Miguel Suarez
 * 
 * @version Nov 8, 2013
 * 
 *          This activity displays a list of all of the setting preferences.
 *          When selected a dialog appears allowing the user to change the
 *          setting. Each setting is stored in a non volatile shared preference
 *          file.
 * 
 */
public class SettingsPreferenceActivity extends Activity
{
    private ListView settingsListView_;

    // Common handles to the preference file
    SharedPreferences preference_;
    SharedPreferences.Editor preferenceEditor_;

    /**
     * @return the name saved in the preferences
     */
    public String getName()
    {
        return preference_.getString( getString( R.string.pref_title_name ),
                getString( R.string.pref_title_name ) );
    }

    /**
     * @return the name saved in the preferences
     */
    public String getPhoneNumber()
    {
        return preference_.getString(
                getString( R.string.pref_title_phone_number ),
                getString( R.string.pref_title_phone_number ) );
    }

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings );

        settingsListView_ = (ListView) findViewById( R.id.settingsListView1 );

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

        // TODO: Hack
        // updatePreference( getString( R.string.pref_default_phone_number ),
        // getString( R.string.pref_default_phone_number ) );
        // updatePreference( getString( R.string.pref_default_name ),
        // getString( R.string.pref_default_name ) );

        // Makes sure a proper phone number is set
        checkForValidPhoneNumber();

        checkForValidName();
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

        final StableArrayAdapter adapter =
                new StableArrayAdapter( this,
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
    public void
            handleUpdatePreferenceSelection( int positionOfSettingToUpdate )
    {
        LayoutInflater li = LayoutInflater.from( getBaseContext() );

        switch ( positionOfSettingToUpdate )
        {
        case 0: // The name preference
            View nameView =
                    li.inflate( R.layout.dialog_update_name_settings, null );

            displayUpdateSettingsDialog(
                    getString( R.string.pref_title_name ),
                    preference_.getString(
                            getString( R.string.pref_title_name ),
                            getString( R.string.pref_title_name ) ), nameView );
            break;

        case 1: // The phone number preference

            // get prompts.xml view
            View phoneNumberView =
                    li.inflate( R.layout.dialog_update_phone_number, null );

            displayUpdateSettingsDialog(
                    getString( R.string.pref_title_phone_number ),
                    preference_.getString(
                            getString( R.string.pref_title_phone_number ),
                            getString( R.string.pref_title_phone_number ) ),
                    phoneNumberView );
            break;

        case 2: // The payment method preference
            displayPaymentDialog( getString( R.string.pref_title_payment ),
                    preference_.getString(
                            getString( R.string.pref_title_payment ),
                            getString( R.string.pref_title_payment ) ) );
            break;
        }

    }

    /**
     * This function uses a dialog containing a radiogroup, it performs almost
     * the same as the displayUpdateSettingsDialog except it's designed for the
     * payment button. It creates a reference to the PayPal radioButton called
     * "payPalRadioButton" and if PayPal is the current selection then is checks
     * that option. Furthermore when then Ok button is selected it checks to see
     * if the payPalRadioButton is checked and updates the payment preference
     * accordingly.
     * 
     * @param settingsToBeUpdated
     * @param currentStringForTheSetting
     */
    private void displayPaymentDialog( final String settingsToBeUpdated,
            String currentStringForTheSetting )
    {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from( getBaseContext() );
        View promptsView =
                li.inflate( R.layout.dialog_update_payment_settings, null );
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder( this );

        // set prompts.xml to alert dialog builder and sets the title
        alertDialogBuilder.setView( promptsView );
        alertDialogBuilder.setTitle( "Update Payment Method" );

        final RadioButton payPalRadioButton =
                (RadioButton) promptsView.findViewById( R.id.paypalRadio );

        payPalRadioButton
                .setChecked( getString( R.string.title_paypal_payment )
                        .contains( currentStringForTheSetting ) );

        alertDialogBuilder
                .setCancelable( false )
                .setPositiveButton( "OK",
                        new DialogInterface.OnClickListener()
                        {
                            public void
                                    onClick( DialogInterface dialog, int id )
                            {
                                String newSettings;

                                if ( payPalRadioButton.isChecked() )
                                {
                                    newSettings =
                                            getString( R.string.title_paypal_payment );
                                }
                                else
                                {
                                    newSettings =
                                            getString( R.string.title_payatpickup_payment );
                                }

                                updatePreference( settingsToBeUpdated,
                                        newSettings );
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
     * This function will create a dialog with the passed setting to be updated
     * and if it is updated then it will return the newly updated settings
     * string
     * 
     * @param settingsToBeUpdated
     *            this is the string the dialog will list is being updated
     * @param currentStringForTheSetting
     * @param promptsView
     */
    private void displayUpdateSettingsDialog(
            final String settingsToBeUpdated,
            String currentStringForTheSetting, View promptsView )
    {
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
        final EditText result =
                (EditText) promptsView
                        .findViewById( R.id.editTextDialogUserInput );
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
                                    // TODO: If the setting to be updated is a
                                    // phone number make sure it's length is 10
                                    // digits
                                    if ( settingsToBeUpdated
                                            .contains( getString( R.string.pref_title_phone_number ) ) )
                                    {
                                        if ( 10 > newSettingsValue.length() )
                                        {
                                            Toast.makeText(
                                                    getApplicationContext(),
                                                    "Please enter a 10 digit phone number with an area code the format: \n 0123456789 ",
                                                    Toast.LENGTH_LONG )
                                                    .show();
                                        }
                                    }
                                    else
                                    {
                                        updatePreference( settingsToBeUpdated,
                                                newSettingsValue );
                                    }
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
     * Updates the preference of the passed preference
     * 
     * @param preferenceToUpdate
     * @param newPreferenceValue
     */
    public void updatePreference( String preferenceToUpdate,
            String newPreferenceValue )
    {
        preferenceEditor_.putString( preferenceToUpdate, newPreferenceValue );

        preferenceEditor_.commit();
        Toast.makeText(
                getApplicationContext(),
                preferenceToUpdate + " has been updated to: "
                        + newPreferenceValue, Toast.LENGTH_SHORT ).show();

        preference_ =
                getSharedPreferences( getString( R.string.pref_title_file ),
                        Context.MODE_PRIVATE );

        if ( allPreferencesSet() )
        {
            MainActivity.inputCorrect = true;
            finish();
        }
    }

    /**
     * Handles inserting the array into the listview
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

    /**
     * Checks to see if a phone number has been set that's not the default
     * 
     * @return whether a custom phone number has been entered
     */
    public boolean isPhoneNumberPrefernceSet()
    {
        return !getString( R.string.pref_title_phone_number ).contains(
                preference_.getString(
                        getString( R.string.pref_title_phone_number ),
                        getString( R.string.pref_title_phone_number ) ) );
    }

    /**
     * Checks to see if a name has been set that's not the default
     * 
     * @return whether a custom name has been entered
     */
    public boolean isNamePreferenceSet()
    {
        return !getString( R.string.pref_title_name ).contains(
                preference_.getString( getString( R.string.pref_title_name ),
                        getString( R.string.pref_title_name ) ) );
    }

    private void checkForValidName()
    {
        // TODO:

        if ( !isNamePreferenceSet() )
        {
            Toast.makeText( getApplicationContext(),
                    "Please enter a valid name.", Toast.LENGTH_SHORT ).show();

            handleUpdatePreferenceSelection( 0 );
        }
    }

    /**
     * Checks to make sure a valid phone number is entered, if it's not entered
     * then it checks to see if the current device is a tablet. If it is a
     * tablet then bring up the settings for the user to enter their phone
     * number. Else it's a phone, so set the phone number preference to the
     * device's phone number.
     */
    private void checkForValidPhoneNumber()
    {
        // Check to see if a phone number preference is saved other than the
        // default
        if ( !isPhoneNumberPrefernceSet() )
        {
            // If the current device is a tablet the
            if ( isTablet( this ) )
            {
                // Tell the user they need to enter a valid phone number
                Toast.makeText( getApplicationContext(),
                        "Please enter a valid phone number.",
                        Toast.LENGTH_SHORT ).show();

                handleUpdatePreferenceSelection( 1 );
            }
            else
            {
                TelephonyManager mTelephonyMgr;
                mTelephonyMgr =
                        (TelephonyManager) getSystemService( Context.TELEPHONY_SERVICE );

                String phoneNumber = mTelephonyMgr.getLine1Number();

                updatePreference(
                        getString( R.string.pref_title_phone_number ),
                        phoneNumber );
            }
        }
    }

    /**
     * @param context
     * @return if it's a tablet or not
     */
    public static boolean isTablet( Context context )
    {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * Checks to see if all of the preferences are set
     * 
     * @return if the required preferences are set
     */
    public boolean allPreferencesSet()
    {
        if ( isNamePreferenceSet() )
        {
            if ( isPhoneNumberPrefernceSet() )
            {
                return true;
            }
        }

        return false;
    }
}