import 'package:flutter/material.dart';
import 'package:flutter_material_pickers/helpers/show_date_picker.dart';
import 'package:get/get.dart';
import 'app_controller.dart';


class ProfileController extends GetxController {



  TextEditingController emailTextController = TextEditingController(text: 'testing@email.com');
  TextEditingController passwordTextController = TextEditingController();
  TextEditingController oldPasswordTextController = TextEditingController();
  TextEditingController passwordAgainTextController = TextEditingController();
  TextEditingController nameTextController = TextEditingController(text : "Jan");
  TextEditingController surnameTextController = TextEditingController(text : "Novak");
  TextEditingController phoneTextController = TextEditingController(text: "+420 774 853 584");
  TextEditingController dateTextController = TextEditingController(text: DateTime(2000,1,1).toString());

  RxBool isChangePassword = false.obs;
  RxBool isChangeInformation = true.obs;

  RxBool displaySuccessfulEditMessage = false.obs;
  RxBool displaySuccessfulResetPasswordMessage = false.obs;

  DateTime birthDate = DateTime(2000,1,1);

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


  handleSubmitButton()  async {
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