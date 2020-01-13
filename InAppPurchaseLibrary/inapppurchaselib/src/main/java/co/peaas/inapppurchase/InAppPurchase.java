package co.peaas.inapppurchase;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import co.peaas.inapppurchase.util.IabHelper;
import co.peaas.inapppurchase.util.IabResult;
import co.peaas.inapppurchase.util.Inventory;
import co.peaas.inapppurchase.util.Purchase;
import co.peaas.inapppurchase.utils.LibraryConstant;

import java.util.List;

/**
 * Created by Apple on 8/29/2017.
 */

public class InAppPurchase {
    static String SKU_INAPP_PREMIUM = "";
    static String SKU_INAPP = "";
    static String SKU_SUB = "";
    public IabHelper mHelper;

    public Activity mContext;
    public String mKey;

    private PurchaseManagedConsumeListener mPurchaseManagedConsumeListener;
    private PurchaseManagedPremiumListener mPurchaseManagedPremiumListener;
    private ConsumeProductListener mConsumeProductListener;
    private PurchaseSubscriptionListener mPurchaseSubscriptionListener;
    private StartSetupListener mStartSetupListener;
    private QueryInventoryListener mQueryInventoryListener;

    public InAppPurchase(Activity context, String key) {
        this.mContext = context;
        this.mKey = key;
    }

    // Start The Setup Process.

    public void StartSetup(StartSetupListener listener) {
        mStartSetupListener = listener;
        mHelper = new IabHelper(mContext, mKey);
        log("Starting setup");
        mHelper.startSetup(onIabSetupFinishedListener);
    }

    // Callback for setup Process .
    // This Listener Method is called when setup Process Complete.

    IabHelper.OnIabSetupFinishedListener onIabSetupFinishedListener =
            new IabHelper.OnIabSetupFinishedListener() {
                @Override
                public void onIabSetupFinished(IabResult result) {
                    mStartSetupListener.onIabSetUpFinishedListener(result);
                }
            };

    //      Asynchronous wrapper for inventory queery.

    public void onQueryInventoryAsync(QueryInventoryListener listener, List<String> moreItemSkus, List<String> moreSubsSkus) {
        mQueryInventoryListener = listener;
        try {
            mHelper.queryInventoryAsync(true, moreItemSkus, moreSubsSkus, mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    public void onQueryInventoryAsync(QueryInventoryListener listener) {
        mQueryInventoryListener = listener;
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    // This Listener notifies when an inventory query operation completes.

    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inv) {
            mQueryInventoryListener.queryInventoryFinishedListener(result, inv);
        }
    };

    // Start purchase for comsumable Product.

    public void onPurchaseManagedConsumeProduct(PurchaseManagedConsumeListener listener, String sku) {
        SKU_INAPP = sku;
        mPurchaseManagedConsumeListener = listener;
        try {
            mHelper.launchPurchaseFlow(mContext, SKU_INAPP, LibraryConstant.RC_REQUEST, mPurchaseFinishedListener, "");
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    public void onPurchaseManagedPremiumProduct(PurchaseManagedPremiumListener listener, String sku) {
        SKU_INAPP_PREMIUM = sku;
        mPurchaseManagedPremiumListener = listener;
        try {
            mHelper.launchPurchaseFlow(mContext, SKU_INAPP, LibraryConstant.RC_REQUEST, mPurchaseFinishedListener, "");
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }


    //Start Purchase for Subscription Product.

    public void onPurchasedSubscription(PurchaseSubscriptionListener listener, String sku) {
        SKU_SUB = sku;
        mPurchaseSubscriptionListener = listener;
        try {
            mHelper.launchSubscriptionPurchaseFlow(mContext, SKU_SUB, LibraryConstant.RC_REQUEST, mPurchaseFinishedListener, "");
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    // callBack that notifies when a Purchase is finished.

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener =
            new IabHelper.OnIabPurchaseFinishedListener() {
                @Override
                public void onIabPurchaseFinished(IabResult result, Purchase info) {
                    if (mHelper == null) {
                        return;
                    }
                    if (result.isFailure()) {
                        mHelper = null;
                        log("SKUFAIL");
                        return;
                    }
                    if (info.getSku().equals(SKU_INAPP_PREMIUM)) {
                        mPurchaseManagedPremiumListener.managedPremiumProductFinishedListener(result, info);
                    } else if (info.getSku().equals(SKU_INAPP)) {
                        try {
                            mHelper.consumeAsync(info, new IabHelper.OnConsumeFinishedListener() {
                                @Override
                                public void onConsumeFinished(Purchase purchase, IabResult result) {
                                    mPurchaseManagedConsumeListener.managedProductConsumeFinishedListener(result, purchase);
                                }
                            });
                        } catch (IabHelper.IabAsyncInProgressException e) {
                            e.printStackTrace();
                        }
                    } else if (info.getSku().equals(SKU_SUB)) {
                        log("Infinite subscription purchased.");
                        mPurchaseSubscriptionListener.subscriptionFinishedListener(result, info);
                    }
                }
            };

    // Start Consume Product

    public void onConsumeProduct(ConsumeProductListener listener, Purchase info) {
        mConsumeProductListener = listener;
        try {
            mHelper.consumeAsync(info, onConsumeFinishedListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    //Callback that notifies when a consumption operation finished.

    IabHelper.OnConsumeFinishedListener onConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                @Override
                public void onConsumeFinished(Purchase purchase, IabResult result) {
                    if (mHelper == null) return;
                    if (result.isSuccess()) {
                        log("Consume successfull");
                        mConsumeProductListener.onConsumeFinishedListener(purchase, result);
                    } else {
                        log("COnsume error");
                    }
                }
            };

    public boolean onActivityRresult(int requestCode, int resultCode, Intent data) {

        Log.d("TAG", "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return false;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            return true;
        }
        return false;
    }

    private void log(String text) {
        Log.d("TAG", text);
    }

}
