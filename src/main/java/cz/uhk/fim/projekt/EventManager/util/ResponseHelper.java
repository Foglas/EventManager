package cz.uhk.fim.projekt.EventManager.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;

/**
 * Pomocná třída pro generování hlášek
 */
public class ResponseHelper {

  /**
   * Error message generátor
   * @param errorType Typ erroru, která má být ve zprávě zabalena
   * @param errorMessage Zpráva o erroru, která má být ve zprávě zabalena
   * @return ResponseEntity<>
   */
  public static ResponseEntity<Object> errorMessage(
    String errorType,
    String errorMessage
  ) {
    Map<String, String> body = new HashMap<>();
    body.put("errorType", errorType);
    body.put("message", errorMessage);

    return ResponseEntity.badRequest().body(body);
  }

  /**
   * Success message generátor
   * @param message Zpráva, která má být ve zprávě zabalena
   * @return ResponseEntity<>
   */
  public static ResponseEntity<Object> successMessage(String message) {
    Map<String, String> body = new HashMap<>();
    body.put("message", message);
    return ResponseEntity.ok(body);
  }
}
