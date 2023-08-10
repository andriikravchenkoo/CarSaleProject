package com.andriikravchenkoo.carsaleproject.configuration;

import com.andriikravchenkoo.carsaleproject.exception.ImageConvertException;
import com.andriikravchenkoo.carsaleproject.service.ImageService;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
@RequiredArgsConstructor
public class AfterInitContextConfig {

  private final ImageService imageService;

  @EventListener
  public void initImageOnStartup(ContextRefreshedEvent event) {
    if (imageService.checkCountImage()) {
      try {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources =
            Arrays.asList(resolver.getResources("classpath*:static/images/init/*"));
        List<MultipartFile> files =
            resources.stream()
                .map(
                    file -> {
                      try {
                        return new MockMultipartFile(
                            "file", file.getFilename(), "image/jpeg", file.getInputStream());
                      } catch (IOException e) {
                        throw new ImageConvertException(file.getFilename());
                      }
                    })
                .collect(Collectors.toList());
        imageService.saveAll(files);
      } catch (IOException exception) {
        log.error("An unexpected error occurred while initializing images resources");
      } catch (ImageConvertException exception) {
        log.error(
            "An unexpected error occurred while initializing image = {}", exception.getMessage());
      }
    }
  }
}
