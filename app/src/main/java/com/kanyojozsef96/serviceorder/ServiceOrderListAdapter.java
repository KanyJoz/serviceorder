package com.kanyojozsef96.serviceorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class ServiceOrderListAdapter extends RecyclerView.Adapter<ServiceOrderListAdapter.ViewHolder>
        implements Filterable {
    // Member variables.
    private ArrayList<ServiceOrder> serviceOrdersActual;
    private ArrayList<ServiceOrder> serviceOrdersAll;
    private Context mContext;
    private int lastPosition = -1;

    ServiceOrderListAdapter(Context context, ArrayList<ServiceOrder> data) {
        this.serviceOrdersActual = data;
        this.serviceOrdersAll = data;
        this.mContext = context;
    }

    @Override
    public ServiceOrderListAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.service_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ServiceOrderListAdapter.ViewHolder holder, int position) {
        ServiceOrder currentItem = serviceOrdersActual.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentItem);

        if(holder.getAdapterPosition() > lastPosition) {
            Random random = new Random();
            boolean flag = random.nextBoolean();
            Animation animation;
            if(flag) {
                animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            } else {
                animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row_reverse);
            }
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return serviceOrdersActual.size();
    }

    /**
     * RecycleView filter
     * **/
    @Override
    public Filter getFilter() {
        return serviceFilter;
    }

    private Filter serviceFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ServiceOrder> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0) {
                results.count = serviceOrdersAll.size();
                results.values = serviceOrdersAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(ServiceOrder item : serviceOrdersAll) {
                    if(item.getCategory().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            serviceOrdersActual = (ArrayList)filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        // Member Variables for the TextViews
        private TextView category;
        private TextView description;
        private TextView contact;
        private TextView orderDate;

        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            category = itemView.findViewById(R.id.listCategory);
            description = itemView.findViewById(R.id.listDesc);
            contact = itemView.findViewById(R.id.listContact);
            orderDate = itemView.findViewById(R.id.listOrderDate);
        }

        void bindTo(ServiceOrder currentItem){
            category.setText(currentItem.getCategory());
            description.setText(currentItem.getDescription());
            contact.setText(currentItem.getContact());
            orderDate.setText(currentItem.getOrderDate());

            itemView.findViewById(R.id.listModify).setOnClickListener(view -> ((ServiceOrderListActivity)mContext).modifyItem(currentItem));
            itemView.findViewById(R.id.listCancel).setOnClickListener(view -> ((ServiceOrderListActivity)mContext).cancelItem(currentItem));
            itemView.findViewById(R.id.listDelete).setOnClickListener(view -> ((ServiceOrderListActivity)mContext).deleteItem(currentItem));
        }
    }
}
