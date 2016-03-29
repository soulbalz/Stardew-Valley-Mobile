package co.impic.stardewvalley.CustomFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import co.impic.stardewvalley.AnalyticsApplication;
import co.impic.stardewvalley.CustomRecyclerAdapter.SkillsListsRecyclerAdapter;
import co.impic.stardewvalley.R;

public class SkillsListsFragment extends Fragment {
    private static final String KEY_SKILL = "skills";

    private String skillname;

    // Recycler View Variable
    protected RecyclerView mRecyclerView;
    protected SkillsListsRecyclerAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected JSONArray mDataset;
    protected String jsonString;

    Tracker mTracker;

    public SkillsListsFragment() {
        // Required empty public constructor
    }

    public static SkillsListsFragment newInstance(String keyword) {
        SkillsListsFragment fragment = new SkillsListsFragment();
        Bundle args = new Bundle();
        args.putString(KEY_SKILL, keyword);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            skillname = bundle.getString(KEY_SKILL);
        }

        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_skills_lists, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Skills Lists Page");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        AdView adView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("272DDD5399A8569C9C1C6A5967EC5282")
                .build();
        adView.loadAd(adRequest);

        TextView skillDetail = (TextView) view.findViewById(R.id.txt_skills_detail);
        skillDetail.setText(jsonString);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.SkillsListsRecyclerView);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setNestedScrollingEnabled(false);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SkillsListsRecyclerAdapter(getActivity(), mDataset, getFragmentManager(), skillname);

        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);

        final ScrollView sv = (ScrollView) view.findViewById(R.id.scrollView);
        sv.isSmoothScrollingEnabled();

        String[] splitSize = adView.getAdSize().toString().split("_");
        String[] splitSize2 = splitSize[0].split("x");

        final float scale = getResources().getDisplayMetrics().density;
        Integer bottomSize = (int) (Integer.parseInt(splitSize2[1]) * scale + 0.5f);

        sv.setPadding(16, 16, 16, bottomSize);
    }

    @Override
    public void onResume() {
        super.onResume();

        mTracker.setScreenName("Villagers Skills Page");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void initDataset() {
        JSONArray objArr = null;
        String json = loadJSONFromAsset();
        try {
            JSONObject obj = new JSONObject(json);
            if (skillname == "lists"){
                objArr = obj.getJSONArray("skill_name");
                jsonString = obj.getString("desc");
            } else {
                JSONObject obj2 = obj.getJSONObject("skill");
                JSONObject obj3 = obj2.getJSONObject(skillname);
                objArr = obj3.getJSONArray("level");
                jsonString = obj3.getString("desc");
            }

            mDataset = objArr;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("json/skills.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
