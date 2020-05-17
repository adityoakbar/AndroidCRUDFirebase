package com.agastya.firebasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EditText editTextKode, editTextNama, editTextHarga;
    private Button buttonInput, buttonView;
    private ProgressDialog loading;
    private DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        database = FirebaseDatabase.getInstance().getReference();

        buttonInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sKode = editTextKode.getText().toString();
                String sNama = editTextNama.getText().toString();
                String sHarga = editTextHarga.getText().toString();

                if (sKode.equals("")) {
                    editTextKode.setError("Silahkan masukkan kode barang");
                    editTextKode.requestFocus();
                } else if (sNama.equals("")) {
                    editTextNama.setError("Silahkan masukkan nama barang");
                    editTextNama.requestFocus();
                } else if (sHarga.equals("")) {
                    editTextHarga.setError("Silahkan masukkan harga barang");
                    editTextHarga.requestFocus();
                } else {
                    loading = ProgressDialog.show(MainActivity.this,
                            null ,
                            "Please wait .... " ,
                            true ,
                            false);

                    submitData(new Data(sKode, sNama, sHarga));
                }
            }
        });

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

    }

    private void submitData(Data data) {
        database.child("Data")
//                .child("data2")
                .push()
                .setValue(data)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        loading.dismiss();

                        editTextKode.setText("");
                        editTextNama.setText("");
                        editTextHarga.setText("");

                        Toast.makeText(MainActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initViews() {
        editTextKode = findViewById(R.id.editTextKode);
        editTextNama = findViewById(R.id.editTextNama);
        editTextHarga = findViewById(R.id.editTextHarga);
        buttonInput = findViewById(R.id.buttonInput);
        buttonView = findViewById(R.id.buttonView);
    }
}
