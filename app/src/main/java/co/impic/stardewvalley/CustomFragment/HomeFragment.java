package co.impic.stardewvalley.CustomFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import co.impic.stardewvalley.R;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
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

        String[] splitSize = adView.getAdSize().toString().split("_");
        String[] splitSize2 = splitSize[0].split("x");

        final float scale = getResources().getDisplayMetrics().density;
        Integer bottomSize = (int) (Integer.parseInt(splitSize2[1]) * scale + 0.5f);

        LinearLayout mLinearLayout = (LinearLayout) view.findViewById(R.id.HomeLayOut);
        mLinearLayout.setPadding(16, 16, 16, bottomSize);
    }
}
