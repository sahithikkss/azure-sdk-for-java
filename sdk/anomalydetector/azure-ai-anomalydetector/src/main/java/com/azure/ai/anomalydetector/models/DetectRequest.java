// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.ai.anomalydetector.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** The DetectRequest model. */
@Fluent
public final class DetectRequest {
    /*
     * Time series data points. Points should be sorted by timestamp in
     * ascending order to match the anomaly detection result. If the data is
     * not sorted correctly or there is duplicated timestamp, the API will not
     * work. In such case, an error message will be returned.
     */
    @JsonProperty(value = "series", required = true)
    private List<TimeSeriesPoint> series;

    /*
     * Can only be one of yearly, monthly, weekly, daily, hourly, minutely or
     * secondly. Granularity is used for verify whether input series is valid.
     */
    @JsonProperty(value = "granularity", required = true)
    private TimeGranularity granularity;

    /*
     * Custom Interval is used to set non-standard time interval, for example,
     * if the series is 5 minutes, request can be set as
     * {"granularity":"minutely", "customInterval":5}.
     */
    @JsonProperty(value = "customInterval")
    private Integer customInterval;

    /*
     * Optional argument, periodic value of a time series. If the value is null
     * or does not present, the API will determine the period automatically.
     */
    @JsonProperty(value = "period")
    private Integer period;

    /*
     * Optional argument, advanced model parameter, max anomaly ratio in a time
     * series.
     */
    @JsonProperty(value = "maxAnomalyRatio")
    private Float maxAnomalyRatio;

    /*
     * Optional argument, advanced model parameter, between 0-99, the lower the
     * value is, the larger the margin value will be which means less anomalies
     * will be accepted.
     */
    @JsonProperty(value = "sensitivity")
    private Integer sensitivity;

    /**
     * Get the series property: Time series data points. Points should be sorted by timestamp in ascending order to
     * match the anomaly detection result. If the data is not sorted correctly or there is duplicated timestamp, the API
     * will not work. In such case, an error message will be returned.
     *
     * @return the series value.
     */
    public List<TimeSeriesPoint> getSeries() {
        return this.series;
    }

    /**
     * Set the series property: Time series data points. Points should be sorted by timestamp in ascending order to
     * match the anomaly detection result. If the data is not sorted correctly or there is duplicated timestamp, the API
     * will not work. In such case, an error message will be returned.
     *
     * @param series the series value to set.
     * @return the DetectRequest object itself.
     */
    public DetectRequest setSeries(List<TimeSeriesPoint> series) {
        this.series = series;
        return this;
    }

    /**
     * Get the granularity property: Can only be one of yearly, monthly, weekly, daily, hourly, minutely or secondly.
     * Granularity is used for verify whether input series is valid.
     *
     * @return the granularity value.
     */
    public TimeGranularity getGranularity() {
        return this.granularity;
    }

    /**
     * Set the granularity property: Can only be one of yearly, monthly, weekly, daily, hourly, minutely or secondly.
     * Granularity is used for verify whether input series is valid.
     *
     * @param granularity the granularity value to set.
     * @return the DetectRequest object itself.
     */
    public DetectRequest setGranularity(TimeGranularity granularity) {
        this.granularity = granularity;
        return this;
    }

    /**
     * Get the customInterval property: Custom Interval is used to set non-standard time interval, for example, if the
     * series is 5 minutes, request can be set as {"granularity":"minutely", "customInterval":5}.
     *
     * @return the customInterval value.
     */
    public Integer getCustomInterval() {
        return this.customInterval;
    }

    /**
     * Set the customInterval property: Custom Interval is used to set non-standard time interval, for example, if the
     * series is 5 minutes, request can be set as {"granularity":"minutely", "customInterval":5}.
     *
     * @param customInterval the customInterval value to set.
     * @return the DetectRequest object itself.
     */
    public DetectRequest setCustomInterval(Integer customInterval) {
        this.customInterval = customInterval;
        return this;
    }

    /**
     * Get the period property: Optional argument, periodic value of a time series. If the value is null or does not
     * present, the API will determine the period automatically.
     *
     * @return the period value.
     */
    public Integer getPeriod() {
        return this.period;
    }

    /**
     * Set the period property: Optional argument, periodic value of a time series. If the value is null or does not
     * present, the API will determine the period automatically.
     *
     * @param period the period value to set.
     * @return the DetectRequest object itself.
     */
    public DetectRequest setPeriod(Integer period) {
        this.period = period;
        return this;
    }

    /**
     * Get the maxAnomalyRatio property: Optional argument, advanced model parameter, max anomaly ratio in a time
     * series.
     *
     * @return the maxAnomalyRatio value.
     */
    public Float getMaxAnomalyRatio() {
        return this.maxAnomalyRatio;
    }

    /**
     * Set the maxAnomalyRatio property: Optional argument, advanced model parameter, max anomaly ratio in a time
     * series.
     *
     * @param maxAnomalyRatio the maxAnomalyRatio value to set.
     * @return the DetectRequest object itself.
     */
    public DetectRequest setMaxAnomalyRatio(Float maxAnomalyRatio) {
        this.maxAnomalyRatio = maxAnomalyRatio;
        return this;
    }

    /**
     * Get the sensitivity property: Optional argument, advanced model parameter, between 0-99, the lower the value is,
     * the larger the margin value will be which means less anomalies will be accepted.
     *
     * @return the sensitivity value.
     */
    public Integer getSensitivity() {
        return this.sensitivity;
    }

    /**
     * Set the sensitivity property: Optional argument, advanced model parameter, between 0-99, the lower the value is,
     * the larger the margin value will be which means less anomalies will be accepted.
     *
     * @param sensitivity the sensitivity value to set.
     * @return the DetectRequest object itself.
     */
    public DetectRequest setSensitivity(Integer sensitivity) {
        this.sensitivity = sensitivity;
        return this;
    }
}
