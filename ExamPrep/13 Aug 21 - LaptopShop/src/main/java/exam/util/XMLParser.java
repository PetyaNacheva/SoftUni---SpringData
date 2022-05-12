package exam.util;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface XMLParser {
    @SuppressWarnings("unchecked")
    <T> T parseXml(String filePath, Class<T> objectClass) throws JAXBException, FileNotFoundException;

    <O> void exportXml(O object, Class<O> objectClass, String filePath) throws JAXBException;
}
