package softuni.exam.instagraphlite.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.modelmapper.Converter;
import softuni.exam.instagraphlite.util.XmlParser;
import softuni.exam.instagraphlite.util.Impl.XmlParserImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public Gson gson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    }
    @Bean
    public XmlParser xmlParser() {
        return new XmlParserImpl();
    }
    //имам го в util папката
    // @Bean
    //public ValidationUtil validationUtil() {
    //   return null;
    //}

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new Converter<String , LocalDate>() {
            @Override
            public LocalDate convert(MappingContext<String, LocalDate> mappingContext) {
                return LocalDate.parse(mappingContext.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
        });

        modelMapper.addConverter(new Converter<String , LocalDateTime>() {
            @Override
            public LocalDateTime convert(MappingContext<String, LocalDateTime> mappingContext) {
                LocalDateTime parsedDateTime = LocalDateTime.parse(mappingContext.getSource(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                return parsedDateTime;
            }
        });

        return modelMapper;
    }

    @Bean
    public BufferedReader bufferedReader(){
        return new BufferedReader(new BufferedReader(new InputStreamReader(System.in)));
    }
}
