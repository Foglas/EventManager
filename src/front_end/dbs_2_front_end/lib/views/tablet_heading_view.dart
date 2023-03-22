import 'package:dbs_2_front_end/views/app_view.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../controller/app_controller.dart';
import '../utils/menu_item.dart';

class TabletHeadingView extends GetView<AppController> {
  const TabletHeadingView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: 100,
      width: double.infinity,
      child: Row(
        children: [
          Center(
            child:
                  Text(
                    controller.defaultPageTitle,
                    style: Get.textTheme.headlineLarge!.merge(TextStyle(
                        color: Get.theme.colorScheme.onPrimaryContainer,
                        letterSpacing: 2),),

            ),
          ),
          const Spacer(),
          const TabletMenu(),
        ],
      ),
    );
  }
}

class TabletMenu extends StatelessWidget {
  const TabletMenu({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GetBuilder<AppController>(
      id: 'menu',
      builder: (AppController controller) {
        return Obx(
            () => SingleChildScrollView(
              scrollDirection: Axis.horizontal,
              child: Row(
              children: controller.isUserLoggedIn.value ?
              [for (int i = 0; i < controller.menuItemsForLoggedInUser.length; i++)
                Padding(
                  padding: const EdgeInsets.only(
                    left: 5,
                    right:5,
                  ),
                  child:
                  TabletMenuItemWidget(item: controller.menuItemsForLoggedInUser[i], selected:
                  controller.selectedMenuItem.value == controller.menuItemsForLoggedInUser[i],
                  ),
                )]
                  :
              [for (int i = 0; i < controller.menuItemsForNotLoggedInUser.length; i++)
                Padding(
                  padding: const EdgeInsets.only(
                    left: 5,
                    right: 5,
                  ),
                  child:
                  TabletMenuItemWidget(item: controller.menuItemsForNotLoggedInUser[i], selected:
                  controller.selectedMenuItem.value == controller.menuItemsForNotLoggedInUser[i],
                  ),
                ),
              ],
          ),
            ),
        );
      },
    );
  }
}


class TabletMenuItemWidget extends StatefulWidget {
  final MenuItem item;
  final bool selected;

  const TabletMenuItemWidget({Key? key, required this.item, required this.selected}) : super(key: key);

  @override
  State<TabletMenuItemWidget> createState() => _TabletMenuItemWidgetState();
}

class _TabletMenuItemWidgetState extends State<TabletMenuItemWidget> {
  bool hover = false;

  @override
  Widget build(BuildContext context) {
    return InkWell(
      highlightColor: Colors.transparent,
      focusColor: Colors.transparent,
      splashColor: Colors.transparent,
      hoverColor: Colors.transparent,
      onTap: () {
        Get.find<AppController>().handleMenuItemTapped(widget.item);
      },
      onHover: (bool hover) => setState(() => this.hover = hover),
      child: AnimatedContainer(
        width:  120,
        height: 80,
        padding: hover ? const EdgeInsets.all(4) : const EdgeInsets.all(12),
        duration: const Duration(milliseconds: 500),
        child: Center(
          child: widget.selected
              ? Text(
            widget.item.getMenuItemDisplayName(),
            style: Get.textTheme.titleMedium!.merge(
              const TextStyle(
                  fontWeight: FontWeight.w600,
                  decoration: TextDecoration.underline),
            ),
          )
              : hover
              ? Text(
            widget.item.getMenuItemDisplayName(),
            style: Get.textTheme.titleMedium!.merge(
              const TextStyle(decoration: TextDecoration.underline),
            ),
          )
              : Text(
            widget.item.getMenuItemDisplayName(),
            style: Get.textTheme.titleMedium,
          ),
        ),
      ),
    );
  }
}
