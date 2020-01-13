package co.peaas.inapppurchaselibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.apple.inapppurchaselibrary.R;

import co.peaas.inapppurchase.ConsumeProductListener;
import co.peaas.inapppurchase.InAppPurchase;
import co.peaas.inapppurchase.PurchaseManagedConsumeListener;
import co.peaas.inapppurchase.PurchaseManagedPremiumListener;
import co.peaas.inapppurchase.PurchaseSubscriptionListener;
import co.peaas.inapppurchase.StartSetupListener;
import co.peaas.inapppurchase.util.IabResult;
import co.peaas.inapppurchase.util.Purchase;
import co.peaas.inapppurchase.utils.LibraryConstant;

public class MainActivity extends AppCompatActivity implements StartSetupListener, View.OnClickListener, PurchaseSubscriptionListener, PurchaseManagedConsumeListener, ConsumeProductListener, PurchaseManagedPremiumListener {

    private InAppPurchase inAppPurchase;
    Button purchase, purchasePremium, purchaseSubscrib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inAppPurchase = new InAppPurchase(this, "PublicKey");

        inAppPurchase.StartSetup(this);

        purchase = (Button) findViewById(R.id.onPurchase);
        purchasePremium = (Button) findViewById(R.id.onPurchasePremium);
        purchaseSubscrib = (Button) findViewById(R.id.onPurchaseSubscrib);
        purchasePremium.setOnClickListener(this);
        purchasePremium.setOnClickListener(this);
        purchaseSubscrib.setOnClickListener(this);
        purchase.setOnClickListener(this);
    }

    @Override
    public void onIabSetUpFinishedListener(IabResult result) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.onPurchase:
                inAppPurchase.onPurchaseManagedConsumeProduct(this, "ITEM_SKU");
                break;
            case R.id.onPurchasePremium:
                inAppPurchase.onPurchaseManagedPremiumProduct(this, "ITEM_SKU");
                break;
            case R.id.onPurchaseSubscrib:
                inAppPurchase.onPurchasedSubscription(this, "ITEM_SKU");
                break;
        }
    }

    @Override
    public void subscriptionFinishedListener(IabResult result, Purchase info) {
        // callback that notifies when a Susscrib purchase product finished
    }

    @Override
    public void onConsumeFinishedListener(Purchase purchase, IabResult result) {
        //Consume poduct callback that notifies when a consumption operation finished.
    }

    @Override
    public void managedPremiumProductFinishedListener(IabResult result, Purchase info) {
        // callback that notifies when a purchase product(Premium) finished
    }

    @Override
    public void managedProductConsumeFinishedListener(IabResult result, Purchase info) {
        // callback that notifies when a purchase product(Consume) finished
    }

    /* in case we have to consume a product use this
                inAppPurchase.onConsumeProduct(this, info);*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        inAppPurchase.StartSetup(this);
        if (requestCode == LibraryConstant.RC_REQUEST) {

        }
        if (inAppPurchase.onActivityRresult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        return;
    }
}
