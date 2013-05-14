package sedridor.mce.core;

//import static org.objectweb.asm.Opcodes.ALOAD;
//import static org.objectweb.asm.Opcodes.INVOKESTATIC;
//import java.io.File;
//import java.util.Iterator;
//import org.objectweb.asm.ClassReader;
//import org.objectweb.asm.ClassWriter;
//import org.objectweb.asm.tree.AbstractInsnNode;
//import org.objectweb.asm.tree.ClassNode;
//import org.objectweb.asm.tree.InsnList;
//import org.objectweb.asm.tree.InsnNode;
//import org.objectweb.asm.tree.JumpInsnNode;
//import org.objectweb.asm.tree.LabelNode;
//import org.objectweb.asm.tree.MethodInsnNode;
//import org.objectweb.asm.tree.MethodNode;
//import org.objectweb.asm.tree.VarInsnNode;
import cpw.mods.fml.relauncher.IClassTransformer;

/**
 * 
 * @author Sedridor
 * 
 * Magic happens in here. MAGIC.
 * Obfuscated names will have to be updated with each Obfuscation change.
 *
 */
public class MCE_Transformer implements IClassTransformer
{
	/* class net.minecraft.client.multiplayer.WorldClient */
	private final String classNameWorldClientObfusc = "bdt";
	private final String classNameWorldClient = "net.minecraft.client.multiplayer.WorldClient";
	/* class net.minecraft.world.WorldServer */
	private final String classNameWorldServerObfusc = "iz";
	private final String classNameWorldServer = "net.minecraft.world.WorldServer";
	/* class net.minecraft.client.gui.GuiSelectWorld */
	private final String classNameGuiSelectWorldObfusc = "axu";
	private final String classNameGuiSelectWorld = "net.minecraft.client.gui.GuiSelectWorld";
	/* class net.minecraft.client.renderer.entity.RenderItem */
	private final String classNameRenderItemObfusc = "bhj";
	private final String classNameRenderItem = "net.minecraft.client.renderer.entity.RenderItem";
	/* class net.minecraft.client.gui.inventory.GuiContainer */
	private final String classNameGuiContainerObfusc = "ayl";
	private final String classNameGuiContainer = "net.minecraft.client.gui.inventory.GuiContainer";
	//    /* class net.minecraft.item.ItemStack */
	//    private final String classNameItemStackObfusc = "wm";
	//    private final String classNameItemStack = "net.minecraft.item.ItemStack";
	//    /* class net.minecraft.item.EnumRarity */
	//    private final String classNameEnumRarityObfusc = "wx";
	//    private final String classNameEnumRarity = "net.minecraft.item.EnumRarity";

	@Override
	public byte[] transform(String name, String newName, byte[] bytes)
	{
		if (name.equals(classNameWorldClientObfusc))
		{
			return ClassOverrider.overrideBytes(name, bytes, "net/minecraft/client/multiplayer/WorldClient", MCE_CorePlugin.location);
			//return handleTransform1(bytes, true);
		}
		else if (name.equals(classNameWorldClient))
		{
			return ClassOverrider.overrideBytes(name, bytes, "net/minecraft/client/multiplayer/WorldClient", MCE_CorePlugin.location);
			//return handleTransform1(bytes, false);
		}
		if (name.equals(classNameWorldServerObfusc))
		{
			return ClassOverrider.overrideBytes(name, bytes, "net/minecraft/world/WorldServer", MCE_CorePlugin.location); // new File(MCE_Core.mc.mcDataDir, "coremods/MCE_Core.jar")
			//return handleTransform2(bytes, true);
		}
		else if (name.equals(classNameWorldServer))
		{
			return ClassOverrider.overrideBytes(name, bytes, "net/minecraft/world/WorldServer", MCE_CorePlugin.location); // new File("./coremods/MCE_Core_dummy.jar")
			//return handleTransform2(bytes, false);
		}
		if (name.equals(classNameGuiSelectWorldObfusc))
		{
			return ClassOverrider.overrideBytes(name, bytes, "net/minecraft/client/gui/GuiSelectWorld", MCE_CorePlugin.location);
			//return handleTransformGuiSelectWorld(bytes, true);
		}
		else if (name.equals(classNameGuiSelectWorld))
		{
			return ClassOverrider.overrideBytes(name, bytes, "net/minecraft/client/gui/GuiSelectWorld", MCE_CorePlugin.location);
			//return handleTransformGuiSelectWorld(bytes, false);
		}
		if (name.equals(classNameRenderItemObfusc))
		{
			return ClassOverrider.overrideBytes(name, bytes, "net/minecraft/client/renderer/entity/RenderItem", MCE_CorePlugin.location);
			//return handleTransformRenderItem(bytes, true);
		}
		else if (name.equals(classNameRenderItem))
		{
			return ClassOverrider.overrideBytes(name, bytes, "net/minecraft/client/renderer/entity/RenderItem", MCE_CorePlugin.location);
			//return handleTransformRenderItem(bytes, false);
		}
		if (name.equals(classNameGuiContainerObfusc))
		{
			return ClassOverrider.overrideBytes(name, bytes, "net/minecraft/client/gui/inventory/GuiContainer", MCE_CorePlugin.location);
		}
		else if (name.equals(classNameGuiContainer))
		{
			return ClassOverrider.overrideBytes(name, bytes, "net/minecraft/client/gui/inventory/GuiContainer", MCE_CorePlugin.location);
		}
		//        if (name.equals(classNameItemStackObfusc))
		//        {
		//            return ClassOverrider.getClassBytes(name, new File(MCE_Core.mc.mcDataDir, "coremods/MCE_Core.jar"));
		//        }
		//        else if (name.equals(classNameItemStack))
		//        {
		//            return ClassOverrider.getClassBytes(name, new File("./coremods/MCE_Core_dummy.jar"));
		//        }
		//        if (name.equals(classNameEnumRarityObfusc))
		//        {
		//            return ClassOverrider.getClassBytes(name, new File(MCE_Core.mc.mcDataDir, "coremods/MCE_Core.jar"));
		//        }
		//        else if (name.equals(classNameEnumRarity))
		//        {
		//            return ClassOverrider.getClassBytes(name, new File("./coremods/MCE_Core_dummy.jar"));
		//        }

		return bytes;
	}

//	private byte[] handleTransform1(byte[] bytes, boolean obfuscated)
//	{
//		System.out.println("* MCE Core transform running on WorldClient *");
//		ClassNode classNode = new ClassNode();
//		ClassReader classReader = new ClassReader(bytes);
//		classReader.accept(classNode, 0);
//
//		String methodName;
//		String methodDesc;
//		if (obfuscated)
//		{
//			methodName = "b";
//			methodDesc = "()V";
//		}
//		else
//		{
//			methodName = "tick";
//			methodDesc = "()V";
//		}
//
//		// find method to inject into
//		Iterator<MethodNode> methods = classNode.methods.iterator();
//		int indexMethod = 0;
//		while(methods.hasNext())
//		{
//			MethodNode m = methods.next();
//			if (m.name.equals(methodName) && m.desc.equals(methodDesc)) // public void tick()
//			{
//				indexMethod++;
//				System.out.println("In target method! Patching WorldClient! Method " + indexMethod + ", " + m.name);
//
//				AbstractInsnNode targetNode = null;
//				Iterator iter = m.instructions.iterator();
//				LabelNode label = new LabelNode();
//				int index = 0;
//				while (iter.hasNext())
//				{
//					index++;
//					targetNode = (AbstractInsnNode) iter.next();
//					//System.out.println("Opcode: " + index + ", " + targetNode.getOpcode());
//
//					if (targetNode.getOpcode() == -1 && index == 13)
//					{
//						// make new instruction list
//						InsnList toInject = new InsnList();
//
//						toInject.add(new VarInsnNode(ALOAD, 0));
//						toInject.add(new MethodInsnNode(INVOKESTATIC, "sedridor/mce/core/MCE_Core", "tickC", "()V"));
//						toInject.add(new JumpInsnNode(167, label));
//
//						// inject new instruction list into method instruction list
//						m.instructions.insertBefore(targetNode, toInject);
//					}
//					else if (targetNode.getOpcode() == -1 && index == 21)
//					{
//						// make new instruction list
//						InsnList toInject = new InsnList();
//						toInject.add(label);
//
//						// inject new instruction list into method instruction list
//						m.instructions.insertBefore(targetNode, toInject);
//						break;
//					}
//				}
//				//MCE_Core.saveData(ASMUtils.printInsnList(m.instructions), "MCE-preTickC.txt");
//
//
//				System.out.println("Patching Complete, target node was at index: " + index);
//				break;
//			}
//		}
//
//		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
//		classNode.accept(writer);
//		return writer.toByteArray();
//	}
//
//	private byte[] handleTransform2(byte[] bytes, boolean obfuscated)
//	{
//		System.out.println("* MCE Core transform running on WorldServer *");
//		ClassNode classNode = new ClassNode();
//		ClassReader classReader = new ClassReader(bytes);
//		classReader.accept(classNode, 0);
//
//		String methodName;
//		String methodDesc;
//		if (obfuscated)
//		{
//			methodName = "b";
//			methodDesc = "()V";
//		}
//		else
//		{
//			methodName = "tick";
//			methodDesc = "()V";
//		}
//
//		// find method to inject into
//		Iterator<MethodNode> methods = classNode.methods.iterator();
//		int indexMethod = 0;
//		while(methods.hasNext())
//		{
//			MethodNode m = methods.next();
//			if (m.name.equals(methodName) && m.desc.equals(methodDesc)) // public void tick()
//			{
//				indexMethod++;
//				System.out.println("In target method! Patching WorldServer! Method " + indexMethod + ", " + m.name);
//
//				AbstractInsnNode targetNode = null;
//				Iterator iter = m.instructions.iterator();
//				LabelNode label = new LabelNode();
//				int index = 0;
//				while (iter.hasNext())
//				{
//					index++;
//					targetNode = (AbstractInsnNode) iter.next();
//					//System.out.println("Opcode: " + index + ", " + targetNode.getOpcode());
//
//					if (targetNode.getOpcode() == -1 && index == 151)
//					{
//						// make new instruction list
//						InsnList toInject = new InsnList();
//
//						toInject.add(new VarInsnNode(ALOAD, 0));
//						toInject.add(new MethodInsnNode(INVOKESTATIC, "sedridor/mce/core/MCE_Core", "tickS", "()V"));
//						toInject.add(new JumpInsnNode(167, label));
//
//						// inject new instruction list into method instruction list
//						m.instructions.insertBefore(targetNode, toInject);
//					}
//					else if (targetNode.getOpcode() == -1 && index == 161)
//					{
//						// make new instruction list
//						InsnList toInject = new InsnList();
//						toInject.add(label);
//
//						// inject new instruction list into method instruction list
//						m.instructions.insertBefore(targetNode, toInject);
//						break;
//					}
//				}
//				//MCE_Core.saveData(ASMUtils.printInsnList(m.instructions), "MCE-preTickS.txt");
//
//
//				System.out.println("Patching Complete, target node was at index: " + index);
//				break;
//			}
//		}
//
//		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
//		classNode.accept(writer);
//		return writer.toByteArray();
//	}

//	private byte[] handleTransformGuiSelectWorld(byte[] bytes, boolean obfuscated)
//	{
//		System.out.println("* MCE Core transform running on GuiSelectWorld *");
//		ClassNode classNode = new ClassNode();
//		ClassReader classReader = new ClassReader(bytes);
//		classReader.accept(classNode, 0);
//
//		String methodName;
//		String methodDesc;
//		if (obfuscated)
//		{
//			methodName = "h";
//			methodDesc = "(Laxu;)Ljava/text/DateFormat;";
//		}
//		else
//		{
//			methodName = "func_82315_h";
//			methodDesc = "(Lnet/minecraft/client/gui/GuiSelectWorld;)Ljava/text/DateFormat;";
//		}
//
//		// find method to inject into
//		Iterator<MethodNode> methods = classNode.methods.iterator();
//		int indexMethod = 0;
//		while(methods.hasNext())
//		{
//			MethodNode m = methods.next();
//			// public DateFormat func_82315_h(GuiSelectWorld par0GuiSelectWorld)
//			if (m.name.equals(methodName) && m.desc.equals(methodDesc))
//			{
//				indexMethod++;
//				System.out.println("In target method! Patching GuiSelectWorld! Method " + indexMethod + ", " + m.name);
//
//				m.instructions.remove(m.instructions.get(2));
//				m.instructions.remove(m.instructions.get(2));
//				m.instructions.remove(m.instructions.get(2));
//				// make new instruction list
//				InsnList toInject = new InsnList();
//
//				toInject.add(new VarInsnNode(25, 0));
//				toInject.add(new MethodInsnNode(INVOKESTATIC, "sedridor/mce/core/MCE_Features", "func_82315_h", methodDesc));
//				toInject.add(new InsnNode(176));
//
//				// inject new instruction list into method instruction list
//				m.instructions.insert(m.instructions.get(1), toInject);
//				//MCE_Core.saveData(ASMUtils.printInsnList(m.instructions), "MCE-GuiSelectWorld.txt");
//
//				System.out.println("Patching Complete, target node was at index: 2");
//				break;
//			}
//		}
//
//		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
//		classNode.accept(writer);
//		return writer.toByteArray();
//	}
//
//	private byte[] handleTransformRenderItem(byte[] bytes, boolean obfuscated)
//	{
//		System.out.println("* MCE Core transform running on RenderItem *");
//		ClassNode classNode = new ClassNode();
//		ClassReader classReader = new ClassReader(bytes);
//		classReader.accept(classNode, 0);
//
//		String methodName;
//		String methodDesc;
//		if (obfuscated)
//		{
//			methodName = "a";
//			methodDesc = "(Lawv;Lbgf;Lwm;IILjava/lang/String;)V";
//		}
//		else
//		{
//			methodName = "renderItemOverlayIntoGUI";
//			methodDesc = "(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/RenderEngine;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V";
//		}
//
//		// find method to inject into
//		Iterator<MethodNode> methods = classNode.methods.iterator();
//		int indexMethod = 0;
//		while(methods.hasNext())
//		{
//			MethodNode m = methods.next();
//			// public void renderItemOverlayIntoGUI(FontRenderer par1FontRenderer, RenderEngine par2RenderEngine, ItemStack par3ItemStack, int par4, int par5, String par6Str)
//			if (m.name.equals(methodName) && m.desc.equals(methodDesc))
//			{
//				indexMethod++;
//				System.out.println("In target method! Patching RenderItem! Method " + indexMethod + ", " + m.name);
//				LabelNode label = new LabelNode();
//
//				// make new instruction list
//				InsnList toInject = new InsnList();
//
//				toInject.add(new VarInsnNode(25, 1));
//				toInject.add(new VarInsnNode(25, 2));
//				toInject.add(new VarInsnNode(25, 3));
//				toInject.add(new VarInsnNode(21, 4));
//				toInject.add(new VarInsnNode(21, 5));
//				toInject.add(new VarInsnNode(25, 6));
//				toInject.add(new MethodInsnNode(INVOKESTATIC, "sedridor/mce/core/MCE_Features", "renderItemOverlayIntoGUI", methodDesc));
//				toInject.add(new InsnNode(177));
//				toInject.add(label);
//
//				// inject new instruction list into method instruction list
//				m.instructions.insertBefore(m.instructions.get(2), toInject);
//				//MCE_Core.saveData(ASMUtils.printInsnList(m.instructions), "MCE-RenderItem.txt");
//
//				System.out.println("Patching Complete, target node was at index: 0");
//				break;
//			}
//		}
//
//		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
//		classNode.accept(writer);
//		return writer.toByteArray();
//	}
}
