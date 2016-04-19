/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ihasama.ohtu.domain;

import java.util.HashMap;
import java.util.Map;

public class Reference {
    private EntryType entryType;
    private String id;
    private Map<FieldType, String> fields;

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
            String value = convertAccentedChars(e.getValue());
            sb.append(type).append(" = {").append(value).append("},\n");
        }

        sb.append("}");
        return sb.toString();
    }

    private String convertAccentedChars(String string) {
        StringBuilder newString = new StringBuilder();

        for (int i = 0; i< string.length(); i++) {
            char c = string.charAt(i);

            switch (c) {
                case 'ä': newString.append("{\\\"a}"); break;
                case 'ö': newString.append("{\\\"o}"); break;
                case 'Ä': newString.append("{\\\"A}"); break;
                case 'Ö': newString.append("{\\\"O}"); break;
                case 'Å': newString.append("{\\\"AA}"); break;
                case 'å': newString.append("{\\\"aa}"); break;
                default: newString.append(c);
            }
        }

        return newString.toString();
    }

    public Map<FieldType, String> getFields() {
        return fields;
    }


    public void setType(EntryType entryType) {
        this.entryType = entryType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFields(Map<FieldType, String> fields) {
        this.fields = fields;
    }
}
