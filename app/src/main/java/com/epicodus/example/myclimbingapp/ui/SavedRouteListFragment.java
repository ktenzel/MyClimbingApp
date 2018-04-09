package com.epicodus.example.myclimbingapp.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.example.myclimbingapp.Constants;
import com.epicodus.example.myclimbingapp.R;
import com.epicodus.example.myclimbingapp.adapters.FirebaseFindRouteListAdapter;
import com.epicodus.example.myclimbingapp.adapters.FirebaseFindRouteViewHolder;
import com.epicodus.example.myclimbingapp.models.Route;
import com.epicodus.example.myclimbingapp.util.OnStartDragListener;
import com.epicodus.example.myclimbingapp.util.SimpleItemTouchHelperCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedRouteListFragment extends Fragment implements OnStartDragListener {
    private FirebaseFindRouteListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public SavedRouteListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_route_list, container, false);
        ButterKnife.bind(this, view);
        setUpFirebaseAdapter();
        return view;
    }

    private void setUpFirebaseAdapter() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        Query query = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_ROUTES)
                .child(uid)
                .orderByChild(Constants.FIREBASE_QUERY_INDEX);

        mFirebaseAdapter = new FirebaseFindRouteListAdapter(Route.class,
                R.layout.route_list_item_drag, FirebaseFindRouteViewHolder.class,
                query, this, getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mFirebaseAdapter);

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                mFirebaseAdapter.notifyDataSetChanged();
            }
        });
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }
    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
