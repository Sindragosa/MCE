package sedridor.mce.core;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
/*
 * Don't let any access transformer stuff accidentally modify our classes
 * A list of package prefixes for FML to ignore
 */
@TransformerExclusions({"sedridor.mce.core"})
public class MCE_CorePlugin implements IFMLLoadingPlugin
{
	public static File location;

	@Override
	public String[] getLibraryRequestClass()
	{
		return null;
	}

	@Override
	public String[] getASMTransformerClass()
	{
		return new String[] { "sedridor.mce.core.MCE_Transformer" };
	}

	@Override
	public String getModContainerClass()
	{
		return "sedridor.mce.core.MCE_Core";
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
		location = (File)data.get("coremodLocation");
	}
}
