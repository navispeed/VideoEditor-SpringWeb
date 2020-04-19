package eu.navispeed.editor.webui.controller.form;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Data
@ToString
public class CreateProjectForm {
  @Pattern(regexp = "(www|http:|https:)+[^\\s]+[\\w]")
  String url;
}
