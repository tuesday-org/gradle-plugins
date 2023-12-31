package io.github.john.tuesday.build.logic.constants

import org.gradle.plugins.signing.SigningExtension

/**
 * Base group string for Maven publications
 */
public const val PUBLISH_GROUP: String = "io.github.john-tuesday"

/**
 * Property keys and environment variables used to find values when using [SigningExtension.useGpgCmd]
 */
public data object GpgKeys {
    /**
     * Property defining the key name used when signing with GPG [SigningExtension.useGpgCmd]
     */
    public const val KEY_NAME_PROPERTY: String = "signing.gnupg.keyName"

    /**
     * Property defining the passphrase used when signing with GPG [SigningExtension.useGpgCmd]
     */
    public const val PASSPHRASE_PROPERTY: String = "signing.gnupg.passphrase"
}

/**
 * Property keys and environment variables used to find values when using [SigningExtension.useInMemoryPgpKeys]
 */
public data object PgpInMemoryKeys {
    /**
     * Property key holding the secret key used to sign with [SigningExtension.useInMemoryPgpKeys]
     */
    public const val SECRET_KEY_PROPERTY: String = "GPG_SECRET_KEY"

    /**
     * Environment variable holding the secret key used to sign with [SigningExtension.useInMemoryPgpKeys]
     */
    public const val SECRET_KEY_ENVIRONMENT: String = "GPG_SECRET_KEY"

    /**
     * Property key holding the password used to sign with [SigningExtension.useInMemoryPgpKeys]
     */
    public const val PASSWORD_PROPERTY: String = "signing.password"

    /**
     * Environment variable holding password used to sign with [SigningExtension.useInMemoryPgpKeys]
     */
    public const val PASSWORD_ENVIRONMENT: String = "GPG_SIGNING_PASSWORD"

    /**
     * Property key holding the key id used to sign with [SigningExtension.useInMemoryPgpKeys]
     */
    public const val KEY_ID_PROPERTY: String = "signing.keyId"

    /**
     * Environment variable holding key id used to sign with [SigningExtension.useInMemoryPgpKeys]
     */
    public const val KEY_ID_ENVIRONMENT: String = "GPG_SIGNING_KEY_ID"
}
