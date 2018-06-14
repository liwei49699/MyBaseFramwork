package com.sanshang.li.mybaseframwork.test;

import android.content.ContentValues;

/**
 * 从v2.2.x开始，没有作用了
 * @author zysuper
 *
 */
public interface IDataTransferObject {

	public ContentValues getData(String[] cols, String dateFormat);
}
