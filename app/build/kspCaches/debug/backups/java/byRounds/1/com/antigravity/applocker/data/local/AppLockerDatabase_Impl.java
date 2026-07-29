package com.antigravity.applocker.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.antigravity.applocker.data.local.dao.AppLockerDao;
import com.antigravity.applocker.data.local.dao.AppLockerDao_Impl;
import com.antigravity.applocker.data.local.dao.HiddenAppDao;
import com.antigravity.applocker.data.local.dao.HiddenAppDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppLockerDatabase_Impl extends AppLockerDatabase {
  private volatile AppLockerDao _appLockerDao;

  private volatile HiddenAppDao _hiddenAppDao;

  private volatile VaultMediaDao _vaultMediaDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `locked_apps` (`packageName` TEXT NOT NULL, `isLocked` INTEGER NOT NULL, PRIMARY KEY(`packageName`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `intruder_logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `packageName` TEXT NOT NULL, `photoPath` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `hidden_apps` (`packageName` TEXT NOT NULL, PRIMARY KEY(`packageName`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `vault_media` (`id` TEXT NOT NULL, `originalFileName` TEXT NOT NULL, `mimeType` TEXT NOT NULL, `encryptedFilePath` TEXT NOT NULL, `encryptedThumbnailPath` TEXT NOT NULL, `dateAdded` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '576724dfa82eb87619917450128ca73b')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `locked_apps`");
        db.execSQL("DROP TABLE IF EXISTS `intruder_logs`");
        db.execSQL("DROP TABLE IF EXISTS `hidden_apps`");
        db.execSQL("DROP TABLE IF EXISTS `vault_media`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsLockedApps = new HashMap<String, TableInfo.Column>(2);
        _columnsLockedApps.put("packageName", new TableInfo.Column("packageName", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLockedApps.put("isLocked", new TableInfo.Column("isLocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLockedApps = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLockedApps = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLockedApps = new TableInfo("locked_apps", _columnsLockedApps, _foreignKeysLockedApps, _indicesLockedApps);
        final TableInfo _existingLockedApps = TableInfo.read(db, "locked_apps");
        if (!_infoLockedApps.equals(_existingLockedApps)) {
          return new RoomOpenHelper.ValidationResult(false, "locked_apps(com.antigravity.applocker.data.local.entity.LockedAppEntity).\n"
                  + " Expected:\n" + _infoLockedApps + "\n"
                  + " Found:\n" + _existingLockedApps);
        }
        final HashMap<String, TableInfo.Column> _columnsIntruderLogs = new HashMap<String, TableInfo.Column>(4);
        _columnsIntruderLogs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIntruderLogs.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIntruderLogs.put("packageName", new TableInfo.Column("packageName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIntruderLogs.put("photoPath", new TableInfo.Column("photoPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysIntruderLogs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesIntruderLogs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoIntruderLogs = new TableInfo("intruder_logs", _columnsIntruderLogs, _foreignKeysIntruderLogs, _indicesIntruderLogs);
        final TableInfo _existingIntruderLogs = TableInfo.read(db, "intruder_logs");
        if (!_infoIntruderLogs.equals(_existingIntruderLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "intruder_logs(com.antigravity.applocker.data.local.entity.IntruderLogEntity).\n"
                  + " Expected:\n" + _infoIntruderLogs + "\n"
                  + " Found:\n" + _existingIntruderLogs);
        }
        final HashMap<String, TableInfo.Column> _columnsHiddenApps = new HashMap<String, TableInfo.Column>(1);
        _columnsHiddenApps.put("packageName", new TableInfo.Column("packageName", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHiddenApps = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHiddenApps = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHiddenApps = new TableInfo("hidden_apps", _columnsHiddenApps, _foreignKeysHiddenApps, _indicesHiddenApps);
        final TableInfo _existingHiddenApps = TableInfo.read(db, "hidden_apps");
        if (!_infoHiddenApps.equals(_existingHiddenApps)) {
          return new RoomOpenHelper.ValidationResult(false, "hidden_apps(com.antigravity.applocker.data.local.entity.HiddenAppEntity).\n"
                  + " Expected:\n" + _infoHiddenApps + "\n"
                  + " Found:\n" + _existingHiddenApps);
        }
        final HashMap<String, TableInfo.Column> _columnsVaultMedia = new HashMap<String, TableInfo.Column>(6);
        _columnsVaultMedia.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaultMedia.put("originalFileName", new TableInfo.Column("originalFileName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaultMedia.put("mimeType", new TableInfo.Column("mimeType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaultMedia.put("encryptedFilePath", new TableInfo.Column("encryptedFilePath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaultMedia.put("encryptedThumbnailPath", new TableInfo.Column("encryptedThumbnailPath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaultMedia.put("dateAdded", new TableInfo.Column("dateAdded", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVaultMedia = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVaultMedia = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVaultMedia = new TableInfo("vault_media", _columnsVaultMedia, _foreignKeysVaultMedia, _indicesVaultMedia);
        final TableInfo _existingVaultMedia = TableInfo.read(db, "vault_media");
        if (!_infoVaultMedia.equals(_existingVaultMedia)) {
          return new RoomOpenHelper.ValidationResult(false, "vault_media(com.antigravity.applocker.data.local.VaultMediaEntity).\n"
                  + " Expected:\n" + _infoVaultMedia + "\n"
                  + " Found:\n" + _existingVaultMedia);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "576724dfa82eb87619917450128ca73b", "5953c1f19e94dbfd0c7c19741af4b1fd");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "locked_apps","intruder_logs","hidden_apps","vault_media");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `locked_apps`");
      _db.execSQL("DELETE FROM `intruder_logs`");
      _db.execSQL("DELETE FROM `hidden_apps`");
      _db.execSQL("DELETE FROM `vault_media`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(AppLockerDao.class, AppLockerDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(HiddenAppDao.class, HiddenAppDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VaultMediaDao.class, VaultMediaDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public AppLockerDao getDao() {
    if (_appLockerDao != null) {
      return _appLockerDao;
    } else {
      synchronized(this) {
        if(_appLockerDao == null) {
          _appLockerDao = new AppLockerDao_Impl(this);
        }
        return _appLockerDao;
      }
    }
  }

  @Override
  public HiddenAppDao getHiddenAppDao() {
    if (_hiddenAppDao != null) {
      return _hiddenAppDao;
    } else {
      synchronized(this) {
        if(_hiddenAppDao == null) {
          _hiddenAppDao = new HiddenAppDao_Impl(this);
        }
        return _hiddenAppDao;
      }
    }
  }

  @Override
  public VaultMediaDao getVaultMediaDao() {
    if (_vaultMediaDao != null) {
      return _vaultMediaDao;
    } else {
      synchronized(this) {
        if(_vaultMediaDao == null) {
          _vaultMediaDao = new VaultMediaDao_Impl(this);
        }
        return _vaultMediaDao;
      }
    }
  }
}
