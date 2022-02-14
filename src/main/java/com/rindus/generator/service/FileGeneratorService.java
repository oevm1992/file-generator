package com.rindus.generator.service;

import com.google.gson.Gson;
import com.rindus.generator.exception.FileGeneratorException;
import com.rindus.generator.file.FileExtension;
import com.thoughtworks.xstream.XStream;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class FileGeneratorService {

    public byte[] createFile(Object data, FileExtension fileExtension) throws Exception {
        if (FileExtension.XML.equals(fileExtension)) {
            return createXmlFileByte(data);
        }
        if (FileExtension.JSON.equals(fileExtension)) {
            return createJsonFileByte(data);
        }
        throw new FileGeneratorException("Invalid file extension");
    }

    private byte[] createJsonFileByte(Object data) throws Exception {
        Gson gson = new Gson();
        return gson.toJson(data).getBytes(StandardCharsets.UTF_8);
    }

    private byte[] createXmlFileByte(Object data) throws Exception {
        XStream xstream = new XStream();
        return xstream.toXML(data).getBytes(StandardCharsets.UTF_8);
    }

}
