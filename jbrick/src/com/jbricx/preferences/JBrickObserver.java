package com.jbricx.preferences;

import org.eclipse.jface.preference.PreferenceStore;

public interface JBrickObserver {
  void update(PreferenceStore store);
}
