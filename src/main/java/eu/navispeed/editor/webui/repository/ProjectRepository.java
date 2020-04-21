package eu.navispeed.editor.webui.repository;

import eu.navispeed.editor.webui.controller.form.CreateProjectForm;
import eu.navispeed.editor.webui.dto.Project;
import eu.navispeed.editor.webui.dto.Task;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository {
  @POST("/project")
  Call<Project> createProject(@Body CreateProjectForm url);

  @GET("/project/")
  Call<List<Project>> all();

  @GET("/project/{id}")
  Call<Project> get(@Path("id") UUID uuid);

  @GET("/project/{id}/tasks")
  Call<List<Task>> tasksOfProject(@Path("id") UUID uuid);

  @POST("/project/{id}/download")
  Call<Task> download(@Path("id") UUID uuid, @Body Object emptyObject);
}
