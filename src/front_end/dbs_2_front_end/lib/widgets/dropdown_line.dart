import 'package:dropdown_button2/dropdown_button2.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:responsive_builder/responsive_builder.dart';

import '../controller/app_controller.dart';

class DropdownLine extends StatelessWidget {
  final String textValue;
  final GestureTapCallback? onTap;
  final bool disableEdit;
  final bool isError;

  const DropdownLine({
    Key? key,
    required this.textValue,
    this.onTap,
    required this.disableEdit,
    this.isError = false,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final labelStyle = Get.textTheme.bodyMedium?.copyWith(
      color: isError ? Colors.red : null,
    );

    final List<String> stringCategoryOptions = ['concert', 'party', 'trip'];

    return Padding(
      padding: const EdgeInsets.only(top: 8.0, bottom: 8.0),
      child: GestureDetector(
        onTap: onTap,
        child: SizedBox(
          width:
              AppController.to.getDeviceScreenType() == DeviceScreenType.mobile
                  ? 300
                  : 600,
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Visibility(
                visible: AppController.to.getDeviceScreenType() !=
                    DeviceScreenType.mobile,
                child: SizedBox(
                  width: 100,
                  child: Text(
                    '$textValue :',
                    style: Get.textTheme.titleMedium?.copyWith(
                      color: isError ? Colors.red : null,
                    ),
                  ),
                ),
              ),
              Visibility(
                visible: AppController.to.getDeviceScreenType() !=
                    DeviceScreenType.mobile,
                child: const Spacer(),
              ),
              SizedBox(
                width: AppController.to.getDeviceScreenType() ==
                        DeviceScreenType.mobile
                    ? 300
                    : 450,
                child: DropdownButtonHideUnderline(
                  child: DropdownButton2(
                    isExpanded: true,
                    items: stringCategoryOptions
                        .map(
                          (item) => DropdownMenuItem<String>(
                            value: item,
                            child: Text(
                              item,
                            ),
                          ),
                        )
                        .toList(),
                    value: stringCategoryOptions.first,
                    onChanged: (value) {},
                    style: TextStyle(
                      color: isError ? Colors.red : null,
                    ),
                    hint: Text(
                      textValue,
                      style: labelStyle,
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
