import 'dart:async';

import 'package:flutter/material.dart';
import 'package:get/get.dart';

class ErrorMessage extends StatefulWidget {
  final String text;
  final VoidCallback? onDismissed;

  const ErrorMessage(this.text, {Key? key, this.onDismissed}) : super(key: key);

  @override
  ErrorMessageState createState() => ErrorMessageState();
}

class ErrorMessageState extends State<ErrorMessage> {
  Timer? _timer;
  bool _visible = true;

  @override
  void initState() {
    super.initState();
    _startTimer();
  }

  @override
  void dispose() {
    _timer?.cancel();
    super.dispose();
  }

  void _startTimer() {
    _timer?.cancel();
    _timer = Timer(const Duration(seconds: 4), () {
      setState(() {
        _visible = false;
      });
      if (widget.onDismissed != null) {
        widget.onDismissed!();
      }
    });
  }

  @override
  void didUpdateWidget(ErrorMessage oldWidget) {
    super.didUpdateWidget(oldWidget);
    setState(() {
      _visible = true;
    });
    _startTimer();
  }

  @override
  Widget build(BuildContext context) {
    return AnimatedOpacity(
      opacity: _visible ? 1.0 : 0.0,
      duration: const Duration(milliseconds: 500),
      child: Container(
        padding: const EdgeInsets.all(8),
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(25),
          color: Get.theme.colorScheme.errorContainer,
        ),
        child: Text(
          widget.text,
          style: TextStyle(
            color: Get.theme.colorScheme.onErrorContainer,
          ),
        ),
      ),
      onEnd: () {
        if (!_visible && widget.onDismissed != null) {
          widget.onDismissed!();
        }
      },
    );
  }
}

class SuccessMessage extends StatefulWidget {
  final String text;
  final VoidCallback? onDismissed;

  const SuccessMessage(this.text, {Key? key, this.onDismissed})
      : super(key: key);

  @override
  SuccessMessageState createState() => SuccessMessageState();
}

class SuccessMessageState extends State<SuccessMessage> {
  Timer? _timer;
  bool _visible = true;

  @override
  void initState() {
    super.initState();
    _startTimer();
  }

  @override
  void dispose() {
    _timer?.cancel();
    super.dispose();
  }

  void _startTimer() {
    _timer?.cancel();
    _timer = Timer(const Duration(seconds: 3), () {
      setState(() {
        _visible = false;
      });
      if (widget.onDismissed != null) {
        widget.onDismissed!();
      }
    });
  }

  @override
  void didUpdateWidget(SuccessMessage oldWidget) {
    super.didUpdateWidget(oldWidget);
    setState(() {
      _visible = true;
    });
    _startTimer();
  }

  @override
  Widget build(BuildContext context) {
    return AnimatedOpacity(
      opacity: _visible ? 1.0 : 0.0,
      duration: const Duration(milliseconds: 500),
      child: Container(
        padding: const EdgeInsets.all(8),
        decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(25),
            color: Get.theme.colorScheme.secondaryContainer),
        child: Text(
          widget.text,
          style: TextStyle(color: Get.theme.colorScheme.onSecondaryContainer),
        ),
      ),
      onEnd: () {
        if (!_visible && widget.onDismissed != null) {
          widget.onDismissed!();
        }
      },
    );
  }
}
