package com.casper.testdrivendevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        Button btnConfirm = findViewById(R.id.confirm);
        Button btnCancel = findViewById(R.id.cancel);
        final EditText edtTitle = findViewById(R.id.edit_title);

        final Intent intent = getIntent();
        String strTitle = intent.getStringExtra("Title");

        if(strTitle!=null){
            edtTitle.setText(strTitle);
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("Title",edtTitle.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }
}
