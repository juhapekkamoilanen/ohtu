
package com.ihasama.ohtu.io;

import java.io.Closeable;

public interface IO extends Closeable
{
    void print(Object obj);
    void println();
    void println(Object obj);
    int readInt();
    String readLine();
}
