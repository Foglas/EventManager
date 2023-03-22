import 'dart:convert';

import 'package:get/get.dart';
import 'package:http/http.dart';

const String apiUrl = "https://localhost:8080/";

class RestService extends GetxService {
  _get(String path, {Map<String, String>? headers}) async {
    final url = Uri.parse('http://localhost:8080/hello');

    get(url).then((response) {
      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        print(data);
      } else {
        print('Request failed with status: ${response.statusCode}.');
      }
    }).catchError((error) {
      print('Request failed with error: $error.');
    });
  }

  _post(String path, {Map<String, String>? headers, dynamic body}) async {
    try {
      final Uri url = Uri.parse("$apiUrl$path");
      // final Response response = await post(url, headers: headers, body: body);
      // final responseBody = jsonDecode(utf8.decode(response.bodyBytes));

      // return {
      //   'responseStatusCode': response.statusCode,
      //   'responseBody': responseBody,
      // };
    } catch (err) {
      return err;
    }
  }

  tryLogin() async => await _tryLogin();

  _tryLogin() async {
    var response =
        await post(Uri.parse('http://localhost:8080/api/auth/login'));
    if (response.statusCode == 200) {
      print(response.body);
      print(response.statusCode);
    } else {
      print(response.body);
      print(response.statusCode);
    }
  }
}
