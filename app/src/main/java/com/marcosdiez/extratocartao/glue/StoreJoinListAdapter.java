package com.marcosdiez.extratocartao.glue;


import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.marcosdiez.extratocartao.R;
import com.marcosdiez.extratocartao.datamodel.StoreJoin;

import java.util.List;

/**
 * @author Paresh N. Mayani
 */
public class StoreJoinListAdapter extends GenericAdapter<StoreJoin> {

    public StoreJoinListAdapter(Activity activity, List<StoreJoin> list) {
        super(activity, list, R.layout.group_purchase);
    }

    protected BaseViewHolder registerHolder(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.txtFirst = (TextView) convertView.findViewById(R.id.NumPurchases);
        holder.txtSecond = (TextView) convertView.findViewById(R.id.Store);
        holder.txtThird = (TextView) convertView.findViewById(R.id.Amount);
        holder.txtFourth = (TextView) convertView.findViewById(R.id.Total);
        holder.txtFifth = (TextView) convertView.findViewById(R.id.When);
        return holder;
    }

    protected void populateHolder(int position, BaseViewHolder holder2) {
        ViewHolder holder = (ViewHolder) holder2;
        StoreJoin theStoreJoin = list.get(position);
        holder.txtFirst.setText(String.format("%d", theStoreJoin.getNumPurchases()));
        holder.txtSecond.setText(theStoreJoin.getName());
        holder.txtThird.setText(String.format("%1$,.2f", theStoreJoin.getTotal()));
        holder.txtFourth.setText(String.format("%1$,.2f", theStoreJoin.getTotalAmount()));
        holder.txtFifth.setText(theStoreJoin.getLastPurchaseTimestampString());
    }

    private class ViewHolder extends BaseViewHolder {
        TextView txtFirst = null;
        TextView txtSecond = null;
        TextView txtThird = null;
        TextView txtFourth = null;
        TextView txtFifth = null;
    }

}