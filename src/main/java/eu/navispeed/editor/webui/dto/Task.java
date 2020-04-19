package eu.navispeed.editor.webui.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class Task {
  public enum Type {
    DOWNLOAD,
    EXTRACTION,
  }

  public enum State {
    TODO,
    RUNNING,
    DONE,
    DONE_WITH_ERROR
  }

  State state;
  Output output;
  Type type;
  String message;
  Integer progress;
}
