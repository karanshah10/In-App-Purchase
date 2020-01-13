package co.peaas.inapppurchase;

import co.peaas.inapppurchase.util.IabResult;

/**
 * Created by Apple on 8/31/2017.
 */

public interface StartSetupListener {
    void onIabSetUpFinishedListener(IabResult result);
}
