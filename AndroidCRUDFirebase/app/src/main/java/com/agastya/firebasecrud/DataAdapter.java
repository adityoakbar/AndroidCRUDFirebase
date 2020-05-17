package com.agastya.firebasecrud;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.HolderData> {
    private List<Data> mList ;
    private Context context;

    public DataAdapter(Context context , List<Data> mList) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_barang, viewGroup, false);
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holderData, int position) {
        final Data item = mList.get(position);

        try {
            holderData.textViewKode.setText(item.getKode());
            holderData.textViewNama.setText(item.getNama());
            holderData.textViewHarga.setText("Rp. " + item.getHarga());
        } catch (Exception e) {
            e.printStackTrace();
        }

        holderData.cardViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetilActivity.class);
                intent.putExtra("key", item.getKey());
                intent.putExtra("kode", item.getKode());
                intent.putExtra("nama", item.getNama());
                intent.putExtra("harga", item.getHarga());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView textViewKode, textViewNama, textViewHarga;
        CardView cardViewItem;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            textViewKode = itemView.findViewById(R.id.textViewKode);
            textViewNama = itemView.findViewById(R.id.textViewNama);
            textViewHarga = itemView.findViewById(R.id.textViewHarga);
            cardViewItem = itemView.findViewById(R.id.cardViewItem);
        }
    }
}
