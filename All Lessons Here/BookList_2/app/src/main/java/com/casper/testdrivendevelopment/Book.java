package com.casper.testdrivendevelopment;
import androidx.appcompat.app.AppCompatActivity;
import android.media.Image;
import android.text.Layout;
import android.widget.ImageView;
import android.os.Bundle;
/**
 * Created by jszx on 2019/9/24.
 */
class Book {
    private int coverID;
    private String strTitle;

    Book(int coverID, String strTitle) {
        this.coverID = coverID;
        this.strTitle = strTitle;
    }

    int getCoverResourceID() {
        return coverID;
    }

    String getTitle() {
        return strTitle;
    }

    void setTitle(String newTitle){
        strTitle = newTitle;
    };

}
