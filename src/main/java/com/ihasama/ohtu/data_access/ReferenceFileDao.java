/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ihasama.ohtu.data_access;

import com.ihasama.ohtu.domain.Reference;
import com.ihasama.ohtu.exception.InvalidFileException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReferenceFileDao extends FileDao<Reference> {
    
    private static final Logger LOGGER = Logger.getLogger(ReferenceFileDao.class.getName());

    public ReferenceFileDao(File file) throws InvalidFileException, IOException {
        super(file);
        validateFile();
    }

    /**
     * Called upon initialization. Validates the loaded file once for errors.
     * @throws InvalidFileException 
     */
    protected void validateFile() throws InvalidFileException, IOException {
        Pattern patt = Pattern.compile("((@.+\\{.+,\\s(\\s*.+\\s?=\\s?\\{.+\\},\\s*)+\\})\\s*)*");
        BufferedReader bf = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bf.readLine()) != null) {
            sb.append(line);
        }
        
        Matcher m = patt.matcher(sb.toString());
        if (!m.matches())
            throw new InvalidFileException();
    }

    @Override
    public List<Reference> getObjects() {
        List<Reference> references = new ArrayList<>();        
        List<String> blocks = new ArrayList<>();
        int level = 0;        
        StringBuilder sb = new StringBuilder();
        
        try {
            String line;
            BufferedReader bf = new BufferedReader(new FileReader(file));
            while ((line = bf.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    sb.append(line.charAt(i));
                    switch (line.charAt(i)) {
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
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "", ex);
            return new ArrayList<>();
        }
        
        for (String s : blocks)
            System.out.println(s);
        
        return references;
    }

    @Override
    public void add(Reference t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
