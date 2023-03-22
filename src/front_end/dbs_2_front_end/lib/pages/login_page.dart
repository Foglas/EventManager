import 'package:dbs_2_front_end/utils/obx_only.dart';
import 'package:dropdown_button2/dropdown_button2.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:responsive_builder/responsive_builder.dart';

import '../controller/app_controller.dart';
import '../controller/login_controller.dart';
import '../widgets/validation_text.dart';

class LoginPage extends GetView<LoginController> {
  const LoginPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final controller = Get.put(LoginController());

    return Center(
      child: SizedBox(
        width: AppController.to.getDeviceScreenType() !=
            DeviceScreenType.mobile ? 600 : 300,
        child: Column(
          children: [
            /// title of form
            AppController.to.getDeviceScreenType() == DeviceScreenType.mobile ? const SizedBox.shrink() :
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: Obx(() => Text(controller.state.value.title(),
                  style: Get.textTheme.titleLarge)),
            ),

            /// name and surname fields
            ObxOnly(
              only: controller.isRegistration,
              child: Column(
                children: [
                  InputLine(textValue: 'Name', controller: controller.nameTextController),
                  InputLine(textValue: 'Surname', controller: controller.surnameTextController),
                  InputLine(textValue: 'Phone', controller: controller.phoneTextController),
                  InputLine(textValue: 'Date', controller: controller.dateTextController, disableEdit: true, onTap: () => {
                    controller.handleSelectDate(),
                  },),
                ],
              ),
            ),

             InputLine(textValue: 'E-mail', controller: controller.emailTextController,),

            /// email and password fields
            ObxOnly(
              not: controller.isForgotPassword,
              child: Column(
                children: [
                  InputLine(obscureText: true, textValue: 'Password', controller : controller.passwordTextController),
                ],
              ),
            ),

            /// password again button
            ObxOnly(
              only: controller.isRegistration,
              child: InputLine(obscureText: true,textValue: 'Password again', controller: controller.passwordAgainTextController,),
            ),

            /// Registration text link
            Padding(
              padding: const EdgeInsets.only(top: 8.0, bottom: 8.0),
              child: ObxOnly(
                only: controller.isLogin,
                child: Row(
                  children: [const Spacer(),
                    GestureDetector(
                      child: const Text(
                        "Registration",
                        style: TextStyle(decoration: TextDecoration.underline),
                      ),
                      onTap: () {
                        controller.handleSwitchToRegistration();
                      },
                    ),
                    const SizedBox(width: 15),
                    GestureDetector(
                      child: const Text(
                        "Forgot password",
                        style: TextStyle(decoration: TextDecoration.underline),
                      ),
                      onTap: () {
                        controller.handleSwitchToForgotPassword();
                      },
                    ),
                  ],
                ),
              ),
            ),

            /// Back to login text link
            ObxOnly(
              not: controller.isLogin,
              child: GestureDetector(
                      child: Column(
                        children: [
                          Center(
                            child: controller.state.value ==
                                    LoginViewState.registration
                                ? const Text(
                                    "I already have an account",
                                    style: TextStyle(
                                        decoration: TextDecoration.underline),
                                  )
                                : const Text(
                                    "Go back to login form",
                                    style: TextStyle(
                                        decoration: TextDecoration.underline),
                                  ),
                          ),
                        ],
                      ),
                      onTap: () {
                        controller.handleSwitchToLogin();
                      },
                    ),
            ),

            const SizedBox(
              height: 10,
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
                      controller.handleSubmitButton();
                    },
                    child:
                        Obx(() => Text(controller.state.value.submitTextValue())),
                  ),
                ),
              ),
            ),

            /// Success registration message
            ObxOnly(
              only: controller.displaySuccessfulRegistrationMessage,
              child: const Center(
                child: Padding(
                  padding: EdgeInsets.all(8.0),
                  child:
                      SuccessMessage('Your registration was successful, now you can login'),
                ),
              ),
            ),

            /// Error login message
            ObxOnly(
              only: controller.displayErrorLoginMessage,
              child: const Center(
                child: Padding(
                  padding: EdgeInsets.all(8.0),
                  child: ErrorMessage(
                      "You can't login yet, because our amazing spring boot backend is not implemented yet"),
                ),
              ),
            ),

            /// Successful message on forgot password field
            ObxOnly(
              only: controller.displaySuccessfulResetPasswordMessage,
              child: const Center(
                child: Padding(
                  padding: EdgeInsets.all(8.0),
                  child: SuccessMessage(
                      "if this email exists in our database we will maybe send you reset link, but probably not"),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class InputLine extends StatelessWidget {
  final String textValue;

  final VoidCallback? toggleAction;

  final TextEditingController controller;

  final GestureTapCallback? onTap;

  final bool disableEdit;

  final bool obscureText;

  final bool toggleActionEnabled;

  const InputLine({Key? key, this.toggleAction, this.toggleActionEnabled = false, this.obscureText = false, required this.textValue, required this.controller, this.onTap, this.disableEdit = false}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only( top: 8.0, bottom: 8.0),
      child: GestureDetector(
        onTap: onTap,
        child: SizedBox(
          width: AppController.to.getDeviceScreenType() ==
              DeviceScreenType.mobile
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
                        style: Get.textTheme.titleMedium,
                      ))),
              Visibility(
                  visible: AppController.to.getDeviceScreenType() !=
                      DeviceScreenType.mobile,
                  child: const Spacer()),
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
                      label: Text(textValue, style: Get.textTheme.bodyMedium),
                      focusColor: Colors.orangeAccent,
                      enabledBorder: const OutlineInputBorder(
                          //  borderRadius: BorderRadius.circular(4),
                          //  borderSide: BorderSide(
                          //      color: Get.theme.colorScheme.outline, width: 1),
                          ),
                      focusedBorder: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(4),
                        borderSide: BorderSide(
                            color: Get.theme.colorScheme.outline, width: 1),
                      ),
                      // fillColor: ProjectController.to.isParameterValid(type).value
                      //     ? Palette.validItemBackgroundColor
                      //     : Palette.unselectedItemBackgroundColor,
                      filled: true,
                    ),
                  ),),
              Visibility(visible: toggleActionEnabled, child: IconButton(icon: const Icon(Icons.search_rounded), onPressed: toggleAction,),)
            ],
          ),
        ),
      ),
    );
  }
}

class DropdownLine extends StatelessWidget {
  final String textValue;

  final GestureTapCallback? onTap;

  final bool disableEdit;

  const DropdownLine({Key? key, required this.textValue, this.onTap, required this.disableEdit}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final List<String> stringCategoryOptions = ['concert', 'party', 'trip'];

    return Padding(
      padding: const EdgeInsets.only( top: 8.0, bottom: 8.0),
      child: GestureDetector(
        onTap: onTap,
        child: SizedBox(
          width: AppController.to.getDeviceScreenType() ==
              DeviceScreenType.mobile
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
                        style: Get.textTheme.titleMedium,
                      ))),
              Visibility(
                  visible: AppController.to.getDeviceScreenType() !=
                      DeviceScreenType.mobile,
                  child: const Spacer()),
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
                    onChanged: (value) {
                    },
                  ),
                ),),

            ],
          ),
        ),
      ),
    );

  }
}
