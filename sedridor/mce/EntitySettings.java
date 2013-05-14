package sedridor.mce;

import java.util.Arrays;

import net.minecraft.world.biome.BiomeGenBase;

public class EntitySettings
{
    public static String entity;
    public static int weight;
    public static int minGroupCount;
    public static int maxGroupCount;
	BiomeGenBase biomesToSpawnIn[];

	public EntitySettings(String par1, int par2, int par3, int par4, String par5)
    {
        this.entity = par1;
        this.weight = par2;
        this.minGroupCount = par3;
        this.maxGroupCount = par4;
        this.setBiomes(par5);
    }

	public String toDataString() {
		return this.entity + "," + this.weight + "," + this.minGroupCount + "," + this.maxGroupCount;
	}

	public String toBiomeString() {
		return this.entity + "," + this.weight + "," + this.minGroupCount + "," + this.maxGroupCount;
	}

	public String getEntity() {
		return this.entity;
	}

	public int getWeight() {
		return this.weight;
	}

	public int getMinGroupCount() {
		return this.minGroupCount;
	}

	public int getMaxGroupCount() {
		return this.maxGroupCount;
	}

	public BiomeGenBase[] getBiomes() {
		return this.biomesToSpawnIn;
	}

	public void setEntity(String par1) {
		this.entity = par1;
	}

	public void setWeight(int par1) {
		this.weight = par1;
	}

	public void setMinGroupCount(int par1) {
		this.minGroupCount = par1;
	}

	public void setMaxGroupCount(int par1) {
		this.maxGroupCount = par1;
	}

	public void setBiomes(String par1) {
		this.biomesToSpawnIn = new BiomeGenBase[8];
	}
}
