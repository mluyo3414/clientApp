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
import android.telephony.TelephonyManager;

import client.home.MainActivity;

import com.example.foodnow.R;

/**
 * 
 * @author Miguel Suarez
 * @author James Dagres
 * @author Carl Barbee
 * @author Matt Luckam
 * 
 *         After an order is payed for the order details are sent to the server
 *         and an order confirmation number is received from the server.
 */
@SuppressLint( "SimpleDateFormat" )
@SuppressWarnings( { "unused", "rawtypes" } )
public class OrderTabAsyncTask extends AsyncTask
{
    /**
     * orderNumber received from server
     */
    private static String orderNumber;

    private static OrderTab orderTab;

    public OrderTabAsyncTask( OrderTab newOrderTab )
    {
        orderTab = newOrderTab;
    }

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
        String phoneNumber = (String) arg0[3];
        String paymentMethod = (String) arg0[4];

        this.post( order, name, total, phoneNumber, paymentMethod );

        return paymentMethod;
    }

    @Override
    protected void onPostExecute( Object result )
    {
        orderTab.orderConfirmation();
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
     * @param phoneNumber
     *            the number of the user ordering
     * @param paymentMethod
     *            method of payment the customer is using
     * 
     * @return total
     */
    public String post( String order, String name, String total,
            String phoneNumber, String paymentMethod )
    {
        // hard coded IP and port#
        String IPandPort = "172.31.172.58:8080";// 54.201.86.103:8080";

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
                    new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            dateFormatter.setLenient( false );
            Date today = new Date();
            String s = dateFormatter.format( today );
            nameValuePairs.add( new BasicNameValuePair( "time", s ) );

            // adds total
            nameValuePairs.add( new BasicNameValuePair( "total", total ) );

            // adds phone number
            nameValuePairs.add( new BasicNameValuePair( "phone", phoneNumber ) );

            // adds payment method
            nameValuePairs.add( new BasicNameValuePair( "paymentmethod",
                    paymentMethod ) );

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

            orderNumber = data;

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
}