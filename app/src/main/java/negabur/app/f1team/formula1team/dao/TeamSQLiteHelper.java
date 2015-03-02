package negabur.app.f1team.formula1team.dao;

/**
 * Created by Ruben on 20/2/15.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import negabur.app.f1team.formula1team.controlador.ImageTool;


public class TeamSQLiteHelper extends SQLiteOpenHelper {
    // Sentència SQL per crear la taula de Titulars
    private final String SQL_CREATE_TEAM = "CREATE TABLE \"team\" (\"codi\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , \"name\" VARCHAR, \"location\" VARCHAR, \"pilotOne\" VARCHAR, \"pilotSecond\" VARCHAR, \"championship\" INTEGER, \"born\" INTEGER, \"wins\" INTEGER, \"image_source\" BLOB)";
    private Context context;
    private ImageTool imgTool;

    /**
     * Constructor amb paràmetres
     *
     * @param context el context de l'aplicació
     * @param nom     el nom de la base de dades a crear
     * @param factory s'utilitza per crear objectes cursor, o null per defecte
     * @param versio  número de versió de la BD. Si és més gran que la versió actual, es farà un
     *                Upgrade; si és menor es farà un Downgrade
     */
    public TeamSQLiteHelper(Context context, String nom, CursorFactory factory, int versio) {
        super(context, nom, factory, versio);
        this.context = context;
        this.imgTool = new ImageTool();
    }

    /**
     * Event que es produeix quan s'ha de crear la BD
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // S'executen les sentències SQL de creació de la BD
        db.execSQL(SQL_CREATE_TEAM);
        inserirValors(db);

    }


    public void open(){
        getWritableDatabase();
    }


    /**
     * Metode que ens insereix els valors per defecte a la DB
     * @param db
     */
    public void inserirValors(SQLiteDatabase db) {



        String sqlTeam = "Insert into Team (name,location,pilotOne,pilotSecond,championship,born,wins, image_source) values (?,?,?,?,?,?,?,?)";
        SQLiteStatement insertTeam = db.compileStatement(sqlTeam);

        //Insert equip Mclaren Honda
        insertTeam.clearBindings();
        insertTeam.bindString(1, "McLaren-Honda");
        insertTeam.bindString(2, "Woking, Surrey, Anglaterra");
        insertTeam.bindString(3, "Fernando Alonso");
        insertTeam.bindString(4, "Jenson Button");
        insertTeam.bindLong(5, 8);
        insertTeam.bindLong(6, 1963);
        insertTeam.bindLong(7, 182);
        insertTeam.bindBlob(8, this.imgTool.getBytes(this.imgTool.convertImageToByte("mclarenlogo.png", this.context)));
        insertTeam.executeInsert();

        //Insert equip Ferrari
        insertTeam.clearBindings();
        insertTeam.bindString(1, "Scuderia Ferrari");
        insertTeam.bindString(2, "Maranello, Módena, Itàlia");
        insertTeam.bindString(3, "Kimi Räikkönen");
        insertTeam.bindString(4, "Sebastian  Vettel");
        insertTeam.bindLong(5, 16);
        insertTeam.bindLong(6, 1947);
        insertTeam.bindLong(7, 221);
        insertTeam.bindBlob(8, this.imgTool.getBytes(this.imgTool.convertImageToByte("ferrarilogo.png", this.context)));
        insertTeam.executeInsert();

        //Insert equip RedBull
        insertTeam.clearBindings();
        insertTeam.bindString(1, "Red Bull Racing");
        insertTeam.bindString(2, "Milton keynes, Anglaterra");
        insertTeam.bindString(3, "Daniel Ricciardo");
        insertTeam.bindString(4, "Daniil Kvyat");
        insertTeam.bindLong(5, 4);
        insertTeam.bindLong(6, 2005);
        insertTeam.bindLong(7, 50);
        insertTeam.bindBlob(8, this.imgTool.getBytes(this.imgTool.convertImageToByte("redbulllogo.png", this.context)));
        insertTeam.executeInsert();

        //Insert equip Mercedes
        insertTeam.clearBindings();
        insertTeam.bindString(1, "Mercedes AMG F1 Team");
        insertTeam.bindString(2, "Stuttgart, Alemania");
        insertTeam.bindString(3, "Lewis Hamilton");
        insertTeam.bindString(4, "Nico Rosberg");
        insertTeam.bindLong(5, 1);
        insertTeam.bindLong(6, 1954);
        insertTeam.bindLong(7, 28);
        insertTeam.bindBlob(8, this.imgTool.getBytes(this.imgTool.convertImageToByte("mercedeslogo.png", this.context)));
        insertTeam.executeInsert();

        //Insert equip Force India
        insertTeam.clearBindings();
        insertTeam.bindString(1, "Sahara Force india");
        insertTeam.bindString(2, "Northamptonshire, Anglaterra");
        insertTeam.bindString(3, "Nico Hülkenberg");
        insertTeam.bindString(4, "Sergio Pérez");
        insertTeam.bindLong(5, 0);
        insertTeam.bindLong(6, 2007);
        insertTeam.bindLong(7, 0);
        insertTeam.bindBlob(8, this.imgTool.getBytes(this.imgTool.convertImageToByte("forceindialogo.png", this.context)));
        insertTeam.executeInsert();

        //Insert equip Sauber
        insertTeam.clearBindings();
        insertTeam.bindString(1, "Sauber F1 Team");
        insertTeam.bindString(2, "Hinwil, Suïssa");
        insertTeam.bindString(3, "Marcus Ericsson");
        insertTeam.bindString(4, "Felipe Nasr");
        insertTeam.bindLong(5, 0);
        insertTeam.bindLong(6, 1993);
        insertTeam.bindLong(7, 0);
        insertTeam.bindBlob(8, this.imgTool.getBytes(this.imgTool.convertImageToByte("sauberlogo.png", this.context)));
        insertTeam.executeInsert();

        //Insert equip Lotus
        insertTeam.clearBindings();
        insertTeam.bindString(1, "Lotus F1 Team");
        insertTeam.bindString(2, "Enstone, Anglaterra");
        insertTeam.bindString(3, "Romain Grosjean");
        insertTeam.bindString(4, "Pastor Maldonado");
        insertTeam.bindLong(5, 0);
        insertTeam.bindLong(6, 2012);
        insertTeam.bindLong(7, 2);
        insertTeam.bindBlob(8, this.imgTool.getBytes(this.imgTool.convertImageToByte("lotuslogo.png", this.context)));
        insertTeam.executeInsert();

        //Insert equip Williams
        insertTeam.clearBindings();
        insertTeam.bindString(1, "Williams Racing");
        insertTeam.bindString(2, "Grove, Oxfordshire, Anglaterra");
        insertTeam.bindString(3, "Felipe Massa");
        insertTeam.bindString(4, "Valtteri Bottas");
        insertTeam.bindLong(5, 9);
        insertTeam.bindLong(6, 1997);
        insertTeam.bindLong(7, 114);
        insertTeam.bindBlob(8, this.imgTool.getBytes(this.imgTool.convertImageToByte("williamslogo.png", this.context)));
        insertTeam.executeInsert();

        //Insert equip Toro Rosso
        insertTeam.clearBindings();
        insertTeam.bindString(1, "Scuderia Toro rosso");
        insertTeam.bindString(2, "Faenza, Itàlia");
        insertTeam.bindString(3, "Max Verstappen");
        insertTeam.bindString(4, "Carlos Sainz Jr");
        insertTeam.bindLong(5, 0);
        insertTeam.bindLong(6, 2005);
        insertTeam.bindLong(7, 1);
        insertTeam.bindBlob(8, this.imgTool.getBytes(this.imgTool.convertImageToByte("tororossologo.png", this.context)));
        insertTeam.executeInsert();




    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int versioAnterior, int versioNova) {

    }
}