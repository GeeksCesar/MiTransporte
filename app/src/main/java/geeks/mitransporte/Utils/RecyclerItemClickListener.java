package geeks.mitransporte.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener{

    private OnItemClickListener itemClickListener;
    GestureDetector gestureDetector;

    public interface OnItemClickListener{
        public void OnItemClick(View view, int position);
    }

    public RecyclerItemClickListener(Context context, OnItemClickListener listener){
        itemClickListener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent event) {
        View childView = rv.findChildViewUnder(event.getX(), event.getY());

        if (childView != null && itemClickListener != null && gestureDetector.onTouchEvent(event)){
            itemClickListener.OnItemClick(childView, rv.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


}
