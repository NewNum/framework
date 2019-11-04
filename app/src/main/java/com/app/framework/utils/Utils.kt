package com.app.framework.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.DisplayMetrics
import android.view.WindowManager
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern
import java.math.BigDecimal


/**
 * 格式化金额
 * @param double 金额
 * @param number 小数位数
 *
 * @return 格式化后的金额字符串
 */
fun formatMoney(double: Double?, number: Int = 2): String? {
    if (double == null) return null
    if (number <= 0) {
        return double.toInt().toString()
    } else
        return DecimalFormat("#0.${"0" * number}").format(double)
}

/**
 * 字符串乘法
 * @param count 数量
 *
 * @return 返回
 */
operator fun String.times(count: Int): String {
    val sb = StringBuffer()
    for (i in 1..count) {
        sb.append(this)
    }
    return sb.toString()
}

fun blurPhone(mobile: String): String {
    return mobile.replace(Regex("(\\d{3})\\d{4}(\\d{4})"), "$1****$2")
}

fun isPhoneNumber(phone: String): Boolean {
    return Pattern.matches("^1\\d{10}\$", phone)
}

/**
 * 获取屏幕的宽度（单位：px）
 *
 * @return 屏幕宽px
 */
fun getScreenWidth(context: Context?): Int {
    if (context == null) return 0
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()// 创建了一张白纸
    windowManager.defaultDisplay.getMetrics(dm)// 给白纸设置宽高
    return dm.widthPixels
}

/**
 * 获取屏幕的高度（单位：px）
 *
 * @return 屏幕高px
 */
fun getScreenHeight(context: Context?): Int {
    if (context == null) return 0
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()// 创建了一张白纸
    windowManager.defaultDisplay.getMetrics(dm)// 给白纸设置宽高
    return dm.heightPixels
}

/**
 * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
 */
fun dip2px(context: Context?, dpValue: Float): Int {
    if (context == null) return 0
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun px2dip(context: Context?, pxValue: Float): Int {
    if (context == null) return 0
    val scale = context.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * 将sp值转换为px值，保证文字大小不变
 * @param spValue
 * @return
 */
fun sp2px(context: Context?, spValue: Float): Int {
    if (context == null) return 0
    val fontScale = context.resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}

/**
 * 将px值转换为sp值，保证文字大小不变
 * @param pxValue
 * @return
 */
fun px2sp(context: Context?, pxValue: Float): Int {
    if (context == null) return 0
    val fontScale = context.resources.displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}

/**
 * 复制内容到剪切板
 *
 * @param copyStr
 * @return
 */
fun copyToClipboard(context: Context, copyStr: String?): Boolean {
    return try {
        //获取剪贴板管理器
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 创建普通字符型ClipData
        val mClipData = ClipData.newPlainText("Label", copyStr)
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData)
        true
    } catch (e: Exception) {
        false
    }
}

fun callPhone(context: Context, phone: String) {
    context.startActivity(Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:$phone")))
}

fun isAppInstalled(context: Context, packageName: String): Boolean {
    val packageManager = context.packageManager
    //获取系统中安装的应用包的信息
    val listPackageInfo = packageManager.getInstalledPackages(0)
    for (i in listPackageInfo.indices) {
        if (listPackageInfo[i].packageName.equals(packageName, ignoreCase = true)) {
            return true
        }
    }
    return false
}


fun convertToRequestBody(param: String): RequestBody {
    return RequestBody.create(MediaType.parse("text/plain"), param)
}

fun convertToRequestBody(params: Map<String, String>): Map<String, RequestBody> {
    val map = mutableMapOf<String, RequestBody>()
    params.keys.forEach {
        map[it] = RequestBody.create(MediaType.parse("text/plain"), params[it])
    }
    return map
}


fun fileToMultipartBodyParts(param: String, file: File): MultipartBody.Part {
    val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
    return MultipartBody.Part.createFormData(param, file.name, requestBody)
}

fun fileToMultipartBodyParts(params: Map<String, File>): List<MultipartBody.Part> {
    val list = mutableListOf<MultipartBody.Part>()
    params.keys.forEach {
        val file = params[it]
        val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
        list.add(MultipartBody.Part.createFormData(it, file?.name, requestBody))
    }
    return list
}


fun isSameDay(date1: Date, date2: Date): Boolean {
    val cal1 = Calendar.getInstance()
    cal1.setTime(date1)
    val cal2 = Calendar.getInstance()
    cal2.setTime(date2)
    return isSameDay(cal1, cal2)
}

fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6)
}

fun cleanInternalCache(context: Context): Boolean {
    return deleteFilesByDirectory(context.externalCacheDir)
}


/**
 * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
 *
 * @param directory
 */
fun deleteFilesByDirectory(directory: File): Boolean {
    if (directory.exists()) {
        if (directory.isDirectory) {
            for (item in directory.listFiles()) {
                if (!deleteFilesByDirectory(item)) {
                    return false
                }
            }
        } else {
            if (!directory.delete()) {
                return false
            }
        }
    }

    return true
}

/**
 * 获取文件夹大小
 * @param file File实例
 * @return long
 */
fun getFolderSize(file: File): Long {
    var size: Long = 0
    try {
        val fileList = file.listFiles()
        for (i in fileList.indices) {
            size += if (fileList[i].isDirectory) {
                getFolderSize(fileList[i])

            } else {
                fileList[i].length()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return size
}

/**
 * 格式化单位
 * @param size
 * @return
 */
fun formatSize(size: Double): String {
    val kiloByte = size / 1024
    if (kiloByte < 1) {
        return size.toString() + "B"
    }

    val megaByte = kiloByte / 1024
    if (megaByte < 1) {
        val result1 = BigDecimal(java.lang.Double.toString(kiloByte))
        return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB"
    }

    val gigaByte = megaByte / 1024
    if (gigaByte < 1) {
        val result2 = BigDecimal(java.lang.Double.toString(megaByte))
        return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB"
    }

    val teraBytes = gigaByte / 1024
    if (teraBytes < 1) {
        val result3 = BigDecimal(java.lang.Double.toString(gigaByte))
        return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB"
    }
    val result4 = BigDecimal(teraBytes)
    return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
}