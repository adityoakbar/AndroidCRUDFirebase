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

public class DetilActivity extends AppCompatActivity {
    private EditText editTextKodeDetil, editTextNamaDetil, editTextHargaDetil;
    private Button buttonUpdate, buttonDelete;

    private String key, kode, nama, harga;

    private ProgressDialog loading;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        database = FirebaseDatabase.getInstance().getReference();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sKode = editTextKodeDetil.getText().toString();
                String sNama = editTextNamaDetil.getText().toString();
                String sHarga = editTextHargaDetil.getText().toString();

                if (sKode.equals("")) {
                    editTextKodeDetil.setError("Silahkan masukkan kode barang");
                    editTextKodeDetil.requestFocus();
                } else if (sNama.equals("")) {
                    editTextNamaDetil.setError("Silahkan masukkan nama barang");
                    editTextNamaDetil.requestFocus();
                } else if (sHarga.equals("")) {
                    editTextHargaDetil.setError("Silahkan masukkan harga barang");
                    editTextHargaDetil.requestFocus();
                } else {
                    loading = ProgressDialog.show(DetilActivity.this,
                            null ,
                            "Please wait .... " ,
                            true ,
                            false);

                    editData(new Data(sKode, sNama, sHarga));
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(DetilActivity.this,
                        null ,
                        "Please wait .... " ,
                        true ,
                        false);
                hapusData();
            }
        });

    }

    private void hapusData() {
        database.child("Data")
                .child(key)
                .removeValue()
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        Toast.makeText(DetilActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetilActivity.this, ListActivity.class);
                        startActivity(intent);
                    }
                });
    }

    private void editData(Data data) {
        database.child("Data")
                .child(key)
                .setValue(data)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loading.dismiss();

                        editTextKodeDetil.setText("");
                        editTextNamaDetil.setText("");
                        editTextHargaDetil.setText("");

                        Toast.makeText(DetilActivity.this, "Data berhasil diubah", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetilActivity.this, ListActivity.class);
                        startActivity(intent);
                    }
                });
    }

    private void initViews() {
        editTextKodeDetil = findViewById(R.id.editTextKode);
        editTextNamaDetil = findViewById(R.id.editTextNama);
        editTextHargaDetil = findViewById(R.id.editTextHarga);
        buttonUpdate = findViewById(R.id.buttonInput);
        buttonDelete = findViewById(R.id.buttonView);

        setViews();
    }

    private void setViews() {
        getIntentData();
        editTextKodeDetil.setText(kode);
        editTextNamaDetil.setText(nama);
        editTextHargaDetil.setText(harga);

        buttonUpdate.setText(R.string.btn_update);
        buttonDelete.setText(R.string.btn_delete);
        buttonDelete.setBackground(getDrawable(R.drawable.rounded_background_red));
    }

    private void getIntentData() {
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        kode = intent.getStringExtra("kode");
        nama = intent.getStringExtra("nama");
        harga = intent.getStringExtra("harga");
    }
}
