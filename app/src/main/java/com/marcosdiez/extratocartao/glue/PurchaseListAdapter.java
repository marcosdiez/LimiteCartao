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
 *
 * @author Paresh N. Mayani
 */
public class PurchaseListAdapter extends BaseAdapter
{
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
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((Purchase) getItem(position)).getId();
    }

    private class ViewHolder {
        TextView txtFirst;
        TextView txtSecond;
        TextView txtThird;
        TextView txtFourth;
        TextView txtFifth;
        TextView txtSixth;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  activity.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_purchase, null);
            holder = new ViewHolder();
            holder.txtSixth = (TextView) convertView.findViewById(R.id.purchase_id);
            holder.txtFirst = (TextView) convertView.findViewById(R.id.Card);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.Store);
            holder.txtThird = (TextView) convertView.findViewById(R.id.Amount);
            holder.txtFourth = (TextView) convertView.findViewById(R.id.When);
            holder.txtFifth = (TextView) convertView.findViewById(R.id.Map);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        Purchase thePurchase = list.get(position);
        holder.txtSixth.setText(thePurchase.getId().toString());
        holder.txtFirst.setText(thePurchase.getCard().toString());
        holder.txtSecond.setText(thePurchase.getStore().toString());
        holder.txtThird.setText(String.format("%1$,.2f", thePurchase.getAmount()));
        holder.txtFourth.setText(thePurchase.getTimeStampString());
        holder.txtFifth.setText(thePurchase.hasMap() ? "Mapa" : "");

        return convertView;
    }

}