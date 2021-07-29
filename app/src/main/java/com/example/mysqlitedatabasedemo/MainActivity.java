package com.example.mysqlitedatabasedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText nameEditText,ageEditText,genderEditText,idEditText;
    private Button addButton,showButton2,updateButton,deleteButton;
    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.Editname);
        ageEditText = findViewById(R.id.Editage);
        genderEditText = findViewById(R.id.Editgender);
        idEditText = findViewById(R.id.idEditTextId);

        addButton = findViewById(R.id.addBtn);
        showButton2 = findViewById(R.id.showBtn);
        updateButton = findViewById(R.id.updateBtn);
        deleteButton = findViewById(R.id.deleteBtn);


        myDatabaseHelper = new MyDatabaseHelper(this);
       SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();



        addButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                String name = nameEditText.getText().toString();
                String age = ageEditText.getText().toString();
                String gender = genderEditText.getText().toString();
                String id = idEditText.getText().toString();

                if(view.getId() == R.id.addBtn){

                    long rowId = myDatabaseHelper.insertData(name,age,gender);
                    if(rowId==-1){
                        Toast.makeText(getApplicationContext(),"Unsuccessfull",Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(getApplicationContext(),"Successfully "+rowId+" inserted",Toast.LENGTH_LONG).show();

                    }

                }
            }
        });
                    //Read Data
        showButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.showBtn){

                  Cursor cursor =  myDatabaseHelper.displayAllData();

                  if(cursor.getCount() == 0 ){
                      //if there is no data will display msg
                      showData("Error","No Data Found");
                      return ;
                  }
                  StringBuffer stringBuffer = new StringBuffer();

                    while(cursor.moveToNext()){
                        stringBuffer.append("ID : "+cursor.getString(0)+"\n");
                        stringBuffer.append("Name : "+cursor.getString(1)+"\n");
                        stringBuffer.append("Age : "+cursor.getString(2)+"\n");
                        stringBuffer.append("Gender : "+cursor.getString(3)+"\n\n\n");
                    }

                    showData("ResultSet",stringBuffer.toString());

                }
            }
        });
                    //Update
        updateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(view.getId()== R.id.updateBtn)
                {

                    boolean isUpdated = myDatabaseHelper.updateData(
                            idEditText.getText().toString(),
                            nameEditText.getText().toString(),
                            ageEditText.getText().toString(),
                            genderEditText.getText().toString());

                    if(isUpdated==true) {
                        Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"not updated",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
                    //Delete
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(view.getId()==R.id.deleteBtn){

                    int value = myDatabaseHelper.deleteData(idEditText.getText().toString());

                    if(value>0){
                        Toast.makeText(getApplicationContext(), "Successfully Deleted", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Not Deleted", Toast.LENGTH_LONG).show();
                    }



                }
            }
        });
    }
                        //ReadData then Show Data Function

            public void showData(String title,String message)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(title);
                builder.setMessage(message);
                builder.setCancelable(true);
                builder.show();

            }
}