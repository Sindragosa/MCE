package sedridor.mce;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.network.packet.Packet130UpdateSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

public class GuiEditSignMCE extends GuiScreen
{
	/**
	 * This String is just a local copy of the characters allowed in text rendering of minecraft.
	 */
	private static final String allowedCharacters = ChatAllowedCharacters.allowedCharacters;

	/** The title string that is displayed in the top-center of the screen. */
	protected String screenTitle = "Edit sign message:";

	/** Reference to the sign object. */
	private TileEntitySign entitySign;

	/** Counts the number of screen updates. */
	private int updateCounter;

	/** The number of the line that is being edited. */
	private int editLine = 0;
	private GuiButton field_100001_o;

	protected GuiTextField[] guiTextField = new GuiTextField[4];
	protected GuiButton[] button = new GuiButton[16];
	public boolean showColors = false;

	public GuiEditSignMCE(TileEntitySign par1TileEntitySign)
	{
		this.entitySign = par1TileEntitySign;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui()
	{
		this.buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		this.entitySign.setEditable(false);
		this.guiTextField[0] = new GuiTextField(this.fontRenderer, this.width / 2 - 60, this.height / 3 - 18, 120, 17);
		this.guiTextField[1] = new GuiTextField(this.fontRenderer, this.width / 2 - 60, this.height / 3 + 3, 120, 17);
		this.guiTextField[2] = new GuiTextField(this.fontRenderer, this.width / 2 - 60, this.height / 3 + 23, 120, 17);
		this.guiTextField[3] = new GuiTextField(this.fontRenderer, this.width / 2 - 60, this.height / 3 + 43, 120, 17);
		int var1;

		for (var1 = 0; var1 < 4; ++var1)
		{
			this.guiTextField[var1].setText(this.entitySign.signText[var1]);
			this.guiTextField[var1].setMaxStringLength(15);
			this.guiTextField[var1].setCanLoseFocus(true);
		}

		this.guiTextField[0].getEnableBackgroundDrawing();
		this.setFocus(0);
		this.buttonList.add(this.field_100001_o = new GuiButton(0, this.width / 2 - 60, this.height / 3 + 63, 120, 20, "Done"));
		this.buttonList.add(new GuiButton(1, this.width / 2 + 82, this.height / 3 - 20, 40, 20, "Copy"));
		this.buttonList.add(new GuiButton(2, this.width / 2 + 82, this.height / 3 + 0, 40, 20, "Paste"));
		var1 = 5;
		int var2 = 0;
		int[] var3 = new int[] {82, 92, 102, 112};
		int var4 = 62;
		String[] var5 = new String[] {"0", "1", "2", "3", "8", "9", "a", "b", "4", "5", "6", "7", "c", "d", "e", "f"};

		for (int var6 = 0; var6 < 16; ++var6)
		{
			this.button[var6] = new GuiButton(var1, this.width / 2 + var3[var2], this.height / 3 + var4, 9, 10, " \u00a7" + var5[var6] + "\u2588");
			this.button[var6].drawButton = false;
			this.buttonList.add(this.button[var6]);
			++var1;

			if (var2 == 3)
			{
				var2 = 0;
				var4 += 10;
			}
			else
			{
				++var2;
			}
		}

		this.buttonList.add(new GuiButton(3, this.width / 2 + 62, this.height / 3 + 63, 20, 20, "\u00a74\u2022\u00a72\u2022\u00a76\u2022\u00a7b\u2022"));
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat events
	 */
	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
		NetClientHandler var1 = this.mc.getNetHandler();

		if (var1 != null)
		{
			var1.addToSendQueue(new Packet130UpdateSign(this.entitySign.xCoord, this.entitySign.yCoord, this.entitySign.zCoord, this.entitySign.signText));
		}

		this.entitySign.setEditable(true);
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen()
	{
		++this.updateCounter;
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
	 */
	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		int var2;
		if (par1GuiButton.enabled && par1GuiButton.id == 0)
		{
			for (var2 = 0; var2 < 4; ++var2)
			{
				this.entitySign.signText[var2] = this.guiTextField[var2].getText().replace("\u00a7", "&");
			}

			this.entitySign.onInventoryChanged();
			this.mc.displayGuiScreen((GuiScreen)null);
		}
		else if (par1GuiButton.id == 1)
		{
			this.copy();
		}
		else if (par1GuiButton.id == 2)
		{
			this.paste();
		}
		else if (par1GuiButton.id == 3)
		{
			this.showColors = !this.showColors;

			for (var2 = 0; var2 < 16; ++var2)
			{
				this.button[var2].drawButton = this.showColors;
			}
		}
		else if (par1GuiButton.id == 5)
		{
			this.setColor("0");
		}
		else if (par1GuiButton.id == 6)
		{
			this.setColor("1");
		}
		else if (par1GuiButton.id == 7)
		{
			this.setColor("2");
		}
		else if (par1GuiButton.id == 8)
		{
			this.setColor("3");
		}
		else if (par1GuiButton.id == 9)
		{
			this.setColor("8");
		}
		else if (par1GuiButton.id == 10)
		{
			this.setColor("9");
		}
		else if (par1GuiButton.id == 11)
		{
			this.setColor("a");
		}
		else if (par1GuiButton.id == 12)
		{
			this.setColor("b");
		}
		else if (par1GuiButton.id == 13)
		{
			this.setColor("4");
		}
		else if (par1GuiButton.id == 14)
		{
			this.setColor("5");
		}
		else if (par1GuiButton.id == 15)
		{
			this.setColor("6");
		}
		else if (par1GuiButton.id == 16)
		{
			this.setColor("7");
		}
		else if (par1GuiButton.id == 17)
		{
			this.setColor("c");
		}
		else if (par1GuiButton.id == 18)
		{
			this.setColor("d");
		}
		else if (par1GuiButton.id == 19)
		{
			this.setColor("e");
		}
		else if (par1GuiButton.id == 20)
		{
			this.setColor("f");
		}
	}

	/**
	 * Called when the mouse is clicked.
	 */
	@Override
	protected void mouseClicked(int var1, int var2, int var3)
	{
		super.mouseClicked(var1, var2, var3);
		int[] var4 = new int[] {this.height / 3 - 18, this.height / 3 + 3, this.height / 3 + 23, this.height / 3 + 43};
		int var5 = this.width / 2 - 60;

		for (int var6 = 0; var6 < 4; ++var6)
		{
			boolean var7 = var1 >= var5 && var1 < var5 + 120 && var2 >= var4[var6] && var2 < var4[var6] + 17;

			if (var7)
			{
				this.setFocus(var6);

				if (this.guiTextField[var6].isFocused() && var3 == 0)
				{
					int var8 = var1 - var5;

					if (this.guiTextField[var6].getEnableBackgroundDrawing())
					{
						var8 -= 4;
					}

					String var9 = this.fontRenderer.trimStringToWidth(this.guiTextField[var6].getText().substring(0), this.guiTextField[var6].getWidth());
					this.guiTextField[var6].setCursorPosition(this.fontRenderer.trimStringToWidth(var9, var8).length() + 0);
				}
			}
		}
	}

	protected void setColor(String par1)
	{
		par1 = "\u00a7" + par1;
		for (int var2 = 0; var2 < 4; ++var2)
		{
			if (this.guiTextField[var2].isFocused())
			{
				StringBuffer var3 = new StringBuffer(this.guiTextField[var2].getText());
				var3.insert(this.guiTextField[var2].getCursorPosition(), par1);
				this.guiTextField[var2].setText(var3.toString());
				this.entitySign.signText[var2] = this.guiTextField[var2].getText();
			}
		}
	}

	protected void copy()
	{
		String var1 = "";

		for (int var2 = 0; var2 < 4; ++var2)
		{
			var1 = var1 + this.entitySign.signText[var2] + "\n";
		}

		GuiScreen.setClipboardString(var1);
	}

	protected void paste()
	{
		String var1 = GuiScreen.getClipboardString().replaceAll("\r", "\n");
		var1 = var1.replaceAll("\n\n", "\n");
		String[] var2 = var1.split("\n", 60);
		int var3 = var2.length;
		boolean var4 = false;

		if (var3 >= 1)
		{
			this.guiTextField[0].setText(var2[0]);
			this.entitySign.signText[0] = this.guiTextField[0].getText();
		}

		if (var3 >= 2)
		{
			this.guiTextField[1].setText(var2[1]);
			this.entitySign.signText[1] = this.guiTextField[1].getText();
		}

		if (var3 >= 3)
		{
			this.guiTextField[2].setText(var2[2]);
			this.entitySign.signText[2] = this.guiTextField[2].getText();
		}

		if (var3 >= 4)
		{
			this.guiTextField[3].setText(var2[3]);
			this.entitySign.signText[3] = this.guiTextField[3].getText();
		}
	}

	/**
	 * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
	 */
	@Override
	protected void keyTyped(char par1, int par2)
	{
		int var3;
		if (par1 == 33)
		{
			for (var3 = 0; var3 < 4; ++var3)
			{
				this.entitySign.signText[var3] = this.guiTextField[var3].getText().replace("\u00a7", "&");
			}
		}

		if (par1 == 22 && par2 == 47)
		{
			for (var3 = 0; var3 < 4; ++var3)
			{
				if (this.guiTextField[var3].isFocused())
				{
					this.guiTextField[var3].writeText("");
					this.guiTextField[var3].setText(this.guiTextField[var3].getText() + GuiScreen.getClipboardString());
				}
			}
		}
		else
		{
			if (par2 == 199)
			{
				this.copy();
			}
			else if (par2 == 207)
			{
				this.paste();
			}
			else
			{
				if (par2 == 1)
				{
					for (var3 = 0; var3 < 4; ++var3)
					{
						this.entitySign.signText[var3] = this.guiTextField[var3].getText();
					}

					this.entitySign.onInventoryChanged();
					this.mc.displayGuiScreen((GuiScreen)null);
				}
				else
				{
					for (var3 = 0; var3 < 4; ++var3)
					{
						if (this.guiTextField[var3].isFocused())
						{
							this.guiTextField[var3].textboxKeyTyped(par1, par2);
							this.entitySign.signText[var3] = this.guiTextField[var3].getText();
						}
					}
				}

				int var4;
				int var5;

				if (par2 == 28 || par2 == 15 || par2 == 208)
				{
					var3 = 0;

					for (var4 = 0; var4 < 4; ++var4)
					{
						if (this.guiTextField[var4].isFocused())
						{
							var5 = var4 + 1;

							if (var4 >= 3)
							{
								var5 = 0;
							}

							this.setFocus(var5);
							break;
						}

						++var3;
					}

					if (var3 >= 4)
					{
						this.setFocus(0);
					}
				}

				if (par2 == 200)
				{
					var3 = 0;

					for (var4 = 0; var4 < 4; ++var4)
					{
						if (this.guiTextField[var4].isFocused())
						{
							var5 = var4 - 1;

							if (var4 <= 0)
							{
								var5 = 3;
							}

							this.setFocus(var5);
							break;
						}

						++var3;
					}

					if (var3 >= 4)
					{
						this.setFocus(3);
					}
				}
			}
		}
	}

	protected void setFocus(int par1)
	{
		for (int var2 = 0; var2 < 4; ++var2)
		{
			if (var2 == par1)
			{
				this.guiTextField[var2].setFocused(true);
			}
			else
			{
				this.guiTextField[var2].setFocused(false);
				this.guiTextField[var2].setCanLoseFocus(true);
			}
		}

		this.guiTextField[par1].setFocused(true);
		this.guiTextField[par1].setCanLoseFocus(false);
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		for (int var4 = 0; var4 < 4; ++var4)
		{
			this.guiTextField[var4].drawTextBox();
		}

		TileEntityRenderer.instance.renderTileEntityAt(this.entitySign, -0.5D, -0.75D, -0.5D, 0.0F);
		this.fontRenderer.drawStringWithShadow("Edit Sign", this.width / 2 - 60, this.height / 3 - 30, 16775920);
		this.fontRenderer.drawStringWithShadow("" + (15 - this.entitySign.signText[0].length()), this.width / 2 + 64, this.height / 3 - 13, 11250603);
		this.fontRenderer.drawStringWithShadow("" + (15 - this.entitySign.signText[1].length()), this.width / 2 + 64, this.height / 3 + 7, 11250603);
		this.fontRenderer.drawStringWithShadow("" + (15 - this.entitySign.signText[2].length()), this.width / 2 + 64, this.height / 3 + 27, 11250603);
		this.fontRenderer.drawStringWithShadow("" + (15 - this.entitySign.signText[3].length()), this.width / 2 + 64, this.height / 3 + 47, 11250603);
		super.drawScreen(par1, par2, par3);
	}
}
