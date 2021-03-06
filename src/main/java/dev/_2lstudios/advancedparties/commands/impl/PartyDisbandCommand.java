package dev._2lstudios.advancedparties.commands.impl;

import dev._2lstudios.advancedparties.api.events.PartyDisbandEvent;
import dev._2lstudios.advancedparties.commands.Command;
import dev._2lstudios.advancedparties.commands.CommandContext;
import dev._2lstudios.advancedparties.commands.CommandListener;
import dev._2lstudios.advancedparties.parties.Party;
import dev._2lstudios.advancedparties.parties.PartyDisbandReason;
import dev._2lstudios.advancedparties.players.PartyPlayer;

@Command(
  name = "disband"
)
public class PartyDisbandCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        PartyPlayer player = ctx.getPlayer();
        Party party = player.getParty();

        if (party != null) {
            if (party.isLeader(player)) {
                PartyDisbandEvent event = new PartyDisbandEvent(party, player);
                if (ctx.getPlugin().callEvent(event)) {
                    player.setParty(null);
                    party.disband(PartyDisbandReason.BY_LEADER);
                    player.sendI18nMessage("disband.disbanded");
                }
            } else {
                player.sendI18nMessage("disband.not-leader");
            }
        } else {
            player.sendI18nMessage("common.not-in-party");
        }
    }
}
