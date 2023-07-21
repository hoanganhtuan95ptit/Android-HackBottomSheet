# Android-HackBottomSheet

<img src="https://raw.githubusercontent.com/hoanganhtuan95ptit/Android-HackBottomSheet/main/demo.gif" width="20%">

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
