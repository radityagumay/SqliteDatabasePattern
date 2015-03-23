package raditya.labs.sqlitedatabase.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import raditya.labs.sqlitedatabase.database.table.InvoiceTable;
import raditya.labs.sqlitedatabase.model.InvoiceModel;

/**
 * Created by raditya on 3/23/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;
    private static SQLiteDatabase mDatabase;

    private static final String DATABASE_NAME = "pattern";

    private static final int DATABASE_VERSION = 1;

    //private static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pattern.db";

    public static synchronized DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //super(context, path, null, DATABASE_VERSION);
        mDatabase = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(InvoiceTable.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO
    }

    /**
     * INSERT DATA BIASA
     */
    public static void insert(String tableName, Object ob){
        ContentValues values = null;
        switch (tableName){
            case InvoiceTable.TABLE_NAME:
                List<InvoiceModel> models = (List<InvoiceModel>)ob;
                for(int i = 0; i < models.size(); i++){
                    values = new ContentValues();
                    values.put("id", models.get(i).getId());
                    values.put("comment",  models.get(i).getComment());
                    mDatabase.insert(InvoiceTable.TABLE_NAME, null, values);
                }
                break;
        }
    }

    /**
     * UPDATE DATA
     */
    public static void update(String tableName, Object ob){
        ContentValues values = null;
        switch (tableName){
            case InvoiceTable.TABLE_NAME:
                InvoiceModel models = (InvoiceModel)ob;
                values = new ContentValues();
                values.put("id", models.getId());
                values.put("comment", models.getComment());
                mDatabase.update(InvoiceTable.TABLE_NAME, values, "id = ?", new String[]{String.valueOf(models.getId())});
                break;
        }
    }

    /**
     * INSERT DATA BULK
     */
    public static void bulkInsert(String tableName, Object ob){
        ContentValues values = null;
        String sql = "";
        SQLiteStatement statement = null;
        switch (tableName){
            case InvoiceTable.TABLE_NAME:
                sql = "INSERT INTO " + tableName + " VALUES (?,?);";
                statement = mDatabase.compileStatement(sql);
                mDatabase.beginTransaction();
                List<InvoiceModel> models = (List<InvoiceModel>)ob;
                for(int i = 0; i < models.size(); i++){
                    statement.clearBindings();
                    statement.bindLong(1, models.get(i).getId());
                    statement.bindString(2, models.get(i).getComment());
                    statement.execute();
                }
                mDatabase.setTransactionSuccessful();
                mDatabase.endTransaction();
                break;
        }
    }

    /**
     * DELETE
     */
    public static void delete(){
        mDatabase.execSQL("delete from " + InvoiceTable.TABLE_NAME);
    }

    /**
     * GET DATA
     */
    public static Object query(String tableName, String query){
        Object result = null;
        Cursor cursor = null;

        switch (tableName){
            case InvoiceTable.TABLE_NAME:
                cursor = mDatabase.rawQuery(query, null);
                if(cursor != null){
                    cursor.moveToLast();
                    if(cursor.getCount() > 0){
                        cursor.moveToFirst();
                        InvoiceModel model = new InvoiceModel();
                        model.setId(cursor.getInt(0));
                        model.setComment(cursor.getString(1));
                        result = model;
                    }
                    cursor.close();
                }
                break;
        }
        return result;
    }

    /**
     * GET LIST DATA
     */
    public static List<Object> queries(String tableName, String query){
        List<Object> result = null;
        Cursor cursor = null;

        switch (tableName){
            case InvoiceTable.TABLE_NAME:
                cursor = mDatabase.rawQuery(query, null);
                if(cursor != null){
                    if(cursor.moveToFirst()){
                        result = new ArrayList<Object>();
                        do{
                            InvoiceModel model = new InvoiceModel();
                            model.setId(cursor.getInt(0));
                            model.setComment(cursor.getString(1));
                            result.add(model);
                        }while (cursor.moveToNext());
                    }
                    cursor.close();
                }
                break;
        }
        return result;
    }
}
