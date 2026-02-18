package com.example.foroHub.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class RsaKeyGenerator {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();

        RSAPrivateKey priv = (RSAPrivateKey) kp.getPrivate();
        RSAPublicKey pub = (RSAPublicKey) kp.getPublic();

        Path resources = Paths.get("src/main/resources");
        Files.createDirectories(resources);

        Path privPath = resources.resolve("app.key");
        Path pubPath  = resources.resolve("app.pub");

        writePem(privPath, "PRIVATE KEY", priv.getEncoded());
        writePem(pubPath, "PUBLIC KEY", pub.getEncoded());

        System.out.println("OK -> " + privPath.toAbsolutePath());
        System.out.println("OK -> " + pubPath.toAbsolutePath());
    }

    private static void writePem(Path path, String type, byte[] derBytes) throws IOException {
        String b64 = Base64.getMimeEncoder(64, "\n".getBytes(StandardCharsets.UTF_8))
                .encodeToString(derBytes);

        String pem = "-----BEGIN " + type + "-----\n" +
                b64 + "\n" +
                "-----END " + type + "-----\n";

        Files.writeString(path, pem, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
