package com.pdmaf.utils.database;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import org.svenson.JSON;
import com.pdmaf.utils.exceptions.ApplicationException;

/**
 * Used to pass query options to view queries.
 * For example:
 * <pre>
 * database.queryView("company/all", Map.class, new Options().count(1).descending(true);
 * </pre>
 * 
 * this is to shield the client code from lower level classes.
 *
 */
public class KeyOption extends HashMap<String, Object> {
    private static final long serialVersionUID = -4025495141211906568L;

    private JSON optionsJSON = new JSON();

    public KeyOption() {}

    public KeyOption(Map<String,String> map)
    {
        putAll(map);
    }

    /**
     * Copies the options of the given Options object if it is not <code>null</code>.
     *
     * @param keyOptions   Options to be copied, can be <code>null</code>.
     */
    public KeyOption(KeyOption keyOptions)
    {
        if (keyOptions != null)
        {
            for (Map.Entry<String, Object> e : keyOptions.entrySet())
            {
                put(e.getKey(),e.getValue());
            }
        }
    }

    public KeyOption(String key, Object value)
    {
        put(key, value);
    }

    @Override
    public KeyOption put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }

    public KeyOption key(Object key)
    {
        return put("key",key);
    }

    public KeyOption startKey(Object key)
    {
        return put("startkey",key);
    }

    public KeyOption startKeyDocId(String docId)
    {
        return put("startkey_docid", docId);
    }

    public KeyOption endKey(Object key)
    {
        return put("endkey",key);
    }

    public KeyOption limit(int limit)
    {
        return put("limit", limit);
    }

    public KeyOption update(boolean update)
    {
        return put("update",update);
    }

    public KeyOption descending(boolean update)
    {
        return put("descending",update);
    }

    public KeyOption skip(int skip)
    {
        return put("skip",skip);
    }

    public KeyOption group(boolean group)
    {
        return put("group",group);
    }

    public KeyOption reduce(boolean reduce)
    {
        return put("reduce",reduce);
    }

    public KeyOption includeDocs(boolean includeDocs)
    {
        return put("include_docs",includeDocs);
    }

    public String toQuery()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("?");

        boolean first = true;
        try
        {
            for (Map.Entry<String, Object> e : entrySet())
            {
                if (!first)
                {
                    sb.append("&");
                }
                sb.append(e.getKey()).append("=");
                String json = optionsJSON.forValue(e.getValue());

                sb.append(URLEncoder.encode(json, "UTF-8"));
                first = false;
            }
            if (sb.length() <= 1)
            {
                return "";
            }
            else
            {
                return sb.toString();
            }
        }
        catch (UnsupportedEncodingException e)
        {
            throw new ApplicationException("KeyOption: toQuery - unsupported encoding for the string.");
        }
    }

    public Map<String, String> asMap() {
        Map<String, String> map = new HashMap<String, String>();
        Iterator iterator = this.keySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>) iterator.next();
            map.put(pairs.getKey(), pairs.getValue().toString());
        }
            
        return map;
    }
    /**
     * Can be imported statically to have a syntax a la <code>option().count(1);</code>.
     * @return new Option instance
     */
    public static KeyOption instance()
    {
        return new KeyOption();
    }
}