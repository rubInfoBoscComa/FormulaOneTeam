package negabur.app.f1team.formula1team.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import negabur.app.f1team.formula1team.R;
import negabur.app.f1team.formula1team.model.Team;

/**
 * Created by Ruben on 6/2/15.
 */
public class TeamDetail extends ActionBarActivity {

    Team informationTeam;
    private ImageTool imgTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.imgTool = new ImageTool();

        //Activar la "fletxeta" del menú per tirar enrere
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imgTeamDetail = (ImageView) findViewById(R.id.imgTeamDetail);
        TextView lblLocation = (TextView) findViewById(R.id.lblLocation);
        TextView lblBorn = (TextView) findViewById(R.id.lblBorn);
        TextView lblChampionShip = (TextView) findViewById(R.id.lblChampionShip);
        TextView lblVictory = (TextView) findViewById(R.id.lblVictory);
        TextView lblPilotOne = (TextView) findViewById(R.id.lblCurrentPilotOne);
        TextView lblPilotSecond = (TextView) findViewById(R.id.lblCurrentPilotSecond);
        ImageView imgTwitterLogo = (ImageView) findViewById(R.id.imgTwitter);


        informationTeam = (Team) getIntent().getExtras().getSerializable("team");
        imgTeamDetail.setImageBitmap(this.imgTool.getPhoto(informationTeam.getImageID()));
        lblLocation.setText(informationTeam.getLocation());
        lblBorn.setText(Integer.toString(informationTeam.getBorn()));
        lblChampionShip.setText(Integer.toString(informationTeam.getChampionships()));
        lblVictory.setText(Integer.toString(informationTeam.getWins()));
        lblPilotOne.setText(informationTeam.getPilotOne());
        lblPilotSecond.setText(informationTeam.getPilotSecond());
        imgTwitterLogo.setImageResource(R.drawable.twitterlogo);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private ShareActionProvider mShareActionProvider;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setShareIntent(getDefaultIntent());
        // Return true to display menu
        return true;
    }

    private Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Hola! et passo informació sobre l'equip " + informationTeam.getName().toString() + " de Formula 1.\n"
                + "·Fundat: " + informationTeam.getBorn() + "\n"
                + "·Seu: " + informationTeam.getLocation().toString() + "\n"
                + "·Campionats: " + informationTeam.getChampionships() + "\n"
                + "·Victòries: " + informationTeam.getWins() + "\n"
                + "·Pilots: \n"
                + "     -" + informationTeam.getPilotOne().toString() + "\n"
                + "     -" + informationTeam.getPilotSecond().toString());
        return intent;
    }
}
