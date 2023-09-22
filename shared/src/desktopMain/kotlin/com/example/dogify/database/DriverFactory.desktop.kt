package com.example.dogify.database

import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.scope.Scope

internal actual fun Scope.createDriver(databaseName: String): SqlDriver {
    TODO("Not yet implemented")
}