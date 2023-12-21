@echo off
"C:\\Users\\Smk_winner\\AppData\\Local\\Android\\sdk\\cmake\\3.22.1\\bin\\cmake.exe" ^
  "-HC:\\Users\\Smk_winner\\Desktop\\BeSafeBox_Android_app\\mobile\\src\\main\\cpp" ^
  "-DCMAKE_SYSTEM_NAME=Android" ^
  "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON" ^
  "-DCMAKE_SYSTEM_VERSION=19" ^
  "-DANDROID_PLATFORM=android-19" ^
  "-DANDROID_ABI=x86_64" ^
  "-DCMAKE_ANDROID_ARCH_ABI=x86_64" ^
  "-DANDROID_NDK=C:\\Users\\Smk_winner\\AppData\\Local\\Android\\sdk\\ndk\\23.1.7779620" ^
  "-DCMAKE_ANDROID_NDK=C:\\Users\\Smk_winner\\AppData\\Local\\Android\\sdk\\ndk\\23.1.7779620" ^
  "-DCMAKE_TOOLCHAIN_FILE=C:\\Users\\Smk_winner\\AppData\\Local\\Android\\sdk\\ndk\\23.1.7779620\\build\\cmake\\android.toolchain.cmake" ^
  "-DCMAKE_MAKE_PROGRAM=C:\\Users\\Smk_winner\\AppData\\Local\\Android\\sdk\\cmake\\3.10.2.4988404\\bin\\ninja.exe" ^
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=C:\\Users\\Smk_winner\\Desktop\\BeSafeBox_Android_app\\mobile\\build\\intermediates\\cxx\\Debug\\2k3n3e94\\obj\\x86_64" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=C:\\Users\\Smk_winner\\Desktop\\BeSafeBox_Android_app\\mobile\\build\\intermediates\\cxx\\Debug\\2k3n3e94\\obj\\x86_64" ^
  "-DCMAKE_BUILD_TYPE=Debug" ^
  "-BC:\\Users\\Smk_winner\\Desktop\\BeSafeBox_Android_app\\mobile\\.cxx\\Debug\\2k3n3e94\\x86_64" ^
  -GNinja
