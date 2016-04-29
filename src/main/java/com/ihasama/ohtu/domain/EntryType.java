/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihasama.ohtu.domain;

import java.util.Arrays;
import java.util.List;

public enum EntryType {    
    ARTICLE(
            new FieldType[]{FieldType.ADDRESS, FieldType.TITLE, FieldType.JOURNAL, FieldType.YEAR, FieldType.VOLUME},
            new FieldType[]{FieldType.NUMBER, FieldType.PAGES, FieldType.MONTH, FieldType.NOTE, FieldType.KEY}
    ),
    BOOK(
            new FieldType[]{FieldType.AUTHOR, FieldType.TITLE, FieldType.PUBLISHER, FieldType.YEAR},
            new FieldType[]{FieldType.VOLUME, FieldType.SERIES, FieldType.ADDRESS, FieldType.EDITION, FieldType.MONTH, FieldType.NOTE, FieldType.KEY}
    ),
    BOOKLET(
            new FieldType[]{FieldType.TITLE},
            new FieldType[]{FieldType.AUTHOR, FieldType.HOWPUBLISHED, FieldType.ADDRESS, FieldType.MONTH, FieldType.YEAR, FieldType.NOTE, FieldType.KEY}
    ),
    CONFERENCE(
            new FieldType[]{FieldType.AUTHOR, FieldType.TITLE, FieldType.BOOKTITLE, FieldType.YEAR},
            new FieldType[]{FieldType.VOLUME, FieldType.SERIES, FieldType.PAGES, FieldType.ADDRESS, FieldType.MONTH, FieldType.ORGANIZATION, FieldType.PUBLISHER, FieldType.NOTE, FieldType.KEY}
    ),
    INBOOK(
            new FieldType[]{FieldType.AUTHOR, FieldType.TITLE, FieldType.CHAPTER, FieldType.PUBLISHER, FieldType.YEAR},
            new FieldType[]{FieldType.VOLUME, FieldType.SERIES, FieldType.TYPE, FieldType.ADDRESS, FieldType.EDITION, FieldType.MONTH, FieldType.NOTE, FieldType.KEY}
    ),
    INCOLLECTION(
            new FieldType[]{FieldType.AUTHOR, FieldType.TITLE, FieldType.BOOKTITLE, FieldType.PUBLISHER, FieldType.YEAR},
            new FieldType[]{FieldType.VOLUME, FieldType.SERIES, FieldType.EDITOR, FieldType.TYPE, FieldType.CHAPTER, FieldType.PAGES, FieldType.ADDRESS, FieldType.EDITION, FieldType.MONTH, FieldType.NOTE, FieldType.KEY}
    ),
    INPROCEEDINGS(
            new FieldType[]{FieldType.AUTHOR, FieldType.TITLE, FieldType.BOOKTITLE, FieldType.YEAR},
            new FieldType[]{FieldType.VOLUME, FieldType.SERIES, FieldType.PAGES, FieldType.ADDRESS, FieldType.MONTH, FieldType.EDITOR, FieldType.ORGANIZATION, FieldType.PUBLISHER, FieldType.NOTE, FieldType.KEY}
    ),
    MANUAL(
            new FieldType[]{FieldType.TITLE},
            new FieldType[]{FieldType.AUTHOR, FieldType.ORGANIZATION, FieldType.ADDRESS, FieldType.EDITION, FieldType.MONTH, FieldType.YEAR, FieldType.NOTE, FieldType.KEY}
    ),
    MASTERSTHESIS(
            new FieldType[]{FieldType.AUTHOR, FieldType.TITLE, FieldType.SCHOOL, FieldType.YEAR},
            new FieldType[]{FieldType.TYPE, FieldType.ADDRESS, FieldType.MONTH, FieldType.NOTE, FieldType.KEY}
    ),
    MISC(
            new FieldType[]{},
            new FieldType[]{FieldType.AUTHOR, FieldType.TITLE, FieldType.HOWPUBLISHED, FieldType.MONTH, FieldType.YEAR, FieldType.NOTE, FieldType.KEY}
    ),
    PHDTHESIS(
            new FieldType[]{FieldType.AUTHOR, FieldType.TITLE, FieldType.SCHOOL, FieldType.YEAR},
            new FieldType[]{FieldType.TYPE, FieldType.ADDRESS, FieldType.MONTH, FieldType.NOTE, FieldType.KEY}
    ),
    PROCEEDINGS(
            new FieldType[]{FieldType.TITLE, FieldType.YEAR},
            new FieldType[]{FieldType.VOLUME, FieldType.SERIES, FieldType.ADDRESS, FieldType.MONTH, FieldType.PUBLISHER, FieldType.EDITOR, FieldType.ORGANIZATION, FieldType.NOTE, FieldType.KEY}
    ),
    TECHREPORT(
            new FieldType[]{FieldType.AUTHOR, FieldType.TITLE, FieldType.INSTITUTION, FieldType.YEAR},
            new FieldType[]{FieldType.TYPE, FieldType.NUMBER, FieldType.ADDRESS, FieldType.MONTH, FieldType.NOTE, FieldType.KEY}
    ),
    UNPUBLISHED(
            new FieldType[]{FieldType.AUTHOR, FieldType.TITLE, FieldType.NOTE},
            new FieldType[]{FieldType.MONTH, FieldType.YEAR, FieldType.KEY}
    );

    private final FieldType[] requiredFields;
    private final FieldType[] optionalFields;

    private EntryType(FieldType[] req, FieldType[] opt) {
        this.requiredFields = req;
        this.optionalFields = opt;
    }
    
    public FieldType[] getRequiredFieldTypes() {
        return Arrays.copyOf(requiredFields, requiredFields.length);
    }
    
    public FieldType[] getOptionalFieldTypes() {
        return Arrays.copyOf(optionalFields, optionalFields.length);
    }
    
    public FieldType[] getValidFieldTypes() {
        List<FieldType> all = Arrays.asList(requiredFields);
        all.addAll(Arrays.asList(optionalFields));
        return all.toArray(new FieldType[] {});
    }
}