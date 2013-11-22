package client.orders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import client.home.MainActivity;

import com.example.foodnow.R;

@SuppressWarnings( "unused" )
public class OrderTabAsyncTask extends AsyncTask
{
    private String orderNumber;

    @Override
    protected Object doInBackground( Object... arg0 )
    {

        String order = (String) arg0[0];

        String name = (String) arg0[1];

        String total = (String) arg0[2];

        this.post( order, name, total );

        // TODO Takes in parameters and send to the server
        return null;
    }

    public String post( String order, String name, String total )
    {
        // hard coded IP and port#
        String IPandPort = "172.31.161.253:8080";

        // posting to server
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost( "http://" + IPandPort + "/client" );
        String data = "";
        try
        {
            // three parameters are passed
            List<NameValuePair> nameValuePairs =
                    new ArrayList<NameValuePair>( 1 );
            nameValuePairs.add( new BasicNameValuePair( "username", name ) );

            // adds order
            nameValuePairs.add( new BasicNameValuePair( "order", order ) );

            // gets and adds date
            DateFormat dateFormatter =
                    new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
            dateFormatter.setLenient( false );
            Date today = new Date();
            String s = dateFormatter.format( today );
            nameValuePairs.add( new BasicNameValuePair( "location", s ) );

            // add total at some point
            // nameValuePairs.add( new BasicNameValuePair( "orderTotal", total )
            // );

            post.setEntity( new UrlEncodedFormEntity( nameValuePairs ) );

            HttpResponse response = client.execute( post );
            BufferedReader rd =
                    new BufferedReader( new InputStreamReader( response
                            .getEntity().getContent() ) );
            // read from server
            String line = "";
            StringBuffer sb = new StringBuffer( "" );
            String newline = System.getProperty( "line.separator" );
            while ( (line = rd.readLine()) != null )
            {
                sb.append( line + newline );

            }
            rd.close();
            data = sb.toString();

            setOrderNumber( data );

            // get client.orders status
            return (data);
        }
        catch ( IOException e )
        {
            data = "ERROR FROM SERVER";
            e.printStackTrace();
        }
        return data;

    }

    /**
     * gets the order number
     * 
     * @return order number
     */
    public String getOrderNumber()
    {
        return orderNumber;
    }

    /**
     * Sets the order number
     * 
     * @param orderNumber
     *            order number
     */
    public void setOrderNumber( String orderNumber )
    {
        this.orderNumber = orderNumber;
    }

}
