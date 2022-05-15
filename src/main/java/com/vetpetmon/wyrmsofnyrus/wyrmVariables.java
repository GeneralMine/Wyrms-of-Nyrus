package com.vetpetmon.wyrmsofnyrus;

import com.vetpetmon.wyrmsofnyrus.invasion.HiveName;
import com.vetpetmon.wyrmsofnyrus.synapselib.optiMath;
import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import net.minecraft.world.storage.WorldSavedData;
import net.minecraft.world.World;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public class wyrmVariables {
    public static String wyrmInvasionStatus = "";
    public static String hiveName = "";
    public static int wyrmEvo;

    public static class MapVariables extends WorldSavedData {
        public static final String DATA_NAME = "wyrmsofnyrus_mapvars";
        public boolean invasionStarted = false;

        public MapVariables() {
            super(DATA_NAME);
        }

        public MapVariables(String s) {
            super(s);
        }

        @Override
        public void readFromNBT(NBTTagCompound nbt) {
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
            nbt.setBoolean("invasionStarted", invasionStarted);
            return nbt;
        }

        public void syncData(World world) {
            this.markDirty();
            if (world.isRemote) {
                wyrmsofnyrus.PACKET_HANDLER.sendToServer(new WorldSavedDataSyncMessage(0, this));
            } else {
                wyrmsofnyrus.PACKET_HANDLER.sendToAll(new WorldSavedDataSyncMessage(0, this));
            }
        }

        public static MapVariables get(World world) {
            MapVariables instance = (MapVariables) world.getMapStorage().getOrLoadData(MapVariables.class, DATA_NAME);
            if (instance == null) {
                instance = new MapVariables();
                world.getMapStorage().setData(DATA_NAME, instance);
            }
            return instance;
        }
    }

    public static class WorldVariables extends WorldSavedData {
        public static final String DATA_NAME = "wyrmsofnyrus_worldvars";
        public double wyrmInvasionPoints = 0;
        public double wyrmInvasionDifficulty = 1;
        public int wyrmEvo = 0;
        public static String hiveName = "";
        public WorldVariables() {
            super(DATA_NAME);
        }

        public WorldVariables(String s) {
            super(s);
        }

        @Override
        public void readFromNBT(NBTTagCompound nbt) {
            wyrmInvasionPoints			= 	optiMath.arcForm(nbt.getDouble("wyrmInvasionPoints"));
            wyrmEvo                     =   nbt.getInteger("wyrmEvo");
            wyrmInvasionDifficulty		= 	nbt.getDouble("wyrmInvasionDifficulty");
            hiveName		            = 	nbt.getString("hiveName");
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
            nbt.setInteger("wyrmEvo", wyrmEvo);
            nbt.setDouble("wyrmInvasionPoints", wyrmInvasionPoints);
            nbt.setDouble("wyrmInvasionDifficulty", optiMath.arcForm(wyrmInvasionDifficulty));
            nbt.setString("hiveName", hiveName);
            if (hiveName == ""){
                hiveName = HiveName.HiveName();
            }
            return nbt;
        }

        public void syncData(World world) {
            this.markDirty();
            if (world.isRemote) {
                wyrmsofnyrus.PACKET_HANDLER.sendToServer(new WorldSavedDataSyncMessage(1, this));
            } else {
                wyrmsofnyrus.PACKET_HANDLER.sendToDimension(new WorldSavedDataSyncMessage(1, this), world.provider.getDimension());
            }
        }

        public static WorldVariables get(World world) {
            WorldVariables instance = (WorldVariables) world.getPerWorldStorage().getOrLoadData(WorldVariables.class, DATA_NAME);
            if (instance == null) {
                instance = new WorldVariables();
                world.getPerWorldStorage().setData(DATA_NAME, instance);
            }
            return instance;
        }
    }

    public static class WorldSavedDataSyncMessageHandler implements IMessageHandler<WorldSavedDataSyncMessage, IMessage> {
        @Override
        public IMessage onMessage(WorldSavedDataSyncMessage message, MessageContext context) {
            if (context.side == Side.SERVER)
                context.getServerHandler().player.getServerWorld()
                        .addScheduledTask(() -> syncData(message, context, context.getServerHandler().player.world));
            else
                Minecraft.getMinecraft().addScheduledTask(() -> syncData(message, context, Minecraft.getMinecraft().player.world));
            return null;
        }

        private void syncData(WorldSavedDataSyncMessage message, MessageContext context, World world) {
            if (context.side == Side.SERVER) {
                message.data.markDirty();
                if (message.type == 0)
                    wyrmsofnyrus.PACKET_HANDLER.sendToAll(message);
                else
                    wyrmsofnyrus.PACKET_HANDLER.sendToDimension(message, world.provider.getDimension());
            }
            if (message.type == 0) {
                world.getMapStorage().setData(MapVariables.DATA_NAME, message.data);
            } else {
                world.getPerWorldStorage().setData(WorldVariables.DATA_NAME, message.data);
            }
        }
    }

    public static class WorldSavedDataSyncMessage implements IMessage {
        public int type;
        public WorldSavedData data;
        public WorldSavedDataSyncMessage() {
        }

        public WorldSavedDataSyncMessage(int type, WorldSavedData data) {
            this.type = type;
            this.data = data;
        }

        @Override
        public void toBytes(io.netty.buffer.ByteBuf buf) {
            buf.writeInt(this.type);
            ByteBufUtils.writeTag(buf, this.data.writeToNBT(new NBTTagCompound()));
        }

        @Override
        public void fromBytes(io.netty.buffer.ByteBuf buf) {
            this.type = buf.readInt();
            if (this.type == 0)
                this.data = new MapVariables();
            else
                this.data = new WorldVariables();
            this.data.readFromNBT(ByteBufUtils.readTag(buf));
        }
    }
}
