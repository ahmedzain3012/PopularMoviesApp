package com.example.android.az.popularmoviesapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {
    public static final String LOG_TAG = MainActivity.class.getName();
    /**
     * Constant value for the Movies loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    LoaderManager loaderManager;
    private static final int MOVIES_LOADER_ID = 1;
    /**
     * URL for movies data from the movies dataset
     */
    private static final String REQUEST_URL =
            "https://api.themoviedb.org/3/movie/";
    /**
     * REQUEST_SECTION URL for movies data from the movies dataset
     */
    String REQUEST_SECTION;
    /**
     * to check if loader is run
     */
    private boolean isRunning = false;

    /**
     * Adapter for the list of movies
     */
    private MoviesAdapter mAdapter;
    /**
     * if no internet or no data
     */
    private TextView emptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Find a reference to the {@link GridView} in the layout
        GridView moviesGridView = findViewById(R.id.gv_movies_grid);
        // Find a reference to the {@link TextView} in the layout
        emptyView = findViewById(R.id.tv_empty_view);
        //set the EmptyView for the GridView
        moviesGridView.setEmptyView(emptyView);

        // Create a new adapter that takes an empty list of movies as input
        mAdapter = new MoviesAdapter(this, new ArrayList<Movie>());

        // Set the adapter on the {@link GridView}
        // so the list can be populated in the user interface
        moviesGridView.setAdapter(mAdapter);

        // Set an item click listener on the GridView, which sends an intent to a  DetailMovieActivity
        // to display a movie info with more information about the selected movie.
        moviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current movie that was clicked on
                Movie currentMovie = mAdapter.getItem(position);
                // Create a new intent to view the movie
                Intent intent = new Intent(MainActivity.this, DetailMovieActivity.class);
                //add the data with putExtra to send it to DetailMovieActivity
                intent.putExtra("movieParcelable", currentMovie);
                // Send the intent to launch a new activity
                startActivity(intent);
            }
        });


        // Get a reference to the LoaderManager, in order to interact with loaders.
        loaderManager = getLoaderManager();
        //initiate the loader by method
        executeLoader(getString(R.string.settings_def_section_default));
    }

    /**
     * executeLoader method to initiate loader with diff url request section
     *
     * @param requestSection
     */
    void executeLoader(String requestSection) {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            //assign value for REQUEST_SECTION befor initiate loader
            REQUEST_SECTION = requestSection;

            if (!isRunning) {
                /**
                 * if isRunning false mean first run then initLoader
                 * else mean we already has loader then restartLoader
                 */
                loaderManager.initLoader(MOVIES_LOADER_ID, null, this);
                isRunning = true;
            } else {
                loaderManager.restartLoader(MOVIES_LOADER_ID, null, this);
            }


        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.pb_loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            emptyView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        /**
         * create request url from REQUEST_URL + REQUEST_SECTION
         * then parse it as Uri
         * the using uri builder fro append format and api_key
         */
        String REQUEST_URL_SECTION = REQUEST_URL + REQUEST_SECTION;
        Uri baseUri = Uri.parse(REQUEST_URL_SECTION);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "json");
        //TODO has to add you api_key before run app
        uriBuilder.appendQueryParameter("api_key", BuildConfig.API_KEY);
        //return the MoviesLoader with url
        return new MoviesLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.pb_loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No movies found."
        emptyView.setText(R.string.no_data);
        // Clear the adapter of previous movies data
        mAdapter.clear();

        // If there is a valid list of {@link Movie}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (movies != null && !movies.isEmpty()) {
            mAdapter.addAll(movies);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        // Clear the adapter of previous movies data
        mAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //create menu with my own menu
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle menu option to execute loader with different url
        switch (item.getItemId()) {
            case R.id.action_most_popular:
                executeLoader(getString(R.string.most_popular_menu_item_val));
                return true;
            case R.id.action_highest_rated:
                executeLoader(getString(R.string.highest_rated_menu_item_val));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}