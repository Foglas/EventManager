import 'package:flutter/material.dart';
import 'package:flutter_material_pickers/helpers/show_date_picker.dart';
import 'package:get/get.dart';
import '../service/rest_service.dart';
import 'app_controller.dart';

class ProfileController extends GetxController {
  TextEditingController emailTextController = TextEditingController();
  TextEditingController passwordTextController = TextEditingController();
  TextEditingController oldPasswordTextController = TextEditingController();
  TextEditingController passwordAgainTextController = TextEditingController();
  TextEditingController nameTextController = TextEditingController();
  TextEditingController surnameTextController = TextEditingController();
  TextEditingController phoneTextController = TextEditingController();
  TextEditingController dateTextController = TextEditingController();

  RxBool isChangePassword = false.obs;
  RxBool isChangeInformation = true.obs;

  RxBool displaySuccessfulEditMessage = false.obs;
  RxBool displaySuccessfulResetPasswordMessage = false.obs;

  DateTime birthDate = DateTime(2000, 1, 1);

  onInit() async {
    final ServerResponse response = await RestService.to.getCurrentUser();

    emailTextController.text = response.body['email'] ?? '';
    nameTextController.text = response.body['name'] ?? '';
    surnameTextController.text = response.body['surname'] ?? '';
    phoneTextController.text = response.body['phone'] ?? '';
    dateTextController.text = response.body['birthDate'] ?? '';
  }

  _resetDisplayMessages() {
    displaySuccessfulEditMessage.value = false;
    displaySuccessfulResetPasswordMessage.value = false;
  }

  handleSwitchToResetPassword() {
    isChangePassword.value = true;
    isChangeInformation.value = false;
    _resetDisplayMessages();
  }

  handleSwitchToEditDetails() {
    isChangePassword.value = false;
    isChangeInformation.value = true;
    _resetDisplayMessages();
  }

  handleSelectDate() async {
    await showMaterialDatePicker(
      title: 'Select date',
      firstDate: DateTime(1910),
      lastDate: DateTime(2010),
      context: Get.context!,
      selectedDate: birthDate,
      onChanged: (value) => {
        birthDate = value,
        dateTextController.text = value.toString(),
      },
    );
  }

  handleSubmitButton() async {
    _resetDisplayMessages();

    AppController.to.displayCircularProgressIndicator.value = true;

    await Future.delayed(const Duration(seconds: 1));

    AppController.to.displayCircularProgressIndicator.value = false;

    if (isChangePassword.value) {
      _resetTextEditingControllers();
      displaySuccessfulEditMessage.value = true;
    } else if (isChangeInformation.value) {
      _resetTextEditingControllers();
      handleSwitchToEditDetails();
      displaySuccessfulEditMessage.value = true;
    }
  }

  _resetTextEditingControllers() {
    nameTextController.text = '';
    surnameTextController.text = '';
    emailTextController.text = '';
    passwordTextController.text = '';
    oldPasswordTextController.text = '';
    passwordAgainTextController.text = '';
    phoneTextController.text = '';
    dateTextController.text = '';
  }
}
