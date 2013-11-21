package client.orders;

import com.example.foodnow.R;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderTab extends ListActivity
{
    /** Items entered by the user is stored in this ArrayList variable */
    private static ArrayList<String> list;

    /** Declaring an ArrayAdapter to set items to ListView */
    private static ArrayAdapter<String> adapter;
    /**
     * Instance of the list view
     */
    @SuppressWarnings( "unused" )
    private static ListView listView;
    /**
     * Footer for the total
     */
    private static TextView footer;
    /**
     * alter confirming an addition to your plate
     */
    private AlertDialog.Builder alertbox;
    /**
     * current item number selected
     */
    private int currentNumber;

    /**
     * Called when the activity is first created
     */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_order_tab );

        footer = (TextView) findViewById( R.id.Footer );
        list = new ArrayList<String>();
        listView = (ListView) findViewById( R.id.list );
        adapter =
                new ArrayAdapter<String>( this, R.layout.list_view,
                        R.id.itemName, list );

        setListAdapter( adapter );
    }

    @Override
    protected void onListItemClick( ListView l, View v, int position, long id )
    {
        currentNumber = position;

        removeItem();
    }

    private void removeItem()
    {
        // prepare the alert box
        alertbox = new AlertDialog.Builder( OrderTab.this );
        // set the message to display
        alertbox.setMessage( "¿Remove from Plate?" );
        // set a positive/yes button and create a listener
        alertbox.setPositiveButton( "Yes",
                new DialogInterface.OnClickListener()
                {
                    // //////////////////////////////////////////
                    // do something when the YES button is clicked
                    // //////////////////////////////////////////
                    public void onClick( DialogInterface arg0, int arg1 )
                    {
                        list.remove( currentNumber );

                        Toast.makeText( getApplicationContext(),
                                "The item was removed from your plate",
                                Toast.LENGTH_SHORT ).show();

                        adapter.notifyDataSetChanged();

                        double total = 0;
                        for ( int i = 0; i < list.size(); i++ )
                        {
                            total +=
                                    Double.parseDouble( list
                                            .get( i )
                                            .substring(
                                                    list.get( i ).indexOf( "$" ) + 1 ) );
                        }
                        footer.setTextSize( 25 );
                        DecimalFormat twoDForm = new DecimalFormat( "#.##" );
                        footer.setText( "Total: $"
                                + Double.valueOf( twoDForm.format( total ) ) );
                    }
                } );

        // set a negative/no button and create a listener
        alertbox.setNegativeButton( "No", new DialogInterface.OnClickListener()
        {

            // //////////////////////////////////////////
            // do something when the NO button is clicked
            // //////////////////////////////////////////
            public void onClick( DialogInterface arg0, int arg1 )
            {
                Toast.makeText( getApplicationContext(),
                        "The item was NOT removed from your plate",
                        Toast.LENGTH_SHORT ).show();
            }
        } );

        alertbox.show();

    }

    public void addItems( String newItem )
    {
        list.add( newItem );
        adapter.notifyDataSetChanged();

        double total = 0;
        for ( int i = 0; i < list.size(); i++ )
        {
            total +=
                    Double.parseDouble( list.get( i ).substring(
                            list.get( i ).indexOf( "$" ) + 1 ) );
        }
        footer.setTextSize( 25 );
        DecimalFormat twoDForm = new DecimalFormat( "#.##" );
        footer.setText( "Total: $" + Double.valueOf( twoDForm.format( total ) ) );

    }

}
