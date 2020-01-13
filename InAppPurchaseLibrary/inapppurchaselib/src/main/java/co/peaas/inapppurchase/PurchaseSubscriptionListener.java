package co.peaas.inapppurchase;

import co.peaas.inapppurchase.util.IabResult;
import co.peaas.inapppurchase.util.Purchase;

/**
 * Created by Apple on 8/30/2017.
 */

public interface PurchaseSubscriptionListener {
    void subscriptionFinishedListener(IabResult result, Purchase info);
}
