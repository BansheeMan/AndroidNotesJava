package com.example.androidnotesjava;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private int number;
    private String name;
    private String description;
    private String date;

    public Note(int number, String date, String name, String description) {
        this.number = number;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    protected Note(Parcel in) {
        number = in.readInt();
        name = in.readString();
        description = in.readString();
        date = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(number);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(date);
    }
}