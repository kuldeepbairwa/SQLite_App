package com.kuldeep.sqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etUsername, etName, etPass;
    Button btn_insert, btn_delete, btn_view, btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUserName);
        etPass = findViewById(R.id.etPass);
        btn_delete = findViewById(R.id.btn_delete);
        btn_view = findViewById(R.id.btn_view);
        btn_update = findViewById(R.id.btn_update);
        btn_insert = findViewById(R.id.btn_insert);

        DatabaseConn g = new DatabaseConn(this);
//        SQLiteDatabase db = g.getReadableDatabase();


        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String username = etUsername.getText().toString();
                String pass = etPass.getText().toString();

                if (name.isEmpty() || username.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "all fields are required!", Toast.LENGTH_SHORT).show();
                } else {

                    Boolean i = g.insertData(name, username, pass);
                    if (i) {
                        Toast.makeText(MainActivity.this, "successful", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(MainActivity.this, "not successful", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                Boolean i = g.deleteData(username);
                if(i){
                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Not successful", Toast.LENGTH_SHORT).show();
                }


            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String username = etUsername.getText().toString();
                String pass = etPass.getText().toString();
                Boolean i = g.updateData(name,username,pass);
                if(i){
                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Not successful", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor t = g.getData();
                if (t.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                } else {
                    StringBuffer buffer = new StringBuffer();
                    while (t.moveToNext()) {
                        buffer.append("Name: " + t.getString(1) + "\n");
                        buffer.append("Username: " + t.getString(2) + "\n");
                        buffer.append("password: " + t.getString(3) + "\n");
                    }


                    // Create the object of AlertDialog Builder class
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    // Set the message show for the Alert time
                    builder.setMessage(buffer.toString());

                    // Set Alert Title
                    builder.setTitle("Signed Up users data");

                    // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                    builder.setCancelable(true);


                    // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                    builder.setNeutralButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {

                    });

                    // Create the Alert dialog
                    AlertDialog alertDialog = builder.create();
                    // Show the Alert Dialog box
                    alertDialog.show();
                }


            }


        });
    }
}