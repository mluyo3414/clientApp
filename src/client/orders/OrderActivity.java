package client.orders;

import com.example.foodnow.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * @author Jimmy Dagres
 * @author Carl Barbee
 * @author Matt Luckam
 * @author Miguel Suarez
 * 
 * @version Nov 8, 2013
 * 
 */
public class OrderActivity extends Activity
{

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_order_home );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.order_home, menu );
        return true;
    }

}
