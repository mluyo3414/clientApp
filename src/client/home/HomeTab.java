package client.home;

import com.example.foodnow.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class HomeTab extends Activity
{

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home_tab );

        TextView textView = (TextView) findViewById( R.id.Home_Tab_View );

        textView.setText( "Location: 117A Lavery Hall\n" );
        textView.append( "                  Blacksburg, VA 24061 \n\n" );
        textView.append( "Hours: Mon–Fri 10:30am–10:00pm" );

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.home_tab, menu );
        return true;
    }

}
