package net.sharksystem.persons.android;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import net.sharksystem.R;
import net.sharksystem.asap.android.Util;
import net.sharksystem.sharknet.android.SharkNetActivity;
import net.sharksystem.sharknet.android.SharkNetApp;

public abstract class PersonListActivity extends SharkNetActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private PersonListContentAdapter mAdapter;

    public PersonListActivity() {
        super(SharkNetApp.getSharkNetApp());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.getLogStart(), "onCreate");

        try {
            setContentView(R.layout.person_list_drawer_layout);

            this.getSharkNetApp().setupDrawerLayout(this);

            PersonsApp.getPersonsApp();

            ////////////////////////////////////////////////////////////////////////
            //                         prepare action bar                         //
            ////////////////////////////////////////////////////////////////////////
            // setup toolbar
            Toolbar myToolbar = (Toolbar) findViewById(R.id.person_list_with_toolbar);
            setSupportActionBar(myToolbar);

            ////////////////////////////////////////////////////////////////////////
            //                         prepare recycler view                      //
            ////////////////////////////////////////////////////////////////////////
            mRecyclerView = (RecyclerView) findViewById(R.id.person_list_recycler_view);

            mAdapter = new PersonListContentAdapter(this);
            RecyclerView.LayoutManager mLayoutManager =
                    new LinearLayoutManager(getApplicationContext());

            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(mAdapter);
            Log.d(this.getLogStart(), "attached content adapter");
        }
        catch(Exception e) {
            Log.d(this.getLogStart(), "problems while attaching content adapter: "
                    + e.getLocalizedMessage());
            // debug break
            int i = 42;
        }
    }

    protected void onResume() {
        super.onResume();
        Log.d(Util.getLogStart(this), "onResume: assume data set changed.");
        this.mAdapter.notifyDataSetChanged();
    }
}