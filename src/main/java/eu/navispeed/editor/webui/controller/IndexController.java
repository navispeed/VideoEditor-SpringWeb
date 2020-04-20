package eu.navispeed.editor.webui.controller;

import eu.navispeed.editor.webui.controller.form.CreateProjectForm;
import eu.navispeed.editor.webui.dto.Project;
import eu.navispeed.editor.webui.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/")
@Slf4j
public class IndexController {
  private final ProjectRepository projectRepository;

  public IndexController(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @GetMapping
  public String index(Model model) {
    model.addAttribute("createProjectForm", new CreateProjectForm());
    return "index";
  }

  @PostMapping
  public ModelAndView createProject(@Valid CreateProjectForm form) {
    LOGGER.debug("Received form : {}", form);
    try {
      return Optional.ofNullable(projectRepository.createProject(form).execute().body())
          .map(Project::getUuid).map(uuid -> {
            try {
              projectRepository.download(uuid, new Object()).execute();
            } catch (IOException e) {
              LOGGER.error("Cannot start download", e);
            }
            return new ModelAndView("redirect:/project/" + uuid, HttpStatus.OK);
          }).orElse(new ModelAndView("redirect:/", Map.of("error", "Something happen, cannot create "
              + "project for URL: " + form.getUrl()),
              HttpStatus.BAD_REQUEST));
    } catch (IOException e) {
      return new ModelAndView("redirect:/", Map.of("error", "Something bad happen, cannot create "
          + "project for URL: " + form.getUrl()),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/todo")
  public ModelAndView todo() {
    return new ModelAndView("todo", HttpStatus.NOT_IMPLEMENTED);
  }
}
