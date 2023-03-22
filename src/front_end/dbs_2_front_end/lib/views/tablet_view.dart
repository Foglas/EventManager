
import 'package:dbs_2_front_end/views/tablet_content_view.dart';
import 'package:dbs_2_front_end/views/tablet_heading_view.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../controller/app_controller.dart';

class TabletView extends GetView<AppController> {
  const TabletView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: const [
          TabletContentView(),
          Padding(padding: EdgeInsets.only(
              left: 20, right: 20, top: 40, bottom: 10),
            child: TabletHeadingView(),
          ),
        ],
      ),

    );
  }
}


