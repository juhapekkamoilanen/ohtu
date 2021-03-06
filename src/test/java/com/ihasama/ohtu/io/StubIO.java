package com.ihasama.ohtu.io;

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

    @Override
    public void println() {
        println("");
    }

    @Override
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
    public void print(Object obj) {
        println(obj);
    }

    @Override
    public String readLine() {
        if (i < lines.length) {
            return lines[i++];
        }
        return "";
    }

    @Override
    public void close() {
    }

    @Override
    public void truncate() {
        prints.clear();
    }

    @Override
    public void flush() throws IOException {
    }
}
