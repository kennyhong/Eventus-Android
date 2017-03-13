package com.example.baronvonfaustiii.eventus_android.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.baronvonfaustiii.eventus_android.R;
import com.example.baronvonfaustiii.eventus_android.model.Event;
import com.example.baronvonfaustiii.eventus_android.ui.ViewEventActivity;

import java.util.ArrayList;


public class EventListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final ArrayList<Event> events;

    public EventListAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_event_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((EventViewHolder) holder).bindView(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events != null ? events.size() : 0;
    }

    public void refresh(ArrayList<Event> events) {
        this.events.clear();
        this.events.addAll(events);
        notifyDataSetChanged();
    }

    public void add(Event event){
        events.add(event);
        notifyDataSetChanged();
    }

    public Event getEventByTitle(String title)
    {
        Event result = null;

        for(int i = 0 ; i < events.size(); i++)
        {
            if(events.get(i).getName().equals(title))
            {
                result = events.get(i);
            }
        }

        return result;
    }

    public Event getEvent(Event event)
    {
        Event result = null;

        int id = event.getID();

        for(int i = 0; i < events.size(); i++)
        {
            if(events.get(i).getID() == id)
            {
                result = events.get(i);
                break;
            }
        }

        return result;
    }


    public void remove(Event event){
        events.remove(event);
        notifyDataSetChanged();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
//        private final View view;
        private final TextView textName;
        private final TextView textDescription;

        public EventViewHolder(View v){
            super(v);
            this.textName = (TextView) v.findViewById(R.id.eventItemName);
            this.textDescription = (TextView) v.findViewById(R.id.eventItemDescription);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ViewEventActivity.class);
                    intent.putExtra(ViewEventActivity.EXTRA_EVENT, events.get(getAdapterPosition()));
                    ((Activity)context).startActivityForResult(intent,2);
                }
            });
        }
        public void bindView(final Event event) {
            // Populate the elements
            textName.setText(event.getName());
            textDescription.setText((event.getDescription()));
        }
    }
}
