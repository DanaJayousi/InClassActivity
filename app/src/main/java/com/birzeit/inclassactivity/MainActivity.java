package com.birzeit.inclassactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText bookTitle;
    EditText authorName;
    EditText pages;
    Switch available;
    Button add;
    Button save;
    SharedPrefManager sharedPrefManager;
    ArrayList<Book> books = new ArrayList<>();
    int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
        add.setOnClickListener(view -> {
            books.add(new Book(bookTitle.getText().toString(),
                    authorName.getText().toString(),
                    Integer.parseInt(pages.getText().toString()),
                    available.isChecked()));
            bookTitle.setText("");
            authorName.setText("");
            pages.setText("");
            available.setChecked(false);
        });
        save.setOnClickListener(view -> {
            Gson gson = new Gson();
            for ( ; counter < books.size(); counter++){
                String bookString = gson.toJson(books.get(counter));
                if(sharedPrefManager.writeString(String.valueOf(counter),bookString))
                    Toast.makeText(this,"saved" + bookString, Toast.LENGTH_SHORT).show();
                else Toast.makeText(this,"FAILED",Toast.LENGTH_SHORT).show();
            }
            sharedPrefManager.writeInt("counter", counter);
        });
    }

    private void setup() {
        bookTitle = findViewById(R.id.bookTitle);
        authorName = findViewById(R.id.authorName);
        pages = findViewById(R.id.pages);
        available = findViewById(R.id.available);
        add = findViewById(R.id.addBtn);
        save = findViewById(R.id.saveBtn);
        sharedPrefManager = SharedPrefManager.getInstance(this);
        counter = sharedPrefManager.readInt("counter",0);

    }
}