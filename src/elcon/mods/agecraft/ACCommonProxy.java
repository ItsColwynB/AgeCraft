package elcon.mods.agecraft;

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import elcon.mods.agecraft.core.PlayerTradeManager;
import elcon.mods.agecraft.core.clothing.PlayerClothingServer;
import elcon.mods.agecraft.core.gui.ContainerPlayerTrade;
import elcon.mods.agecraft.core.gui.ContainerSmeltery;
import elcon.mods.agecraft.core.gui.ContainerWorkbench;
import elcon.mods.agecraft.core.player.ACPlayerServer;
import elcon.mods.agecraft.core.tileentities.TileEntityAgeTeleporterChest;
import elcon.mods.agecraft.core.tileentities.TileEntitySmelteryFurnace;
import elcon.mods.agecraft.core.tileentities.TileEntityWorkbench;
import elcon.mods.agecraft.prehistory.gui.ContainerSharpener;
import elcon.mods.agecraft.prehistory.gui.InventorySharpener;
import elcon.mods.core.ElConCore;
import elcon.mods.core.player.PlayerAPI;
import elcon.mods.core.player.PlayerAPI.PlayerCoreType;

public class ACCommonProxy implements IGuiHandler {

	public void registerResources() {
		
	}
	
	public void registerRenderInformation() {	
		//init player clothing server
		PlayerClothingServer.clothingDir = new File(ElConCore.minecraftDir, File.separator + "clothing");
		PlayerClothingServer.clothingFileDir = new File(ElConCore.minecraftDir, File.separator + "playerSkins");
	}

	public void registerPlayerAPI() {
		PlayerAPI.register(PlayerCoreType.SERVER, ACPlayerServer.class);
		ACLog.info("Registered PlayerAPI classes");
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == 0) {
			return player.inventoryContainer;
		} else if(id == 1) {
			return new ContainerPlayerTrade(player.inventory, PlayerTradeManager.trades.get(player.username), (byte) 2);
		} else if(id == 10) {
			return new ContainerChest(player.inventory, (TileEntityAgeTeleporterChest) world.getBlockTileEntity(x, y, z));
		} else if(id == 11) {
			return new ContainerWorkbench(player, player.inventory, (TileEntityWorkbench) world.getBlockTileEntity(x, y, z), world, x, y, z);
		} else if(id == 12) {
			return new ContainerSmeltery(player, (TileEntitySmelteryFurnace) world.getBlockTileEntity(x, y, z));
		} else if(id == 30) {
			return new ContainerSharpener(player, new InventorySharpener());
		} 
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	public MinecraftServer getMCServer() {
		return FMLCommonHandler.instance().getMinecraftServerInstance();
	}
}
