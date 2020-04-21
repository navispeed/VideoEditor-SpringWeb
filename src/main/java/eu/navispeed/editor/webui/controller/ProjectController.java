package eu.navispeed.editor.webui.controller;

import eu.navispeed.editor.webui.dto.Project;
import eu.navispeed.editor.webui.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import retrofit2.Response;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.UUID;

@Controller
@RequestMapping("/project")
@Slf4j
public class ProjectController {
  private final ProjectRepository projectRepository;


  public ProjectController(ProjectRepository projectRepository) throws UnknownHostException {
    this.projectRepository = projectRepository;
  }

  @GetMapping("/{id}")
  public String detail(Model model, @PathVariable("id") UUID id) throws IOException {
    Response<Project> execute = projectRepository.get(id).execute();
    Project project = execute.body();
    if (execute.raw().code() == 200 && project != null) {
      model.addAttribute("project", project);
      model.addAttribute("actionUrl", String.format("/project/%s/action", id));
      model.addAttribute("embedPlayer",
          "http://www.youtube.com/embed/" + project.getUrl().getUrl().split("=")[1] +
              "?autoplay=0&origin=https://downloader.navispeed.eu/");
      return "project";
    }
    return "redirect:/";
  }

  @GetMapping("/{id}/action")
  public String actionForm(Model model, @PathVariable("id") UUID projectUuid)
      throws IOException {
    Response<Project> execute = projectRepository.get(projectUuid).execute();
    int code = execute.raw().code();
    if (!execute.raw().isSuccessful()) {
      return "redirect:/" + code;
    }
    model.addAttribute("project", execute.body());
    return "action";
  }
}
