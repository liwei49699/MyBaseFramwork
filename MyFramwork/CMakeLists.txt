# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

# 指定cmake版本
cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

# 设置变量SRC_DIR为C/C++文件所在路径
set(SRC_DIR src/main/cpp/my)

#设置生成的so动态库最后输出的路径
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/../MyFramwork/src/main/jniLibs/${ANDROID_ABI})

# 向CMake添加依赖源文件或库
# 指令需传入三个参数（函数库名称、库类型、依赖源文件相对路径）
add_library( # Sets the name of the library.
             # 生成函数库的名称，即libnative-lib.so或libnative-lib.a(lib和.so/.a默认缺省)
             native-lib

             # Sets the library as a shared library.
             # 生成库类型：动态库为SHARED，静态库为STATIC
             SHARED

             # Provides a relative path to your source file(s).
             # 依赖的c/cpp文件(相对路径)
             src/main/cpp/native-lib.cpp
             ${SRC_DIR}/my.cpp)

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

# 用于定位NDK中的库
# 需传入两个参数(path变量、ndk库名称)
find_library( # Sets the name of the path variable.
              # 设置path变量的名称，这里为NDK中的日志库
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

# 指定要关联到的原生库的库
target_link_libraries( # Specifies the target library.
                       # 指定目标库，与上面指定的函数库名一致
                       native-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       # 链接的库，根据log-lib变量对应liblog.so函数库
                       ${log-lib} )

# Android系统目前支持的CPU架构
# ARMv5，ARMv7 (从2010年起)
# x86 (从2011年起)
# MIPS (从2012年起)
# ARMv8，MIPS64和x86_64 (从2014年起)
# 每一个CPU架构对应一个ABI
# CPU架构           ABI
# ARMv5   --->    armeabi
# ARMv7   --->    armeabi-v7a
# x86     --->    x86
# MIPS    --->    mips
# ARMv8   --->    arm64-v8a
# MIPS64  --->    mips64
# x86_64  --->    x86_64
# armeabi：默认选项，将创建以基于ARM* v5TE 的设备为目标的库。 具有这种目标
#   的浮点运算使用软件浮点运算。 使用此ABI（二进制接口）创建的二进制代码将可以
#   在所有 ARM*设备上运行。所以armeabi通用性很强。但是速度慢
# armeabi-v7a：创建支持基于ARM* v7 的设备的库，并将使用硬件FPU指令。
# armeabi-v7a是针对有浮点运算或高级扩展功能的arm v7 cpu。
# mips：MIPS是世界上很流行的一种RISC处理器。MIPS的意思是“无内部互锁流水级
#   的微处理器”(Microprocessor without interlocked piped stages)，其机
#   制是尽量利用软件办法避免流水线中的数据相关问题。
# x86：支持基于硬件的浮点运算的IA-32 指令集。x86是可以兼容armeabi平台运行
#   的，无论是armeabi-v7a还是armeabi，同时带来的也是性能上的损耗，另外需要
#   指出的是，打包出的x86的so，总会比armeabi平台的体积更小。
# 总结
# 如果项目只包含了 armeabi，那么在所有Android设备都可以运行；
# 如果项目只包含了 armeabi-v7a，除armeabi架构的设备外都可以运行；
# 如果项目只包含了 x86，那么armeabi架构和armeabi-v7a的Android设备是无法运行的；
# 如果同时包含了 armeabi，armeabi-v7a和x86，所有设备都可以运行，程序在运行的时候去加载不同平台对应的so，这是较为完美的一种解决方案，同时也会导致包变大。
