import 'package:flutter/material.dart';
import 'package:flutter_material_pickers/helpers/show_date_picker.dart';
import 'package:get/get.dart';
import 'package:intl/intl.dart';

import '../service/rest_service.dart';
import '../utils/menu_item.dart';
import '../widgets/validation_text.dart';
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
  RxBool emailErrorState = false.obs;

  TextEditingController passwordTextController = TextEditingController();
  RxBool passwordErrorState = false.obs;

  TextEditingController passwordAgainTextController = TextEditingController();
  RxBool passwordAgainErrorState = false.obs;

  TextEditingController nameTextController = TextEditingController();
  RxBool nameErrorState = false.obs;

  TextEditingController surnameTextController = TextEditingController();
  RxBool surnameErrorState = false.obs;

  TextEditingController phoneTextController = TextEditingController();
  RxBool phoneErrorState = false.obs;

  TextEditingController dateTextController = TextEditingController();
  RxBool dateErrorState = false.obs;

  Rx<Widget> messageWidget = Rx(const SizedBox.shrink());

  RxString errorLoginMessage = ''.obs;

  DateTime birthDate = DateTime(2000, 1, 1);

  handleSwitchToRegistration() {
    _resetErrorStates();
    state.value = LoginViewState.registration;
  }

  handleSwitchToLogin() {
    _resetErrorStates();
    state.value = LoginViewState.login;
  }

  handleSwitchToForgotPassword() {
    _resetErrorStates();
    state.value = LoginViewState.forgotPassword;
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
    _resetErrorStates();

    if (emailTextController.value.text.isEmpty ||
        dateTextController.value.text.isEmpty ||
        nameTextController.value.text.isEmpty ||
        surnameTextController.value.text.isEmpty ||
        phoneTextController.value.text.isEmpty ||
        passwordTextController.value.text.isEmpty ||
        passwordAgainTextController.value.text.isEmpty) {
      if (nameTextController.value.text.isEmpty) {
        nameErrorState.value = true;
      }

      if (surnameTextController.value.text.isEmpty) {
        surnameErrorState.value = true;
      }

      if (phoneTextController.value.text.isEmpty) {
        phoneErrorState.value = true;
      }

      if (emailTextController.value.text.isEmpty) {
        emailErrorState.value = true;
      }

      if (passwordTextController.value.text.isEmpty) {
        passwordErrorState.value = true;
      }

      if (passwordAgainTextController.value.text.isEmpty) {
        passwordAgainErrorState.value = true;
      }

      if (dateTextController.value.text.isEmpty) {
        dateErrorState.value = true;
      }

      _putErrorMessage(
          message: "All fields has to be filled in order to register");
      return;
    }

    final ServerResponse response = await RestService.to.registerUser(
      dateOfBirth: dateTextController.value.text,
      email: emailTextController.value.text,
      name: nameTextController.value.text,
      password: passwordTextController.value.text,
      passwordAgain: passwordAgainTextController.value.text,
      phone: phoneTextController.value.text,
      surname: surnameTextController.value.text,
      username: emailTextController.value.text,
    );

    if (response.statusCode == 400) {
      _putErrorMessage(message: response.body['message']);
      return;
    }

    state.value = LoginViewState.login;
    _putSuccessMessage(message: response.body['message']);

    _resetTextEditingControllers();
  }

  _handleLogin() async {
    passwordErrorState.value = false;
    emailErrorState.value = false;

    if (emailTextController.text.isEmpty ||
        passwordTextController.text.isEmpty) {
      emailErrorState.value = true;
      passwordErrorState.value = true;

      _putErrorMessage(message: "Both email and password has to be filled");
      return;
    }

    if (!emailTextController.value.text.isEmail) {
      emailErrorState.value = true;
      _putErrorMessage(message: "Email is not a valid email");
      return;
    }

    final ServerResponse response = await RestService.to.loginUser(
        password: passwordTextController.value.text,
        email: emailTextController.value.text);

    if (response.statusCode == 400) {
      _putErrorMessage(message: response.body['message']!);
      return;
    }

    if (response.statusCode == 200) {
      AppController.to.isUserLoggedIn.value = true;
      AppController.to.handleMenuItemTapped(MenuItem.profile);
    }

    _resetTextEditingControllers();
  }

  _handleForgotPassword() async {
    state.value = LoginViewState.login;
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

  _resetErrorStates() {
    emailErrorState.value = false;
    passwordErrorState.value = false;
    passwordAgainErrorState.value = false;
    nameErrorState.value = false;
    surnameErrorState.value = false;
    phoneErrorState.value = false;
    dateErrorState.value = false;
  }

  _putErrorMessage({required String message}) {
    messageWidget.value = ErrorMessage(message);
  }

  _putSuccessMessage({required String message}) {
    messageWidget.value = SuccessMessage(message);
  }
}
