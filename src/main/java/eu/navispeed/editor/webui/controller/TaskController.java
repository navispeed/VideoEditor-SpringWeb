package eu.navispeed.editor.webui.controller;

import eu.navispeed.editor.webui.dto.Task;
import eu.navispeed.editor.webui.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/task")
public class TaskController {
  private final ProjectRepository projectRepository;
  private final String baseUrl;

  public TaskController(ProjectRepository projectRepository,
      @Value("${service.retrofit.baseUrl}") String baseUrl) {
    this.projectRepository = projectRepository;
    this.baseUrl = baseUrl;
  }

  @GetMapping("/{id}/stream")
  public ModelAndView stream(@PathVariable("id") UUID id) throws IOException {
    Optional<Task> any =
        projectRepository.all().execute().body().stream().flatMap(p -> p.getTasks().stream())
            .filter(t -> t.getId().equals(id)).findAny();
    return any.map(
        t -> new ModelAndView("stream", Map.of("url",
            baseUrl + "output/" + t.getOutput().getId() + "/stream")))
        .orElseGet(() -> new ModelAndView("redirect:/", Map.of("error", "Cannot stream video")));
  }
}
