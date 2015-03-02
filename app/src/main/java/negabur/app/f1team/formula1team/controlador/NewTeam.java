package negabur.app.f1team.formula1team.controlador;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import negabur.app.f1team.formula1team.R;
import negabur.app.f1team.formula1team.dao.TeamSQLiteHelper;
import negabur.app.f1team.formula1team.model.Team;

/**
 * Created by Ruben on 25/2/15.
 */
public class NewTeam extends ActionBarActivity {

    private ImageView imageLogo;
    private Button btnImage;
    private EditText editNewName;
    private EditText editNewLocation;
    private EditText editNewBorn;
    private EditText editNewChampionships;
    private EditText editNewWins;
    private EditText editNewPilotOne;
    private EditText editNewPilotSecond;

    private byte[] imatgeArray;

    private Toast alert;

    private static int RESULT_LOAD_IMAGE = 1;

    private ImageTool imgTool;

    private TeamSQLiteHelper tsh;

    private Team newTeam;

    private boolean edit = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newteam);

        tsh = new TeamSQLiteHelper(this, "formulaone.db", null, 2);

        imgTool = new ImageTool();
        imageLogo = (ImageView) findViewById(R.id.imageLogo);
        btnImage = (Button) findViewById(R.id.btnUpImage);
        btnImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        editNewName = (EditText) findViewById(R.id.editNewName);
        editNewLocation = (EditText) findViewById(R.id.editNewLocation);
        editNewBorn = (EditText) findViewById(R.id.editNewBorn);
        editNewChampionships = (EditText) findViewById(R.id.editNewChampionships);
        editNewWins = (EditText) findViewById(R.id.editNewWins);
        editNewPilotOne = (EditText) findViewById(R.id.editNewPilotOne);
        editNewPilotSecond = (EditText) findViewById(R.id.editNewPilotSecond);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            String result;
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            if (cursor == null) {
                result = selectedImage.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
                cursor.close();
            }

            cursor.close();
            Bitmap imgLogo = this.imgTool.convertImageToByte(result);
            Bitmap resized = Bitmap.createScaledBitmap(imgLogo, 200, 46, true);

            imatgeArray = this.imgTool.getBytes(resized);

            imageLogo.setImageBitmap(resized);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        /**
         * Rep un intent i mira si existeix el paquet "ediTeam" per indicar que estem editant un equip i no afegint-ne un de nou
         */
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (getIntent().getExtras().getSerializable("editTeam") != null) {
                edit = true;
                newTeam = (Team) getIntent().getExtras().getSerializable("editTeam");
                update();
            }
        }
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_new, menu);

            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            switch (id) {

                case R.id.menu_new_ok:

                    String resultat = comprovarDades();
                    alert = Toast.makeText(getApplicationContext(),
                            resultat, Toast.LENGTH_LONG);

                    int codi = Activity.RESULT_OK;
                    int idTeam = -1;
                    //Si estem editant el id del equip no és el valor per defecte sino que he de recollir el seu valor
                    if (edit) {
                        codi = 2;
                        idTeam = newTeam.getCodi();

                    }
                    //Si les dades son correctes, (Controlades per expressions regulars) ens desarà l'equip
                    if (resultat.equals("Dades Correctes")) {
                        alert.show();
                        newTeam = new Team(idTeam, editNewName.getText().toString(), editNewLocation.getText().toString(), editNewPilotOne.getText().toString(),
                                editNewPilotSecond.getText().toString(), Integer.parseInt(editNewBorn.getText().toString()),
                                Integer.parseInt(editNewChampionships.getText().toString()), Integer.parseInt(editNewWins.getText().toString()),
                                imatgeArray);

                        Bundle bundTeam = new Bundle();
                        bundTeam.putSerializable("newTeam", newTeam);

                        Intent intent = new Intent();

                        intent.putExtras(bundTeam);


                        setResult(codi, intent);
                        edit = false;
                        finish();
                    } else {
                        alert.show();
                    }

                    break;
            }

            return super.onOptionsItemSelected(item);
        }

    /**
     * Mètode que comprova que les dades a emmagatzemmar siguin correctes
     * @return error o correcte
     */
    public String comprovarDades() {

        //Controlar si la imatge es null
        if (imatgeArray == null) {
            return "És obligatori afegir una imatge";
        }
        //Controlar si el text new name és correcte
        if (controlarDades(editNewName.getText().toString(), "^[A-Za-zÀàÈèÉéÒòÓóÚúÍíÑñäëïöüÄËÏÖÜ_\\-, ]{2,40}$")) {
            //Controlar si el text new Location és correcte
            if (controlarDades(editNewLocation.getText().toString(), "^[A-Za-zÀàÈèÉéÒòÓóÚúÍíÑñäëïöüÄËÏÖÜ_\\-, ]{2,40}$")) {
                //Controlar si el text New Born és correcte
                if (controlarDades(editNewBorn.getText().toString(), "\\d{4}")) {
                    //Controlar si el text New championships és correcte
                    if (controlarDades(editNewChampionships.getText().toString(), "\\d{1,4}")) {
                        //Controlar si el text New Wins és correcte
                        if (controlarDades(editNewWins.getText().toString(), "\\d{1,4}")) {
                            //Controlar si el text New Pilot One és correcte
                            if (controlarDades(editNewPilotOne.getText().toString(), "^[A-Za-zÀàÈèÉéÒòÓóÚúÍíÑñäëïöüÄËÏÖÜ_\\-, ]{2,40}$")) {
                                //Controlar si el text New Pilot Second és correcte
                                if (controlarDades(editNewPilotSecond.getText().toString(), "^[A-Za-zÀàÈèÉéÒòÓóÚúÍíÑñäëïöüÄËÏÖÜ_\\- ,]{2,40}$")) {
                                    return "Dades Correctes";
                                } else {
                                    return "El nom del segon pilot ha d'estar comprès per caràcters i amb una longitud entre 2 i 20";
                                }
                            } else {
                                return "El nom del primer pilot ha d'estar comprès per caràcters i amb una longitud entre 2 i 20";
                            }
                        } else {
                            return "El camp victòries ha de ser un valor numèric d'un màxim de quatre xifres i un mínim de 1";
                        }
                    } else {
                        return "El camp campionats ha de ser un valor numèric d'un màxim de quatre xifres i un mínim de 1";
                    }
                } else {
                    return "El camp naixament ha de ser un valor numèric de quatre xifres";
                }
            } else {
                return "El camp seu ha de ser una cadena de caràcters amb una longitud entre 2 i 30 caràcters";
            }
        } else {
            return "El camp nom ha de ser una cadena de caràcters amb una longitud entre 2 i 20 caràcters";
        }
    }

    /**
     * Mètode que controla que les dades introduïdes siguin correctes dintre
     * Del patró
     * @param cadena
     * @param pattern
     * @return
     */
    public boolean controlarDades(String cadena, String pattern) {

        CharSequence inputStr = cadena;

        Pattern patt = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = patt.matcher(inputStr);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * Estableix les dades de l'equip
     */
    public void update() {


        imageLogo.setImageBitmap(this.imgTool.getPhoto(newTeam.getImageID()));
        imatgeArray = newTeam.getImageID();
        editNewName.setText(newTeam.getName());
        editNewLocation.setText(newTeam.getLocation());
        editNewPilotOne.setText(newTeam.getPilotOne());
        editNewPilotSecond.setText(newTeam.getPilotSecond());
        editNewChampionships.setText("" + newTeam.getChampionships());
        editNewBorn.setText("" + newTeam.getBorn());
        editNewWins.setText("" + newTeam.getWins());


    }

}
