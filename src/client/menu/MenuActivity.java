package client.menu;

import client.home.MainActivity;

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
 *         This is the menu class which will add items to the plate (cart)
 * 
 * @version Nov 8, 2013
 * 
 */
public class MenuActivity extends MainActivity
{

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_menu_home );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menu_home, menu );
        return true;
    }
}
