package com.marcosdiez.extratocartao.glue;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.marcosdiez.extratocartao.R;
import com.marcosdiez.extratocartao.datamodel.StoreJoin;

import java.util.List;

/**
 * @author Paresh N. Mayani
 */
public class StoreJoinListAdapter extends BaseAdapter {
    public List<StoreJoin> list;
    Activity activity;

    public StoreJoinListAdapter(Activity activity, List<StoreJoin> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public StoreJoin getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (getItem(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.group_purchase, null);
            holder = new ViewHolder();
            holder.txtFirst = (TextView) convertView.findViewById(R.id.NumPurchases);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.Store);
            holder.txtThird = (TextView) convertView.findViewById(R.id.Amount);
            holder.txtFourth = (TextView) convertView.findViewById(R.id.Total);
            holder.txtFifth = (TextView) convertView.findViewById(R.id.When);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        StoreJoin theStoreJoin = list.get(position);
        holder.txtFirst.setText(String.format("%d", theStoreJoin.getNumPurchases()));
        holder.txtSecond.setText(theStoreJoin.getName().toString());
        holder.txtThird.setText(String.format("%1$,.2f", theStoreJoin.getTotal()));
        holder.txtFourth.setText(String.format("%1$,.2f", theStoreJoin.getTotalAmount()));
        holder.txtFifth.setText(theStoreJoin.getLastPurchaseTimestampString());

        return convertView;
    }

    private class ViewHolder {
        TextView txtFirst = null;
        TextView txtSecond = null;
        TextView txtThird = null;
        TextView txtFourth = null;
        TextView txtFifth = null;
    }

}