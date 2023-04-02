import 'package:flutter/material.dart';
import 'package:flutter_material_pickers/helpers/show_date_picker.dart';
import 'package:get/get.dart';
import 'package:intl/intl.dart';

import '../service/rest_service.dart';
import '../utils/menu_item.dart';
import '../widgets/validation_text.dart';
import 'app_controller.dart';

/// Represents the different states for the login screen.
enum LoginViewState {
  registration, // Registration form
  login, // Login form
  forgotPassword; // Forgot password form

  /// Returns the title of the login screen for the current state.
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

  /// Returns the text to display on the submit button for the current state.
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

  /// Returns true if the current state is the login state.
  bool isLogin() {
    return this == LoginViewState.login;
  }

  /// Returns true if the current state is the registration state.
  bool isRegistration() {
    return this == LoginViewState.registration;
  }

  /// Returns true if the current state is the forgot password state.
  bool isResetPassword() {
    return this == LoginViewState.forgotPassword;
  }
}

class LoginController extends GetxController {
  // Observable state for the current view state
  Rx<LoginViewState> state = Rx(LoginViewState.login);

  // Controllers for all text fields in the form
  TextEditingController emailTextController = TextEditingController();
  TextEditingController passwordTextController = TextEditingController();
  TextEditingController passwordAgainTextController = TextEditingController();
  TextEditingController nameTextController = TextEditingController();
  TextEditingController surnameTextController = TextEditingController();
  TextEditingController phoneTextController = TextEditingController();
  TextEditingController dateTextController = TextEditingController();

  // Observable error states for each text field
  RxBool emailErrorState = false.obs;
  RxBool passwordErrorState = false.obs;
  RxBool passwordAgainErrorState = false.obs;
  RxBool nameErrorState = false.obs;
  RxBool surnameErrorState = false.obs;
  RxBool phoneErrorState = false.obs;
  RxBool dateErrorState = false.obs;

  // Observable widget to display success/error messages
  Rx<Widget> messageWidget = Rx(const SizedBox.shrink());

  // Default birth date to January 1st, 2000
  DateTime birthDate = DateTime(2000, 1, 1);

  /// Resets error states and switches to registration view.
  void handleSwitchToRegistration() {
    _resetErrorStates();
    state.value = LoginViewState.registration;
  }

  /// Resets error states and switches to login view.
  void handleSwitchToLogin() {
    _resetErrorStates();
    state.value = LoginViewState.login;
  }

  /// Resets error states and switches to forgot password view.
  void handleSwitchToForgotPassword() {
    _resetErrorStates();
    state.value = LoginViewState.forgotPassword;
  }

  /// Shows a material date picker dialog to select a birth date.
  /// Updates the [dateTextController] with the selected date.
  Future<void> handleSelectDate() async {
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

  /// Submits the form based on the current view state.
  /// Displays a circular progress indicator while processing the submission.
  /// Does nothing if the view state is not supported.
  Future<void> handleSubmitButton() async {
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

  /// Handles user registration.
  /// Validates the form and sends a registration request to the server.
  /// Updates error and success message states accordingly.
  /// Resets the text editing controllers on success.
  Future<void> _handleRegistration() async {
    _resetErrorStates();

    // Check if all fields are filled
    if (emailTextController.value.text.isEmpty ||
        dateTextController.value.text.isEmpty ||
        nameTextController.value.text.isEmpty ||
        surnameTextController.value.text.isEmpty ||
        phoneTextController.value.text.isEmpty ||
        passwordTextController.value.text.isEmpty ||
        passwordAgainTextController.value.text.isEmpty) {
      // Update error states for each empty field
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
      // Set error message
      _putErrorMessage(
          message: "All fields have to be filled in order to register");
      return;
    }

    // Check if email is valid
    if (!emailTextController.value.text.isEmail) {
      // Update error state and set error message
      emailErrorState.value = true;
      _putErrorMessage(message: "Email is not a valid email");
      return;
    }

    // Send registration request to the server
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

    // Handle server response
    if (response.statusCode == 400) {
      // Check error types and update error states accordingly
      if (response.body['errorType'] == "name_surname_same") {
        nameErrorState.value = true;
        surnameErrorState.value = true;
      }
      if (response.body['errorType'] == "phone_number_length") {
        phoneErrorState.value = true;
      }
      if (response.body['errorType'] == "password_not_match") {
        passwordAgainErrorState.value = true;
      }
      if (response.body['errorType'] == "email_exists") {
        emailErrorState.value = true;
      }

      // Set error message for invalid registration data
      _putErrorMessage(message: response.body['message']);
      return;
    }
    if (response.statusCode == 200) {
      // Set success message for successful registration and switch to login view
      state.value = LoginViewState.login;
      _putSuccessMessage(message: response.body['message']);
      // Reset text editing controllers
      _resetTextEditingControllers();
      return;
    }
    if (response.statusCode == 500) {
      // Set error message for server error
      _putErrorMessage(message: response.body['message']);
    }
  }

  /// Handles user login.
  /// Validates the form and sends a login request to the server.
  /// Updates error message and user login state accordingly.
  /// Resets the text editing controllers on success.
  Future<void> _handleLogin() async {
    // Reset error states
    passwordErrorState.value = false;
    emailErrorState.value = false;

    // Check if both email and password are filled
    if (emailTextController.text.isEmpty ||
        passwordTextController.text.isEmpty) {
      // Update error states and set error message
      emailErrorState.value = true;
      passwordErrorState.value = true;
      _putErrorMessage(message: "Both email and password have to be filled");
      return;
    }

    // Check if email is valid
    if (!emailTextController.value.text.isEmail) {
      // Update error state and set error message
      emailErrorState.value = true;
      _putErrorMessage(message: "Email is not a valid email");
      return;
    }

    // Send login request to the server
    final ServerResponse response = await RestService.to.loginUser(
        password: passwordTextController.value.text,
        email: emailTextController.value.text);

    // Handle server response
    if (response.statusCode == 400) {
      // Check error types and update error states accordingly
      if (response.body['errorType'] == "wrong_password") {
        passwordErrorState.value = true;
      }
      if (response.body['errorType'] == "user_not_found") {
        emailErrorState.value = true;
      }

      // Set error message for invalid login data
      _putErrorMessage(message: response.body['message']!);
      return;
    }
    if (response.statusCode == 200) {
      // Set user login state and switch to profile view
      await AppController.to.storeUserJwtToken(response.body['message']);

      AppController.to.handleMenuItemTapped(MenuItem.profile);
      // Reset text editing controllers
      _resetTextEditingControllers();
      return;
    }
    if (response.statusCode == 500) {
      // Set error message for server error
      _putErrorMessage(message: response.body['message']);
    }
  }

  /// Handles forgot password action.
  /// Validates the email and switches to login view.
  /// Sets a success message indicating that the password reset feature is not implemented yet.
  /// Resets text editing controllers.
  Future<void> _handleForgotPassword() async {
    // Reset error state
    emailErrorState.value = false;

    // Check if email is filled and valid
    if (emailTextController.value.text.isEmpty) {
      // Update error state and set error message
      emailErrorState.value = true;
      _putErrorMessage(
          message: 'Email has to be filled in order to reset your password');
      return;
    }
    if (!emailTextController.value.text.isEmail) {
      // Update error state and set error message
      emailErrorState.value = true;
      _putErrorMessage(
          message:
              'Email has to be a valid email in order to reset your password');
      return;
    }

    // Set success message and switch to login view
    state.value = LoginViewState.login;
    _putSuccessMessage(
        message:
            "This feature is not implemented yet. Please contact support for password reset.");
    // Reset text editing controllers
    _resetTextEditingControllers();
  }

  /// Resets text editing controllers.
  void _resetTextEditingControllers() {
    nameTextController.text = '';
    surnameTextController.text = '';
    emailTextController.text = '';
    passwordTextController.text = '';
    passwordAgainTextController.text = '';
    phoneTextController.text = '';
    dateTextController.text = '';
  }

  /// Resets error states.
  void _resetErrorStates() {
    emailErrorState.value = false;
    passwordErrorState.value = false;
    passwordAgainErrorState.value = false;
    nameErrorState.value = false;
    surnameErrorState.value = false;
    phoneErrorState.value = false;
    dateErrorState.value = false;
  }

  /// Sets error message state with the given message.
  void _putErrorMessage({required String message}) {
    messageWidget.value = ErrorMessage(message);
  }

  /// Sets success message state with the given message.
  void _putSuccessMessage({required String message}) {
    messageWidget.value = SuccessMessage(message);
  }
}
