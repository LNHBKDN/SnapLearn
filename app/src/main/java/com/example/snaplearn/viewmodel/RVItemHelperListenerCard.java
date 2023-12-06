package com.example.snaplearn.viewmodel;

import android.graphics.Canvas;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class RVItemHelperListenerCard extends ItemTouchHelper.SimpleCallback {
    private ItemTouchHelperListener listener;
    public RVItemHelperListenerCard(int dragDirs, int swipeDirs, ItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if(listener != null){
            listener.onSwiped(viewHolder);
        }
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        View foreGroundView = null;
        if (viewHolder != null) {
            foreGroundView = ((CardAdapter.CardViewHolder) viewHolder).foreground;
        }
        getDefaultUIUtil().onSelected(foreGroundView);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foreGroundView = ((CardAdapter.CardViewHolder) viewHolder).foreground;
        getDefaultUIUtil().onDrawOver(c,recyclerView,foreGroundView,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foreGroundView = ((CardAdapter.CardViewHolder) viewHolder).foreground;
        getDefaultUIUtil().onDraw(c,recyclerView,foreGroundView,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View foreGroundView = ((CardAdapter.CardViewHolder) viewHolder).foreground;
        getDefaultUIUtil().clearView(foreGroundView);
    }
}
