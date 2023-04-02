import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:http/http.dart' as http;

import '../controller/app_controller.dart';

const serverUrl = 'localhost:8080';

class RestService extends GetxService {
  static RestService get to => Get.find();

  /// Sends a GET request to the specified path with optional headers.
  ///
  /// Returns a [ServerResponse] object containing the response status code and body.
  Future<ServerResponse> _get(String path,
      {Map<String, String>? headers}) async {
    try {
      final Uri url = Uri.http(serverUrl, path);

      debugPrint('Creating GET request to URI: ${url.toString()}');

      final http.Response response = await http.get(
        url,
        headers: {
          'Content-Type': 'application/json',
          ...?headers,
        },
      );

      debugPrint('Response status: ${response.statusCode}');

      debugPrint('Response body: ${response.body}');

      debugPrint('Headers of response: ${response.headers}');

      return ServerResponse(
          body: jsonDecode(response.body), statusCode: response.statusCode);
    } catch (err) {
      return ServerResponse(body: {"message": err.toString()}, statusCode: 500);
    }
  }

  /// Sends a POST request to the specified path with optional headers and body.
  ///
  /// Returns a [ServerResponse] object containing the response status code and body.
  Future<ServerResponse> _post(String path,
      {Map<String, String>? headers, dynamic body}) async {
    try {
      final Uri url = Uri.http(serverUrl, path);

      debugPrint('Creating POST request to URI: ${url.toString()}');

      final http.Response response = await http.post(url,
          headers: {
            'Content-Type': 'application/json',
            ...?headers,
          },
          body: jsonEncode(body));

      debugPrint('Response status: ${response.statusCode}');

      debugPrint('Response body: ${response.body}');

      debugPrint('Headers of response: ${response.headers}');

      return ServerResponse(
          body: jsonDecode(response.body), statusCode: response.statusCode);
    } catch (err) {
      debugPrint(err.toString());
      return ServerResponse(body: {"message": err.toString()}, statusCode: 500);
    }
  }

  /// Sends a POST request to login the user with the specified email and password.
  ///
  /// Returns a [ServerResponse] object containing the response status code and body.
  Future<ServerResponse> loginUser({
    required String email,
    required String password,
  }) async {
    Map<String, String> requestBody = {
      "email": email,
      "password": password,
    };

    return await _post('api/user/login', body: requestBody);
  }

  /// Sends a POST request to register the user with the specified details.
  ///
  /// Returns a [ServerResponse] object containing the response status code and body.
  Future<ServerResponse> registerUser({
    required String username,
    required String email,
    required String password,
    required String passwordAgain,
    required String name,
    required String surname,
    required String phone,
    required String dateOfBirth,
  }) async {
    Map<String, dynamic> requestBody = {
      "email": email,
      "username": username,
      "password": password,
      "passwordAgain": passwordAgain,
      "userDetails": {
        "dateOfBirth": dateOfBirth,
        "name": name,
        "surname": surname,
        "phone": phone
      }
    };

    return await _post('api/user/register', body: requestBody);
  }

  /// Retrieves an authentication token using the provided [token] string.
  /// Returns `true` if the authentication token was successfully retrieved,
  /// `false` otherwise.
  Future<bool> authToken({required String token}) async {
    if (token.isEmpty) {
      return false;
    }

    final request = await _get('api/auth/test', headers: _authHeader());

    return request.statusCode == 200;
  }

  /// Retrieves the current user's data using the authorized credentials.
  Future<ServerResponse> getCurrentUser() async {
    return await _get('api/auth/currentUser', headers: _authHeader());
  }

  /// Returns a map containing the authorization header to be used in
  /// HTTP requests.
  Map<String, String> _authHeader() {
    return {"Authorization": "Bearer ${AppController.to.jwtToken}"};
  }
}

/// Represents a response from the server, containing a status code and response body.
class ServerResponse {
  final int statusCode;
  final Map<String, dynamic> body;

  ServerResponse({required this.statusCode, required this.body});
}
