package client.orders;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodnow.R;

public class OrderTab extends ListActivity
{

    /**
     * async task sends data to the server
     */
    private static OrderTabAsyncTask orderToServer;
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
     * number of items in the order
     */
    private static int numberOfItemsOnPlate;
    /**
     * Confirm button
     */
    private static Button button;
    /**
     * total of the order in dollars
     */
    private static Double total;

    /**
     * Called when the activity is first created
     */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_order_tab );
        numberOfItemsOnPlate = 0;

        footer = (TextView) findViewById( R.id.Footer );
        list = new ArrayList<String>();
        listView = (ListView) findViewById( R.id.list );
        adapter =
                new ArrayAdapter<String>( this, R.layout.list_view,
                        R.id.itemName, list );

        setListAdapter( adapter );

        button = (Button) findViewById( R.id.Button );
        button.setEnabled( false );

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

                        // recalculates the total
                        total = 0.0;
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
                        total = Double.valueOf( twoDForm.format( total ) );
                        footer.setText( "Total: $" + total );
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

        numberOfItemsOnPlate--;

        if ( numberOfItemsOnPlate <= 0 )
        {
            button.setEnabled( false );
        }

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
        button.setEnabled( true );
        numberOfItemsOnPlate++;

        // calculates the total
        total = 0.0;
        for ( int i = 0; i < list.size(); i++ )
        {
            total +=
                    Double.parseDouble( list.get( i ).substring(
                            list.get( i ).indexOf( "$" ) + 1 ) );
        }
        footer.setTextSize( 25 );
        DecimalFormat twoDForm = new DecimalFormat( "#.##" );
        total = Double.valueOf( twoDForm.format( total ) );

        footer.setText( "Total: $" + total );

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

                            /**
                             * Order confirmation dialogue
                             */
                            @SuppressWarnings( "unchecked" )
                            public void
                                    onClick( DialogInterface arg0, int arg1 )
                            {
                                // sends data to the server
                                SharedPreferences preference_ =
                                        getSharedPreferences(
                                                getString( R.string.pref_title_file ),
                                                Context.MODE_PRIVATE );
                                String userName =
                                        preference_
                                                .getString(
                                                        getString( R.string.pref_title_name ),
                                                        getString( R.string.pref_title_name ) );

                                orderToServer = new OrderTabAsyncTask();
                                orderToServer.execute( list.toString(),
                                        userName, total.toString() );

                                orderConfirmation();

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

    private void orderConfirmation()
    {
        // waits for the confirmation number to be given
        while ( orderToServer.getOrderNumber() == null )
        {
        }

        // // prepare the alert box
        // AlertDialog.Builder alertbox = new AlertDialog.Builder( OrderTab.this
        // );
        // // set the message to display
        // alertbox.setMessage( "Your order has been confirmed \n\nOrder ID: "
        // + orderToServer.getOrderNumber() );

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from( getBaseContext() );
        View promptsView = li.inflate( R.layout.dialog_order_confirmed, null );

        // prepare the alert box
        AlertDialog.Builder alertbox = new AlertDialog.Builder( OrderTab.this );

        alertbox.setView( promptsView );
        alertbox.setTitle( "Order Confirmed" );

        // Display text prompting the user that the order was confirmed
        final TextView confirmationTextView;
        confirmationTextView =
                (TextView) promptsView
                        .findViewById( R.id.orderconfirmationTextView );
        confirmationTextView.setText( "Your order has been confirmed" );

        // TODO: display the order number
        final TextView orderNumberTextView;
        orderNumberTextView =
                (TextView) promptsView.findViewById( R.id.confirmationTextView );
        orderNumberTextView.setText( "Order ID: "
                + orderToServer.getOrderNumber() );

        alertbox.setPositiveButton( "Ok", new DialogInterface.OnClickListener()
        {
            public void onClick( DialogInterface arg0, int arg1 )
            {
                // after order completion resets
                // the order
                list.clear();
                total = 0.0;
                adapter.notifyDataSetChanged();
                footer.setText( "" );
                button.setEnabled( false );
                numberOfItemsOnPlate = 0;
            }
        } );
        alertbox.show();
    }

}
