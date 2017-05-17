package com.example.lorvent.popular_movie_app1.fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.example.lorvent.popular_movie_app1.utils.AppConfig;
import com.example.lorvent.popular_movie_app1.DetailsActivity;
import com.example.lorvent.popular_movie_app1.network.GetAllMovies;
import com.example.lorvent.popular_movie_app1.utils.Movie;
import com.example.lorvent.popular_movie_app1.utils.Network;
import com.example.lorvent.popular_movie_app1.NetworkErrorActivity;
import com.example.lorvent.popular_movie_app1.R;
import com.example.lorvent.popular_movie_app1.utils.RecyclerTouchListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment  {
    ArrayList<Movie> movieArrayList;
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    FrameLayout frameLayout;
    private GridView mGridView;
    private static final int CURSOR_LOADER_ID = 0;
    Cursor c;
    View v;
    public MainFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
             v=inflater.inflate(R.layout.fragment_main, container, false);
            frameLayout=(FrameLayout)v.findViewById(R.id.frame);
            recyclerView=(RecyclerView)v.findViewById(R.id.recyclerview);
            movieArrayList=new ArrayList<>();
            int orientation= getResources().getConfiguration().orientation;
            if(orientation== Configuration.ORIENTATION_PORTRAIT){
                layoutManager=new GridLayoutManager(getActivity(),2);
            }
            else{
                layoutManager=new GridLayoutManager(getActivity(),3);
            }
            new GetAllMovies(recyclerView,layoutManager,getActivity(),movieArrayList).execute(AppConfig.API_KEY);

            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),recyclerView, new RecyclerTouchListener.ClickListener() {
                @Override

                public void onClick(View view, int position) {

                    Movie movie= movieArrayList.get(position);
                    int movieId=movie.getMovie_id();


                    if (!Network.isConnected(getActivity())){
                        Intent i = new Intent(getActivity(),NetworkErrorActivity.class);
                        i.putExtra("movie_id",movieId);
                        startActivity(i);}
                    if (Network.isConnected(getActivity())){

                        Intent i=new Intent(getActivity(),DetailsActivity.class);
                        i.putExtra("movie_id",movieId);
                        i.putExtra("call_from","main");
                        //Log.i("movie_id", String.valueOf(movieId));
                        getActivity().startActivity(i);}
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
               v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        getActivity().finish();

                        return true;
                    }
                }
                return false;
            }
        });
        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        setHasOptionsMenu(true);
        if (actionBar != null) {
            actionBar.setTitle("Movies");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return v;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            getActivity().finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
