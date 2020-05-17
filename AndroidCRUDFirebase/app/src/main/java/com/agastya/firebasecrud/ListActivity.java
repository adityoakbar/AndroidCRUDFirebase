package com.agastya.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private DatabaseReference database;
    private ProgressDialog loading;
    private RecyclerView recyclerViewItem;
    private DataAdapter dataAdapter;
    private ArrayList<Data> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        database = FirebaseDatabase.getInstance().getReference();

        recyclerViewItem = findViewById(R.id.recyclerViewItem);

        RecyclerView.LayoutManager mLayyoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewItem.setLayoutManager(mLayyoutManager);
        recyclerViewItem.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(ListActivity.this,
                null ,
                "Please wait .... " ,
                true ,
                false);

        database.child("Data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mList = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    try {
                        Data data = noteDataSnapshot.getValue(Data.class);
                        data.setKey(noteDataSnapshot.getKey());
                        mList.add(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                dataAdapter = new DataAdapter(ListActivity.this, mList);
                recyclerViewItem.setAdapter(dataAdapter);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
                loading.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ListActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
