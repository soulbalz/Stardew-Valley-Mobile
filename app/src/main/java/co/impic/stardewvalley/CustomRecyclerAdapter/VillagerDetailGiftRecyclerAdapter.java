package co.impic.stardewvalley.CustomRecyclerAdapter;

/**
 * Created by clOminiC on 3/24/16.
 */

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import co.impic.stardewvalley.R;

public class VillagerDetailGiftRecyclerAdapter extends RecyclerView.Adapter<VillagerDetailGiftRecyclerAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private JSONArray mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewDesc;
        private final ImageView imageViewItem;

        public ViewHolder(View v) {
            super(v);
            textViewName = (TextView) v.findViewById(R.id.txt_villager_gift_name);
            textViewDesc = (TextView) v.findViewById(R.id.txt_villager_gift_found_in);
            imageViewItem = (ImageView) v.findViewById(R.id.icon_villager_item);
        }

    }

    public VillagerDetailGiftRecyclerAdapter(JSONArray dataSet) {
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recyclerview_layout_villager_detail_gift, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        try {
            viewHolder.textViewName.setText(mDataSet.getJSONObject(position).getString("name"));
            viewHolder.textViewDesc.setText(Html.fromHtml("<b>แหล่งที่พบ:</b> " + mDataSet.getJSONObject(position).getString("found_in")));
            if (mDataSet.getJSONObject(position).getString("image").length() != 0) {
                Picasso.with(viewHolder.imageViewItem.getContext())
                        .load(mDataSet.getJSONObject(position).getString("image")).into(viewHolder.imageViewItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (null != mDataSet ? mDataSet.length() : 0);
    }
}