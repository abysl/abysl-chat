package org.chiachat.app.desktop

import app.cash.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.chiachat.app.SharedAppModules
import org.chiachat.app.compose.ComposeAppModule
import org.chiachat.app.compose.ComposeRoot
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class DesktopApp(driver: SqlDriver) {
  val composeRoot = ComposeRoot()

  val desktopModule = module {
    single { driver }
    factory(named("ioScope")) { CoroutineScope(Dispatchers.IO) }

    factory(named("vmScope")) { CoroutineScope(Dispatchers.Default) }
  }

  init {
    startKoin {
      modules(
          desktopModule,
          SharedAppModules.sharedModule,
          ComposeAppModule.composeModule,
      )
      allowOverride(false)
    }
  }
}
