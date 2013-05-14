package sedridor.mce;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class RayTraceVsComplexBlock
{
    private World world;
    private int blockX;
    private int blockY;
    private int blockZ;
    private Vec3 startRay;
    private Vec3 endRay;
    List intersectionPointList = new ArrayList();

    public RayTraceVsComplexBlock(World par1World, int par2, int par3, int par4, Vec3 par5, Vec3 par6)
    {
        this.world = par1World;
        this.blockX = par2;
        this.blockY = par3;
        this.blockZ = par4;
        this.startRay = Vec3.createVectorHelper(par5.xCoord, par5.yCoord, par5.zCoord);
        this.endRay = Vec3.createVectorHelper(par6.xCoord, par6.yCoord, par6.zCoord);
    }

    public void AddBoxWithLocalCoordsToIntersectionList(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
    {
        Vec3 var1 = this.startRay.addVector((double)(-this.blockX), (double)(-this.blockY), (double)(-this.blockZ));
        Vec3 var2 = this.endRay.addVector((double)(-this.blockX), (double)(-this.blockY), (double)(-this.blockZ));
        Vec3 var3 = Vec3.createVectorHelper(minX, minY, minZ);
        Vec3 var4 = Vec3.createVectorHelper(maxX, maxY, maxZ);
        Vec3 var5 = var1.getIntermediateWithXValue(var2, var3.xCoord);
        Vec3 var6 = var1.getIntermediateWithXValue(var2, var4.xCoord);
        Vec3 var7 = var1.getIntermediateWithYValue(var2, var3.yCoord);
        Vec3 var8 = var1.getIntermediateWithYValue(var2, var4.yCoord);
        Vec3 var9 = var1.getIntermediateWithZValue(var2, var3.zCoord);
        Vec3 var10 = var1.getIntermediateWithZValue(var2, var4.zCoord);

        if (!this.isVecInsideYZBounds(var5, var3, var4))
        {
            var5 = null;
        }

        if (!this.isVecInsideYZBounds(var6, var3, var4))
        {
            var6 = null;
        }

        if (!this.isVecInsideXZBounds(var7, var3, var4))
        {
            var7 = null;
        }

        if (!this.isVecInsideXZBounds(var8, var3, var4))
        {
            var8 = null;
        }

        if (!this.isVecInsideXYBounds(var9, var3, var4))
        {
            var9 = null;
        }

        if (!this.isVecInsideXYBounds(var10, var3, var4))
        {
            var10 = null;
        }

        Vec3 var11 = null;

        if (var5 != null && (var11 == null || var1.squareDistanceTo(var5) < var1.squareDistanceTo(var11)))
        {
            var11 = var5;
        }

        if (var6 != null && (var11 == null || var1.squareDistanceTo(var6) < var1.squareDistanceTo(var11)))
        {
            var11 = var6;
        }

        if (var7 != null && (var11 == null || var1.squareDistanceTo(var7) < var1.squareDistanceTo(var11)))
        {
            var11 = var7;
        }

        if (var8 != null && (var11 == null || var1.squareDistanceTo(var8) < var1.squareDistanceTo(var11)))
        {
            var11 = var8;
        }

        if (var9 != null && (var11 == null || var1.squareDistanceTo(var9) < var1.squareDistanceTo(var11)))
        {
            var11 = var9;
        }

        if (var10 != null && (var11 == null || var1.squareDistanceTo(var10) < var1.squareDistanceTo(var11)))
        {
            var11 = var10;
        }

        if (var11 != null)
        {
            byte var12 = -1;

            if (var11 == var5)
            {
                var12 = 4;
            }

            if (var11 == var6)
            {
                var12 = 5;
            }

            if (var11 == var7)
            {
                var12 = 0;
            }

            if (var11 == var8)
            {
                var12 = 1;
            }

            if (var11 == var9)
            {
                var12 = 2;
            }

            if (var11 == var10)
            {
                var12 = 3;
            }

            this.intersectionPointList.add(new MovingObjectPosition(this.blockX, this.blockY, this.blockZ, var12, var11.addVector((double)this.blockX, (double)this.blockY, (double)this.blockZ)));
        }
    }

    public MovingObjectPosition GetFirstIntersection()
    {
        MovingObjectPosition movingObjectPosition = null;
        Iterator iterator = this.intersectionPointList.iterator();
        double var1 = 0.0D;

        while (iterator.hasNext())
        {
            MovingObjectPosition movingObjectPosition1 = (MovingObjectPosition)iterator.next();
            double var2 = movingObjectPosition1.hitVec.squareDistanceTo(this.endRay);

            if (var2 > var1)
            {
                movingObjectPosition = movingObjectPosition1;
                var1 = var2;
            }
        }

        return movingObjectPosition;
    }

    private boolean isVecInsideYZBounds(Vec3 par1, Vec3 par2, Vec3 par3)
    {
        return par1 == null ? false : par1.yCoord >= par2.yCoord && par1.yCoord <= par3.yCoord && par1.zCoord >= par2.zCoord && par1.zCoord <= par3.zCoord;
    }

    private boolean isVecInsideXZBounds(Vec3 par1, Vec3 par2, Vec3 par3)
    {
        return par1 == null ? false : par1.xCoord >= par2.xCoord && par1.xCoord <= par3.xCoord && par1.zCoord >= par2.zCoord && par1.zCoord <= par3.zCoord;
    }

    private boolean isVecInsideXYBounds(Vec3 par1, Vec3 par2, Vec3 par3)
    {
        return par1 == null ? false : par1.xCoord >= par2.xCoord && par1.xCoord <= par3.xCoord && par1.yCoord >= par2.yCoord && par1.yCoord <= par3.yCoord;
    }
}
