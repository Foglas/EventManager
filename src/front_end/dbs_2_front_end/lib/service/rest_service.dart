import 'dart:convert';

import 'package:http/http.dart';

const serverUrl = 'localhost:8080';

class RestService {
  _get(String path, {Map<String, String>? headers}) async {
    try {
      final Uri url = Uri.http(serverUrl, path);

      print('hovno ${url.toString()}');
      final Response response = await get(
        url,
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          ...?headers,
        },
      );

      print('Response status : ${response.statusCode}');

      print('Response body : ${response.body}');

      print('header of response : ${response.headers}');

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

      print('hovno ${url.toString()}');
      final Response response = await post(url,
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            ...?headers,
          },
          body: body);

      print('Response status : ${response.statusCode}');

      print('Response body : ${response.body}');

      print('header of response : ${response.headers}');

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

  registerUser() async {
    await _post('api/auth/register', body: {
      "username": "matest",
      "email": "fake@mail.com",
      "password": "password"
    });

    print('///');

    final log = await _post('api/auth/login', body: {
      "username": "matest",
      "password": "password",
    });

    print('token:  ${log['jwt'].toString()}');

    await auth('false');
    print('///////');
    await auth(log['jwt']);
    return;
  }

  auth(String token) async {
    await _get('api/dummy', headers: {"Authorization": "Bearer $token"});
  }
}
