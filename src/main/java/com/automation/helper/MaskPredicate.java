package com.automation.helper;

import com.google.common.base.Function;
import org.openqa.selenium.WebDriver;

public class MaskPredicate implements Function<WebDriver, Boolean> {
    private int dwrMaskMaxAbsenceCount = 2;
    private Function<WebDriver, Boolean> hiddenPredicate;
    private int maskAbsenceCount = 0;

    public MaskPredicate(Function<WebDriver, Boolean> hiddenPredicate) {
        this.hiddenPredicate = hiddenPredicate;
    }

    public MaskPredicate(int dwrMaskMaxAbsenceCount, Function<WebDriver, Boolean> hiddenPredicate) {
        this.hiddenPredicate = hiddenPredicate;
        this.dwrMaskMaxAbsenceCount = dwrMaskMaxAbsenceCount;
    }

    @Override
    public Boolean apply(WebDriver driver) {
        if (hiddenPredicate.apply(driver)) {
            // mask hidden, retry
            maskAbsenceCount++;
            if (maskAbsenceCount >= dwrMaskMaxAbsenceCount) {
                return true;
            }
        } else {
            // mask is shown, then always accept if mask is hidden
            maskAbsenceCount = dwrMaskMaxAbsenceCount + 1;
        }
        return false;
    }

}
