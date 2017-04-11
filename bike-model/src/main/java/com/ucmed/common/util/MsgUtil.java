package com.ucmed.common.util;

import net.rubyeye.xmemcached.MemcachedClient;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ucmed on 2017/3/16.
 */
public class MsgUtil {
    public static final int DEFAULT_VERIFY_CODE_LENGTH = 6;

    public static final String TAG_NET_ACCOUNTER_REG_MSG = "NET_ACCOUNTER_REG";


    private static final Logger LOG = LoggerFactory.getLogger(MsgUtil.class);

    private static ThreadPoolFactory msgThreadPoolFactory = ApplicationContextUtil
            .getBean("msgThreadPoolFactory", ThreadPoolFactory.class);


    /**
     * 获得或生成短信验证码
     *
     * @param memcachedClient
     *            cache manager.
     * @param tag
     *            api name.
     * @param phone
     *            phone number
     * @param i
     *            length of generated verify code.Set value as
     *            DEFAULT_VERIFY_CODE_LENGTH if i<=0;
     * @return cached generated code or null.
     */
    public static String getCachedNumber(MemcachedClient memcachedClient, String tag,
                                         String phone, int i) {
        if(i <= 0) {
            i = DEFAULT_VERIFY_CODE_LENGTH;
        }
        if(StringUtils.isNotBlank(phone) && memcachedClient != null) {
            try {
                String no = (String) memcachedClient.get(Constants.CACHE_TAG + tag
                        + phone, Constants.NUMBER_QUERY_CACHED_TIME_IN_SECOND);
                if(no == null) {
                    //TODO 测试期间验证码默认为123456
                    no = "123456";
                    //no = RandomStringUtils.randomNumeric(i);
                    memcachedClient.set(Constants.CACHE_TAG + tag + phone, Constants.NUMBER_QUERY_CACHED_TIME_IN_SECOND, no);
                }
                return no;
            } catch(Exception e) {
            }
        }
        // ?如果缓存为空时，返回给用户验证码会导致用户收到验证码却验证不了吧。
        // return RandomStringUtils.randomNumeric(i);
        return null;
    }

    public static String getCachedMsg(MemcachedClient memcachedClient, String tag,
                                      String phone) throws Exception  {
        String no = null;
        if(StringUtils.isNotBlank(phone) && memcachedClient != null) {
            no = (String) memcachedClient.get(Constants.CACHE_TAG + tag
                    + phone, Constants.NUMBER_QUERY_CACHED_TIME_IN_SECOND);
        }
        if (no != null){
            memcachedClient.delete(Constants.CACHE_TAG + tag
                    + phone);
            return  no;
        }
        return null;
    }

}

