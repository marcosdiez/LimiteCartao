package com.marcosdiez.extratocartao.glue;


import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.marcosdiez.extratocartao.R;
import com.marcosdiez.extratocartao.datamodel.Purchase;

import java.util.List;

/**
 * @author Paresh N. Mayani
 */
public class PurchaseListAdapter extends GenericAdapter<Purchase> {

    public PurchaseListAdapter(Activity activity, List<Purchase> list) {
        super(activity, list, R.layout.item_purchase);
    }

    protected BaseViewHolder registerHolder(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.txtFirst = (TextView) convertView.findViewById(R.id.Card);
        holder.txtSecond = (TextView) convertView.findViewById(R.id.Store);
        holder.txtThird = (TextView) convertView.findViewById(R.id.Amount);
        holder.txtFourth = (TextView) convertView.findViewById(R.id.Total);
        holder.txtFifth = (TextView) convertView.findViewById(R.id.When);
        return holder;
    }

    protected void populateHolder(int position, BaseViewHolder holder2) {
        ViewHolder holder = (ViewHolder) holder2;
        Purchase thePurchase = list.get(position);
        holder.txtFirst.setText(thePurchase.getCard().toShortString());
        holder.txtSecond.setText(thePurchase.getStore().toString());
        holder.txtThird.setText(String.format("%1$,.2f", thePurchase.getAmount()));
        holder.txtFourth.setText(String.format("%1$,.2f", thePurchase.getTotalAmount()));
        holder.txtFifth.setText(thePurchase.getTimeStampString());

    }

    private class ViewHolder extends BaseViewHolder {
        TextView txtFirst = null;
        TextView txtSecond = null;
        TextView txtThird = null;
        TextView txtFourth = null;
        TextView txtFifth = null;
    }

}