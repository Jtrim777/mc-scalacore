package com.github.jtrim777.scalacore

import com.github.jtrim777.scalacore.setup.{ClientProxy, CoreClientProxy, CoreServerProxy, ServerProxy}
import com.github.jtrim777.scalacore.utils.ContentManager
import net.minecraftforge.common.ForgeMod
import net.minecraftforge.eventbus.api.{IEventBus, SubscribeEvent}
import net.minecraftforge.fml.event.lifecycle.{FMLCommonSetupEvent, FMLDedicatedServerSetupEvent}
import net.minecraftforge.fml.event.server.{FMLServerAboutToStartEvent, FMLServerStartedEvent, FMLServerStartingEvent}

class ScalaCore extends ModHeart {
  override def getModID: String = ScalaCore.MODID

  override def getClientProxy: ClientProxy = CoreClientProxy()

  override def getServerProxy: ServerProxy = CoreServerProxy()

  override protected def initialize(bus: IEventBus): Unit = {
    super.initialize(bus)
    ForgeMod.enableMilkFluid()
  }

  override def getContent: ContentManager = CoreContent

  @SubscribeEvent
  def commonSetup(event: FMLCommonSetupEvent): Unit = {

  }

  @SubscribeEvent
  def serverSetup(event: FMLDedicatedServerSetupEvent): Unit = {

  }

  @SubscribeEvent
  def serverWillStart(event: FMLServerAboutToStartEvent): Unit = {

  }

  @SubscribeEvent
  def serverStarting(event: FMLServerStartingEvent): Unit = {

  }

  @SubscribeEvent
  def serverStarted(event: FMLServerStartedEvent): Unit = {

  }
}

object ScalaCore {
  final val MODID: String = "scalacore"
  final val Version: String = "0.1.0"
}