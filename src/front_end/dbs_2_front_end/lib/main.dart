import 'dart:io';
import 'package:dbs_2_front_end/utils/app_typography.dart';
import 'package:dbs_2_front_end/utils/color_schemes.dart';
import 'package:dbs_2_front_end/utils/my_http_overrides.dart';
import 'package:dbs_2_front_end/views/app_view.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';


import 'package:flutter_web_plugins/url_strategy.dart';

import 'controller/app_controller.dart';
import 'utils/firebase_options.dart';

main() async {
  WidgetsFlutterBinding.ensureInitialized();
  HttpOverrides.global = MyHttpOverrides();

  if (kIsWeb) {
    usePathUrlStrategy();
  }

  Get.put(AppController(), permanent: true);

  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );

  runApp(const App());
}

class App extends StatelessWidget {
  const App({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      title: 'DBS project',
      theme: ThemeData(
          useMaterial3: true,
          colorScheme: lightColorScheme,
          textTheme: appMaterialLightPrimaryTextTheme),
      darkTheme: ThemeData(
          useMaterial3: true,
          colorScheme: darkColorScheme,
          textTheme: appMateriaDarkTextTheme),
      home: const HomePage(),
    );
  }
}
