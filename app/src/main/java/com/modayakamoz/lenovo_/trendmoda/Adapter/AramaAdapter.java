package com.modayakamoz.lenovo_.trendmoda.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.modayakamoz.lenovo_.trendmoda.Object.AliciObject;
import com.modayakamoz.lenovo_.trendmoda.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo- on 29.11.2017.
 */

public class AramaAdapter extends ArrayAdapter<AliciObject> {

    Context context;
    int  textViewResourceId;
    List<AliciObject> items, tempItems, suggestions;

    public AramaAdapter(Context context,  int textViewResourceId, List<AliciObject> items) {
        super(context,  textViewResourceId, items);
        this.context = context;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<AliciObject>(items); // this makes the difference.
        suggestions = new ArrayList<AliciObject>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_people, parent, false);
        }
        AliciObject people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(people.getAd()+" "+people.getSoyad()+" "+people.getTel());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((AliciObject) resultValue).getAd()+" "+((AliciObject) resultValue).getSoyad()+" "+((AliciObject) resultValue).getTel();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (AliciObject people : tempItems) {
                    if ((people.getAd()+" "+people.getSoyad()+" "+people.getTel()).toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<AliciObject> filterList = (ArrayList<AliciObject>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (AliciObject people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
