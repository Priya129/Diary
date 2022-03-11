package com.example.chalnafirebase;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private DataShow activity;
    private List<Model> mlist;
    private final NewInterface newInterface;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyAdapter(DataShow activity, List<Model> mlist, NewInterface newInterface) {
        this.activity = activity;
        this.mlist = mlist;
        this.newInterface = newInterface;


    }
    public void updateData(int position)
    {
        Model item = mlist.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId", item.getId());
        bundle.putString("uTitle", item.getTitle());
        bundle.putString("uStory", item.getStory());
        bundle.putString("uDate",item.getDate());
        Intent intent = new Intent(activity,MainActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
    public void deleteData(int position)
    {
        Model item = mlist.get(position);
        db.collection("Users").document(item.getId()).delete().
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            notifyRemoved(position);
                            Toast.makeText(activity, "Your Data is deleted!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void notifyRemoved(int position)
    {
        mlist.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Model model = mlist.get(position);
        holder.title.setText(model.getTitle());
        holder.date.setText(model.getDate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newInterface.OnItemClicked(mlist.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView date;
        TextView title;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            cardView = itemView.findViewById(R.id.main_cardview);
            date = itemView.findViewById(R.id.date_text);
        }
    }
}

