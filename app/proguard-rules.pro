# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# ==========================================
# 通用设置
# ==========================================
# 压缩优化算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
# 迭代优化次数
-optimizationpasses 5
# 禁止优化
-dontoptimize
# 禁止缩减代码
-dontshrink
# 禁止多样化类名
-dontusemixedcaseclassnames
# 禁止混淆公共LIB类
-dontskipnonpubliclibraryclasses
# 禁止预先验证
-dontpreverify
# 忽略警告信息
-ignorewarnings
# 输出详细LOG
-verbose
# 保持@JavascriptInterface annotations 不被混淆掉
-keepattributes *Annotation*

# 禁止混淆类
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.app.Activity
-keep class com.app.view.**

# 禁止混淆本地方法
-keepclasseswithmembernames class * {
    native <methods>;
}

# 禁止混淆枚举类型
-keepclassmembers,allowoptimization enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 禁止混淆初始化方法
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 禁止混淆Parcelable对象
-keepclassmembers class * implements android.os.Parcelable {
    static android.os.Parcelable$Creator CREATOR;
}

# 禁止混淆Serializable对象
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#本地方法
-keep class com.app.source.response.** {*;}
-keep class com.app.framework.net.base.** {*;}
-keep public class com.app.framework.R$*{
    public static final int *;
}

-dontwarn  org.eclipse.jdt.annotation.**
-dontwarn  c.t.**