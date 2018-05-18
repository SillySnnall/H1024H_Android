package silly.h1024h.base.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;
import java.util.List;

import silly.h1024h.db.DatabaseHelper;

/**
 * 内容：GC DaoBase类
 * 作者：StringBOX
 * 时间：2017/3/10 15:29
 */

public class BaseDao<Entity> {
    public static final String TAG = "BaseDao";
    private DatabaseHelper helper;
    private Dao<Entity, Integer> mDao;

    public BaseDao(Context context, Class clasz) {
        try {
            helper = DatabaseHelper.getHelper(context);
            mDao = helper.getDao(clasz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Dao对象
     *
     * @return
     */
    public Dao<Entity, Integer> getDao() {
        return mDao;
    }

    /**
     * 添加或更新数据到数据库（集合）
     */
    public void createOrUpdate(List<Entity> entityList) {
        try {
            if (entityList == null) {
                Log.d(TAG,"create:添加或更新数据失败 entityList = null");
                return;
            }
            if (entityList.size() == 0) {
                 Log.d(TAG, "create:添加或更新数据失败 entityList.size() = 0");
                return;
            }

            for (Entity entity : entityList) {
                mDao.createOrUpdate(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
             Log.d(TAG, "create:添加或更新数据失败" + e);
        }
    }

    /**
     * 添加或更新数据到数据库（对象）
     */
    public void createOrUpdate(Entity entity) {
        try {
            if (entity == null) {
                 Log.d(TAG, "create:添加或更新数据失败 entity = null");
                return;
            }
            mDao.createOrUpdate(entity);
        } catch (Exception e) {
            e.printStackTrace();
             Log.d(TAG, "create:添加或更新数据失败" + e);
        }
    }

    /**
     * 从数据库查询全部数据
     */
    public List<Entity> queryForAll() {
        try {
            return mDao.queryForAll();
        } catch (Exception e) {
            e.printStackTrace();
             Log.d(TAG, "queryForAll:查询数据失败" + e);
            return new ArrayList<>();
        }
    }

    /**
     * 从数据库删除全部数据
     */
    public void deleteAll() {
        try {
            mDao.delete(mDao.queryForAll());
        } catch (Exception e) {
            e.printStackTrace();
             Log.d(TAG, "deleteAll:删除数据失败" + e);
        }
    }

    /**
     * 从数据库删除数据
     */
    public void delete(Entity entity) {
        try {
            mDao.delete(entity);
        } catch (Exception e) {
            e.printStackTrace();
             Log.d(TAG, "delete:删除数据失败" + e);
        }
    }
}
