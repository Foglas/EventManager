import 'package:flutter/material.dart';

import 'color_schemes.dart';

TextTheme appMaterialLightPrimaryTextTheme = TextTheme(
  displayLarge: TextStyle(color: lightColorScheme.tertiary),
  displayMedium: TextStyle(color: lightColorScheme.tertiary),
  displaySmall: TextStyle(color: lightColorScheme.tertiary),
  headlineLarge: TextStyle(
    color: lightColorScheme.tertiary,
  ),
  headlineMedium: TextStyle(color: lightColorScheme.tertiary),
  headlineSmall: TextStyle(color: lightColorScheme.tertiary),
  bodyLarge: TextStyle(color: lightColorScheme.tertiary),
  bodyMedium: TextStyle(color: lightColorScheme.tertiary),
  bodySmall: TextStyle(color: lightColorScheme.tertiary),
  labelLarge: TextStyle(color: lightColorScheme.tertiary),
  labelMedium: TextStyle(color: lightColorScheme.tertiary),
  labelSmall: TextStyle(color: lightColorScheme.tertiary),
);

TextTheme appMateriaDarkTextTheme = const TextTheme(
  displayLarge: TextStyle(fontStyle: FontStyle.normal, fontWeight: FontWeight.w900,),
  displayMedium: TextStyle(fontStyle: FontStyle.normal, fontWeight: FontWeight.w800,),
  displaySmall: TextStyle(fontStyle: FontStyle.normal, fontWeight: FontWeight.w600,),
  headlineLarge: TextStyle(
    fontStyle: FontStyle.normal, fontWeight: FontWeight.w900,
  ),
  headlineMedium: TextStyle(fontStyle: FontStyle.normal, fontWeight: FontWeight.w800,),
  headlineSmall: TextStyle(fontStyle: FontStyle.normal, fontWeight: FontWeight.w600,),
  bodyLarge: TextStyle(fontStyle: FontStyle.normal,),
  bodyMedium: TextStyle(fontStyle: FontStyle.normal),
  bodySmall: TextStyle(fontStyle: FontStyle.normal),
  labelLarge: TextStyle(fontStyle: FontStyle.normal),
  labelMedium: TextStyle(fontStyle: FontStyle.normal),
  labelSmall: TextStyle(fontStyle: FontStyle.normal),
);