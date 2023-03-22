import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';

class ObxOnly extends StatelessWidget {
  final RxBool? only;
  final RxBool? not;
  final Widget child;


  const ObxOnly({Key? key, this.only, required this.child, this.not}) : super(key: key);

  @override
  Widget build(BuildContext context) {

    if (only != null) {
      return Obx(
              () => only!.value ? child : const SizedBox.shrink()
      );
    }
    if (not != null) {
      return Obx(
              () => not!.value ? const SizedBox.shrink() : child,
      );
    }

    return const SizedBox.shrink();
  }
}
