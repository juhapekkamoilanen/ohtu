
package com.ihasama.ohtu.io;

import java.io.Closeable;
import java.io.Flushable;

public interface IO extends Closeable, Flushable
{
    void print(Object obj);
    void println();
    void println(Object obj);
    void truncate();
    String readLine();
}
