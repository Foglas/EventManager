import 'package:flutter/material.dart';
import 'package:flutter_material_pickers/helpers/show_date_picker.dart';
import 'package:get/get.dart';

import '../utils/menu_item.dart';
import 'app_controller.dart';

enum LoginViewState {
  registration, login, forgotPassword;

  String title() {
    switch (this) {
      case LoginViewState.registration:
      return 'Registration form';
      case LoginViewState.login:
        return 'Login form';
      case LoginViewState.forgotPassword:
        return 'Forgot password form';
    }
  }

  String submitTextValue() {
    switch (this) {

      case LoginViewState.registration:
        return "Register";
      case LoginViewState.login:
        return "Log in";
      case LoginViewState.forgotPassword:
        return "Submit";
    }
  }
}

class LoginController extends GetxController {
  Rx<LoginViewState> state = Rx(LoginViewState.login);

  TextEditingController emailTextController = TextEditingController();
  TextEditingController passwordTextController = TextEditingController();
  TextEditingController passwordAgainTextController = TextEditingController();
  TextEditingController nameTextController = TextEditingController();
  TextEditingController surnameTextController = TextEditingController();
  TextEditingController phoneTextController = TextEditingController();
  TextEditingController dateTextController = TextEditingController();


  RxBool isForgotPassword = false.obs;
  RxBool isRegistration = false.obs;
  RxBool isLogin = true.obs;

  RxBool displaySuccessfulRegistrationMessage = false.obs;
  RxBool displaySuccessfulResetPasswordMessage = false.obs;
  RxBool displayErrorLoginMessage = false.obs;

  DateTime birthDate = DateTime(2000,1,1);

  _resetDisplayMessages() {
    displaySuccessfulRegistrationMessage.value = false;
    displayErrorLoginMessage.value = false;
    displaySuccessfulResetPasswordMessage.value = false;
  }

  handleSwitchToRegistration() {
    state.value = LoginViewState.registration;
    isLogin.value = false;
    isForgotPassword.value = false;
    isRegistration.value = true;

    _resetDisplayMessages();
  }

  handleSwitchToLogin() {
    state.value = LoginViewState.login;
    isRegistration.value = false;
    isForgotPassword.value = false;
    isLogin.value = true;

    _resetDisplayMessages();
  }

  handleSwitchToForgotPassword() {
    state.value = LoginViewState.forgotPassword;
    isRegistration.value = false;
    isLogin.value = false;
    isForgotPassword.value = true;

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
    AppController.to.displayCircularProgressIndicator.value = true;

    await Future.delayed(const Duration(seconds: 1));

    AppController.to.displayCircularProgressIndicator.value = false;

    switch(state.value) {
      case LoginViewState.registration:
        state.value = LoginViewState.login;
        displaySuccessfulRegistrationMessage.value = true;
        isRegistration.value = false;
        isLogin.value = true;
        _resetTextEditingControllers();
        break;
      case LoginViewState.login:
        if (emailTextController.text.isNotEmpty && passwordTextController.text.isNotEmpty) {
          AppController.to.isUserLoggedIn.value = true;
          AppController.to.handleMenuItemTapped(MenuItem.profile);
        } else {
          displayErrorLoginMessage.value = true;
        }
        state.value = LoginViewState.login;
        isLogin.value = true;
        _resetTextEditingControllers();

        break;
      case LoginViewState.forgotPassword:
        state.value = LoginViewState.login;
        displaySuccessfulResetPasswordMessage.value = true;
        isForgotPassword.value = false;
        isLogin.value = true;
        _resetTextEditingControllers();
        break;
    }
  }

  _resetTextEditingControllers() {
    nameTextController.text = '';
    surnameTextController.text = '';
    emailTextController.text = '';
    passwordTextController.text = '';
    passwordAgainTextController.text = '';
    phoneTextController.text = '';
    dateTextController.text = '';
  }
}