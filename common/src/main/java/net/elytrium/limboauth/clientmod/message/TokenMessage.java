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

package net.elytrium.limboauth.clientmod.message;

import dev.architectury.networking.NetworkManager;
import java.util.Base64;
import java.util.function.Supplier;
import net.elytrium.limboauth.clientmod.ModCommon;
import net.elytrium.limboauth.clientmod.Settings;
import net.elytrium.limboauth.clientmod.utils.DomainUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.FriendlyByteBuf;

public class TokenMessage {

  private final String token;

  public TokenMessage(FriendlyByteBuf buf) {
    if (buf.readableBytes() == 0) {
      this.token = null;
    } else {
      byte[] token = new byte[buf.readableBytes()];
      buf.readBytes(token);

      this.token = Base64.getEncoder().encodeToString(token);
    }
  }

  public TokenMessage(String token) {
    this.token = token;
  }

  public void encode(FriendlyByteBuf buf) {
    buf.writeBytes(Base64.getDecoder().decode(this.token));
  }

  public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
    ServerData server = Minecraft.getInstance().getCurrentServer();
    if (server != null) {
      String ip = DomainUtils.escapeIp(server.ip);
      if (this.token == null || this.token.isEmpty()) {
        String token = Settings.IMP.TOKENS.get(ip);
        if (token != null) {
          ModCommon.MOD_CHANNEL.sendToServer(new TokenMessage(token));
        }
      } else {
        Settings.IMP.TOKENS.put(ip, this.token);
        Settings.IMP.save(ModCommon.MOD_CONFIG);
      }
    }
  }
}
