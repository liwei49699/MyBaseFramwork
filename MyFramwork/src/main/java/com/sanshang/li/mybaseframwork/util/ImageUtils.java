package com.sanshang.li.mybaseframwork.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by li on 2018/6/20.
 * WeChat 18571658038
 * author LiWei
 */

public class ImageUtils {

    /**
     * 压缩图片
     * @param filePath
     * @param targetPath
     * @param quality  压缩质量
     * @return
     */
    public static String compressImage(String filePath, String targetPath, int quality)  {

        Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片
        int degree = readPictureDegree(filePath);//获取相片拍摄角度

        if(degree!=0){//旋转照片角度，防止头像横着显示
            bm=rotateBitmap(bm,degree);
        }

        File outputFile=new File(targetPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
                //outputFile.createNewFile();
            }else{
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
        }catch (Exception e){
            e.printStackTrace();
        }
        return outputFile.getPath();
    }

    /**
     * 获取照片角度
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转照片
     * @param bitmap
     * @param degree
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap,int degree) {

        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degree);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * 压缩图像
     * @param path
     * @return
     */
    public static String compressionImage(Context context,String path) {
        File f = new File(path);
        if (f.exists()) {
            String extensionName = FileUtils.getExtensionName(f.getName());
            String fileName = String.valueOf(System.currentTimeMillis());
            String newName = fileName+"."+extensionName;
            String cache = FileUtils.getCacheDir(context)+"temp_image/";
            FileUtils.checkDir(cache);
            String newPath = cache+ newName;
            FileInputStream fis;
            try {
                fis = new FileInputStream(f);
                //返回的实际可读字节数,也就是总大小 但是需要注意的是。java虚拟机中的字符是Unicode编码
                long size= fis.available();
                fis.close();
                fis=null;
                if(size>204800L){   //200KB以内不压缩
                    Bitmap bm = getSmallBitmap(path,480,800);
                    FileOutputStream fos = new FileOutputStream(new File(newPath));
                    bm.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                    if(fos!=null){
                        fos.close();
                        fos=null;
                    }
                    bm.recycle();
                    bm=null;

                    return newPath;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    public static Bitmap getSmallBitmap(String filePath) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比 现在主流手机比较多是480*800分辨率，所以高和宽我们设置为
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }
    /**
     * 根据路径获得图片并压缩返回bitmap用于显示
     * @param filePath
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath,int width,int height) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 将uri转换为绝对路径
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
