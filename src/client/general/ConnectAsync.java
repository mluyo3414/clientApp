package client.general;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Intent;
import android.os.AsyncTask;
import client.home.FromServer1;
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

    protected String ipAndPort;
    // main activity to call back
    MainActivity activity_;

    /**
     * 
     * @param mainActivity
     *            ConnectAsync constructor
     */
    public ConnectAsync( client.home.MainActivity mainActivity )
    {
        // main activity instance to start next activity
        activity_ = mainActivity;
    }

    /**
     * 
     * @param IPAndPort
     * @return response from server
     * @throws Exception
     */
    public String getInternetData( String IPAndPort ) throws Exception
    {
        BufferedReader in = null;
        String data = "";
        try
        {
            // setup http client
            HttpClient client = new DefaultHttpClient();
            // process data from
            URI website = new URI( "http://" + IPAndPort );
            // request using get method
            HttpGet request = new HttpGet( website );
            HttpResponse response = client.execute( request );
            // string using buffered reader
            // streamreader bytes into characters
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
            in.close();
            data = sb.toString();
            // returns responser from the server
            return parseData( data );

        }
        finally
        {
            if ( in != null )
            {
                try
                {
                    in.close();
                    return parseData( data );

                }
                catch ( Exception e )
                {
                    e.printStackTrace();

                }
            }
        }
    }

    @Override
    protected String doInBackground( String... params )
    {

        ipAndPort = params[0];

        // getting info from server
        String data = "";
        try
        {
            data = getInternetData( ipAndPort );

        }
        catch ( Exception e )
        {

            e.printStackTrace();
        }

        return data;

    }

    @Override
    protected void onPostExecute( String fromParseData )
    {

        // calls next activity to display server response and number of users if
        // there was a connection

        Intent in = new Intent( activity_, FromServer1.class );

        in.putExtra( "Data", fromParseData );

        activity_.startActivity( in );

    }

    private String parseData( String data )
    {

        // check if server is up

        return data;
    }
}
