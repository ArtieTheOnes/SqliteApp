package artie.urop.sqliteapp;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editId,editContent,editID;
    Button btnAddData;
    Button btnViewAll;
    Button btnUpdate;
    Button btnDelete;
    Button btnView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editId = (EditText)findViewById(R.id.editText_CUSTOMIZED_ID);
        editContent = (EditText)findViewById(R.id.editText_CONTENT);
        //editID = (EditText)findViewById(R.id.editText_ID);
        btnAddData = (Button)findViewById(R.id.button_add);
        btnViewAll = (Button)findViewById(R.id.button_viewAll);
        btnUpdate = (Button)findViewById(R.id.button_update);
        btnDelete = (Button)findViewById(R.id.button_delete);
        btnView = (Button)findViewById(R.id.button_view);
        AddData();
        viewAll();
        updateData();
        deleteData();
        view();

    }

    public void deleteData()
    {
        btnDelete.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Integer deletedRows = myDb.deleteData(editId.getText().toString());
                        if (deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not deleted", Toast.LENGTH_LONG).show();

                    }
                }
        );

    }


    public void updateData()
    {
        btnUpdate.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        boolean isUpdated = myDb.updateData(/*editID.getText().toString(),*/editId.getText().toString(), editContent.getText().toString());
                        if (isUpdated)
                            Toast.makeText(MainActivity.this,"Data updated", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data no updated", Toast.LENGTH_LONG).show();


                    }
                }
        );
    }
    public void viewAll()
    {
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            // show message
                            showMessage("ERROR","No Data Found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            //buffer.append("ID: " + res.getString(0) + '\n');
                            buffer.append("ID: " + res.getString(0) + '\n');
                            buffer.append("CONTENT: " + res.getString(1) + '\n');
                            buffer.append("TIMESTAMP: " + res.getString(2) + '\n');
                        }
                        // show all data
                        showMessage("Data",buffer.toString());

                    }
                }

        );
    }

    public void view()
    {
        btnView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            // show message
                            showMessage("ERROR","No Data Found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        int isFound = 0;
                        while (res.moveToNext()) {
                            if (res.getString(0).equals(editId.getText().toString()))
                            {
                                //buffer.append("ID: " + res.getString(0) + '\n');
                                buffer.append("ID: " + res.getString(0) + '\n');
                                buffer.append("CONTENT: " + res.getString(1) + '\n');
                                buffer.append("TIMESTAMP: " + res.getString(2) + '\n');
                                /*buffer.append("LENGTH: " + res.getString(0).length() + '\n');
                            buffer.append("LENGTH: " + editId.getText().toString().length() + '\n');
                            buffer.append("LENGTH: " + (res.getString(0) == editId.getText().toString()) + '\n');
*/
                            isFound = 1;

                            }
                        }
                        // show all data
                        if (isFound == 0)
                        {
                            showMessage("ERROR","No Data Found");
                            return;
                        }
                        showMessage("Data",buffer.toString());

                    }
                }

        );
    }
    public void showMessage(String title, String Message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void AddData ()
    {
        btnAddData.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        boolean isInserted =  myDb.insertData(editId.getText().toString(), editContent.getText().toString());
                        if (isInserted)
                            Toast.makeText(MainActivity.this,"Data inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data no inserted", Toast.LENGTH_LONG).show();


                    }
                }
        );
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
