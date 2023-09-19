# Android-HackBottomSheet

<img src="https://raw.githubusercontent.com/hoanganhtuan95ptit/Android-HackBottomSheet/main/demo.gif" width="20%">

[<img src="https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png" width="20%">](https://play.google.com/store/apps/details?id=com.ipa.english.phonetics)

# Download
```java

allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
    
    
dependencies {
    implementation 'com.github.hoanganhtuan95ptit:Android-HackBottomSheet:$new_version'
}
```

# Use

##### You just need to change BottomSheet's dialog to CustomBottomSheetDialog. The rest CustomBottomSheetDialog will take care of itself

```java

open class BaseBottomSheetDialogFragment(contentLayoutId: Int) : BottomSheetDialogFragment(contentLayoutId) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return CustomBottomSheetDialog(requireContext(), theme)
    }
}
```

This library may not really be optimal, but I look forward to receiving public contributions to improve the library.
