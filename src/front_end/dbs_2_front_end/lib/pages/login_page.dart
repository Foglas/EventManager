import 'package:dropdown_button2/dropdown_button2.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:responsive_builder/responsive_builder.dart';

import '../controller/app_controller.dart';
import '../controller/login_controller.dart';
import '../widgets/input_line.dart';

class LoginPage extends GetView<LoginController> {
  const LoginPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final controller = Get.put(LoginController());

    return Center(
      child: SizedBox(
        width: AppController.to.getDeviceScreenType() != DeviceScreenType.mobile
            ? 600
            : 300,
        child: Column(
          children: [
            /// title of form
            AppController.to.getDeviceScreenType() == DeviceScreenType.mobile
                ? const SizedBox.shrink()
                : Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Obx(() => Text(controller.state.value.title(),
                        style: Get.textTheme.titleLarge)),
                  ),

            /// name and surname fields
            Obx(
              () => controller.state.value.isRegistration()
                  ? Column(
                      children: [
                        Obx(
                          () => InputLine(
                              isError: controller.nameErrorState.value,
                              textValue: 'Name',
                              controller: controller.nameTextController),
                        ),
                        Obx(
                          () => InputLine(
                              isError: controller.surnameErrorState.value,
                              textValue: 'Surname',
                              controller: controller.surnameTextController),
                        ),
                        Obx(
                          () => InputLine(
                              isError: controller.phoneErrorState.value,
                              textValue: 'Phone',
                              controller: controller.phoneTextController),
                        ),
                        Obx(
                          () => InputLine(
                            isError: controller.dateErrorState.value,
                            textValue: 'Date',
                            controller: controller.dateTextController,
                            disableEdit: true,
                            onTap: () => {
                              controller.handleSelectDate(),
                            },
                          ),
                        ),
                      ],
                    )
                  : const SizedBox.shrink(),
            ),

            Obx(
              () => InputLine(
                textValue: 'E-mail',
                controller: controller.emailTextController,
                isError: controller.emailErrorState.value,
              ),
            ),

            /// email and password fields
            Obx(
              () => controller.state.value.isResetPassword()
                  ? const SizedBox.shrink()
                  : Obx(
                      () => InputLine(
                          isError: controller.passwordErrorState.value,
                          obscureText: true,
                          textValue: 'Password',
                          controller: controller.passwordTextController),
                    ),
            ),

            /// password again button
            Obx(
              () => controller.state.value.isRegistration()
                  ? InputLine(
                      isError: controller.passwordAgainErrorState.value,
                      obscureText: true,
                      textValue: 'Password again',
                      controller: controller.passwordAgainTextController,
                    )
                  : const SizedBox.shrink(),
            ),

            /// Registration text link
            Padding(
              padding: const EdgeInsets.only(top: 8.0, bottom: 8.0),
              child: Obx(
                () => controller.state.value.isLogin()
                    ? Row(
                        children: [
                          const Spacer(),
                          GestureDetector(
                            child: const Text(
                              "Registration",
                              style: TextStyle(
                                  decoration: TextDecoration.underline),
                            ),
                            onTap: () {
                              controller.handleSwitchToRegistration();
                            },
                          ),
                          const SizedBox(width: 15),
                          GestureDetector(
                            child: const Text(
                              "Forgot password",
                              style: TextStyle(
                                  decoration: TextDecoration.underline),
                            ),
                            onTap: () {
                              controller.handleSwitchToForgotPassword();
                            },
                          ),
                        ],
                      )
                    : const SizedBox.shrink(),
              ),
            ),

            /// Back to login text link
            Obx(
              () => controller.state.value.isLogin()
                  ? const SizedBox.shrink()
                  : GestureDetector(
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
                    child: Obx(
                        () => Text(controller.state.value.submitTextValue())),
                  ),
                ),
              ),
            ),

            Padding(
              padding: const EdgeInsets.only(top: 15, bottom: 15),
              child: Obx(() => controller.messageWidget.value),
            ),
          ],
        ),
      ),
    );
  }
}
