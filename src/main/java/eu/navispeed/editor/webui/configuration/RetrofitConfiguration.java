package eu.navispeed.editor.webui.configuration;

import eu.navispeed.editor.webui.repository.ProjectRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;

@Configuration
public class RetrofitConfiguration {

  @Bean
  public ProjectRepository projectRepository(Retrofit retrofit) {
    return retrofit.create(ProjectRepository.class);
  }

}
