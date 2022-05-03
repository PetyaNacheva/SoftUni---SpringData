package com.example.football.util;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface XmlParser {
    <T> T parseXml(String filePath, Class<T> objectClass) throws JAXBException, FileNotFoundException;

    <T> void exportXml(T object, Class<T> objectClass, String filePath) throws JAXBException;
}
