package me.jumper251.replay.replaysystem.recording;


import me.jumper251.replay.listener.AbstractListener;
import me.jumper251.replay.replaysystem.data.types.InvData;
import me.jumper251.replay.replaysystem.data.types.MetadataUpdate;
import me.jumper251.replay.replaysystem.utils.NPCManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class CompListener extends AbstractListener {

    private PacketRecorder packetRecorder;

    public CompListener(PacketRecorder packetRecorder) {
        super();

        this.packetRecorder = packetRecorder;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onSwap(PlayerSwapHandItemsEvent e) {
        Player p = e.getPlayer();
        if (this.packetRecorder.getRecorder().getPlayers().contains(p.getName())) {

            InvData data = NPCManager.copyFromPlayer(p, true, true);
            data.setMainHand(NPCManager.fromItemStack(e.getMainHandItem()));
            data.setOffHand(NPCManager.fromItemStack(e.getOffHandItem()));

            this.packetRecorder.addData(p.getName(), data);
        }

    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onGlide(EntityToggleGlideEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            PlayerWatcher watcher = this.packetRecorder.getRecorder().getData().getWatcher(p.getName());

            if (this.packetRecorder.getRecorder().getPlayers().contains(p.getName())) {
                watcher.setElytra(!p.isGliding());

                this.packetRecorder.addData(p.getName(), new MetadataUpdate(watcher.isBurning(), watcher.isBlocking(), watcher.isElytra()));
            }

        }
    }

}
