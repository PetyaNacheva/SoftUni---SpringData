package softuni.exam.util;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface XmlParser {
    <T> T parseXml(String filePath, Class<T> objectClass) throws JAXBException, FileNotFoundException;

    // The object that will be exported, its class, FILE_PATH
    <O> void exportXml(O object, Class<O> objectClass, String filePath) throws JAXBException;
}
