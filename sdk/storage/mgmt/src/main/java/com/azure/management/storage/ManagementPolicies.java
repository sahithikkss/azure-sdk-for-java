/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.storage;


import com.azure.core.annotation.Fluent;
import com.azure.management.resources.fluentcore.collection.SupportsCreating;
import com.azure.management.resources.fluentcore.model.HasInner;
import com.azure.management.storage.models.ManagementPoliciesInner;
import reactor.core.publisher.Mono;

/**
 * Type representing ManagementPolicies.
 */
@Fluent
public interface ManagementPolicies extends SupportsCreating<ManagementPolicy.DefinitionStages.Blank>, HasInner<ManagementPoliciesInner> {
    /**
     * Gets the managementpolicy associated with the specified storage account.
     *
     * @param resourceGroupName The name of the resource group within the user's subscription. The name is case insensitive.
     * @param accountName The name of the storage account within the specified resource group. Storage account names must be between 3 and 24 characters in length and use numbers and lower-case letters only.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    Mono<ManagementPolicy> getAsync(String resourceGroupName, String accountName);

    /**
     * Deletes the managementpolicy associated with the specified storage account.
     *
     * @param resourceGroupName The name of the resource group within the user's subscription. The name is case insensitive.
     * @param accountName The name of the storage account within the specified resource group. Storage account names must be between 3 and 24 characters in length and use numbers and lower-case letters only.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    Mono<Void> deleteAsync(String resourceGroupName, String accountName);
}