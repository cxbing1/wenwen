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

     public EventModel setEventType(EventType eventType) {
          this.eventType = eventType;
          return this;
     }

     public int getActionId() {
          return actionId;
     }

     public EventModel setActionId(int actionId) {
          this.actionId = actionId;
          return this;
     }

     public int getEntityType() {
          return entityType;
     }

     public EventModel setEntityType(int entityType) {
          this.entityType = entityType;
          return this;
     }

     public int getEntityId() {
          return entityId;
     }

     public EventModel setEntityId(int entityId) {
          this.entityId = entityId;
          return this;
     }

     public int getEntityOwnerId() {
          return entityOwnerId;
     }

     public EventModel setEntityOwnerId(int entityOwnerId) {
          this.entityOwnerId = entityOwnerId;
          return this;
     }

     public Map<String, String> getExts() {
          return exts;
     }

     public EventModel setExts(Map<String, String> exts) {
          this.exts = exts;
          return this;
     }



}
