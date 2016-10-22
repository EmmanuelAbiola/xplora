package dise.xplora_starterproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toolbar;


public class MainActivity extends Activity {
  private Toolbar toolbar;
  private Menu menu;
  private boolean isListView;
  //Declaring 2 fields; 1 to hold a reference to RecycleView
  //and another to hold a reference to the LayoutManager
  private RecyclerView mRecyclerView;
  private StaggeredGridLayoutManager mStaggeredLayoutManager;
  private TravelListAdapter mAdapter;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setUpActionBar();


    mRecyclerView = (RecyclerView) findViewById(R.id.list);
    mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
    mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
// created a new instance of the adapter and passed into mRecyclerView
    mAdapter = new TravelListAdapter(this);
    mRecyclerView.setAdapter(mAdapter);
    mAdapter.setOnItemClickListener(onItemClickListener);

    isListView = true;

  }

  private TravelListAdapter.OnItemClickListener onItemClickListener = new TravelListAdapter.OnItemClickListener() {
    @Override
    public void onItemClick(View v, int position) {
      // 1.You’ve renamed the intent to provide more context
      Intent transitionIntent = new Intent(MainActivity.this, DetailActivity.class);
      transitionIntent.putExtra(DetailActivity.EXTRA_PARAM_ID, position);
      ImageView placeImage = (ImageView) v.findViewById(R.id.placeImage);
      LinearLayout placeNameHolder = (LinearLayout) v.findViewById(R.id.placeNameHolder);
      // 2.You get references to both the navigation bar and status bar
      View navigationBar = findViewById(android.R.id.navigationBarBackground);
      View statusBar = findViewById(android.R.id.statusBarBackground);

      Pair<View, String> imagePair = Pair.create((View) placeImage, "tImage");
      Pair<View, String> holderPair = Pair.create((View) placeNameHolder, "tNameHolder");
      // 3.Created three new instances of Pair – one for the navigation bar, one for the status bar, and one for the toolbar
      Pair<View, String> navPair = Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
      Pair<View, String> statusPair = Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME);
      Pair<View, String> toolbarPair = Pair.create((View)toolbar, "tActionBar");
      // 4.And finally you’ve updated the options that passed to the new activity to include the references to the new views.
      try {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, imagePair, holderPair, navPair, statusPair, toolbarPair);
        ActivityCompat.startActivity(MainActivity.this, transitionIntent, options.toBundle());
      } catch (Exception e) {
        e.printStackTrace();
      }


    }
  };



  private void setUpActionBar() {
    if (toolbar != null) {
      setActionBar(toolbar);
      getActionBar().setDisplayHomeAsUpEnabled(false);
      getActionBar().setDisplayShowTitleEnabled(true);
      getActionBar().setElevation(7);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);
    this.menu = menu;
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_toggle) {
      toggle();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void toggle() {
    //StaggeredLayoutManager lets you add versatility to your layouts.
    // To change your existing list to a more compact two-column grid,
    // you simply have to change the spanCount of the mLayoutManager in MainActivity.
    mStaggeredLayoutManager.setSpanCount(2);
    MenuItem item = menu.findItem(R.id.action_toggle);
    if (isListView) {
      item.setIcon(R.drawable.ic_action_list);
      item.setTitle("Show as list");
      isListView = false;
    } else {
      mStaggeredLayoutManager.setSpanCount(1);
      item.setIcon(R.drawable.ic_action_grid);
      item.setTitle("Show as grid");
      isListView = true;
    }
  }
}
