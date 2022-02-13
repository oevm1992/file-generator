package com.rindus.generator.util;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import java.nio.charset.StandardCharsets;

public class FileUtils {

    public static byte[] createJsonFileByte(Object data) throws Exception {
        Gson gson = new Gson();
        return gson.toJson(data).getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] createXmlFileByte(Object data) throws Exception {
        XStream xstream = new XStream();
        return xstream.toXML(data).getBytes(StandardCharsets.UTF_8);
    }
}
