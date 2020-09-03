// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.redis.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.util.logging.ClientLogger;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** The RedisRegenerateKeyParameters model. */
@Fluent
public final class RedisRegenerateKeyParameters {
    @JsonIgnore private final ClientLogger logger = new ClientLogger(RedisRegenerateKeyParameters.class);

    /*
     * The Redis access key to regenerate.
     */
    @JsonProperty(value = "keyType", required = true)
    private RedisKeyType keyType;

    /**
     * Get the keyType property: The Redis access key to regenerate.
     *
     * @return the keyType value.
     */
    public RedisKeyType keyType() {
        return this.keyType;
    }

    /**
     * Set the keyType property: The Redis access key to regenerate.
     *
     * @param keyType the keyType value to set.
     * @return the RedisRegenerateKeyParameters object itself.
     */
    public RedisRegenerateKeyParameters withKeyType(RedisKeyType keyType) {
        this.keyType = keyType;
        return this;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (keyType() == null) {
            throw logger
                .logExceptionAsError(
                    new IllegalArgumentException(
                        "Missing required property keyType in model RedisRegenerateKeyParameters"));
        }
    }
}
