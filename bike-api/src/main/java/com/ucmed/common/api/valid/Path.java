package com.ucmed.common.api.valid;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by ucmed on 2017/3/15.
 */
public interface Path {
    Map<String, InputStream> getValidators();
}
