# CS2340 Project Repository for Team 48 
[![Build Status](https://circleci.com/gh/guyfleeman/CS2340.svg?&style=shield)](https://circleci.com/gh/guyfleeman/CS2340) 
[![Coverage Status](https://coveralls.io/repos/github/guyfleeman/CS2340/badge.svg?branch=master)](https://coveralls.io/github/guyfleeman/CS2340?branch=master)

## Build Prerequisites
Gradle must be installed either through your *nix package manager or by manual download. Gradle version 2.10 or greater is required.

## Building the Project
#### Compile Java Source
`$ gradle compileJava`

#### Run Tests
Tests will fail if code is not written to Sun Style standards.

`$ gradle test`

#### JFX Jar
You can test the embedded JavaFX jar.

`$ gradle jfxJar`

#### JFX Native
You can build a native deployment package for your platform (bundled with the test JVM for version). Note: building a native package has to compress the JVM and will take a few minutes even on a powerful machine.

`$ gradle jfxNative`

#### Checkstyle

`$ gradle checkstyleMain`

## CircleCI
We are using CircleCI for continuous integration. CircleCI will validate
checkstyle, unit test, and jfxJar builds for all non-deployment scenarios.
For deployment scenarios, the standard checks are run and the bundling of
a native package is also confirmed.
