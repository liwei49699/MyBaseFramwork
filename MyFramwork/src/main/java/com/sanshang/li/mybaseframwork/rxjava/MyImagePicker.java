package com.sanshang.li.mybaseframwork.rxjava;

import com.qingmei2.rximagepicker.entity.Result;
import com.qingmei2.rximagepicker.entity.sources.Camera;
import com.qingmei2.rximagepicker.entity.sources.Gallery;

import io.reactivex.Observable;

/**
 * Created by li on 2018/6/20.
 * WeChat 18571658038
 * author LiWei
 */

public interface MyImagePicker {

    @Gallery
    //打开相册选择图片
    Observable<Result> openGallery();

    @Camera
    //打开相机拍照
    Observable<Result> openCamera();
}
