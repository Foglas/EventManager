import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:http/http.dart' as http;

const serverUrl = 'localhost:8080';

class RestService extends GetxService {
  static RestService get to => Get.find();

  Future<ServerResponse> _get(String path,
      {Map<String, String>? headers}) async {
    try {
      final Uri url = Uri.http(serverUrl, path);

      debugPrint('Creating get request to URI : ${url.toString()}');

      final http.Response response = await http.get(
        url,
        headers: {
          'Content-Type': 'application/json',
          ...?headers,
        },
      );

      debugPrint('Response status : ${response.statusCode}');

      debugPrint('Response body : ${response.body}');

      debugPrint('header of response : ${response.headers}');

      return ServerResponse(
          body: jsonDecode(response.body), statusCode: response.statusCode);
    } catch (err) {
      return ServerResponse(body: {"message": err.toString()}, statusCode: 500);
    }
  }

  Future<ServerResponse> _post(String path,
      {Map<String, String>? headers, dynamic body}) async {
    try {
      final Uri url = Uri.http(serverUrl, path);

      debugPrint('Creating post request to URI :  ${url.toString()}');

      final http.Response response = await http.post(url,
          headers: {
            'Content-Type': 'application/json',
            ...?headers,
          },
          body: jsonEncode(body));

      debugPrint('Response status : ${response.statusCode}');

      debugPrint('Response body : ${response.body}');

      debugPrint('header of response : ${response.headers}');

      return ServerResponse(
          body: jsonDecode(response.body), statusCode: response.statusCode);
    } catch (err) {
      debugPrint(err.toString());
      return ServerResponse(body: {"message": err.toString()}, statusCode: 500);
    }
  }

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
}

class ServerResponse {
  final int statusCode;
  final Map<String, dynamic> body;

  ServerResponse({required this.statusCode, required this.body});
}
