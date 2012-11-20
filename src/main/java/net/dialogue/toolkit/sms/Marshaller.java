package net.dialogue.toolkit.sms;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterMatcher;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

class Marshaller extends XStreamMarshaller {

    public Marshaller() {
        setAnnotatedClasses(new Class<?>[]{
                SendSmsRequest.class,
                SendSmsResponse.class,
                Sms.class
        });

        setConverters(new ConverterMatcher[] { new RequestConverter() });
    }

    private static class RequestConverter implements Converter {

        public boolean canConvert(Class clazz) {
            return SendSmsRequest.class.isAssignableFrom(clazz);
        }

        public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
            SendSmsRequest request = (SendSmsRequest) value;
            context.convertAnother(request.getMessages(), new RequestConverter.ListConverter("X-E3-Message"));
            context.convertAnother(request.getRecipients(), new RequestConverter.ListConverter("X-E3-Recipients"));
            for (Map.Entry<String, String> entry : request.entrySet()) {
                writer.startNode(entry.getKey());
                writer.setValue(entry.getValue());
                writer.endNode();
            }
        }

        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
            throw new UnsupportedOperationException();
        }

        private static class ListConverter implements Converter {

            private String elementName;

            public ListConverter(String elementName) {
                this.elementName = elementName;
            }

            public boolean canConvert(Class clazz) {
                return List.class.isAssignableFrom(clazz);
            }

            public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
                for (String entry : (List<String>) value) {
                    writer.startNode(elementName);
                    writer.setValue(entry);
                    writer.endNode();
                }
            }

            public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
                throw new UnsupportedOperationException();
            }
        }
    }

    @Override
    public void marshalWriter(Object graph, Writer writer) throws XmlMappingException, IOException {
        super.marshalWriter(graph, writer);
    }
}
