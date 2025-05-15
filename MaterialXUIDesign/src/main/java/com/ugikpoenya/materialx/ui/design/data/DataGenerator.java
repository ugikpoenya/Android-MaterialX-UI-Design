package com.ugikpoenya.materialx.ui.design.data;

import android.content.Context;
import android.content.res.TypedArray;

import com.ugikpoenya.materialx.ui.design.model.Image;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {
    public static List<Image> getListImage(Context ctx, int intro_image, int intro_title, int intro_content) {
        List<Image> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(intro_image);
        String title[] = ctx.getResources().getStringArray(intro_title);
        String content[] = ctx.getResources().getStringArray(intro_content);
        for (int i = 0; i < drw_arr.length(); i++) {
            Image obj = new Image();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.name = title[i];
            obj.brief = content[i];
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        return items;
    }

    public static List<Image> getListImage(Context ctx, int intro_image) {
        List<Image> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(intro_image);
        for (int i = 0; i < drw_arr.length(); i++) {
            Image obj = new Image();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        return items;
    }
}
