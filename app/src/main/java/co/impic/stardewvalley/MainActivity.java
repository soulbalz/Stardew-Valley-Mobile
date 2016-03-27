package co.impic.stardewvalley;

/**
 * Created by clOminiC on 3/22/16.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import co.impic.stardewvalley.CustomFragment.HomeFragment;
import co.impic.stardewvalley.CustomFragment.VillagerFragment;
import co.impic.stardewvalley.CustomFragment.VillagersListsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    private static long back_pressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        HomeFragment fragment = null;
        try {
            fragment = HomeFragment.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_layout, fragment);
        transaction.commit();

    }

    public void openBrowser(View view){

        //Get url from tag
        String url = (String)view.getTag();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);

        //pass the url to intent data
        intent.setData(Uri.parse(url));

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            if (back_pressed + 2000 > System.currentTimeMillis()) {
                finish(); /** otherwise directly exit from here...**/
            } else {
                Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
            }
            back_pressed = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean is_fragment = false;

        Fragment fragment = null;

        switch(id) {
            case R.id.back_to_main:
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.activity_main_drawer);
                break;
            case R.id.nav_home:
                is_fragment = true;
                fragment = HomeFragment.newInstance();
                break;
            case R.id.nav_villagers:
                is_fragment = true;
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.villagers_submenu_drawer);
                fragment = VillagersListsFragment.newInstance();
                break;
            case R.id.villagers_sub_mrqi:
                is_fragment = true;
                fragment = VillagerFragment.newInstance("mrqi");
                break;
            default:
                is_fragment = true;
                fragment = VillagerFragment.newInstance(((String) item.getTitle()).toLowerCase());
        }

        if (is_fragment){

            // Insert the fragment by replacing any existing fragment
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_layout, fragment);
            if (id != R.id.nav_home) {
                transaction.addToBackStack(null);
            }
            transaction.commit();

            // Set action bar title
            if (id == R.id.nav_home) {
                setTitle("Stardew Valley");
            } else {
                setTitle(item.getTitle());
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }

        return true;
    }
}