package eu.navispeed.editor.webui.controller;

import eu.navispeed.editor.webui.dto.Task;
import eu.navispeed.editor.webui.repository.ProjectRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class APIController {
  private final ProjectRepository projectRepository;

  public APIController(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @GetMapping("/{projectUuid}/tasks")
  public List<Task> getTasks(@PathVariable("projectUuid") UUID projectUuid) throws IOException {
    return projectRepository.get(projectUuid).execute().body().getTasks();
  }
}
