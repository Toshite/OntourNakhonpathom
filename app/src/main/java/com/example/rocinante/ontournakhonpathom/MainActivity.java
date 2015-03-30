package com.example.rocinante.ontournakhonpathom;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends ActionBarActivity {

    // Variables
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private MenuListAdapter mMenuAdapter;
    private String[] title;
    private String[] subtitle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private GoogleMap googleMap;
    private Marker marker;

    private static final LatLng KANCHANABURI = new LatLng(14.040936, 99.503670);
    private static final LatLng NAKONPHATOM = new LatLng(13.819678, 100.060127);
    private static final LatLng RATCHABURI = new LatLng(13.529034, 99.814297);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);

        // Get the title
        mTitle = mDrawerTitle = getTitle();

        // Get the map
        googleMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        // Generate content
        title = new String[] {"เมืองกาญจน์", "เมืองนครปฐม", "เมืองราชบุรี"};
        subtitle = new String[] {"Kanchanaburi", "Nakhonpathom", "Ratchanaburi"};

        // Link the content
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        mDrawerList = (ListView)findViewById(R.id.listview_drawer);

        // Set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mMenuAdapter = new MenuListAdapter(MainActivity.this, title, subtitle);

        mDrawerList.setAdapter(mMenuAdapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // Enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ActionBarDrawerToggle ties together the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close) {

            public void OnDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                // Set the title on the action when drawer open
                getSupportActionBar().setTitle(mDrawerTitle);
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        if (marker != null) {
            marker.remove();
        }
        switch(position) {
            case 0:
                marker = googleMap.addMarker(
                        new MarkerOptions()
                                .position(KANCHANABURI)
                );
                // Move the camera instantly to hamburg with a zoom of 15.
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(KANCHANABURI, 15));
                break;
            case 1:
                marker = googleMap.addMarker(

                        new MarkerOptions()
                                .position(NAKONPHATOM)
                );
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NAKONPHATOM, 15));
                break;
            case 2:
                marker = googleMap.addMarker(
                        new MarkerOptions()
                                .position(RATCHABURI)
                                .title("เมืองราชบุรี")
                );
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(RATCHABURI, 15));
                break;
        }

        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        mDrawerList.setItemChecked(position, true);

        // Get the title followed by the position
        setTitle(title[position]);

        // Close drawer
        mDrawerLayout.closeDrawer(mDrawerList);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

}
