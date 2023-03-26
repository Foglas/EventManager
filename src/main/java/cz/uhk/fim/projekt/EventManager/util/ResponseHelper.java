package cz.uhk.fim.projekt.EventManager.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;

public class ResponseHelper {

  public static ResponseEntity<Object> errorMessage(
    String errorType,
    String errorMessage
  ) {
    Map<String, String> body = new HashMap<>();
    body.put("errorType", errorType);
    body.put("message", errorMessage);

    return ResponseEntity.badRequest().body(body);
  }

  public static ResponseEntity<Object> successMessage(String message) {
    Map<String, String> body = new HashMap<>();
    body.put("message", message);
    return ResponseEntity.ok(body);
  }
}
