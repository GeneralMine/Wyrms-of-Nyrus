package com.vetpetmon.wyrmsofnyrus.synapselib.NetworkMessages;

import com.vetpetmon.wyrmsofnyrus.world.biome.SpreadingBiome;
import com.vetpetmon.wyrmsofnyrus.wyrmsofnyrus;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class biomeChange implements IMessage {
    private int x;
    private int z;
    private int biomeId;

    public biomeChange() {
    }

    public biomeChange(int x, int z, int biomeId) {
        this.x = x;
        this.z = z;
        this.biomeId = biomeId;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        x = packetBuffer.readVarInt();
        z = packetBuffer.readVarInt();
        biomeId = packetBuffer.readVarInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        packetBuffer.writeVarInt(x);
        packetBuffer.writeVarInt(z);
        packetBuffer.writeVarInt(biomeId);
    }

    public static class Handler implements IMessageHandler<biomeChange, IMessage> {
        @Override
        public IMessage onMessage(biomeChange message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                WorldClient world = Minecraft.getMinecraft().world;
                Chunk chunk = world.getChunkFromBlockCoords(new BlockPos(message.x, 0, message.z));
                ((INewChunk) chunk).getBiomeIDList()[(message.z & 15) << 4 | message.x & 15] = message.biomeId;
                world.markBlockRangeForRenderUpdate(new BlockPos(message.x, 0, message.z), new BlockPos(message.x, 0, message.z));
            });
            return null;
        }
    }
}
