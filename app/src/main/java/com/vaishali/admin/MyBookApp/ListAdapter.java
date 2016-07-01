package com.vaishali.admin.MyBookApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Admin on 6/30/2016.
 */
public class ListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflator;
    String[] content={"Scan using barcode scanner","Add manually"};
    public ListAdapter(Context context)
    {
        this.mContext=context;
        this.inflator= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {

        return content.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v=convertView;
        if(convertView==null)
        {

            v=inflator.inflate(R.layout.dialog_box,null);

        }
        return null;
    }
}
