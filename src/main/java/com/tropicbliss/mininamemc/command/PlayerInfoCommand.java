package com.tropicbliss.mininamemc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.tropicbliss.mininamemc.util.RequestHandler;
import com.tropicbliss.mininamemc.util.exceptions.PlayerNotFoundException;
import com.tropicbliss.mininamemc.util.exceptions.RequestException;
import com.tropicbliss.mininamemc.util.mojang.MojangResponse;
import com.tropicbliss.mininamemc.util.mojang.MojangResponse.Textures;
import com.tropicbliss.mininamemc.util.mojang.MojangResponse.Username;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.ClickEvent.Action;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class PlayerInfoCommand {

  public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
      boolean dedicated) {
    dispatcher.register(CommandManager.literal("player")
        .then(CommandManager.literal("namemc").then(CommandManager.argument("player",
            StringArgumentType.word()).executes(
            context -> run(context.getSource(),
                StringArgumentType.getString(context, "player"))))));
  }

  public static int run(ServerCommandSource source, String name)
      throws CommandSyntaxException {
    try {
      RequestHandler handler = RequestHandler.getInstance();
      MojangResponse response = handler.sendMojangRequest(name);
      source.sendFeedback(new LiteralText("UUID: " + response.getUuid()), true);
      source.sendFeedback(new LiteralText(""), true);
      Username[] usernameHistory = response.getUsernameHistory();
      if (usernameHistory.length != 0) {
        source.sendFeedback(new LiteralText("Username history:"), true);
        int count = 0;
        for (Username n : usernameHistory) {
          count++;
          source.sendFeedback(new LiteralText(count + ":"), true);
          source.sendFeedback(new LiteralText("Username: " + n.getUsername()), true);
          if (n.getChangedAt() != null) {
            source.sendFeedback(new LiteralText("Changed at: " + n.getChangedAt()), true);
          }
          source.sendFeedback(new LiteralText(""), true);
        }
      }
      Textures textures = response.getTextures();
      source.sendFeedback(new LiteralText("Custom: " + textures.isCustom()), true);
      source.sendFeedback(new LiteralText("Slim: " + textures.isSlim()), true);
      source.sendFeedback(new LiteralText(""), true);
      source.sendFeedback(new LiteralText("Skin URL:"), true);
      source.sendFeedback(
          new LiteralText(textures.getSkin().getUrl()).formatted(Formatting.UNDERLINE,
              Formatting.AQUA).styled(style -> style.withClickEvent(
              new ClickEvent(Action.OPEN_URL, textures.getSkin().getUrl()))), true);
      if (textures.getCape() != null) {
        source.sendFeedback(new LiteralText(""), true);
        source.sendFeedback(new LiteralText("Cape URL:"), true);
        source.sendFeedback(
            new LiteralText(textures.getCape().getUrl()).formatted(Formatting.UNDERLINE,
                Formatting.AQUA).styled(style -> style.withClickEvent(
                new ClickEvent(Action.OPEN_URL, textures.getCape().getUrl()))), true);
      }
      if (response.getCreatedAt() != null) {
        source.sendFeedback(new LiteralText(""), true);
        source.sendFeedback(new LiteralText("Created at: " + response.getCreatedAt()), true);
      }
    } catch (PlayerNotFoundException | RequestException e) {
      throw new SimpleCommandExceptionType(new LiteralText(e.getMessage())).create();
    } catch (Exception e) {
      throw new SimpleCommandExceptionType(new LiteralText("An exception occurred")).create();
    }
    return 1;
  }
}
