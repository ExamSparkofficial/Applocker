package com.antigravity.applocker.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.antigravity.applocker.data.local.entity.IntruderLogEntity;
import com.antigravity.applocker.data.local.entity.LockedAppEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppLockerDao_Impl implements AppLockerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LockedAppEntity> __insertionAdapterOfLockedAppEntity;

  private final EntityInsertionAdapter<IntruderLogEntity> __insertionAdapterOfIntruderLogEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteLockedApp;

  public AppLockerDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLockedAppEntity = new EntityInsertionAdapter<LockedAppEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `locked_apps` (`packageName`,`isLocked`) VALUES (?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LockedAppEntity entity) {
        statement.bindString(1, entity.getPackageName());
        final int _tmp = entity.isLocked() ? 1 : 0;
        statement.bindLong(2, _tmp);
      }
    };
    this.__insertionAdapterOfIntruderLogEntity = new EntityInsertionAdapter<IntruderLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `intruder_logs` (`id`,`timestamp`,`packageName`,`photoPath`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final IntruderLogEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getTimestamp());
        statement.bindString(3, entity.getPackageName());
        if (entity.getPhotoPath() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPhotoPath());
        }
      }
    };
    this.__preparedStmtOfDeleteLockedApp = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM locked_apps WHERE packageName = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertLockedApp(final LockedAppEntity app,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfLockedAppEntity.insert(app);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertIntruderLog(final IntruderLogEntity log,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfIntruderLogEntity.insert(log);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteLockedApp(final String packageName,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteLockedApp.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, packageName);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteLockedApp.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<LockedAppEntity>> getAllLockedApps() {
    final String _sql = "SELECT * FROM locked_apps";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"locked_apps"}, new Callable<List<LockedAppEntity>>() {
      @Override
      @NonNull
      public List<LockedAppEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfIsLocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isLocked");
          final List<LockedAppEntity> _result = new ArrayList<LockedAppEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LockedAppEntity _item;
            final String _tmpPackageName;
            _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            final boolean _tmpIsLocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsLocked);
            _tmpIsLocked = _tmp != 0;
            _item = new LockedAppEntity(_tmpPackageName,_tmpIsLocked);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<IntruderLogEntity>> getIntruderLogs() {
    final String _sql = "SELECT * FROM intruder_logs ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"intruder_logs"}, new Callable<List<IntruderLogEntity>>() {
      @Override
      @NonNull
      public List<IntruderLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "photoPath");
          final List<IntruderLogEntity> _result = new ArrayList<IntruderLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final IntruderLogEntity _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpPackageName;
            _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            final String _tmpPhotoPath;
            if (_cursor.isNull(_cursorIndexOfPhotoPath)) {
              _tmpPhotoPath = null;
            } else {
              _tmpPhotoPath = _cursor.getString(_cursorIndexOfPhotoPath);
            }
            _item = new IntruderLogEntity(_tmpId,_tmpTimestamp,_tmpPackageName,_tmpPhotoPath);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
