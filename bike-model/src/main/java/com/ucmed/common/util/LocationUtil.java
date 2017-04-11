package com.ucmed.common.util;

import com.ucmed.common.model.location.CityModel;
import com.ucmed.common.model.location.ProvinceModel;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ucmed on 2017/3/15.
 */
@Service
public class LocationUtil {

    private static final Logger logger = Logger.getLogger(LocationUtil.class);

    /**
     *
     * @param locale
     * @return
     */
    public String loadLocation(Locale locale) {

        String localeStr = locale.toString();
        String path = "/location_" + localeStr + ".xml";
        InputStream is = this.getClass().getResourceAsStream(path);
        if (null == is) {
            path = "/location_zh_CN.xml";
            is = this.getClass().getResourceAsStream(path);
        }

        StringBuffer buf = new StringBuffer();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while (null != (line = reader.readLine())) {
                buf.append(line);
            }
        } catch (IOException e) {
            logger.error("", e);
        }

        return buf.toString();
    }

    @SuppressWarnings("unchecked")
    public String loadChineseLocation() {

        String path = "/location_zh_CN.xml";
        InputStream is = this.getClass().getResourceAsStream(path);
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(is);
            Element root = document.getRootElement();
            for (Element e : (List<Element>) root.elements()) {
                // 找到中国的地区
                if (e.attribute("Code").getValue().equals("1")) {
                    String s = e.asXML();
                    logger.debug(s);
                    return s;
                }
            }
        } catch (DocumentException e) {
            logger.error("InputStream:" + is, e);
        }

        return "";
    }

    /**
     * 获取省份列表
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ProvinceModel> loadProvinceList() {

        String path = "/location_zh_CN.xml";
        InputStream is = this.getClass().getResourceAsStream(path);
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(is);
            Element root = document.getRootElement();
            for (Element e : (List<Element>) root.elements()) {
                // 找到中国的地区
                if (e.attribute("Code").getValue().equals("1")) {
                    String s = e.asXML();
                    return getPorvinceListFromXMLStr(s);
                }
            }
        } catch (DocumentException e) {
            logger.error("InputStream:" + is, e);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private List<ProvinceModel> getPorvinceListFromXMLStr(String xmlDocStr) {
        StringReader read = new StringReader(xmlDocStr);
        InputSource source = new InputSource(read);
        SAXBuilder sb = new SAXBuilder();
        List<ProvinceModel> provinceList = new ArrayList<ProvinceModel>();
        try {

            org.jdom.Document doc = sb.build(source);
            org.jdom.Element rootElement = doc.getRootElement();
            List<Object> children = rootElement.getChildren();
            org.jdom.Element et = null;
            ProvinceModel pm = null;
            for (int i = 0; i < children.size(); i++) {
                et = (org.jdom.Element) children.get(i);
                pm = new ProvinceModel();
                pm.setCode(Integer.parseInt(et.getAttributeValue("Code")));
                pm.setName(et.getAttributeValue("Name"));
                provinceList.add(pm);
            }
        } catch (JDOMException | IOException e) {
            logger.error("获取省份列表异常:" + e);
        }

        return provinceList;
    }

    @SuppressWarnings("unchecked")
    public List<CityModel> loadCityListFromProvinceCode(String pName) {
        String path = "/location_zh_CN.xml";
        InputStream is = this.getClass().getResourceAsStream(path);
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(is);
            Element root = document.getRootElement();
            for (Element e : (List<Element>) root.elements()) {
                // 找到中国的地区
                if (e.attribute("Code").getValue().equals("1")) {
                    String s = e.asXML();
                    return getXMLCityFromXMLDocStr(s, pName);
                }
            }
        } catch (DocumentException e) {
            logger.error("InputStream:" + is, e);
        }

        return null;
    }

    /**
     * 根据省份code获取城市列表
     *
     * @param xmlDocStr
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<CityModel> getXMLCityFromXMLDocStr(String xmlDocStr, String pName) {
        try {
            Document document = DocumentHelper.parseText(xmlDocStr);
            Element root = document.getRootElement();
            for (Element e : (List<Element>) root.elements()) {
                // 找到对应的省份的市
                if (e.attribute("Name").getValue().equals(pName)) {
                    String s = e.asXML();
                    return getCityListFromXMLDocStr(s);
                }
            }
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }
        return null;

    }

    @SuppressWarnings("unchecked")
    private List<CityModel> getCityListFromXMLDocStr(String xmlDocStr) {
        StringReader read = new StringReader(xmlDocStr);
        InputSource source = new InputSource(read);
        SAXBuilder sb = new SAXBuilder();
        List<CityModel> cityList = new ArrayList<CityModel>();
        try {

            org.jdom.Document doc = sb.build(source);
            org.jdom.Element rootElement = doc.getRootElement();
            List<Object> children = rootElement.getChildren();
            org.jdom.Element et = null;
            CityModel cm = null;
            for (int i = 0; i < children.size(); i++) {
                et = (org.jdom.Element) children.get(i);
                cm = new CityModel();
                cm.setCode(Integer.parseInt(et.getAttributeValue("Code")));
                cm.setName(et.getAttributeValue("Name"));
                cityList.add(cm);
            }
        } catch (JDOMException | IOException e) {
            logger.error("获取省份列表异常:" + e);
        }

        return cityList;
    }

    public static void main(String[] args) {
        // new LocationUtil().loadProvinceList();
        // new LocationUtil().loadCityListFromProvinceCode(11);
        for (CityModel model : new LocationUtil().loadCityListFromProvinceCode("北京")) {
            System.out.println(model.getCode() + "  " + model.getName());
        }
    }
}
