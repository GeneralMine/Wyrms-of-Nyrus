package com.vetpetmon.wyrmsofnyrus.api;

import java.util.HashMap;
import java.util.Map;

public class configBase {
    public final Map<String,String> values = new HashMap<String, String>();

    public boolean getBoolean(IConfigs option)
    {
        return Boolean.valueOf(values.get(option.getName()));
    }

    public int getInteger(IConfigs option)
    {
        return Integer.valueOf(values.get(option.getName()));
    }

    public void put(IConfigs option, Object obj)
    {
        values.put(option.getName(), ""+obj);
    }
}
