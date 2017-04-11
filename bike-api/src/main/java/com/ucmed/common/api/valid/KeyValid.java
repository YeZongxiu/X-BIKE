package com.ucmed.common.api.valid;

import net.sf.json.JSONObject;
import org.dom4j.Element;
import com.ucmed.common.exception.ValidateException;
import com.ucmed.common.util.ValueUtil;
import com.ucmed.common.util.TypeUtil;

/**
 * Created by ucmed on 2017/3/15.
 */
public class KeyValid implements Valid {
    private static final String NAME = "key";

    public KeyValid() {
    }

    public void valid(JSONObject res, JSONObject params, Element el) throws ValidateException {
        String name = el.attributeValue("name");
        if(name == null) {
            throw new ValidateException(Integer.valueOf(4), "api definition is not valid, name of key element must exists");
        } else {
            String r = el.attributeValue("required");
            String t = el.attributeValue("type");
            if(t == null) {
                throw new ValidateException(Integer.valueOf(4), "api definition is not valid, type of key element must exists: " + name);
            } else {
                String alias = el.attributeValue("alias");
                Object v = params.get(name);
                if(r != null && "true".equalsIgnoreCase(r) && !ValueUtil.hasValue(v)) {
                    throw new ValidateException(Integer.valueOf(2), "api missing param of key element: " + name);
                } else if((r != null && "false".equalsIgnoreCase(r) || r == null) && !ValueUtil.hasValue(v)) {
                    String d = el.attributeValue("default");
                    if(d != null) {
                        v = TypeUtil.getValue(t, d);
                        res.put(alias == null?name:alias, v);
                    }

                } else if(ValueUtil.hasValue(v)) {
                    res.put(alias == null?name:alias, TypeUtil.getValidValue(v, t));
                }
            }
        }
    }

    public String getNodeName() {
        return "key";
    }
}
