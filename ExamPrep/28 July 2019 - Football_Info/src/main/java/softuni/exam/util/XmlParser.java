package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;


public interface XmlParser {
    @SuppressWarnings("unchecked")
    <T> T parseXml(String filePath, Class<T> objectClass) throws JAXBException, FileNotFoundException;

    <O> void exportXml(O object, Class<O> objectClass, String filePath) throws JAXBException;
}
