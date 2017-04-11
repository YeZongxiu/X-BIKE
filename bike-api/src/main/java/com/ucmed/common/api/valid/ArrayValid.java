package com.ucmed.common.api.valid;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.Element;
import com.ucmed.common.exception.ValidateException;

import java.util.Iterator;
import java.util.List;
import com.ucmed.common.util.TypeUtil;
import com.ucmed.common.util.ValueUtil;

/**
 * Created by ucmed on 2017/3/15.
 */
public class ArrayValid extends AbsValid {
    public static final String NAME = "array";

    public ArrayValid() {
    }

    public void valid(JSONObject res, JSONObject params, Element el) throws ValidateException {
        String name = el.attributeValue("name");
        if(name == null) {
            throw new ValidateException(Integer.valueOf(4), "api definition is not valid, name of array element is null");
        } else {
            String r = el.attributeValue("required");
            JSONArray v = params.optJSONArray(name);
            String alias = el.attributeValue("alias");
            if(r != null && "true".equalsIgnoreCase(r) && v == null) {
                throw new ValidateException(Integer.valueOf(2), "api missing param: " + name);
            } else if(v != null) {
                int s = v.size();
                if(s >= ValueUtil.getInteger(el.attributeValue("min"), Integer.valueOf(0)).intValue() && s <= ValueUtil.getInteger(el.attributeValue("max"), Integer.valueOf(2147483647)).intValue()) {
                    JSONArray temp = new JSONArray();
                    this.validateElement(temp, v, el);
                    res.put(alias == null?name:alias, temp);
                } else {
                    throw new ValidateException(Integer.valueOf(2), "api param array incorrect length: " + name);
                }
            }
        }
    }

    public String getNodeName() {
        return "array";
    }

    private void validateElement(JSONArray temp, JSONArray v, Element el) throws ValidateException {
        if(v != null) {
            this.validateElementNode(temp, v, el);
        }
    }

    private void validateElementNode(JSONArray temp, JSONArray v, Element e) throws ValidateException {
        String t = e.attributeValue("type");
        t = t == null?"key":t;
        List els2;
        Object arr;
        Iterator arr2;
        if("key".equalsIgnoreCase(t)) {
            els2 = e.elements();
            if(els2.size() != 1) {
                throw new ValidateException(Integer.valueOf(4), "api definition is not valid, array\'s value element must single");
            }

            Element a = (Element)els2.get(0);
            if(a == null || !"value".equals(a.getName())) {
                throw new ValidateException(Integer.valueOf(4), "api definition is not valid, array\'s child node must exists and be value");
            }

            String t2 = a.attributeValue("type");
            if(t2 == null) {
                throw new ValidateException(Integer.valueOf(4), "api definition is not valid, value\'s type must exists");
            }

            arr2 = v.iterator();

            while(arr2.hasNext()) {
                arr = arr2.next();
                if(!TypeUtil.isValidBaseType(arr, t2)) {
                    throw new ValidateException(Integer.valueOf(2), "api param type error of array\'s value: " + v);
                }

                temp.add(arr);
            }
        } else {
            Object a1;
            Iterator t21;
            if("object".equalsIgnoreCase(t)) {
                els2 = e.elements();
                if(els2.size() < 1) {
                    throw new ValidateException(Integer.valueOf(4), "api definition is not valid, array\'s object element\'s children must bigger than 0");
                }

                t21 = v.iterator();

                while(t21.hasNext()) {
                    a1 = t21.next();
                    JSONObject arr1 = new JSONObject();
                    arr2 = null;

                    JSONObject arr21;
                    try {
                        arr21 = (JSONObject)a1;
                    } catch (Exception var13) {
                        throw new ValidateException(Integer.valueOf(2), "api params type error of array\'s object element\'s child:" + a1);
                    }

                    Iterator var11 = els2.iterator();

                    while(var11.hasNext()) {
                        Element e2 = (Element)var11.next();
                        this.getValidFactory().getValid(e2.getName()).valid(arr1, arr21, e2);
                    }

                    temp.add(arr1);
                }
            } else {
                if(!"array".equalsIgnoreCase(t)) {
                    throw new ValidateException(Integer.valueOf(4), "api definition is not valid, unkown type of array:" + t);
                }

                els2 = e.elements();
                if(els2.size() != 1) {
                    throw new ValidateException(Integer.valueOf(4), "api definition is not valid, array\'s element\'s child must single, whose element is array");
                }

                t21 = v.iterator();

                while(t21.hasNext()) {
                    a1 = t21.next();
                    arr = null;

                    JSONArray arr3;
                    try {
                        arr3 = (JSONArray)a1;
                    } catch (Exception var12) {
                        throw new ValidateException(Integer.valueOf(2), "api params type error of array\'s element, whose type is array:" + a1);
                    }

                    JSONArray arr22 = new JSONArray();
                    this.validateElement(arr22, arr3, (Element)els2.get(0));
                    temp.add(arr22);
                }
            }
        }

    }

}
