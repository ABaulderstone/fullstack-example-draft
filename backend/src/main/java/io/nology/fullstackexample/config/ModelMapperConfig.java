package io.nology.fullstackexample.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper mapper = new ModelMapper();
    mapper
      .typeMap(String.class, String.class)
      .setConverter(new StringTrimConverter());
    mapper.getConfiguration().setSkipNullEnabled(true);
    return mapper;
  }

  // helper classes can go here
  class StringTrimConverter implements Converter<String, String> {

    @Override
    public String convert(MappingContext<String, String> context) {
      if (context.getSource() == null) {
        return null;
      }

      return context.getSource().trim();
    }
  }
}
