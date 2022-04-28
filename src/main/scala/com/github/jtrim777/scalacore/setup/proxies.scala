package com.github.jtrim777.scalacore.setup

import com.github.jtrim777.scalacore.ScalaCore
import com.github.jtrim777.scalacore.render.FluidContainerModel
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.{ForgeModelBakery, ModelLoaderRegistry}
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.loading.FMLEnvironment
import org.apache.logging.log4j.Logger

sealed trait ModProxy {
  def init(): Unit

  def getClientWorld: Level

  def getClientPlayer: Player

  def executeClient(f:() => Unit): Unit
  def executeServer(f:() => Unit): Unit

  def inClient[T](f:() => T): Option[T]
  def inServer[T](f:() => T): Option[T]
}

trait ClientProxy extends ModProxy {
  override def getClientWorld: Level = Minecraft.getInstance().level

  override def getClientPlayer: Player = Minecraft.getInstance().player

  override def executeClient(f: () => Unit): Unit = f()

  override def executeServer(f: () => Unit): Unit = {}

  override def inClient[T](f: () => T): Option[T] = Some(f())

  override def inServer[T](f: () => T): Option[T] = None
}

trait ServerProxy extends ModProxy {
  override def getClientWorld: Level =
    throw new IllegalStateException("This proxy code should only be called from the client side")

  override def getClientPlayer: Player =
    throw new IllegalStateException("This proxy code should only be called from the client side")

  override def executeClient(f: () => Unit): Unit = {}

  override def executeServer(f: () => Unit): Unit = f()

  override def inClient[T](f: () => T): Option[T] = None

  override def inServer[T](f: () => T): Option[T] = Some(f())
}

private[scalacore] case class CoreClientProxy(log: Logger) extends ClientProxy {
  override def init(): Unit = {
  }

  @SubscribeEvent
  def registerModelLoaders(event: ModelRegistryEvent): Unit = {
    log.info(s"Registering model loaders on dist ${FMLEnvironment.dist}")
    Thread.sleep(1000) // Yah fuck this, but it is necessary
    ModelLoaderRegistry.registerLoader(new ResourceLocation(ScalaCore.MODID, "fluid_container"), new FluidContainerModel.Loader)
  }

  @SubscribeEvent
  def clientSetupEvent(event: FMLClientSetupEvent): Unit = {
    ForgeModelBakery.addSpecialModel(new ModelResourceLocation("scalacore:tin_can_empty", "inventory"))
    ForgeModelBakery.addSpecialModel(new ModelResourceLocation("scalacore:tin_can_filled", "inventory"))
  }
}

private[scalacore] case class CoreServerProxy(log: Logger) extends ServerProxy {
  override def init(): Unit = {
    Minecraft.getInstance().level.getRecipeManager
  }
}