package com.example.ticket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class TicketAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<Ticket> personArrayList;
    private ArrayList<Ticket> filterList;
    private CustomFilter filter;

    public TicketAdapter(Context context, ArrayList<Ticket> personArrayList) {
        this.context = context;
        this.personArrayList = personArrayList;
        this.filterList = personArrayList;
    }

    @Override
    public int getCount() {
        return personArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return personArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.activity_main2, null);
        }
        Ticket ticket = (Ticket) getItem(position);
        if ((ticket != null)) {
            TextView textAddr = (TextView) view.findViewById(R.id.textView);
            TextView textPrice = (TextView) view.findViewById(R.id.textView3);
            TextView textType = (TextView) view.findViewById(R.id.textView2);

            String s = ticket.getGaDi() + " -> " + ticket.getGaDen();
            textAddr.setText(s);
            textPrice.setText(ticket.getDonGia() + "");
            String t = (ticket.getTheLoai() == 1) ? "Khứ hồi" : "Một chiều";
            textType.setText(t);
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter();
        }
        return filter;
    }

    private class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<Ticket> filters = new ArrayList<>();
                for (int i = 0; i < filterList.size(); i++) {
                    if (filterList.get(i).getGaDen().toUpperCase().contains(constraint)) {
                        Ticket person = new Ticket(filterList.get(i).getId(), filterList.get(i).getGaDen(), filterList.get(i).getGaDi(), filterList.get(i).getDonGia(), filterList.get(i).getTheLoai());
                        filters.add(person);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            } else {
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            personArrayList = (ArrayList<Ticket>) results.values;
            notifyDataSetChanged();
        }
    }
}
