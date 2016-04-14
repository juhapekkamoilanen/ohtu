/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ihasama.ohtu.domain;

import java.util.HashMap;
import java.util.Map;

public class Reference {
    private final EntryType entryType;
    private final String id;
    private final Map<FieldType, String> fields;
    
    public Reference(EntryType type, String id) {
        this.entryType = type;
        this.id = id;
        this.fields = new HashMap<>();
    }
    
    public EntryType getType() {
        return this.entryType;
    }
    
    public String getId() {
        return this.id;
    }
    
    public boolean addField(FieldType type, String value) {
        if (fields.containsKey(type))
            return false;
        
        fields.put(type, value);
        return true;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("@").append(entryType.toString().toLowerCase());
        sb.append("{").append(id).append(",\n");
        
        for (Map.Entry<FieldType, String> e : fields.entrySet()) {
            String type = e.getKey().toString().toLowerCase();
            String value = e.getValue();
            sb.append(type).append(" = {").append(value).append("},\n");
        }
        
        sb.append("}");
        return sb.toString();
    }

    public Map<FieldType, String> getFields() {
        return fields;
    }
    
    
}
