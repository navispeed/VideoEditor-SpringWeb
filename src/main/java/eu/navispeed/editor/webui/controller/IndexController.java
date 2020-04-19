package eu.navispeed.editor.webui.controller;

import eu.navispeed.editor.webui.controller.form.CreateProjectForm;
import eu.navispeed.editor.webui.dto.Project;
import eu.navispeed.editor.webui.repository.ProjectRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/")
@Slf4j
public class IndexController {
  private final ProjectRepository projectRepository;

  public IndexController(
      ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @GetMapping
  public String index(Model model) {
    model.addAttribute("createProjectForm", new CreateProjectForm());
    return "index";
  }

  @SneakyThrows
  @PostMapping
  public ModelAndView createProject(@Valid CreateProjectForm form, ModelAndView modelAndView) {
    LOGGER.debug("Received form : {}", form);
    return Optional.ofNullable(projectRepository.createProject(form).execute().body())
        .map(Project::getUuid).map(uuid -> new ModelAndView("redirect:/project/" + uuid,
            HttpStatus.OK))
        .orElse(new ModelAndView("redirect:/", Map.of("error", "Something happen"),
            HttpStatus.BAD_REQUEST));
  }

  @GetMapping("/todo")
  public ModelAndView todo() {
    return new ModelAndView("todo", HttpStatus.NOT_IMPLEMENTED);
  }
}
