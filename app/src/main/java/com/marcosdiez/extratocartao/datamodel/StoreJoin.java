package com.marcosdiez.extratocartao.datamodel;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.Date;
import java.util.List;

/**
 * Created by Marcos on 2016-02-08.
 */
public class StoreJoin extends SugarRecord<StoreJoin> {

    String name;
    double total;
    int numPurchases;
    long lastPurchaseTimestamp;

    @Ignore
    double totalAmount = 0;

    public StoreJoin() {
    }

    public static List<StoreJoin> getList() {
        String query = "select\n" +
                "store.id as ID,\n" +
                "store.name as NAME,\n" +
                "sum(amount) as TOTAL,\n" +
                "count(store.id) as NUM_PURCHASES,\n" +
                "max(purchase.timestamp) as LAST_PURCHASE_TIMESTAMP\n" +
                "from purchase\n" +
                "join store on store.id = purchase.store\n" +
                "group by purchase.store\n" +
                "order by TOTAL desc";

        List<StoreJoin> pList = StoreJoin.findWithQuery(StoreJoin.class, query);

        double total = 0;
        for (StoreJoin p : pList) {
            total += p.getTotal();
            p.setTotalAmount(total);
        }
        return pList;
    }

    public String getName() {
        return name;
    }

    public double getTotal() {
        return total;
    }

    public int getNumPurchases() {
        return numPurchases;
    }

    public long getLastPurchaseTimestamp() {
        return lastPurchaseTimestamp;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getLastPurchaseTimestampString() {
        return Purchase.dateTimeFormat.format(getDate());
    }

    public Date getDate() {
        return new Date(this.lastPurchaseTimestamp);
    }
}
