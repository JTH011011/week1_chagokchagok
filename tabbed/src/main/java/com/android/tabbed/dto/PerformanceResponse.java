package com.android.tabbed.dto;

import com.android.tabbed.entity.Performance;

public class PerformanceResponse {
    private Performance performance;
    private String warningMessage;

    public PerformanceResponse(Performance performance, String warningMessage) {
        this.performance = performance;
        this.warningMessage = warningMessage;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }
}