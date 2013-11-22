package client.orders;

import client.home.ConnectAsyncCurrentConnected;
import client.home.CurrentConnected;
import client.menu.MenuTab;

import com.example.foodnow.R;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
     * Confirm button
     */
    private static Button button;

    /**
     * async task sends data to the server
     */
    OrderTabAsyncTask orderToServer;

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

        button = (Button) findViewById( R.id.Button );

        addListenerOnButton();

    }

    /**
     * When list view item is clicked
     */
    @Override
    protected void onListItemClick( ListView l, View v, int position, long id )
    {
        currentNumber = position;

        removeItem();
    }

    /**
     * Settings for item that are removed from the list
     */
    private void removeItem()
    {
        // prepare the alert box
        alertbox = new AlertDialog.Builder( OrderTab.this );
        // set the message to display
        alertbox.setMessage( "�Remove from Plate?" );
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

    /**
     * Items added to the list - called from the menu
     * 
     * @param newItem
     *            String with new items name from the menu
     */
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

    /**
     * When confirm button is pushed
     */
    public void addListenerOnButton()
    {
        button.setOnClickListener( new View.OnClickListener()
        {
            public void onClick( View v )
            {
                // prepare the alert box
                AlertDialog.Builder alertbox =
                        new AlertDialog.Builder( OrderTab.this );
                // set the message to display
                alertbox.setMessage( "Confirm your order?" );
                // set a positive/yes button and create a listener
                alertbox.setPositiveButton( "Yes",
                        new DialogInterface.OnClickListener()
                        {

                            public void
                                    onClick( DialogInterface arg0, int arg1 )
                            {
                                // prepare the alert box
                                AlertDialog.Builder alertbox =
                                        new AlertDialog.Builder( OrderTab.this );
                                // set the message to display
                                alertbox.setMessage( "Your order has been confirmed \n\nOrder ID: add id here" );

                                // //////////////////////////////////////////
                                // start async task send arraylist string, name,
                                orderToServer = new OrderTabAsyncTask();
                                orderToServer.execute( list.toString() ); // and
                                                                          // name
                                // //////////////////////////////////////////

                                alertbox.setPositiveButton( "Ok",
                                        new DialogInterface.OnClickListener()
                                        {
                                            public void onClick(
                                                    DialogInterface arg0,
                                                    int arg1 )
                                            {
                                            }
                                        } );
                                alertbox.show();
                            }
                        } );

                // set a negative/no button and create a listener
                alertbox.setNegativeButton( "No",
                        new DialogInterface.OnClickListener()
                        {

                            // //////////////////////////////////////////
                            // do something when the NO button is clicked
                            // //////////////////////////////////////////
                            public void
                                    onClick( DialogInterface arg0, int arg1 )
                            {

                            }
                        } );
                alertbox.show();
            }

        } );

    }
}