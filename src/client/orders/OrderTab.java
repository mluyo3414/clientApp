package client.orders;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

@SuppressWarnings( { "unused", "unchecked" } )
public class OrderTab extends ListActivity
{

    /**
     * controls process flow 1. paypal 2. send to server 3. confirm order
     */
    public static int nextStep;

    /**
     * async task sends data to the server
     */
    private static OrderTabAsyncTask orderToServer;

    // ////////////////
    // layout items //
    // ///////////////
    /**
     * Instance of the list view
     */
    private static ListView listView;
    /**
     * Footer for the total
     */
    private static TextView footer;
    /**
     * Confirm button
     */
    private static Button button;

    // //////////////
    // containers //
    // /////////////
    /**
     * Items entered by the user is stored in this ArrayList variable
     */
    private static ArrayList<String> list;
    /**
     * Declaring an ArrayAdapter to set items to ListView
     */
    private static ArrayAdapter<String> adapter;

    /**
     * alter confirming an addition to your plate
     */
    private AlertDialog.Builder alertbox;

    // /////////////////
    // number values //
    // ////////////////
    /**
     * current item number selected
     */
    private int currentNumber;
    /**
     * number of items in the order
     */
    private static int numberOfItemsOnPlate;
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

        // sets up layout
        setContentView( R.layout.activity_order_tab );
        button = (Button) findViewById( R.id.Button );
        button.setEnabled( false );
        footer = (TextView) findViewById( R.id.Footer );
        listView = (ListView) findViewById( R.id.list );
        numberOfItemsOnPlate = 0;

        // sets up adapter
        list = new ArrayList<String>();
        adapter =
                new ArrayAdapter<String>( this, R.layout.list_view,
                        R.id.itemName, list );
        setListAdapter( adapter );

        onConfirmClick();

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

        calculateTotal();
    }

    /**
     * When confirm button is clicked
     */
    public void onConfirmClick()
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
                            // When order is confirmed
                            public void
                                    onClick( DialogInterface arg0, int arg1 )
                            {
                                OrderTab.this.sendToPaypal();
                                OrderTab.this.sendToServer();
                            }
                        } );

                // set a negative/no button and create a listener
                alertbox.setNegativeButton( "No", null );
                alertbox.show();
            }
        } );
    }

    /**
     * Settings for item that are removed from the list
     */
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
                    public void onClick( DialogInterface arg0, int arg1 )
                    {
                        list.remove( currentNumber );
                        adapter.notifyDataSetChanged();

                        Toast.makeText( getApplicationContext(),
                                "The item was removed from your plate",
                                Toast.LENGTH_SHORT ).show();

                        numberOfItemsOnPlate--;
                        if ( numberOfItemsOnPlate <= 0 )
                        {
                            button.setEnabled( false );
                        }
                        calculateTotal();
                    }
                } );

        // set a negative/no button and create a listener
        alertbox.setNegativeButton( "No", new DialogInterface.OnClickListener()
        {
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
     * sends order total to paypal for payment
     */
    private void sendToPaypal()
    {
        Intent in = new Intent( OrderTab.this, PaypalPaymentActivity.class );
        in.putExtra( "orderTotal", total );
        OrderTab.this.startActivity( in );
    }

    /**
     * sends order and username to server after payment information has been
     * sent to paypal
     */
    private void sendToServer()
    {
        SharedPreferences preference_ =
                getSharedPreferences( getString( R.string.pref_title_file ),
                        Context.MODE_PRIVATE );
        String userName =
                preference_.getString( getString( R.string.pref_title_name ),
                        getString( R.string.pref_title_name ) );

        orderToServer = new OrderTabAsyncTask();
        orderToServer.execute( list.toString(), userName, total.toString() );
    }

    /**
     * confirms the order after payment has been received and order has been
     * sent to the server
     */
    private void orderConfirmation()
    {
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

        final TextView orderNumberTextView;
        orderNumberTextView =
                (TextView) promptsView.findViewById( R.id.confirmationTextView );
        orderNumberTextView.setText( "Order ID: "
                + orderToServer.getOrderNumber() );

        alertbox.setPositiveButton( "Ok", new DialogInterface.OnClickListener()
        {
            // after order completion resets the order
            public void onClick( DialogInterface arg0, int arg1 )
            {

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

    /**
     * recalculates total and displays it
     */
    private void calculateTotal()
    {
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
     * When list view item is clicked
     */
    @Override
    protected void onListItemClick( ListView l, View v, int position, long id )
    {
        currentNumber = position;

        removeItem();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if ( nextStep == 2 )
        {
            orderConfirmation();
            nextStep = 0;
        }
    }
}