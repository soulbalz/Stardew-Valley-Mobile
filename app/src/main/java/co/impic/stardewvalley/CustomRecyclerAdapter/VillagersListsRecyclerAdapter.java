package co.impic.stardewvalley.CustomRecyclerAdapter;

/**
 * Created by clOminiC on 3/24/16.
 */

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import co.impic.stardewvalley.CustomFragment.VillagerFragment;
import co.impic.stardewvalley.R;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class VillagersListsRecyclerAdapter extends RecyclerView.Adapter<VillagersListsRecyclerAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private JSONArray mDataSet;
    private Activity mContext;
    private FragmentManager mFragmentManager;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;

        private final TextView textViewVillagersName;
        private final ImageView imageViewVillagers;

        public ViewHolder(View v) {
            super(v);
            mView = v;

            textViewVillagersName = (TextView) mView.findViewById(R.id.txt_villagers_name);
            imageViewVillagers = (ImageView) mView.findViewById(R.id.icon_villagers);
        }

        public int setCicked(final String name, final Activity context, final FragmentManager fragmentManager){
            // Define click listener for the ViewHolder's View.
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    VillagerFragment fragment = VillagerFragment.newInstance(name.toLowerCase());

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.content_layout, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                    context.setTitle(name);
                }
            });
            return 0;
        }
    }

    public VillagersListsRecyclerAdapter(Activity context, JSONArray dataSet, FragmentManager fragmentManager) {
        mDataSet = dataSet;
        mContext = context;
        mFragmentManager = fragmentManager;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recyclerview_layout_villagers_lists, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        try {
            viewHolder.textViewVillagersName.setText(mDataSet.getJSONObject(position).getString("name"));
            Picasso.with(viewHolder.imageViewVillagers.getContext())
                    .load(mDataSet.getJSONObject(position).getString("image")).into(viewHolder.imageViewVillagers);

            viewHolder.setCicked(mDataSet.getJSONObject(position).getString("keyword"), mContext, mFragmentManager);
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