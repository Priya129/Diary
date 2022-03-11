package com.example.chalnafirebase;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
public class Enterpassword extends AppCompatActivity {
    TextInputEditText editText;
    Button button;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterpassword);
        SharedPreferences settings = getSharedPreferences("PREPS",0);
        password = settings.getString("password","");
        editText = findViewById(R.id.editText3);
        button = findViewById(R.id.button2);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.purple));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if(text.equals(password))
                {
                    startActivity(new Intent(Enterpassword.this,DataShow.class));
                    finish();
                } else
                {
                    Toast.makeText(Enterpassword.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if(item_id == R.id.notification)
        {
            startActivity(new Intent(Enterpassword.this,notification.class));
        } else if (item_id == R.id.resetpassword) {
            startActivity(new Intent(Enterpassword.this,Createpassword.class));
        }
        return true;
    }
}