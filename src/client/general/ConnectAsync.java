package client.general;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;
import client.home.HomeTab;
import client.home.MainActivity;

/**
 * @author Miguel Suarez
 * @author Carl Barbee
 * @author Matt Luckam
 * @author James Dagres
 * 
 *         This asynchronous activity connects the app with the server and gets
 *         the number of users already in the server. It receives any string
 *         website and returns the string contained on that site
 */
public class ConnectAsync extends AsyncTask<String, Void, String>
{
    /**
     * main activity to call back
     */
    private MainActivity activity_;

    /**
     * 
     * Constructor that takes in an instance of the class that called it
     * 
     * @param mainActivity
     *            instance of the class that called this class
     */
    public ConnectAsync( client.home.MainActivity mainActivity )
    {
        // main activity instance to start next activity
        activity_ = mainActivity;
    }

    /**
     * Performs an HTTPGET to verify the server is active
     * 
     * @return toReturn returns a value if the server is active, otherwise null
     */
    @Override
    protected String doInBackground( String... params )
    {

        String ipAndPort = params[0];

        // HTTP GET
        String url = "http://" + ipAndPort;
        BufferedReader in = null;
        String toReturn = null;
        try
        {
            HttpClient client = new DefaultHttpClient();

            // sets a 5 second timeout on the server
            HttpParams httpParams = client.getParams();
            HttpConnectionParams.setConnectionTimeout( httpParams, 5000 );
            HttpConnectionParams.setSoTimeout( httpParams, 5000 );

            URI myWebsite = new URI( url );

            HttpGet request = new HttpGet( myWebsite );
            HttpResponse response = client.execute( request );

            in =
                    new BufferedReader( new InputStreamReader( response
                            .getEntity().getContent() ) );
            StringBuffer sb = new StringBuffer( "" );
            String l = "";
            String newline = System.getProperty( "line.separator" );
            while ( (l = in.readLine()) != null )
            {
                sb.append( l + newline );
            }

            toReturn = sb.toString();

            in.close();

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return toReturn;

    }

    /**
     * if correct string is received from the server that app is opened,
     * otherwise the app notifies the user that the server is not active and
     * closes the app
     * 
     * @param serverResponse
     *            string received from the server, null if the server times out
     */
    @Override
    protected void onPostExecute( String serverResponse )
    {
        if ( serverResponse != null
                && serverResponse.startsWith( "Hello from the Server," ) )
        {
            // start tabs
            HomeTab.serverStatus = serverResponse;
            activity_.createTabs();

        }
        else
        {
            // kill the app
            activity_.alertUserServerIsNotConnected();
        }
    }
}
