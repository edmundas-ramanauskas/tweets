package co.alttab.tweets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by edmundas on 16.5.31.
 */
public class XmlSerializer<T> implements Serializer<T> {

    private final Class<T> clazz;

    public XmlSerializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    public String serialize(T source)  {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            Writer writer = new StringWriter();
            jaxbMarshaller.marshal(source, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException("Can't serialize to xml", e);
        }
    }
}
