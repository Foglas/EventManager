import 'package:dbs_2_front_end/controller/profile_controller.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import '../widgets/input_line.dart';
import '../widgets/validation_text.dart';
import 'login_page.dart';

class ProfilePage extends GetView<ProfileController> {
  const ProfilePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final controller = Get.put(ProfileController());
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Center(
            child: Text(
              'This is content for profile, user can edit his information',
              style: Get.textTheme.titleLarge,
            ),
          ),
          const Divider(),

          Obx(
            () => controller.isChangeInformation.value
                ? Column(
                    children: [
                      InputLine(
                        textValue: 'Email',
                        controller: controller.emailTextController,
                        disableEdit: true,
                      ),
                      InputLine(
                          textValue: 'Name',
                          controller: controller.nameTextController),
                      InputLine(
                          textValue: 'Surname',
                          controller: controller.surnameTextController),
                      InputLine(
                          textValue: 'Phone',
                          controller: controller.phoneTextController),
                      InputLine(
                        textValue: 'Birth date',
                        controller: controller.dateTextController,
                        disableEdit: true,
                        onTap: () => {
                          controller.handleSelectDate(),
                        },
                      ),
                      GestureDetector(
                        child: const Text(
                          "Change my password",
                          style:
                              TextStyle(decoration: TextDecoration.underline),
                        ),
                        onTap: () {
                          controller.handleSwitchToResetPassword();
                        },
                      ),
                    ],
                  )
                : const SizedBox.shrink(),
          ),

          Obx(
            () => controller.isChangePassword.value
                ? Column(
                    children: [
                      InputLine(
                          obscureText: true,
                          textValue: 'Old password',
                          controller: controller.oldPasswordTextController),
                      InputLine(
                          obscureText: true,
                          textValue: 'New password',
                          controller: controller.passwordTextController),
                      InputLine(
                          obscureText: true,
                          textValue: 'New password again',
                          controller: controller.passwordTextController),
                      GestureDetector(
                        child: const Text(
                          "Back to edit user details",
                          style:
                              TextStyle(decoration: TextDecoration.underline),
                        ),
                        onTap: () {
                          controller.handleSwitchToEditDetails();
                        },
                      ),
                    ],
                  )
                : const SizedBox.shrink(),
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
                  child: const Text(
                    'Edit',
                  ),
                ),
              ),
            ),
          ),

          /// Success registration message
          Obx(
            () => controller.displaySuccessfulEditMessage.value
                ? const Center(
                    child: Padding(
                      padding: EdgeInsets.all(8.0),
                      child: SuccessMessage('Your edit was successful'),
                    ),
                  )
                : const SizedBox.shrink(),
          ),

          /// Success registration message
          Obx(
            () => controller.displaySuccessfulResetPasswordMessage.value
                ? const Center(
                    child: Padding(
                      padding: EdgeInsets.all(8.0),
                      child: SuccessMessage('Your password was changed'),
                    ),
                  )
                : const SizedBox.shrink(),
          ),
        ],
      ),
    );
  }
}
