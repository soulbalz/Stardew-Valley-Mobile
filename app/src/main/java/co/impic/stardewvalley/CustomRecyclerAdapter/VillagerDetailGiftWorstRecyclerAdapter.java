package co.impic.stardewvalley.CustomRecyclerAdapter;

/**
 * Created by clOminiC on 3/24/16.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import co.impic.stardewvalley.R;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class VillagerDetailGiftWorstRecyclerAdapter extends RecyclerView.Adapter<VillagerDetailGiftWorstRecyclerAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private JSONArray mDataSet;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewDesc;
        private final TextView textViewVillagersName;
        private final ImageView imageViewVillagers;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getPosition() + " clicked.");
                }
            });
            textViewName = (TextView) v.findViewById(R.id.txt_villager_heart_name);
            textViewDesc = (TextView) v.findViewById(R.id.txt_villager_heart_desc);

            textViewVillagersName = (TextView) v.findViewById(R.id.txt_villagers_name);
            imageViewVillagers = (ImageView) v.findViewById(R.id.icon_villagers);
        }

    }

    public VillagerDetailGiftWorstRecyclerAdapter(JSONArray dataSet) {
        mDataSet = dataSet;
        System.out.println(dataSet);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recyclerview_layout_villager_detail_heart_levels, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        try {
            viewHolder.textViewName.setText(mDataSet.getJSONObject(position).getString("name"));
            viewHolder.textViewDesc.setText(mDataSet.getJSONObject(position).getString("desc"));
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