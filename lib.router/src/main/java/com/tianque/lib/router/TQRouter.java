package com.tianque.lib.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.tianque.lib.router.post.OnPostResultListener;
import com.tianque.lib.router.post.PostRequest;

import java.util.Map;

public class TQRouter {
    public static Context mContext;
    public static BundleLauncher routerLauncher;
    public static final String KEY_QUERY = "tqrouter-query";
    public static final int REQUEST_CODE_DEFAULT = 10000;
    private static final String SHARED_PREFERENCES_SMALL = "tqrouter";
    private static final String SHARED_PREFERENCES_KEY_VERSION = "version";
    private static final String SHARED_PREFERENCES_BUNDLE_VERSIONS = "tqrouter.app-versions";
    private static final String SHARED_PREFERENCES_BUNDLE_MODIFIES = "tqrouter.app-modifies";
    private static final String SHARED_PREFERENCES_BUNDLE_UPGRADES = "tqrouter.app-upgrades";

    private static String sBaseUri = ""; // base url of uri

    private static boolean sHasSetUp;


    public static BundleLauncher getRouterLauncher() {
        return routerLauncher;
    }

    public static void setRouterLauncher(BundleLauncher routerLauncher) {
        TQRouter.routerLauncher = routerLauncher;
    }


    public static void setUp(Context context) {
        setUp(context, null, null);
    }


    public static void setUp(Context context, BundleLauncher bundleLauncher) {
        setUp(context, bundleLauncher, null);
    }

    public static void setUp(Context context, BundleLauncher bundleLauncher, OnCompleteListener listener) {
        mContext = context;
        if (bundleLauncher != null) setRouterLauncher(bundleLauncher);
        else
            setRouterLauncher(new ActivityBundleLauncher());
        if (sHasSetUp) {
            if (listener != null) {
                listener.onComplete();
            }
            return;
        }
        Bundle.loadLaunchableBundles(listener);
        sHasSetUp = true;
    }

    /**
     * 用来注册插件的路由地址，由不同的插件来调用
     */
    public static void register(Context context) {
        Bundle.registerSubBundles(context);
    }

    public static Context getContext() {
        return mContext;
    }

    public static boolean openUri(String uriString, Context context) {
        return openUri(makeUri(uriString), context);
    }

    public static boolean openUri(Uri uri, Context context) {
        // System url schemes
        String scheme = uri.getScheme();
        if (scheme != null
                && !scheme.equals("http")
                && !scheme.equals("https")
                && !scheme.equals("file")) {
            ApplicationUtils.openUri(uri, context);
            return true;
        }

        // Small url schemes
        Postcard postcard = Bundle.makePostcard(uri);
        if (postcard != null) {
            postcard.getBundle().launchFrom(context, postcard);
            return true;
        }
        return false;
    }


    public static boolean openUriWithResult(String uriString, Context context, android.app.FragmentManager fragmentManager, OnPostResultListener onPostResultListener) {
        PostRequest postRequest = new PostRequest();
        postRequest.setFragmentManager(fragmentManager);
        postRequest.setOnPostResultListener(onPostResultListener);
        return openUriWithResult(makeUri(uriString), context, postRequest);
    }

    public static boolean openUriWithResult(String uriString, Context context, android.support.v4.app.FragmentManager fragmentManager, OnPostResultListener onPostResultListener) {
        PostRequest postRequest = new PostRequest();
        postRequest.setV4FragmentManager(fragmentManager);
        postRequest.setOnPostResultListener(onPostResultListener);
        return openUriWithResult(makeUri(uriString), context, postRequest);
    }

    public static boolean openUriWithResult(Uri uri, Context context, PostRequest postRequest) {
        // System url schemes
        String scheme = uri.getScheme();
        if (scheme != null
                && !scheme.equals("http")
                && !scheme.equals("https")
                && !scheme.equals("file")) {
            ApplicationUtils.openUri(uri, context);
            return true;
        }

        // Small url schemes
        Postcard postcard = Bundle.makePostcard(uri);
        if (postcard != null) {
            if (postRequest != null) {
                postcard.setPostRequest(postRequest);
            }
            postcard.getBundle().launchFrom(context, postcard);
            return true;
        }
        return false;
    }

    public static Intent getIntentOfUri(String uriString, Context context) {
        return getIntentOfUri(makeUri(uriString), context);
    }

    public static Intent getIntentOfUri(Uri uri, Context context) {
        // System url schemes
        String scheme = uri.getScheme();
        if (scheme != null
                && !scheme.equals("http")
                && !scheme.equals("https")
                && !scheme.equals("file")) {
            return ApplicationUtils.getIntentOfUri(uri);
        }

        // Small url schemes
        Postcard postcard = Bundle.makePostcard(uri);
        if (postcard != null) {
            return postcard.getBundle().createIntent(context, postcard);
        }
        return null;
    }

    public static <T> T createFragment(String uriString, Context context) {
        return createObject("fragment", uriString, context);
    }

    public static <T> T createFragmentV4(String uriString, Context context) {
        return createObject("fragment-v4", uriString, context);
    }

    public static <T> T createObject(String type, String uriString, Context context) {
        return createObject(type, makeUri(uriString), context);
    }

    public static <T> T createObject(String type, Uri uri, Context context) {
        Postcard postcard = Bundle.makePostcard(uri);
        if (postcard != null) {
            return postcard.getBundle().createObject(postcard, context, type);
        }
        return null;
    }

    public static Uri getUri(Activity context) {
        android.os.Bundle extras = context.getIntent().getExtras();
        if (extras == null) {
            return null;
        }
        String query = extras.getString(KEY_QUERY);
        if (query == null) {
            return null;
        }
        return Uri.parse(query);
    }

    public static void setBaseUri(String url) {
        sBaseUri = url;
    }

    public static String getBaseUri() {
        return sBaseUri;
    }


    public static SharedPreferences getSharedPreferences() {
        return getContext().getSharedPreferences(SHARED_PREFERENCES_SMALL, 0);
    }

    public static Map<String, Integer> getBundleVersions() {
        return (Map<String, Integer>) getContext().
                getSharedPreferences(SHARED_PREFERENCES_BUNDLE_VERSIONS, 0).getAll();
    }

    public interface OnCompleteListener {
        void onComplete();
    }

    //______________________________________________________________________________________________
    // Private

    private static Uri makeUri(String uriString) {
        if (!uriString.startsWith("http://")
                && !uriString.startsWith("https://")
                && !uriString.startsWith("file://")) {
            uriString = sBaseUri + uriString;
        }
        return Uri.parse(uriString);
    }
}
