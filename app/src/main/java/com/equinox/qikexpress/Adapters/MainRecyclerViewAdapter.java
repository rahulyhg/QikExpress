package com.equinox.qikexpress.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.equinox.qikexpress.Activities.MainActivity;
import com.equinox.qikexpress.Activities.ShopListActivity;
import com.equinox.qikexpress.Enums.QikList;
import com.equinox.qikexpress.Fragments.SelectPlaceFragment;
import com.equinox.qikexpress.R;
import com.equinox.qikexpress.ViewHolders.MainListViewHolder;

import java.util.Arrays;
import java.util.List;

import static com.equinox.qikexpress.Models.DataHolder.userPlaceHashMap;

/**
 * Created by mukht on 10/29/2016.
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainListViewHolder> {

    private Activity activity;
    private List<QikList> qikLists;

    public MainRecyclerViewAdapter(Activity activity) {
        this.activity = activity;
        qikLists = Arrays.asList(QikList.values());
    }

    @Override
    public MainListViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_menu_list, parent, false);
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int itemPosition = recyclerView.indexOfChild(v);
                if (userPlaceHashMap.isEmpty())
                    Snackbar.make(v, "Please wait for your places to be loaded!", Snackbar.LENGTH_SHORT).show();
                else {
                    QikList qikList = QikList.values()[parent.indexOfChild(v)];
                    SelectPlaceFragment selectPlaceFragment = SelectPlaceFragment.newInstance(qikList.toString());
                    selectPlaceFragment.show(((MainActivity) activity).getSupportFragmentManager(), "SelectPlaceFragment");
                }
            }
        });
        return new MainListViewHolder(holder, activity);
    }

    @Override
    public void onBindViewHolder(MainListViewHolder holder, int position) {
        QikList qikList = qikLists.get(position);
        holder.getCardText().setText(QikList.values()[position].getFaceName());
        holder.getCardIcon().setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(), qikList.getIcon(), null));
        holder.getListCard().setBackground(ResourcesCompat.getDrawable(activity.getResources(), qikList.getBackground(), null));
    }

    @Override
    public int getItemCount() {
        return qikLists.size();
    }
}
