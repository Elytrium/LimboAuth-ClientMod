/*
 * Copyright (C) 2023 Elytrium
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.elytrium.limboauth.clientmod;

import dev.architectury.networking.NetworkChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.elytrium.limboauth.clientmod.message.TokenMessage;
import net.minecraft.resources.ResourceLocation;

public class ModCommon {
  public static final String MOD_ID = "limboauth-clientmod";
  public static final Path MOD_CONFIG = Paths.get("config", "limboauth.yml");
  public static final NetworkChannel MOD_CHANNEL = NetworkChannel.create(new ResourceLocation("limboauth", "mod"));

  public static void init() {
    Settings.IMP.reload(MOD_CONFIG);
    MOD_CHANNEL.register(TokenMessage.class, TokenMessage::encode, TokenMessage::new, TokenMessage::apply);
  }
}
