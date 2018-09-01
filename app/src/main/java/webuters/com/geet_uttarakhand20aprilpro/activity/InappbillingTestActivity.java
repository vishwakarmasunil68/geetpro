package webuters.com.geet_uttarakhand20aprilpro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import webuters.com.geet_uttarakhand20aprilpro.R;
import webuters.com.geet_uttarakhand20aprilpro.Utils.TagUtils;
import webuters.com.geet_uttarakhand20aprilpro.util.IabBroadcastReceiver;
import webuters.com.geet_uttarakhand20aprilpro.util.IabHelper;
import webuters.com.geet_uttarakhand20aprilpro.util.IabResult;
import webuters.com.geet_uttarakhand20aprilpro.util.Inventory;
import webuters.com.geet_uttarakhand20aprilpro.util.Purchase;

public class InappbillingTestActivity extends AppCompatActivity{
    Button btn_download;
    String base64EncodedPublicKey;
    IabHelper mHelper;
    IabBroadcastReceiver mBroadcastReceiver;
//    private final String ITEM_SKU = "android.test.purchased";
    private final String ITEM_SKU = "song_10";
    static final int RC_REQUEST = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inappbilling_test);
        btn_download = findViewById(R.id.btn_download);
        base64EncodedPublicKey = getResources().getString(R.string.google_licence_key);

        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d(TagUtils.getTag(),"Problem setting up in-app billing: " + result);
                    return;
                }

                // Hooray, IAB is fully set up. Now, let's get an inventory of
                // stuff we own.
                Log.d(TagUtils.getTag(), "Setup successful. Querying inventory.");
                try {
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mHelper.launchPurchaseFlow(InappbillingTestActivity.this, ITEM_SKU, 10001,
                            mPurchaseFinishedListener, "mypurchasetoken");
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {
            Log.d(TagUtils.getTag(), "Query inventory finished.");
            if (result.isFailure()) {
                Log.d(TagUtils.getTag(),"Failed to query inventory: " + result);
                return;
            }

            Log.d(TagUtils.getTag(), "Query inventory was successful.");

                /*
                 * Check for items we own. Notice that for each purchase, we check
                 * the developer payload to see if it's correct! See
                 * verifyDeveloperPayload().
                 */

            // // Check for gas delivery -- if we own gas, we should fill up the
            // tank immediately
            Purchase gasPurchase = inventory.getPurchase(ITEM_SKU);
            if (gasPurchase != null ) {
                Log.d(TagUtils.getTag(), "We have gas. Consuming it.");
                try {
                    mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
                            mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase) {
            Log.d(TagUtils.getTag(), "Purchase finished: " + result + ", purchase: "
                    + purchase);
            if (result.isFailure()) {
                Log.d(TagUtils.getTag(),"Error purchasing: " + result);
                return;
            }
//            if (!verifyDeveloperPayload(purchase)) {
//                complain("Error purchasing. Authenticity verification failed.");
//                return;
//            }

            Log.d(TagUtils.getTag(), "Purchase successful.");

            if (purchase.getSku().equals(ITEM_SKU)) {

                // remove query inventory method from here and put consumeAsync() directly
                try {
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }

            }


        }
    };

    public void consumeItem() {
        try {
            mHelper.queryInventoryAsync(mReceivedInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
    }

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {


            if (result.isFailure()) {
                // Handle failure
                Log.d(TagUtils.getTag(),"error in consuming product");
            } else {
                try {
                    mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
                            mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {

                        //final listener
                        Log.d(TagUtils.getTag(),"product consumed");
                    } else {
                        // handle error
                        Log.d(TagUtils.getTag(),"product consumed failed");
                    }
                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) try {
            mHelper.dispose();
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
        mHelper = null;
    }
}
