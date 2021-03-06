package com.example.bharath.bt_check;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;




public class MainActivity extends AppCompatActivity {


    Button send,connect,receive;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    BluetoothSocket socket = null;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    String action;
    int counter;
    volatile boolean stopWorker;
    TextView t;
    data d;
    IntentFilter filter1,filter2,filter3;
    char []buff=new char[10];
    int index=0;
    String ip;
    HashMap<String,String> task=new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        send = (Button) findViewById(R.id.send);
        connect=(Button)findViewById(R.id.connect);
        d=new data(MainActivity.this);



        task.put("00", "Call");
        task.put("01", "Message");
        task.put("10", "Camera");
        task.put("11", "Music");

        t = (TextView) findViewById(R.id.textView);
        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //Registering filters...
        filter1 = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter2 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter3 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        //this.registerReceiver(mReceiver, filter1);
        //this.registerReceiver(mReceiver, filter2);
        //this.registerReceiver(mReceiver, filter3);


        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }


        /*connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothDevice device=null;
                for(BluetoothDevice t : mBluetoothAdapter.getBondedDevices()){
                    if(t.getName().toString().equals("MyPhone Agua Rio") || t.getName().toString().equals("RobertSpartacus-0")){
                        device=t;
                        break;
                    }
                }
                Toast.makeText(getApplicationContext(),"Found .."+device.getName().toString(),Toast.LENGTH_SHORT).show();
                UUID SERIAL_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); // bluetooth serial port service
                //UUID SERIAL_UUID = device.getUuids()[0].getUuid(); //if you don't know the UUID of the bluetooth device service, you can get it like this from android cache



                try {
                    socket = device.createRfcommSocketToServiceRecord(SERIAL_UUID);
                } catch (Exception e) {
                    Log.e("", "Error creating socket");}

                try {
                    socket.connect();
                    Log.e("","Connected");
                } catch (IOException e) {
                    Log.e("",e.getMessage());
                    try {
                        Log.e("","trying fallback...");

                        socket =(BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(device,1);
                        socket.connect();

                        Log.e("","Connected");
                    }
                    catch (Exception e2) {
                        Log.e("", "Couldn't establish Bluetooth connection!");
                    }
                }

                beginListenForData();

            }

        });*/


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*biind();
                d.addCall();*/
            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.getConatct();
            }
        });


    }


    public void biind(){
        Intent dialogIntent = new Intent(MainActivity.this, BT.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(dialogIntent);
    }
    /*
    }



    void sendData(String m) throws IOException{
        String msg = m;
        mmOutputStream=socket.getOutputStream();
        mmOutputStream.write(msg.getBytes());
        Log.d("Write:","Data sent..."+m);
        t.setText(""+m);
    }

    void beginListenForData()
    {
        final Handler handler = new Handler();
        final byte delimiter = 10; //This is the ASCII code for a newline character

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        mmInputStream = socket.getInputStream();
                        int bytesAvailable = mmInputStream.available();
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i];
                                if(b == delimiter)
                                {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable()
                                    {
                                        public void run()
                                        {
                                            char[] tt=data.toCharArray();
                                            t.setText("Character :"+tt[0]);
                                            buff[index]=tt[0];
                                            check();
                                        }
                                    });
                                }
                                else
                                {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

*/
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

            }
            else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {

                    Toast.makeText(getApplicationContext(),"Connected...",Toast.LENGTH_SHORT).show();

            }
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

            }
            else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {

            }
            else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {

                Toast.makeText(getApplicationContext(),"DisConnected...",Toast.LENGTH_SHORT).show();

            }
        }
    };

    public void check(){
        action=task.get(String.valueOf(buff).trim());
        if(action!=null)
        {
            index=0;
            Toast.makeText(getApplicationContext(), "" + action, Toast.LENGTH_SHORT).show();
            action=null;
            buff=new char[10];
        }
        else {
            index++;
        }
    }

    @Override
    public void onStop(){
        //unregisterReceiver(mReceiver);
        super.onStop();
        finish();
    }

    @Override
    public void onPause(){
        //unregisterReceiver(mReceiver);
        super.onPause();
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
