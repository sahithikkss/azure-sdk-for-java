/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.network.v2020_06_01;

import rx.Observable;
import com.microsoft.azure.management.network.v2020_06_01.implementation.LoadBalancerFrontendIPConfigurationsInner;
import com.microsoft.azure.arm.model.HasInner;

/**
 * Type representing LoadBalancerFrontendIPConfigurations.
 */
public interface LoadBalancerFrontendIPConfigurations extends HasInner<LoadBalancerFrontendIPConfigurationsInner> {
    /**
     * Gets load balancer frontend IP configuration.
     *
     * @param resourceGroupName The name of the resource group.
     * @param loadBalancerName The name of the load balancer.
     * @param frontendIPConfigurationName The name of the frontend IP configuration.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    Observable<FrontendIPConfiguration> getAsync(String resourceGroupName, String loadBalancerName, String frontendIPConfigurationName);

    /**
     * Gets all the load balancer frontend IP configurations.
     *
     * @param resourceGroupName The name of the resource group.
     * @param loadBalancerName The name of the load balancer.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    Observable<FrontendIPConfiguration> listAsync(final String resourceGroupName, final String loadBalancerName);

}
