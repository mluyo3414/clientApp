package client.orders;

import android.app.DialogFragment;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodnow.R;

/**
 * @author James Dagres
 * @author Miguel Suarez
 * @author Carl "CAH" Barbee
 * @author Matt Luckam
 * 
 * @version Nov 8, 2013
 * 
 *          TODO: This dialog will be called when the client confirms sending an
 *          order to the server. It clears out all of the items in the plate
 *          (the shopping cart). It receives the unique order ID from the server
 *          and stores it in the clients profile
 * 
 *          TODO: current code implemented from:
 *          http://developer.android.com/reference
 *          /android/app/DialogFragment.html
 */
public class OrderSentFragment extends DialogFragment
{
    int mNum;

    /**
     * Create a new instance of MyDialogFragment, providing "num" as an
     * argument.
     */
    static OrderSentFragment newInstance( int num )
    {
        OrderSentFragment f = new OrderSentFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt( "num", num );
        f.setArguments( args );

        return f;
    }

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        mNum = getArguments().getInt( "num" );

        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        switch ( (mNum - 1) % 6 )
        {
        case 1:
            style = DialogFragment.STYLE_NO_TITLE;
            break;
        case 2:
            style = DialogFragment.STYLE_NO_FRAME;
            break;
        case 3:
            style = DialogFragment.STYLE_NO_INPUT;
            break;
        case 4:
            style = DialogFragment.STYLE_NORMAL;
            break;
        case 5:
            style = DialogFragment.STYLE_NORMAL;
            break;
        case 6:
            style = DialogFragment.STYLE_NO_TITLE;
            break;
        case 7:
            style = DialogFragment.STYLE_NO_FRAME;
            break;
        case 8:
            style = DialogFragment.STYLE_NORMAL;
            break;
        }
        switch ( (mNum - 1) % 6 )
        {
        case 4:
            theme = android.R.style.Theme_Holo;
            break;
        case 5:
            theme = android.R.style.Theme_Holo_Light_Dialog;
            break;
        case 6:
            theme = android.R.style.Theme_Holo_Light;
            break;
        case 7:
            theme = android.R.style.Theme_Holo_Light_Panel;
            break;
        case 8:
            theme = android.R.style.Theme_Holo_Light;
            break;
        }
        setStyle( style, theme );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState )
    {
        // View v = inflater.inflate( R.layout.fragment_dialog, container, false
        // );
        
        // TODO:
        // View tv = v.findViewById( R.id.text );
        // ((TextView) tv).setText( "Dialog #" + mNum + ": using style "
        // + getNameForNum( mNum ) );
        //
        // // Watch for button clicks.
        // Button button = (Button) v.findViewById( R.id.show );
        // button.setOnClickListener( new OnClickListener()
        // {
        // public void onClick( View v )
        // {
        // // When button is clicked, call up to owning activity.
        // ((FragmentDialog) getActivity()).showDialog();
        // }
        // } );
        //
        // return v;

        return new View( null ); // HACK: REMOVE LATER!!
    }
}
