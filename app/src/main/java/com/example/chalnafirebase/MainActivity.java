package com.example.chalnafirebase;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;

import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.FirebaseFirestore;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;
public class MainActivity extends AppCompatActivity {
    EditText title;
    TextView date;
    EditText story;
    ImageView button;
    ImageView imageView;
    FirebaseFirestore db;
    private String uTitle, uId, uStory, uDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        story = findViewById(R.id.story);
        button = findViewById(R.id.imageView3);
        imageView = findViewById(R.id.imageView2);
        db = FirebaseFirestore.getInstance();
        Bundle bundle = getIntent().getExtras();
        if(bundle!= null)
        {

            uDate = bundle.getString("uDate");
            uTitle = bundle.getString("uTitle");
            uStory = bundle.getString("uStory");
            uId = bundle.getString("uId");
            date.setText(uDate);
            title.setText(uTitle);
            story.setText(uStory);
        }
        date = findViewById(R.id.date);
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(android.icu.text.DateFormat.FULL).format(calendar.getTime());
        date.setText(currentDate);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.purple));
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Date = date.getText().toString();
                String Title = title.getText().toString();
                String Story = story.getText().toString();
                Bundle bundle = getIntent().getExtras();
                if(bundle!= null)
                {
                    String id = uId;
                    updateToFireStore(id,Title,Story);
                } else {
                    String id = UUID.randomUUID().toString();
                    savetoFirestore(id, Title, Story,Date);
                }

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DataShow.class));
            }
        });
    }




    private void updateToFireStore(String id, String title, String story) {
        db.collection("Users").document(id).update("title",title,"story",story).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "Data Update!!", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(MainActivity.this, "Error :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void savetoFirestore( String id, String title, String story, String date) {
        if(!title.isEmpty() && !story.isEmpty())
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put("date",date);
            map.put("id",id);
            map.put("title",title);
            map.put("story",story);
            db.collection("Users").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(MainActivity.this, "Data Saved!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Failed to store!", Toast.LENGTH_SHORT).show();
                }
            });

        } else
        {
            Toast.makeText(MainActivity.this, "Please fill the Field", Toast.LENGTH_SHORT).show();
        }


    }

}