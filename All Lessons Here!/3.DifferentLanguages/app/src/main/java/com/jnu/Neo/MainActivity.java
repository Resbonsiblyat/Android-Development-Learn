package com.jnu.Neo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnChange = findViewById(R.id.button_change_language);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Resources resources = MainActivity.this.getResources();
                DisplayMetrics dm = resources.getDisplayMetrics();
                Configuration config = resources.getConfiguration();

                EditText edtCountry = findViewById(R.id.edit_text_country);


                switch (edtCountry.getText().toString()){
                    case "zh":
                    config.locale = Locale.CHINESE;
                    resources.updateConfiguration(config, dm);
                    reboot();
                    break;

                    case "en":
                    config.locale = Locale.ENGLISH;
                    resources.updateConfiguration(config, dm);
                    reboot();
                    break;

                    case "jp":
                    config.locale = Locale.JAPANESE;
                   // config.setLocale(Locale.JAPANESE);
                    resources.updateConfiguration(config, dm);
                    reboot();
                    break;

                    case "kr":
                    config.locale = Locale.KOREAN;
                    resources.updateConfiguration(config, dm);
                    reboot();
                    break;

                    default:
                    }
                }
        });


    }

    void reboot(){
        MainActivity mainActivity = (MainActivity) MainActivity.this;
        mainActivity.recreate();
    }


}
