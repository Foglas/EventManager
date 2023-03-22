import 'package:flutter/cupertino.dart';
import 'package:flutter_material_pickers/helpers/show_date_picker.dart';
import 'package:get/get.dart';

import 'app_controller.dart';

enum DashboardViewState {
  search,
  create,
  detail,
}

class DashboardController extends GetxController {
  Rx<DashboardViewState> state = Rx(DashboardViewState.search);

  String lipsum = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

  RxInt detailPageIndex = 0.obs;
  RxInt length = 5.obs;

  RxBool isSearch = true.obs;
  RxBool isCreate = false.obs;
  RxBool isDetail = false.obs;

  RxBool showEndDateValidationMessage = false.obs;

  final DateTime now = DateTime.now();

  DateTime startDate = DateTime.now();
  DateTime endDate = DateTime.now().add(const Duration(days: 2),);

  TextEditingController nameTextController = TextEditingController();
  TextEditingController descriptionTextController = TextEditingController();
  TextEditingController startDateTextController = TextEditingController();
  TextEditingController endDateTextController = TextEditingController();

  TextEditingController searchTextController = TextEditingController();

  handleSearch() async {
    AppController.to.displayCircularProgressIndicator.value = true;
    await Future.delayed(const Duration(seconds: 1));
    AppController.to.displayCircularProgressIndicator.value = false;
    handleSwitchToSearch();
  }

  handleSelectStartDate() async {
    await showMaterialDatePicker(
      title: 'Select date',
      firstDate: now,
      lastDate: DateTime(2030),
      context: Get.context!,
      selectedDate: startDate,
      onChanged: (value) => {
        startDate = value,
        if (endDate.isBefore(startDate)) {
          endDate = startDate.add(const Duration(hours: 1),),
          endDateTextController.text = endDate.toString(),
          showEndDateValidationMessage.value = true,
        },
        startDateTextController.text = value.toString(),
      },
    );
  }

  handleSelectEndDate() async {
    await showMaterialDatePicker(
      title: 'Select date',
      firstDate: startDate.add(const Duration(hours: 1),),
      lastDate: DateTime(2030),
      context: Get.context!,
      selectedDate: endDate,
      onChanged: (value) => {
        endDate = value,
        endDateTextController.text = value.toString(),
      },
    );
  }

  handleSwitchToDetail(int i) {
    state.value = DashboardViewState.detail;
    isCreate.value = false;
    isSearch.value = false;
    isDetail.value = true;

    detailPageIndex.value = i;
  }

  handleAddNewAction() async {
    AppController.to.displayCircularProgressIndicator.value = true;
    await Future.delayed(const Duration(seconds: 1));
    AppController.to.displayCircularProgressIndicator.value = false;
    length.value += 1;
    handleSwitchToSearch();

  }

  handleSwitchToSearch() {
    state.value = DashboardViewState.detail;
    isCreate.value = false;
    isSearch.value = true;
    isDetail.value = false;
  }

  handleSwitchToCreate() {
    state.value = DashboardViewState.detail;
    isCreate.value = true;
    isSearch.value = false;
    isDetail.value = false;
  }


}


