package com.example.bamaproject.androidhomesecurityarduino;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bamaproject.androidhomesecurityarduino.Security.activities.LoginActivity;

import static com.example.bamaproject.androidhomesecurityarduino.Security.activities.LoginActivity.TAG_USERNAME;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar ;
    private CardView btn1,btn2,btn3,btn4;
    public static String namaku;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        btn1 = (CardView)findViewById(R.id.r1);
        btn4 = (CardView)findViewById(R.id.r4);
        btn3 = (CardView)findViewById(R.id.r3);
        btn2 = (CardView)findViewById(R.id.r2);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,DeviceList.class);
                startActivity(i);
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,petunjuk.class);
                startActivity(i);

            }
        });


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,tentang.class);
                startActivity(i);

            }
        });


        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,pengaturan.class);
                startActivity(i);
            }
        });



    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.keluar, menu);





        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.keluar) {

            showAlertLogOut();
                   return true;
        }



        return super.onOptionsItemSelected(item);
    }





//----------------

    //perintah keluar ------------
    private void showAlertLogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("Keluar Akun Anda ?")
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);


                        // TODO Auto-generated method stub
                        // update login session ke FALSE dan mengosongkan nilai id dan username
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(LoginActivity.session_status, false);
                           editor.putString(TAG_USERNAME, null);
                        editor.commit();



                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        finish();
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(),"Keluar Berhasil ",Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
//----------------













}
