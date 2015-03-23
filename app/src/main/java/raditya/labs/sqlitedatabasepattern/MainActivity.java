package raditya.labs.sqlitedatabasepattern;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import raditya.labs.sqlitedatabase.database.table.InvoiceTable;
import raditya.labs.sqlitedatabase.model.InvoiceModel;
import raditya.labs.sqlitedatabase.database.helper.DatabaseHelper;

/**
 * Created by raditya on 3/23/2015.
 */
public class MainActivity extends Activity {

    private TextView mTvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvTime = (TextView)findViewById(R.id.tv_time);

        Button btnBiasa = (Button)findViewById(R.id.btn_insert_biasa);
        btnBiasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertIntoDatabaseBiasa();
            }
        });

        Button btnBulk = (Button)findViewById(R.id.btn_insert_bulk);
        btnBulk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertIntoDatabaseBulk();
            }
        });

        Button delete = (Button)findViewById(R.id.btn_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletaData();
            }
        });

        Button btnLoadSubject = (Button)findViewById(R.id.btn_load_subject);
        btnLoadSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataSubject();
            }
        });

        Button btnLoadInvoice = (Button)findViewById(R.id.btn_load_invoice);
        btnLoadInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataInvoice();
            }
        });

        Button btnUpdate = (Button)findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upadate();
            }
        });
    }

    private void upadate(){
        InvoiceModel model = (InvoiceModel)DatabaseHelper.getInstance(this.getApplicationContext()).query(InvoiceTable.TABLE_NAME, "SELECT * FROM " + InvoiceTable.TABLE_NAME + " where id = 154");
        Log.d("RADITYA GUMAY", model.getId() + " " + model.getComment());

        model.setComment("raditya gumay");
        DatabaseHelper.getInstance(this.getApplicationContext()).update(InvoiceTable.TABLE_NAME, model);

        InvoiceModel models = (InvoiceModel)DatabaseHelper.getInstance(this.getApplicationContext()).query(InvoiceTable.TABLE_NAME, "SELECT * FROM " + InvoiceTable.TABLE_NAME + " where id = 154");
        Log.d("RADITYA GUMAY", models.getId() + " " + models.getComment());
    }

    private void loadDataSubject(){
        List<Object> objects = DatabaseHelper.getInstance(this.getApplicationContext()).queries(InvoiceTable.TABLE_NAME, "Select * from " + InvoiceTable.TABLE_NAME);
    }

    private void loadDataInvoice(){
        List<Object> objects = DatabaseHelper.getInstance(this.getApplicationContext()).queries(InvoiceTable.TABLE_NAME, "Select * from " + InvoiceTable.TABLE_NAME);
    }


    private void deletaData(){
        DatabaseHelper.getInstance(this.getApplicationContext()).delete();
    }

    private void insertIntoDatabaseBulk(){
        // JUST CLEAR DATA IN DATABASE BEFORE INSERT
        deletaData();

        long startTime = System.currentTimeMillis();
        List<InvoiceModel> models = new ArrayList<InvoiceModel>();
        for(int i = 0; i < 1000; i++){
            InvoiceModel model = new InvoiceModel();
            model.setId(i);
            model.setComment("Hello " + i);
            models.add(model);
        }
        DatabaseHelper.getInstance(this.getApplicationContext()).bulkInsert(InvoiceTable.TABLE_NAME, models);
        long diff = System.currentTimeMillis() - startTime;
        ((TextView)findViewById(R.id.tv_time)).setText("Exec Time: " + String.format("%d min, %d sec, %d mili",
                TimeUnit.MILLISECONDS.toMinutes(diff),
                TimeUnit.MILLISECONDS.toSeconds(diff) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diff)),
                TimeUnit.MILLISECONDS.toMillis(diff)));
    }

    private void insertIntoDatabaseBiasa(){
        // JUST CLEAR DATA IN DATABASE BEFORE INSERT
        deletaData();

        long startTime = System.currentTimeMillis();
        List<InvoiceModel> models = new ArrayList<InvoiceModel>();
        for(int i = 0; i < 1000; i++){
            InvoiceModel model = new InvoiceModel();
            model.setId(i);
            model.setComment("Hello " + i);
            models.add(model);
        }
        DatabaseHelper.getInstance(this.getApplicationContext()).insert(InvoiceTable.TABLE_NAME, models);
        long diff = System.currentTimeMillis() - startTime;
        ((TextView)findViewById(R.id.tv_time)).setText("Exec Time: " + String.format("%d min, %d sec, %d mili",
                TimeUnit.MILLISECONDS.toMinutes(diff),
                TimeUnit.MILLISECONDS.toSeconds(diff) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diff)),
                TimeUnit.MILLISECONDS.toMillis(diff)));
    }
}
