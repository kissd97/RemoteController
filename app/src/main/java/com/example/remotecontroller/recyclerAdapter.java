package com.example.remotecontroller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<Cards> cardList;
    private RecyclerViewListener listener;

    public recyclerAdapter(ArrayList<Cards> cardList, RecyclerViewListener listener){
        this.cardList = cardList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView device_name;
        private TextView device_type;

        public MyViewHolder(final View view){
            super(view);
            device_name = view.findViewById(R.id.deviceNameTV);
            device_type = view.findViewById(R.id.deviceTypeTV);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        String deviceName = cardList.get(position).getDeviceName();
        holder.device_name.setText(deviceName);

        String deviceType = cardList.get(position).getDeviceType();
        holder.device_type.setText(deviceType);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public interface RecyclerViewListener{
        void onClick(View v, int position);
    }
}
