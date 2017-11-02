package com.example.joem.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.Serializable;

//If the class implements 'serializable' then all the attribute/parameters must be serializable
public class ThirdActivity extends AppCompatActivity implements Serializable{

    String name;
    double age;

    public ThirdActivity(String name, double age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "ThirdActivity{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        setTitle("Third Activity");
    }
}
