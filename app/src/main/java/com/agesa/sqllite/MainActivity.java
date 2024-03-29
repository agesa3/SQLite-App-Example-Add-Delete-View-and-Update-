package com.agesa.sqllite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText nameEdt, surEdt, markEdt,updateEdt;
    Button addBtn, viewBtn,upBtn,deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        nameEdt = findViewById(R.id.name);
        markEdt = findViewById(R.id.marks);
        surEdt = findViewById(R.id.surname);
        addBtn = findViewById(R.id.addBtn);
        viewBtn = findViewById(R.id.viewBtn);
        upBtn=findViewById(R.id.update);
        updateEdt=findViewById(R.id.rowId);
        deleteBtn=findViewById(R.id.delete);
        addData();
        viewAll();
        updateData();
        deleteData();
    }
    public void deleteData(){
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows=myDb.deleteData(updateEdt.getText().toString());
                if (deleteRows>0){
                    Toast.makeText(MainActivity.this, "Record deleted Successfuly", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void updateData(){
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate=myDb.upDateData(updateEdt.getText().toString(),nameEdt.getText().toString(),surEdt.getText().toString(),markEdt.getText().toString());
                if (isUpdate==true){
                    Toast.makeText(MainActivity.this, "Data is Updated", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addData() {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(nameEdt.getText().toString(), surEdt.getText().toString(), markEdt.getText().toString());
                if (isInserted == true) {
                    Toast.makeText(MainActivity.this, "Data inserted succesfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void viewAll() {
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                       showMessage("Error","Nothing to display");
                    return;
                } else {
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append("Id: " + res.getString(0) + "\n");
                        buffer.append("Name: " + res.getString(1) + "\n");
                        buffer.append("Surname: " + res.getString(2) + "\n");
                        buffer.append("Marks: " + res.getString(3) + "\n\n");


                    }
                    //show all data
                       showMessage("Data",buffer.toString());
                }
            }
        });
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
