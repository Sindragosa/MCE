package sedridor.mce;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class MCE_Reflect {

    private static Field field_modifiers = null;

    public static void init()
    {
		try {
	        field_modifiers = Field.class.getDeclaredField("modifiers");
	        field_modifiers.setAccessible(true);
		} catch (Throwable e) {}
    }

    /**
     * Gets the value of the specified variable.
     * Returns it as an Object.
     */
    public static Object getValue(Class par1Class, Object par2Object, int par3)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredFields()[par3];
			var4.setAccessible(true);
	        return var4.get(par2Object);
		} catch (Throwable e) {}
        return null;
    }

    /**
     * Gets the value of the specified variable.
     * Returns it as an Object.
     */
    public static Object getValue(Class par1Class, Object par2Object, String par3)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredField(par3);
			var4.setAccessible(true);
	        return var4.get(par2Object);
		} catch (Throwable e) {}
        return null;
    }

    /**
     * Sets block variable to the specified value.
     * @return 
     */
    public static boolean setValue(Class par1Class, Object par2Object, int par3, Block par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredFields()[par3];
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            var4.set(par2Object, par4Object);
            return true;
		} catch (Throwable e) {
	        return false;
		}
	}

    /**
     * Sets item variable to the specified value.
     * @return 
     */
    public static boolean setValue(Class par1Class, Object par2Object, int par3, Item par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredFields()[par3];
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            var4.set(par2Object, par4Object);
            return true;
		} catch (Throwable e) {
	        return false;
		}
	}

    /**
     * Sets an int variable to the specified value.
     */
    public static boolean setValue(Class par1Class, Object par2Object, int par3, int par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredFields()[par3];
			var4.setAccessible(true);
            //field_modifiers = Field.class.getDeclaredField("modifiers");
            //field_modifiers.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            var4.set(par2Object, par4Object);
            return true;
		} catch (Throwable e) {
	        return false;
		}
    }

    /**
     * Sets a float variable to the specified value.
     */
    public static boolean setValue(Class par1Class, Object par2Object, int par3, float par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredFields()[par3];
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            var4.set(par2Object, par4Object);
            return true;
		} catch (Throwable e) {
	        return false;
		}
    }

    /**
     * Sets a double variable to the specified value.
     */
    public static boolean setValue(Class par1Class, Object par2Object, int par3, double par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredFields()[par3];
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            var4.set(par2Object, par4Object);
            return true;
		} catch (Throwable e) {
	        return false;
		}
    }

    /**
     * Sets a long variable to the specified value.
     */
    public static boolean setValue(Class par1Class, Object par2Object, int par3, long par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredFields()[par3];
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            var4.set(par2Object, par4Object);
            return true;
		} catch (Throwable e) {
	        return false;
		}
    }

    /**
     * Sets a string variable to the specified value.
     */
    public static boolean setValue(Class par1Class, Object par2Object, int par3, String par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredFields()[par3];
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            var4.set(par2Object, par4Object);
            return true;
		} catch (Throwable e) {
	        return false;
		}
    }

    /**
     * Sets an object variable to the specified value.
     */
    public static boolean setValue(Class par1Class, Object par2Object, int par3, Object par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredFields()[par3];
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            var4.set(par2Object, par4Object);
            return true;
		} catch (Throwable e) {
	        return false;
		}
	}

    /**
     * Sets block int variable to the specified value.
     */
    public static boolean setValue(Class par1Class, Object par2Object, String par3, Block par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredField(par3);
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            var4.set(par2Object, par4Object);
            return true;
		} catch (Throwable e) {
	        return false;
		}
	}

    /**
     * Sets item variable to the specified value.
     */
    public static boolean setValue(Class par1Class, Object par2Object, String par3, Item par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredField(par3);
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            var4.set(par2Object, par4Object);
            return true;
		} catch (Throwable e) {
	        return false;
		}
	}

    /**
     * Sets an int variable to the specified value.
     */
    public static boolean setValue(Class par1Class, Object par2Object, String par3, int par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredField(par3);
			var4.setAccessible(true);
            //field_modifiers = Field.class.getDeclaredField("modifiers");
            //field_modifiers.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            var4.set(par2Object, par4Object);
            return true;
		} catch (Throwable e) {
	        return false;
		}
    }

    /**
     * Sets a float variable to the specified value.
     */
    public static boolean setValue(Class par1Class, Object par2Object, String par3, float par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredField(par3);
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            var4.set(par2Object, par4Object);
            return true;
		} catch (Throwable e) {
	        return false;
		}
    }

    /**
     * Sets a double variable to the specified value.
     */
    public static boolean setValue(Class par1Class, Object par2Object, String par3, double par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredField(par3);
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            var4.set(par2Object, par4Object);
            return true;
		} catch (Throwable e) {
	        return false;
		}
    }

    /**
     * Sets a long variable to the specified value.
     */
    public static boolean setValue(Class par1Class, Object par2Object, String par3, long par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredField(par3);
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            var4.set(par2Object, par4Object);
            return true;
		} catch (Throwable e) {
	        return false;
		}
    }

    /**
     * Sets a string variable to the specified value.
     */
    public static boolean setValue(Class par1Class, Object par2Object, String par3, String par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredField(par3);
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            var4.set(par2Object, par4Object);
            return true;
		} catch (Throwable e) {
	        return false;
		}
    }

    /**
     * Sets an object variable to the specified value.
     */
    public static boolean setValue(Class par1Class, Object par2Object, String par3, Object par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredField(par3);
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            var4.set(par2Object, par4Object);
            return true;
		} catch (Throwable e) {
	        return false;
		}
	}

    
    /**
     * Adds a block to an array of blocks.
     * @return index
     */
    public static int addValue(Class par1Class, Object par2Object, int par3, Block par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredFields()[par3];
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            Block[] var6 = (Block[])var4.get((Object)null);
            List var7 = Arrays.asList(var6);
            ArrayList var8 = new ArrayList();
            var8.addAll(var7);
            if (!var8.contains(par4Object))
            {
                var8.add(par4Object);
            }
            int var9 = var8.indexOf(par4Object);
            var4.set((Object)null, var8.toArray(new Block[0]));
            return var9;
		} catch (Throwable e) {}
        return -1;
    }

    /**
     * Removes a block from an array of blocks.
     * @return index
     */
    public static void removeValue(Class par1Class, Object par2Object, int par3, Block par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredFields()[par3];
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            Block[] var6 = (Block[])var4.get((Object)null);
            List var7 = Arrays.asList(var6);
            ArrayList var8 = new ArrayList();
            var8.addAll(var7);
            if (!var8.contains(par4Object))
            {
                var8.remove(par4Object);
            }
            int var9 = var8.indexOf(par4Object);
            var4.set((Object)null, var8.toArray(new Block[0]));
		} catch (Throwable e) {}
    }

    /**
     * Adds a block to an array of blocks.
     * @return index
     */
    public static int addValue(Class par1Class, Object par2Object, String par3, Block par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredField(par3);
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            Block[] var6 = (Block[])var4.get((Object)null);
            List var7 = Arrays.asList(var6);
            ArrayList var8 = new ArrayList();
            var8.addAll(var7);
            if (!var8.contains(par4Object))
            {
                var8.add(par4Object);
            }
            int var9 = var8.indexOf(par4Object);
            var4.set((Object)null, var8.toArray(new Block[0]));
            return var9;
		} catch (Throwable e) {}
        return -1;
    }

    /**
     * Removes a block from an array of blocks.
     * @return index
     */
    public static void removeValue(Class par1Class, Object par2Object, String par3, Block par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredField(par3);
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            Block[] var6 = (Block[])var4.get((Object)null);
            List var7 = Arrays.asList(var6);
            ArrayList var8 = new ArrayList();
            var8.addAll(var7);
            if (!var8.contains(par4Object))
            {
                var8.remove(par4Object);
            }
            int var9 = var8.indexOf(par4Object);
            var4.set((Object)null, var8.toArray(new Block[0]));
		} catch (Throwable e) {}
    }

    /**
     * Adds a block to an array of blocks.
     * @return index
     */
    public static int addValue(Class par1Class, Object par2Object, int par3, String par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredFields()[par3];
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            String[] var6 = (String[])var4.get((Object)null);
            List var7 = Arrays.asList(var6);
            ArrayList var8 = new ArrayList();
            var8.addAll(var7);
            if (!var8.contains(par4Object))
            {
                var8.add(par4Object);
            }
            int var9 = var8.indexOf(par4Object);
            var4.set((Object)null, var8.toArray(new String[0]));
            return var9;
		} catch (Throwable e) {}
        return -1;
    }

    /**
     * Removes a block from an array of blocks.
     * @return index
     */
    public static void removeValue(Class par1Class, Object par2Object, int par3, String par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredFields()[par3];
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            String[] var6 = (String[])var4.get((Object)null);
            List var7 = Arrays.asList(var6);
            ArrayList var8 = new ArrayList();
            var8.addAll(var7);
            if (!var8.contains(par4Object))
            {
                var8.remove(par4Object);
            }
            int var9 = var8.indexOf(par4Object);
            var4.set((Object)null, var8.toArray(new String[0]));
		} catch (Throwable e) {}
    }

    /**
     * Adds a block to an array of blocks.
     * @return index
     */
    public static int addValue(Class par1Class, Object par2Object, String par3, String par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredField(par3);
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            String[] var6 = (String[])var4.get((Object)null);
            List var7 = Arrays.asList(var6);
            ArrayList var8 = new ArrayList();
            var8.addAll(var7);
            if (!var8.contains(par4Object))
            {
                var8.add(par4Object);
            }
            int var9 = var8.indexOf(par4Object);
            var4.set((Object)null, var8.toArray(new String[0]));
            return var9;
		} catch (Throwable e) {}
        return -1;
    }

    /**
     * Removes a block from an array of blocks.
     * @return index
     */
    public static void removeValue(Class par1Class, Object par2Object, String par3, String par4Object)
    {
		Field var4;
		try {
			var4 = par1Class.getDeclaredField(par3);
			var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);
            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }
            String[] var6 = (String[])var4.get((Object)null);
            List var7 = Arrays.asList(var6);
            ArrayList var8 = new ArrayList();
            var8.addAll(var7);
            if (!var8.contains(par4Object))
            {
                var8.remove(par4Object);
            }
            int var9 = var8.indexOf(par4Object);
            var4.set((Object)null, var8.toArray(new String[0]));
		} catch (Throwable e) {}
    }
}
