package com.example.najm.annonce;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.example.najm.annonce.Fragment.AnnonceDetailsFragment;
import com.example.najm.annonce.Fragment.AnnonceListFragment;
import com.example.najm.annonce.Fragment.Annonce_Sauve;
import com.example.najm.annonce.Fragment.NavigationDrawerFragment;
import com.example.najm.annonce.Fragment.Profil;
import com.example.najm.annonce.Fragment.Search;
import com.example.najm.annonce.Fragment.User_Annonce;
import com.example.najm.annonce.Helpers.Enums;


public class Menu_App extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_app);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment mFragment = null;
        FragmentManager fragmentManager =  getSupportFragmentManager();
        switch (position){
            case 0:
                mFragment = AnnonceListFragment.newInstance(0);
                break;
            case 1 :
                mFragment = User_Annonce.newInstance(1);
                break;
            case 2:
                mFragment = Search.newInstance(2);
                break;
            case 3:
                mFragment = Profil.newInstance(3);
                break;
            case 4:
                mFragment = Annonce_Sauve.newInstance(4);
                break;
            case 5:
                mFragment = main.newInstance(5);
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, mFragment)
                .addToBackStack(null)
                .commit();
          }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:

                break;
            case 1:

                break;

            case 2:

                break;
            case 3:

                break;
            case 4:
                /*mTitle = getString(R.string.title_section5);*/
                break;
        }
    }


    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement



        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }*/

    public void ReplaceFragment(Enums.FragmentEnums frag, int sectionNumber, long itemId){

        NavigationDrawerFragment navFrag = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (frag){
            case AnnonceDetailsFragment:
                AnnonceDetailsFragment customerFrag = AnnonceDetailsFragment.newInstance(sectionNumber, itemId);
                navFrag.mDrawerToggle.setDrawerIndicatorEnabled(false);
                fragmentManager.beginTransaction().replace(R.id.container, customerFrag)
                        .addToBackStack(null).commit();
                break;



        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavigationDrawerFragment navFrag = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        navFrag.mDrawerToggle.setDrawerIndicatorEnabled(true);
        onSectionAttached(navFrag.mCurrentSelectedPosition);
    }


}