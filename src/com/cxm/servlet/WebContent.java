package com.cxm.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 将数据转换成Map存储
 * url-pattern --> servlet-name --> servlet-class
 */
public class WebContent {
    private List<Entity> entities;
    private List<Mapping> mappings;
    private Map<String,String> entityMap=new HashMap<>();
    private Map<String,String> mappingMap=new HashMap<>();

    public WebContent(List<Entity> entities, List<Mapping> mappings) {
        this.entities = entities;
        this.mappings = mappings;

        /**
         * key --> servlet-name
         * value --> servlet-class
         */
        for (Entity e : entities) {
            entityMap.put(e.getName(),e.getClz());
        }

        for (Mapping m :mappings) {
            Set<String> p = m.getPatterns();
            for (String s :p) {
                mappingMap.put(s,m.getName());
            }
        }
    }
    public String getClz(String pattern){
        String name = mappingMap.get(pattern);
        System.out.println(name);
        String clazz = entityMap.get(name);
        System.out.println(clazz);
        return clazz;
    }
}
