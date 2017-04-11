package com.ucmed.common.api.valid;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by ucmed on 2017/3/15.
 */
public class ValidFactory {
    private Map<String, Valid> valids;

    public ValidFactory() {
    }

    public void setValids(Map<String, Valid> valids) {
        this.valids = valids;
        Iterator var3 = valids.keySet().iterator();

        while(var3.hasNext()) {
            String k = (String)var3.next();
            Valid v = (Valid)valids.get(k);
            if(v instanceof AbsValid) {
                ((AbsValid)v).setFactory(this);
            }
        }

    }

    public Valid getValid(String nodeName) {
        return this.valids != null?(Valid)this.valids.get(nodeName):null;
    }
}
