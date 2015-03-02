package negabur.app.f1team.formula1team.controlador;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import negabur.app.f1team.formula1team.R;
import negabur.app.f1team.formula1team.dao.TeamSQLConversor;
import negabur.app.f1team.formula1team.dao.TeamSQLiteHelper;
import negabur.app.f1team.formula1team.model.Team;


public class MainActivity extends ActionBarActivity implements SearchView.OnQueryTextListener {

    private ListView lstTeams;
    private ArrayList<Team> teamsInformation = new ArrayList<Team>();
    private Adapter adapter;
    private SearchView searchView;
    private TeamSQLConversor tsc;
    private SQLiteDatabase bd;
    private TeamSQLiteHelper db;
    private TextView txtNoTeam;
    private Toast alert;
    private int codi;

    private boolean restore = false;

    private static int ADD_TEAM = 1;
    private static int EDIT_TEAM = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new TeamSQLiteHelper(this, "formulaone.db", null, 2);
        bd = db.getWritableDatabase();
        tsc = new TeamSQLConversor(db);


        lstTeams = (ListView) findViewById(R.id.lstTeam);

        txtNoTeam = (TextView) findViewById(R.id.txtNoTeams);

        if (db != null) {
            refreshNewDate();
            db.close();
        }

        registerForContextMenu(lstTeams);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {

            case R.id.menu_item_new:
                Intent nouEquip = new Intent(getBaseContext(), NewTeam.class);
                startActivityForResult(nouEquip, ADD_TEAM);
                break;

            case R.id.menu_item_restore:
                codi = 1;
                confirmarRestaurar(codi);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.getFilter().filter(s);
        return false;
    }

    /**
     * Mètode que s'executa al tornar a l'activitat un cop fet un startOnActivityResult
     * Aquest mètode comprova si volem eliminar o editar un equip per tal de realitzar els canvis necessaris
     * i actualitza el listView
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADD_TEAM && resultCode == RESULT_OK) {

            Team newTeam = (Team) data.getExtras().getSerializable("newTeam");

            if (tsc.save(newTeam) != -1) {
                refreshNewDate();
                alert = Toast.makeText(getApplicationContext(),
                        "S'ha afegit un equip correctament", Toast.LENGTH_LONG);
            }

        } else if (requestCode == EDIT_TEAM && resultCode == 2) {
            Team newTeam = (Team) data.getExtras().getSerializable("newTeam");

            if (tsc.update(newTeam)) {
                refreshNewDate();
                alert = Toast.makeText(getApplicationContext(),
                        "Canvis guardats correctament", Toast.LENGTH_LONG);
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Metode que refresca la llista
     */
    public void refreshNewDate() {
        codi = 2;
        teamsInformation = tsc.getAllArr();
        if (teamsInformation.isEmpty()) {

            txtNoTeam.setVisibility(txtNoTeam.VISIBLE);
            lstTeams.setVisibility(lstTeams.INVISIBLE);
            confirmarRestaurar(codi);


        } else {
            txtNoTeam.setVisibility(txtNoTeam.INVISIBLE);
            lstTeams.setVisibility(lstTeams.VISIBLE);

            adapter = new Adapter(this, teamsInformation);

            lstTeams.setAdapter(adapter);
            lstTeams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getBaseContext(), TeamDetail.class);
                    Bundle b = new Bundle();
                    b.putSerializable("team", adapter.getItem(position));
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Opcions");
        menu.setHeaderIcon(R.mipmap.ic_action_settings);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.action_edit:
                confirmacioEdicio(info);
                return true;
            case R.id.action_delete:
                confirmacioEliminar(info);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * Mètode  que ens mostra un diàleg de confirmació  per eliminar un equip
     * @param info
     */
    public void confirmacioEliminar(AdapterView.AdapterContextMenuInfo info) {
        //Dialeg
        final AdapterView.AdapterContextMenuInfo infoK = info;
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setTitle("Confirmació d'eliminar");
        //Assignant missatge
        builder1.setMessage("Estàs segur que vols eliminar " + teamsInformation.get(infoK.position).getName() + " de la teva llista d'equips?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        tsc.remove(teamsInformation.get(infoK.position));
                        alert = Toast.makeText(getApplicationContext(),
                                "Has eliminat l'equip " + teamsInformation.get(infoK.position).getName() + " de forma correcta", Toast.LENGTH_LONG);
                        alert.show();
                        refreshNewDate();
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        alert = Toast.makeText(getApplicationContext(),
                                "Has decidit no eliminar l'equip " + teamsInformation.get(infoK.position).getName(), Toast.LENGTH_LONG);
                        alert.show();
                    }
                });

        AlertDialog confirmMessage = builder1.create();
        confirmMessage.show();

    }

    /**
     * Mètode  que ens mostra un diàleg de confirmació  per editar  l'equip
     * @param info
     */
    public void confirmacioEdicio(AdapterView.AdapterContextMenuInfo info) {
        //Dialeg
        final AdapterView.AdapterContextMenuInfo infoK = info;
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setTitle("Confirmació d'edició");
        //Assignant missatge
        builder1.setMessage("Estàs segur que vols editar " + teamsInformation.get(infoK.position).getName() + "?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent edicioEquip = new Intent(getBaseContext(), NewTeam.class);
                        Bundle bundTeam = new Bundle();
                        bundTeam.putSerializable("editTeam", teamsInformation.get(infoK.position));
                        edicioEquip.putExtras(bundTeam);
                        startActivityForResult(edicioEquip, EDIT_TEAM);
                        refreshNewDate();
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        alert = Toast.makeText(getApplicationContext(),
                                "Has decidit no editar l'equip " + teamsInformation.get(infoK.position).getName(), Toast.LENGTH_LONG);
                        alert.show();
                    }
                });

        AlertDialog confirmMessage = builder1.create();
        confirmMessage.show();
    }

    /**
     * Mètode  que ens mostra un diàleg de confirmació  per restaurar un equip
     * @param codi
     */
    public void confirmarRestaurar(int codi) {
        String message;

        if(codi == 1){
            message="Estàs segur que vols restaurar els equips per defecte? Perdràs tots els canvis realitzats";
        }
        else{
            message = "Sembla que en aquests moments no hi ha cap equip, vols restaurar els valors per defecte?";
        }
        //Dialeg
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setTitle("Confirmació de Restaurar");
        //Assignant missatge
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        tsc.removeAll();

                        bd =db.getWritableDatabase();
                        db.inserirValors(bd);

                        alert = Toast.makeText(getApplicationContext(),
                                "Has decidit restaurar els valors per defecte", Toast.LENGTH_LONG);
                        alert.show();
                        teamsInformation = tsc.getAllArr();


                        refreshNewDate();
                        dialog.cancel();
                        db.close();

                    }
                });
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        alert = Toast.makeText(getApplicationContext(),
                                "Has decidit no realitzar la restauració", Toast.LENGTH_LONG);
                        alert.show();
                    }
                });

        AlertDialog confirmMessage = builder1.create();
        confirmMessage.show();
    }


}
