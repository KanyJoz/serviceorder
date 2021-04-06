package com.example.shop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ShoppingItemAdapter extends RecyclerView.Adapter<ShoppingItemAdapter.ViewHolder> implements Filterable {
    private ArrayList<ShoppingItem> mShoppingItemData;
    private ArrayList<ShoppingItem> mShoppingItemDataAll;
    private Context mContext;
    private int lastPosition = -1;


    ShoppingItemAdapter(Context context, ArrayList<ShoppingItem> itemsData) {
        this.mShoppingItemData = itemsData;
        this.mShoppingItemDataAll = itemsData;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ShoppingItemAdapter.ViewHolder holder, int position) {
        ShoppingItem currentItem = mShoppingItemData.get(position);

        holder.bindTo(currentItem);
    }

    @Override
    public int getItemCount() {
        return mShoppingItemData.size();
    }

    @Override
    public Filter getFilter() {
        return shoppingFilter;
    }

    private Filter shoppingFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ShoppingItem> filtererList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(constraint == null || constraint.length() == 0){
                results.count = mShoppingItemDataAll.size();
                results.values = mShoppingItemDataAll;
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(ShoppingItem item : mShoppingItemDataAll){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filtererList.add(item);
                    }
                }

                results.count = filtererList.size();
                results.values = filtererList;
            }


            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mShoppingItemData = (ArrayList) results.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mInfo;
        private TextView mPrice;
        private ImageView mItem;
        private RatingBar mRatingBar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.itemTitle);
            mInfo = itemView.findViewById(R.id.subTitle);
            mPrice = itemView.findViewById(R.id.price);
            mItem = itemView.findViewById(R.id.itemImage);
            mRatingBar = itemView.findViewById(R.id.ratingBar);

            itemView.findViewById(R.id.add_to_cart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Acticity", "Add card button clicked");
                }
            });
        }

        public void bindTo(ShoppingItem currentItem) {
            mTitle.setText(currentItem.getName());
            mInfo.setText(currentItem.getInfo());
            mPrice.setText(currentItem.getPrice());
            mRatingBar.setRating(currentItem.getRatedInfo());

            Glide.with(mContext).load(currentItem.getImageResource()).into(mItem);
        }


    }
}
