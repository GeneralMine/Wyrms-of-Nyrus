package com.vetpetmon.wyrmsofnyrus.locallib.networkmessages;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class BiomeList implements IMessage {
    private int chunkX;
    private int chunkZ;
    private int[] biomeList;

    public BiomeList() {
    }

    public BiomeList(int chunkX, int chunkZ, int[] biomeArray) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.biomeList = biomeArray;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
        chunkX = packetBuffer.readVarInt();
        chunkZ = packetBuffer.readVarInt();
        biomeList = packetBuffer.readVarIntArray();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
        packetBuffer.writeVarInt(chunkX);
        packetBuffer.writeVarInt(chunkZ);
        packetBuffer.writeVarIntArray(biomeList);
    }

    public static class Handler implements IMessageHandler<BiomeList, IMessage> {
        @Override
        public IMessage onMessage(BiomeList message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                WorldClient world = Minecraft.getMinecraft().world;
                Chunk chunk = world.getChunkFromChunkCoords(message.chunkX, message.chunkZ);
                ((INewChunk) chunk).setBiomeIDList(message.biomeList);
                world.markBlockRangeForRenderUpdate(new BlockPos(chunk.getPos().getXStart(), 0, chunk.getPos().getZStart()), new BlockPos(chunk.getPos().getXEnd(), 0, chunk.getPos().getZEnd()));
            });
            return null;
        }
    }
}
