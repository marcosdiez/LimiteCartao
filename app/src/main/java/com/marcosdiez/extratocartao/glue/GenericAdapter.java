package com.marcosdiez.extratocartao.glue;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Marcos on 2016-05-15.
 */
public abstract class GenericAdapter<T> extends BaseAdapter {
    public List<T> list;
    Activity activity;
    int resource;

    public GenericAdapter(Activity activity, List<T> list, int resource) {
        super();
        this.activity = activity;
        this.list = list;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        T theObj = getItem(position);
        if(theObj instanceof SugarRecord){
            return ((SugarRecord) theObj).getId();
        }
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(resource, null);
            holder = registerHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (BaseViewHolder) convertView.getTag();
        }

        populateHolder(position, holder);
        return convertView;
    }

    abstract BaseViewHolder registerHolder(View convertView);

    abstract void populateHolder(int position, BaseViewHolder holder);

    protected class BaseViewHolder {
    }
}
