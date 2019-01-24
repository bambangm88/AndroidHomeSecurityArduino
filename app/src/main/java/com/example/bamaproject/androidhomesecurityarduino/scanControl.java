package com.example.bamaproject.androidhomesecurityarduino;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;


public class scanControl extends AppCompatActivity {

    // Button btnOn, btnOff, btnDis;
    Button  Discnt, Abt;

    private CardView btnOn,btnOff,btn3,btn4;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;

    public static String encHill ;

    private IntentIntegrator intentIntegrator;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device

        //view of the scanControl
        setContentView(R.layout.activity_scan_control);

        //call the widgets
        btnOn = (CardView) findViewById(R.id.crvScanOn);
        btnOff = (CardView) findViewById(R.id.crvScanOff);
        Discnt = (Button)findViewById(R.id.dis_btn);


        new ConnectBT().execute(); //Call the class to connect

        //commands to be sent to bluetooth
        btnOn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intentIntegrator = new IntentIntegrator(scanControl.this);
                intentIntegrator.initiateScan();      //method to turn on
            }
        });

        //commands to be sent to bluetooth
        btnOff.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                turnOffLed("mati");    //method to turn on
            }
        });



        Discnt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Disconnect(); //close connection
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "Hasil tidak ditemukan", Toast.LENGTH_SHORT).show();
            }else{

                try{


                    String a = result.getContents() ;

//
//                // Enkripsi Algoritma Hill Chipher
//                    int k[][]={{17,17,5}, {21,18,21}, {2,2,19}};
//                    int p[]=new int[300];
//                    int c[]=new int[300];
//                    int i=0;
//
//                    String str = "bam" ;
//
//                    for( i=0;i<str.length();i++)
//                    {
//                        int c1=str.charAt(i);
//                        //System.out.println(c1);
//                        p[i]=(c1)-97;
//                    }
//                    i=0;int zz=0;
//                    for( int b=0;b<str.length()/3;b++)
//                    {
//                        for(int j=0;j<3;j++)
//                        {
//                            for(int x=0;x<3;x++)
//                            {
//                                c[i]+=k[j][x]*p[x+zz];
//                            }i++;
//                        }
//                        zz+=3;
//                    }
//                    System.out.print("Encrypted Text : ");
//                    for(int z=0;z<str.length();z++) {
//                        System.out.print((char) ((c[z] % 26) + 97));
//
//                        encHill = ""+(char) ((c[z] % 26) + 97) ;
//
//                    }
//                    // Finish Enkripsi Algoritma Hill Chipher
//
//
//                    Log.d("myTag", "Hasil Enkripsi"+encHill);
//                    Toast.makeText(this, "Hasil Enkripsi = "+encHill, Toast.LENGTH_SHORT).show();

                    if (a.equals("menyala")){
                        turnOnLed(a) ;
                    } else if(a.equals("mati")) {
                        turnOffLed(a);
                    } else {
                        Toast.makeText(this, "Qr Code Tidak Cocok", Toast.LENGTH_SHORT).show();
                    }





                }catch (Exception e){
                    e.printStackTrace();
                    // jika format encoded tidak sesuai maka hasil
                    // ditampilkan ke toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


















    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }

    private void turnOffLed(String var_off)
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write(var_off.toString().getBytes());
                Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content), "Pintu Terkunci", Snackbar.LENGTH_SHORT);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.Gagal));
                snackbar.show();


            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void turnOnLed(String var_on)
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write(var_on.toString().getBytes());

                Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content), "Pintu Terbuka", Snackbar.LENGTH_SHORT);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.orangeMuda));
                snackbar.show();

            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_led_control, menu);
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



    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(scanControl.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}