# intellij-hidpi-profiles

[![Donate][donate img]][donate]
[![Version][version img]][jetbrains]
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
    1. Default system font size and type;  
    2. Editor font size and type;  
    3. Console font size and type.  
        
Then go to **Settings > Appearance & Behavior > HDPI profiles > Add current profile** and save your profile.

![create profile](/_preview/create_profile.gif)

### 2. Switch between profiles

Toggle between profiles in main menu "HIDPI profiles"

![switch profile](/_preview/switch_profile.gif)

[travis]:https://travis-ci.org/mskonovalov/intellij-hidpi-profiles
[travis img]:https://travis-ci.org/mskonovalov/intellij-hidpi-profiles.svg?branch=2017.1
[codacy]:https://www.codacy.com/gh/mskonovalov/intellij-hidpi-profiles/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=mskonovalov/intellij-hidpi-profiles&amp;utm_campaign=Badge_Grade
[codacy img]:https://app.codacy.com/project/badge/Grade/2d84ab14425f48c2a64b77e45a4b00e3
[gitter]:https://gitter.im/intellij-hidpi-profiles/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge
[gitter img]:https://badges.gitter.im/intellij-hidpi-profiles/Lobby.svg
[license]:LICENSE
[license img]:https://img.shields.io/badge/License-MIT-blue.svg
[jetbrains]:https://plugins.jetbrains.com/plugin/9541
[downloads img]:https://img.shields.io/jetbrains/plugin/d/9541-hidpi-profiles.svg
[version img]:https://img.shields.io/jetbrains/plugin/v/9541.svg
[donate]:https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=A4M96N5FM5GGY&lc=US&item_name=HIDPI+Profiles+Development&no_note=0&curency_code=USD&bn=PP-DonationsBF:btn_donateCC_LG.gif:NonHosted
[donate img]:https://img.shields.io/badge/Donate-PayPal-green.svg

## Known issues
1. Terminal font is supported but is not adjusted automatically when you switch between profiles.
According to [this thread](https://intellij-support.jetbrains.com/hc/en-us/community/posts/207004525-terminal-theme) in order to adjust font for Terminal window
you should close the Terminal window and then open again (it will be opened with font settings from active profile)
2. Font for GitToolbox is not adjustable
