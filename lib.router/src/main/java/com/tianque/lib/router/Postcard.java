package com.tianque.lib.router;

import android.net.Uri;

import com.tianque.lib.router.post.OnPostResultListener;
import com.tianque.lib.router.post.PostRequest;

public final class Postcard  {
    private Uri uri;
    private Object tag;             // A tag prepare for some thing wrong.
    private Bundle mBundle;         // Data to tranform
//    private int flags = -1;         // Flags of route
//    private int timeout = 300;      // Navigation timeout, TimeUnit.Second !
//    private IProvider provider;     // It will be set value, if this postcard was provider.
//    private boolean greenChannal;

//    // copy from RouteMeta
//    private RouteType type;         // Type of route
//    private Element rawType;        // Raw type of route
//    private Class<?> destination;   // Destination
    private String path;            // Path of route
    private String query;            // Path of route
//    private String group;           // Group of route
//    private int priority = -1;      // The smaller the number, the higher the priority
//    private int extra;              // Extra data
//    private Map<String, Integer> paramsType;  // Param type
//    // ......

    private PostRequest postRequest;

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public void setBundle(Bundle bundle) {
        this.mBundle = bundle;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public PostRequest getPostRequest() {
        return postRequest;
    }

    public void setPostRequest(PostRequest postRequest) {
        this.postRequest = postRequest;
    }
}
