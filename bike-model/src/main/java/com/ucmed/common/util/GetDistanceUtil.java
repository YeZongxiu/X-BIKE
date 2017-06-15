package com.ucmed.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.chainsaw.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Created by wzyk88 on 2017/4/8.
 */
public class GetDistanceUtil {
    private static final Logger LOG = LoggerFactory.getLogger(GetDistanceUtil.class);

    private static Double rad(Double d){
        return  d * Math.PI / 180.0;
    }

    public static Double getDistanceByGPS(Double longitude, Double latitude, Double log, Double lati){
        Double radLat1 = rad(latitude);
        Double radLat2 = rad(lati);
        Double a = radLat1 - radLat2;
        Double b = rad(longitude - log);
        Double d;
        Double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * Constants.R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(latitude) * Math.cos(lati) * sb2 * sb2));
        return d;
    }

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

    public static JSONObject getByIp(){
        String body;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String url = "http://api.map.baidu.com/location/ip?" + "&ak=" + Constants.BAIDUAK + "&coor=bd09ll";
        try {
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity);
            JSONObject object = JSONObject.fromObject(body);
            if (0 == object.optInt("status")){
                JSONObject result = object.optJSONObject("content").optJSONObject("point");
                return result;
            }
        } catch (Exception e){

        }finally {
            httpclient.getConnectionManager().shutdown();
        }
        return null;
    }

    public static void tranToBaiDu(JSONObject result, String listName){
        JSONArray array = result.optJSONArray(listName);
        JSONArray newArray = new JSONArray();
        for (Object object : array) {
            JSONObject obj = JSONObject.fromObject(object);
            getGPSFromGaoDe(obj);
            newArray.add(obj);
        }
        result.put(listName, newArray);
    }

    public static void getGPSFromGaoDe(JSONObject object){
        String body;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String url = "http://api.map.baidu.com/geoconv/v1/?coords=" + object.optString("longitude") + "," + object.optString("latitude") + "&from=3&ak=" + Constants.BAIDUAK;
        try {
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity);
            JSONObject baidu = JSONObject.fromObject(body);
            if (0 == object.optInt("status")){
                JSONArray result = baidu.optJSONArray("result");
                if (result != null && !result.isEmpty()){
                    object.put("longitude", JSONObject.fromObject(result.opt(0)).optString("x"));
                    object.put("latitude", JSONObject.fromObject(result.opt(0)).optString("y"));
                }
            }
        } catch (Exception e){

        }finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

    public static void toGaoDe(JSONObject object){
        String body;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String url = "http://restapi.amap.com/v3/assistant/coordinate/convert?locations=" + object.optString("longitude") + "," + object.optString("latitude") + "&coordsys=baidu&output=JSON&key=" + Constants.GAODE;
        try {
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity);
            JSONObject gaode = JSONObject.fromObject(body);
            if ("1".equals(gaode.optString("status"))){
                String[] locations = gaode.optString("locations").split(",");
                Double longitude = Double.parseDouble(locations[0]);
                Double latitude = Double.parseDouble(locations[1]);
                BigDecimal logiDecimal = new BigDecimal(longitude);
                Double logi = logiDecimal.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
                BigDecimal latiDecimal = new BigDecimal(latitude);
                Double lati = latiDecimal.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
                object.put("longitude", logi.toString());
                object.put("latitude", lati.toString());
            }
        } catch (Exception e){
            LOG.info(e.getMessage());
        }finally {
            httpclient.getConnectionManager().shutdown();
        }
    }
}
