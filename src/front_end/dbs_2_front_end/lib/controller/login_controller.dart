import 'package:flutter/material.dart';
import 'package:flutter_material_pickers/helpers/show_date_picker.dart';
import 'package:get/get.dart';
import 'package:intl/intl.dart';

import '../service/rest_service.dart';
import '../utils/menu_item.dart';
import 'app_controller.dart';

enum LoginViewState {
  registration,
  login,
  forgotPassword;

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

  bool isLogin() {
    return this == LoginViewState.login;
  }

  bool isRegistration() {
    return this == LoginViewState.registration;
  }

  bool isResetPassword() {
    return this == LoginViewState.forgotPassword;
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

  RxBool displaySuccessfulRegistrationMessage = false.obs;
  RxBool displaySuccessfulResetPasswordMessage = false.obs;
  RxBool displayErrorLoginMessage = false.obs;

  DateTime birthDate = DateTime(2000, 1, 1);

  handleSwitchToRegistration() {
    state.value = LoginViewState.registration;

    _resetDisplayMessages();
  }

  handleSwitchToLogin() {
    state.value = LoginViewState.login;

    _resetDisplayMessages();
  }

  handleSwitchToForgotPassword() {
    state.value = LoginViewState.forgotPassword;

    _resetDisplayMessages();
  }

  handleSelectDate() async {
    await showMaterialDatePicker(
      title: 'Select date',
      firstDate: DateTime(1910),
      lastDate: DateTime(2010),
      context: Get.context!,
      selectedDate: birthDate,
      onChanged: (value) {
        birthDate = value;
        String formattedDate = DateFormat('yyyy-MM-dd').format(value);
        dateTextController.text = formattedDate;
      },
    );
  }

  handleSubmitButton() async {
    AppController.to.displayCircularProgressIndicator.value = true;
    switch (state.value) {
      case LoginViewState.registration:
        await _handleRegistration();
        break;
      case LoginViewState.login:
        await _handleLogin();
        break;
      case LoginViewState.forgotPassword:
        await _handleForgotPassword();
        break;
    }
    AppController.to.displayCircularProgressIndicator.value = false;
  }

  _handleRegistration() async {
    await RestService.to.registerUser(
      dateOfBirth: dateTextController.value.text,
      email: emailTextController.value.text,
      name: nameTextController.value.text,
      password: passwordTextController.value.text,
      passwordAgain: passwordAgainTextController.value.text,
      phone: phoneTextController.value.text,
      surname: surnameTextController.value.text,
      username: emailTextController.value.text,
    );

    state.value = LoginViewState.login;
    displaySuccessfulRegistrationMessage.value = true;

    _resetTextEditingControllers();
  }

  String get newMethod => '';

  _handleLogin() async {
    if (emailTextController.text.isNotEmpty &&
        passwordTextController.text.isNotEmpty) {
      AppController.to.isUserLoggedIn.value = true;
      AppController.to.handleMenuItemTapped(MenuItem.profile);
    } else {
      displayErrorLoginMessage.value = true;
    }

    state.value = LoginViewState.login;

    await RestService.to.loginUser(
        password: passwordTextController.value.text,
        username: emailTextController.value.text);

    _resetTextEditingControllers();
  }

  _handleForgotPassword() async {
    state.value = LoginViewState.login;
    displaySuccessfulResetPasswordMessage.value = true;

    _resetTextEditingControllers();
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

  _resetDisplayMessages() {
    displaySuccessfulRegistrationMessage.value = false;
    displayErrorLoginMessage.value = false;
    displaySuccessfulResetPasswordMessage.value = false;
  }
}
