package io.nology.fullstackexample;

import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

@SpringBootTest
class ConfigTest {

  @Value("classpath:application-test.yml")
  private Resource configFile;

  @Test
  void testConfigFileExists() throws IOException {
    System.out.println(
      "Config file contents:\n" +
      Files.readString(configFile.getFile().toPath())
    );
  }
}
