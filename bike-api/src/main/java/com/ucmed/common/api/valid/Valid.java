package com.ucmed.common.api.valid;

import net.sf.json.JSONObject;
import org.dom4j.Element;
import com.ucmed.common.exception.ValidateException;

/**
 * Created by ucmed on 2017/3/15.
 */
public interface Valid {
    void valid(JSONObject var1, JSONObject var2, Element var3) throws ValidateException;

    String getNodeName();
}
