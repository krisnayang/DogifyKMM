package com.example.dogify.util

import kotlinx.coroutines.Dispatchers

internal actual fun getDispatcherProvider(): DispatcherProvider = DesktopDispatcherProvider()

private class DesktopDispatcherProvider: DispatcherProvider{
  override val main = Dispatchers.Main
  override val io = Dispatchers.IO
  override val unconfined = Dispatchers.Unconfined
}