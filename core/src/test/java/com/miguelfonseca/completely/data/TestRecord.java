package com.miguelfonseca.completely.data;

import java.util.Arrays;
import java.util.List;

public class TestRecord implements Indexable
{
    private final List<String> fields;

    public TestRecord(String... fields)
    {
        this.fields = Arrays.asList(fields);
    }

    @Override
    public List<String> getFields()
    {
        return fields;
    }
}
