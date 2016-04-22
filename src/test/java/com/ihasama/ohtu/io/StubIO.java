package com.ihasama.ohtu.io;

import com.ihasama.ohtu.io.IO;
import java.io.IOException;
import java.util.ArrayList;

public class StubIO implements IO
{

    private String[] lines;
    private int i;
    private ArrayList<String> prints;

    public StubIO(String... values)
    {
        this.lines = values;
        prints = new ArrayList<String>();
    }

    public void println() {
        println("");
    }

    public void println(Object obj) {
        prints.add(obj.toString());
    }

    public int readInt(String prompt)
    {
        println(prompt);
        return Integer.parseInt(lines[i++]);
    }

    public ArrayList<String> getPrints() {
        return prints;
    }

    public String readLine(String prompt)
    {
        println(prompt);
        if (i < lines.length) {
            return lines[i++];
        }
        return "";
    }

    @Override
    public void close() throws IOException {
    }
}
