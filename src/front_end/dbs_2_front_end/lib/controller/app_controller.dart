import 'package:dbs_2_front_end/service/rest_service.dart';
import 'package:dbs_2_front_end/utils/menu_item.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:get/get.dart';
import 'package:responsive_builder/responsive_builder.dart';

import '../pages/dashboard_page.dart';

const jwtKey = 'jwt_auth_token_key';

/// The controller for the entire application.
class AppController extends GetxController {
  static AppController get to => Get.find();

  /// Information about the sizing of the device screen.
  SizingInformation? sizingInformation;

  /// The default title for all pages.
  String defaultPageTitle = "Hello FIM";

  /// Indicates whether to display a circular progress indicator.
  RxBool displayCircularProgressIndicator = false.obs;

  /// Indicates whether the user is logged in.
  RxBool isUserLoggedIn = false.obs;

  late String jwtToken;

  final FlutterSecureStorage storage = const FlutterSecureStorage();

  /// The currently selected menu item.
  Rx<MenuItem> selectedMenuItem = Rx(MenuItem.events);

  /// The content widget that corresponds to the selected menu item.
  Rx<Widget> selectedContent = Rx(const DashboardPage());

  @override
  onInit() async {
    super.onInit();

    jwtToken = await storage.read(key: jwtKey) ?? '';

    isUserLoggedIn.value = await RestService.to.authToken(token: jwtToken);
  }

  /// The menu items that are available to logged in users.
  List<MenuItem> menuItemsForLoggedInUser = [
    MenuItem.events,
    MenuItem.profile,
    MenuItem.about,
  ];

  /// The menu items that are available to not logged in users.
  List<MenuItem> menuItemsForNotLoggedInUser = [
    MenuItem.events,
    MenuItem.login,
    MenuItem.about
  ];

  /// Handles a tap on a menu item.
  void handleMenuItemTapped(MenuItem item) {
    selectedMenuItem.value = item;
    selectedContent.value = item.getPage();
    update(['menu']);
  }

  /// Returns the type of the device screen.
  DeviceScreenType getDeviceScreenType() {
    return sizingInformation != null
        ? sizingInformation!.deviceScreenType
        : DeviceScreenType.desktop;
  }

  Future<void> storeUserJwtToken(String jwtToken) async {
    isUserLoggedIn.value = true;
    this.jwtToken = jwtToken;

    await storage.write(key: jwtKey, value: jwtToken);
  }
}
