package client.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
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
 *          A {@link PreferenceActivity} that presents a set of application
 *          settings. On handset devices, settings are presented as a single
 *          list. On tablets, settings are split by category, with category
 *          headers shown to the left of the list of settings.
 *          <p>
 *          See <a
 *          href="http://developer.android.com/design/patterns/settings.html">
 *          Android Design: Settings</a> for design guidelines and the <a
 *          href="http://developer.android.com/guide/topics/ui/settings.html"
 *          >Settings API Guide</a> for more information on developing a
 *          Settings UI.
 * 
 */
public class SettingsPreferenceActivity extends Activity
{
    private String stringIP_;
    private String stringPort_;

    private EditText phoneNumberText_, nameText_, homeLocationText_,
            portText_, ipText_;
    private TextView paymentMethodSet_;

    private ListView settingsListView_;

    // Common handles to the preference file
    SharedPreferences preference_;
    SharedPreferences.Editor preferenceEditor_;

    /**
     * @return the IP
     */
    public String getStringIP()
    {
        return stringIP_;
    }

    /**
     * @param stringIP_
     *            the Port
     */
    public void setStringIP( String stringIP_ )
    {
        this.stringIP_ = stringIP_;
    }

    /**
     * Determines whether to always show the simplified settings UI, where
     * settings are presented in a single list. When false, settings are shown
     * as a master/detail two-pane view on tablets. When true, a single pane is
     * shown on tablets.
     */
    private static final boolean ALWAYS_SIMPLE_PREFS = false;

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings );

        settingsListView_ =
                (ListView) findViewById( R.id.settingsListView1 );

        setupSimplePreferencesScreen();

        displaySettingsStrings();

        preference_ =
                getSharedPreferences( getString( R.string.pref_title_file ),
                        Context.MODE_PRIVATE );

        try
        {
            preferenceEditor_ = preference_.edit();
        }
        catch ( Exception ex )
        {

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
                Toast.makeText( getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG )
                        .show();
            }
        } );
    }

    /**
     * This function will create a dialog with the passed setting to be updated
     * and if it is updated then it will return the newly updated settings
     * string
     * 
     * @param settingsToBeUpdated
     *            this is the string the dialog will list is being updated
     * @param currentStringForTheSetting
     * @return the updated preference string
     */
    private String displayUpdateSettingsDialog( String settingsToBeUpdated,
            String currentStringForTheSetting )
    {
        // TODO:

        // preferenceEditor_.putString( getString( R.id.nameEditText ),
        // (String) s );

        return null;
    }

    /**
     * Sets up the edit texts and their event listeners, puts the array into the
     * listview
     */
    private void initializeSettingsValue()
    {
        try
        {

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
     * Populates the list view with the string values
     */
    private void displaySettingsStrings()
    {
        // setListAdapter( new ArrayAdapter<String>( this, R.id.listView1,
        // R.array.settings_array ) );
        // settingsListView_ = getListView();
        // TODO:
    }

    /**
     * Shows the simplified settings UI if the device configuration if the
     * device configuration dictates that a simplified, single-pane UI should be
     * shown.
     */
    private void setupSimplePreferencesScreen()
    {
        // TODO:

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
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet( Context context )
    {
        return (context.getResources().getConfiguration().screenLayout
        & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener =
            new Preference.OnPreferenceChangeListener()
            {
                @Override
                public boolean onPreferenceChange( Preference preference,
                        Object value )
                {
                    String stringValue = value.toString();

                    if ( preference instanceof ListPreference )
                    {
                        // For list preferences, look up the correct display
                        // value in
                        // the preference's 'entries' list.
                        ListPreference listPreference =
                                (ListPreference) preference;
                        int index =
                                listPreference.findIndexOfValue( stringValue );

                        // Set the summary to reflect the new value.
                        preference.setSummary(
                                index >= 0
                                        ? listPreference.getEntries()[index]
                                        : null );

                    }
                    else
                    {
                        // For all other preferences, set the summary to the
                        // value's
                        // simple string representation.
                        preference.setSummary( stringValue );
                    }
                    return true;
                }
            };

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     * 
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue( Preference preference )
    {
        // Set the listener to watch for value changes.
        preference
                .setOnPreferenceChangeListener( sBindPreferenceSummaryToValueListener );

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange( preference,
                PreferenceManager
                        .getDefaultSharedPreferences( preference.getContext() )
                        .getString( preference.getKey(), "" ) );
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
                            "pref_general" );

            if ( null != stringIP_
                    && getString( R.string.pref_default_ip ) != stringIP_ )
            {
                stringPort_ =
                        preference_.getString(
                                getString( R.string.pref_title_port ),
                                "pref_general" );

                if ( null != stringPort_
                        && getString( R.string.pref_default_port ) != stringPort_ )
                {
                    return true;
                }
            }
        }
        catch ( Exception ex )
        {

        }

        return false;
    }

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
