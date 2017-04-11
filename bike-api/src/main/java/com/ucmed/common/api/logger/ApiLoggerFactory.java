package com.ucmed.common.api.logger;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;
import com.ucmed.common.exception.ApiLoggerException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ucmed on 2017/3/15.
 */
public class ApiLoggerFactory {
    public static final Class LOG_CLASS = Log.class;
    public static final Logger LOG = Logger.getLogger(ApiLoggerFactory.class);
    private Object service = null;
    private String[] basePakages = new String[]{"com.ucmed.api.logger"};
    private String a = "classpath*:";
    private String b = "/**/*.class";
    private Map c = new HashMap();
    private ApiLogger defLogger = null;

    public ApiLoggerFactory() {
    }

    public Object getService() {
        return this.service;
    }

    public void setService(Object var1) {
        this.service = var1;
    }

    public String[] getBasePakages() {
        return this.basePakages;
    }

    public void setBasePakages(String[] var1) {
        this.basePakages = var1;
    }

    public ApiLogger getDefLogger() {
        return this.defLogger;
    }

    public void setDefLogger(ApiLogger var1) {
        this.defLogger = var1;
    }

    public void init() {
        if(this.getDefLogger() == null) {
            throw new RuntimeException("must definition a default Logger !!!");
        } else {
            this.getDefLogger().setService(this.service);
            LOG.debug("load logger class with annotation : " + LOG_CLASS.getName());
            if(this.basePakages != null) {
                PathMatchingResourcePatternResolver var1 = new PathMatchingResourcePatternResolver();
                String[] var5 = this.basePakages;
                int var4 = this.basePakages.length;

                for(int var3 = 0; var3 < var4; ++var3) {
                    String var2 = var5[var3];

                    try {
                        LOG.debug("start load logger class from path : " + var2);
                        Resource[] var6 = var1.getResources(this.a + ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(var2)) + this.b);
                        SimpleMetadataReaderFactory var7 = new SimpleMetadataReaderFactory();
                        Resource[] var10 = var6;
                        int var9 = var6.length;

                        for(int var8 = 0; var8 < var9; ++var8) {
                            Resource var21 = var10[var8];
                            AnnotationMetadata var22;
                            if((var22 = var7.getMetadataReader(var21).getAnnotationMetadata()).isConcrete() && var22.isIndependent() && !var22.isAbstract()) {
                                String var11 = var22.getClassName();
                                LOG.debug("start load logger class : " + var11);
                                Map var23;
                                if((var23 = var22.getAnnotationAttributes(LOG_CLASS.getName())) != null) {
                                    String[] var24;
                                    if((var24 = (String[])var23.get("apiName")) == null || var24.length <= 0) {
                                        throw new ApiLoggerException("load logger class(" + var11 + ")\'s annotation(" + LOG_CLASS.getName() + ") must has apiName value");
                                    }

                                    ApiLogger var12 = null;
                                    String[] var15 = var24;
                                    int var14 = var24.length;

                                    for(int var13 = 0; var13 < var14; ++var13) {
                                        String var25 = var15[var13];
                                        if(this.c.containsKey(var25)) {
                                            throw new ApiLoggerException("api name(" + var25 + ") is exists, check your logger code");
                                        }

                                        ApiLogger var10000 = var12 == null?(ApiLogger)ApiLogger.class.cast(Class.forName(var11).newInstance()):var12;
                                        var12 = var10000;
                                        var10000.setService(this.service);
                                        this.c.put(var25, var12);
                                    }
                                }
                            }
                        }

                        LOG.debug("end load logger class from path : " + var2);
                    } catch (IOException var16) {
                        LOG.warn(var16);
                    } catch (ApiLoggerException var17) {
                        LOG.warn(var17);
                    } catch (ClassNotFoundException var18) {
                        LOG.warn(var18);
                    } catch (InstantiationException var19) {
                        LOG.warn(var19);
                    } catch (IllegalAccessException var20) {
                        LOG.warn(var20);
                    }
                }
            }

        }
    }

    public ApiLogger getLogger(String var1) {
        ApiLogger var2;
        return (var2 = (ApiLogger)this.c.get(var1)) != null?var2:this.defLogger;
    }
}
