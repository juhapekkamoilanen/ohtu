
package com.ihasama.ohtu.io;

import java.io.IOException;

public interface IO
{
    void print(Object obj);
    void println();
    void println(Object obj);
    int readInt();
    String readLine();
    void flushInput();
    void resetOutput() throws IOException;
}
