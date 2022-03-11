package com.example.chalnafirebase;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;

import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class dataActivity extends AppCompatActivity {
    TextView show_title, show_story,text;
    ImageView imageview9;
    CardView cardView;
    FirebaseDatabase database;
    FirebaseStorage storage;
   ActivityResultLauncher<String> launcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        show_title = findViewById(R.id.show_title);
        show_story = findViewById(R.id.show_story);
        text = findViewById(R.id.text);
        cardView = findViewById(R.id.cardView);
        imageview9 = findViewById(R.id.imageview9);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        database.getReference().child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Picasso.get().load(image).into(imageview9);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
              imageview9.setImageURI(result);
              final StorageReference reference = storage.getReference().child("image");
              reference.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                          reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                              @Override
                              public void onSuccess(Uri uri) {
                                       database.getReference().child("image"+ UUID.randomUUID().toString()).setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                           @Override
                                           public void onSuccess(Void unused) {
                                               Toast.makeText(dataActivity.this, "Image Uploaded..", Toast.LENGTH_SHORT).show();
                                           }
                                       });
                              }
                          });
                  }
              });
            }
        });
              cardView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      launcher.launch("image/*");
                  }
              });
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.purple));
        }


        text.setText(getIntent().getStringExtra("data3"));
        show_title.setText(getIntent().getStringExtra("data"));
        show_story.setText(getIntent().getStringExtra("data1"));
        show_story.setMovementMethod(new ScrollingMovementMethod());

    }

}