package com.example.bharath.bt_check;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BT extends InputMethodService implements KeyboardView.OnKeyboardActionListener{


    static InputConnection ic=null;
    String input;
    GridLayout gl;
    ViewGroup v;
    Button but;
    Drawable dd;
    View h;
    View myView;
    int rIndex=2,cIndex=0;
    WindowManager.LayoutParams p;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if(intent.getExtras()!=null){
            //floating(value);
        }
        return START_STICKY; // or whatever your flag
    }



    private  KeyboardView kv;
    private WindowManager wm;
    private LinearLayout ll;
    private Button b;
    BtReceiver btrec;

    @Override
    public View onCreateInputView() {
        kv=(KeyboardView)getLayoutInflater().inflate(R.layout.keyboard,null);
        kv.setOnKeyboardActionListener(this);
        Intent i=new Intent(this,Bluetooth.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(i);
        btrec=new BtReceiver();
        IntentFilter iff=new IntentFilter();
        iff.addAction(Bluetooth.BLUETOOTH_SERVICE);
        this.registerReceiver(btrec,iff);
        floating();
        return kv;
    }



    public void floating(){
        wm=(WindowManager)getSystemService(WINDOW_SERVICE);
        /*ll=new LinearLayout(this);
        b=new Button(this);
        ViewGroup.LayoutParams bp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        b.setText("Send");
        b.setLayoutParams(bp);
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setBackgroundColor(Color.argb(66, 255, 0, 0));
        ll.setLayoutParams(llp);
        WindowManager.LayoutParams p=new WindowManager.LayoutParams(400,150, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        p.x=0;
        p.y=0;
        p.gravity= Gravity.CENTER | Gravity.CENTER;
        ll.addView(b);
        wm.addView(ll,p);
        ic = getCurrentInputConnection();
        ic.commitText(t, 1);*/
        p=new WindowManager.LayoutParams(1000,800, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.OPAQUE);
        LayoutInflater fac=LayoutInflater.from(BT.this);
        myView=fac.inflate(R.layout.skeyboard, null);
        gl=(GridLayout)myView.findViewById(R.id.gl);
        wm.addView(myView, p);
        typeee();

    }

    public void typeee(){

        v=(ViewGroup)gl.getChildAt(rIndex);
        v.setBackgroundColor(Color.BLUE);
        h=v.getChildAt(cIndex);
        dd=h.getBackground();
        but=(Button)v.getChildAt(cIndex);
        h.setBackgroundColor(Color.GREEN);


    }

    public void defa(){
        v.setBackgroundColor(0);
        h.setBackground(dd);

    }

    @Override
    public void onPress(int primaryCode) {


    }



    @Override
    public void onFinishInput() {
        Intent i1=new Intent(this,BT.class);
        Intent i2=new Intent(this,Bluetooth.class);
        super.onFinishInput();
        //wm.removeView(myView);
        stopService(i1);
        stopService(i2);
        //unregisterReceiver(btrec);
    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {


    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    public void timer(){



    }


    class BtReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            String s=intent.getStringExtra("bt");
            defa();
            s=s.trim();
            int val=Integer.parseInt(s);
            Toast.makeText(getApplicationContext(), "Display:" + s, Toast.LENGTH_SHORT).show();
            if(val==1){

                rIndex+=2;
                if(rIndex%12==0)
                    rIndex=2;
                typeee();

            }
            else{

                cIndex+=2;
                if(cIndex%12==0)
                    cIndex=0;
                typeee();

            }

        }
    }


}
