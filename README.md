[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ComboView-green.svg?style=true)](https://android-arsenal.com/details/1/3338)

# ComboView
### A view of clicking effect by combo action


# UI Effect

![combo1](./combo1.gif)

# Final Effect

![combo2](./combo2.gif)


# How To Use
```java

  ComboView.Params params = ComboView.Params.create()
 
                 //Must
                 .cornerRadius(dimen(R.dimen.cb_dimen_25), dimen(R.dimen.cb_dimen_52))// Following three to***** values must be the same can morph to circle
                 .width(dimen(R.dimen.cb_dimen_70), dimen(R.dimen.cb_dimen_52))
                 .height(dimen(R.dimen.cb_dimen_38), dimen(R.dimen.cb_dimen_52))
                 .morphDuration(300)
                 .text("赠送", "连击")
 
                 //Option -- and values below is default
                 .color(color(R.color.cb_color_blue), color(R.color.cb_color_blue))
                 .colorPressed(color(R.color.cb_color_blue_dark), color(R.color.cb_color_blue_dark))
                 .strokeWidth(dimen(R.dimen.cb_dimen_1), dimen(R.dimen.cb_dimen_1))
                 .strokeColor(color(R.color.cb_color_blue), color(R.color.cb_color_blue))
                 .circleDuration(5000)
                 .rippleDuration(300)
                 .padding(dimen(R.dimen.cb_dimen_3))
                 .textSize(16)
                 .textColor(color(R.color.cb_color_white))
                 .comboClickListener(new ComboView.ComboClickListener() {
                     @Override
                     public void onComboClick() {
                         Log.e("MainActivity","onComboClick");
                     }
 
                     @Override
                     public void onNormalClick() {
                         Log.e("MainActivity","onNormalClick");
                     }
                 });
 
         comboView.settingMorphParams(params);

```

## Integrate With Special UI

```xml

<!--layout_combo_view must overlay other UI,and just adjust ComboView's layout_width,layout_height,
marin*** something to locate where actually ComboView need be placed-->

<RelativeLayout
        android:id="@+id/layout_combo_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@android:color/transparent">

        <com.bruce.library.ComboView
            android:layout_width="220dp"
            android:layout_height="220dp"
            android:id="@+id/combo_view"
            android:layout_alignParentRight="true"
            android:layout_alignParentBottom="true"
            android:layout_marginRight="-65dp"
            android:layout_marginBottom="-80dp"
            />

    </RelativeLayout>

```

### Dependency

```groovy

 compile 'com.brucetoo.comboview:comboview:1.0.0'

```

OR

```groovy

repositories {
    maven {
        url "https://jitpack.io"
    }
}


dependencies {
    compile 'com.github.brucetoo:ComboView:{latest-commit-tag}'
}

```
  

### Thanks [dmytrodanylyk](https://github.com/dmytrodanylyk) and his [android-morphing-button](https://github.com/dmytrodanylyk/android-morphing-button)



------

## License

Copyright 2016 Bruce too

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

See [LICENSE](LICENSE) file for details.
