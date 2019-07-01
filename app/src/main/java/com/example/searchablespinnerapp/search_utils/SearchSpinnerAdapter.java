package com.example.searchablespinnerapp.search_utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.searchablespinnerapp.R;

import java.util.ArrayList;

/**
 * Created by Anurag on 28/6/19.
 */
public class SearchSpinnerAdapter<T> extends RecyclerView.Adapter<SearchSpinnerAdapter.ViewHolder> implements Filterable {

    private final SearchInterface searchInterface;
    private ArrayList<T> mArrayList = new ArrayList<>();
    private ArrayList<T> mFilteredList = new ArrayList<>();
    private CustomFilter customFilter = null;
    private int tag;

    public SearchSpinnerAdapter(ArrayList<T> connectionName, SearchInterface searchInterface) {
        customFilter = new CustomFilter();
        this.searchInterface = searchInterface;
        this.mArrayList = connectionName;
        this.mFilteredList = connectionName;
    }
    public SearchSpinnerAdapter(ArrayList<T> connectionName, SearchInterface searchInterface,int tag) {
        this.tag = tag;
        customFilter = new CustomFilter();
        this.searchInterface = searchInterface;
        this.mArrayList = connectionName;
        this.mFilteredList = connectionName;
    }

    @NonNull
    @Override
    public SearchSpinnerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new SearchSpinnerAdapter.ViewHolder(viewItem, searchInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchSpinnerAdapter.ViewHolder holder, final int position) {
        Object item = mFilteredList.get(position);
        if (item instanceof DialogSearchable) {
            String searchableFiled = ((DialogSearchable) item).getSearchableField();
            holder.txtItemName.setText(searchableFiled);
        } else if (item instanceof String) {
            String searchableFiled = (String) item;
            holder.txtItemName.setText(searchableFiled);

        }
    }

    @Override
    public int getItemCount() {
        if (mFilteredList != null) {
            return mFilteredList.size();
        } else
            return 0;
    }

    @Override
    public Filter getFilter() {
        return customFilter;
    }

   public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtItemName;
        private LinearLayout llRootView;
        private SearchInterface searchInterface;

        public ViewHolder(View itemView, SearchInterface searchInterface) {
            super(itemView);
            txtItemName = (TextView) itemView.findViewById(R.id.txt_item_name);
            llRootView = (LinearLayout) itemView.findViewById(R.id.ll_rootview);
            this.searchInterface = searchInterface;

            llRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ViewHolder.this.searchInterface.setSearchClickListener(getPositionByName(),tag);
                }
            });

        }

        private int getPositionByName() {
            int adapterPosition = getAdapterPosition();
            Object filteredString = mFilteredList.get(adapterPosition);
            for (int i = 0; i < mArrayList.size(); i++) {
                if (mArrayList.get(i).equals(filteredString)) {
                    return i;
                }
            }
            return adapterPosition;
        }
    }


    public class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();

            if (charString.isEmpty()) {
                mFilteredList = mArrayList;
            } else {
                ArrayList<T> filteredList = new ArrayList<>();
                for (int i = 0; i < mArrayList.size(); i++) {
                    T item = mArrayList.get(i);
                    if (item instanceof DialogSearchable) {
                        String searchableFiled = ((DialogSearchable) item).getSearchableField();
                        if (searchableFiled.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            filteredList.add(item);
                        }
                    } else if (item instanceof String) {
                        String searchableFiled = (String) item;

                        if (searchableFiled.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            filteredList.add(item);
                        }

                    }

                }
                mFilteredList = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = mFilteredList;
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mFilteredList = (ArrayList<T>) filterResults.values;
            notifyDataSetChanged();
        }
    }


}
