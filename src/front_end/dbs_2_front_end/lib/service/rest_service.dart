import 'dart:convert';

import 'package:http/http.dart';

const String apiUrl = "https://localhost:8080/";

RestService restService = RestService();

class RestService {

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
      final Response response = await post(url, headers: headers, body: body);
      final responseBody = jsonDecode(utf8.decode(response.bodyBytes));

      return {
        'responseStatusCode': response.statusCode,
        'responseBody': responseBody,
      };
    } catch (err) {
      return err;
    }
  }

  getHelloMessage() async => await _get('hello');

  Future<String> getHello() async {
    var response = await get(Uri.parse('http://localhost:8080/hello'));
    if (response.statusCode == 200) {
      return response.body;
    } else {
      throw Exception('Failed to load greeting');
    }
  }
}
