package com.example.joem.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    static String NAME_KEY = "NAME"; //name key for passing data via intent (using key-value pair)
    static String AGE_KEY = "AGE";
    static String THIRD_ACTIVITY_KEY = "ThirdActivity";
    static String PERSON_KEY = "PERSON";
    CharSequence[] sequenceName = {"Red", "Blue", "Yellow", "Green"};//charSequence for AlertDialog demo
    public static final int REQ_CODE = 100;//integer used to identify request in 'startActivityForResult'
    public static final String VALUE_KEY = "value";//used for 'results demo'

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main Activity");
        Log.d("demo", "inside onCreate");//message printed to logcat debug when app is created

        findViewById(R.id.buttonToSecond).setOnClickListener(new View.OnClickListener() {//button onClick listener
            @Override
            public void onClick(View view) {
                //EXPLICIT intent that takes context (Main) then goes to specified location (second activity class)
                //Explicit because hard bound class name "SecondActivity"
                //just typing "this" for context refers to 'onClickListener,' so "MainActivity.this" is used to refer to 'Main'
                //Will display SecondActivity on top of MainActivity
                Intent intentName = new Intent(MainActivity.this, SecondActivity.class);
                //startActivity(intentName);//starts Second activity when clicked
                startActivityForResult(intentName, REQ_CODE);
            }
        });

        findViewById(R.id.buttonImplicit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //IMPLICIT intent, the rest coded in AndroidManifest.xml
                //For implicit intent, you specify the action
                //"com.example.joem.myapplication.intent.action.VIEW" is the 'action'
                Intent intentName = new Intent("com.example.joem.myapplication.intent.action.VIEW");
                intentName.addCategory(Intent.CATEGORY_DEFAULT); //if no default is declared, the above become the default
                startActivity(intentName);
            }
        });

        //button that passes simple data using explicit intent, and goes to second activity
        findViewById(R.id.buttonDataPass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentName = new Intent(MainActivity.this, SecondActivity.class);
                intentName.putExtra(NAME_KEY, "Bob Smith"); //key=>value, NAME_KEY=>Bob Smith
                intentName.putExtra(AGE_KEY, (double) 25.5);

                //creates instance of "ThirdActivity," setting name variable to Alice Smith and age variable to 20.5
                ThirdActivity className = new ThirdActivity("Alice Smith", 20.5);
                intentName.putExtra(THIRD_ACTIVITY_KEY, className);

                startActivity(intentName);
            }
        });

        //CRASHES so can't have parcel and above data passed at the same time the way I have it set up currently
        findViewById(R.id.buttonParcel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentName = new Intent(MainActivity.this, SecondActivity.class);
                //The line below creates new person objects and puts it in extra to be parcelized, calling the parcel methods
                intentName.putExtra(PERSON_KEY, new Person("Connor Smith", 15.5, "Charlotte"));
                startActivity(intentName);
            }
        });

        //string action uri (uri being the link you want to open, phone# to dial, etc; in this case a url)
        Intent intentName = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.uncc.edu"));
        startActivity(intentName);//starts intent on activity start

        //some intents require permission, such as this one which is trying to make a call
        //commented out because I haven't learned about permissions yet
        /*Intent intentName2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:9802535875"));
        startActivity(intentName2);*/

        AlertDialog.Builder builderName = new AlertDialog.Builder(this);
        //can chain setTitle.setBuilder because "setTitle" returns Builder again
        //setMessage can use "id references" as well
        //setPositiveButton's second input creates an anonnymous class
        builderName.setTitle("Pick a color")
                //.setMessage("Are you sure?") //removed so you can see color options
                //next line is code for selecting mulitple options, as opposed to checking one option featured below
                //.setMultiChoiceItems(sequenceName, null, new DialogInterface.OnMultiChoiceClickListener(){});
                    //null (above) declares nothing will be pre-selected
                    //multiChoice uses bool, so true/false is returned
                .setItems(sequenceName, new DialogInterface.OnClickListener() {
                    @Override//onClick method telling us if user has clicked a color button
                    public void onClick(DialogInterface dialogInterface, int integerName) {
                        //references the color "sequenceName" index and prints to logcat debug
                        Log.d("demo", "Selected " + sequenceName[integerName]);
                    }
                })//Makes selection options a radio buttons
                //-1 declares that nothing will be pre-selected, must choose index location for pre-selection (0=red, 1=blue, etc)
                .setSingleChoiceItems(sequenceName, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int integerName) {
                        Log.d("demo", "Selected " + sequenceName[integerName]);
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override//onClick method telling us if user has clicked the ok button
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("demo", "Clicked Ok");
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {//chained with above
            @Override//onClick method telling us if user has clicked the cancel button
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("demo", "Clicked Cancel");
            }
        })
        .setCancelable(false);//makes it so clicking outside alert dialog doesn't exit out of it

        //made 'final' so it exists after method has finished
        final AlertDialog alertDialogName = builderName.create();//create instance of alert dialog
        findViewById(R.id.buttonAlert).setOnClickListener(new View.OnClickListener() {//button implementing alert
            @Override
            public void onClick(View view) {
                alertDialogName.show();//shows alert dialog
                //alertDialogName.dismiss();//retires alert dialog (works on progressBar?)
            }
        });
        //ProgressBar progressBarName = new ProgressBar(this); //progressDialog no longer supported, learn ProgressBar
    }

    //We make the 'request' and give it a code
    //When we get 'response' it will call 'onActivityResult' and pass requestCode that we can identify it
    //Result code is indication of what happened ('secondActivity' sets this)
    //'Intent' will hold data sent back from the secondActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //an activity can instantiate a lot of other activities, so this validates we have the correct code
        if (requestCode == REQ_CODE){
            if (resultCode == RESULT_OK){
                String stringName = data.getExtras().getString(VALUE_KEY);
                Log.d("demo", "Value recieved is: " + stringName);
            }else if(resultCode == RESULT_CANCELED){
                Log.d("demo", "No value received");
            }
        }
    }

    @Override
    protected void onDestroy() {
        Log.d("demo", "inside onDestroy");//activity life cycle showing where app is
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        Log.d("demo", "inside onPause");
        super.onPause();
    }
    @Override
    protected void onResume() {
        Log.d("demo", "inside onResume");
        super.onResume();
    }
    @Override
    protected void onStart() {
        Log.d("demo", "inside onStart");
        super.onStart();
    }
    @Override
    protected void onStop() {
        Log.d("demo", "inside onStop");
        super.onStop();
    }
}
