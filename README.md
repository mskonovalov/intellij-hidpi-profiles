# intellij-hidpi-profiles

[![Download on https://plugins.jetbrains.com/plugin][version img]][jetbrains]
[![Download on https://plugins.jetbrains.com/plugin][downloads img]][jetbrains]
[![Build Status][travis img]][travis]
[![Codacy Badge][codacy img]][codacy]
[![Join the chat at https://gitter.im/intellij-hidpi-profiles/Lobby][gitter img]][gitter]
[![License][license img]][license]

Simple plugin for Intellij IDEA that provides possibility to create several UI profiles for different screen resolutions and easily toggle between them.
Especially this plugin will be useful for Linux users with several displays of different resolutions.

## How to use

To start using this plugin:

### 1. Create profiles

Set up all the fonts sizes in your IDE. Plugin supports:  
    1. Default system font size;  
    2. Editor font size;  
    3. Console font size.  
        
Then go to **Settings > Appearance & Behavior > HDPI profiles > Add current profile** and save your profile.

![create profile](/_preview/create_profile.gif)

### 2. Switch between profiles

Toggle between profiles in main menu "HIDPI profiles"

![switch profile](/_preview/switch_profile.gif)

[travis]:https://travis-ci.org/mskonovalov/intellij-hidpi-profiles
[travis img]:https://travis-ci.org/mskonovalov/intellij-hidpi-profiles.svg?branch=2017.1
[codacy]:https://www.codacy.com/app/mskonovalov/intellij-hidpi-profiles?utm_source=github.com&utm_medium=referral&utm_content=mskonovalov/intellij-hidpi-profiles&utm_campaign=badger
[codacy img]:https://api.codacy.com/project/badge/Grade/2e383d972ce14195897fda7215dad868
[gitter]:https://gitter.im/intellij-hidpi-profiles/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge
[gitter img]:https://badges.gitter.im/intellij-hidpi-profiles/Lobby.svg
[license]:LICENSE
[license img]:https://img.shields.io/badge/License-MIT-blue.svg
[jetbrains]:https://plugins.jetbrains.com/plugin/9541
[downloads img]:https://img.shields.io/jetbrains/plugin/d/9541-hidpi-profiles.svg
[version img]:https://img.shields.io/jetbrains/plugin/v/9541.svg

## Known issues
1. Terminal font
Terminal font is supported in Profiles. But it is not adjusted automatically when you switch between profiles.
According to [this thread](https://intellij-support.jetbrains.com/hc/en-us/community/posts/207004525-terminal-theme) in order to adjust font for Terminal window
you should close the Terminal window and then open again (it will be opened with font settings from active profile)
2. Font for GitToolbox is not adjustable
