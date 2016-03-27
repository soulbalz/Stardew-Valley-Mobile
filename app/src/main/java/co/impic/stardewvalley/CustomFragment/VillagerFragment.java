package co.impic.stardewvalley.CustomFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import co.impic.stardewvalley.CustomRecyclerAdapter.VillagerDetailGiftRecyclerAdapter;
import co.impic.stardewvalley.CustomRecyclerAdapter.VillagerDetailHeartLevelsRecyclerAdapter;
import co.impic.stardewvalley.R;

public class VillagerFragment extends Fragment {
    private static final String KEY_FILE = "file";

    private String filename;

    // Recycler View Variable
    protected RecyclerView mRecyclerView;
    protected VillagerDetailHeartLevelsRecyclerAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected JSONArray mDataset;
    protected JSONObject jsonData;

    public VillagerFragment() {
        // Required empty public constructor
    }

    public static VillagerFragment newInstance(String namefile) {

        VillagerFragment fragment = new VillagerFragment();
        Bundle args = new Bundle();
        args.putString(KEY_FILE, namefile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            filename = bundle.getString(KEY_FILE);
        }

        initDataset();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_villager, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AdView adView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("272DDD5399A8569C9C1C6A5967EC5282")
                .build();
        adView.loadAd(adRequest);

        TextView villagerName = (TextView) view.findViewById(R.id.txt_villager_name);
        TextView villagerBirthday = (TextView) view.findViewById(R.id.txt_villager_bithday);
        TextView villagerMarriage = (TextView) view.findViewById(R.id.txt_villager_marrige);
        TextView villagerDetail = (TextView) view.findViewById(R.id.txt_villager_detail);
        ImageView villagerImage = (ImageView) view.findViewById(R.id.icon_villager);

        // Gift
        TextView bestGift = (TextView) view.findViewById(R.id.txt_best_gift);
        TextView goodGift = (TextView) view.findViewById(R.id.txt_good_gift);
        TextView neutralGift = (TextView) view.findViewById(R.id.txt_neutral_gift);
        TextView badGift = (TextView) view.findViewById(R.id.txt_bad_gift);
        TextView worstGift = (TextView) view.findViewById(R.id.txt_worst_gift);

        try {
            villagerName.setText(Html.fromHtml("<b>ชื่อ</b> " + jsonData.getString("name")));
            villagerBirthday.setText(Html.fromHtml("<b>วันเกิด: </b> " + jsonData.getString("birthday")));
            villagerMarriage.setText(jsonData.getString("marriage"));
            villagerDetail.setText(jsonData.getString("desc"));
            if (jsonData.getString("image").length() != 0){
                Picasso.with(villagerImage.getContext())
                    .load(jsonData.getString("image")).into(villagerImage);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            // Set Data To RecyclerView Gifts Best
            DatasetGift((RecyclerView) view.findViewById(R.id.giftBestRecyclerView), jsonData.getJSONObject("gift"), "best", bestGift);

            // Set Data To RecyclerView Gifts Good
            DatasetGift((RecyclerView) view.findViewById(R.id.giftGoodRecyclerView), jsonData.getJSONObject("gift"), "good", goodGift);

            // Set Data To RecyclerView Heart Gifts Neutral
            DatasetGift((RecyclerView) view.findViewById(R.id.giftNeutralRecyclerView), jsonData.getJSONObject("gift"), "neutral", neutralGift);

            // Set Data To RecyclerView  Gifts Bad
            DatasetGift((RecyclerView) view.findViewById(R.id.giftBadRecyclerView), jsonData.getJSONObject("gift"), "bad", badGift);

            // Set Data To RecyclerView  Gifts Worst
            DatasetGift((RecyclerView) view.findViewById(R.id.giftWorstRecyclerView), jsonData.getJSONObject("gift"), "worst", worstGift);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Set Data To RecyclerView Heart Events
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new VillagerDetailHeartLevelsRecyclerAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);


        final ScrollView sv = (ScrollView) view.findViewById(R.id.scrollView);
        sv.isSmoothScrollingEnabled();

        String[] splitSize = adView.getAdSize().toString().split("_");
        String[] splitSize2 = splitSize[0].split("x");

        final float scale = getResources().getDisplayMetrics().density;
        Integer bottomSize = (int) (Integer.parseInt(splitSize2[1]) * scale + 0.5f);

        sv.setPadding(16, 16, 16, bottomSize);
    }

    private void initDataset() {
        JSONArray objArr;
        String json = loadJSONFromAsset(filename);
        try {
            jsonData = new JSONObject(json);

            objArr = jsonData.getJSONArray("heart_events");

            mDataset = objArr;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset(String file) {
        String json = null;
        String gFile = String.format("json/villagers/%s.json", file);
        try {
            InputStream is = getActivity().getAssets().open(gFile);
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

    public void DatasetGift(RecyclerView recyclerView, JSONObject json, String feeling, TextView giftView) {
        JSONArray objArr = null;
        String textData = "";
        try {
            JSONObject json1 = json.getJSONObject(feeling);

            objArr = json1.getJSONArray("item");

            if (json1.getString("en_msg").length() != 0) {
                textData += json1.getString("en_msg") + "<br>";
            }
            if (json1.getString("th_msg").length() != 0) {
                textData += json1.getString("th_msg") + "<br>";
            }
            textData += "<b>หมายเหตุ:</b> " + json1.getString("note");

            giftView.setText(Html.fromHtml(textData));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        VillagerDetailGiftRecyclerAdapter adapter = new VillagerDetailGiftRecyclerAdapter(objArr);

        recyclerView.setFocusable(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


}