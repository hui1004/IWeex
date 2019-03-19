package com.weex.iweex.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class PageViewAdapter extends PagerAdapter{
    private List<View> views;
    public PageViewAdapter(List<View> views){
       this.views=views;
    }
    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public int getItemPosition(Object object) {
//        View view = (View)object;
//        int currentPage =view.getContext().getCurrentPagerIdx(); // Get current page index
//        if(currentPage == (Integer)view.getTag()){
//            return POSITION_NONE;
//        }else{
//            return POSITION_UNCHANGED;
//        }
      return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // return super.instantiateItem(container, position);
        View view=views.get(position);
        view.setTag(position);
        container.addView(view);
        return  view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView(views.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //return super.getPageTitle(position);
        return "标题"+position;
    }
}
