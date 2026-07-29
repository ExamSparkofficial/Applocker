package com.antigravity.applocker.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
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
public final class VaultMediaDao_Impl implements VaultMediaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VaultMediaEntity> __insertionAdapterOfVaultMediaEntity;

  private final EntityDeletionOrUpdateAdapter<VaultMediaEntity> __deletionAdapterOfVaultMediaEntity;

  public VaultMediaDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVaultMediaEntity = new EntityInsertionAdapter<VaultMediaEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `vault_media` (`id`,`originalFileName`,`mimeType`,`encryptedFilePath`,`encryptedThumbnailPath`,`dateAdded`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VaultMediaEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getOriginalFileName());
        statement.bindString(3, entity.getMimeType());
        statement.bindString(4, entity.getEncryptedFilePath());
        statement.bindString(5, entity.getEncryptedThumbnailPath());
        statement.bindLong(6, entity.getDateAdded());
      }
    };
    this.__deletionAdapterOfVaultMediaEntity = new EntityDeletionOrUpdateAdapter<VaultMediaEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `vault_media` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VaultMediaEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
  }

  @Override
  public Object insertMedia(final VaultMediaEntity media,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVaultMediaEntity.insert(media);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteMedia(final VaultMediaEntity media,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfVaultMediaEntity.handle(media);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<VaultMediaEntity>> getAllMedia() {
    final String _sql = "SELECT * FROM vault_media ORDER BY dateAdded DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"vault_media"}, new Callable<List<VaultMediaEntity>>() {
      @Override
      @NonNull
      public List<VaultMediaEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfOriginalFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "originalFileName");
          final int _cursorIndexOfMimeType = CursorUtil.getColumnIndexOrThrow(_cursor, "mimeType");
          final int _cursorIndexOfEncryptedFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptedFilePath");
          final int _cursorIndexOfEncryptedThumbnailPath = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptedThumbnailPath");
          final int _cursorIndexOfDateAdded = CursorUtil.getColumnIndexOrThrow(_cursor, "dateAdded");
          final List<VaultMediaEntity> _result = new ArrayList<VaultMediaEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VaultMediaEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpOriginalFileName;
            _tmpOriginalFileName = _cursor.getString(_cursorIndexOfOriginalFileName);
            final String _tmpMimeType;
            _tmpMimeType = _cursor.getString(_cursorIndexOfMimeType);
            final String _tmpEncryptedFilePath;
            _tmpEncryptedFilePath = _cursor.getString(_cursorIndexOfEncryptedFilePath);
            final String _tmpEncryptedThumbnailPath;
            _tmpEncryptedThumbnailPath = _cursor.getString(_cursorIndexOfEncryptedThumbnailPath);
            final long _tmpDateAdded;
            _tmpDateAdded = _cursor.getLong(_cursorIndexOfDateAdded);
            _item = new VaultMediaEntity(_tmpId,_tmpOriginalFileName,_tmpMimeType,_tmpEncryptedFilePath,_tmpEncryptedThumbnailPath,_tmpDateAdded);
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
  public Object getMediaById(final String id,
      final Continuation<? super VaultMediaEntity> $completion) {
    final String _sql = "SELECT * FROM vault_media WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<VaultMediaEntity>() {
      @Override
      @Nullable
      public VaultMediaEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfOriginalFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "originalFileName");
          final int _cursorIndexOfMimeType = CursorUtil.getColumnIndexOrThrow(_cursor, "mimeType");
          final int _cursorIndexOfEncryptedFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptedFilePath");
          final int _cursorIndexOfEncryptedThumbnailPath = CursorUtil.getColumnIndexOrThrow(_cursor, "encryptedThumbnailPath");
          final int _cursorIndexOfDateAdded = CursorUtil.getColumnIndexOrThrow(_cursor, "dateAdded");
          final VaultMediaEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpOriginalFileName;
            _tmpOriginalFileName = _cursor.getString(_cursorIndexOfOriginalFileName);
            final String _tmpMimeType;
            _tmpMimeType = _cursor.getString(_cursorIndexOfMimeType);
            final String _tmpEncryptedFilePath;
            _tmpEncryptedFilePath = _cursor.getString(_cursorIndexOfEncryptedFilePath);
            final String _tmpEncryptedThumbnailPath;
            _tmpEncryptedThumbnailPath = _cursor.getString(_cursorIndexOfEncryptedThumbnailPath);
            final long _tmpDateAdded;
            _tmpDateAdded = _cursor.getLong(_cursorIndexOfDateAdded);
            _result = new VaultMediaEntity(_tmpId,_tmpOriginalFileName,_tmpMimeType,_tmpEncryptedFilePath,_tmpEncryptedThumbnailPath,_tmpDateAdded);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
