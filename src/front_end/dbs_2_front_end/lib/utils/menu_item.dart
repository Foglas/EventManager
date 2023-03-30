
import 'package:dbs_2_front_end/pages/dashboard_page.dart';
import 'package:dbs_2_front_end/pages/login_page.dart';
import 'package:dbs_2_front_end/pages/profile_page.dart';
import 'package:flutter/material.dart';

import '../pages/about_page.dart';

enum MenuItem {
  events,
  profile,
  login,
  about,
  ;

  String getTitle() {
    switch (this) {
      case MenuItem.events:
        return "Events";
      case MenuItem.profile:
        return "User profile";
      case MenuItem.login:
        return "Login page";
      case MenuItem.about:
        return "About page";
    }
  }

  String getMenuItemDisplayName() {
    switch (this) {
      case MenuItem.events:
        return "Events";
      case MenuItem.profile:
        return "Profile";
      case MenuItem.login:
        return "Login";
      case MenuItem.about:
        return "About";
    }
  }

  Widget getPage() {
    switch (this) {
      case MenuItem.events:
        return const DashboardPage();
      case MenuItem.profile:
        return const ProfilePage();
      case MenuItem.login:
        return const LoginPage();
      case MenuItem.about:
        return const AboutPage();
    }
  }
}