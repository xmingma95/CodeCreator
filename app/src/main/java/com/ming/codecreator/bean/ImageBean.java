package com.ming.codecreator.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author ming
 * @version $Rev$
 * @des ${TODO}
 */
public class ImageBean implements Parcelable {
    private long date;
    private String path;


    public ImageBean(){

    }
    public ImageBean(Parcel parcel) {
       date=parcel.readLong();
       path=parcel.readString();
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(date);
        parcel.writeString(path);
    }

    public static final Parcelable.Creator<ImageBean> CREATOR=
            new Parcelable.Creator<ImageBean>(){

                @Override
                public ImageBean createFromParcel(Parcel parcel) {
                    return new ImageBean(parcel);
                }

                @Override
                public ImageBean[] newArray(int i) {
                    return new ImageBean[i];
                }
            };

}
