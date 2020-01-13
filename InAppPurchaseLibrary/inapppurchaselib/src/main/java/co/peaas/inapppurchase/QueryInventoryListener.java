package co.peaas.inapppurchase;

import co.peaas.inapppurchase.util.IabResult;
import co.peaas.inapppurchase.util.Inventory;

/**
 * Created by Apple on 9/1/2017.
 */

public interface QueryInventoryListener {
    void queryInventoryFinishedListener(IabResult result, Inventory inv);
}
