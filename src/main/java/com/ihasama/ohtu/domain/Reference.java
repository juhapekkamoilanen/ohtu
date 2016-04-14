/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ihasama.ohtu.domain;

import java.util.HashMap;
import java.util.Map;

public class Reference {
    private final EntryType type;
    private final Map<FieldType, String> fields;
    
    public Reference(EntryType type) {
        this.type = type;
        this.fields = new HashMap<>();
    }
}
