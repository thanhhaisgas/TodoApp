package com.example.haint.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {

    Button btnSave;
    EditText txtEditItem;

    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        setTitle("Edit Item");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.checkbox);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveText();
            }
        });
    }

    private void saveText() {
        if(txtEditItem.getText().toString().isEmpty()){
            Toast.makeText(this, "Text is not empty", Toast.LENGTH_SHORT).show();
        }else {
            Intent result = new Intent();
            result.putExtra("result", txtEditItem.getText().toString());
            result.putExtra("pos", pos);
            setResult(1, result);
            finish();
        }
    }

    private void addControls() {
        btnSave= (Button) findViewById(R.id.btnSave);
        txtEditItem = (EditText) findViewById(R.id.txtEditItem);

        getTextEdit();
    }

    private void getTextEdit() {
        Intent getText = getIntent();
        Bundle bundle = getText.getBundleExtra("textEdit");
        String textEdit = bundle.getString("text");
        pos = bundle.getInt("pos");
        txtEditItem.setText(textEdit);
    }

    @Override
    public void onBackPressed() {
        btnSave.callOnClick();
    }
}
