package com.casper.testdrivendevelopment;
import androidx.appcompat.app.AppCompatActivity;
import android.media.Image;
import android.text.Layout;
import android.widget.ImageView;
import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by jszx on 2019/9/24.
 */

public class Book implements Serializable {

    private static final long serialVersionUID = 19991006L;

    private int coverID;
    private String strTitle;

    public Book(int coverID, String strTitle) {
        this.coverID = coverID;
        this.strTitle = strTitle;
    }

    public int getCoverResourceID() {
        return coverID;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String newTitle){
        strTitle = newTitle;
    }

}
