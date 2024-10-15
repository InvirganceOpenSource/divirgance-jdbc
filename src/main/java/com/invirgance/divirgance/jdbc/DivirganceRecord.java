/*
 * Copyright 2024 INVIRGANCE LLC

Permission is hereby granted, free of charge, to any person obtaining a copy 
of this software and associated documentation files (the “Software”), to deal 
in the Software without restriction, including without limitation the rights to 
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies 
of the Software, and to permit persons to whom the Software is furnished to do 
so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all 
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
SOFTWARE.
 */
package com.invirgance.divirgance.jdbc;

import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author jbanes
 */
public class DivirganceRecord implements Map<String, Object>
{
    private final HashMap<String, Object> map;
    
    private boolean ordered = false;
    private OrderedKeys<String> orderedKeys;

    public DivirganceRecord()
    {
        this(true);
    }

    public DivirganceRecord(boolean ordered)
    {
        map = new HashMap<>();
        this.ordered = ordered;
        
        if(ordered) this.orderedKeys = new OrderedKeys<>();
    }
    
    public DivirganceRecord(Map<String, Object> map)
    {
        this();
        
        this.map.putAll(map);
        
        if(map instanceof DivirganceRecord)
        {
            if(((DivirganceRecord)map).ordered)
            {
                this.ordered = true;
                this.orderedKeys = new OrderedKeys<>(((DivirganceRecord)map).orderedKeys);
            }
        }
    }
    
    @Override
    public int size()
    {
        return this.map.size();
    }

    @Override
    public boolean isEmpty()
    {
        return this.map.isEmpty();
    }
    
    public boolean isNull(String key)
    {
        return (this.map.get(key) == null);
    }

    @Override
    public boolean containsKey(Object key)
    {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value)
    {
        return this.map.containsValue(value);
    }

    @Override
    public Object get(Object key)
    {
        return this.map.get(key);
    }
    
    public boolean getBoolean(String key) throws SQLException
    {
        Object value = this.map.get(key);
        
        if(value == null) throw new SQLException(key + " is null and therefore can't be converted to a boolean");
        if(value instanceof Boolean) return ((Boolean)value);
        if(value instanceof String) return Boolean.parseBoolean(value.toString());
        
        throw new SQLException("Class type of " + value.getClass().getName() + " for " + key + " cannot be converted to a boolean");
    }
    
    public boolean getBoolean(String key, boolean defaultValue) throws SQLException
    {
        Object value = this.map.get(key);
        
        if(value == null) return defaultValue;
        if(value instanceof Boolean) return ((Boolean)value);
        if(value instanceof String) return Boolean.parseBoolean(value.toString());
        
        throw new SQLException("Class type of " + value.getClass().getName() + " for " + key + " cannot be converted to a boolean");
    }
    
    public double getDouble(String key) throws SQLException
    {
        Object value = this.map.get(key);
        
        if(value == null) throw new SQLException(key + " is null and therefore can't be converted to a double");
        if(value instanceof Double) return ((Double)value);
        if(value instanceof String) return Double.parseDouble(value.toString());
        
        throw new SQLException("Class type of " + value.getClass().getName() + " for " + key + " cannot be converted to a double");
    }
    
    public double getDouble(String key, double defaultValue) throws SQLException
    {
        Object value = this.map.get(key);
        
        if(value == null) return defaultValue;
        if(value instanceof Double) return ((Double)value);
        if(value instanceof String) return Double.parseDouble(value.toString());
        
        throw new SQLException("Class type of " + value.getClass().getName() + " for " + key + " cannot be converted to a double");
    }
    
    public int getInt(String key) throws SQLException
    {
        Object value = this.map.get(key);
        
        if(value == null) throw new SQLException(key + " is null and therefore can't be converted to an int");
        if(value instanceof Integer) return ((Integer)value);
        if(value instanceof String) return Integer.parseInt(value.toString());
        
        throw new SQLException("Class type of " + value.getClass().getName() + " for " + key + " cannot be converted to an int");
    }
    
    public int getInt(String key, int defaultValue) throws SQLException
    {
        Object value = this.map.get(key);
        
        if(value == null) return defaultValue;
        if(value instanceof Integer) return ((Integer)value);
        if(value instanceof String) return Integer.parseInt(value.toString());
        
        throw new SQLException("Class type of " + value.getClass().getName() + " for " + key + " cannot be converted to an int");
    }
    
    public ArrayList getJSONArray(String key) throws SQLException
    {
        Object value = this.map.get(key);
        
        if(value == null) return null;
        if(value instanceof ArrayList) return ((ArrayList)value);
        
        throw new SQLException("Class type of " + value.getClass().getName() + " for " + key + " cannot be converted to a JSONArray");
    }
    
    public ArrayList getJSONArray(String key, ArrayList defaultValue) throws SQLException
    {
        Object value = this.map.get(key);
        
        if(value == null) return defaultValue;
        if(value instanceof ArrayList) return ((ArrayList)value);
        
        throw new SQLException("Class type of " + value.getClass().getName() + " for " + key + " cannot be converted to a JSONArray");
    }
    
    public DivirganceRecord getJSONObject(String key) throws SQLException
    {
        Object value = this.map.get(key);
        
        if(value == null) return null;
        if(value instanceof DivirganceRecord) return ((DivirganceRecord)value);
        
        throw new SQLException("Class type of " + value.getClass().getName() + " for " + key + " cannot be converted to a JSONObject");
    }
    
    public DivirganceRecord getJSONObject(String key, DivirganceRecord defaultValue) throws SQLException
    {
        Object value = this.map.get(key);
        
        if(value == null) return defaultValue;
        if(value instanceof DivirganceRecord) return ((DivirganceRecord)value);
        
        throw new SQLException("Class type of " + value.getClass().getName() + " for " + key + " cannot be converted to a JSONObject");
    }
    
    public String getString(String key)
    {
        Object value = this.map.get(key);
        
        if(value == null) return null;
        
        return value.toString();
    }
    
    public String getString(String key, String defaultValue)
    {
        Object value = this.map.get(key);
        
        if(value == null) return defaultValue;
        
        return value.toString();
    }

    @Override
    public Object put(String key, Object value)
    {
        if(ordered && !orderedKeys.contains(key)) orderedKeys.add(key);
        
        return this.map.put(key, value);
    }

    @Override
    public Object remove(Object key)
    {
        if(ordered) orderedKeys.remove((String)key);
        
        return this.map.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> map)
    {
        if(ordered)
        {
            for(String key : map.keySet())
            {
                if(!orderedKeys.contains(key)) orderedKeys.add(key);
            }
        }
        
        this.map.putAll(map);
    }

    @Override
    public void clear()
    {
        if(ordered) orderedKeys.clear();
        
        this.map.clear();
    }

    @Override
    public Set<String> keySet()
    {
        if(ordered) return orderedKeys;
        
        return this.map.keySet();
    }

    @Override
    public Collection<Object> values()
    {
        return this.map.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet()
    {
        return this.map.entrySet();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        DivirganceRecord other;
        Object left;
        Object right;
        
        if(obj == this) return true;
        if(!(obj instanceof DivirganceRecord)) return false;
        
        other = (DivirganceRecord)obj;
        
        if(other.size() != size()) return false;
        
        for(String key : keySet())
        {
            if(!other.containsKey(key)) return false;
            
            left = get(key);
            right = other.get(key);
            
            if(left == null && right == null) continue;
            if(left == null) return false;
            if(right == null) return false;

            if(!left.getClass().equals(right.getClass())) return false;
            if(!left.equals(right)) return false;
        }
        
        return true;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 0xC0FFEE;
        Object value;

        for(String key : keySet())
        {
            value = get(key);
            
            if(value == null) continue;

            hash += value.hashCode();
        }

        return hash + size();
    }
    
    private class OrderedKeys<T> extends ArrayList<T> implements Set<T>
    {
        public OrderedKeys()
        {
            super();
        }
        
        public OrderedKeys(Collection collection)
        {
            super(collection);
        }
    }
}
