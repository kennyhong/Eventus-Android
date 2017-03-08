package com.example.baronvonfaustiii.eventus_android.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.baronvonfaustiii.eventus_android.R;
import com.example.baronvonfaustiii.eventus_android.model.Service;
import com.example.baronvonfaustiii.eventus_android.ui.ViewServiceActivity;

import java.util.ArrayList;

/**
 * Created by Bailey on 2/26/2017.
 */

public class ServiceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final ArrayList<Service> services;

    public ServiceListAdapter(Context context, ArrayList<Service> services) {
        this.context = context;
        this.services = services;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ServiceViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_event_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ServiceViewHolder) holder).bindView(services.get(position));
    }

    @Override
    public int getItemCount() {
        return services != null ? services.size() : 0;
    }

    public void refresh(ArrayList<Service> events) {
        this.services.clear();
        this.services.addAll(services);
        notifyDataSetChanged();
    }

    public void add(Service event){
        services.add(event);
        notifyDataSetChanged();
    }

    public void remove(Service event){
        services.remove(event);
        notifyDataSetChanged();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder {
//        private final View view;
        private final TextView textName;
        private final TextView textDescription;

        public ServiceViewHolder(View v){
            super(v);
            this.textName = (TextView) v.findViewById(R.id.eventItemName);
            this.textDescription = (TextView) v.findViewById(R.id.eventItemDescription);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ViewServiceActivity.class);
                    intent.putExtra(ViewServiceActivity.EXTRA_SERVICE, services.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
        public void bindView(final Service service) {
            // Populate the elements
            textName.setText(service.getName());
            textDescription.setText((service.getDescription()));
        }
    }
}
