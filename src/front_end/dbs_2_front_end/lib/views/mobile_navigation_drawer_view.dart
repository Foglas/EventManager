import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../controller/app_controller.dart';
import '../utils/menu_item.dart';

class MobileNavigationDrawer extends GetView<AppController> {
  const MobileNavigationDrawer({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GetBuilder<AppController>(
        id: 'menu',
        builder: (AppController controller) {
          return NavigationDrawer(children: [

            DrawerHeader(
              decoration: BoxDecoration(
                color: Get.theme.colorScheme.primaryContainer,
              ),
              child: Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Text(controller.defaultPageTitle, style: Get.textTheme.titleLarge,),
                    Obx(() => controller.isUserLoggedIn.value ?
                    Text('You are logged in') : Text('You are not logged in'),
                    )
                  ],
                ),
              ),
            ),

            const Divider(),

            controller.isUserLoggedIn.value
                ? Column(
                    children: [
                      for (int i = 0;
                          i < controller.menuItemsForLoggedInUser.length;
                          i++)
                        Padding(
                          padding: const EdgeInsets.only(
                            left: 5,
                            right: 5,
                          ),
                          child: MobileNavigationDrawerItem(
                            item: controller.menuItemsForLoggedInUser[i],
                            selected: controller.selectedMenuItem.value ==
                                controller.menuItemsForLoggedInUser[i],
                          ),
                        )
                    ],
                  )
                : Column(
                    children: [
                      for (int i = 0;
                          i < controller.menuItemsForNotLoggedInUser.length;
                          i++)
                        Padding(
                          padding: const EdgeInsets.only(
                            left: 5,
                            right: 5,
                          ),
                          child: MobileNavigationDrawerItem(
                            item: controller.menuItemsForNotLoggedInUser[i],
                            selected: controller.selectedMenuItem.value ==
                                controller.menuItemsForNotLoggedInUser[i],
                          ),
                        ),
                    ],
                  )
          ],);
        },);
  }
}

class MobileNavigationDrawerItem extends GetView<AppController> {
  final MenuItem item;

  final bool selected;

  const MobileNavigationDrawerItem(
      {Key? key, required this.selected, required this.item})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    if (selected) {
      return Material(
        color: Get.theme.colorScheme.primaryContainer,
        child: ListTile(
          title: Text(
            item.getMenuItemDisplayName(),
            style: TextStyle(
              color: Get.theme.colorScheme.onPrimaryContainer,
            ),
          ),
          onTap: () {
            Navigator.of(context).pop();
            controller.handleMenuItemTapped(item);
          },
        ),
      );
    } else {
      return ListTile(
        title: Text(
          item.getMenuItemDisplayName(),
        ),
        onTap: () {
          Navigator.of(context).pop();
          controller.handleMenuItemTapped(item);
        },
      );
    }
  }
}
