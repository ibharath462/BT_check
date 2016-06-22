package com.example.bharath.bt_check;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
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
    int swapFlag=0;
    GridLayout gl,gl2;
    CountDownTimer cT;
    ViewGroup v;
    LinearLayout ll1,predictiveView;
    Button but;
    Drawable dd;
    View h;
    LST disText=null;
    Handler mHandler;
    View myView;
    int rIndex=2,cIndex=0;
    WindowManager.LayoutParams p;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        
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
        disText=new LST(BT.this);
        btrec=new BtReceiver();
        mHandler=new Handler();

        cT=new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        }.start();

        IntentFilter iff=new IntentFilter();
        iff.addAction(Bluetooth.BLUETOOTH_SERVICE);
        this.registerReceiver(btrec,iff);
        floating();
        return kv;
    }




    public void floating(){
        wm=(WindowManager)getSystemService(WINDOW_SERVICE);
        p=new WindowManager.LayoutParams(1000,900, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.OPAQUE);
        LayoutInflater fac=LayoutInflater.from(BT.this);

        if(swapFlag==0) {
            myView = fac.inflate(R.layout.skeyboard, null);
            gl = (GridLayout) myView.findViewById(R.id.gl);
            ll1 = (LinearLayout) myView.findViewById(R.id.ll);
            predictiveView=(LinearLayout)myView.findViewById(R.id.predictiveView);
        }
        else{

            myView = fac.inflate(R.layout.numkeyboard, null);
            gl = (GridLayout) myView.findViewById(R.id.gl2);
            ll1 = (LinearLayout) myView.findViewById(R.id.ll2);

        }

        disText.setLetterSpacing(5);
        disText.setTextColor(Color.WHITE);
        disText.setTextSize(25);
        disText.setText("Recognising...");
        ll1.addView(disText);
        wm.addView(myView, p);
        typeee();

    }

    public void typeee(){

        v=(ViewGroup)gl.getChildAt(rIndex);
        v.setBackgroundColor(Color.BLUE);
        h=v.getChildAt(cIndex);
        dd=h.getBackground();
        h.setBackgroundColor(Color.GREEN);
        but=(Button)v.getChildAt(cIndex);
        Log.d("XXXRow",""+rIndex);
        Log.d("XXXColumn", "" + cIndex);
        Log.d("XXXButton", "" + but.getText().toString());
        timer();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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



        cT=new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {


                long t=millisUntilFinished/1000;
                Log.d("Time:",""+t);
                disText.setText("Axing "+but.getText().toString()+" in "+t+" sec");

            }

            @Override
            public void onFinish() {

                String textToInput=but.getText().toString().toLowerCase();
                if(textToInput.equals("xx")){
                    wm.removeView(myView);
                    stopSelf();
                    cancel();
                    onFinishInput();
                    return;
                }
                else if(textToInput.equals("sp")){
                    ic = getCurrentInputConnection();
                    ic.commitText(" ", 1);
                }
                else if(textToInput.equals("bs")){
                    ic = getCurrentInputConnection();
                    ic.deleteSurroundingText(1,0);
                }
                else if(textToInput.equals("sw")){

                    if(swapFlag==0){
                        swapFlag=1;
                    }
                    else{
                        swapFlag=0;
                    }
                    rIndex=2;
                    cIndex=0;
                    ll1.removeView(disText);
                    wm.removeView(myView);
                    floating();
                    cT.cancel();

                }
                else{
                    ic = getCurrentInputConnection();
                    ic.commitText(textToInput, 1);
                }
                rIndex=2;
                cIndex=0;
                defa();
                typeee();
                cancel();

            }
        };

        cT.start();


    }


    class BtReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            String s=intent.getStringExtra("bt");
            defa();
            cT.cancel();
            s=s.trim();
            int val=Integer.parseInt(s);
            Toast.makeText(getApplicationContext(), "Display:" + s, Toast.LENGTH_SHORT).show();
            if(val==1){

                rIndex+=2;
                if(rIndex%12==0)
                    rIndex=2;
                typeee();
            }
            else if(val==0){

                cIndex+=2;
                if(cIndex%12==0)
                    cIndex=0;
                typeee();

            }
            //timer();

        }
    }


}
