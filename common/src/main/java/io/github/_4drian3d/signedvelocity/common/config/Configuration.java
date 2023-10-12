package io.github._4drian3d.signedvelocity.common.config;

import io.github._4drian3d.signedvelocity.common.MessageMode;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class Configuration {
    public final Redis redis = new Redis();
    @Comment(
            """
                    The message mode to use for sending information between the proxy and the server.
                    You can choose between PLUGIN_MESSAGE and REDIS.
                    """
    )
    private MessageMode messageMode = MessageMode.PLUGIN_MESSAGE;

    public MessageMode messageMode() {
        return messageMode;
    }

    @ConfigSerializable
    public static class Redis {
        @Comment(
                """
                        The host of the redis server.
                        """
        )
        private String host = "localhost";

        @Comment(
                """
                        The port of the redis server.
                        """
        )
        private int port = 6379;

        @Comment(
                """
                        The password of the redis server.
                        """
        )
        private String password = "";

        @Comment(
                """
                        The channel to use for sending information between the proxy and the server.
                        """
        )
        private String channel = "signedvelocity";

        public String host() {
            return host;
        }

        public int port() {
            return port;
        }

        public String password() {
            return password;
        }

        public String channel() {
            return channel;
        }
    }
}
