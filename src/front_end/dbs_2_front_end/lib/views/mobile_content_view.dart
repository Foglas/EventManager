
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../controller/app_controller.dart';

class MobileContentView extends GetView<AppController> {
  const MobileContentView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(top: 12.0, bottom: 12),
      child: Obx(() => controller.selectedContent.value),
    );
  }
}
