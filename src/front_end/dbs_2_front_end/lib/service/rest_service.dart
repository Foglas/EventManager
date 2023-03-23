import 'dart:convert';

import 'package:http/http.dart';

const serverUrl = 'localhost:8080';

class RestService {
  _get(String path, {Map<String, String>? headers}) async {
    try {
      final Uri url = Uri.http(serverUrl, path);
      final Response response = await get(url, headers: headers);
      final responseBody = jsonDecode(utf8.decode(response.bodyBytes));

      print('Response status : ${response.statusCode}');

      print('Response body : ${response.bodyBytes}');

      return {
        'responseStatusCode': response.statusCode,
        'responseBody': responseBody,
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
      print(url.toString());
      final Response response = await post(url,
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            ...?headers,
          },
          body: body);
      final responseBody = response.body;

      print('Response status : ${response.statusCode}');

      print('Response body : ${response.body}');

      return {
        'requestBody': body,
        'responseStatusCode': response.statusCode,
        'responseBody': responseBody,
        'requestHeaders': headers,
      };
    } catch (err) {
      return {
        'requestBody': body,
        'responseStatusCode': 500,
        'responseBody': err,
        'requestHeaders': headers,
      };
    }
  }

  registerUser() async {
    await _post('api/auth/register', body: {
      "username": "matest",
      "email": "fake@mail.com",
      "password": "password"
    });

    print('///');

    await _post('api/auth/login', body: {
      "email": "fake@mail.com",
      "password": "password",
    });
  }
}
