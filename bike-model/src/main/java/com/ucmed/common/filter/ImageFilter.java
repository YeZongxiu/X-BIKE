package com.ucmed.common.filter;

import com.ucmed.common.util.Constants;

/**
 * Created by ucmed on 2017/3/18.
 */
public class ImageFilter extends FileFilter {
    @Override
    protected boolean validateFile(String originalFilename, int length) {
        return length > 0 ? super.validateFile(originalFilename, length)
                : false;
    }

    @Override
    public String getImageRoot() {
        return Constants.USER_IMG_UPLOAD_URL;
    }

    @Override
    public String getParamsName() {
        return Constants.PARAMS;
    }
}
