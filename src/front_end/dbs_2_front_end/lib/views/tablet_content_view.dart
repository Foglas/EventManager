


import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../controller/app_controller.dart';

class TabletContentView extends GetView<AppController> {
  const TabletContentView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        ListView(
          children: [
            const SizedBox(height: 200),
            Padding(
              padding: const EdgeInsets.only(left: 10.0, right: 10),
              child: Obx(() => controller.selectedContent.value),
            ),
          ],
        ),
        Container(decoration: BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.topCenter,
            end: Alignment.bottomCenter,
            colors: [Get.theme.colorScheme.primaryContainer, Get.theme.colorScheme.background],
          ),
        ),
            height: 200, width: double.infinity),
      ],
    );
  }
}
