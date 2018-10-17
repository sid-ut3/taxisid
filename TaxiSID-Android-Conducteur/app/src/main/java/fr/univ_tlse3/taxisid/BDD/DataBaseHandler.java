package fr.univ_tlse3.taxisid.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Insa on 07/01/2016.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "AppDB";
    public static final String TABLE_CODE = "codeTable";
    public static final String KEY_ID_CODE = "idCode";
    public static final String KEY_CODE_NAME = "nom";
    public static final String KEY_CODE_UTILISATION = "utilisation";
    public static final String[] COLONNES_CODE = {KEY_ID_CODE, KEY_CODE_NAME, KEY_CODE_UTILISATION };

    public static final String TABLE_HISTORIQUE = "historique";
    public static final String KEY_ID_HISTORIQUE = "idHistorique";
    public static final String KEY_CODE_MESSAGE = "message";
    public static final String KEY_CODE_DATE = "date";
    public static final String[] COLONNES_HISTORIQUE = {KEY_ID_HISTORIQUE, KEY_CODE_MESSAGE, KEY_CODE_DATE};


    protected static final String CREATION_TABLE_HISTORIQUE = "CREATE TABLE "
            + TABLE_HISTORIQUE + " ( "
            + KEY_ID_HISTORIQUE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_CODE_MESSAGE +" TEXT, " + KEY_CODE_DATE +" TEXT );";

    private static final String CREATION_TABLE_CODE =
            "CREATE TABLE " + TABLE_CODE + " ( "
                    + KEY_ID_CODE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_CODE_NAME +" TEXT, "
                    + KEY_CODE_UTILISATION +" TEXT );";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATION_TABLE_HISTORIQUE);
        db.execSQL(CREATION_TABLE_CODE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_CODE);
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORIQUE);
        this.onCreate(arg0);
        Log.i("SQLite DB", "Upgrade");
    }
}
