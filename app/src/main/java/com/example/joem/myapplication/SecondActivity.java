package com.example.joem.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    EditText editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle("Second Activity");

        findViewById(R.id.buttonDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//"finish" ends activity
            }
        });

        if(getIntent() != null && getIntent().getExtras() != null){
            //Looks at 'intent,' "getExtras" retrieves map from intent, returns string from NAME_KEY in Main and prints it to 'toast'
            //Concatenated with a comma and getDouble retrieving the AGE_KEY double
            //Using 'toast' template instead of printing to logcat
            Toast.makeText(this, getIntent().getExtras().getString(MainActivity.NAME_KEY) +
                    ", " + getIntent().getExtras().getDouble(MainActivity.AGE_KEY), Toast.LENGTH_SHORT).show();

            //Creates instance of "ThirdActivity" named "className" and gets intent via serializable extras from the Third_Activity_Key in main
            ThirdActivity className = (ThirdActivity) getIntent().getExtras().getSerializable(MainActivity.THIRD_ACTIVITY_KEY);
            Toast.makeText(this, className.toString(), Toast.LENGTH_SHORT).show(); //Toasts the above information using className variable

            //Creates instance of "Person" named "personName" and gets intent via parcelable extras from the Person_Key in main
            Person personName = getIntent().getExtras().getParcelable(MainActivity.PERSON_KEY);
            Toast.makeText(this, personName.toString(), Toast.LENGTH_SHORT).show();
        }

        editTextName = (EditText) findViewById(R.id.editText);

        findViewById(R.id.buttonSendText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringName = editTextName.getText().toString();
                if (stringName == null || stringName.length() == 0){//validates if text field is empty
                    setResult(RESULT_CANCELED);//result cancelled bc couldn't get a result
                }else{
                    //create intent to hold data you want to send back
                    //said intent is not targeted to anybody, whatever the result you are going to send is going to be passed back to the caller/instantiating activity (in this case 'main')
                    Intent intentName = new Intent();//this intent targets nothing, created to hold data, so don't need to set action
                    intentName.putExtra(MainActivity.VALUE_KEY, stringName);//add value you want to send back in intent
                    setResult(RESULT_OK, intentName);//result sent when activity finishes
                }
                finish();//to finish this activity
            }
        });
    }
}