package softuni.exam.util;

import org.springframework.stereotype.Component;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Component
public class XmlParserImpl implements XmlParser{
    @Override
    @SuppressWarnings("unchecked")
    public <T> T parseXml(String filePath, Class<T> objectClass) throws JAXBException, FileNotFoundException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(objectClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (T) unmarshaller.unmarshal(new FileReader(filePath));

    }

    @Override
    public <O> void exportXml(O object, Class<O> objectClass, String filePath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(objectClass);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        marshaller.marshal(object, new File(filePath));
    }
}
