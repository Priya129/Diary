package com.example.chalnafirebase;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
public class Createpassword extends AppCompatActivity {
    TextInputEditText editText1, editText2;

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpassword);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        button = findViewById(R.id.button);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.purple));
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = editText1.getText().toString();
                String text2 = editText2.getText().toString();
                if(text1.equals("") || text2.equals(""))
                {
                    Toast.makeText(Createpassword.this, "No Password Enter", Toast.LENGTH_SHORT).show();
                } else
                if(text1.equals(text2))
                {
                    SharedPreferences settings = getSharedPreferences("PREPS",0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("password",text1);
                    editor.apply();
                    startActivity(new Intent(Createpassword.this,DataShow.class));
                    finish();
                }else
                {
                    Toast.makeText(Createpassword.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}