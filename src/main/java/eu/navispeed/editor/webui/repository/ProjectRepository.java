package eu.navispeed.editor.webui.repository;

import eu.navispeed.editor.webui.controller.form.CreateProjectForm;
import eu.navispeed.editor.webui.dto.Project;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.UUID;

public interface ProjectRepository {
  @POST("/project")
  Call<Project> createProject(@Body CreateProjectForm url);

  @GET("/project/{id}")
  Call<Project> get(@Path("id") UUID uuid);
}
