import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:responsive_builder/responsive_builder.dart';

import '../controller/app_controller.dart';
import '../controller/dashboard_controller.dart';
import 'login_page.dart';

class DashboardPage extends GetView<DashboardController> {
  const DashboardPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final controller = Get.put(DashboardController());
    return Center(
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: SizedBox(
          width:
              AppController.to.getDeviceScreenType() != DeviceScreenType.mobile
                  ? 600
                  : double.infinity,
          child: Column(
            children: [
              /// dashboard list view;
              Obx(
                () => controller.isSearch.value
                    ? Obx(
                        () => Column(
                          children: [
                            InputLine(
                                textValue: 'Search',
                                controller: controller.searchTextController,
                                toggleActionEnabled: true,
                                toggleAction: () {
                                  controller.handleSearch();
                                }),
                            Row(
                              children: [
                                Text(
                                  'List of actions',
                                  style: Get.textTheme.titleMedium,
                                ),
                                const Spacer(),
                                const DashboardPlusIconButton(),
                              ],
                            ),
                            Column(
                              children: [
                                for (int i = 0;
                                    i < controller.length.value;
                                    i++)
                                  Column(
                                    children: [
                                      EventListTile(i),
                                      const Divider(),
                                    ],
                                  )
                              ],
                            ),
                          ],
                        ),
                      )
                    : const SizedBox.shrink(),
              ),

              /// dashboard list view;
              Obx(
                () => controller.isDetail.value
                    ? Column(
                        children: [
                          Center(
                            child: Obx(
                              () => Text(
                                "${(controller.detailPageIndex.value + 1).toString()} Detail screen",
                                style: Get.textTheme.titleMedium,
                              ),
                            ),
                          ),
                          const Divider(),

                          Text(controller.lipsum),

                          /// Submit button
                          Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: Center(
                              child: SizedBox(
                                width: 300,
                                height: 50,
                                child: ElevatedButton(
                                  onPressed: () {
                                    controller.handleSwitchToSearch();
                                  },
                                  child: const Text('Go back'),
                                ),
                              ),
                            ),
                          ),
                        ],
                      )
                    : const SizedBox.shrink(),
              ),

              Obx(
                () => controller.isCreate.value
                    ? Column(
                        children: [
                          InputLine(
                            textValue: 'Name',
                            controller: controller.nameTextController,
                          ),
                          InputLine(
                            textValue: 'description',
                            controller: controller.descriptionTextController,
                          ),
                          InputLine(
                            textValue: 'Start date',
                            controller: controller.startDateTextController,
                            disableEdit: true,
                            onTap: () => {
                              controller.handleSelectStartDate(),
                            },
                          ),
                          InputLine(
                            textValue: 'End date',
                            controller: controller.endDateTextController,
                            disableEdit: true,
                            onTap: () => {
                              controller.handleSelectEndDate(),
                            },
                          ),
                          const DropdownLine(
                            textValue: 'category',
                            disableEdit: false,
                          ),

                          /// Submit button
                          Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: Center(
                              child: SizedBox(
                                width: 300,
                                height: 50,
                                child: ElevatedButton(
                                  onPressed: () {
                                    controller.handleAddNewAction();
                                  },
                                  child: const Text('Add event to list'),
                                ),
                              ),
                            ),
                          ),

                          Obx(
                            () => controller.showEndDateValidationMessage.value
                                ? const Center(
                                    child: Text(
                                        "Your end date was changed because start day must be before your end date"),
                                  )
                                : const SizedBox.shrink(),
                          ),
                        ],
                      )
                    : const SizedBox.shrink(),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class EventListTile extends GetView<DashboardController> {
  final int i;

  const EventListTile(this.i, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListTile(
      title: Text('${(i + 1).toString()} Event'),
      subtitle: const Text('interesting event'),
      leading: const Icon(Icons.event),
      trailing: const Icon(Icons.arrow_right_sharp),
      onTap: () {
        controller.handleSwitchToDetail(i);
      },
    );
  }
}

class DashboardPlusIconButton extends StatelessWidget {
  const DashboardPlusIconButton({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    if (!Get.isRegistered<DashboardController>())
      return const SizedBox.shrink();

    final controller = Get.find<DashboardController>();

    return Align(
      alignment: Alignment.topRight,
      child: Obx(
        () => controller.isSearch.value
            ? IconButton(
                icon: const Icon(
                  Icons.add_circle_rounded,
                  size: 25,
                ),
                onPressed: () {
                  controller.handleSwitchToCreate();
                },
              )
            : const SizedBox.shrink(),
      ),
    );
  }
}
