package com.jbricx.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

public interface JBrickObserver {
  void update(IPreferenceStore store);
}
