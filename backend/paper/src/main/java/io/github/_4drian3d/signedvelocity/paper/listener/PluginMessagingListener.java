package io.github._4drian3d.signedvelocity.paper.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import io.github._4drian3d.signedvelocity.common.SignedQueue;
import io.github._4drian3d.signedvelocity.common.SignedResult;
import io.github._4drian3d.signedvelocity.paper.SignedVelocity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class PluginMessagingListener implements PluginMessageListener {
    private final SignedVelocity plugin;

    public PluginMessagingListener(final SignedVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(
            @NotNull final String channel,
            @NotNull final Player player,
            final byte@NotNull[] message
    ) {
        if (!Objects.equals(channel, SignedVelocity.CHANNEL)) {
            return;
        }
        @SuppressWarnings("UnstableApiUsage")
        final ByteArrayDataInput input = ByteStreams.newDataInput(message);

        final UUID playerId = UUID.fromString(input.readUTF());
        final String source = input.readUTF();
        final String result = input.readUTF();

        final SignedQueue queue = switch (source) {
            case "COMMAND_RESULT" -> plugin.getCommandQueue();
            case "CHAT_RESULT" -> plugin.getChatQueue();
            default -> throw new IllegalArgumentException("Invalid source " + source);
        };
        final SignedResult resulted = switch (result) {
            case "CANCEL" -> SignedResult.cancel();
            case "MODIFY" -> SignedResult.modify(input.readUTF());
            case "ALLOWED" -> SignedResult.allowed();
            default -> throw new IllegalArgumentException("Invalid result " + result);
        };
        queue.dataFrom(playerId).complete(resulted);
    }
}
