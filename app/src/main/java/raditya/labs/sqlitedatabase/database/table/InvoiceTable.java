package raditya.labs.sqlitedatabase.database.table;

/**
 * Created by raditya on 3/23/2015.
 */
public class InvoiceTable {
    // Name
    public static final String TABLE_NAME = "invoice";

    // Column
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_COMMENT = "comment";

    public static String createTable(){
        return "CREATE TABLE " + TABLE_NAME + "( " +
                COLUMN_ID + " integer primary key autoincrement, " +
                COLUMN_COMMENT + " text not null);";
    }

    public static String getAllData(){
        return  "SELECT FROM " + TABLE_NAME;
    }

    public static String getSpecifyData(String id){
        return "SELECT FROM " + TABLE_NAME + " where id = " + id;
    }
}
