import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:responsive_builder/responsive_builder.dart';

import '../controller/app_controller.dart';

class InputLine extends StatelessWidget {
  final String textValue;
  final VoidCallback? toggleAction;
  final TextEditingController controller;
  final GestureTapCallback? onTap;
  final bool disableEdit;
  final bool obscureText;
  final bool toggleActionEnabled;
  final bool isError;

  const InputLine({
    Key? key,
    this.toggleAction,
    this.toggleActionEnabled = false,
    this.obscureText = false,
    required this.textValue,
    required this.controller,
    this.onTap,
    this.disableEdit = false,
    this.isError = false,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final textFieldStyle = TextStyle(
      color: isError ? Get.theme.colorScheme.onErrorContainer : null,
    );

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
                    ? (!toggleActionEnabled ? 300 : 250)
                    : (!toggleActionEnabled ? 450 : 400),
                child: TextField(
                  obscureText: obscureText,
                  obscuringCharacter: "*",
                  enabled: !disableEdit,
                  onTap: onTap,
                  controller: controller,
                  decoration: InputDecoration(
                    label: Text(
                      textValue,
                      style: textFieldStyle,
                    ),
                    focusColor: Colors.orangeAccent,
                    enabledBorder: const OutlineInputBorder(),
                    focusedBorder: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(4),
                      borderSide: const BorderSide(
                        width: 1,
                      ),
                    ),
                    filled: true,
                    fillColor: isError
                        ? Get.theme.colorScheme.errorContainer
                        : Get.theme.colorScheme.background,
                  ),
                  style: textFieldStyle,
                ),
              ),
              Visibility(
                visible: toggleActionEnabled,
                child: IconButton(
                  icon: const Icon(Icons.search_rounded),
                  onPressed: toggleAction,
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}
