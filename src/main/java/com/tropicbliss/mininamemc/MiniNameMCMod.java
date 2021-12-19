package com.tropicbliss.mininamemc;

import com.tropicbliss.mininamemc.registrar.ModCommandRegister;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MiniNameMCMod implements ModInitializer {

  public static final Logger LOGGER = LogManager.getLogger("mininamemc");

  @Override
  public void onInitialize() {
    ModCommandRegister.registerCommands();
    LOGGER.info("Hello Fabric world!");
  }
}
