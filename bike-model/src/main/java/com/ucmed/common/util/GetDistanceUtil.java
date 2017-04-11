package com.ucmed.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by wzyk88 on 2017/4/8.
 */
public class GetDistanceUtil {
    public static JSONArray getPoint(JSONArray array, Double longitude, Double latitude, JSONArray list){
        Long distance = 0l;
        Integer pointIndex = 0;
        for (int i = 0; i < array.size(); i++) {
            JSONObject park = JSONObject.fromObject(array.get(i));
            Long pointDistance = getDistance(longitude, latitude, park.optDouble("longitude"), park.optDouble("latitude"));
            if (distance > pointDistance || distance == 0){
                distance = pointDistance;
                pointIndex = i;
            }
            if (i == (array.size() - 1)){
                list.add(array.get(pointIndex));
                array.remove(array.get(pointIndex));
                if (array != null && !array.isEmpty()){
                    JSONObject next = JSONObject.fromObject(array.get(pointIndex));
                    return getPoint(array, next.optDouble("longitude"), next.optDouble("latitude"), list);
                } else {
                    return  list;
                }
            }
        }
        return new JSONArray();
    }
    public static Long getDistance(Double longitude, Double latitude, Double lo, Double la){
        String body;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String url = "http://api.map.baidu.com/routematrix/v2/driving?output=json&origins=" + latitude + "," + longitude + "&destinations=" + la + "," + lo + "&ak=" + Constants.BAIDUAK;
        try {
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity);
            JSONObject object = JSONObject.fromObject(body);
            if (0 == object.optInt("status")){
                JSONArray result = object.optJSONArray("result");
                if (result != null && !result.isEmpty()){
                    return  JSONObject.fromObject(result.get(0)).optJSONObject("distance").optLong("value");
                }
            }
        } catch (Exception e){

        }finally {
            httpclient.getConnectionManager().shutdown();
        }
        return 0l;
    }

    public static JSONObject getBaiduGPS(Double longitude, Double latitude){
        String body;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String url = "http://api.map.baidu.com/geoconv/v1/?coords=" + longitude + "," + latitude + "&ak=" + Constants.BAIDUAK;
        try {
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity);
            JSONObject object = JSONObject.fromObject(body);
            if (0 == object.optInt("status")){
                JSONArray result = object.optJSONArray("result");
                if (result != null && !result.isEmpty()){
                    return  JSONObject.fromObject(result.opt(0));
                }
            }
        } catch (Exception e){

        }finally {
            httpclient.getConnectionManager().shutdown();
        }
        return new JSONObject();
    }
}
