package client.orders;

import java.math.BigDecimal;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

/**
 * @author Modified by James Dagres
 * @author Modified by Carl Barbee
 * @author Modified by Matt Luckam
 * @author Modified by Miguel Suarez
 * @version Nov 8, 2013
 * 
 *          A modified PayPal class from their sample class found here:
 *          https://github.com/paypal/PayPal-Android-SDK
 */
public class PaypalPaymentActivity extends Activity
{

    // set to PaymentActivity.ENVIRONMENT_PRODUCTION to move real money.
    // set to PaymentActivity.ENVIRONMENT_SANDBOX to use your test credentials
    // from https://developer.paypal.com
    // set to PaymentActivity.ENVIRONMENT_NO_NETWORK to kick the tires without
    // communicating to PayPal's servers.
    private static final String CONFIG_ENVIRONMENT =
            PaymentActivity.ENVIRONMENT_SANDBOX;

    // note that these credentials will differ between live & sandbox
    // environments.
    private static final String CONFIG_CLIENT_ID =
            "AeI7FhD9x6AfHXszMk5gkKakasnK5mfrE0WRfqUOfL3KzZQ0oksedTgj_gaj";
    // when testing in sandbox, this is likely the -facilitator email address.
    private static final String CONFIG_RECEIVER_EMAIL =
            "mig.suarez49-facilitator@gmail.com";

    // private static OrderTab orderTab;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        // setContentView( R.layout.activity_payment );

        Intent intent = new Intent( this, PayPalService.class );

        intent.putExtra( PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT,
                CONFIG_ENVIRONMENT );
        intent.putExtra( PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID );
        intent.putExtra( PaymentActivity.EXTRA_RECEIVER_EMAIL,
                CONFIG_RECEIVER_EMAIL );

        startService( intent );

        Intent in = getIntent();
        Bundle b = in.getExtras();
        if ( b != null )
        {
            String total = b.get( "orderTotal" ).toString();
            // orderTab = (OrderTab) b.get( "instance" );
            this.onBuyPressed( total );
        }
    }

    public void onBuyPressed( String total ) // View pressed
                                             // )
    {
        PayPalPayment thingToBuy =
                new PayPalPayment( new BigDecimal( total ), "USD",
                        "FoodNow order" );

        Intent intent = new Intent( this, PaymentActivity.class );

        intent.putExtra( PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT,
                CONFIG_ENVIRONMENT );
        intent.putExtra( PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID );
        intent.putExtra( PaymentActivity.EXTRA_RECEIVER_EMAIL,
                CONFIG_RECEIVER_EMAIL );

        // It's important to repeat the clientId here so that the SDK has it if
        // Android restarts your
        // app midway through the payment UI flow.
        intent.putExtra( PaymentActivity.EXTRA_CLIENT_ID,
                "credential-from-developer.paypal.com" );
        intent.putExtra( PaymentActivity.EXTRA_PAYER_ID,
                "your-customer-id-in-your-system" );
        intent.putExtra( PaymentActivity.EXTRA_PAYMENT, thingToBuy );

        startActivityForResult( intent, 0 );
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode,
            Intent data )
    {
        if ( resultCode == Activity.RESULT_OK )
        {
            PaymentConfirmation confirm =
                    data.getParcelableExtra( PaymentActivity.EXTRA_RESULT_CONFIRMATION );
            if ( confirm != null )
            {
                try
                {
                    Log.i( "paymentExample", confirm.toJSONObject()
                            .toString( 4 ) );

                    OrderTab.nextStep = 1;
                    this.finish();

                    // see
                    // https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                }
                catch ( JSONException e )
                {
                    Log.e( "paymentExample",
                            "an extremely unlikely failure occurred: ", e );
                }
            }
        }
        else if ( resultCode == Activity.RESULT_CANCELED )
        {
            Log.i( "paymentExample", "The user canceled." );
        }
        else if ( resultCode == PaymentActivity.RESULT_PAYMENT_INVALID )
        {
            Log.i( "paymentExample",
                    "An invalid payment was submitted. Please see the docs." );
        }
    }

    @Override
    public void onDestroy()
    {
        stopService( new Intent( this, PayPalService.class ) );
        super.onDestroy();
    }
}
