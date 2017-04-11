package com.ucmed.common.api.valid;

import net.sf.json.JSONObject;
import org.dom4j.Element;
import com.ucmed.common.exception.ValidateException;

import java.util.Iterator;
import java.util.List;

/**
 * Created by ucmed on 2017/3/15.
 */
public class ObjectValid extends AbsValid {
    private static final String NAME = "object";

    public ObjectValid() {
    }

    public void valid(JSONObject res, JSONObject params, Element el) throws ValidateException {
        String name = el.attributeValue("name");
        if(name == null) {
            throw new ValidateException(Integer.valueOf(4), "api definition is not valid, object element\'s name must exists");
        } else {
            String r = el.attributeValue("required");
            JSONObject v = params.optJSONObject(name);
            String alias = el.attributeValue("alias");
            if(r != null && "true".equalsIgnoreCase(r) && v == null) {
                throw new ValidateException(Integer.valueOf(2), "api missing param of object element: " + name);
            } else if(v != null) {
                JSONObject temp = new JSONObject();
                JSONObject t2 = res.optJSONObject(name);
                if(t2 != null) {
                    Iterator e = t2.keySet().iterator();

                    while(e.hasNext()) {
                        Object els = e.next();
                        temp.put(els, t2.get(els));
                    }
                }

                List els1 = el.elements();
                Iterator var12 = els1.iterator();

                while(var12.hasNext()) {
                    Element e1 = (Element)var12.next();
                    this.validate(temp, v, e1);
                }

                res.put(alias == null?name:alias, temp);
            }
        }
    }

    public String getNodeName() {
        return "object";
    }
}
