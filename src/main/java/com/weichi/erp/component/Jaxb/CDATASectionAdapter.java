package com.weichi.erp.component.Jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by Wewon on 2018/9/14.
 */
public class CDATASectionAdapter extends XmlAdapter<String, String> {
    @Override
    public String unmarshal(String v) throws Exception {
        return v;
    }

    @Override
    public String marshal(String v) throws Exception {
        if (v != null) {
            return "<![CDATA[" + v + "]]>";
        }
        return null;
    }

}
