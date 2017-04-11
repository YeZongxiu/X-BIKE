package com.ucmed.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

public class ModelDataObjectUtil {
    private static Logger log = Logger.getLogger(ModelDataObjectUtil.class);

    public static <T> T model2do(Object model, Class<T> dataObjectClass) {

        if(model == null) {
            return null;
        }

        T dataObject = null;
        try {
            dataObject = dataObjectClass.newInstance();
        } catch(InstantiationException e1) {
            log.error(e1);
        } catch(IllegalAccessException e2) {
            log.error(e2);
        }

        List<Field> fields = new ArrayList<Field>();
        getAllField(fields, dataObject.getClass(), 1);
        for(Field field : fields) {
            try {
                Field modelField = getFiledByName(model.getClass(),
                    field.getName());

                field.setAccessible(true);
                if(modelField == null)
                    continue;
                modelField.setAccessible(true);
                field.set(dataObject, modelField.get(model));
            } catch(Exception e) {
                log.warn(e.getMessage());
                continue;
            }
        }
        return dataObject;

    }

    public static <T> T do2model(Object dataObject, Class<T> modelClass) {

        if(dataObject == null) {
            return null;
        }

        T model = null;
        try {
            model = modelClass.newInstance();
        } catch(InstantiationException e1) {
            log.error(e1);
        } catch(IllegalAccessException e1) {
            log.error(e1);
        }

        List<Field> fields = new ArrayList<Field>();
        getAllField(fields, dataObject.getClass(), 1);

        for(Field field : fields) {
            try {
                Field modelField = getFiledByName(model.getClass(),
                    field.getName());

                field.setAccessible(true);
                if(modelField == null)
                    continue;
                modelField.setAccessible(true);
                modelField.set(model, field.get(dataObject));
            } catch(Exception e) {
                log.warn(e.getMessage());
                continue;
            }
        }
        return model;
    }
    /**
     * 将DataObject 类列表转化为Modle列表.
     * @param l DataObject 类列表.
     * @param modelClass 要转化的目标class.
     * @return model 列表或者空的列表.
     */
    @SuppressWarnings("rawtypes")
    public static <T> List<T> doList2ModelList(List l, Class<T> modelClass) {
        if(null == l || l.isEmpty()) {
            return new ArrayList<T>();
        }
        List<T> models = new ArrayList<T>();
        for(Object o : l) {
            T model = do2model(o, modelClass);
            if(null != model) {
                models.add(model);
            }
        }
        return models;
    }

    private static List<Field> getAllField(List<Field> list,
        Class<?> currentClass, int i) {
        if(i >= 5) {
            return list;
        }
        try {
            if(currentClass == null)
                return list;
            Class<?> superClass = currentClass.getSuperclass();

            Field field[] = currentClass.getDeclaredFields();
            if(null != field) {
                CollectionUtils.addAll(list, field);
                return getAllField(list, superClass, ++i);
            }
            return list;
        } catch(Exception e) {
            return list;
        }
    }

    private static Field getFiledByName(Class<?> clazz, String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch(SecurityException e) {
        } catch(NoSuchFieldException e) {
        }
        if(field == null && clazz.getSuperclass() != null) {
            return getFiledByName(clazz.getSuperclass(), fieldName);
        } else {
            return field;
        }
    }

}
