/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ihasama.ohtu.data_access;

import com.ihasama.ohtu.exception.InvalidFileException;
import java.io.File;

public abstract class FileDao<T> implements Dao<T> {
    
    protected final File file;
    
    public FileDao(File file) throws InvalidFileException {
        this.file = file;
    }

}
