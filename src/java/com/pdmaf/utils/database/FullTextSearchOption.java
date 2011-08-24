package com.pdmaf.utils.database;

import org.svenson.JSON;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

import com.pdmaf.utils.exceptions.ApplicationException;

/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 13, 2009
 * Time: 7:03:29 PM
 */
public class FullTextSearchOption extends HashMap<String, Object> {
    private static final long serialVersionUID = -4025495141211906568L;

    public FullTextSearchOption() {}

    public FullTextSearchOption(Map<String,String> map)
    {
        putAll(map);
    }

    /**
     * Copies the options of the given Options object if it is not <code>null</code>.
     *
     * @param FullTextSearchOptions   Options to be copied, can be <code>null</code>.
     */
    public FullTextSearchOption(FullTextSearchOption FullTextSearchOptions)
    {
        if (FullTextSearchOptions != null)
        {
            for (Map.Entry<String, Object> e : FullTextSearchOptions.entrySet())
            {
                put(e.getKey(),e.getValue());
            }
        }
    }

    public FullTextSearchOption(String key, Object value)
    {
        put(key, value);
    }

    @Override
    public FullTextSearchOption put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }

    /**
     * the string passed has to be in the format for lucene search terms:
     * http://lucene.apache.org/java/2_4_0/queryparsersyntax.html
     * 
     * @param key
     * @return
     */
    public FullTextSearchOption q(String key)
    {
        return put("q",key);
    }

    public FullTextSearchOption lang(String lang)
    {
        return put("lang", lang);
    }

    public FullTextSearchOption sort(String sort)
    {
        return put("sort", sort);
    }

    public FullTextSearchOption limit(int limit)
    {
        return put("limit", limit);
    }

    public FullTextSearchOption stale(String ok)
    {
        return put("stale", ok);
    }

    public FullTextSearchOption rewrite(boolean rewrite)
    {
        return put("rewrite", rewrite);
    }

    public FullTextSearchOption skip(int skip)
    {
        return put("skip",skip);
    }

    public FullTextSearchOption debug(boolean debug)
    {
        return put("debug",debug);
    }

    public FullTextSearchOption reduce(boolean reduce)
    {
        return put("reduce",reduce);
    }

    public FullTextSearchOption includeDocs(boolean includeDocs)
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
                String json = e.getValue().toString();

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
            throw new ApplicationException("FullTextSearchOption: toQuery - unsupported encoding for the string.");
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
    public static FullTextSearchOption instance()
    {
        return new FullTextSearchOption();
    }
}
