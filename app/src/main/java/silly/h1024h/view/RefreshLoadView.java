package silly.h1024h.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by SillySnnall on 2018/4/25.
 */

public class RefreshLoadView extends SwipeRefreshLayout {
    private OnLoadListener mOnLoadListener;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private int oldItemCount = 0;
    private Context mContext;

    public final static int LAYOUTMANAGER_VERTICAL = RecyclerView.VERTICAL;
    public final static int LAYOUTMANAGER_HORIZONTAL = RecyclerView.HORIZONTAL;

    public RefreshLoadView(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public RefreshLoadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void init(RecyclerView recyclerView, RecyclerView.Adapter adapter, int spanCount, int orientation, boolean isWaterfall) throws Exception {
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        if (spanCount == 0) {
            throw new Exception("布局列数不能为0");
        }
        if (spanCount == 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, orientation, false));
        } else {
            if (isWaterfall) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, orientation);
                staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, spanCount, orientation, false));
            }
        }
        recyclerView.setAdapter(adapter);
    }

    public void init(RecyclerView recyclerView, RecyclerView.Adapter adapter) throws Exception {
        init(recyclerView, adapter, 1, LAYOUTMANAGER_VERTICAL, false);
    }

    public void init(RecyclerView recyclerView, RecyclerView.Adapter adapter, int spanCount) throws Exception {
        init(recyclerView, adapter, spanCount, LAYOUTMANAGER_VERTICAL, false);
    }

    public void addOnLoadListener(OnLoadListener onLoadListener) {
        this.mOnLoadListener = onLoadListener;
        if (recyclerView != null) recyclerView.addOnScrollListener(onScrollListener);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                layoutManager.invalidateSpanAssignments();
            }
            if (!recyclerView.canScrollVertically(1) && RecyclerView.SCROLL_STATE_IDLE == newState && mOnLoadListener != null) {
                mOnLoadListener.onLoad();
            }
        }
    };

    public interface OnLoadListener {
        void onLoad();
    }

    public void removeOnLoadListener() {
        if (recyclerView != null) recyclerView.removeOnScrollListener(onScrollListener);
    }

    public void loadComplete() {
        if (adapter != null) {
            adapter.notifyItemRangeChanged(oldItemCount, adapter.getItemCount() - 1);
            oldItemCount = adapter.getItemCount();
        }
    }

    public void refreshComplete() {
        if (adapter != null) adapter.notifyDataSetChanged();
        setRefreshing(false);
    }

    public <T> T getAdapter() {
        return (T) adapter;
    }
}
