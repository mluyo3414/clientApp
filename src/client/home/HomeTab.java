package client.home;

import com.example.foodnow.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

/**
 * 
 * @author Miguel Suarez
 * @author Carl Barbee
 * @author Matt Luckam
 * @author James Dagres
 * 
 *         Tab the app is opened to. It displays the location and hours of the
 *         store and a welcome address from the server including how many orders
 *         are currently being made
 * 
 */
public class HomeTab extends Activity
{
    /**
     * response from the server, includes welcome and number of orders currently
     * in the queue
     */
    public static String serverStatus;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home_tab );

        TextView textView = (TextView) findViewById( R.id.Home_Tab_View );

        // home screen text display
        textView.setText( "Location: 117A Lavery Hall\n" );
        textView.append( "                  Blacksburg, VA 24061 \n\n" );
        textView.append( "Hours: Mon–Fri 10:30am–10:00pm" );
        textView.append( "\n\n\n" + serverStatus );

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.home_tab, menu );
        return true;
    }

}
