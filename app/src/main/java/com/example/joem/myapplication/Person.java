package com.example.joem.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JoeM on 10/1/2017.
 */

//constructor that gets a parcel, and from that parcel you reconstruct a 'person' (or whatever you're using)
public class Person implements Parcelable{
    String name;
    double age;
    String city;

    public Person(String name, double age, String city) {//constructor so we can use this class and initialize things
        this.name = name;
        this.age = age;
        this.city = city;
    }

    @Override
    public String toString() {//toString so we can print the information
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }

    protected Person(Parcel in) { //order is important, must be the same throughout
        this.name = in.readString();
        this.age = in.readDouble();
        this.city = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override//creates new 'person' with parcel inside it, calling the above constructor
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override//similar to the above method but for an array
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    //method called when you try to parcelize or change an object into a parcel (or to move the object to a 'person')
    //writes whatever parameters you want into the parcel
    @Override
    public void writeToParcel(Parcel parcelName, int i) { //order is important, must be the same throughout
        parcelName.writeString(this.name);//destination where parcel is going to hold 'string' data
        parcelName.writeDouble(this.age);//destination where parcel is going to hold 'double' data
        parcelName.writeString(this.city);
    }
}
