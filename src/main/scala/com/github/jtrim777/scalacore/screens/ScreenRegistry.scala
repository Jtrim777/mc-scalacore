package com.github.jtrim777.scalacore.screens

import com.github.jtrim777.scalacore.menu.{MenuBase, MenuDataProvider}
import com.github.jtrim777.scalacore.tiles.TileBase
import net.minecraft.client.gui.screens.MenuScreens
import net.minecraft.world.inventory.MenuType

trait ScreenRegistry {
  type ScreenConfig[D <: MenuDataProvider, M <: MenuBase[D], T <: TileBase, U <: Screen[D, M, T]] = (MenuType[M], MenuScreens.ScreenConstructor[M, U])

  val Screens: List[ScreenConfig[_ <: MenuDataProvider, _ <: MenuBase[_],_ <: TileBase,_ <: Screen[_ <: MenuDataProvider, _ <: MenuBase[_], _ <: TileBase]]]

  def registerScreens(): Unit

  protected def screen[
    D <: MenuDataProvider,
    M <: MenuBase[D],
    T <: TileBase,
    U <: Screen[D, M, T]
  ](
     menu: MenuType[M],
     maker: MenuScreens.ScreenConstructor[M, U]
   ): ScreenConfig[D,M,T,U] = {
    (menu, maker)
  }
}
