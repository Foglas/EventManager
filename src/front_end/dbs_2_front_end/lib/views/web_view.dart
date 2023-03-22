
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../controller/app_controller.dart';
import 'web_content_view.dart';
import 'web_heading_view.dart';

class WebView extends GetView<AppController> {
  const WebView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: const [
          WebContentView(),
          Padding(padding: EdgeInsets.only(
              left: 80, right: 80, top: 80, bottom: 10),
            child: WebHeadingView(),
          ),
        ],
      ),

    );
  }
}

class BackgroundGradient extends StatelessWidget {
  const BackgroundGradient({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SizedBox.expand(
      child: Column(
        children: [
          Expanded(flex: 1, child: Container(
            decoration: BoxDecoration(
              gradient: LinearGradient(
                begin: Alignment.topCenter,
                end: Alignment.bottomCenter,
                colors: [Get.theme.colorScheme.primaryContainer, Colors.transparent],
              )
            ),
          ),),
          Expanded(flex: 3, child: Container(),),
        ],
      ),
    );
  }
}

