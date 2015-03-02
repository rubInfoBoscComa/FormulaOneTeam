package negabur.app.f1team.formula1team.controlador;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.util.Timer;
import java.util.TimerTask;

import negabur.app.f1team.formula1team.R;

/**
 * Created by Ruben on 10/2/15.
 */
public class Splash extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "rDLoDDysKVop84FGlFwEjdSlk";
    private static final String TWITTER_SECRET = "jNzRPizsDJYTNvm83hUiIhJWIvgzHaSdnKz1L4FRdiqhxOO9gn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_splash);


        ImageView imgLogo = (ImageView) findViewById(R.id.imgLogo);
        imgLogo.setImageResource(R.drawable.logof1);


        TimerTask nextScreen = new TimerTask() {
            @Override
            public void run() {

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);

                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
        };

        Timer inici = new Timer();
        inici.schedule(nextScreen, 4000);



    }


}
