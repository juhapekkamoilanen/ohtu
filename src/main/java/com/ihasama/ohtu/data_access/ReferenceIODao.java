/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ihasama.ohtu.data_access;

import com.ihasama.ohtu.domain.EntryType;
import com.ihasama.ohtu.domain.FieldType;
import com.ihasama.ohtu.domain.Reference;
import com.ihasama.ohtu.exception.InvalidFileException;
import com.ihasama.ohtu.io.IO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReferenceIODao extends IODao<Reference> {
    
    private final String fileData;

    public ReferenceIODao(IO io) throws InvalidFileException, IOException {
        super(io);
        fileData = loadIOData();
        validateIOData();
    }
    
    protected String loadIOData() throws FileNotFoundException, IOException {
        
        // read from the beginning
        io.resetOutput();
        
        StringBuilder sb = new StringBuilder();
        
        String line;
        while ((line = io.readLine()) != null)
            sb.append(line);
        
        return sb.toString();
    }

    /**
     * Called upon initialization. Validates the loaded file once for errors.
     * 
     * @throws InvalidFileException 
     */
    protected void validateIOData() throws InvalidFileException, IOException {
        Pattern patt = Pattern.compile("((@.+\\{.+,\\s(\\s*.+\\s?=\\s?\\{.+\\},\\s*)+\\})\\s*)*");        
        Matcher m = patt.matcher(fileData);
        if (!m.matches())
            throw new InvalidFileException();
    }
    
    protected List<String> getReferenceBlocks() {
        List<String> blocks = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int level = 0;
        
        for (int i = 0; i < fileData.length(); i++) {
            
            // maybe optimize to append substrings instead?
            sb.append(fileData.charAt(i));
            switch (fileData.charAt(i)) {
                case '{':
                    level++;
                    break;
                case '}':
                    if (--level == 0) {
                        blocks.add(sb.toString().trim());
                        sb = new StringBuilder();
                    }                                
                    break;
                default:
                    break;
            }
        }
        
        return blocks;
    }

    @Override
    public List<Reference> getObjects() {
        List<Reference> references = new ArrayList<>();
        List<String> blocks = getReferenceBlocks();
        
        for (String b : blocks) {
            String refType = b.substring(1, b.indexOf('{')).toUpperCase();
            String id = b.substring(b.indexOf('{') + 1, b.indexOf(','));
            String[] lines = b.substring(b.indexOf(',') + 1, b.length() - 1).split("},\\s*");
            Reference ref = new Reference(EntryType.valueOf(refType), id);
            
            for (String line : lines) {
                String[] pieces = line.split("\\s=\\s\\{");
                FieldType type = FieldType.valueOf(pieces[0].toUpperCase());
                ref.addField(type, pieces[1]);
            }
            
            references.add(ref);
        }
        
        return references;
    }

    @Override
    public void add(Reference t) {
        io.println(t);
        io.println();
    }

    @Override
    public void removeAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Reference t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
