package negabur.app.f1team.formula1team.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import negabur.app.f1team.formula1team.model.Team;

/**
 * Created by Ruben on 22/2/15.
 */
public class TeamSQLConversor {

    private TeamSQLiteHelper helper;

    /**
     * Consructor per defecte
     */
    public TeamSQLConversor() {

    }

    /**
     * Constructor amb paràmetres
     *
     * @param helper l'ajudant de la BD de Titulars
     */
    public TeamSQLConversor(TeamSQLiteHelper helper) {
        this.helper = helper;
    }

    /**
     * Desa un nou titular a la taula
     *
     * @param team l'objecte a desar
     * @return l'id del nou titular desat
     */
    public long save(Team team) {
        long index = -1;
        // s'agafa l'objecte base de dades en mode escriptura
        SQLiteDatabase db = helper.getWritableDatabase();
        // es crea un objecte de diccionari (clau,valor) per indicar els valors a afegir
        ContentValues dades = new ContentValues();

        dades.put("name", team.getName());
        dades.put("location", team.getLocation());
        dades.put("pilotOne", team.getPilotOne());
        dades.put("pilotSecond", team.getPilotSecond());
        dades.put("championship", team.getChampionships());
        dades.put("born", team.getBorn());
        dades.put("wins", team.getWins());
        dades.put("image_source", team.getImageID());

        try {
            index = db.insertOrThrow("Team", null, dades);
            // volem veure en el log el que passa
            Log.i("Team", dades.toString() + " afegit amb codi " + index);
        } catch (Exception e) {
            // volem reflectir en ellog que hi ha hagut un error
            Log.e("Team", e.getMessage());
        }
        return index;
    }

    public boolean update(Team team){
        // s'agafa l'objecte base de dades en mode escriptura
        SQLiteDatabase db = helper.getWritableDatabase();
        // es crea un objecte de diccionari (clau,valor) per indicar els valors a afegir
        ContentValues dades = new ContentValues();

        dades.put("name", team.getName());
        dades.put("location", team.getLocation());
        dades.put("pilotOne", team.getPilotOne());
        dades.put("pilotSecond", team.getPilotSecond());
        dades.put("championship", team.getChampionships());
        dades.put("born", team.getBorn());
        dades.put("wins", team.getWins());
        dades.put("image_source", team.getImageID());

        try {
            db.update("Team", dades,"codi " + "=" + team.getCodi(),null);
            // volem veure en el log el que passa
            Log.i("Team", dades.toString() + " afegit amb codi " + team.getCodi());
        } catch (Exception e) {
            // volem reflectir en ellog que hi ha hagut un error
            Log.e("Team", e.getMessage());
        }
        return true;
    }

    /**
     * Retorna un cursor amb totes les dades de la taula
     *
     * @return
     */
    public Cursor getAll() {
        SQLiteDatabase db = helper.getReadableDatabase();

        return db.query(true, "Titulars",
                new String[]{"codi", "titol", "subtitol"},
                null, null, null, null, null, null);
    }


    public ArrayList<Team> getAllArr() {
        SQLiteDatabase db = helper.getReadableDatabase();
        //el primer true diu si es fa distinc o no
        Cursor c = db.query(true, "Team",
                new String[]{"codi", "name", "location", "pilotOne", "pilotSecond", "championship", "born", "wins", "image_source"},
                null, null, null, null, null, null);

        ArrayList<Team> llista = new ArrayList<Team>();
        while (c.moveToNext()) {
            llista.add(new Team(c.getInt(c.getColumnIndex("codi")), c.getString(c.getColumnIndex("name")),
                    c.getString(c.getColumnIndex("location")), c.getString(c.getColumnIndex("pilotOne")), c.getString(c.getColumnIndex("pilotSecond")),
                    c.getInt(c.getColumnIndex("championship")), c.getInt(c.getColumnIndex("born")), c.getInt(c.getColumnIndex("wins")), c.getBlob(c.getColumnIndex("image_source"))));
        }
        db.close();
        return llista;

        // Si hi ha moltes dades és millor retornar el cursor perque sino faríem el recorregut aquí
        // i a l'adapter. Es feria servir sobretot si hem de mostrar les dades a un listview
    }


    /**
     * Esborra el titular passat per paràmetre
     *
     * @param t el titular a esborrar
     * @return la quantitat de titulars eliminats
     */
    public boolean remove(Team t) {
        // obtenir l'objecte BD en mode esriptura
        SQLiteDatabase db = helper.getWritableDatabase();

        return db.delete("Team", "codi=" + t.getCodi(), null) > 0;
    }

    /**
     * Esborra tots els titulars de la taula
     *
     * @return
     */
    public boolean removeAll() {
        // obtenir l'objecte BD en mode escriptura
        SQLiteDatabase db = helper.getWritableDatabase();

        return db.delete("Team", null, null) > 0;
    }

}
