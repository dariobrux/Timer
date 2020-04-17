# Timer

This library is a simple Timer for Android, written in Kotlin.

![Watch the video](https://github.com/dariobrux/Timer/blob/master/preview.gif)

It's very simple and it works in background or in foreground. 

> The above preview is a simple Activity test that's not included into the library. The library contains only the Timer object that you can use whatever you want.

## Import using Gradle
Step 1. Add it in your root build.gradle at the end of repositories:
~~~~ gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
~~~~
Step 2. Add the dependency
~~~~ gradle
dependencies {
    implementation 'com.github.dariobrux:Timer:1.0.0'
}
~~~~

## How to use via code (Kotlin)
~~~~ kotlin
val timer: Timer = Timer()

// The duration is always in milliseconds.
timer.setDuration(10_000L)

// The timer is a daemon when it also runs when the application is destroyed. 
// In this case passing false, it works only when the application is active.
timer.setIsDaemon(false)

// The delay is always in milliseconds.
timer.setStartDelay(0L)

// Setting true, the callbacks are invoked on the main thread.
timer.setOnTimerListener(this, true)
~~~~

To start/resume, pause and stop you can call:
~~~~ kotlin
timer.start()
timer.pause()
timer.stop()
~~~~

You can invoke the callbacks by `OnTimerListener`, via `timer.setOnTimerListener(this, true)`.
* `onTimerStarted()` invoked when `timer.start()` is called. It notify that the timer has started. If you have set a startDelay, it will launch when the delay ends.
* `onTimerPaused(remainingMillis: Long)` invoked when `timer.pause()` is called. The `remainingMillis` are the milliseconds at the current paused time.
* `onTimerRun(milliseconds: Long)` invoked when the timer is running. The `milliseconds` are the elapsed milliseconds.
* `onTimerStopped()` invoked when `timer.stop()` is called.
* `onTimerEnded()` invoked when the timer ends.

## License
~~~~
Copyright 2020 Dario Bruzzese

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
~~~~
