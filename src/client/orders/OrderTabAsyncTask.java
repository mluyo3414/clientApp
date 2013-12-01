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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import client.home.MainActivity;

import com.example.foodnow.R;

@SuppressLint( "SimpleDateFormat" )
@SuppressWarnings( { "unused", "rawtypes" } )
public class OrderTabAsyncTask extends AsyncTask
{
    /**
     * orderNumber received from server
     */
    private static String orderNumber;

    @Override
    protected Object doInBackground( Object... arg0 )
    {
        while ( OrderTab.nextStep != 1 )
        {
            android.os.SystemClock.sleep( 5000 );
        }

        String order = (String) arg0[0];
        String name = (String) arg0[1];
        String total = (String) arg0[2];

        this.post( order, name, total );

        OrderTab.nextStep = 2;
        return null;
    }

    /**
     * posts the order, users name, and total cost on the server and receives a
     * order number in return
     * 
     * @param order
     *            order of user in string
     * @param name
     *            users name
     * @param total
     *            total cost of the order
     * @return total
     */
    public String post( String order, String name, String total )
    {
        // hard coded IP and port#
        String IPandPort = "54.201.86.103:8080";

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
            nameValuePairs.add( new BasicNameValuePair( "time", s ) );

            // adds total
            nameValuePairs.add( new BasicNameValuePair( "total", total ) );

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
            OrderTab.nextStep = 3;
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
        OrderTabAsyncTask.orderNumber = orderNumber;
    }

}