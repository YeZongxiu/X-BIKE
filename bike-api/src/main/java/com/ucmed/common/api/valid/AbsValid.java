package com.ucmed.common.api.valid;

import net.sf.json.JSONObject;
import org.dom4j.Element;
import com.ucmed.common.exception.ValidateException;;

/**
 * Created by ucmed on 2017/3/15.
 */
public abstract class AbsValid implements Valid {
    private ValidFactory factory;

    public AbsValid() {
    }

    public void validate(JSONObject res, JSONObject params, Element el) throws ValidateException {
        String elName = el.getName();
        Valid v = this.factory.getValid(elName);
        if(v != null) {
            v.valid(res, params, el);
        } else {
            throw new ValidateException(Integer.valueOf(4), "api definition is not valid, unknown valid of element name:" + elName);
        }
    }

    public void setFactory(ValidFactory factory) {
        this.factory = factory;
    }

    protected ValidFactory getValidFactory() {
        return this.factory;
    }
}