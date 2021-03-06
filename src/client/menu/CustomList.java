package client.menu;

import com.example.foodnow.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Miguel Suarez
 * @author James Dagres
 * @author Carl Barbee
 * @author Matt Luckam
 * 
 *         Custom static listview. Each list item contains both an image and
 *         text.
 */
public class CustomList extends ArrayAdapter<String>
{

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;

    /**
     * 
     * sets the context and the string and image arrays to be displayed in the
     * list
     * 
     * @param context
     *            class the list will be displayed in
     * @param web
     *            array strings to be displayed in the list
     * @param imageId
     *            array images to be displayed in the list
     */
    public CustomList( Activity context, String[] web, Integer[] imageId )
    {
        super( context, R.layout.list_single, web );
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }

    @Override
    public View getView( int position, View view, ViewGroup parent )
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate( R.layout.list_single, null, true );
        TextView txtTitle = (TextView) rowView.findViewById( R.id.txt );

        ImageView imageView = (ImageView) rowView.findViewById( R.id.img );
        txtTitle.setText( web[position] );

        imageView.setImageResource( imageId[position] );
        return rowView;
    }
}