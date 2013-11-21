package client.home;

import com.example.foodnow.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * 
 * @author Miguel S This activity displays the menu with the options to pick
 *         from as well as the option to add a name to the client.orders
 */
public class UserInput extends Activity
{

	Spinner orderSpinner;
	EditText name;
	String nameString;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );

		setContentView( R.layout.activity_user_input );

		orderSpinner = (Spinner) findViewById( R.id.productSpinner );
		name = (EditText) findViewById( R.id.Name );
		orderSpinner.setEnabled( false );
		// enables spinner once user starts typing his/her name
		name.addTextChangedListener( new TextWatcher()
		{

			@Override
			public void afterTextChanged( Editable s )
			{
				// displays spinner once user types
				waitListView();
			}

			@Override
			public void beforeTextChanged( CharSequence s, int start,
					int count, int after )
			{
			}

			@Override
			public void onTextChanged( CharSequence s, int start, int before,
					int count )
			{

			}

		} );

	}

	/**
	 * This method enables the spinner and waits for the user to pick a
	 * selection.
	 */
	public void waitListView()
	{
		orderSpinner.setEnabled( true );
		orderSpinner
				.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener()
				{
					// triggers new activity once option is selected and the
					// name is not empty

					@SuppressLint( "NewApi" )
					@Override
					public void onItemSelected( AdapterView<?> parent,
							View view, int position, long id )
					{

						Object item = orderSpinner.getItemAtPosition( position );
						String order = item.toString();
						//retrieving name and sending it to the next activity
						nameString = name.getText().toString();
						Intent in =
								new Intent( getApplicationContext(),
										CurrentConnected.class );
						// start intent with client.orders and name
						in.putExtra( "client.orders", order );
						in.putExtra( "name", nameString );
						// start new activity
						if ( nameString.isEmpty() == false )
						{
							//gives information to the user..
							Toast.makeText( getApplicationContext(),
									"Almost done...", Toast.LENGTH_SHORT )
									.show();

							startActivity( in );
						}
						else
						{

							Toast.makeText( getApplicationContext(),
									"Please Enter a Name", Toast.LENGTH_SHORT )
									.show();

						}

					}

					public void onNothingSelected( AdapterView<?> parent )
					{
						// Do nothing...just wait
					}
				} );

	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.user_input, menu );
		return true;
	}

}
