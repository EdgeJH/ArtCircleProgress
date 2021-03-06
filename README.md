ArtCircleProgress
===============
Android Circle Progress 

![ArtCircleProgress](/ArtCircleProgress_.gif)

Gradle
------------
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
```groovy
dependencies {
     implementation 'com.github.EdgeJH:ArtCircleProgress:1.0.3'
}
```

Usage
--------
```xml
   <com.edge.artcircle.ArtCircleProgress
            android:layout_width="0dp"
            android:layout_weight="1"
            android:layout_height="wrap_content"
            app:shadowRadius="10dp"
            app:progressStartColor="#30aaee"
            app:progressEndColor="#ee6666"
            app:progress="70"
            app:textColor="#ee6666"
            app:maxProgress="100"
            app:animateLoading="true"
            app:animateDuration="2000"
            app:outMode="false"
            app:progressWidth="5dp"
            app:textSize="25dp"
            app:textShow="true"
            app:progressBackColor="#eee"
            app:circleBackgroundColor="@android:color/white"
            />
```
```java
        ArtCircleProgress artCircleProgress = new ArtCircleProgress(this);
        artCircleProgress.setProgress(30);
        artCircleProgress.setMaxProgress(50);
```


##### Properties:

* `app:shadowRadius`
* `app:progressStartColor`
* `app:progressEndColor`
* `app:progress`
* `app:maxProgress`
* `app:animateLoading`
* `app:animateDuration`
* `app:outMode`
* `app:progressWidth`
* `app:textSize`
* `app:textShow`
* `app:textColor`
* `app:progressBackColor`    
* `app:circleBackgroundColor`         

License
--------
```
Copyright 2017 EdgeJH


Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

```
