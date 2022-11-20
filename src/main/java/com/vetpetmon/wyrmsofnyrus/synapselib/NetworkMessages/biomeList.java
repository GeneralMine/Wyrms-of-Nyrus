package com.vetpetmon.wyrmsofnyrus.synapselib.NetworkMessages;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class biomeList implements IMessage {
    private int chunkX;
    private int chunkZ;
    private int[] biomeList;

    public biomeList() {
    }

    public biomeList(int chunkX, int chunkZ, int[] biomeArray) {
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

    public static class Handler implements IMessageHandler<biomeList, IMessage> {
        @Override
        public IMessage onMessage(biomeList message, MessageContext ctx) {
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
