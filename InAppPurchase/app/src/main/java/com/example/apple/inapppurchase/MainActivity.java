package com.example.apple.inapppurchase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

public class MainActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {
    //    IInAppBillingService mService;
    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bp = new BillingProcessor(this, null, this);
//        Intent serviceIntent =
//                new Intent("com.android.vending.billing.InAppBillingService.BIND");
//        serviceIntent.setPackage("com.android.vending");
//        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mService != null) {
//            unbindService(mServiceConn);
//        }
//    }
//
//    final ServiceConnection mServiceConn = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            mService = IInAppBillingService.Stub.asInterface(service);
//            Log.d("TAG", "Connected");
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            Log.d("TAG", "DisConnected");
//            mService = null;
//        }
//    };
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
//        Called when requested PRODUCT ID was successfully purchased
    }

    @Override
    public void onPurchaseHistoryRestored() {
//        Called when purchase history was restored and the list of all owned PRODUCT ID's
//                 was loaded from Google Play
    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
//        Called when some error occurred. See Constants class for more details
//        Note - this includes handling the case where the user canceled the buy dialog:
//        errorCode = Constants.BILLING_RESPONSE_RESULT_USER_CANCELED

    }

    @Override
    public void onBillingInitialized() {
//        Called when BillingProcessor was initialized and it's ready to purchase
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
