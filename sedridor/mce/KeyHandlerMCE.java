package sedridor.mce;

import sedridor.mce.proxy.ClientProxy;
import java.util.EnumSet;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class KeyHandlerMCE extends KeyHandler
{
	private Minecraft mc;
	private ClientProxy proxy;

	private static KeyBinding keyUp = new KeyBinding("Movement Up", Keyboard.KEY_UP);
	private static KeyBinding keyDown = new KeyBinding("Movement Down", Keyboard.KEY_DOWN);
	private static KeyBinding keyRight = new KeyBinding("Movement Right", Keyboard.KEY_RIGHT);
	private static KeyBinding keyLeft = new KeyBinding("Movement Left", Keyboard.KEY_LEFT);

	private static KeyBinding[] keyBindings = new KeyBinding[] {keyUp, keyDown, keyRight, keyLeft};
	private static boolean[] keyBooleans = new boolean[] {true, true, true, true};

	private static boolean keyPressed;
	public static int moveForward = 0;
	private static boolean fixFlightSpeed;

	public KeyHandlerMCE(ClientProxy proxy)
	{
		super(keyBindings, keyBooleans);
		this.mc = Minecraft.getMinecraft();
		this.proxy = proxy;
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
	{
		if (MCE_Settings.ArrowsMovement.equalsIgnoreCase("yes"))
		{
			//do whatever
			if (types.contains(TickType.CLIENT)) {
				// make sure not in GUI element (e.g. chat box)
				if (tickEnd)
				{
					if (this.mc.currentScreen == null)
					{
						if (!(this.mc.thePlayer.movementInput instanceof MovementInputFromOptionsMCE))
						{
							this.mc.thePlayer.movementInput = new MovementInputFromOptionsMCE(this.mc.gameSettings);
						}
						if (kb == keyUp)
						{
							KeyHandlerMCE.moveForward = 1;
						}
						if (kb == keyDown)
						{
							KeyHandlerMCE.moveForward = -1;
						}
						if (kb == keyRight)
						{
							this.mc.thePlayer.setAngles(MCE_Settings.ArrowsTurnrate, 0);
						}
						if (kb == keyLeft)
						{
							this.mc.thePlayer.setAngles(-MCE_Settings.ArrowsTurnrate, 0);
						}
						if (!Keyboard.isKeyDown(Keyboard.KEY_UP) && !Keyboard.isKeyDown(Keyboard.KEY_DOWN))
						{
							KeyHandlerMCE.moveForward = 0;
						}
					}
					else
					{
						KeyHandlerMCE.moveForward = 0;
					}
				}
			}
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
	{
		// do nothing
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		// keys should be handled in game in the client
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return "MCE Key Bindings";
	}
}
