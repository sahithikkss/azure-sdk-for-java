/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.appplatform.v2020_07_01.implementation;

import com.microsoft.azure.management.appplatform.v2020_07_01.ConfigServerResource;
import com.microsoft.azure.arm.model.implementation.WrapperImpl;
import com.microsoft.azure.management.appplatform.v2020_07_01.ConfigServerProperties;

class ConfigServerResourceImpl extends WrapperImpl<ConfigServerResourceInner> implements ConfigServerResource {
    private final AppPlatformManager manager;
    ConfigServerResourceImpl(ConfigServerResourceInner inner, AppPlatformManager manager) {
        super(inner);
        this.manager = manager;
    }

    @Override
    public AppPlatformManager manager() {
        return this.manager;
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public ConfigServerProperties properties() {
        return this.inner().properties();
    }

    @Override
    public String type() {
        return this.inner().type();
    }

}
