package com.pango.comunicaciones;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.SubMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.Field;

import layout.FragmentInicio;
import layout.FragmentNoticias;

public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        FragmentInicio.OnFragmentInteractionListener,
        FragmentNoticias.OnFragmentInteractionListener{

    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    private enum NavigationFragment{
        Inicio,
        Noticias,
        Publicaciones,
        Imagenes,
        Videos
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent toReservaTicketFiltro = new Intent(getApplicationContext(), ReservaTicketFiltro.class);
        startActivity(toReservaTicketFiltro);
        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // ponemos el contenido inicial
        ChangeFragment(NavigationFragment.Inicio);
        uncheckItemsMenu();
        bottomNavigationView.getMenu().findItem(R.id.navigation_inicio).setChecked(true);

        getSupportActionBar().setTitle("Antappacay te informa");
        */
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
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
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_noticias) {
            ClickMenuNoticias();
        } else if (id == R.id.nav_publicaciones) {
            uncheckItemsMenu();
            bottomNavigationView.getMenu().findItem(R.id.navigation_publicaciones).setChecked(true);
        } else if (id == R.id.nav_imagenes) {
            uncheckItemsMenu();
            bottomNavigationView.getMenu().findItem(R.id.navigation_imagenes).setChecked(true);
        } else if (id == R.id.nav_videos) {
            uncheckItemsMenu();
            bottomNavigationView.getMenu().findItem(R.id.navigation_videos).setChecked(true);
        } else if (id == R.id.nav_tickets) {
            uncheckItemsMenu();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_inicio:
                    setTitle("Antappacay te informa");
                    ChangeFragment(NavigationFragment.Inicio);
                    uncheckItemsMenu();
                    return true;
                case R.id.navigation_imagenes:
                    uncheckItemsMenu();
                    navigationView.getMenu().findItem(R.id.nav_imagenes).setChecked(true);
                    return true;
                case R.id.navigation_videos:
                    uncheckItemsMenu();
                    navigationView.getMenu().findItem(R.id.nav_videos).setChecked(true);
                    return true;
                case R.id.navigation_noticias:
                    ClickMenuNoticias();
                    return true;
                case R.id.navigation_publicaciones:
                    uncheckItemsMenu();
                    navigationView.getMenu().findItem(R.id.nav_publicaciones).setChecked(true);
                    return true;
            }
            return false;
        }

    };

    public void ClickMenuNoticias() {
        uncheckItemsMenu();
        bottomNavigationView.getMenu().findItem(R.id.navigation_noticias).setChecked(true);
        navigationView.getMenu().findItem(R.id.nav_noticias).setChecked(true);
        ChangeFragment(NavigationFragment.Noticias);
    }

    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            //Timber.e(e, "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            //Timber.e(e, "Unable to change value of shift mode");
        }
    }

    public void uncheckItemsMenu() {
        // limpiamos todos los seleccionados
        Menu menu = navigationView.getMenu();
        uncheckItems(menu);
        menu = bottomNavigationView.getMenu();
        uncheckItems(menu);
    }

    private void uncheckItems(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.hasSubMenu()) {
                SubMenu subMenu = item.getSubMenu();
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    subMenuItem.setChecked(false);
                }
            } else {
                item.setChecked(false);
            }
        }
    }

    private void ChangeFragment(NavigationFragment value){
        Fragment fragment = null;
        switch (value) {
            case Inicio:    fragment = new FragmentInicio(); break;
            case Noticias:    fragment = new FragmentNoticias(); break;
        }
        if(fragment!=null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, fragment)
                    .commit();
    }
}
