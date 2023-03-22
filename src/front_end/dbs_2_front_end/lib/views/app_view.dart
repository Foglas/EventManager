import 'package:dbs_2_front_end/controller/app_controller.dart';
import 'package:dbs_2_front_end/utils/obx_only.dart';
import 'package:dbs_2_front_end/views/tablet_view.dart';
import 'package:dbs_2_front_end/views/web_view.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:get/get.dart';
import 'package:responsive_builder/responsive_builder.dart';

import 'mobile_view.dart';

class HomePage extends StatelessWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GetBuilder<AppController>(
      builder: (AppController controller) {
        return Stack(
          children: [
            SelectionArea(
              child: ResponsiveBuilder(builder: (context, sizingInformation) {
                controller.sizingInformation = sizingInformation;

                if (sizingInformation.deviceScreenType ==
                    DeviceScreenType.desktop) {
                  return const WebView();
                }
                if (sizingInformation.deviceScreenType ==
                    DeviceScreenType.tablet) {
                  return const TabletView();
                }
                if (sizingInformation.deviceScreenType ==
                    DeviceScreenType.mobile) {
                  return const MobileView();
                }

                return const TabletView();
              }),
            ),
            ObxOnly(
              only: controller.displayCircularProgressIndicator,
              child: const SizedBox.expand(
                  child: Center(child: CircularProgressIndicator())),
            ),
          ],
        );
      },
    );
  }
}
