package com.marcosdiez.extratocartao.glue;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.marcosdiez.extratocartao.R;
import com.marcosdiez.extratocartao.datamodel.MonthlyPurchase;

import java.util.List;

/**
 * @author Paresh N. Mayani
 */
public class MonthlyPurchaseListAdapter extends BaseAdapter {
    public List<MonthlyPurchase> list;
    Activity activity;

    public MonthlyPurchaseListAdapter(Activity activity, List<MonthlyPurchase> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MonthlyPurchase getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // do we need this ?
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.monthly_purchase, null);
            holder = new ViewHolder();
            holder.txtFirst = (TextView) convertView.findViewById(R.id.Card);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.NumPurchases);
            holder.txtThird = (TextView) convertView.findViewById(R.id.When);
            holder.txtFourth = (TextView) convertView.findViewById(R.id.Amount);
            holder.txtFifth = (TextView) convertView.findViewById(R.id.Total);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MonthlyPurchase theMonthlyPurchase = list.get(position);
        holder.txtFirst.setText(theMonthlyPurchase.getCard());
        holder.txtSecond.setText(String.format("%d", theMonthlyPurchase.getNumPurchases()));
        holder.txtThird.setText(theMonthlyPurchase.getTimestampString());
        holder.txtFourth.setText(String.format("%1$,.2f", theMonthlyPurchase.getAmount()));
        holder.txtFifth.setText(String.format("%1$,.2f", theMonthlyPurchase.getTotalAmount()));

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