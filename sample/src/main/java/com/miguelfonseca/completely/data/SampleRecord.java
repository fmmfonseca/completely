package com.miguelfonseca.completely.data;

import java.util.Arrays;
import java.util.List;

public class SampleRecord implements Indexable
{
    private final String name;

    public SampleRecord(String name)
    {
        this.name = name;
    }

    @Override
    public List<String> getFields()
    {
        return Arrays.asList(name);
    }

    public String getName()
    {
        return name;
    }
}
