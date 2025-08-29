package com.reyson.spotd.Class.Components.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reyson.spotd.Data.Model.GroupedNotification;
import com.reyson.spotd.R;

import java.util.ArrayList;

public class NotifyParentAdapter extends RecyclerView.Adapter<NotifyParentAdapter.ViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<GroupedNotification> notifications;
    private LayoutInflater inflater;
    private Context context;

    public NotifyParentAdapter(Context context, ArrayList<GroupedNotification> notifications) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.fragment_parent_notify, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        GroupedNotification notification = notifications.get(position);
        holder.snrpnl_header.setText(notification.getHeader());
        NotifyChildAdapter childAdapter = new NotifyChildAdapter(context, notification.getData());
        holder.snrpnl_recyclerview.setAdapter(childAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.snrpnl_recyclerview.getContext(), LinearLayoutManager.VERTICAL, false);
        holder.snrpnl_recyclerview.setLayoutManager(layoutManager);
        holder.snrpnl_recyclerview.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView snrpnl_header;
        RecyclerView snrpnl_recyclerview;
        public ViewHolder(@NonNull View v) {
            super(v);
            snrpnl_recyclerview = v.findViewById(R.id.snrpnl_recyclerview);
            snrpnl_header = v.findViewById(R.id.snrpnl_header);

        }
    }
}