package com.datasiqn.arcadia.npc;

import com.datasiqn.arcadia.Arcadia;
import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public final class CreatedNpc {
    private final long id;
    private final ArcadiaNpc npc;
    private final ServerPlayer serverPlayer;

    private boolean shown = false;

    public CreatedNpc(long id, ArcadiaNpc npc, ServerPlayer serverPlayer) {
        this.id = id;
        this.npc = npc;
        this.serverPlayer = serverPlayer;
    }

    public void show() {
        shown = true;
    }

    public void hide() {
        shown = false;
    }

    public long getId() {
        return id;
    }

    public ArcadiaNpc getNpc() {
        return npc;
    }

    public ServerPlayer getPlayer() {
        return serverPlayer;
    }

    public boolean isShown() {
        return shown;
    }

    public @NotNull JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("shown", shown);
        jsonObject.add("npc", npc.toJson());
        return jsonObject;
    }

    public static @NotNull CompletableFuture<CreatedNpc> fromJson(Arcadia plugin, @NotNull JsonObject jsonObject) {
        ArcadiaNpc npc = ArcadiaNpc.fromJson(jsonObject.get("npc").getAsJsonObject());
        return npc.createFakePlayer(plugin)
                .thenApply(serverPlayer -> {
                    CreatedNpc createdNpc = new CreatedNpc(jsonObject.get("id").getAsLong(), npc, serverPlayer);
                    createdNpc.shown = jsonObject.get("shown").getAsBoolean();
                    return createdNpc;
                });
    }
}
