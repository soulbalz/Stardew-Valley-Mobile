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

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class SkillItemsRecyclerAdapter extends RecyclerView.Adapter<SkillItemsRecyclerAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private JSONArray mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewSkillItemName;
        private final ImageView imageItemView;

        public ViewHolder(View v) {
            super(v);

            textViewSkillItemName = (TextView) v.findViewById(R.id.txt_skill_item_name);
            imageItemView = (ImageView) v.findViewById(R.id.icon_skill_item);
        }
    }

    public SkillItemsRecyclerAdapter(JSONArray dataSet) {
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recyclerview_layout_skill_items, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        try {
            viewHolder.textViewSkillItemName.setText(Html.fromHtml(mDataSet.getJSONObject(position).getString("name")));
            if (mDataSet.getJSONObject(position).getString("image").length() != 0) {
                Picasso.with(viewHolder.imageItemView.getContext())
                        .load(mDataSet.getJSONObject(position).getString("image")).into(viewHolder.imageItemView);
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