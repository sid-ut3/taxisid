package fr.univ_tlse3.taxisid.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Insa on 07/01/2016.
 */
    public class LiaisonDataBase
    {
        // Attributs nécessaire dans cette classe
        private SQLiteDatabase database;
        private DataBaseHandler maBDD;

        /**
         * Constructeur
         * @param context de la bdd
         */
        public LiaisonDataBase(Context context) {
            maBDD = new DataBaseHandler(context);
        }

        /**
         * Méthode qui permet d'ouvrir la bdd
         * @throws SQLException problème du a l'ouverture de la bdd
         */
        public void open() throws SQLException {
            database = maBDD.getWritableDatabase();
        }

        /**
         * Méthode qui permet de fermer la bdd
         */
        public void close() {
            maBDD.close();
        }

        /**
         * Méthode qui permet la création d'un message
         * @param message message envoyer par la centrale
         */
        public void creationMessage(String message, String date){
            SQLiteDatabase db = maBDD.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DataBaseHandler.KEY_CODE_MESSAGE, message);
            values.put(DataBaseHandler.KEY_CODE_DATE, date);

            String s = values.get(DataBaseHandler.KEY_CODE_DATE).toString();
            long id = db.insert(DataBaseHandler.TABLE_HISTORIQUE, null, values);

            Log.i("CREATION Message", "Contenu : " + id + "Date :" + s);
            db.close();
        }

        public void suppressionMessage(int id){
            SQLiteDatabase db = maBDD.getWritableDatabase();
            boolean i = db.delete(DataBaseHandler.TABLE_HISTORIQUE, DataBaseHandler.KEY_ID_HISTORIQUE + "=" + id, null) > 0;
            Log.i("Message supprimé ", " "+id);
            db.close();
        }

        public void suppressionHistorique(){
            SQLiteDatabase db = maBDD.getWritableDatabase();
            db.execSQL("delete from "+ DataBaseHandler.TABLE_HISTORIQUE);
            db.execSQL("delete from sqlite_sequence where name = '"+ DataBaseHandler.TABLE_HISTORIQUE+"'");
            Log.i("Table historique ", " supprimé");
            db.close();
        }

        /**
         * Méthode qui affiche toutes l'historique
         * @return liste de message
         */
        public List<Message> getHistorique() {
            List<Message> historique = new ArrayList<Message>();

            SQLiteDatabase db = maBDD.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + DataBaseHandler.TABLE_HISTORIQUE, null);

            while (c.moveToNext())
            {
                Message cou = new Message(c.getInt(c.getColumnIndex(DataBaseHandler.KEY_ID_HISTORIQUE)),c.getString(c.getColumnIndex(DataBaseHandler.KEY_CODE_MESSAGE)),c.getString(c.getColumnIndex(DataBaseHandler.KEY_CODE_DATE)));
                historique.add(cou);
            }

            c.close();
            db.close();
            return historique;
        }

        /**
         * Trouver un message à partir de son id
         * @param id id du message
         * @return message
         */
        public Message getMessageFromId(int id) {

            SQLiteDatabase db = maBDD.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + DataBaseHandler.TABLE_HISTORIQUE + " WHERE " +
                    DataBaseHandler.KEY_ID_HISTORIQUE + " = " + id, null);

            Message cou = new Message();
            while (c.moveToNext())
            {
                cou.setId(c.getInt(c.getColumnIndex(DataBaseHandler.KEY_ID_HISTORIQUE)));
                cou.setMessage(c.getString(c.getColumnIndex(DataBaseHandler.KEY_CODE_MESSAGE)));
                String date = c.getString(c.getColumnIndex(DataBaseHandler.KEY_CODE_DATE));
                String[] separated = date.split(" ");
                String d = separated[0];
                String h = separated[1];
                Log.i("Heure ", " "+h);
                cou.setDate(d);
                cou.setHeure(h);
            }
            c.close();
            db.close();
            return cou;
        }

        public int getMaxMessage(){
            SQLiteDatabase db = maBDD.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + DataBaseHandler.TABLE_HISTORIQUE, null);
            c.moveToFirst();
            int count= c.getInt(0);
            c.close();
            db.close();
            return count;
        }

        /**
         * Méthode qui permet la création d'un code
         * @param nom du code
         */
        public void creationCode(String nom, String utilisation ){
            SQLiteDatabase db = maBDD.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DataBaseHandler.KEY_CODE_NAME, nom);
            values.put(DataBaseHandler.KEY_CODE_UTILISATION, utilisation);
            long id = db.insert(DataBaseHandler.TABLE_CODE, null, values);
            Log.i("CREATION Code ", "Contenu : " + id);
            db.close();
        }

        /**
         * Méthode qui affiche toutes les codes
         * @return une liste avec tout les codes
         */
        public List<Code> getCode(String utilisation) {
            List<Code> listeCode = new ArrayList<Code>();
            SQLiteDatabase db = maBDD.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + DataBaseHandler.TABLE_CODE + " WHERE " + DataBaseHandler.KEY_CODE_UTILISATION + " = '"+ utilisation+"'", null);
            while (c.moveToNext())
            {
                Code cou = new Code(c.getInt(c.getColumnIndex(DataBaseHandler.KEY_ID_CODE)),c.getString(c.getColumnIndex(DataBaseHandler.KEY_CODE_NAME)), c.getString(c.getColumnIndex(DataBaseHandler.KEY_CODE_UTILISATION)) );
                listeCode.add(cou);
            }
            c.close();
            db.close();
            return listeCode;
        }


        /**
         * Trouver un nom de code à partir de son id
         * @param id id du code
         * @return code
         */
        public String getCodeFromId(int id) {
            SQLiteDatabase db = maBDD.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + DataBaseHandler.TABLE_CODE + " WHERE " +
                    DataBaseHandler.KEY_ID_CODE + " = " + id, null);
            Code cou = new Code();
            String res = "null";
            while (c.moveToNext())
            {
                cou.setId(c.getInt(c.getColumnIndex(DataBaseHandler.KEY_ID_CODE)));
                cou.setNom(c.getString(c.getColumnIndex(DataBaseHandler.KEY_CODE_NAME)));
                cou.setUtilisation(c.getString(c.getColumnIndex(DataBaseHandler.KEY_CODE_UTILISATION)));
                res = cou.getNom();
            }
            c.close();
            db.close();
            return res;
        }

    }
