package com.tianque.lib.router;

import android.content.Context;
import com.tianque.lib.router.TQRouter;
import java.io.File;

/**
 * This class consists exclusively of static methods that operate on file.
 */
 final class FileUtils {
    private static final String DOWNLOAD_PATH = "tqrouter_patch";
    private static final String INTERNAL_PATH = "tqrouter_base";

    public static File getInternalFilesPath(String dir) {
        File file = TQRouter.getContext().getDir(dir, Context.MODE_PRIVATE);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File getDownloadBundlePath() {
        return getInternalFilesPath(DOWNLOAD_PATH);
    }

    public static File getInternalBundlePath() {
        return getInternalFilesPath(INTERNAL_PATH);
    }
}
