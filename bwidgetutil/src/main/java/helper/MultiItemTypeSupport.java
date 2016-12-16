package helper;

/**
 * Created by ZhaoJie1234 on 2016/12/16.
 */
public interface MultiItemTypeSupport<T> {
    int getLayoutId(int positon, T t);
    int getViewTypeCount();
    int getItemViewType(int position, T t);
}
