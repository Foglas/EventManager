import 'package:flutter/material.dart';
import 'package:get/get.dart';

class ErrorMessage extends StatelessWidget {
  final String text;

  const ErrorMessage( this.text, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(8),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(25),
        color: Get.theme.colorScheme.errorContainer
      ),
      child: Text(
        text, style: TextStyle(
        color: Get.theme.colorScheme.onErrorContainer
      ),
      ),
    );
  }
}

class SuccessMessage extends StatelessWidget {
  final String text;

  const SuccessMessage( this.text, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(8),
      decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(25),
          color: Get.theme.colorScheme.secondaryContainer
      ),
      child: Text(
        text, style: TextStyle(
          color: Get.theme.colorScheme.onSecondaryContainer
      ),
      ),
    );
  }
}
