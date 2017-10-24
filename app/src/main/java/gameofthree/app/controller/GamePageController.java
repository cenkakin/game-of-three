package gameofthree.app.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cenk
 */
@RestController
public class GamePageController {

  @GetMapping(path = "/", produces = MediaType.TEXT_HTML_VALUE)
  public Resource index() {
    return new ClassPathResource("static/index.html");
  }
}
