/*
 * This file is part of Coins.
 *
 * Copyright © 2017 Beelzebu
 * Coins is licensed under the GNU General Public License.
 *
 * Coins is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Coins is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.nifheim.beelzebu.coins.bungee;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import net.nifheim.beelzebu.coins.core.Core;
import net.nifheim.beelzebu.coins.core.MethodInterface;
import net.nifheim.beelzebu.coins.core.utils.IConfiguration;

/**
 *
 * @author Beelzebu
 */
public class BungeeMethods implements MethodInterface {

    private final Main plugin = Main.getInstance();
    private final CommandSender console = ProxyServer.getInstance().getConsole();

    @Override
    public Object getPlugin() {
        return plugin;
    }

    @Override
    public IConfiguration getConfig() {
        return plugin.getConfiguration();
    }

    @Override
    public Object getMessages() {
        return null;
    }

    @Override
    public String getString(Object player, String path) {
        try {
            path = Core.getInstance().rep(((Configuration) getMessages()).getString(path));
        } catch (NullPointerException ex) {
            log("The string " + path + " does not exists in the messages file, please add this manually.");
            log("If you belive that this is an error please contact to the developer.");
            Core.getInstance().debug(ex.getCause().getMessage());
            path = "";
        }
        return path;
    }

    @Override
    public void runAsync(Runnable rn) {
        ProxyServer.getInstance().getScheduler().runAsync((Plugin) getPlugin(), rn);
    }

    @Override
    public void runAsync(Runnable rn, Long timer) {
        ProxyServer.getInstance().getScheduler().schedule((Plugin) getPlugin(), rn, timer, TimeUnit.MINUTES);
    }

    @Override
    public void runSync(Runnable rn) {
        rn.run();
    }

    @Override
    public void executeCommand(String cmd) {

    }

    @Override
    public void log(Object log) {
        console.sendMessage(Core.getInstance().rep("&8[&cCoins&8] &7" + log));
    }

    @Override
    public String getNick(Object player) {
        return ((ProxiedPlayer) player).getName();
    }

    @Override
    public String getNick(UUID uuid) {
        return ProxyServer.getInstance().getPlayer(uuid).getName();
    }

    @Override
    public UUID getUUID(Object player) {
        return ((ProxiedPlayer) player).getUniqueId();
    }

    @Override
    public UUID getUUID(String player) {
        return ProxyServer.getInstance().getPlayer(player).getUniqueId();
    }

    @Override
    public Object getConsole() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendMessage(Object CommandSender, String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public File getDataFolder() {
        return plugin.getDataFolder();
    }

    @Override
    public InputStream getResource(String file) {
        return plugin.getResourceAsStream(file);
    }
}
