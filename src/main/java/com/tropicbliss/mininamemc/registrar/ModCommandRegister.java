package com.tropicbliss.mininamemc.registrar;

import com.tropicbliss.mininamemc.command.PlayerInfoCommand;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class ModCommandRegister {

  public static void registerCommands() {
    CommandRegistrationCallback.EVENT.register(PlayerInfoCommand::register);
  }
}
