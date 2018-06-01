package com.tianque.lib.router;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

final class ApplicationUtils {


    /**
     * Wrap an system action intent by the <tt>uri</tt>
     * @param uri the intent uri
     * @return
     */
    public static Intent getIntentOfUri(Uri uri) {
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    /**
     * Start an activity related to the <tt>uri</tt>
     * @param uri the intent uri
     * @param context current context
     */
    public static void openUri(Uri uri, Context context) {
        Intent intent = getIntentOfUri(uri);
        context.startActivity(intent);
    }
}
