import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:http/http.dart' as http;

const serverUrl = 'localhost:8080';

class RestService extends GetxService {
  static RestService get to => Get.find();

  _get(String path, {Map<String, String>? headers}) async {
    try {
      final Uri url = Uri.http(serverUrl, path);

      debugPrint('Creating get request to URI : ${url.toString()}');

      final http.Response response = await http.get(
        url,
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          ...?headers,
        },
      );

      debugPrint('Response status : ${response.statusCode}');

      debugPrint('Response body : ${response.body}');

      debugPrint('header of response : ${response.headers}');

      return {
        'responseStatusCode': response.statusCode,
        'responseBody': response.body,
        'requestHeaders': headers,
      };
    } catch (err) {
      return {
        'responseStatusCode': 500,
        'responseBody': err,
        'requestHeaders': headers,
      };
    }
  }

  _post(String path, {Map<String, String>? headers, dynamic body}) async {
    try {
      final Uri url = Uri.http(serverUrl, path);

      debugPrint('Creating post request to URI :  ${url.toString()}');

      final http.Response response = await http.post(url,
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            ...?headers,
          },
          body: body);

      debugPrint('Response status : ${response.statusCode}');

      debugPrint('Response body : ${response.body}');

      debugPrint('header of response : ${response.headers}');

      return jsonDecode(response.body);
    } catch (err) {
      return {
        'requestBody': body,
        'responseStatusCode': 500,
        'responseBody': err,
        'requestHeaders': headers,
      };
    }
  }

  Future<void> loginUser({
    required String username,
    required String password,
  }) async {
    Map<String, String> requestBody = {
      "email": username,
      "password": password,
    };

    await _post('api/login', body: requestBody);
  }

  Future<void> registerUser({
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
      "dateOfBirth": dateOfBirth,
      "name": name,
      "surname": surname,
      "phone": phone
    };

    debugPrint(requestBody.toString());

    await _post('api/register', body: requestBody);
  }
}
