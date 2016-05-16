package com.marcosdiez.extratocartao.glue;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.marcosdiez.extratocartao.R;
import com.marcosdiez.extratocartao.datamodel.Purchase;

import java.util.List;

/**
 * @author Paresh N. Mayani
 */
public class PurchaseListAdapter extends BaseAdapter {
    public List<Purchase> list;
    Activity activity;

    public PurchaseListAdapter(Activity activity, List<Purchase> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Purchase getItem(int position) {
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
            convertView = inflater.inflate(R.layout.item_purchase, null);
            holder = new ViewHolder();
            holder.txtFirst = (TextView) convertView.findViewById(R.id.Card);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.Store);
            holder.txtThird = (TextView) convertView.findViewById(R.id.Amount);
            holder.txtFourth = (TextView) convertView.findViewById(R.id.Total);
            holder.txtFifth = (TextView) convertView.findViewById(R.id.When);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Purchase thePurchase = list.get(position);
        holder.txtFirst.setText(thePurchase.getCard().toShortString());
        holder.txtSecond.setText(thePurchase.getStore().toString());
        holder.txtThird.setText(String.format("%1$,.2f", thePurchase.getAmount()));
        holder.txtFourth.setText(String.format("%1$,.2f", thePurchase.getTotalAmount()));
        holder.txtFifth.setText(thePurchase.getTimeStampString());


        return convertView;
    }

    private class ViewHolder {
        TextView txtFirst = null;
        TextView txtSecond = null;
        TextView txtThird = null;
        TextView txtFifth = null;
        TextView txtFourth = null;
    }

}