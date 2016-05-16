package com.marcosdiez.extratocartao.glue;


import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.marcosdiez.extratocartao.R;
import com.marcosdiez.extratocartao.datamodel.MonthlyPurchase;

import java.util.List;

/**
 * @author Paresh N. Mayani
 */
public class MonthlyPurchaseListAdapter extends GenericAdapter<MonthlyPurchase> {

    public MonthlyPurchaseListAdapter(Activity activity, List<MonthlyPurchase> list) {
        super(activity, list, R.layout.monthly_purchase);
    }


    protected BaseViewHolder registerHolder(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.txtFirst = (TextView) convertView.findViewById(R.id.Card);
        holder.txtSecond = (TextView) convertView.findViewById(R.id.NumPurchases);
        holder.txtThird = (TextView) convertView.findViewById(R.id.When);
        holder.txtFourth = (TextView) convertView.findViewById(R.id.Amount);
        holder.txtFifth = (TextView) convertView.findViewById(R.id.Total);
        return holder;
    }


    protected void populateHolder(int position, BaseViewHolder holder2) {
        ViewHolder holder = (ViewHolder) holder2;
        MonthlyPurchase theMonthlyPurchase = list.get(position);
        holder.txtFirst.setText(theMonthlyPurchase.getCard());
        holder.txtSecond.setText(String.format("%d", theMonthlyPurchase.getNumPurchases()));
        holder.txtThird.setText(theMonthlyPurchase.getTimestampString());
        holder.txtFourth.setText(String.format("%1$,.2f", theMonthlyPurchase.getAmount()));
        holder.txtFifth.setText(String.format("%1$,.2f", theMonthlyPurchase.getTotalAmount()));
    }

    private class ViewHolder extends BaseViewHolder {
        TextView txtFirst = null;
        TextView txtSecond = null;
        TextView txtThird = null;
        TextView txtFourth = null;
        TextView txtFifth = null;
    }

}