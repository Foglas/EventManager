import 'package:dbs_2_front_end/service/rest_service.dart';
import 'package:dbs_2_front_end/utils/menu_item.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:responsive_builder/responsive_builder.dart';

import '../pages/dashboard_page.dart';

class AppController extends GetxController {
  static AppController get to => Get.find();

  SizingInformation? sizingInformation;

  String defaultPageTitle = "Hello FIM";

  RxBool displayCircularProgressIndicator = false.obs;
  RxBool isUserLoggedIn = false.obs;

  Rx<MenuItem> selectedMenuItem = Rx(MenuItem.events);
  Rx<Widget> selectedContent = Rx(const DashboardPage());

  List<MenuItem> menuItemsForLoggedInUser = [
    MenuItem.events,
    MenuItem.profile,
    MenuItem.about,
  ];

  List<MenuItem> menuItemsForNotLoggedInUser = [
    MenuItem.events,
    MenuItem.login,
    MenuItem.about
  ];

  @override
  onInit() async {
    super.onInit();

    final restService = Get.put(RestService());

    final test = await restService.tryLogin();

    print(test);
  }

  handleMenuItemTapped(MenuItem item) {
    selectedMenuItem.value = item;
    selectedContent.value = item.getPage();
    update(['menu']);
  }

  DeviceScreenType getDeviceScreenType() {
    return sizingInformation != null
        ? sizingInformation!.deviceScreenType
        : DeviceScreenType.desktop;
  }
}
