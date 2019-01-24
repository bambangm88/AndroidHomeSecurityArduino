package com.example.bamaproject.androidhomesecurityarduino;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bamaproject.androidhomesecurityarduino.R;
import com.example.bamaproject.androidhomesecurityarduino.Security.sql.DatabaseHelper;

import static com.example.bamaproject.androidhomesecurityarduino.Security.activities.LoginActivity.TAG_USERNAME;

public class pengaturan extends AppCompatActivity {


    DatabaseHelper SQLite = new DatabaseHelper(this) ;
    EditText txt_pass,txtKonfirm;

    Button ubah ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        txt_pass= (EditText) findViewById(R.id.textPassword);
        txtKonfirm = (EditText) findViewById(R.id.textKonfirmPassword);
        ubah = findViewById(R.id.btnUbah) ;

        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });

    }




    // Update data in SQLite database
    private void edit() {
        if (String.valueOf(txt_pass.getText()).equals(null) || String.valueOf(txtKonfirm.getText()).equals(""))
                {

                    Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content), "Silahkan Isi Field", Snackbar.LENGTH_SHORT);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.Gagal));
                    snackbar.show();





        } else if (String.valueOf(txt_pass.getText())== String.valueOf(txtKonfirm.getText()))
        {

            Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content), "Password Tidak Cocok", Snackbar.LENGTH_SHORT);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.Gagal));
            snackbar.show();


        } else {
            SQLite.update(MainActivity.namaku, txt_pass.getText().toString().trim()    );
            Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content), "Password Berhasil Diubah", Snackbar.LENGTH_SHORT);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.orangeMuda));
            snackbar.show();
            blank();

        }
    }

    // Make blank all Edit Text
    private void blank() {
        txt_pass.setText(null);
        txtKonfirm.setText(null);

    }






}
