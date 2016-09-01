package com.example.admin.emergencyservicecontact;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;
import android.support.v7.app.ActionBarDrawerToggle;


public class MainActivity extends AppCompatActivity {

    Intent intent;
    public String nationPassData;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toogle;
    NavigationView navigationView;
    private Toolbar toolbar;
    FragmentTransaction fragmentTransaction;
    TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //shared preference
        SharedPreferences sp = this.getSharedPreferences("Checking", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sp.edit();
        Boolean isFirstRun = sp.getBoolean("checkingFirstRun", true);
        //checking first run
        if(isFirstRun) {
            Intent intent = new Intent(this, Splash.class);
            startActivity(intent);
        }
        //===========================================================================
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupDrawerContent(navigationView);

        nationPassData = sp.getString("nationNamePassData", "");
        //set toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleText = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //ganti title sesuai nation yg dipilih
        titleText.setText(nationPassData);

        //connecting toolbar to drawer layout
        toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toogle);

        //set fragment
        FragmentMain fragmentMain = new FragmentMain();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragmentMain);
        fragmentTransaction.commit();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    //note: ubah settings jdi about> item navigation drawer belum unchecked kl press back
    //sama klo bisa tambahin feature change language
    public void selectDrawerItem(MenuItem menuItem) {

        switch(menuItem.getItemId()) {
            case R.id.call_id:
                FragmentMain fragmentMain = new FragmentMain();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragmentMain);
                fragmentTransaction.commit();
                //intent = new Intent(MainActivity.this, LandingPage.class);
                //startActivity(intent);
                break;
            case R.id.changing_id:
                intent = new Intent(MainActivity.this, LandingPage.class);
                startActivity(intent);
                break;
            case R.id.about_id:
                FragmentAboutPage fragmentAboutPage = new FragmentAboutPage();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragmentAboutPage);
                fragmentTransaction.commit();
                //titleText = (TextView) toolbar.findViewById(R.id.toolbar_title);
                titleText.setText("About");
                //intent = new Intent(MainActivity.this, FragmentAboutPage.class);
                //startActivity(intent);
                break;
        }
        drawerLayout.closeDrawers();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toogle.syncState();
    }

    /*public void onClickEmergency(View view) {
        intent = new Intent(this, ServicePage.class);
        String nationResult = nationPassData;
        intent.putExtra("nationResult", nationResult);
        startActivity(intent);
    }*/

    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.drawer_menu);
        navigationView.getMenu().findItem(R.id.changing_id).setChecked(false);
        navigationView.getMenu().findItem(R.id.settings_id).setChecked(false);
        return true;
    }*/

    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (toogle.onOptionsItemSelected(item)) {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            // Pass any configuration change to the drawer toggles
            toogle.onConfigurationChanged(newConfig);
        }
    */

    @Override
    public void onBackPressed() {
        intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }
}