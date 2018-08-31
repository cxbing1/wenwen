package com.xbcheng.wenwen.async;

import java.util.HashMap;
import java.util.Map;

public class EventModel {

     private EventType eventType;
     private int actionId;
     private int entityType;
     private int entityId;
     private int entityOwnerId;

     private Map<String,String> exts = new HashMap<>();

     public EventModel(){}

     public EventModel(EventType eventType, int actionId, int entityType, int entityId, int entityOwnerId) {
          this.eventType = eventType;
          this.actionId = actionId;
          this.entityType = entityType;
          this.entityId = entityId;
          this.entityOwnerId = entityOwnerId;
     }

     public EventModel setExts(String key, String value) {
          exts.put(key,value);
          return this;
     }

     public String getExts(String key) {
          return exts.get(key);
     }


     public EventType getEventType() {
          return eventType;
     }

     public void setEventType(EventType eventType) {
          this.eventType = eventType;
     }

     public int getActionId() {
          return actionId;
     }

     public void setActionId(int actionId) {
          this.actionId = actionId;
     }

     public int getEntityType() {
          return entityType;
     }

     public void setEntityType(int entityType) {
          this.entityType = entityType;
     }

     public int getEntityId() {
          return entityId;
     }

     public void setEntityId(int entityId) {
          this.entityId = entityId;
     }

     public int getEntityOwnerId() {
          return entityOwnerId;
     }

     public void setEntityOwnerId(int entityOwnerId) {
          this.entityOwnerId = entityOwnerId;
     }

     public Map<String, String> getExts() {
          return exts;
     }

     public void setExts(Map<String, String> exts) {
          this.exts = exts;
     }



}
