package com.example.lorvent.popular_movie_app1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.lorvent.popular_movie_app1.fragments.MainFragment;
import com.example.lorvent.popular_movie_app1.fragments.PopularFragment;
import com.example.lorvent.popular_movie_app1.fragments.TopRatedFragment;
import com.example.lorvent.popular_movie_app1.utils.Network;

public class MainActivity extends AppCompatActivity {
    CheckBox popular,top_rated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!Network.isConnected(this))
        {     Intent i=new Intent(this,NetworkErrorActivity.class);
              startActivity(i);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        if (actionBar != null) {
            // actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Movies");

        }

        Fragment fragment1 = new MainFragment();
        FragmentTransaction trans1 = getSupportFragmentManager().beginTransaction();
        trans1.replace(R.id.frame, fragment1);
        trans1.addToBackStack(null);
        trans1.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()== R.id.action_settings) {

            MaterialDialog dialog=new MaterialDialog.Builder(MainActivity.this)
                    .title("SORT BY")
                    .customView(R.layout.movie_sort, true)
                    .positiveText("ok")
                    .positiveColorRes(R.color.colorPrimary)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (popular.isChecked())
                            {
                                Fragment fragment2 = new TopRatedFragment();
                                FragmentTransaction trans1 = getSupportFragmentManager().beginTransaction();
                                trans1.replace(R.id.frame, fragment2);
                                trans1.addToBackStack(null);
                                trans1.commit();
                            }
                            if (top_rated.isChecked())
                            {
                                Fragment fragment2 = new PopularFragment();
                                FragmentTransaction trans1 = getSupportFragmentManager().beginTransaction();
                                trans1.replace(R.id.frame, fragment2);
                                trans1.addToBackStack(null);
                                trans1.commit();
                            }
                        }
                    })
                    .negativeColorRes(R.color.colorPrimary)
                    .negativeText("")
                    .show();

            View view = dialog.getCustomView();
            if (view != null) {
                popular=(CheckBox) dialog.getCustomView().findViewById(R.id.popular);
                top_rated=(CheckBox) dialog.getCustomView().findViewById(R.id.top_rated);

            }
            popular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){top_rated.setChecked(false);}
                }
            });
            top_rated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){popular.setChecked(false);}
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }
}
