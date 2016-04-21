
package com.ihasama.ohtu.io;

import java.io.Closeable;

public interface IO extends Closeable
{
    void println();
    void println(Object obj);
    int readInt(String prompt);
    String readLine(String prompt);
}
