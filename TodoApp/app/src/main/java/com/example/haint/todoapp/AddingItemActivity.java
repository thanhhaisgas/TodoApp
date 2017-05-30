package com.example.haint.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AddingItemActivity extends AppCompatActivity {

    Button btnAdd;
    EditText txtAddNew;
    ListView lvItems;

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_item);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setTitle("Simple Todo");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.checkbox);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItemToListView();
            }
        });
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItemsToTextFile();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goEditActivity(position);
            }
        });
    }

    private void goEditActivity(int pos) {
        Intent goEdit = new Intent(this, EditItemActivity.class);
        Bundle bundle = new Bundle();
        String textEdit = items.get(pos).toString();
        bundle.putString("text", textEdit);
        bundle.putInt("pos", pos);
        goEdit.putExtra("textEdit", bundle);
        startActivityForResult(goEdit, 1);
    }

    private void addNewItemToListView() {
        if(txtAddNew.getText().toString().isEmpty()){
            Toast.makeText(this,"Text is not empty", Toast.LENGTH_SHORT).show();
        }else {
            items.add(txtAddNew.getText().toString());
            itemsAdapter.notifyDataSetChanged();
            txtAddNew.setText("");
            writeItemsToTextFile();
        }
    }

    private void readItemsFromTextFile(){
        File fileDir = getFilesDir();
        File todoFile =  new File(fileDir, "todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch (IOException e){
            items = new ArrayList<>();
        }
    }

    private void writeItemsToTextFile(){
        File fileDir = getFilesDir();
        File todoFile =  new File(fileDir, "todo.txt");
        try{
            FileUtils.writeLines(todoFile, items);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void addControls() {
        btnAdd= (Button) findViewById(R.id.btnAdd);
        txtAddNew= (EditText) findViewById(R.id.txtAddNew);
        lvItems = (ListView) findViewById(R.id.lvItems);

        items = new ArrayList<>();
        readItemsFromTextFile();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            int pos = data.getIntExtra("pos", 0);
            String text = data.getStringExtra("result");
            items.set(pos, text);
            itemsAdapter.notifyDataSetChanged();
            writeItemsToTextFile();
        }
    }
}
