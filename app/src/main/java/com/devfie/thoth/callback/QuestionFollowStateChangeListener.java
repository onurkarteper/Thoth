package com.devfie.thoth.callback;

import java.io.Serializable;

/**
 * Created by mac-onur on 16.04.2017.
 */

public interface QuestionFollowStateChangeListener extends Serializable {
    void onStateChange(Boolean isFollow);
}
