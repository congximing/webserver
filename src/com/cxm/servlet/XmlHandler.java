package com.cxm.servlet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XmlHandler extends DefaultHandler {
    private List<Entity> entities;
    private List<Mapping> mappings;
    private Entity entity;
    private Mapping mapping;
    private boolean isMap = false;
    private String tag;

    @Override
    public void startDocument() throws SAXException {
        entities = new ArrayList<Entity>();
        mappings = new ArrayList<Mapping>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("servlet".equals(qName)){
            entity = new Entity();
            isMap = false;
        }else if ("servlet-mapping".equals(qName)){
            mapping = new Mapping();
            isMap = true;
        }
        tag = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch,start,length).trim();//!!!!!
        if (content.length() > 0){
            if (!isMap){
                if ("servlet-name".equals(tag)){
                    entity.setName(content);
                }else if ("servlet-class".equals(tag)){
                    entity.setClz(content);
                }
            }
            else {
                if ("servlet-name".equals(tag)){
                    mapping.setName(content);
                }else if ("url-pattern".equals(tag)){
                    mapping.addPattern(content);
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("servlet".equals(qName)){
            entities.add(entity);
        } else if ("servlet-mapping".equals(qName)) {
            mappings.add(mapping);
        }
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }
}
