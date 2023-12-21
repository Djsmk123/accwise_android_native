@echo off
"C:\\Users\\Smk_winner\\AppData\\Local\\Android\\sdk\\cmake\\3.22.1\\bin\\cmake.exe" ^
  "-HC:\\Users\\Smk_winner\\Desktop\\BeSafeBox_Android_app\\mobile" ^
  "-DCMAKE_SYSTEM_NAME=Android" ^
  "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON" ^
  "-DCMAKE_SYSTEM_VERSION=19" ^
  "-DANDROID_PLATFORM=android-19" ^
  "-DANDROID_ABI=x86" ^
  "-DCMAKE_ANDROID_ARCH_ABI=x86" ^
  "-DANDROID_NDK=C:\\Users\\Smk_winner\\AppData\\Local\\Android\\sdk\\ndk\\25.1.8937393" ^
  "-DCMAKE_ANDROID_NDK=C:\\Users\\Smk_winner\\AppData\\Local\\Android\\sdk\\ndk\\25.1.8937393" ^
  "-DCMAKE_TOOLCHAIN_FILE=C:\\Users\\Smk_winner\\AppData\\Local\\Android\\sdk\\ndk\\25.1.8937393\\build\\cmake\\android.toolchain.cmake" ^
  "-DCMAKE_MAKE_PROGRAM=C:\\Users\\Smk_winner\\AppData\\Local\\Android\\sdk\\cmake\\3.10.2.4988404\\bin\\ninja.exe" ^
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=C:\\Users\\Smk_winner\\Desktop\\BeSafeBox_Android_app\\mobile\\build\\intermediates\\cxx\\Debug\\4a59a4g6\\obj\\x86" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=C:\\Users\\Smk_winner\\Desktop\\BeSafeBox_Android_app\\mobile\\build\\intermediates\\cxx\\Debug\\4a59a4g6\\obj\\x86" ^
  "-DCMAKE_BUILD_TYPE=Debug" ^
  "-BC:\\Users\\Smk_winner\\Desktop\\BeSafeBox_Android_app\\mobile\\.cxx\\Debug\\4a59a4g6\\x86" ^
  -GNinja
