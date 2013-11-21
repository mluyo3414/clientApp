package client.menu;

import client.orders.OrderTab;

import com.example.foodnow.R;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MenuTab extends Activity
{
    /**
     * list of menu items
     */
    private ListView list;

    /**
     * instance of order tab to send items to the order
     */
    private OrderTab orderTab;

    /**
     * alter confirming an addition to your plate
     */
    private AlertDialog.Builder alertbox;

    /**
     * current item number selected
     */
    private int currentNumber;

    /**
     * list of items on the menu
     */
    private String[] web = { "Grilled Chicken Burrito - $6.79",
            "Pulled Pork Burrito - $7.09", "Shredded Beef Burrito - $7.49",
            "Grilled Steak Burrito - $7.49",
            "Seasoned Ground Beef Burrito - $7.09",
            "Vegetarian Burrito - $6.79" };

    /**
     * images of items on the menu
     */
    private Integer[] imageId = { R.drawable.chicken_burrito,
            R.drawable.pulled_pork_burrito, R.drawable.shredded_beef_burrito,
            R.drawable.steak_burrito, R.drawable.ground_beef_burrito,
            R.drawable.veggie_burrito };

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        orderTab = new OrderTab();
        currentNumber = 0;

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_menu_tab );

        this.addToPlate();

        this.displayMenu();

    }

    private void displayMenu()
    {
        // displays menu list
        CustomList adapter = new CustomList( MenuTab.this, web, imageId );
        list = (ListView) findViewById( R.id.list );
        list.setAdapter( adapter );
        list.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick( AdapterView<?> parent, View view,
                    int position, long id )
            {
                // display box
                MenuTab.this.currentNumber = position;
                alertbox.show();
            }
        } );
    }

    private void addToPlate()
    {
        // prepare the alert box
        alertbox = new AlertDialog.Builder( MenuTab.this );
        // set the message to display
        alertbox.setMessage( "¿Add to Plate?" );
        // set a positive/yes button and create a listener
        alertbox.setPositiveButton( "Yes",
                new DialogInterface.OnClickListener()
                {
                    // //////////////////////////////////////////
                    // do something when the YES button is clicked
                    // //////////////////////////////////////////
                    public void onClick( DialogInterface arg0, int arg1 )
                    {
                        MenuTab.this.orderTab
                                .addItems( MenuTab.this.web[MenuTab.this.currentNumber] );

                        Toast.makeText( getApplicationContext(),
                                "The item was added to your plate",
                                Toast.LENGTH_SHORT ).show();
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
                        "The item was NOT added to your plate",
                        Toast.LENGTH_SHORT ).show();
            }
        } );

    }
}
