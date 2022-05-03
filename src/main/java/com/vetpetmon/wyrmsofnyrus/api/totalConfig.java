package com.vetpetmon.wyrmsofnyrus.api;

public class totalConfig implements IConfigs{
    String name;

    private totalConfig(String name)
    {
        this.name=name;
    }

    @Override
    public String getName()
    {
        return name;
    }

    public String toString()
    {
        return this.getName();
    }
}
