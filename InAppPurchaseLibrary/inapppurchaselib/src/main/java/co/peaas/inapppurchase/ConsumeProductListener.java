package co.peaas.inapppurchase;

import co.peaas.inapppurchase.util.IabResult;
import co.peaas.inapppurchase.util.Purchase;

/**
 * Created by Apple on 9/1/2017.
 */

public interface ConsumeProductListener {
    void onConsumeFinishedListener(Purchase purchase, IabResult result);
}
