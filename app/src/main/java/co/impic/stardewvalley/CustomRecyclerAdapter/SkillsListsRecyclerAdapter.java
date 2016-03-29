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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import co.impic.stardewvalley.CustomFragment.SkillsListsFragment;
import co.impic.stardewvalley.CustomLinearLayoutManager;
import co.impic.stardewvalley.R;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class SkillsListsRecyclerAdapter extends RecyclerView.Adapter<SkillsListsRecyclerAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private JSONArray mDataSet;
    private Activity mContext;
    private FragmentManager mFragmentManager;
    private String mSkillName;

    private SkillItemsRecyclerAdapter adapter;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;

        private final TextView textViewSkillsName;
        private final TextView textViewSkillsDesc;

        private final TextView textViewSkillName;
        RecyclerView childRecyclerView;
        RecyclerView.LayoutManager layoutManager;

        public ViewHolder(View v) {
            super(v);
            mView = v;

            textViewSkillsName = (TextView) mView.findViewById(R.id.txt_skills_name);
            textViewSkillsDesc = (TextView) mView.findViewById(R.id.txt_skills_desc);

            textViewSkillName = (TextView) mView.findViewById(R.id.txt_skill_name);
            childRecyclerView = (RecyclerView) mView.findViewById(R.id.SkillItemsRecyclerView);
            layoutManager = new CustomLinearLayoutManager(mView.getContext());
        }

        public int setCicked(final String name, final Activity context, final FragmentManager fragmentManager){
            // Define click listener for the ViewHolder's View.
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SkillsListsFragment fragment = SkillsListsFragment.newInstance(name.toLowerCase());

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

    public SkillsListsRecyclerAdapter(Activity context, JSONArray dataSet, FragmentManager fragmentManager, String skillname) {
        mDataSet = dataSet;
        mContext = context;
        mFragmentManager = fragmentManager;
        mSkillName = skillname;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v;
        if (mSkillName == "lists") {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recyclerview_layout_skills_lists, viewGroup, false);
        } else {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recyclerview_layout_skill, viewGroup, false);
        }

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        try {
            if (mSkillName == "lists") {
                viewHolder.textViewSkillsName.setText(mDataSet.getJSONObject(position).getString("name"));
                viewHolder.textViewSkillsDesc.setText(mDataSet.getJSONObject(position).getString("desc"));
                viewHolder.setCicked(mDataSet.getJSONObject(position).getString("name"), mContext, mFragmentManager);
            } else {
                viewHolder.textViewSkillName.setText(mDataSet.getJSONObject(position).getString("title"));

                viewHolder.childRecyclerView.setFocusable(false);
                viewHolder.childRecyclerView.setNestedScrollingEnabled(false);
                viewHolder.childRecyclerView.setLayoutManager(viewHolder.layoutManager);
                adapter = new SkillItemsRecyclerAdapter(mDataSet.getJSONObject(position).getJSONArray("item"));
                viewHolder.childRecyclerView.setAdapter(adapter);
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