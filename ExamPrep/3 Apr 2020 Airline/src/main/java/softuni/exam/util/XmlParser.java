package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

@Component
public interface XmlParser {
    <T> T parseXml(String filePath, Class<T> objectClass) throws JAXBException, FileNotFoundException;

    // The object that will be exported, its class, FILE_PATH
    <T> void exportXml(T object, Class<T> objectClass, String filePath) throws JAXBException;
}
