package com.jmeyer.bindingofisaac.structures;

import com.jmeyer.bindingofisaac.IsaacMod;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class BasementWorldGenerator implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch(world.provider.getDimension()) {
            case 0: generateBasement(world, random, chunkX*16, chunkZ*16);
        }
    }

    private void generateBasement(World world, Random random, int blockX, int blockZ) {
        if (blockX != 0 || blockZ != 0) {
            return;
        }

        //TODO store basement so GameHandler can reload on player relog
        if (IsaacMod.game.basement == null) {
            IsaacMod.game.basement = new Basement(9);
            IsaacMod.game.basement.generate(5);
            System.out.println(IsaacMod.game.basement);
        }

        int gridSize = IsaacMod.game.basement.getGridSize();
        for (int z = 0; z < gridSize * BasementRoom.ROOM_LENGTH; z += BasementRoom.ROOM_LENGTH) {
            for (int x = 0; x < gridSize * BasementRoom.ROOM_WIDTH; x += BasementRoom.ROOM_WIDTH) {
                (new WorldGenBasementRoom()).generate(world, random, new BlockPos(x, 4, z));
            }
        }
    }
}
