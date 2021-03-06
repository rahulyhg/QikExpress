package com.equinox.qikexpress.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.equinox.qikexpress.Models.DataHolder;
import com.equinox.qikexpress.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mukht on 11/3/2016.
 */

public class GroceryExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private List<String> listDataHeader; // header titles
    private Map<String,String> categoryImageMapping;
    private HashMap<String,List<String>> listDataChild;
    private String placeId;

    public GroceryExpandableListAdapter(List<String> listDataHeader, HashMap<String, List<String>> listChildData, Map<String, String> categoryImageMapping, Activity activity, String placeId) {
        this.activity = activity;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
        this.categoryImageMapping = categoryImageMapping;
        this.placeId = placeId;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = listDataHeader.get(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.grocery_items_list_group, null);
        }
        NetworkImageView categoryImage = (NetworkImageView) convertView.findViewById(R.id.grocery_category_header_image);
        if (categoryImageMapping.get(headerTitle) != null)
            categoryImage.setImageUrl(categoryImageMapping.get(headerTitle), DataHolder.getInstance().getImageLoader());
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.grocery_item_group_name);
        lblListHeader.setText(headerTitle + " (" + getChildrenCount(groupPosition) + ") ");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.grocery_items_list_child, null);
        }
        RecyclerView itemChildRecyclerView = (RecyclerView) convertView.findViewById(R.id.grocery_category2_child_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        itemChildRecyclerView.setLayoutManager(layoutManager);
        itemChildRecyclerView.setHasFixedSize(true);
        itemChildRecyclerView.setAdapter(new GroceryCategory2RecyclerAdapter(activity, categoryImageMapping,
                listDataChild.get(listDataHeader.get(groupPosition)), listDataHeader.get(groupPosition), placeId));
        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
