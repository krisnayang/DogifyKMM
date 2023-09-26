package com.example.dogify.database

import com.example.dogify.db.DogifyDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.koin.core.scope.Scope

internal actual fun Scope.createDriver(databaseName: String): SqlDriver =
  JdbcSqliteDriver(databaseName)
    .also { driver ->
    DogifyDatabase.Schema.create(driver)
}