
import 'package:dbs_2_front_end/views/mobile_content_view.dart';
import 'package:dbs_2_front_end/views/mobile_navigation_drawer_view.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../controller/app_controller.dart';
class MobileView extends GetView<AppController> {
  const MobileView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Get.theme.colorScheme.primaryContainer,
        foregroundColor: Get.theme.colorScheme.onPrimaryContainer,
        title: Obx(() => Text(
          controller.selectedMenuItem.value.getTitle(),
          style: Get.textTheme.titleMedium!.merge(TextStyle(color: Get.theme.colorScheme.onPrimaryContainer)),
        )),
      ),
      drawer: const MobileNavigationDrawer(),
      body: const SingleChildScrollView(child: MobileContentView()),

    );
  }
}
